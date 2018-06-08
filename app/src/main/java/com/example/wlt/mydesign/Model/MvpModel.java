package com.example.wlt.mydesign.Model;

import android.os.Handler;
import android.util.Log;

import com.example.wlt.mydesign.Presenter.Callback;
import com.example.wlt.mydesign.base.net.GoodsService;
import com.example.wlt.mydesign.base.util.Internet;
import com.example.wlt.mydesign.base.util.YeeJSONUtil;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by 25122 on 2018/6/7.
 */

public class MvpModel {

    /**
     * 获取网络接口数据
     * @param param 请求参数
     * @param callback 数据回调接口
     */
    public static void getNetData(final String param, final Callback<String> callback){

        // 利用postDelayed方法模拟网络请求数据的耗时操作
        Internet.getInstance()
                .create(GoodsService.class)
                .GetClass(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String s = responseBody.string();
                        JSONObject js = new JSONObject(s);
                        String state = YeeJSONUtil.getString(js,"state");
                        String message = YeeJSONUtil.getString(js,"message");
                        if(state.equals("success")){
                            callback.onSuccess(YeeJSONUtil.getString(js,"data"));
                        }else {
                            callback.onFailure(message);
                        }
                        callback.onComplete();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onError();
                    }
                });
    }
}
