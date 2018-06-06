package com.example.wlt.mydesign.View.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wlt.mydesign.R;

/**
 * Created by 25122 on 2018/6/4.
 */

public class MineFragment extends Fragment{

    public MineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_mine,null,false);
        return view;
    }
}
