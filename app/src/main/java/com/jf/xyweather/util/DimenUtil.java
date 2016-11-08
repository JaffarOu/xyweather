package com.jf.xyweather.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.jf.xyweather.base.MyApplications;

/**
 * Created by JF on 2016/11/8.
 * Tool that used to change px,dp,sp
 */
public final class DimenUtil {

    private static final float SCALE;
    private static final float FONT_SCALE;

    static {
        DisplayMetrics displayMetrics = MyApplications.getContext().getResources().getDisplayMetrics();
        SCALE = displayMetrics.density;
        FONT_SCALE = displayMetrics.scaledDensity;
    }

    /**
     * Change dp to px
     * @param dpValue The dp you want to change
     * @return result px
     */
    public static float dpToPx(float dpValue){
        return dpValue * SCALE + 0.5f;
    }

    /**
     * Change px to dp
     * @param pxValue The px you want to change
     * @return result dp
     */
    public static float pxToDp(float pxValue){
        return (int) (pxValue / SCALE + 0.5f);
    }

    /**
     * Change sp to px
     * @param spValue The sp you want to change
     * @return result px
     */
    public static float spToPx(float spValue){
        return spValue * FONT_SCALE + 0.5f;
    }

    /**
     * Change px to sp
     * @param pxValue The px you want to change
     * @return result sp
     */
    public static float pxToSp(float pxValue){
        return pxValue / FONT_SCALE + 0.5f;
    }
}
