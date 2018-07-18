package com.yiyun.web.liumo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.yiyun.exceptions.AuthenticationException;
import com.yiyun.utils.Base64Util;
import com.yiyun.utils.StreamUtil;

/**
 * @title SecretUtils
 * @author wangyudong
 * @date 2018年7月15日下午10:57:39
 */
public class SecretUtils {

	/** RSA最大加密明文大小 */
	private static final int MAX_ENCRYPT_BLOCK = 117;
	/** RSA最大解密密文大小 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	public static final String SIGN_TYPE_RSA = "RSA";
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	public static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAL/o6DkomMM1z2yniG6PyLmq323ZPOPdzkXf+6VZrUjxZZQKIczyqU+l5xbuD/Rld8lFIA5PwZhsiPkLCImLqTsCAwEAAQ==";
	public static final String PRIVATE_KEY = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAv+joOSiYwzXPbKeIbo/Iuarfbdk8493ORd/7pVmtSPFllAohzPKpT6XnFu4P9GV3yUUgDk/BmGyI+QsIiYupOwIDAQABAkAS6qZZXAMS2u8DStBL9ahXhNxZO5yg6XA3uwhXGCvYkKc7ObyS7Y9vcHFlH5IBYczl+AkDtp3xtM5WY7Uk8WnhAiEA4JUwqvgG3mIruPR2NbvZ3h03qF1uf8virDCLvFDNm60CIQDawZ/ue8BsuxMc8/w+/3ztN9MsuUu2c9H8CtVjp+r1hwIhAK8fpvOqtLB0nON8vTDpatporh37vU6qDfd3Y39VAv+BAiBUxdYpgBGI0VJN1Qs6ip2p/Ak8+q2pjeahCr24+dimvwIhAJcB7v/b2gir37uxaegRzR3ZSyS+IQPJA/7wXyQgpKM4";

	/**
	 * @title map加签
	 * @param params
	 *            字符串
	 * @param charset
	 *            字符编码
	 * @return
	 * @throws RuntimeException
	 */
	public static String rsaSign(Map<String, String> params, String charset) throws RuntimeException {
		return rsaSign(params, PRIVATE_KEY, charset);
	}

	/**
	 * @title map加签
	 * @param params
	 *            字符串
	 * @param privateKey
	 *            私钥
	 * @param charset
	 *            字符编码
	 * @return
	 * @throws RuntimeException
	 */
	public static String rsaSign(Map<String, String> params, String privateKey, String charset) throws RuntimeException {
		String signContent = getSignContent(params);
		return rsaSign(signContent, privateKey, charset);
	}

