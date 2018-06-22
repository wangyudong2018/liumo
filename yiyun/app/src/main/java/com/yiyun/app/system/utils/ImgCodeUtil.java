package com.yiyun.app.system.utils;

import com.yiyun.app.common.config.redis.RedisTemplateDAO;
import com.yiyun.constants.MessageConstants;
import com.yiyun.utils.Base64Util;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
/**
 * 
 * @Title 生成图片验证码
 * @Description 
 * @author XieLinGe
 * @createDate 2017年8月3日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
@Component
public class ImgCodeUtil {
	// 图片高度
    private static final int IMG_HEIGHT = 100;
    // 图片宽度
    private static final int IMG_WIDTH = 30;
    // 验证码长度
    private static final int CODE_LEN = 4;
    
    @Resource(name = "redisTemplateDAO")
	private RedisTemplateDAO redisTemplateDAO;
    
    private static ImgCodeUtil imgCodeUtil;
	
	public void setUserInfo(RedisTemplateDAO redisTemplateDAO) { 
		this.redisTemplateDAO = redisTemplateDAO; 
	} 
	
	@PostConstruct 
	public void init() { 
		imgCodeUtil = this;
		imgCodeUtil.redisTemplateDAO = this.redisTemplateDAO;
		////System.out.println("初始化验证类======================================================");
	} 
	
	/**
	 * 
	 * @Title 生成图片验证码
	 * @Description 
	 * @author XieLinGe
	 * @createDate 2017年8月3日
	 * @modifier
	 * @modifyDate
	 * @version 1.0
	 */
	public static String createCode(String phone) throws IOException {
		// 用于绘制图片，设置图片的长宽和图片类型（RGB)
		BufferedImage bi = new BufferedImage(IMG_HEIGHT, IMG_WIDTH, BufferedImage.TYPE_INT_RGB);
		// 获取绘图工具
		Graphics graphics = bi.getGraphics();
		graphics.setColor(new Color(250,223,162)); // 使用RGB设置背景颜色
		graphics.fillRect(0, 0, 100, 30); // 填充矩形区域

		// 验证码中所使用到的字符
		char[] codeChar = "123456789".toCharArray();
		String captcha = ""; // 存放生成的验证码
		Random random = new Random();
		for(int i = 0; i < CODE_LEN; i++) { // 循环将每个验证码字符绘制到图片上
		    int index = random.nextInt(codeChar.length);
		    // 随机生成验证码颜色
		    graphics.setColor(new Color(random.nextInt(150), random.nextInt(200), random.nextInt(255)));
		    // 将一个字符绘制到图片上，并制定位置（设置x,y坐标）
		    graphics.drawString(codeChar[index] + "", (i * 20) + 15, 20);
		    captcha += codeChar[index];
		}
		// 将生成的验证码code放入redis中
		imgCodeUtil.redisTemplateDAO.put(MessageConstants.IMG_CODE + phone, captcha, null, 300L);
		// 通过ImageIO将图片输出
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		ImageIO.write(bi, "JPG", out);  
		byte[] b = out.toByteArray();
		return Base64Util.encode2String(b);
	}

	public static void main(String[] args) throws IOException {
	    createCode("18812345678");
	}
}
