package com.example.online_retailersapp.ui.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseActivity;
import com.example.online_retailersapp.base.Messg;
import com.example.online_retailersapp.bean.AddOrder;
import com.example.online_retailersapp.bean.BatchDeleteCards;
import com.example.online_retailersapp.bean.FindUserAddressByIdBean;
import com.example.online_retailersapp.bean.JsonBean;
import com.example.online_retailersapp.bean.SelectAllCards;
import com.example.online_retailersapp.ui.user.SelectUserOrdersActivity;
import com.example.online_retailersapp.ui.user.SetUserAddressActivity;
import com.example.online_retailersapp.utils.BaseRecyclerViewAdapter;
import com.example.online_retailersapp.utils.DateUtil;
import com.example.online_retailersapp.utils.MyApplication;
import com.example.online_retailersapp.utils.MyViewHolder;
import com.example.online_retailersapp.utils.Shape;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends BaseActivity {
    List<SelectAllCards.DatasDTO> list = new ArrayList<>();
    private JsonBean jsonBean;
    private CardView mToolbar;
    private android.widget.ImageView mIconLeft;
    private android.widget.TextView mBarTitle;
    private android.widget.ImageView mIconRight;
    private androidx.recyclerview.widget.RecyclerView mRv;
    private android.widget.TextView mTvAllprice;
    private android.widget.Button mBtnZf;
    private Map<String, Integer> numberMap;
    private List<String> c_names;
    private Map<String, String> priceMap;
    private float allprice;
    private List<JsonBean.DatasDTO> datas;
    private boolean b = false;

    @Override
    protected void initView() {

        mToolbar = findViewById(R.id.toolbar);
        mIconLeft = findViewById(R.id.icon_left);
        mBarTitle = findViewById(R.id.bar_title);
        mIconRight = findViewById(R.id.icon_right);
        mRv = findViewById(R.id.rv);
        mTvAllprice = findViewById(R.id.tv_allprice);
        mBtnZf = findViewById(R.id.btn_zf);
        mBarTitle.setText("提交订单");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mIconLeft.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        c_names = new ArrayList<>();
    }

    @Override
    protected void intData() {
        setRv();

        for (int i = 0; i < datas.size(); i++) {
            JsonBean.DatasDTO datasDTO = datas.get(i);
            //个数
            Integer integer = numberMap.get(datasDTO.getC_name());
            String s = priceMap.get(datasDTO.getC_name());
            Float value = Float.valueOf(s);
            float v = value * integer;
            allprice += v;
        }
        mTvAllprice.setText(allprice + "");
    }

    private void addUserOrdersOkhp(String token, Map<String, Integer> map, JsonBean jsonBean) {
        List<JsonBean.DatasDTO> datas = jsonBean.getDatas();
        for (int i = 0; i < map.size(); i++) {
            Integer integer = map.get(jsonBean.getDatas().get(i).getC_name());
            datas.get(i).setC_count(integer);
        }
        if (Shape.getValue("receiving", "").equals("") || Shape.getValue("receiving", "") == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
            builder.setTitle("还未设置默认收货地址,请设置")
                    .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(OrderActivity.this, SetUserAddressActivity.class));
                        }
                    }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        } else {
//            解析json
            FindUserAddressByIdBean.DataDTO receiving = new Gson().fromJson(Shape.getValue("receiving", ""),
                    FindUserAddressByIdBean.DataDTO.class);
            System.out.println("receiving:" + Shape.getValue("receiving", ""));
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("card", datas);
            objectMap.put("receiving", receiving);
            objectMap.put("order_no", DateUtil.getOrder_no("jx"));
            String json = new Gson().toJson(objectMap);
            okhtp(json);

            removeCard(datas);
            System.out.println("已经设置默认地址");
        }
    }

    private void removeCard(List<JsonBean.DatasDTO> datas) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            list.add(datas.get(i).getC_id());
        }
//        removeCardOkhp(list);
    }

    private void removeCardOkhp(List<Integer> list) {
        String value = String.valueOf(list);
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setPost(Config.HOST + Config.batchDeleteCards, Shape.getValue("token", ""), "Ids", value);
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                BatchDeleteCards batchDeleteCards = new Gson().fromJson(s, BatchDeleteCards.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = batchDeleteCards.getCode();
                        if (code == 200) {
//                            Messg messg = new Messg();
//                            messg.setTargCard("card");
//                            EventBus.getDefault().postSticky(messg);
//                            startActivity(new Intent(OrderActivity.this, HomeActivity.class));
                        }
                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });
    }

    private void okhtp(String json) {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setPost(Config.HOST + Config.addUserAddOrders, Shape.getValue("token", ""), "json", json);
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OrderActivity.this, s, Toast.LENGTH_SHORT).show();
                        AddOrder addOrder = new Gson().fromJson(s, AddOrder.class);
                        Integer code = addOrder.getCode();
                        if (code == 200) {
                            startActivity(new Intent(OrderActivity.this, SelectUserOrdersActivity.class));
                        }
                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });
    }

    private void setRv() {
        datas = jsonBean.getDatas();
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(new BaseRecyclerViewAdapter<JsonBean.DatasDTO>(datas, R.layout.order_item, datas.size()) {
            private TextView mTvPrice;
            private TextView mTvNumber;
            private TextView mTvName;
            private ImageView mIv;
            @Override
            protected void conView(MyViewHolder holder, JsonBean.DatasDTO datasDTO, int position) {
                mTvPrice = holder.getView(R.id.tv_price);
                mIv = holder.getView(R.id.iv);
                mTvName = holder.getView(R.id.tv_name);
                mTvNumber = holder.getView(R.id.tv_number);
                Glide.with(MyApplication.getContext()).load(Config.HOST + datasDTO.getImg_uri())
                        .into(mIv);
                c_names.add(datasDTO.getC_name());
                mTvName.setText(c_names.get(position));
                mTvNumber.setText("数量:" + numberMap.get(datasDTO.getC_name()));
                mTvPrice.setText("单价:"+datasDTO.getC_price());
            }
        });
    }

    @Override
    public void setMessg(Messg messg) {
        String json = messg.getJson();
        Toast.makeText(this, json, Toast.LENGTH_SHORT).show();
        jsonBean = new Gson().fromJson(json, JsonBean.class);
        priceMap = messg.getPriceMap();
        numberMap = messg.getNumbermap();
        datas = jsonBean.getDatas();
    }

    @Override
    protected void initEvent() {
        mIconLeft.setOnClickListener(v -> finish());
        mBtnZf.setOnClickListener(v -> sumit());
    }

    private void sumit() {
        Toast.makeText(this, String.valueOf(c_names), Toast.LENGTH_SHORT).show();
        addUserOrdersOkhp(Shape.getValue("token", ""), numberMap, jsonBean);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }
}
