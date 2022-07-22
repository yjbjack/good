package com.yjb.goods.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String forMatTimeStamp(Timestamp timestamp){
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        return format;
    }

    //系统时间转化为TimeStamp
    public static Timestamp getCurrentTimeStamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static BigDecimal getBigDecimal(String data){
        BigDecimal bigDecimal = new BigDecimal(data);
        return bigDecimal;
    }

    public static String getCurrentTime(){
        Date date  = new Date();
        String strDateFormat = "yyyy-MM.dd:HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String dateFormat = sdf.format(date);
        return dateFormat;
    }
}
