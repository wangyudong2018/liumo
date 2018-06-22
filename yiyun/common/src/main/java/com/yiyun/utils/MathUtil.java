package com.yiyun.utils;

import java.math.BigDecimal;

/**
 * @title 数学数字相关工具类
 * @description 数字精度转换 , 小数百分比互转等等
 * @author Xingbz
 * @createDate 2017年8月1日
 * @version 1.0
 *
 */
public class MathUtil {

	/**
	 * @title 数字转为字符串 , 若为空则返回0
	 * @param num
	 * @return
	 */
	public static String num2StrNotNal(BigDecimal num){
		if(num == null){
			return "0";
		}
		return num.toString();
	}
	
	/**
	 * @title 数字转为字符产,若为空则返回NULL
	 * @param num
	 * @return
	 */
	public static String num2Str(BigDecimal num){
		return num == null ? null : num.toString();
	}
	
	/**
	 * @title 计算多个数据和和
	 * @param numArr
	 * @return
	 */
	public static BigDecimal sum(Object... numArr){
		BigDecimal sum = BigDecimal.ZERO;
		if(numArr == null || numArr.length == 0){
			return sum;
		}
		for(Object num : numArr){
			BigDecimal bd = BigDecimal.ZERO;
			if(num instanceof Integer || num instanceof Long || num instanceof Float || num instanceof Double || num instanceof BigDecimal){
				bd = new BigDecimal(num + "");
				sum = sum.add(bd == null ? BigDecimal.ZERO : bd);
			}
		}
		return sum;
	}
	
	/**
	 * @Title 拼接sql查询条件 in
	 * @Description
	 * @author XieLinGe
	 * @createDate 2017年10月19日 下午3:03:33
	 * @modifier
	 * @modifyDate	
	 * @version 1.0
	 */
	public static String getInParam(String param){
		String [] temp = param.split(",");
		StringBuilder url = new StringBuilder();
		for (int i = 0; i < temp.length; i++) {
			if(i>0){
				url.append("'");
			}
			url.append(temp[i]).append("',");
		}

		return url.substring(0,(url.lastIndexOf(",")-1));
	}
	
	public static void main(String[] args) {
		////System.out.println(sum(1,0.1,10L,12.3f , BigDecimal.TEN));
//		String[] s = "app,Andorid".split(",");
//		String ss = "";
//		for (int i = 0; i < s.length; i++) {
//			if (i == 0) {
//				ss += "'";
//			}
//			ss += s[i];
//			if (i < s.length - 1) {
//				ss += ",";
//			}
//			if (i == s.length - 1) {
//				ss += "'";
//			}
//		}
//		//System.out.println(ss);
		//System.out.println(getInParam("app,Andorid"));
	}
}

