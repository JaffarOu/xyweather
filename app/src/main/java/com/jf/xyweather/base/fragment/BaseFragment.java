package com.jf.xyweather.base.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jf.xyweather.base.MyApplications;

/**
 * Created by jf on 2016/6/16.
 * 基类"Fragment"_应用里面所有"Fragment"的基类
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplications.showLog(getClass().getSimpleName());
    }
}
