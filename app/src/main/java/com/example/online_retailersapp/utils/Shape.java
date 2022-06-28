package com.example.online_retailersapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class Shape {


    private static final SharedPreferences preferences=MyApplication.getContext().getSharedPreferences("mlf",Context.MODE_PRIVATE);
    private static final SharedPreferences.Editor editor=preferences.edit();

    public static void setValue(String key,String value){
        editor.putString(key, value).commit();
    }
    public static void setValue(String key,int value){
        editor.putInt(key, value).commit();
    }
    public static void setValue(String key,boolean value){
        editor.putBoolean(key, value).commit();
    }
    public static void setValue(String key,float value){
        editor.putFloat(key, value).commit();
    }
    public static void setStringSet(String key, Set<String> value){
        editor.putStringSet(key, value).commit();
    }
    public static String getValue(String key,String value){
        return preferences.getString(key, value);
    }
    public static int getValue(String key,int value){
        return preferences.getInt(key, value);
    }
    public static Float getValue(String key,float value){
        return preferences.getFloat(key, value);
    }
    public static Set<String> getValue(String key,Set<String> value){
        return preferences.getStringSet(key, value);
    }
    public static boolean getValue(String key,boolean value){
        return preferences.getBoolean(key, value);
    }
    public static void removeAll(){
        editor.clear().commit();
    }


}
