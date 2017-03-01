package com.xescm.ofc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类
 * Created by hiyond on 2016/11/15.
 */
public class DateUtils {

    //匹配 YYYY-MM-DD
    public static final String DATE = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))";
    //匹配 hh:mm:ss
    public static final String DATE_TIME = "";

    public enum DateFormatType {
        TYPE1("yyyy-MM-dd HH:mm:ss"),
        TYPE2("yyyy-MM-dd");

        DateFormatType(String type) {
            this.type = type;
        }

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * 日期转为字符串格式
     *
     * @param date 日期
     * @param type 需要转换的类型
     * @return 返回日期的字符串
     * @throws Exception 异常抛出
     */
    public static String Date2String(Date date, DateFormatType type) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type.getType());
            return simpleDateFormat.format(date);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 字符串转日期
     *
     * @param date 日期格式的字符串
     * @param type 日期类型
     * @return 返回转换后的日期
     * @throws Exception 抛出异常
     */
    public static Date String2Date(String date, DateFormatType type) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type.getType());
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 转换字符串的日期格式
     *
     * @param dateS   日期字符串
     * @param oldType 原来的日期格式
     * @param type    要转换的日期格式
     * @return 返回新格式的字符串
     * @throws Exception 抛出异常
     */
    public static String DateTransType(String dateS, String oldType, DateFormatType type) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldType);
            Date date = simpleDateFormat.parse(dateS);
            return new SimpleDateFormat(type.getType()).format(date);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     *  只获得日期的年月日
     *  处理格式为yyyy-MM-dd的字符串
     * @param date
     * @return
     */
    public static String dateSubStringGetYMD(String date) {
        try {
            date = date.substring(0,10);
            return date;
        } catch (Exception e) {
        }
        return null;
    }

    /*public static void main(String[] args) throws Exception {
        System.out.println(DateFormatType.TYPE1.getType());
        System.out.println(Date2String(new Date(), DateFormatType.TYPE1));
        System.out.println(dateSubStringGetYMD("2016-11-1888"));
        System.out.println(String2Date(dateSubStringGetYMD("2016-11-1888"), DateUtils.DateFormatType.TYPE2));
    }*/


}
