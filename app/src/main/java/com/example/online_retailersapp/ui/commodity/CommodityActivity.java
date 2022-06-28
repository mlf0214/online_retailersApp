package com.example.online_retailersapp.ui.commodity;

import android.content.Intent;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseActivity;
import com.example.online_retailersapp.base.Messg;
import com.example.online_retailersapp.bean.AddOrder;
import com.example.online_retailersapp.bean.CommodityData;
import com.example.online_retailersapp.ui.order.OrderActivity;
import com.example.online_retailersapp.ui.user.LoginActivity;
import com.example.online_retailersapp.utils.MyApplication;
import com.example.online_retailersapp.utils.Shape;
import com.example.online_retailersapp.utils.DigUtil;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommodityActivity extends BaseActivity {
    private String c_id;
    private android.widget.ImageView mIconLeft;
    private android.widget.TextView mBarTitle;
    private android.widget.ImageView mIv;
    private android.widget.TextView mTvName;
    private android.widget.TextView mTvContent;
    private android.widget.TextView mPrice;
    private android.widget.ImageView mRemove;
    private android.widget.TextView mNumber;
    private android.widget.ImageView mAdd;
    private android.widget.Button mCard;
    private android.widget.Button mOrder;
    private int count=1;
    private CommodityData commodityData=null;
    @Override
    protected void initView() {
        mIconLeft = findViewById(R.id.icon_left);
        mBarTitle = findViewById(R.id.bar_title);
        mIv = findViewById(R.id.iv);
        mTvName = findViewById(R.id.tv_name);
        mTvContent = findViewById(R.id.tv_content);
        mPrice = findViewById(R.id.price);
        mRemove = findViewById(R.id.remove);
        mNumber = findViewById(R.id.number);
        mAdd = findViewById(R.id.add);
        mCard = findViewById(R.id.card);
        mOrder = findViewById(R.id.order);
        mBarTitle.setText("商品详情");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mIconLeft.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void intData() {
        mNumber.setText(String.valueOf(count));
        selectCommdoityData(c_id);
    }

    private void selectCommdoityData(String c_id) {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST+Config.findCommodity+c_id,"");
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(s);
                        commodityData = new Gson().fromJson(s, CommodityData.class);
                        if (commodityData.getCode()==200){
                            CommodityData.DataDTO data = commodityData.getData();
                            mTvContent.setText("\t\t"+data.getC_advertisement());
                            mPrice.setText(String.valueOf("￥"+data.getC_price()));
                            mTvName.setText(data.getC_name());
                            Glide.with(MyApplication.getContext()).load(Config.HOST+data.getC_uri()).into(mIv);
                        }else {
                            Toast.makeText(CommodityActivity.this, "暂无内容", Toast.LENGTH_SHORT).show();
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
        mAdd.setOnClickListener(v -> addNumber());
        mRemove.setOnClickListener(v -> removeNumber());
        mOrder.setOnClickListener(v -> {
            addOrder(commodityData.getData(),mPrice.getText().toString());
        });
        mCard.setOnClickListener(v -> addCard(c_id,mPrice.getText().toString()));
        mIconLeft.setOnClickListener(v -> finish());
    }

    private void addCard(String c_id, String price) {
        String s = price.replace("￥", "");
        Double dprice = Double.valueOf(s);
//        Toast.makeText(this, "c_id:"+c_id, Toast.LENGTH_SHORT).show();
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setPost(Config.HOST+Config.addOrder, Shape.getValue("token",""),"c_id",c_id,"c_price", String.valueOf(dprice),"count", String.valueOf(count));
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AddOrder addOrder = new Gson().fromJson(s, AddOrder.class);
                        String msg = addOrder.getMsg();
                        Integer code = addOrder.getCode();
                        if (code ==200){
                            Toast.makeText(CommodityActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        }
                        if (code==400){
                            Toast.makeText(CommodityActivity.this, "此商品已添加,请到购物车查看", Toast.LENGTH_SHORT).show();
                        }
                        if(code==400&& msg.equals("token验证失败")){
//                            Toast.makeText(CommodityActivity.this, "token验证失败", Toast.LENGTH_SHORT).show();
                            DigUtil.setDig(CommodityActivity.this, LoginActivity.class);
                        }
                    }

                });
            }
            @Override
            public void Error(String s) {
                System.out.println(s);
            }
        });
    }

    private void addOrder( CommodityData.DataDTO data, String price) {
        Map<String,Object> map=new HashMap<>();
        Map<String,Integer> numbers=new HashMap<>();
        Map<String,String> priceMap=new HashMap<>();
        numbers.put(data.getC_name(),count);
        List<Object> list=new ArrayList<>();
        Gson gson = new Gson();
        list.add(data);
        map.put("datas",list);
        String s = price.replace("￥", "");
        map.put(data.getC_name(),s);
        priceMap.put(data.getC_name(),s);
        String json = gson.toJson(map);
        Messg messg = EventBus.getDefault().removeStickyEvent(Messg.class);
        String replace = json.replace("c_uri", "img_uri");
        messg.setJson(replace);
        messg.setNumbermap(numbers);
        messg.setPriceMap(priceMap);
        EventBus.getDefault().postSticky(messg);
        startActivity(new Intent(MyApplication.getContext(), OrderActivity.class));
    }

    private void removeNumber() {
        count--;
        if (count<=1){
            count=1;
        }
        mNumber.setText(String.valueOf(count));
    }

    private void addNumber() {
        count++;
        if (count<=1){
            count=1;
        }
        mNumber.setText(String.valueOf(count));
    }

    @Override
    public void setMessg(Messg messg) {
        c_id=messg.getC_id();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commoditydata;
    }
}
