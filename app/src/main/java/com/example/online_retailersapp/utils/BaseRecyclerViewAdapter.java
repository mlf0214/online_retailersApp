package com.example.online_retailersapp.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyViewHolder> {
    private List<T> list;
    private int size;
    private int layoutId;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }


    public BaseRecyclerViewAdapter(List<T> list, int layoutId, int size) {
        this.list = list;
        this.size = size;
        this.layoutId = layoutId;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(layoutId, parent, false);
        System.out.println("onCreateViewHolder方法被调用了");
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        conView(holder,list.get(position),position);


    }
    protected abstract void conView(MyViewHolder holder, T t, int position);
    @Override
    public int getItemCount() {
        return size;
    }
    public void setDATA(List<T> rows) {
        this.list.clear();
        this.list=rows;
        notifyDataSetChanged();
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }






}
