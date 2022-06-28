package com.example.online_retailersapp.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityMessg {
    private static List<Activity> activityList;
    static {
        activityList=new ArrayList<>();
    }
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    public static void exit(){
        for (Activity a:
             activityList) {
            a.finish();

        }
    }


}
