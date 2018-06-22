package com.yiyun.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeManageUtil {

	/**
	 * 将字符串的日期转换为long类型
	 * @return
	 */
	public static long  timeToLong(String date,String pattern){
		
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);	
		long time=0;
		try {
			time=sdf.parse(date).getTime();
		} catch (ParseException e) {
//			 1TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}
	
	public static  String  addDay(String date,int n)throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		cal.setTime(sdf.parse(date));
		cal.add(Calendar.DAY_OF_MONTH,n);
		return sdf.format(cal.getTime());		
	}
	
	public static String addDay(Date date,int n)throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH,n);
		return sdf.format(cal.getTime());	
	}
	
	//两个日期相隔的天数
	public static long dateGapByDay(String date1,String date2)throws Exception{
		long gap=0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date start=sdf.parse(date1);
		Date end=sdf.parse(date2);
		long milGap=end.getTime()-start.getTime();
		gap=milGap/(1000*60*60*24);
		return gap;
	}
	
	//日期格式化为String
	public static String datePattern(Date date,String pattern){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		String date_1=sdf.format(date);
		return date_1;
	}
	
	//日期格式化为String
	public static String datePattern(String date,String pattern){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		String date_1=null;
		try {
			date_1 = sdf.format((sdf.parse(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date_1;
	}
	
	public static int dayForWeek(String pTime) throws Exception {  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		 Calendar c = Calendar.getInstance();  
		 c.setTime(format.parse(pTime));  
		 int dayForWeek = 0;  
		 if(c.get(Calendar.DAY_OF_WEEK) == 1){  
		  dayForWeek = 7;  
		 }else{  
		  dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;  
		 }  
		 return dayForWeek;  
	}  
	
	public static String weekForInteger(int day){
		String week="";
		switch(day){
		  case 1:
			     week="星期一";
			     break;
		  case 2:
			     week="星期二";
			     break;
		  case 3:
			     week="星期三";
			     break;
		  case 4:
			     week="星期四";
			     break;
		  case 5:
			     week="星期五";
			     break;
		  case 6:
			     week="星期六";
			     break;
		  case 7:
			     week="星期日";
			     break;
		
		}
		return week;
	} 
	
	//计算当月的天数
	public static int monthOfDays(String date) throws Exception{
		int days=0;
		date+="-01";
		Calendar ca=Calendar.getInstance();
		ca.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		days=ca.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}
	
	//列服财务结账日计算
	public static  String  calOfDate(String trainTime)throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		Calendar cal=Calendar.getInstance();
		cal.setTime(sdf.parse(trainTime));
		int month=cal.get(Calendar.MONTH);
		int year=cal.get(Calendar.YEAR);
		if(month==0){
			month=12;
			year-=1;
		}
		String calTime="";	
		calTime=year+"-"+month;
		calTime=sdf.format(sdf.parse(calTime))+"-"+"26";
		return calTime;
	}
	
	//比较当前日期，是否是在7月28之前
	public static boolean compareDate(String date){
		boolean flag=true;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1=Calendar.getInstance();
		Calendar c2=Calendar.getInstance();
		try {
			c1.setTime(sdf.parse("2016-07-28"));
			c2.setTime(sdf.parse(date));
			if(c1.compareTo(c2)<=0){//在7月28日之后
				flag=false;
			}
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return flag;
	}
	
	public static void main(String[] args)throws Exception {
		////System.out.println(timeToLong("2016-01-07 11:49", "yyyy-MM-dd HH:mm"));
//		DecimalFormat df=new DecimalFormat("#.##");
//		//System.out.println(df.format(150.0/332.0*100).toString());
//	   List<Object[]> list=new ArrayList<>();
//	   Object[]obj_1=new Object[2];
//	   obj_1[0]="ouyang";
//	   obj_1[1]=23.45;
//	   list.add(obj_1);
//	   Object[]obj_2=new Object[2];
//	   obj_2[0]="大海";
//	   obj_2[1]=45.89;
//	   list.add(obj_2);
//	  //System.out.println(JsonUtil.obj2Json(list));
//	  //System.out.println(weekForInteger(dayForWeek("2016-04-13")));
		//System.out.println(calOfDate("2016-12"));
     	String trainStart="2016-12";
//		Calendar ca=Calendar.getInstance();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
//		ca.setTime(sdf.parse(trainStart));
//		int month=ca.get(Calendar.MONTH);
//		int year=ca.get(Calendar.YEAR);
//		if(month==0){
//			month=12;
//			year-=1;
//		}
//	    String calTime=year+"-"+month+"-"+"26";
//	    //System.out.println(calTime);
		
		String curStart= TimeManageUtil.calOfDate(trainStart);
		String curEnd=trainStart+"-25";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		
		String lastStart= TimeManageUtil.calOfDate(sdf.format(sdf.parse(curStart)));
		String lastEnd=sdf.format(sdf.parse(curStart))+"-25";
		//System.out.println(curStart+":"+curEnd);
		//System.out.println(lastStart+":"+lastEnd);
		//System.out.println(compareDate("2016-07-25"));
	}
}
