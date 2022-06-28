package com.example.online_retailersapp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseFragment extends Fragment {
    private View view;
    private Activity mactivity;
    public Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        bundle = getArguments();
        initView();
        initData();
        initEvent();
        return view;
    }

    protected abstract void initEvent();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

    public View getView(){
        return this.view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mactivity= (Activity) getContext();
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void setMesg(Messg messg){}

    public Activity getMyActivity() {
        return this.mactivity;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
