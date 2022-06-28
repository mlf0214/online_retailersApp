package com.example.online_retailersapp.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseActivity;
import com.example.online_retailersapp.base.Messg;
import com.example.online_retailersapp.ui.fragment.Fragment1;
import com.example.online_retailersapp.ui.fragment.Fragment2;
import com.example.online_retailersapp.ui.fragment.Fragment3;
import com.example.online_retailersapp.ui.fragment.Fragment4;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private android.widget.LinearLayout mTab1;
    private android.widget.LinearLayout mTab2;
    private android.widget.LinearLayout mTab3;
    private android.widget.LinearLayout mTab4;
    private boolean flag=false;

    @Override
    protected void initView() {

        mTab1 = findViewById(R.id.tab1);
        mTab2 = findViewById(R.id.tab2);
        mTab3 = findViewById(R.id.tab3);
        mTab4 = findViewById(R.id.tab4);
    }

    @Override
    protected void intData() {

    }

    @Override
    public void setMessg(Messg messg) {
        if (messg.getTargCard().equals("card")){
            flag=true;
        }
    }

    @Override
    protected void initEvent() {
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        mTab4.setOnClickListener(this);
        setTab(0,null);
        if (flag){
            setTab(2,null);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab1:
                setTab(0,null);
                break;
            case R.id.tab2:
                setTab(1,null);
                break;
            case R.id.tab3:
                setTab(2,null);
                break;
            case R.id.tab4:
                setTab(3,null);
                break;
        }
    }

    private void setFragment(Fragment fragment,String messg) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (messg!=null){
            Bundle bundle=new Bundle();
            bundle.putString("messg",messg);
            fragment.setArguments(bundle);
        }
        ft.replace(R.id.home_cotent,fragment).
                setCustomAnimations(
                        FragmentTransaction.TRANSIT_ENTER_MASK,
                        FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
                )
                .commit();
    }

    public void setTab(int i,String messg) {
        initSelect();
        switch (i){
            case 0:
                mTab1.setSelected(true);
                setFragment(new Fragment1(),null);
                break;
            case 1:
                mTab2.setSelected(true);
                setFragment(new Fragment2(),messg);
                break;
            case 2:
                mTab3.setSelected(true);
                setFragment(new Fragment3(),null);
                break;
            case 3:
                mTab4.setSelected(true);
                setFragment(new Fragment4(),null);
                break;
        }
    }

    private void initSelect() {
        if (mTab1!=null){
            mTab1.setSelected(false);
        }
        if (mTab2!=null){
            mTab2.setSelected(false);
        }
        if (mTab3!=null){
            mTab3.setSelected(false);
        }
        if (mTab4!=null){
            mTab4.setSelected(false);
        }
//        mTab2.setSelected(false);
//        mTab3.setSelected(false);
//        mTab4.setSelected(false);
    }


}
