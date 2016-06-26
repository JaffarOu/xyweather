package com.jf.xyweather.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jf.xyweather.base.MyApplications;

/**
 * Created by jf on 2016/6/16.
 * 基类"Fragment"_应用里面所有"Fragment"的基类
 */
abstract public class BaseFragment extends Fragment {

    protected View layoutView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplications.showLog("which_fragment", getClass().getSimpleName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initExtra();
        layoutView = inflater.inflate(getLayoutViewId(), container, false);
        initView(layoutView);
        return layoutView;
    }

    /**
     * get the id of the layout file
     * @return the resource id of the layout file that child class want to inflate
     */
    abstract protected int getLayoutViewId();

    /**
     * initial some before initial view
     */
    abstract protected void initExtra();

    /**
     * initial view before the "onCreateView" method return
     * @param layoutView the view inflated according the id that from "getLayoutViewId()" method
     */
    abstract protected void initView(View layoutView);
}
