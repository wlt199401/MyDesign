package com.example.wlt.mydesign.base.util;

import com.example.wlt.mydesign.App;
import com.example.wlt.mydesign.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 25122 on 2018/6/7.
 */

public class Internet {
    private static Internet internet;
    private Retrofit retrofit;

    public Internet() {
        retrofit = createRetrofit();
    }

    public static Internet getInstance(){
        if(internet==null){
            synchronized (Internet.class){
                if(internet==null){
                    internet = new Internet();
                }
            }
        }
        return internet;
    }
    //创建相应的服务接口
    public <T> T create(Class<T> service){
        if(service==null){
            throw new NullPointerException("service is null");
        }
        return retrofit.create(service);
    }

    private Retrofit createRetrofit(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS);
        return new Retrofit.Builder()
                .baseUrl(App.Url)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
