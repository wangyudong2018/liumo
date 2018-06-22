package com.yiyun.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据相关工具类
 * 
 * @author bin
 *
 */
public class
DataUtil {
    /**
	 * 邀请码生成器，算法原理：<br/>
	 * 1) 获取id: 1127738 <br/>
	 * 2) 使用自定义进制转为：gpm6 <br/>
	 * 3) 转为字符串，并在后面加'o'字符：gpm6o <br/>
	 * 4）在后面随机产生若干个随机数字字符：gpm6o7 <br/>
	 * 转为自定义进制后就不会出现o这个字符，然后在后面加个'o'，这样就能确定唯一性。最后在后面产生一些随机字符进行补全。<br/>
	 */

	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p',
			'5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };

	/** (不能与自定义进制有重复) */
	private static final char b = 'o';

	/** 进制长度 */
	private static final int binLen = r.length;

	/** 序列最小长度 */
	private static final int s = 8;
	//默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 提供精确的加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	/**
	 * 提供精确的减法运算。
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 小数点以后10位，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1,double v2){
		return div(v1,v2,DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1,double v2,int scale){
		if(scale<0){
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v,int scale){
		if(scale<0){
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String round2(double abc){
		DecimalFormat df=new DecimalFormat("#.##");
		return df.format(abc);
	}

    public static String round2(BigDecimal abc) {
        if (abc == null) {
            return "0";
        }

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(abc);
    }

    public static BigDecimal add(BigDecimal val1, BigDecimal val2) {
        if (val1 == null) {
            val1 = BigDecimal.ZERO;
        }

        if (val2 == null) {
            val2 = BigDecimal.ZERO;
        }

        return val1.add(val2);
    }

	/**
	 * 根据ID生成六位随机码
	 * 
	 * @param id
	 *            ID
	 * @return 随机码
	 */
	public static String toSerialCode(long id) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			Random rnd = new Random();
			for (int i = 1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}
	/**
	 * 获取指定位数的随机字符串
	 * @param pwd_len
	 * @return
	 */
	public static String genRandomNum(int pwd_len){
	    //35是因为数组是从0开始的，26个字母+10个数字
	    final int maxNum = 36;
	    int i; //生成的随机数
	    int count = 0; //生成的密码的长度
	    char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	    StringBuffer pwd = new StringBuffer("");
	    Random r = new Random();
	    while(count < pwd_len){
	     //生成随机数，取绝对值，防止生成负数，
	     i = Math.abs(r.nextInt(maxNum)); //生成的数最大为36-1
	     if (i >= 0 && i < str.length) {
	      pwd.append(str[i]);
	      count ++;
	     }
	    }
	    return pwd.toString();
	 }

	/**
	 * 判断数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static boolean isMobile(String mobileStr) {
		if (StringUtils.isBlank(mobileStr)) {
			return false;
		}
		// Pattern pattern =
		// Pattern.compile("^(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}$");
		// return pattern.matcher(mobileStr).matches();
        return mobileStr.length() == 11 && mobileStr.startsWith("1");
    }

	public static boolean isIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
		return pattern.matcher(idCard).matches();
	}

	public static boolean isBankCard(String bankStr) {
		if (StringUtils.isBlank(bankStr)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^(\\d{16}|\\d{17}|\\d{18}|\\d{19})$");
		return pattern.matcher(bankStr).matches();
	}

	/**
	 * @Description 将银行卡号除后四位外全变为*
	 * @param bankNum
	 *            1234567890123456
	 * @author Xingbz
	 * @createDate 2017年8月2日
	 * @return 卡号前(length-4)位*号及后4位明文 ************3456
	 * @version 1.0
	 */
	public static String starsBankNum(String bankNum) {
		if (StringUtils.isBlank(bankNum)) {
			return bankNum;
		} else {
			int length = bankNum.length();
			String stars = getCharsByNum(length - 4, "*");
			// String stars = length == 16 ? "************" : "***************";
			return stars + bankNum.substring(length - 4, length);
		}
	}

	/** 身份证脱敏 */
	public static String starsIdcard(String idcard) {
	    if(StringUtils.isBlank(idcard) || (idcard.length() != 15 && idcard.length() != 18)){
	        return "";
        }
        int length = idcard.length();
        String stars = length == 15 ? "*********" : "************";
        return idcard.substring(0 , 3) + stars + idcard.substring(length - 3);
	}

    /** 手机号脱敏 */
    public static String starsPhone(String phone) {
        if(StringUtils.isBlank(phone) || phone.length() != 11){
            return "";
        }
        int length = phone.length();
        return phone.substring(0 , 3) + "****" + phone.substring(length - 4);
    }

    /** 姓名脱敏 */
    public static String starsName(String name) {
        if(StringUtils.isBlank(name)){
            return "";
        }
        int length = name.length();
        String stars = getCharsByNum(length - 1, "*");
        return name.substring(0 , 1) + stars;
    }

	/**
	 * @title 获得num个长度的指定字符
	 * @param num
	 * @param charParam
	 * @return
	 */
	public static String getCharsByNum(int num, final String charParam) {
		if (num <= 0) {
			return "";
		}
		StringBuilder sbStr = new StringBuilder();
		for (int i = 0; i < num; i++) {
			sbStr.append(charParam);
		}
		return sbStr.toString();
	}

	/**
	 * @Title 身份认证时年龄18-45岁校验，其他提示不可借款-
	 * @Description
	 * @author XieLinGe
	 * @createDate 2017年8月24日
	 * @modifier
	 * @modifyDate
	 * @version 1.0
	 */
	public static boolean checkCardAge(String cardNo) {
		int age = 0;
		Calendar cal1 = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		cal1.set(Integer.parseInt(cardNo.substring(6, 10)), Integer.parseInt(cardNo.substring(10, 12)), Integer.parseInt(cardNo.substring(12, 14)));
		age = getYearDiff(today, cal1);
        return age >= 18 && age <= 45;

	}

	public static int getYearDiff(Calendar cal, Calendar cal1) {
		int m = (cal.get(Calendar.MONTH)) - (cal1.get(Calendar.MONTH));
		int y = (cal.get(Calendar.YEAR)) - (cal1.get(Calendar.YEAR));
		return (y * 12 + m) / 12;
	}

	/**
	 * 实体类转map<String , String>
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> convertObjToMap(Object bean) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Class clazz = bean.getClass();
		Field[] fs = clazz.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field field = fs[i];
			field.setAccessible(true); // 设置些属性是可以访问的
			try {
				Object val = field.get(bean);
				if (val != null) {
					if (field != null && val != null) {
						String k = field.getName();
//						String v = val.toString();
						paramMap.put(k, val);
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			} // 得到此属性的值
		}
		return paramMap;
	}


	/* 是否是脱敏字段 */
	public static boolean isStar(String name){
        if (StringUtils.isBlank(name)) {
            return false;
        }
        return name.contains("*");
    }

	public static void main(String[] args) {
        System.out.println(starsIdcard("510501199502218275"));
        System.out.println(starsPhone("18955556666"));
	}
}
