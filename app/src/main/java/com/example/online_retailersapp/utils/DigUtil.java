package com.example.online_retailersapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;

import com.example.online_retailersapp.utils.myinterface.MyInterfaceDig;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

public class DigUtil  {
    public static MyInterfaceDig myInterfaceDig;
    private static CityPickerView cityPickerView;
    public static void setDig(Activity activity,Class<?> c){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Token已过期，请重新登录")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.startActivity(new Intent(activity,c));
                    }
                }).create().show();

    }
    //先设置这个
    public static void showCityPicker(Activity activity) {
         cityPickerView = new CityPickerView();
        cityPickerView.init(activity);
        CityConfig cityConfig = new CityConfig.Builder()
                //设置标题和颜色
                .title("地址选择")
                .titleTextColor("#EF0C0C")
                .cancelTextColor("#095CFA")
                .confirTextColor("#F106B5")
                .setLineColor("#0EF106")
                .visibleItemsCount(5)
                .province("浙江省")//默认显示的省份
                .city("杭州市")//默认显示省份下面的城市
                .district("余杭区")//默认显示省市下面的区县数据
                .build();
        cityPickerView.setConfig(cityConfig);
    }
    public static CityPickerView getCityPickerView(TextView textView){
        cityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                String s = province + ":" + city + ":" + district;
                textView.setText(s);
            }
        });
        cityPickerView.showCityPicker();
        return cityPickerView;
    }
    public static void setMyInterfaceDig(MyInterfaceDig myInterfaceDig1){
        myInterfaceDig=myInterfaceDig1;
    }
    public static void delete(Activity activity) {
        AlertDialog.Builder b=new AlertDialog.Builder(activity);
        b.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterfaceDig.OnYes();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setTitle("删了不可恢复哦").create().show();
    }
}