	/**
	 * @title 字符串加签
	 * 
	 * @param content
	 *            字符串
	 * @param privateKey
	 *            私钥
	 * @param charset
	 *            字符编码
	 * @return
	 * @throws RuntimeException
	 */
	public static String rsaSign(String content, String privateKey, String charset) throws RuntimeException {
		try {
			PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));

			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);

			if (StringUtils.isEmpty(charset)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(charset));
			}

			byte[] signed = signature.sign();
			return new String(Base64Util.encodeBase64(signed));
		} catch (InvalidKeySpecException ie) {
			throw new RuntimeException("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", ie);
		} catch (Exception e) {
			throw new RuntimeException("RSAcontent = " + content + "; charset = " + charset, e);
		}
	}

	/**
	 * @title map验签 , 以默认的公钥
	 * @param params
	 * @param charset
	 * @return
	 * @throws RuntimeException
	 */
	public static boolean rsaCheck(Map<String, String> params, String charset) throws AuthenticationException {
		return rsaCheck(params, PUBLIC_KEY, charset);
	}

	/**
	 * @title map验签
	 * @param params
	 * @param publicKey
	 * @param charset
	 * @return
	 * @throws RuntimeException
	 */
	public static boolean rsaCheck(Map<String, String> params, String publicKey, String charset) throws AuthenticationException {
		String sign = params.get("sign");
		String content = getSignContent(params);
		boolean result = rsaCheckContent(content, sign, publicKey, charset);
		return result;
	}

	/**
	 * @title 字符串验签
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @param charset
	 * @return
	 * @throws RuntimeException
	 * @throws AuthenticationException
	 */
	public static boolean rsaCheckContent(String content, String sign, String publicKey, String charset) throws AuthenticationException {
		try {
			PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));

			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);

			if (StringUtils.isEmpty(charset)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(charset));
			}

			return signature.verify(Base64Util.decodeBase64(sign.getBytes()));
		} catch (Exception e) {
			throw new AuthenticationException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset);
		}
	}

	/**
	 * @title 公钥加密
	 * 
	 * @param content
	 *            待加密内容
	 * @param publicKey
	 *            公钥
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @return 密文内容
	 * @throws RuntimeException
	 */
	public static String rsaEncrypt(String content, String publicKey, String charset) throws AuthenticationException {
		try {
			PublicKey pubKey = getPublicKeyFromX509(SIGN_TYPE_RSA, new ByteArrayInputStream(publicKey.getBytes()));
			Cipher cipher = Cipher.getInstance(SIGN_TYPE_RSA);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] data = StringUtils.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = Base64Util.encodeBase64(out.toByteArray());
			out.close();

			return StringUtils.isEmpty(charset) ? new String(encryptedData) : new String(encryptedData, charset);
		} catch (Exception e) {
			throw new AuthenticationException("EncryptContent = " + content + ",charset = " + charset);
		}
	}

	/**
	 * @title 私钥解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param privateKey
	 *            私钥
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @return 明文内容
	 * @throws RuntimeException
	 */
	public static String rsaDecrypt(String content, String privateKey, String charset) throws AuthenticationException {
		try {
			PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));
			Cipher cipher = Cipher.getInstance(SIGN_TYPE_RSA);
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			byte[] encryptedData = StringUtils.isEmpty(charset) ? Base64Util.decodeBase64(content.getBytes()) : Base64Util.decodeBase64(content.getBytes(charset));
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();

			return StringUtils.isEmpty(charset) ? new String(decryptedData) : new String(decryptedData, charset);
		} catch (Exception e) {
			throw new AuthenticationException("EncodeContent = " + content + ",charset = " + charset);
		}
	}

	// ======= 数据处理的一些方法

	/**
	 * @title 获取公钥对象
	 * @param algorithm
	 * @param ins
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		StringWriter writer = new StringWriter();
		StreamUtil.io(new InputStreamReader(ins), writer);

		byte[] encodedKey = writer.toString().getBytes();

		encodedKey = Base64Util.decodeBase64(encodedKey);

		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}

	/**
	 * @title 获取私钥对象
	 * @param algorithm
	 * @param ins
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
		if (ins == null || StringUtils.isEmpty(algorithm)) {
			return null;
		}

		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		byte[] encodedKey = StreamUtil.readText(ins).getBytes();

		encodedKey = Base64Util.decodeBase64(encodedKey);

		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}

	/**
	 * @title 将map中的参数排序后组合成字符串(key1=value1&key2=value2...)
	 * @param params
	 * @return
	 */
	public static String getSignContent(Map<String, String> params) {
		if (params == null) {
			return null;
		}

		StringBuffer content = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		int index = 0;
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
				if (!"sign".equals(key) && !"sign_type".equals(key)) {
					content.append((index == 0 ? "" : "&") + key + "=" + value);
				}
				index++;
			}
		}
		return content.toString();
	}

	public static String getToken(String id) {
		String token = null;
		try {
			String content = id + "#" + System.currentTimeMillis();
			token = rsaEncrypt(content, PUBLIC_KEY, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	public static String getTokenUserId(String token) {
		try {
			if (StringUtils.isBlank(token)) {
				return null;
			}

			String content = rsaDecrypt(token, PRIVATE_KEY, "UTF-8");
			if (StringUtils.isBlank(content)) {
				return null;
			}

			String[] data = content.split("#");
			if (data.length != 2) {
				return null;
			}

			String date = data[1];
			if ((System.currentTimeMillis() - DateUtils.MILLIS_PER_DAY * 3) > Long.parseLong(date)) {
				return null;
			}
			return data[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}