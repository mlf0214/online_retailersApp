package com.example.online_retailersapp.ui.user;

import android.content.Intent;
import android.widget.Toast;

import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseActivity;
import com.example.online_retailersapp.base.Messg;
import com.example.online_retailersapp.bean.Register;
import com.example.online_retailersapp.utils.DigUtil;
import com.example.online_retailersapp.utils.Shape;
import com.example.online_retailersapp.utils.VerificationUtil;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

public class SetUserAddressDataActivity extends BaseActivity {
    private androidx.cardview.widget.CardView mToolbar;
    private android.widget.ImageView mIconLeft;
    private android.widget.TextView mBarTitle;
    private android.widget.ImageView mIconRight;
    private android.widget.TextView mSetardess;
    private android.widget.Button mBtn;
    private android.widget.EditText mEtName;
    private android.widget.EditText mEtPhone;
    private android.widget.TextView mTvSetardess;
    private android.widget.EditText mEtSetardess;
    private boolean flag=false;
    private String r_id;

    @Override
    protected void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mIconLeft = findViewById(R.id.icon_left);
        mBarTitle = findViewById(R.id.bar_title);
        mIconRight = findViewById(R.id.icon_right);
        mBtn = findViewById(R.id.btn);
        mEtName = findViewById(R.id.et_name);
        mEtPhone = findViewById(R.id.et_phone);
        mTvSetardess = findViewById(R.id.tv_setardess);
        mEtSetardess = findViewById(R.id.et_setardess);
        mBarTitle.setText("收货信息");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mIconLeft.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void intData() {
    }

    @Override
    protected void initEvent() {
        mTvSetardess.setOnClickListener(v -> setardess());
        mBtn.setOnClickListener(v -> sumit());
        mIconLeft.setOnClickListener(v -> sumitreturn());
    }

    private void sumitreturn() {
        startActivity(new Intent(SetUserAddressDataActivity.this,SetUserAddressActivity.class));
    }

    @Override
    public void setMessg(Messg messg) {
        if (String.valueOf(messg.getR_id()).length()>0&&messg.getR_id()!=0){
            r_id = String.valueOf(messg.getR_id());
            flag=true;
            Toast.makeText(this, messg.getR_id()+"", Toast.LENGTH_SHORT).show();
        }
    }

    private void sumit() {
        String phone = mEtPhone.getText().toString();
        String name = mEtName.getText().toString();
        String e = mTvSetardess.getText().toString();
        String s = mEtSetardess.getText().toString();
        if (name.equals("")|| phone.equals("")||
                e.equals("请选择城市")|| s.equals("")){
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
        }else {
            if (!VerificationUtil.phone(phone)){
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            }else {
                if (flag){
                    //                进行网络请求,更新用户信息
                    updateArdess(name,phone,e+"\n"+s,r_id);
                }else {
//                进行网路请求，添加用户地址信息
                    addArdess(name,phone,e+"\n"+s);
                }


            }
        }
    }

    private void addArdess(String name, String phone, String address) {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setPut(Config.HOST+Config.addUserAddress, Shape.getValue("token",""),
                "name",name,"phone",phone,"address",address);
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Register register = new Gson().fromJson(s, Register.class);
                        if (register.getCode()==200){
                            Toast.makeText(SetUserAddressDataActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SetUserAddressDataActivity.this,SetUserAddressActivity.class));
                        }if (register.getCode()==500){
                            Toast.makeText(SetUserAddressDataActivity.this, "手机号重复，无法添加", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });

    }
    private void updateArdess(String name,String phone,String address,String r_id){
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setPut(Config.HOST+Config.updateUserAddress,Shape.getValue("token",""),"r_id",r_id);
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                Register register = new Gson().fromJson(s, Register.class);
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Integer code = register.getCode();
                       if (code==200){
                           Toast.makeText(SetUserAddressDataActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                           Messg messg = EventBus.getDefault().getStickyEvent(Messg.class);
                           messg.setR_id(0);
                           EventBus.getDefault().postSticky(messg);
                           startActivity(new Intent(SetUserAddressDataActivity.this,SetUserAddressActivity.class));
                       }else {
                           Toast.makeText(SetUserAddressDataActivity.this, register.getMsg(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }

            @Override
            public void Error(String s) {

            }
        });

    }
    private void setardess() {
        DigUtil.showCityPicker(SetUserAddressDataActivity.this);
        DigUtil.getCityPickerView(mTvSetardess);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SetUserAddressDataActivity.this,SetUserAddressActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_useraddressdata;
    }
}
