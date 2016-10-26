package com.jf.xyweather.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by JF on 2016/10/26.
 * An ListView without vertical scroll
 */
public class NoScrollListView extends ListView{

    public NoScrollListView(Context context) {
        super(context);
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
