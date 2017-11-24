package com.example.lenovo.yot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lenovo on 2017/9/28.
 */
public class Time {

    public  static long timeNow(){
        return System.currentTimeMillis();
    }

    public  static String dateNow(Date date){

        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd HH:mm");
        return format1.format(date);
    }


    public  static String dateforName(){


        String dateforName;
        dateforName = String.valueOf(System.currentTimeMillis());
        return dateforName;

    }

    public static  Long  StringtoLong(String string){

        long l = Long.valueOf(string).longValue();
        return  l;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    /**
     * 返回发布时间距离当前的时间
     */
    public static String timeAgo(Long createdTime) {

        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat format3 = new SimpleDateFormat("MM-dd");


        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str_cur = formatter.format(curDate);
        Date agoDate = new Date(createdTime);//获取过去时间
        String str_ago = formatter.format(agoDate);
        if (createdTime != null) {
            long agoTimeInMin = (new Date(System.currentTimeMillis()).getTime() - new Date(createdTime).getTime()) / 1000 / 60;
            //如果在当前时间以前一分钟内
            if (agoTimeInMin <= 1) {
                return "刚刚";
            } else if (agoTimeInMin <= 60) {
                //如果传入的参数时间在当前时间以前10分钟之内
                return agoTimeInMin + "分钟前";
            } else if (agoTimeInMin <= 60 * 24) {
                if(str_ago.equals(str_cur)){
                    return agoTimeInMin / 60 + "小时前 "+format2.format(createdTime);
                }
                else{
                    return "昨天 "+format2.format(createdTime);
                }

            }
            else {
                return format3.format(createdTime);
            }
        } else {
            return format.format(new Date(0));
        }
    }

}
