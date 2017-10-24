package com.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConvertTime {
    //写入数据库


    //字符串转化为 Timestamp
    //String tsStr = "2011-05-09 11:49:45";
    //client' string = yyyy-MM-dd HH/mm/ss;
    public static Timestamp convertToTimestamp(String dateStr) {
        try {

            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            java.util.Date date11 = df1.parse(dateStr);
            String time = df1.format(date11);
            Timestamp ts = Timestamp.valueOf(time);
            System.out.println("转换的时间: "+ts);
            return ts;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    //字符串转化为 "2015-12-02 17:02:36.750"


    //从数据库拿出

}
