package com.yiyun.core.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
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
}
