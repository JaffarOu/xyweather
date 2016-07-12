package com.jf.xyweather.base.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jf.xyweather.base.MyApplications;

/**
 * Created by jf on 2016/6/16.
 * The basic Activity of all Activities in this App
 */
abstract public class BaseActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打印当前"Activity"名字，方便调试
        MyApplications.showLog("which_activity", this.getClass().getSimpleName());
        setContentView(getContentViewId());
        initOther();
        initView();
    }

    /**
     * Get the id of layout file in Activity
     * @return The id of layout file inActivity
     */
    abstract protected int getContentViewId();

    /**
     * Initial something before call setContentView() method
     */
    abstract protected void initOther();

    /**
     * Initial view in here
     */
    abstract protected void initView();
}
