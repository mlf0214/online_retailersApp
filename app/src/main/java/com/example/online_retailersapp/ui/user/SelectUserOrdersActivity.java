package com.example.online_retailersapp.ui.user;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseActivity;
import com.example.online_retailersapp.bean.AllordersBean;
import com.example.online_retailersapp.ui.home.HomeActivity;
import com.example.online_retailersapp.utils.BaseRecyclerViewAdapter;
import com.example.online_retailersapp.utils.MyViewHolder;
import com.example.online_retailersapp.utils.Shape;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;

import java.util.List;

public class SelectUserOrdersActivity extends BaseActivity {
    private androidx.cardview.widget.CardView mToolbar;
    private android.widget.ImageView mIconLeft;
    private android.widget.TextView mBarTitle;
    private android.widget.ImageView mIconRight;
    private android.widget.TextView mTvClear;
    private androidx.recyclerview.widget.RecyclerView mRv;


    @Override
    protected void initView() {

        mToolbar = findViewById(R.id.toolbar);
        mIconLeft = findViewById(R.id.icon_left);
        mBarTitle = findViewById(R.id.bar_title);
        mIconRight = findViewById(R.id.icon_right);
        mRv = findViewById(R.id.rv);
        mBarTitle.setText("我的订单");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mIconLeft.setImageResource(R.drawable.ic_baseline_chevron_left_24);

    }

    @Override
    protected void intData() {
        selectUserOrders();
    }

    private void selectUserOrders() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST + Config.findAllUserOrders, Shape.getValue("token", ""));
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                System.out.println("allorders:" + s);
                AllordersBean allordersBean = new Gson().fromJson(s, AllordersBean.class);
                List<AllordersBean.DatasDTO> datas = allordersBean.getDatas();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (datas.size() == 0) {
                            Toast.makeText(SelectUserOrdersActivity.this, "没有内容", Toast.LENGTH_SHORT).show();
                        } else {
                            mRv.setLayoutManager(new LinearLayoutManager(SelectUserOrdersActivity.this));
                            mRv.setAdapter(new BaseRecyclerViewAdapter<AllordersBean.DatasDTO>(datas, R.layout.order1_item, datas.size()) {

                                private TextView mTvOrderNo;
                                private TextView mTvPrice;
                                private TextView mTvNumber;
                                private TextView mTvName;
                                private ImageView mIv;

                                @Override
                                protected void conView(MyViewHolder holder, AllordersBean.DatasDTO datasDTO, int position) {
                                    mTvOrderNo = holder.getView(R.id.tv_order_no);
                                    mIv = holder.getView(R.id.iv);
                                    mTvName = holder.getView(R.id.tv_name);
                                    mTvNumber = holder.getView(R.id.tv_number);
                                    Glide.with(getApplicationContext()).load(Config.HOST + datasDTO.getImg_uri()).into(mIv);
                                    mTvName.setText(datasDTO.getC_name());
                                    mTvPrice = holder.getView(R.id.tv_price);
                                    mTvNumber.setText("数量:" + datasDTO.getNumber());
                                    Integer integer = Integer.valueOf(datasDTO.getNumber());
                                    Float value = Float.valueOf(datasDTO.getPrice());
                                    float v = integer * value;
                                    mTvPrice.setText("总价:" + v + "");
                                    mTvOrderNo.setText(datasDTO.getOrder_no());
                                }
                            });
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
    protected void initEvent() {
        mIconLeft.setOnClickListener(v -> finish());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_selectuserorders;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SelectUserOrdersActivity.this, HomeActivity.class));
        super.onBackPressed();

    }
}
