package com.example.wlt.mydesign.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wlt.mydesign.MainActivity;
import com.example.wlt.mydesign.R;
import com.example.wlt.mydesign.Views.IBaseView;
import com.example.wlt.mydesign.base.util.Loading_dialog;

/**
 * Created by 25122 on 2018/6/7.
 */

public class BaseActivity extends AppCompatActivity implements IBaseView{
    private Loading_dialog loading_dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading_dialog = new Loading_dialog(BaseActivity.this);
    }

    @Override
    public void showLoading() {
        if(!loading_dialog.isShowing()){
            loading_dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(loading_dialog.isShowing()){
            loading_dialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErr() {
        showToast(getResources().getString(R.string.api_error_msg));
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }
}
