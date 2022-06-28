package com.example.online_retailersapp.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseFragment;
import com.example.online_retailersapp.base.Messg;
import com.example.online_retailersapp.bean.BatchDeleteCards;
import com.example.online_retailersapp.bean.SelectAllCards;
import com.example.online_retailersapp.ui.home.HomeActivity;
import com.example.online_retailersapp.ui.order.OrderActivity;
import com.example.online_retailersapp.utils.BaseRecyclerViewAdapter;
import com.example.online_retailersapp.utils.DigUtil;
import com.example.online_retailersapp.utils.MyViewHolder;
import com.example.online_retailersapp.utils.Shape;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.myinterface.MyInterfaceDig;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Fragment3 extends BaseFragment {
    private androidx.cardview.widget.CardView mToolbar;

    private ImageView mIconLeft;
    private TextView mBarTitle;
    private ImageView mIconRight;
    private RecyclerView mRv;
    private TextView mTv;
    private List<Integer> list = new ArrayList<>();
    private Map<Integer, CheckBox> checkBoxMap = new HashMap<>();
    private Button mBtnRemove;
    private Button mBtnAddOrder;
    private List<SelectAllCards.DatasDTO> addOrderdatasDTOS = new ArrayList<>();
    private List<SelectAllCards.DatasDTO> removedatasDTOS = new ArrayList<>();
    private CheckBox mSellectAllcb;
    private List<SelectAllCards.DatasDTO> datas;
    @Override
    protected void initEvent() {
        mTv.setOnClickListener(v -> sumit());
        mSellectAllcb.setOnClickListener(v -> sumitSellCb(checkBoxMap));
        mBtnAddOrder.setOnClickListener(v -> sumitAddOrder(checkBoxMap, datas));
        mBtnRemove.setOnClickListener(v -> sumitRemoveCard(checkBoxMap, datas));
    }



    private void sumit() {
        HomeActivity myActivity = (HomeActivity) getMyActivity();
        myActivity.setTab(1,null);
    }

    private void sumitSellCb(Map<Integer, CheckBox> checkBoxMap) {
        if (mSellectAllcb.isChecked()){
            for (int i = 0; i < checkBoxMap.size(); i++) {
                checkBoxMap.get(i).setChecked(true);
            }
        }else {
            for (int i = 0; i < checkBoxMap.size(); i++) {
                checkBoxMap.get(i).setChecked(false);
            }
        }
    }

    @Override
    protected void initData() {
        selectAllOrdes();
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST + Config.selectAllCards, Shape.getValue("token", ""));
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                getMyActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(s);
                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });
    }

    private void selectAllOrdes() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST + Config.selectAllCards, Shape.getValue("token", ""));
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                System.out.println("f3:" + s);
                SelectAllCards selectAllCards = new Gson().fromJson(s, SelectAllCards.class);
                datas = selectAllCards.getDatas();
                getMyActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getMyActivity(), s, Toast.LENGTH_SHORT).show();
                        Integer code = selectAllCards.getCode();
                        if (code == 200) {
                            if (datas.size()==0){
                                mTv.setVisibility(View.VISIBLE);
                                mRv.setVisibility(View.GONE);

                            }else {
                                mTv.setVisibility(View.GONE);
                                mRv.setLayoutManager(new LinearLayoutManager(getMyActivity()));
                                BaseRecyclerViewAdapter<SelectAllCards.DatasDTO> adapter = new BaseRecyclerViewAdapter<SelectAllCards.DatasDTO>(datas, R.layout.card_item, datas.size()) {
                                    private TextView mTvPrice;
                                    private ImageView mIvAdd;
                                    private ImageView mIvRemove;
                                    private TextView mTvCount;
                                    private TextView mTvName;
                                    private ImageView mIv;
                                    private CheckBox mCb;
                                    @Override
                                    protected void conView(MyViewHolder holder, SelectAllCards.DatasDTO datasDTO, int position) {
                                        mCb = holder.getView(R.id.cb);
                                        mIv = holder.getView(R.id.iv);
                                        mTvName = holder.getView(R.id.tv_name);
                                        mTvCount = holder.getView(R.id.tv_count);
                                        mIvRemove = holder.getView(R.id.iv_remove);
                                        mTvPrice = holder.getView(R.id.tv_price);
                                        mIvAdd = holder.getView(R.id.iv_add);
                                        Glide.with(getMyActivity()).load(Config.HOST + datasDTO.getImg_uri()).into(mIv);
                                        mTvName.setText(datasDTO.getC_name());
                                        mTvPrice.setText("￥" + datasDTO.getC_price());
                                        mTvCount.setText(datasDTO.getC_count() + "");
                                        list.add(datasDTO.getC_count());
                                        checkBoxMap.put(position, mCb);
                                        mIvAdd.setOnClickListener(v -> sumitAdd(datasDTO, position, holder.getView(R.id.tv_count)));
                                        mIvRemove.setOnClickListener(v -> sumitRemove(datasDTO, position, holder.getView(R.id.tv_count)));
                                    }
                                };
                                adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Toast.makeText(getMyActivity(), position + "", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                mRv.setAdapter(adapter);
                            }

                        }
                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });
    }
    private void sumitRemoveCard(Map<Integer, CheckBox> checkBoxMap, List<SelectAllCards.DatasDTO> datas) {
        System.out.println("checkBoxMap.size()="+checkBoxMap.size());
        List<Integer> list=new ArrayList<>();
        for (int i = 0; i < checkBoxMap.size(); i++) {
            if (checkBoxMap.get(i).isChecked()) {
                SelectAllCards.DatasDTO datasDTO = datas.get(i);
                Integer c_id = datasDTO.getC_id();
                list.add(c_id);
                System.out.println("checkBox被选中了");
            }
        }
        if (list.size()!=0 &&checkBoxMap.size()!=0){
            DigUtil.delete(getMyActivity());
            DigUtil.setMyInterfaceDig(new MyInterfaceDig() {
                @Override
                public void OnYes() {
                    sumitRemoveDig(checkBoxMap,datas,list);
                }
            });
        }if (list.size()==0){
            Toast.makeText(getMyActivity(), "请选择购物车信息", Toast.LENGTH_SHORT).show();
        }if(checkBoxMap.size()==0){
            Toast.makeText(getMyActivity(), "没有可删除的东西了", Toast.LENGTH_SHORT).show();
        }
    }
    private void sumitRemoveDig(Map<Integer, CheckBox> checkBoxMap, List<SelectAllCards.DatasDTO> datas, List<Integer> list) {
        System.out.println("integerSet:"+list);

//        进行网络请求
        if (list.size()!=0){
            removeCardOkhp(list);
            list.clear();
            checkBoxMap.clear();
        }
    }

    private void removeCardOkhp(List<Integer> integerSet) {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        String s = String.valueOf(integerSet);
        System.out.println("s:"+s);
        okhttpUtil.setPost(Config.HOST+Config.batchDeleteCards,Shape.getValue("token",""),"Ids", s);
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                BatchDeleteCards batchDeleteCards = new Gson().fromJson(s, BatchDeleteCards.class);
                getMyActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getMyActivity(), s, Toast.LENGTH_SHORT).show();
                        selectAllOrdes(list);
                        System.out.println("integerSet.size="+integerSet.size());
                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });
    }
    private void selectAllOrdes(List<Integer> list) {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST + Config.selectAllCards, Shape.getValue("token", ""));
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                System.out.println("f3:" + s);
                SelectAllCards selectAllCards = new Gson().fromJson(s, SelectAllCards.class);
                List<SelectAllCards.DatasDTO> datas = selectAllCards.getDatas();
                getMyActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = selectAllCards.getCode();
                        if (code == 200) {
                            if (datas.size()==0){
                                mTv.setVisibility(View.VISIBLE);
                                mRv.setVisibility(View.GONE);
                            }else {
                                mTv.setVisibility(View.GONE);
                                mRv.setLayoutManager(new LinearLayoutManager(getMyActivity()));
                                BaseRecyclerViewAdapter<SelectAllCards.DatasDTO> adapter = new BaseRecyclerViewAdapter<SelectAllCards.DatasDTO>(datas, R.layout.card_item, datas.size()) {
                                    private TextView mTvPrice;
                                    private ImageView mIvAdd;
                                    private ImageView mIvRemove;
                                    private TextView mTvCount;
                                    private TextView mTvName;
                                    private ImageView mIv;
                                    private CheckBox mCb;
                                    @Override
                                    protected void conView(MyViewHolder holder, SelectAllCards.DatasDTO datasDTO, int position) {
                                        mCb = holder.getView(R.id.cb);
                                        mIv = holder.getView(R.id.iv);
                                        mTvName = holder.getView(R.id.tv_name);
                                        mTvCount = holder.getView(R.id.tv_count);
                                        mIvRemove = holder.getView(R.id.iv_remove);
                                        mTvPrice = holder.getView(R.id.tv_price);
                                        mIvAdd = holder.getView(R.id.iv_add);
                                        Glide.with(getMyActivity()).load(Config.HOST + datasDTO.getImg_uri()).into(mIv);
                                        mTvName.setText(datasDTO.getC_name());
                                        mTvPrice.setText("￥" + datasDTO.getC_price());
                                        mTvCount.setText(list.get(position)+"");
                                        list.add(datasDTO.getC_count());
                                        checkBoxMap.put(position, mCb);
                                        mIvAdd.setOnClickListener(v -> sumitAdd(datasDTO, position, holder.getView(R.id.tv_count)));
                                        mIvRemove.setOnClickListener(v -> sumitRemove(datasDTO, position, holder.getView(R.id.tv_count)));
                                    }
                                };
                                adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
//                                        Toast.makeText(getMyActivity(), position + "", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                mRv.setAdapter(adapter);
                            }
                        }
                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });
    }
    private void sumitAddOrder(Map<Integer, CheckBox> checkBoxMap, List<SelectAllCards.DatasDTO> datas) {
        if (checkBoxMap.size()!=0){
            float price = 0;
            List<Object> list1 = new ArrayList<>();
            Map<String, Integer> numbers = new HashMap<>();
            Map<String,String> priceMap=new HashMap<>();
            for (int i = 0; i < checkBoxMap.size(); i++) {
                if (checkBoxMap.get(i).isChecked()) {
                    SelectAllCards.DatasDTO datasDTO = datas.get(i);
//                String img_uri = datasDTO.getImg_uri();
                    String c_price = datasDTO.getC_price();
                    Integer integer = list.get(i);

                    list1.add(datasDTO);
                    //商品选择数量
                    numbers.put(datasDTO.getC_name(), integer);
                    //商品价格
                    priceMap.put(datasDTO.getC_name(), c_price);
                }
            }
            if (list1.size()!=0){
                HashMap<String, Object> map = new HashMap<>();
                map.put("datas", list1);
                map.put("allprice", price);
                Gson gson = new Gson();
                String json = gson.toJson(map);
//                Toast.makeText(getMyActivity(), price + "", Toast.LENGTH_SHORT).show();
                Messg messg = new Messg();
                messg.setJson(json);
                messg.setNumbermap(numbers);
                messg.setPriceMap(priceMap);
                EventBus.getDefault().postSticky(messg);
                startActivity(new Intent(getMyActivity(), OrderActivity.class));
            }else {
                Toast.makeText(getMyActivity(), "请选择购物车商品", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getMyActivity(), "没有商品了", Toast.LENGTH_SHORT).show();
        }
    }
    private void sumitRemove(SelectAllCards.DatasDTO datasDTO, int position, TextView view) {
        Integer integer = list.get(position);
        integer--;
        list.set(position, integer);
        view.setText(integer + "");
    }
    private void sumitAdd(Object obj, int position, TextView view) {
        Integer integer = list.get(position);
        integer++;
        list.set(position, integer);
        view.setText(integer + "");
    }
    @Override
    protected void initView() {
        mToolbar = getView().findViewById(R.id.toolbar);
        mIconLeft = getView().findViewById(R.id.icon_left);
        mBarTitle = getView().findViewById(R.id.bar_title);
        mIconRight = getView().findViewById(R.id.icon_right);
        mRv = getView().findViewById(R.id.rv);
        mBarTitle.setText("购物车");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mTv = getView().findViewById(R.id.tv);
        mBtnRemove = getView().findViewById(R.id.btn_remove);
        mBtnAddOrder = getView().findViewById(R.id.brn_addOrder);
        mSellectAllcb = getView().findViewById(R.id.allcb);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment3;
    }

    @Override
    public void onResume() {
        super.onResume();
        selectAllOrdes();
    }
}
