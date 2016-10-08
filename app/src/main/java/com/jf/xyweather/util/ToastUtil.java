package com.jf.xyweather.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by JF on 2016/10/8.
 * A tool for show the Toast
 */
public class ToastUtil {

    //This class can not be instantiated
    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    //To control if Toast can be showed
    private static final boolean isShow = true;

    /**
     * Show a Toast in short time
     * @param context The Context
     * @param text The content that be showed
     */
    public static void showShortToast(Context context, CharSequence text) {
        if (isShow) Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show a Toast in long time
     * @param context The Context
     * @param text The content that be showed
     */
    public static void showLongToast(Context context, CharSequence text) {
        if (isShow) Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * Show a Toast with specified time
     * @param context The Context
     * @param text The content that be showed
     * @param duration The time
     */
    public static void show(Context context, CharSequence text, int duration) {
        if (isShow) Toast.makeText(context, text, duration).show();
    }
}
