package com.example.wlt.mydesign.Views;

import android.content.Context;

/**
 * Created by 25122 on 2018/6/7.
 */

public interface IBaseView {

    /**
     * 显示正在加载view
     */
    void showLoading();

    /**
     * 关闭正在加载view
     */
    void hideLoading();

    /**
     * 显示提示
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示请求错误提示
     */
    void showErr();

    /**
     * 获取上下文
     * @return 上下文
     */
    Context getContext();

}
