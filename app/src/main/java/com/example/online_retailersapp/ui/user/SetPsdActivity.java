package com.example.online_retailersapp.ui.user;

import android.content.Intent;
import android.widget.Toast;

import com.example.online_retailersapp.R;
import com.example.online_retailersapp.base.BaseActivity;
import com.example.online_retailersapp.bean.Register;
import com.example.online_retailersapp.utils.Shape;
import com.example.online_retailersapp.utils.VerificationUtil;
import com.example.online_retailersapp.utils.conig.Config;
import com.example.online_retailersapp.utils.network.OkHttp_code;
import com.example.online_retailersapp.utils.network.OkhttpUtil;
import com.google.gson.Gson;

public class SetPsdActivity extends BaseActivity {
    private androidx.cardview.widget.CardView mToolbar;
    private android.widget.ImageView mIconLeft;
    private android.widget.TextView mBarTitle;
    private android.widget.ImageView mIconRight;
    private android.widget.EditText mEtOldPsd;
    private android.widget.EditText mEtNewOld;
    private android.widget.EditText mEtNewsOld;
    private android.widget.Button mBrnSetPsd;

    @Override
    protected void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mIconLeft = findViewById(R.id.icon_left);
        mBarTitle = findViewById(R.id.bar_title);
        mIconRight = findViewById(R.id.icon_right);
        mEtOldPsd = findViewById(R.id.et_oldPsd);
        mEtNewOld = findViewById(R.id.et_newOld);
        mEtNewsOld = findViewById(R.id.et_newsOld);
        mBrnSetPsd = findViewById(R.id.brn_setPsd);
        mBarTitle.setText("修改密码");
        mBarTitle.setTextColor(getResources().getColor(R.color.white));
        mIconLeft.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void intData() {

    }

    @Override
    protected void initEvent() {
        mIconLeft.setOnClickListener(v -> finish());
        mBrnSetPsd.setOnClickListener(v -> sumitsetPsd());
    }

    private void sumitsetPsd() {
        String newsPsd = mEtNewOld.getText().toString();
        String newsPsd1 = mEtNewsOld.getText().toString();
        if (newsPsd.equals(newsPsd1)){
            if (!VerificationUtil.password(newsPsd)) {
                setPsdOkht(newsPsd, mEtOldPsd.getText().toString());
            }else {
                Toast.makeText(this, "密码格式错误，请重新输入", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void setPsdOkht(String newsPsd, String oldPsd) {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setPut(Config.HOST+Config.setPassword, Shape.getValue("token",""),"oldpassword",oldPsd,
                "newpassword",newsPsd);
        okhttpUtil.setoKhttp_code(new OkHttp_code() {
            @Override
            public void getString(String s) {
                System.out.println("setPassword:"+s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Register register = new Gson().fromJson(s, Register.class);
                        Integer code = register.getCode();
                        if (code==200){
                            Toast.makeText(SetPsdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SetPsdActivity.this,LoginActivity.class));
                        }else {
                            Toast.makeText(SetPsdActivity.this, register.getMsg(), Toast.LENGTH_SHORT).show();
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
    protected int getLayoutId() {
        return R.layout.activity_setpsdactivity;
    }
}
