package com.example.online_retailersapp.ui.fragment;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseFragment;
import com.example.online_retailersapp.base.Messg;
import com.example.online_retailersapp.bean.Commoditys;
import com.example.online_retailersapp.ui.commodity.CommodityActivity;
import com.example.online_retailersapp.utils.BaseRecyclerViewAdapter;
import com.example.online_retailersapp.utils.MyApplication;
import com.example.online_retailersapp.utils.MyViewHolder;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
public class Fragment2 extends BaseFragment {
    private androidx.cardview.widget.CardView mToolbar;
    private ImageView mIconLeft;
    private TextView mBarTitle;
    private ImageView mIconRight;
    private RadioButton mRb1;
    private RadioButton mRb2;
    private RadioButton mRb3;
    private RadioButton mRb4;
    private RecyclerView mRv;
    private RadioGroup mRg;
    private String banner_type;
    private List<RadioButton> buttonList;
    @Override
    protected void initEvent() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = mRg.findViewById(checkedId);
                String s = radioButton.getText().toString();
                rBsumit(s);
            }
        });
        mRb1.setChecked(true);
        for (int i = 0; i < buttonList.size(); i++) {
            String s = buttonList.get(i).getText().toString();
            if (s.equals(banner_type)){
                rBsumit(s);
                buttonList.get(i).setChecked(true);
            }
        }
    }
    private void rBsumit(String s) {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST + Config.findAllCommodity + "?type=" + s, "");
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                getMyActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(s);
                        Commoditys commoditys = new Gson().fromJson(s, Commoditys.class);
                        List<Commoditys.DatasDTO> datas = commoditys.getDatas();
                        if (commoditys.getCode() == 200) {
                            System.out.println(commoditys);
                            mRv.setAdapter(new BaseRecyclerViewAdapter<Commoditys.DatasDTO>(datas, R.layout.commity2, datas.size()) {

                                private TextView mPrice;
                                private TextView mName;
                                private ImageView mIv;
                                @Override
                                protected void conView(MyViewHolder holder, Commoditys.DatasDTO datasDTO, int position) {
                                    mIv =    holder.getView(R.id.iv);
                                    mName =  holder.getView(R.id.name);
                                    mPrice = holder.getView(R.id.price);
                                    mName.setText(datasDTO.getC_name());
                                    mPrice.setText("￥"+datasDTO.getC_price());
                                    Glide.with(getMyActivity()).load(Config.HOST+datasDTO.getC_uri()).into(mIv);
                                    holder.itemView.setOnClickListener(v -> sumit(datasDTO.getC_id()));
                                }
                            });
                            mRv.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
                        }
                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });
    }

    private void sumit(Integer c_id) {
        Messg messg=new Messg();
        messg.setC_id(String.valueOf(c_id));
        EventBus.getDefault().postSticky(messg);
        startActivity(new Intent(getMyActivity(), CommodityActivity.class));
    }

    @Override
    protected void initData() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();

    }

    @Override
    protected void initView() {

        mToolbar = getView().findViewById(R.id.toolbar);
        mIconLeft = getView().findViewById(R.id.icon_left);
        mBarTitle = getView().findViewById(R.id.bar_title);
        mIconRight = getView().findViewById(R.id.icon_right);
        mRb1 = getView().findViewById(R.id.rb1);
        mRb2 = getView().findViewById(R.id.rb2);
        mRb3 = getView().findViewById(R.id.rb3);
        mRb4 = getView().findViewById(R.id.rb4);
        mRv = getView().findViewById(R.id.rv);
        mBarTitle.setText("分类");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mRg = getView().findViewById(R.id.rg);
        buttonList=new ArrayList<>();
        buttonList.add(mRb1);
        buttonList.add(mRb2);
        buttonList.add(mRb3);
        buttonList.add(mRb4);
        if (bundle!=null){
            banner_type=bundle.getString("messg");
        }
        Toast.makeText(getMyActivity(), "messg:"+banner_type, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment2;
    }
}
