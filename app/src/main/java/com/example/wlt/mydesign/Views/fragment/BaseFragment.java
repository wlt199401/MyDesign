package com.example.wlt.mydesign.Views.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wlt.mydesign.MainActivity;
import com.example.wlt.mydesign.Views.IBaseView;
import com.example.wlt.mydesign.base.BaseActivity;

import java.nio.channels.AcceptPendingException;

/**
 * Created by 25122 on 2018/6/7.
 */

public abstract class BaseFragment extends Fragment implements IBaseView {

    public abstract int getContentViewId();

    protected abstract void initAllMembersView(Bundle saveInstanceState);

    protected Context context;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentViewId(),container,false);
        this.context = getActivity();
        initAllMembersView(savedInstanceState);
        return mRootView;
    }

    @Override
    public void showLoading() {
        checkActivityAttached();
        ((BaseActivity)context).showLoading();
    }

    @Override
    public void hideLoading() {
        checkActivityAttached();
        ((BaseActivity)context).hideLoading();
    }

    @Override
    public void showToast(String msg) {
        checkActivityAttached();
        ((BaseActivity)context).showToast(msg);
    }

    @Override
    public void showErr() {
        checkActivityAttached();
        ((BaseActivity)context).showErr();
    }

    protected boolean isAttachedContext(){
        return getActivity()!=null;
    }

    protected void checkActivityAttached(){
        if(getActivity() == null){
            throw new ActivityNotAttachedException();
        }
    }

    public static class ActivityNotAttachedException  extends RuntimeException{
        public ActivityNotAttachedException(){
            super("Fragment has disconnected from Activity!- -.");
        }
    }
}
