package com.example.online_retailersapp.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImgUtil {
    public static String getImgUriPz(Activity activity, Intent data, ImageView mIv){
        // String SD_PATH = Environment.getExternalStorageDirectory().getPath() + "/拍照上传示例/";
        // SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        // String fileName = format.format(new Date(System.currentTimeMillis())) + ".JPEG";
        // photoPath = SD_PATH + fileName;
        // File file = new File(photoPath);
        // if (!file.getParentFile().exists()) {
        // file.getParentFile().mkdirs();
        Bundle extras = data.getExtras();
        Bitmap bitmap= (Bitmap) extras.get("data");
        Glide.with(activity).load(bitmap).into(mIv);
        bitmap=Bitmap.createScaledBitmap(bitmap,150,150,true);
        String path = Environment.getExternalStorageDirectory() + "/Pictures/";
        File dirFile = new File(path);
        if(dirFile.exists()){
            dirFile.mkdir();
        }
        File file=new File(path+System.currentTimeMillis()+".jpg");
        try {
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }

    public static String getImgUri(Activity activity, Intent data, ImageView mIv, String s) {
        Uri uri = data.getData();
        String img_src = null;
        img_src = uri.getPath();//这是本机的图片路径
        ContentResolver cr = activity.getContentResolver();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
//            设置imgview
            Glide.with(activity).load(bitmap).into(mIv);
            String[] proj={s};
            CursorLoader loader = new CursorLoader(mIv.getContext(), uri, proj, null, null, null);
            Cursor cursor=loader.loadInBackground();
            if (cursor!=null){
                int indexOrThrow = cursor.getColumnIndexOrThrow(s);
                cursor.moveToFirst();
                img_src = cursor.getString(indexOrThrow);
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return img_src;
    }
}
