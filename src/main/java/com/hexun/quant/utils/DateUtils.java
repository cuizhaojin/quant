package com.hexun.quant.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hexun on 2017/3/29.
 */
public class DateUtils {

    public static String TIME_PATTERN = "HH:mm:ss";// 定义标准时间格式
    public static String DATE_PATTERN_1 = "yyyy/MM/dd";// 定义标准日期格式1
    public static String DATE_PATTERN_2 = "yyyy-MM-dd";// 定义标准日期格式2
    public static String DATE_PATTERN_3 = "yyyy/MM/dd HH:mm:ss";// 定义标准日期格式3，带有时间
    public static String DATE_PATTERN_4 = "yyyy/MM/dd HH:mm:ss E";// 定义标准日期格式4，带有时间和星期
    public static String DATE_PATTERN_5 = "yyyy年MM月dd日 HH:mm:ss E";// 定义标准日期格式5，带有时间和星期
    public static String DATE_PATTERN_6 = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_PATTERN_7 = "yyyy年MM月dd日 HH:mm:ss";// 定义标准日期格式7，不带星期
    public static String GetDateTimeFormat(String format) {
        return format = format == "" ? DATE_PATTERN_6 : format;

    }

    /**
     * 时间转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String DateToString(Date date, String format) {
        format = GetDateTimeFormat(format);
        SimpleDateFormat formDateFormat = new SimpleDateFormat(format);
        return formDateFormat.format(date);

    }

    /**
     * 字符串转为时间
     *
     * @param time
     * @param format
     * @return
     */
    public static Date ConvertToDateTime(String time, String format) {
        format = format != "" ? format : "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat(format);
        java.util.Date ctime = null;
        try {
            ctime = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ctime;
    }
}
