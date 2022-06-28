package com.example.online_retailersapp.ui.fragment;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseFragment;
import com.example.online_retailersapp.base.Messg;
import com.example.online_retailersapp.bean.Recommend;
import com.example.online_retailersapp.ui.commodity.CommodityActivity;
import com.example.online_retailersapp.ui.home.HomeActivity;
import com.example.online_retailersapp.utils.BaseRecyclerViewAdapter;
import com.example.online_retailersapp.utils.MyApplication;
import com.example.online_retailersapp.utils.MyViewHolder;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class Fragment1 extends BaseFragment {

    private androidx.cardview.widget.CardView mToolbar;
    private ImageView mIconLeft;
    private TextView mBarTitle;
    private ImageView mIconRight;
    private Banner mBanner;
    private RecyclerView mRv;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        setBanner();
        setRecommend();

    }

    private void setRecommend() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST + Config.findRecommend, "");
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                Recommend recommend = new Gson().fromJson(s, Recommend.class);
                List<Recommend.DatasDTO> datas = recommend.getDatas();
                System.out.println("Recommend:"+s);
                getMyActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (recommend.getCode()==200){
                            StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//                            layout.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                            mRv.setLayoutManager(layout);
                            mRv.setAdapter(new BaseRecyclerViewAdapter<Recommend.DatasDTO>(datas, R.layout.commity, datas.size()) {
                                private TextView mTvPrice;
                                private TextView mTvName;
                                private ImageView mIv;
                                @Override
                                protected void conView(MyViewHolder holder, Recommend.DatasDTO datasDTO, int position) {
                                    mIv =holder.getView(R.id.iv);
                                    mTvName =  holder.getView(R.id.tv_name);
                                    mTvPrice = holder.getView(R.id.tv_price);
                                    Glide.with(getMyActivity()).load(Config.HOST+datasDTO.getC_uri()).into(mIv);
                                    mTvName.setText(datasDTO.getC_name());
                                    Double c_price = datasDTO.getC_price();
                                    mTvPrice.setText("￥"+c_price);
                                    holder.itemView.setOnClickListener(v -> rvsumit(datasDTO.getC_id()));
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

    private void rvsumit(Integer c_id) {
        Messg messg=new Messg();
        messg.setC_id(String.valueOf(c_id));
        EventBus.getDefault().postSticky(messg);
        startActivity(new Intent(MyApplication.getContext(), CommodityActivity.class));
    }

    private void setBanner() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST + Config.findBanner, "");
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                getMyActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(s);
                        com.example.online_retailersapp.bean.Banner banner = new Gson().fromJson(s, com.example.online_retailersapp.bean.Banner.class);
                        if (banner.getCode() == 200) {
                            List<com.example.online_retailersapp.bean.Banner.DatasDTO> datas = banner.getDatas();
                            mBanner.setAdapter(new BannerImageAdapter<com.example.online_retailersapp.bean.Banner.DatasDTO>(datas) {
                                @Override
                                public void onBindView(BannerImageHolder bannerImageHolder, com.example.online_retailersapp.bean.Banner.DatasDTO datasDTO, int i, int i1) {
                                    Glide.with(getMyActivity()).load(Config.HOST + datasDTO.getB_uri()).into(bannerImageHolder.imageView);
                                }
                            });
                            mBanner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(Object o, int i) {
                                    String c_type = datas.get(i).getC_type();
                                    Toast.makeText(getMyActivity(), c_type, Toast.LENGTH_SHORT).show();
                                    HomeActivity homeActivity = (HomeActivity) getMyActivity();
                                    homeActivity.setTab(1,c_type);
                                    Messg messg = new Messg();
                                    messg.setBanner_type(c_type);
                                    EventBus.getDefault().postSticky(messg);
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void Error(String s) {
                getMyActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getMyActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void initView() {
        mToolbar = getView().findViewById(R.id.toolbar);
        mIconLeft = getView().findViewById(R.id.icon_left);
        mBarTitle = getView().findViewById(R.id.bar_title);
        mIconRight = getView().findViewById(R.id.icon_right);
        mBanner = getView().findViewById(R.id.banner);
        mRv = getView().findViewById(R.id.rv);
        mBarTitle.setText("首页");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment1;
    }
}
