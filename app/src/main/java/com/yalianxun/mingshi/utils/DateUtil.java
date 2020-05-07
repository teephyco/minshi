package com.yalianxun.mingshi.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Calendar;

public class DateUtil {
    private static final TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");

    private static final String format1 = "yyyy-MM-dd HH:mm:ss";

    private static final String format2 = "yyyy-MM-dd";

    private static final String format3 = "HH:mm";

    private static final String format4 = "MM-dd HH:mm";

    private static final String format5 = "M月d日";

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat1 = new SimpleDateFormat(format1);

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat2 = new SimpleDateFormat(format2);

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat3 = new SimpleDateFormat(format3);

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat4 = new SimpleDateFormat(format4);

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat5 = new SimpleDateFormat(format5);

    public DateUtil()
    {

    }

    /*
     * 根据所选的时间格式将String类型的时间转换为long类型
     * @param String time, int format
     * @return long
     */

    public static long getTimeStamp(String time, int format) throws ParseException
    {
        if (format == 1)
        {
            return dateFormat1.parse(time).getTime();
        }
        else if (format == 2)
        {
            return dateFormat2.parse(time).getTime();
        }
        else
        {
            return dateFormat3.parse(time).getTime();
        }
    }
    public static long getTimeStamp(String time,int format,long defaulttime)
    {
        try{
            return getTimeStamp(time,format);
        }catch(Exception e){
            return defaulttime;
        }
    }

    /*
     * 根据所选的时间格式将long类型的时间转换为String类型  数据库的unixtime*1000 = 系统的timestamp
     * @param long time, int format
     * @return String
     */
    public static String getTimeFromDB(long timeStamp, int format) throws ParseException
    {
        return getTime(timeStamp*1000,format);
    }
    public static String getTime(long timeStamp, int format) throws ParseException
    {
        if (format == 1)
        {
            return dateFormat1.format(new Date(timeStamp));
        }
        else if (format == 2)
        {
            return dateFormat2.format(new Date(timeStamp));
        }
        else if(format == 3)
        {
            return dateFormat3.format(new Date(timeStamp));
        }
        else if(format == 4){
            return dateFormat4.format(new Date(timeStamp));
        }
        else if(format == 5){
            return dateFormat5.format(new Date(timeStamp));
        }
        else {
            return dateFormat1.format(new Date(timeStamp));
        }
    }

    /**
     * 获取默认时间
     *
     * @return date
     */
    public static Date getDefaultAsDate()
    {
        return new Date(0);
    }

    /**
     * J
     * 获取默认时间
     *
     * @return long
     */
    public static long getDefaultAsLong()
    {
        return 0L;
    }

    /**
     * J
     * 获取当前时间
     *
     * @return date
     */
    public static Date getNowAsDate()
    {
        return new Date();
    }

    /**
     * 获取当前时间
     *
     * @param format 格式
     * @param def    默认
     * @param def    默认
     * @return
     */
    public static String getNowAsString(String format, String def)
    {
        return formatDateAsString(new Date(), format, def);
    }

    /**
     * J
     * 获取当前时间
     *
     * @return long
     */
    public static long getNowAsLong()
    {
        return (new Date()).getTime();
    }

    /**
     * 将一个字符串的日期描述转换为java.util.Date对象
     *
     * @param strDate 字符串的日期描述
     * @param format  字符串的日期格式，比如:“yyyy-MM-dd HH:mm”
     * @return 字符串转换的日期对象java.util.Date
     */
    public static Date getDateFromString(String strDate, String format)
    {
        if (strDate == null || strDate.trim().equals(""))
        {
            return getDefaultAsDate();
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
//        formatter.setTimeZone(timeZone);

        Date date;
        try
        {
            date = formatter.parse(strDate);
        }
        catch (ParseException e)
        {
            date = getDefaultAsDate();
        }

        return date;
    }

    /**
     * J
     * 格式化日期 默认 0000-00-00
     *
     * @param date
     * @param s
     * @return 带默认值的时间格式化字符串
     */
    public static String formatDateAsString(Date date, String s, String def)
    {
        String ret = def;
        if (date != null && !"".equals(s))
        {
            try
            {
                ret = (new SimpleDateFormat(s)).format(date);
            }
            catch (Exception e)
            {
                ret = def;
            }
        }
        return ret;
    }

    /**
     * V
     * 传入秒数，返回如 XX:XX:XX 的一串文字
     *
     * @param second
     * @return 返回 XX:XX:XX 的文字
     */
    public static String getTimeAsString(int second)
    {
        String ret = "";
        int ss = second;
        int HH = 0;
        int mm = 0;

        HH = ss / 60 / 60;
        if (HH > 0)
        {
            ss = ss - (HH * 60 * 60);
            if (HH >= 10)
                ret = HH + ":";
            else
                ret = "0" + HH + ":";
        }
        mm = ss / 60;
        if (mm > 0)
        {
            ss = ss - (mm * 60);
            if (mm >= 10)
                ret += mm + ":";
            else
                ret += "0" + mm + ":";
        }
        else
        {
            ret += "00" + ":";
        }
        if (ss >= 10)
            ret += ss;
        else
            ret += "0" + ss;

        return ret;
    }

    /**
     * V
     * 传入一串时间文字如 XX:XX:XX，返回秒数
     *
     * @param time
     * @return 返回秒数
     */
    public static int getStringAsSeord(String time)
    {
        int ret = 0;
        if (time != null && !"".equals(time.trim()))
        {
            try
            {
                String[] times = time.trim().split(":");
                if (times.length == 3)
                {
                    ret = ret + Integer.parseInt(times[0]) * 60 * 60;    // 时
                    ret = ret + Integer.parseInt(times[1]) * 60;    // 分
                    ret = ret + Integer.parseInt(times[2]);    // 秒

                }
                else if (times.length == 2)
                {
                    ret = ret + Integer.parseInt(times[0]) * 60;    // 分
                    ret = ret + Integer.parseInt(times[1]);    // 秒
                }
                else if (times.length == 1)
                {
                    ret = ret + Integer.parseInt(times[0]);    // 秒
                }
            }
            catch (Exception e)
            {
                ret = 0;
            }
        }
        return ret;
    }

    /**
     * 得到几天前的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d,int day){
        java.util.Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d,int day){
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
        return now.getTime();
    }
}
