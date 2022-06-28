package com.example.online_retailersapp.ui.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseActivity;
import com.example.online_retailersapp.bean.Login;
import com.example.online_retailersapp.ui.home.HomeActivity;
import com.example.online_retailersapp.utils.ActivityMessg;
import com.example.online_retailersapp.utils.Shape;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity {
    private androidx.cardview.widget.CardView mToolbar;
    private android.widget.ImageView mIconLeft;
    private android.widget.TextView mBarTitle;
    private android.widget.ImageView mIconRight;
    private android.widget.EditText mEtUsername;
    private android.widget.EditText mEtPassword;
    private android.widget.Button mBtnLogin;
    private android.widget.Button mBtnZhuce;
    private android.widget.CheckBox mCkSavePsd;
    private android.widget.TextView mTvSetHost;

    @Override
    protected void initView() {

        mToolbar = findViewById(R.id.toolbar);
        mIconLeft = findViewById(R.id.icon_left);
        mBarTitle = findViewById(R.id.bar_title);
        mIconRight = findViewById(R.id.icon_right);
        mEtUsername = findViewById(R.id.et_username);
        mEtPassword = findViewById(R.id.et_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnZhuce = findViewById(R.id.btn_zhuce);
        mBarTitle.setText("登录");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mCkSavePsd = findViewById(R.id.ck_savePsd);
        mTvSetHost = findViewById(R.id.tv_setHost);
    }
    @Override
    protected void intData() {
        mEtPassword.setText(Shape.getValue("password",""));
        mEtUsername.setText(Shape.getValue("username",""));
    }
    @Override
    protected void initEvent() {
        mTvSetHost.setOnClickListener(v -> sumitsethost());
        mBtnZhuce.setOnClickListener(view -> zhuce());
        mBtnLogin.setOnClickListener(view -> sumit(mEtUsername.getText().toString(),
                mEtPassword.getText().toString()));
    }

    private void sumitsethost() {
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.host_edview, null, false);
        builder.setView(view);
        EditText editText=view.findViewById(R.id.et_host);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String host = editText.getText().toString();
                Shape.setValue("host",host);
            }
        });
        builder.setTitle("设置IP端口号").create().show();
    }

    private void sumit(String username, String password) {
        if (Shape.getValue("host","")==null||Shape.getValue("host","").equals("")||Shape.getValue("host","").equals("null")){
            Toast.makeText(LoginActivity.this, "请设置正确的IP端口号", Toast.LENGTH_SHORT).show();
        }else {
            OkhttpUtil okhttpUtil=new OkhttpUtil();
            okhttpUtil.setPost(Config.HOST+Config.login,"","username",username,"password",password);
            okhttpUtil.setoKhttp_code(new OkHttp_code() {
                @Override
                public void getString(String s) {
                    Login login = new Gson().fromJson(s, Login.class);
                    Integer code = login.getCode();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                            if (code==200){
                                String token = login.getToken();
                                Shape.setValue("token",token);
//                            Shape.setValue("r_id",0);
                                Shape.setValue("receiving","");
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                if (mCkSavePsd.isChecked()){
                                    Shape.setValue("username",username);
                                    Shape.setValue("password",password);
                                }else {
                                    Shape.setValue("username","");
                                    Shape.setValue("password","");
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                @Override
                public void Error(String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });
                    System.out.println(s);
                }
            });
        }

    }

    private void zhuce() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onBackPressed() {
        ActivityMessg.exit();
        super.onBackPressed();
    }
}
