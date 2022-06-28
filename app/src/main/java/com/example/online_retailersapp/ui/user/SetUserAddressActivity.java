package com.example.online_retailersapp.ui.user;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseActivity;
import com.example.online_retailersapp.base.Messg;
import com.example.online_retailersapp.bean.AllUserAddress;
import com.example.online_retailersapp.bean.FindUserAddressByIdBean;
import com.example.online_retailersapp.bean.Register;
import com.example.online_retailersapp.ui.home.HomeActivity;
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

import java.util.List;

public class SetUserAddressActivity extends BaseActivity {
    private androidx.cardview.widget.CardView mToolbar;

    private android.widget.ImageView mIconLeft;
    private android.widget.TextView mBarTitle;
    private android.widget.ImageView mIconRight;
    private androidx.recyclerview.widget.RecyclerView mRv;
    private android.widget.ImageButton mIb;

    @Override
    protected void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mIconLeft = findViewById(R.id.icon_left);
        mBarTitle = findViewById(R.id.bar_title);
        mIconRight = findViewById(R.id.icon_right);
        mRv = findViewById(R.id.rv);
        mIb = findViewById(R.id.ib);
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mBarTitle.setText("收货信息管理");
        mIconLeft.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void intData() {
        selectUserAllReceiving();
    }

    private void selectUserAllReceiving() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST + Config.finAllUserAddress, Shape.getValue("token", ""));
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                AllUserAddress userAddress = new Gson().fromJson(s, AllUserAddress.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<AllUserAddress.DatasDTO> datas = userAddress.getDatas();
                                mRv.setLayoutManager(new LinearLayoutManager(SetUserAddressActivity.this));
                                mRv.setAdapter(new BaseRecyclerViewAdapter<AllUserAddress.DatasDTO>(datas, R.layout.address, datas.size()) {

                                    private TextView mTvMoren;
                                    private ImageView mIvEdit;
                                    private TextView mTvPhone;
                                    private TextView mTvName;
                                    private TextView mTvAddress;

                                    @Override
                                    protected void conView(MyViewHolder holder, AllUserAddress.DatasDTO datasDTO, int position) {
                                        mTvAddress = holder.getView(R.id.tv_address);
                                        mTvName = holder.getView(R.id.tv_name);
                                        mTvPhone = holder.getView(R.id.tv_phone);
                                        mIvEdit = holder.getView(R.id.iv_edit);
                                        mTvAddress.setText(datasDTO.getAddress());
                                        mTvMoren = holder.getView(R.id.tv_moren);
                                        mTvName.setText(datasDTO.getName());
                                        mTvPhone.setText(datasDTO.getPhone());
                                        if (datasDTO.getR_id()==Shape.getValue("rId",0)) {
                                            mTvMoren.setVisibility(View.VISIBLE);
                                        }else {
                                            mTvMoren.setVisibility(View.GONE);
                                        }
                                        mIvEdit.setOnClickListener(v -> editsumit(datasDTO.getR_id()));
                                        holder.itemView.setOnLongClickListener(v -> longsumit(v, datasDTO.getR_id()));
                                    }
                                });



                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });
    }

    private void editsumit(int r_id) {
        Messg messg = new Messg();
        messg.setR_id(r_id);
        EventBus.getDefault().postSticky(messg);
        startActivity(new Intent(SetUserAddressActivity.this, SetUserAddressDataActivity.class));

    }

    private boolean longsumit(View v, int r_id) {
        PopupMenu popupMenu = new PopupMenu(SetUserAddressActivity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_delete:
                        DigUtil.delete(SetUserAddressActivity.this);
                        DigUtil.setMyInterfaceDig(new MyInterfaceDig() {
                            @Override
                            public void OnYes() {
                                removeArrdess(r_id);
                            }
                        });
                        break;
                    case R.id.item_set:
                        setMorenArredess(r_id);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
        return true;
    }

    private void setMorenArredess(int r_id) {
//        获取用户默认收货信息
        getUserAdress(r_id);
        Shape.setValue("rId",r_id);
        ;
        selectUserAllReceiving();
    }

    private void getUserAdress(int r_id) {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setPost(Config.HOST+Config.findUserAddressById,Shape.getValue("token",""),"r_id", String.valueOf(r_id));
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                System.out.println("findUserAddressById:"+s);
                FindUserAddressByIdBean bean = new Gson().fromJson(s, FindUserAddressByIdBean.class);
                Integer code = bean.getCode();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code==200){
                            String toJson = new Gson().toJson(bean.getData());
                            Shape.setValue("receiving",toJson);
                            Toast.makeText(SetUserAddressActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SetUserAddressActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void Error(String s) {

            }
        });
    }

    private void removeArrdess(int r_id) {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setDelete(Config.HOST + Config.deleteAllUserAddress, Shape.getValue("token", ""),
                "r_id", String.valueOf(r_id));
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Register register = new Gson().fromJson(s, Register.class);
                        if (register.getCode() == 200) {
                            Toast.makeText(SetUserAddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            selectUserAllReceiving();
                        } else {
                            Toast.makeText(SetUserAddressActivity.this, register.getMsg(), Toast.LENGTH_SHORT).show();
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
        mIb.setOnClickListener(v -> sumit());
        mIconLeft.setOnClickListener(v -> sumitreunrn());
    }

    private void sumitreunrn() {
        startActivity(new Intent(SetUserAddressActivity.this, HomeActivity.class));
    }

    private void sumit() {
        Messg messg = new Messg();
        messg.setR_id(0);
        EventBus.getDefault().postSticky(messg);
        startActivity(new Intent(SetUserAddressActivity.this, SetUserAddressDataActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectUserAllReceiving();
        Toast.makeText(this, "selectUserAllReceiving()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SetUserAddressActivity.this, HomeActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receiving;
    }
}
