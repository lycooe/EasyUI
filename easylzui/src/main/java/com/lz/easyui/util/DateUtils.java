package com.lz.easyui.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final DateFormat FORMATOR_YMDHMS_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat FORMATOR_YMDHMS_2 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final DateFormat FORMATOR_YMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final DateFormat FORMATOR_YMD_1 = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat FORMATOR_YMD_2 = new SimpleDateFormat("yyyyMMdd");
    public static final DateFormat FORMATOR_YMD_3 = new SimpleDateFormat("yyyy年MM月dd日");
    public static final DateFormat FORMATOR_MDHM = new SimpleDateFormat("MM-dd HH:mm");
    public static final DateFormat FORMATOR_TIME_HM = new SimpleDateFormat("HH:mm");
    public static final DateFormat FORMATOR_TIME_MS= new SimpleDateFormat("mm:ss");
    public static final DateFormat FORMATOR_MDW_HM = new SimpleDateFormat("MM月dd日 HH:mm");
    public static final DateFormat FORMATOR_TMY = new SimpleDateFormat("HH:mm MM月dd日 yyyy年");
    public static final DateFormat FORMATOR_MD = new SimpleDateFormat("MM月dd日");


    public static String getDateStr(Date date, DateFormat dateFormat) {
        return dateFormat.format(date);
    }

    public static String getDateStr(long time, DateFormat dateFormat) {
        return dateFormat.format(new Date(time));
    }

    public static Date getDate(String dateStr, DateFormat dateFormat) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static long getDateLong(String dateStr, DateFormat dateFormat) {
        Date date;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return 0l;
        }
        return date.getTime();
    }

    public static String getDateTransformation(String dateStr, DateFormat dateFormatFrom, DateFormat dateFormatTo) {
        try {
            Date date = dateFormatFrom.parse(dateStr);
            return dateFormatTo.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getDateFromLong(long source) {
        Date date = new Date(source);
        return date;
    }

    public static String getChineseDate(long time) {
        Date date = getDateFromLong(time);
        String d = "";
        int index = date.getDay();
        switch (index) {
            case 0:
                d = "周日";
                break;
            case 1:
                d = "周一";
                break;
            case 2:
                d = "周二";
                break;
            case 3:
                d = "周三";
                break;
            case 4:
                d = "周四";
                break;
            case 5:
                d = "周五";
                break;
            case 6:
                d = "周六";
                break;
        }
        return d;
    }

    public static String getChineseDate2(long time) {
        Date date = getDateFromLong(time);
        String d = "";
        int index = date.getDay();
        switch (index) {
            case 0:
                d = "星期日";
                break;
            case 1:
                d = "星期一";
                break;
            case 2:
                d = "星期二";
                break;
            case 3:
                d = "星期三";
                break;
            case 4:
                d = "星期四";
                break;
            case 5:
                d = "星期五";
                break;
            case 6:
                d = "星期六";
                break;
        }
        return d;
    }

    public static void main(String[] str){
        System.out.println(getDateTransformation("05-30 9:30", FORMATOR_MDHM, FORMATOR_YMDHM));
    }
}
