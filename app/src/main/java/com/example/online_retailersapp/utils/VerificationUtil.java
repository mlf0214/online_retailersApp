package com.example.online_retailersapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationUtil {
    /*
     * @description: 验证手机号
     * @author: Ma LingFei
     * @date: 2022/6/1
     * @param: phone
     * @return:
     * @return: boolean
     **/
    public static boolean phone(String phone){
        String regex = "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }
    /*
     * @description: 判断密码格式
     * @author: Ma LingFei
     * @date: 2022/6/1
     * @param: password
     * @return: java.lang.Boolean
     **/
    public static Boolean password(String password){
        String regex="regx = /^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*])[\\da-zA-Z~!@#$%^&*]{8,}$/\n";
        Pattern p=Pattern.compile(regex);
        Matcher matcher = p.matcher(password);
        return matcher.matches();
    }
    /*
     * @description: 判断用户名格式
     * @author: Ma LingFei
     * @date: 2022/6/1
     * @param: username
     * @return: boolean
     **/
    public static  boolean username(String username){
        String regex="regx = /^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*])[\\da-zA-Z~!@#$%^&*]{6,10}$/\n";
        Pattern p=Pattern.compile(regex);
        Matcher matcher = p.matcher(username);
        return matcher.matches();
    }
}
