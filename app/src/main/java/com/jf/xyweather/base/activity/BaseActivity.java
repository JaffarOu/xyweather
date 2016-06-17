package com.jf.xyweather.base.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jf.xyweather.base.MyApplications;

/**
 * Created by jf on 2016/6/16.
 * 基类"Activity"_应用里面所有"Activity"的超类
 */
abstract public class BaseActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打印当前"Activity"名字，方便调试
        MyApplications.showLog(this.getClass().getSimpleName());
        setContentView(getContentViewId());
        initExtra();
        initView();
    }

    abstract protected int getContentViewId();

    abstract protected void initExtra();

    abstract protected void initView();
}
