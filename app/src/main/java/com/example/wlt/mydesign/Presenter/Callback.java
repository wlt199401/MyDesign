package com.example.wlt.mydesign.Presenter;

/**
 * Created by 25122 on 2018/6/7.
 */

public interface Callback<T> {

    /**
     * 数据请求成功
     * @param data
     */
    void onSuccess(T data);

    /**
     * 返回失败数据
     * @param message
     */
    void onFailure(String message);

    /**
     * 请求失败
     */
    void onError();

    /**
     * 请求结束
     */
    void onComplete();

}
