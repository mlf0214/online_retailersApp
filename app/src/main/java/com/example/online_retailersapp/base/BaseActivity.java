package com.example.online_retailersapp.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.online_retailersapp.utils.ActivityMessg;
import com.example.online_retailersapp.utils.myinterface.ImgInterFace;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends FragmentActivity {
    private ImgInterFace imgInterFace;
    public int activityResult_paizhao=500;
    public int activityResult_xingce=501;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ActivityMessg.addActivity(this);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        initView();
        intData();
        initEvent();
    }
    protected abstract void initView();
    protected abstract void intData();
    protected abstract void initEvent();
    protected abstract int getLayoutId();
    public void setImgInterFace(ImgInterFace i){
        imgInterFace=i;
    }
    public void JurisdictionPaiZhao(){
        if (ContextCompat.checkSelfPermission(this,"android.permission.CAMERA")!= PackageManager.PERMISSION_GRANTED){
        //            权限未授予，需要申请权限
            ActivityCompat.requestPermissions(this,new String[]{"android.permission.CAMERA","android.permission.READ_EXTERNAL_STORAGE"},200);
        }else {
            setPaizhao();
        }
    }

    public void setXiangce() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, activityResult_xingce);
    }

    public void setPaizhao() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,activityResult_paizhao);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==200){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                申请成功
                imgInterFace.onImgPaizhaoYes();
            }else {
                Toast.makeText(this,"拒绝授权，程序销毁！",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if (requestCode==300){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                申请成功
                imgInterFace.onImgXiangceYes();
            }else {
                Toast.makeText(this,"拒绝授权，程序销毁！",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResult_paizhao==requestCode&&resultCode==RESULT_OK){
            imgInterFace.onActivityResult(data,activityResult_paizhao);
        }else if (activityResult_xingce==requestCode&&resultCode==RESULT_OK){
            imgInterFace.onActivityResult(data,activityResult_xingce);
        }
    }

    public void JurisdictionXiangce(){
        if (ContextCompat.checkSelfPermission(this,"android.permission.WRITE_EXTERNAL_STORAGE")!= PackageManager.PERMISSION_GRANTED){
//            //            权限未授予，需要申请权限
            ActivityCompat.requestPermissions(this,new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"},300);
        }else {
            setXiangce();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void setMessg(Messg messg){}

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}