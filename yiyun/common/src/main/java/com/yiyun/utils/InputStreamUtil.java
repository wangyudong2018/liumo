package com.yiyun.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 
 *
 */
public class InputStreamUtil {

	final static int BUFFER_SIZE = 4096;

	/**
	 * 将InputStream转换成String
	 * 
	 * @param in
	 *            InputStream
	 * @return String
	 * @throws Exception
	 * 
	 */
	public static String InputStreamTOString(InputStream in) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "ISO-8859-1");
	}

	/**
	 * 将InputStream转换成某种字符编码的String
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String InputStreamTOString(InputStream in, String encoding)
			throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "ISO-8859-1");
	}

	/**
	 * 将String转换成InputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream StringTOInputStream(String in) throws Exception {

		ByteArrayInputStream is = new ByteArrayInputStream(
				in.getBytes("ISO-8859-1"));
		return is;
	}

	/**
	 * 将InputStream转换成byte数组
	 * 
	 * @param in
	 *            InputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] InputStreamTOByte(InputStream in) throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return outStream.toByteArray();
	}

	/**
	 * 将byte数组转换成InputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream byteTOInputStream(byte[] in) throws Exception {

		ByteArrayInputStream is = new ByteArrayInputStream(in);
		return is;
	}

	/**
	 * 将byte数组转换成String
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String byteTOString(byte[] in) throws Exception {

		InputStream is = byteTOInputStream(in);
		return InputStreamTOString(is);
	}

	/**  
	 * @title           根据二进制字符串生成图片  
	 * @param data      生成图片的二进制字符串  
	 * @param fileName  图片名称(完整路径)  
	 * @param type      图片类型  
	 * @return  
	 */  
	public static void saveImage(String data, String fileName,String type) {
		File isExists = new File(fileName);    
		if(!isExists.exists()){
		    BufferedImage image = new BufferedImage(300, 300,BufferedImage.TYPE_BYTE_BINARY);   
		    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();   
		    try {   
		        ImageIO.write(image, type, byteOutputStream);   
		        // byte[] date = byteOutputStream.toByteArray();   
		        byte[] bytes = hex2byte(data);   
		        RandomAccessFile file = new RandomAccessFile(fileName, "rw");   
		        file.write(bytes);   
		        file.close();   
		    } catch (IOException e) {   
		        e.printStackTrace();   
		    } 
		}
	}   
	  
	/**  
	 * 反格式化byte  
	 *   
	 * @param s  
	 * @return  
	 */  
	public static byte[] hex2byte(String s) {   
	    byte[] src = s.toLowerCase().getBytes();   
	    byte[] ret = new byte[src.length / 2];   
	    for (int i = 0; i < src.length; i += 2) {   
	        byte hi = src[i];   
	        byte low = src[i + 1];   
	        hi = (byte) ((hi >= 'a' && hi <= 'f') ? 0x0a + (hi - 'a')   
	                : hi - '0');   
	        low = (byte) ((low >= 'a' && low <= 'f') ? 0x0a + (low - 'a')   
	                : low - '0');   
	        ret[i / 2] = (byte) (hi << 4 | low);   
	    }   
	    return ret;   
	}   
	  
	/**  
	 * 格式化byte  
	 *   
	 * @param b  
	 * @return  
	 */  
	public static String byte2hex(byte[] b) {   
	    char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',   
	            'B', 'C', 'D', 'E', 'F' };   
	    char[] out = new char[b.length * 2];   
	    for (int i = 0; i < b.length; i++) {   
	        byte c = b[i];   
	        out[i * 2] = Digit[(c >>> 4) & 0X0F];   
	        out[i * 2 + 1] = Digit[c & 0X0F];   
	    }   
	  
	    return new String(out);   
	}
}
