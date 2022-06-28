package com.example.online_retailersapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DateUtil {
    static Date date=new Date();
    static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
    public static String getOrder_no(String params) {
        //指定前缀
        String prefix = params;
        //时间戳
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        //生成两位数的随机数
        int random = new Random().nextInt(90) + 10;
        String value = prefix + format.format(new Date()) + random;
        return value;
    }
}
