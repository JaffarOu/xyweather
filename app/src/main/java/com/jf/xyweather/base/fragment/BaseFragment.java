package com.jf.xyweather.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.util.LogUtil;

/**
 * Created by jf on 2016/6/16.
 * The basic Fragment of all Fragments in this App
 */
abstract public class BaseFragment extends Fragment {

    //The View returned in onCreateView() method
    protected View layoutView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("which_fragment", getClass().getSimpleName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initOther();
        layoutView = inflater.inflate(getLayoutViewId(), container, false);
        initView(layoutView);
        return layoutView;
    }

    /**
     * get the id of the layout file
     * @return the resource id of the layout file that child class want to inflate
     */
    abstract protected int getLayoutViewId();

    /**initial something before initial view*/
    protected void initOther(){}

    /**
     * initial view before the "onCreateView" method return
     * @param layoutView the view inflated according the id that from "getLayoutViewId()" method
     */
    protected void initView(View layoutView){}
}
