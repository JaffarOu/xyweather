package com.jf.xyweather.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jf.xyweather.base.activity.BaseActivity;

/**
 * Created by jf on 2016/6/16.
 * Custom Application to replace systems Application
 */
public class MyApplications extends Application{

    private static Context context = null;
    private static final String LOG_TAG = "com.jf.xyweather.test";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    /**
     * Get MyApplications object
     * @return
     */
    public static Context getContext() {
        return context;
    }

    /**
     * To print a log
     * @param tag The tag of your log
     * @param message The message of your log
     */
    public static void showLog(String tag, String message){
        Log.i(tag, message);
    }

    /**
     * To print a log
     * @param message The message of your log
     */
    public static void showLog(String message){
        showLog(LOG_TAG, message);
    }

    /**
     * Show a Toast
     * @param baseActivity "BaseActivity" class
     * @param content The text that showed on Toast
     */
    public static void showToast(BaseActivity baseActivity, String content){
        Toast.makeText(baseActivity, content, Toast.LENGTH_SHORT).show();
    }
}
