package com.jf.xyweather.base.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.util.LogUtil;

/**
 * Created by jf on 2016/6/16.
 * The basic Activity of all Activities in this App
 */
abstract public class BaseActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Print the current Activity's name for debug
        LogUtil.i("which_activity", getClass().getSimpleName());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
