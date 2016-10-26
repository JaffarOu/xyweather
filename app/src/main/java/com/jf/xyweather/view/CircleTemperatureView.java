package com.jf.xyweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by JF on 2016/10/24.
 * A circle temperature show view
 */
public class CircleTemperatureView extends View{

    //Set the max temperature smaller than min temperature as the initial condition
    private int mMaxTemperature = -1;
    private int mMinTemperature = 1;
    private int mCurrentTemperature;

    public CircleTemperatureView(Context context) {
        super(context);
    }

    public CircleTemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasuredSize(widthMeasureSpec), getMeasuredSize(heightMeasureSpec));
    }

    private int getMeasuredSize(int measureSpec){
        int measuredMode = MeasureSpec.getMode(measureSpec);
        int measuredSize = 400;
        if(measuredMode == MeasureSpec.EXACTLY){
            measuredSize = MeasureSpec.getSize(measureSpec);
        }else if(measuredMode == MeasureSpec.AT_MOST){
            measuredSize = Math.min(MeasureSpec.getSize(measureSpec), measuredSize);
        }
        return measuredSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * Set the temperature
     * @param max The max temperature
     * @param now The current temperature
     * @param min The min temperature
     */
    public void setTemperature(int max, int now, int min){

    }

    /**
     * Update UI after you set the temperature
     */
    public void update(){
        //wrong condition
        if( !( (mMaxTemperature>=mCurrentTemperature)&&(mCurrentTemperature>=mMinTemperature) )){
            return;
        }
        invalidate();
    }
}
