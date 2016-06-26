package com.jf.xyweather.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jf.xyweather.base.activity.BaseActivity;

/**
 * Created by jf on 2016/6/16.
 */
public class MyApplications extends Application{

    private static Context context = null;
    private static final String LOG_TAG = "com.jf.xyweather.test";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    public static void showLog(String tag, String content){
        Log.i(tag, content);
    }
    /**
     * 打印Log的信息
     * @param content 待打印的字符串
     */
    public static void showLog(String content){
        showLog(LOG_TAG, content);
    }

    /**
     * 弹出"Toast"
     * @param baseActivity 基类"BaseActivity"
     * @param content 所弹出的"Toast"内容
     */
    public static void showToast(BaseActivity baseActivity, String content){
        Toast.makeText(baseActivity, content, Toast.LENGTH_SHORT).show();
    }
}
