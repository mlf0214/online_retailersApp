package com.example.online_retailersapp.ui.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseActivity;
import com.example.online_retailersapp.bean.Register;
import com.example.online_retailersapp.utils.ImgUtil;
import com.example.online_retailersapp.utils.VerificationUtil;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.myinterface.ImgInterFace;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;

import java.io.File;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "ZhuCeActivity";
    private androidx.cardview.widget.CardView mToolbar;
    private android.widget.ImageView mIconLeft;
    private android.widget.TextView mBarTitle;
    private android.widget.ImageView mIconRight;
    private android.widget.EditText mEtUsername;
    private android.widget.EditText mEtPassword;
    private android.widget.EditText mEtPhone;
    private android.widget.EditText mEtNickname;
    private android.widget.RadioButton mRbNan;
    private android.widget.RadioButton mRbNv;
    private android.widget.Button mBtn;
    private android.widget.ImageView mIv;
    private String imgUri;
    private android.widget.EditText mEtPassword2;


    @Override
    protected void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mIconLeft = findViewById(R.id.icon_left);
        mBarTitle = findViewById(R.id.bar_title);
        mIconRight = findViewById(R.id.icon_right);
        mEtUsername = findViewById(R.id.et_username);
        mEtPassword = findViewById(R.id.et_password);
        mEtPhone = findViewById(R.id.et_phone);
        mEtNickname = findViewById(R.id.et_nickname);
        mRbNan = findViewById(R.id.rb_nan);
        mRbNv = findViewById(R.id.rb_nv);
        mBtn = findViewById(R.id.btn);
        mBarTitle.setText("注册");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mIconLeft.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        mIv = findViewById(R.id.iv);
        mIv.setSelected(true);
        mIv.setScaleType(ImageView.ScaleType.FIT_XY);
        mEtPassword2 = findViewById(R.id.et_password2);
    }
    @Override
    protected void intData() {

    }

    @Override
    protected void initEvent() {
        mIconLeft.setOnClickListener(v -> finish());
//        mBtn.setOnClickListener(v -> sumit(imgUri));
        mBtn.setOnClickListener(v ->  sumitZc(imgUri,mEtUsername.getText().toString()
                ,mEtPassword.getText().toString(),mEtNickname.getText().toString()
                ,mEtPhone.getText().toString().trim()));

        mIv.setOnClickListener(v -> imgsumit());

    }
    private void imgsumit() {
        AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("选择图片")
                        .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JurisdictionPaiZhao();
                            }
                        }).setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            JurisdictionXiangce();
                    }
                }).create().show();
        setImgInterFace(new ImgInterFace() {
            @Override
            public void onImgPaizhaoYes() {
               setPaizhao();
            }

            @Override
            public void onActivityResult(Intent data, int i) {
                if (i==500) {
                    imgUri = ImgUtil.getImgUriPz(RegisterActivity.this, data, mIv);
                    Toast.makeText(RegisterActivity.this, "imgUri:"+imgUri, Toast.LENGTH_SHORT).show();
                }else if (i==501){
                    imgUri = ImgUtil.getImgUri(RegisterActivity.this, data, mIv, MediaStore.Images.Media.DATA);
                    Toast.makeText(RegisterActivity.this, "img_uri"+imgUri, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onImgXiangceYes() {
                setXiangce();
            }
        });
    }

    private void sumitZc(String imgUri, String username, String password, String nickname, String phone) {
        System.out.println("imgUri:"+imgUri);
        if (imgUri!=null){
            if (VerificationUtil.password(password)){
                Toast.makeText(this, "密码格式不正确请重新输入", Toast.LENGTH_SHORT).show();

            }
            else  if (!password.equals(mEtPassword2.getText().toString())){
                Toast.makeText(this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
            }
            else if (!VerificationUtil.phone(phone)){
                Toast.makeText(this, "手机号格式不正确请重新输入", Toast.LENGTH_SHORT).show();
            }
            else if (VerificationUtil.username(username)){
                Toast.makeText(this, "用户名格式不正确请重新输入", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("hhh");
                String sex="";
                sex=mRbNan.isChecked()?"0":"1";
                OkhttpUtil okhttpUtil=new OkhttpUtil();
                okhttpUtil.setPost(Config.HOST+Config.register,"",new File(imgUri),"username",username,"password",password,"nickname",nickname
                        ,"userphone",phone,"sex",sex);
                okhttpUtil.setoKhttp_code(new OkHttp_code() {
                    @Override
                    public void getString(String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Register r = new Gson().fromJson(s, Register.class);
                                    Integer code = r.getCode();
                                    if (code!=null){
                                        if (code==200){
                                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                        }else {
                                            Toast.makeText(RegisterActivity.this, r.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }catch (Exception e){
                                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void Error(String s) {
                        Log.d(TAG,s);
                    }
                });
            }
        }else {
            Toast.makeText(this, "请插入图片", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }
}
