package com.yiyun.utils;

import java.util.Random;


/**
 * @title 令牌工具类
 * @author Xingbz
 * @createDate 2017年8月9日
 * @version 1.0
 *
 */
public class TokenUtil {

	/**
	 * @title 创建校验令牌
	 * @description 生成规则 , 随机12位字节再进行Base64编码获得15位长度的随机字符串
	 * @author Xingbz
	 * @createDate 2017年8月9日
	 * @return 生成的令牌字符串
	 * @version 1.0
	 */
	public static String createToken() {
		Random rnd = new Random();
		byte[] tokenData = new byte[12];
		rnd.nextBytes(tokenData);
		String token = Base64Util.encode2String(tokenData);
		return token.substring(0, token.length() - 1);
	}


	public static void main(String[] args) {
		for(int i =0;i<1000;i++){
			String token = createToken();
			if(token.indexOf("/") >= 0){

				//System.out.println(token);
			}
		}
//		//System.out.println(createToken());
	}

}