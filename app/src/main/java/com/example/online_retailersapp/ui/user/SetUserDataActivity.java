package com.example.online_retailersapp.ui.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseActivity;
import com.example.online_retailersapp.bean.Register;
import com.example.online_retailersapp.bean.UserData;
import com.example.online_retailersapp.utils.DigUtil;
import com.example.online_retailersapp.utils.ImgUtil;
import com.example.online_retailersapp.utils.Shape;
import com.example.online_retailersapp.utils.VerificationUtil;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.myinterface.ImgInterFace;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;

import java.io.File;

public class SetUserDataActivity extends BaseActivity {
    private androidx.cardview.widget.CardView mToolbar;
    private android.widget.ImageView mIconLeft;
    private android.widget.TextView mBarTitle;
    private android.widget.ImageView mIconRight;
    private android.widget.EditText mEtNickname;
    private android.widget.EditText mEtPhone;
    private android.widget.RadioButton mRbNan;
    private android.widget.RadioButton mRbNv;
    private android.widget.Button mBtnXiugai;
    private android.widget.ImageView mIv;
    private String imgUri;
    private boolean imgflag=false;
    @Override
    protected void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mIconLeft = findViewById(R.id.icon_left);
        mBarTitle = findViewById(R.id.bar_title);
        mIconRight = findViewById(R.id.icon_right);
        mEtNickname = findViewById(R.id.et_nickname);
        mEtPhone = findViewById(R.id.et_phone);
        mRbNan = findViewById(R.id.rb_nan);
        mRbNv = findViewById(R.id.rb_nv);
        mBtnXiugai = findViewById(R.id.btn_xiugai);
        mBarTitle.setText("个人信息");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mIconLeft.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        mIv = findViewById(R.id.iv);
    }

    @Override
    protected void intData() {
        selectUserData();
    }

    private void selectUserData() {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setGet(Config.HOST+Config.findUserData, Shape.getValue("token",""));
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("userdata:"+s);
                        UserData userData = new Gson().fromJson(s, UserData.class);
                        UserData.DataDTO data = userData.getData();
                        Integer sex = data.getSex();
                        if (sex==0){
                            mRbNan.setChecked(true);
                        }else {
                            mRbNv.setChecked(true);
                        }
                        mEtNickname.setText(data.getNickname());
                        mEtPhone.setText(data.getUserphone());
                        mIv.setScaleType(ImageView.ScaleType.FIT_XY);

//                        String userimg_uri = data.getUserimg_uri();
//                        String x = userimg_uri.substring(userimg_uri.indexOf("X"), userimg_uri.length());
                        Glide.with(SetUserDataActivity.this).load(Config.HOST+data.getUserimg_uri())
                                .into(mIv);
                        imgUri= data.getUserimg_uri();
                    }
                });
            }

            @Override
            public void Error(String s) {

            }
        });
    }

    private void sumitUpdateUserData() {
        Toast.makeText(this, "imgflag:"+imgflag, Toast.LENGTH_SHORT).show();
        String phone = mEtPhone.getText().toString();

        Integer sex=mRbNan.isChecked()?0:1;
        boolean phone1 = VerificationUtil.phone(phone);
        if (phone1){
            OkhttpUtil okhttpUtil=new OkhttpUtil();
            if (imgflag){
                //            设置了图片，更新图片
                //       发送请求
                okhttpUtil.setPost(Config.HOST+Config.setUserData,Shape.getValue("token",""),new File(imgUri),
                        "userphone", phone,"sex", String.valueOf(sex),"nickname"
                        ,mEtNickname.getText().toString());
                okhttpUtil.setoKhttp_code(new OkHttp_code() {
                    @Override
                    public void getString(String s) {
                        Register r = new Gson().fromJson(s, Register.class);
                        Integer code = r.getCode();
                        if (code==200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SetUserDataActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    imgflag=false;
                                    selectUserData();
                                }
                            });

                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SetUserDataActivity.this, r.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void Error(String s) {

                    }
                });
            }
            else {
                //                只更新数据不更新图片
                okhttpUtil.setPut(Config.HOST+Config.setUserDataNoImg,Shape.getValue("token",""),
                        "nickname",mEtNickname.getText().toString(),"phone",phone,"sex", String.valueOf(sex));
                okhttpUtil.setoKhttp_code(new OkHttp_code() {
                    @Override
                    public void getString(String s) {
                        Register register = new Gson().fromJson(s, Register.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Integer code = register.getCode();
                                if (register.getMsg().contains("验证失败")){
                                    DigUtil.setDig(SetUserDataActivity.this,LoginActivity.class);
                                }
                                if (code==200){
                                    Toast.makeText(SetUserDataActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    imgflag=false;
                                }else {
                                    Toast.makeText(SetUserDataActivity.this, register.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void Error(String s) {

                    }
                });
            }

        }else {
            Toast.makeText(this, "手机号格式有误，请重新输入", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void initEvent() {
        mIv.setOnClickListener(v -> sumit());
        mIconLeft.setOnClickListener(v -> finish());
        mBtnXiugai.setOnClickListener(v -> sumitUpdateUserData());
    }

    private void sumit() {
        AlertDialog.Builder builder=new AlertDialog.Builder(SetUserDataActivity.this);
        builder.setTitle("设置图片")
                .setNegativeButton("相机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JurisdictionPaiZhao();
                    }
                })
                .setPositiveButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JurisdictionXiangce();
                    }
                })
                .create()
                .show();
        setImgInterFace(new ImgInterFace() {
            @Override
            public void onImgPaizhaoYes() {
                setPaizhao();
            }

            @Override
            public void onActivityResult(Intent data, int i) {
                if (i==500) {
                    imgUri = ImgUtil.getImgUriPz(SetUserDataActivity.this, data, mIv);
                    Toast.makeText(SetUserDataActivity.this, "imgUri:"+imgUri, Toast.LENGTH_SHORT).show();
                    imgflag=true;
                }else if (i==501){
                    imgUri = ImgUtil.getImgUri(SetUserDataActivity.this, data, mIv, MediaStore.Images.Media.DATA);
                    Toast.makeText(SetUserDataActivity.this, "img_uri"+imgUri, Toast.LENGTH_SHORT).show();
                    imgflag=true;
                }
            }

            @Override
            public void onImgXiangceYes() {
                setXiangce();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userdataactivity;
    }
}
