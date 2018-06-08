package com.example.wlt.mydesign.Presenter;

import com.example.wlt.mydesign.Model.MvpModel;
import com.example.wlt.mydesign.Views.MvpView;

/**
 * Created by 25122 on 2018/6/7.
 */

public class HomePresenter extends IBasePresenter<MvpView>{

    public void getData(String params){
        if(!isViewAttached()){
            return;
        }
        //显示正在加载的dialog
        getView().showLoading();

        //调用model请求数据
        MvpModel.getNetData(params, new Callback<String>() {
            @Override
            public void onSuccess(String data) {
                if(isViewAttached()){
                    getView().showData(data);
                }
            }

            @Override
            public void onFailure(String message) {
                //调用view接口提示失败信息
                if(isViewAttached()){
                    getView().showToast(message);
                }
            }

            @Override
            public void onError() {
                //调用view接口提示请求异常
                if(isViewAttached()){
                    getView().showErr();
                }
            }

            @Override
            public void onComplete() {
                // 隐藏正在加载进度条
                if(isViewAttached()){
                    getView().hideLoading();
                }
            }
        });
    }
}
