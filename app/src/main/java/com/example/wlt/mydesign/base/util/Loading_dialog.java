package com.example.wlt.mydesign.base.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.wlt.mydesign.R;


/**
 * Created by WLT on 2017-03-28.
 * 加载提醒对话框
 */

public class Loading_dialog extends Dialog {

    public Loading_dialog(Context context) {
        super(context, R.style.loading_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //点击返回不关闭dialog
        }
        return false;
    }

    private void init() {
//        setCanceledOnTouchOutside(false);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
