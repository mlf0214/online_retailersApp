package com.example.online_retailersapp.utils;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MyViewHolder";
    private SparseArray<View> sparseArray;
    public View countView;
    private List<View> list;
    public int flag=0;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        countView=itemView;
        sparseArray=new SparseArray<>();
        list=new ArrayList<>();
//        Log.d(TAG, String.valueOf(this));
        System.out.println("MyViewHolder构造方法被调用了");
    }

    public <T extends View> T getView(int viewId){
        View view = sparseArray.get(viewId);
        if (view==null){
            view=countView.findViewById(viewId);
            sparseArray.put(viewId,view);
            boolean add = list.add(view);
            if (add){
                flag++;
            }
        }
        return (T) view;
    }
    public View getView2(int flag){
        return list.get(flag);
    }



}
