package com.example.online_retailersapp.utils.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtil {
    private OkHttpClient okHttpClient;
    private JSONObject jsonObject;
    private OkHttp_code oKhttp_code;
    public OkhttpUtil() {
        okHttpClient=new OkHttpClient.Builder()
                .callTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        jsonObject=new JSONObject();
    }
    public void setoKhttp_code(OkHttp_code oKhttp_code){
        this.oKhttp_code=oKhttp_code;
    }
    public void setGet(String url,String token){
        Request request=null;
        if (token.equals("")){
            request=new Request.Builder()
                    .url(url)
                    .get().build();
        }else {
            request=new Request.Builder()
                    .url(url)
                    .addHeader("token",token)
                    .get().build();
        }
        Call call=okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String s = call.toString();
                    oKhttp_code.Error(s);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    oKhttp_code.getString(string);
                }
            });
    }
    public void setPut(String url,String token,String... strings){
        Request request=null;
        for (int i = 0; i < strings.length; i++) {
            if (i%2==0){
                try {
                    jsonObject.put(strings[i],strings[i+1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonObject.toString());
        if (token.equals("")){
            request=new Request.Builder()
                    .put(requestBody)
                    .url(url)
                    .build();
        }else {
            request=new Request.Builder()
                    .put(requestBody)
                    .url(url)
                    .addHeader("token",token)
                    .build();
        }
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String s = call.toString();
                oKhttp_code.Error(s);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                oKhttp_code.getString(string);
            }
        });
    }
    public void setPost(String url,String token,File file,String... strings){
        Request request=null;
        MultipartBody.Builder requestBody=new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (file!=null){
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            requestBody.addFormDataPart("file","img",body);
        }
        Map<String,String> map=new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            if (i%2==0){
                map.put(strings[i],strings[i+1]);
            }
        }
        if (map!=null){
            for (String key:
                    map.keySet()) {
                requestBody.addFormDataPart(key,map.get(key));
            }
        }
        if (token.equals("")){
            request=new Request.Builder()
                    .post(requestBody.build())
                    .url(url)
                    .build();
        }else {
            request=new Request.Builder()
                    .post(requestBody.build())
                    .addHeader("token",token)
                    .url(url)
                    .build();
        }
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String s = call.toString();
                oKhttp_code.Error(s);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                oKhttp_code.getString(string);
            }
        });


    }
    public void setPost(String url,String token,String... strings){
        Request request=null;
        RequestBody requestBody=null;
        for (int i = 0; i < strings.length; i++) {
            if (i%2==0){
                try {
                    jsonObject.put(strings[i],strings[i+1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonObject.toString());
        if (token.equals("")){
            request=new Request.Builder()
                    .post(requestBody)
                    .url(url)
                    .build();
        }else {
            request=new Request.Builder()
                    .post(requestBody)
                    .addHeader("token",token)
                    .url(url)
                    .build();
        }
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String s = call.toString();
                oKhttp_code.Error(s);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                oKhttp_code.getString(string);
            }
        });


    }
    public void uploadImage(String url, File file){
        RequestBody requestBody=null;
        Request request=null;
        RequestBody fileBody=null;
        try {
            fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            requestBody=new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file","img",fileBody)
                    .build();

        }catch (Exception e){
            e.printStackTrace();
        }
        request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                oKhttp_code.Error(e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                oKhttp_code.getString(response.body().string());
            }
        });

    }
    public void setDelete(String url,String token,String... strings){
        Request request=null;
        RequestBody requestBody=null;
        for (int i = 0; i < strings.length; i++) {
            if (i%2==0){
                try {
                    jsonObject.put(strings[i],strings[i+1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonObject.toString());
        if (token.equals("")){
            request=new Request.Builder()
                    .delete(requestBody)
                    .url(url)
                    .build();
        }else {
            request=new Request.Builder()
                    .delete(requestBody)
                    .addHeader("token",token)
                    .url(url)
                    .build();
        }
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String s = call.toString();
                oKhttp_code.Error(s);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                oKhttp_code.getString(string);
            }
        });
    }
}
