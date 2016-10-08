package com.jf.xyweather.util;

import android.util.Log;

/**
 * Created by JF on 2016/10/8.
 * A tool for printing log
 */
public class LogUtil {

    //This class should be used as a tool and it does not allowed create object
    private LogUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    //To control if the log can be print
    private static final boolean isDebug = true;

    //Tag
    private static final String TAG = "xyweatherLog";

    public static void v(String msg) {
        if (isDebug) Log.v(TAG, msg);
    }

    /*print log use the default TAG__start*/
    public static void d(String msg) {
        if (isDebug) Log.d(TAG, msg);
    }

    public static void i(String msg) {
        if (isDebug) Log.i(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug) Log.e(TAG, msg);
    }
    /*print log use the default TAG__end*/

    /*print log use the tag specified by user_start*/
    public static void v(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }
    /*print log use the tag specified by user_end*/
}
