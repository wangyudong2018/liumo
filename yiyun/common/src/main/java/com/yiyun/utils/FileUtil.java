package com.yiyun.utils;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.util.UUID;


/**
 * 
 * @Title 文件工具类
 * @Description
 * @author XieLinGe
 * @createDate 2017年8月10日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class FileUtil {

	/**
	 * 
	 * @Title 读取文件
	 * @Description
	 * @author XieLinGe
	 * @createDate 2017年8月10日
	 * @modifier
	 * @modifyDate
	 * @version 1.0
	 */
	public static String readTxtFile(String filePath) {
		try {
			StringBuffer sb=new StringBuffer();
	        String tempstr=null;
			File file = new File(filePath);
			if (file.exists()) { // 判断文件是否存在
				FileInputStream fis=new FileInputStream(file);
	            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
	            while((tempstr=br.readLine())!=null){
	            	sb.append(tempstr);
	            }
	            fis.close();
	            br.close();
	            //file.delete();
	            return sb.toString();
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

	// byte数组到图片
	public static void byte2image(byte[] data, String path) {
		if (data.length < 3 || path.equals(""))
			return;
		try {
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
		} catch (Exception ex) {
		}
	}

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
            return file.delete();
		} else {
			return false;
		}
	}

	public static String renameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/* author：zss
	 * 日期：2017年3月31日
	 * 功能：根据文件名称判断类型
	 * 接受参数类型：String
	 * 返回参数类型：String
	 * 备注：文件类型不完善，有需要的自行添加
	 */
	public static int fileType(String fileName) {
		if (fileName == null) {
			fileName = "文件名为空！";
			return 500;

		} else {
			// 获取文件后缀名并转化为写，用于后续比较
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
			// 创建图片类型数组0
			String[] img = { "bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
					"cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf" };
			for (int i = 0; i < img.length; i++) {
				if (img[i].equals(fileType)) {
					return 0;
				}
			}

			// 创建文档类型数组1
			String[] document = { "txt", "doc", "docx", "xls", "htm", "html", "jsp", "rtf", "wpd", "pdf", "ppt" };
			for (int i = 0; i < document.length; i++) {
				if (document[i].equals(fileType)) {
					return 1;
				}
			}
			// 创建视频类型数组2
			String[] video = { "mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm" };
			for (int i = 0; i < video.length; i++) {
				if (video[i].equals(fileType)) {
					return 2;
				}
			}
			// 创建音乐类型数组3
			String[] music = { "mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
					"m4a", "vqf" };
			for (int i = 0; i < music.length; i++) {
				if (music[i].equals(fileType)) {
					return 3;
				}
			}

		}
		//4
		return 99;
	}
}
