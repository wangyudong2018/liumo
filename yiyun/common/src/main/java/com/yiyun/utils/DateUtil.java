package com.yiyun.utils;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static final String ENG_DATE_FROMAT = "EEE, d MMM yyyy HH:mm:ss z";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";
    public static final String MM_DD = "MM-dd";
    public static final String YYYY_MM_DD_ZH = "yyyy年MM月dd日";
    public static final String YYYY_MM_DD_ZH_HH_MM = "yyyy年MM月dd日HH:mm";
    public static final String YYYY_MM_DD_ZH_HH_MM_SS = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String HH_MM = "HH:mm";

    /** 默认时间格式 **/
    public static final String DATE_TIME_FORMAT = YYYY_MM_DD_HH_MM_SS;

    /** Date默认时区 **/
    public static final String DATE_TIMEZONE = "GMT+8";

    /**
     * @param date
     * @return
     * @作者 YB
     * @创建日期 2012-7-13
     * @创建时间 下午12:22:40
     * @描述 —— 格式化日期对象
     */
    public static Date date2date(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String str = sdf.format(date);
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    /**
     * @Title 当前时间是否在0：00至6：00之间
     * @Description
     * @author XieLinGe
     * @createDate 2017年11月6日 下午6:28:00
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public static boolean dataCheck(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR);
        return hour < 0 || hour > 6;
    }

    /**
     * @param dateLong
     * @param formatStr
     * @return
     * @作者 YB
     * @创建日期 2012-7-13
     * @创建时间 下午12:22:40
     * @描述 —— 格式化long日期对象
     */
    public static String long2String(Long dateLong, String formatStr) {
        if (dateLong == null || dateLong == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String dateStr = "";
        try {
            Date date = new Date(dateLong);
            dateStr = sdf.format(date);
        } catch (Exception e) {
            return null;
        }
        return dateStr;
    }

    public static String long2String(Long dateLong) {
        return long2String(dateLong, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @param date
     * @return
     * @作者 YB
     * @创建日期 2012-7-13
     * @创建时间 下午12:24:19
     * @描述 —— 时间对象转换成字符串
     */
    public static String date2string(Date date, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(date);
        return strDate;
    }

    /**
     * @param timestamp
     * @return
     * @作者 YB
     * @创建日期 2012-7-13
     * @创建时间 下午12:24:19
     * @描述 —— sql时间对象转换成字符串
     */
    public static String timestamp2string(Timestamp timestamp, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(timestamp);
        return strDate;
    }

    /**
     * @param dateString
     * @param formatStr
     * @return
     * @作者 YB
     * @创建日期 2012-7-13
     * @创建时间 下午1:09:24
     * @描述 —— 字符串转换成时间对象
     */
    public static Date string2date(String dateString, String formatStr) {
        Date formateDate = null;
        DateFormat format = new SimpleDateFormat(formatStr);
        format.setLenient(false);
        try {
            formateDate = format.parse(dateString.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return formateDate;
    }

    /**
     * @param date
     * @return
     * @作者 YB
     * @创建日期 2012-10-10
     * @创建时间 上午09:18:36
     * @描述 —— Date类型转换为Timestamp类型
     */
    public static Timestamp date2timestamp(Date date) {
        if (date == null)
            return null;
        return new Timestamp(date.getTime());
    }

    /**
     * @return
     * @作者 YB
     * @创建日期 2012-9-13
     * @创建时间 下午05:02:57
     * @描述 —— 获得当前年份
     */
    public static String getNowYear() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY);
        return sdf.format(new Date());
    }

    /**
     * @return
     * @作者 YB
     * @创建日期 2012-9-13
     * @创建时间 下午05:03:15
     * @描述 —— 获得当前月份
     */
    public static String getNowMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(MM);
        return sdf.format(new Date());
    }

    /**
     * @return
     * @作者 YB
     * @创建日期 2013-01-24
     * @创建时间 08:41:47
     * @描述 —— 获得当前日期中的日
     */
    public static String getNowDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(DD);
        return sdf.format(new Date());
    }

    /**
     * @param time
     * @return
     * @作者 YB
     * @创建日期 2012-6-17
     * @创建时间 上午10:19:31
     * @描述 —— 指定时间距离当前时间的中文信息
     */
    public static String getLnow(long time) {
        Calendar cal = Calendar.getInstance();
        long timel = cal.getTimeInMillis() - time;
        if (timel / 1000 < 60) {
            return "1分钟以内";
        } else if (timel / 1000 / 60 < 60) {
            return timel / 1000 / 60 + "分钟前";
        } else if (timel / 1000 / 60 / 60 < 24) {
            return timel / 1000 / 60 / 60 + "小时前";
        } else {
            return timel / 1000 / 60 / 60 / 24 + "天前";
        }
    }

    /**
     *
     * @Description 日期相差天数
     * @author XieLinGe
     * @createDate 2017年3月7日 下午2:36:02
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(oDate);

        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {// 跨年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {// 闰年
                    timeDistance += 366;
                } else {// 不是闰年
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {// 同年 , 直接计算差值
            return day2 - day1;
        }
    }

    /**
     * 日期加天数生成新的日期
     *
     * @param date
     *            日期
     * @param day
     *            天数
     * @return
     */
    public static Date dateAddDay(Long date, int day) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTimeInMillis(date);
        aCalendar.add(Calendar.DATE, day);
        return aCalendar.getTime();
    }

    /**
     *
     * @Description 日期相差天数
     * @author XieLinGe
     * @createDate 2017年3月7日 下午2:36:02
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public static int daysOfTwo(Long minStamp, Long maxStamp) {
        if (minStamp == null || maxStamp == null) {
            return 0;
        }

        if (minStamp > maxStamp) {
            return 0;
        }
        Date sDate = new Date(minStamp);
        Date eDate = new Date(maxStamp);

        return daysOfTwo(sDate, eDate);
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = string2date(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     *
     * @Description 获取今天0时时间
     * @author YanXiaoGuang
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public static Long getTodayStartTime() {
        long current = System.currentTimeMillis();// 当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
        // long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
        // long yesterday=System.currentTimeMillis()-24*60*60*1000;//昨天的这一时间的毫秒数
        return zero;
    }

    /**
     * @title 获取两个时间戳之间的秒数 , 用于正/倒计时使用
     * @author Xingbz
     * @createDate 2017年8月4日
     * @param minStamp
     * @param maxStamp
     * @return
     * @version 1.0
     */
    public static Long secondOfTwo(Long minStamp, Long maxStamp) {
        if (minStamp == null || maxStamp == null) {
            return 0L;
        }

        if (minStamp > maxStamp) {
            return 0L;
        }

        return (maxStamp - minStamp) / 1000;
    }

    public static Long secondOfTwo(Date minDate, Date maxDate) {
        return (maxDate.getTime() - minDate.getTime()) / 1000;
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     *
     * @Title 判断这个日期是否在三个月以内
     * @Description
     * @author XieLinGe
     * @createDate 2017年8月17日
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public static boolean checkTimeIsThreeMouth(Long time) {
        Calendar c = Calendar.getInstance();// 默认是当前日期
        int nowday = c.get(Calendar.DAY_OF_YEAR);

        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date(time));
        int oldday = c1.get(Calendar.DAY_OF_YEAR);
        if (Math.abs(nowday - oldday) > 90) {
            return false;
        } else {
            return true;
        }
    }


    public static SimpleDateFormat formatYYYYMMDD = new SimpleDateFormat("yyyyMMdd");

    /**
     * 根据身份证号获取性别 0 女 1男 3身份证格式有误导致识别失败
     */
    static public Integer getGenderByIdCard(String id) {
        if (StringUtils.isNotEmpty(id)) {
            id = id.replaceAll(" ", "");
        } else {
            return 3;
        }

        if (id.length() == 18 || id.length() == 15) {
            String ret = id.substring(id.length() - 2, id.length() - 1);
            return Integer.parseInt(ret) % 2;
        }
        return 3;
    }

    /**
     * 根据身份证获取出生日期时间戳
     * @param idCard
     * @return
     */
    public static Long birthdayFromIdCard(String idCard) {
        if (idCard != null) {
            String birthDayCache = "";
            if (idCard.length() == 18) {
                birthDayCache = idCard.substring(6, 14);
            } else if (idCard.length() == 15) {
                birthDayCache = "19" + idCard.substring(6, 12);
            }
//            System.out.println(birthDayCache);
            if (birthDayCache.length() == 8) {
                try {
                    Date ret = formatYYYYMMDD.parse(birthDayCache);
//                    System.out.println(ret.getTime());
                    return ret.getTime();
                } catch (Exception exp) {

                }
            }
        }
        return null;
    }

    /**
     * 根据出生日期时间戳获取年龄
     * @param birthDayTimeTravel
     * @return
     */
    public static int ageFromBirthDay(Long birthDayTimeTravel) {
        if (birthDayTimeTravel != null) {
            Calendar nowCalendar = Calendar.getInstance();
            Calendar birthDate = Calendar.getInstance();
            birthDate.setTime(new Date(birthDayTimeTravel));
            int age = nowCalendar.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);//根据年份差计算虚岁 , 如2018 - 2000 = 18岁
            if (nowCalendar.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH)) {//如果当年还未到生日月份 , 如当前5月 , 生日在6月 , 则实际年龄 - 1
                age = age - 1;
            }
            if (nowCalendar.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) && nowCalendar.get(Calendar.DATE) < birthDate.get(Calendar.DATE)) {//如果达到生日月份但日期未到 , 如今天为5.24 , 生日在5.25 , 则实际年龄 - 1
                age = age - 1;
            }
//            System.out.println(age);
            return age;
        }
        return 0;
    }

    public static void main(String[] args) {
        String s = "[\"[\\\"http://p0zf4lksu.bkt.clouddn.com/FjI7TWZ1Ly3eijo3PhHviAKrCAO5.png\\\"\",\"\\\"http://p0zf4lksu.bkt.clouddn.com/FlbgqU4UBXmKPe0exvTOYZ7rhuQY.png\\\"\",\"\\\"http://p0zf4lksu.bkt.clouddn.com/FgerMvkYTV1z9kNTP7qWt8RArZbz.png\\\"]\"]";
//        System.out.println(s.);
    }
}