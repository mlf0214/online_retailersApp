package com.example.online_retailersapp.ui.fragment;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseFragment;
import com.example.online_retailersapp.bean.UserData;
import com.example.online_retailersapp.ui.user.LoginActivity;
import com.example.online_retailersapp.ui.user.SelectUserOrdersActivity;
import com.example.online_retailersapp.ui.user.SetPsdActivity;
import com.example.online_retailersapp.ui.user.SetUserAddressActivity;
import com.example.online_retailersapp.ui.user.SetUserDataActivity;
import com.example.online_retailersapp.utils.DigUtil;
import com.example.online_retailersapp.utils.Shape;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;

public class Fragment4 extends BaseFragment {

    private CardView mToolbar;
    private ImageView mIconLeft;
    private TextView mBarTitle;
    private ImageView mIconRight;
    private LinearLayout mLl1;
    private LinearLayout mLl2;
    private LinearLayout mLl3;
    private LinearLayout mLl4;
    private ImageView mIv;
    private TextView mTv;
    private Button mBtn;

    @Override
    protected void initEvent() {
        mLl1.setOnClickListener(v -> sumitSetUserData());
        mLl2.setOnClickListener(v -> sumitSetUserPsd());
        mLl3.setOnClickListener(v -> sumitSelectUserOrder());
        mLl4.setOnClickListener(v -> sumitSetUserAddress());
        mBtn.setOnClickListener(v -> outlogin());
    }

    private void outlogin() {
        startActivity(new Intent(getMyActivity(),LoginActivity.class));
        //清除用户设置的信息
        Shape.removeAll();
    }

    private void sumitSelectUserOrder() {
        startActivity(new Intent(getMyActivity(), SelectUserOrdersActivity.class));
    }

    private void sumitSetUserAddress() {
        startActivity(new Intent(getMyActivity(), SetUserAddressActivity.class));
    }

    private void sumitSetUserPsd() {
        startActivity(new Intent(getMyActivity(), SetPsdActivity.class));

    }

    private void sumitSetUserData() {
        startActivity(new Intent(getMyActivity(), SetUserDataActivity.class));

    }

    @Override
    protected void initData() {
        selectUserData();
    }

    private void selectUserData() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST + Config.findUserData, Shape.getValue("token", ""));
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                getMyActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("userdata:" + s);
                        UserData userData = new Gson().fromJson(s, UserData.class);
                        UserData.DataDTO data = userData.getData();
                        if (userData.getCode() == 200) {
                            Glide.with(getMyActivity()).load(Config.HOST + data.getUserimg_uri()).into(mIv);
                            mTv.setText(data.getNickname());
                        } else if (userData.getMsg().contains("验证失败")) {
                            DigUtil.setDig(getMyActivity(), LoginActivity.class);
                        }

                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });
    }

    @Override
    protected void initView() {
        mToolbar = getView().findViewById(R.id.toolbar);
        mIconLeft = getView().findViewById(R.id.icon_left);
        mBarTitle = getView().findViewById(R.id.bar_title);
        mIconRight = getView().findViewById(R.id.icon_right);
        mLl1 = getView().findViewById(R.id.ll1);
        mLl2 = getView().findViewById(R.id.ll2);
        mLl3 = getView().findViewById(R.id.ll3);
        mLl4 = getView().findViewById(R.id.ll4);
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mBarTitle.setText("个人中心");
        mIv = getView().findViewById(R.id.iv);
        mTv = getView().findViewById(R.id.tv);
        mBtn = getView().findViewById(R.id.btn);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment4;
    }
}
