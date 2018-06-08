package com.example.wlt.mydesign;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 25122 on 2018/6/7.
 */

public class App extends Application {
    private static App instance;
    public static final String GET_AES ="WANGLUTAOAIQRB22";
    //    public static final String Uri = "http://172.27.35.1/";
    public static final String Url = "https://wxapi.dshaitao.com";
//    public static final String Uri = "http://qrbsg.free.ngrok.cc/";

    public void onCreate() {
        super.onCreate();
        instance = this;
//        Stetho.initializeWithDefaults(this);
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());
    }
    public static App getInstance(){
        // 因为我们程序运行后，Application是首先初始化的，如果在这里不用判断instance是否为空
        return instance;
    }
}
