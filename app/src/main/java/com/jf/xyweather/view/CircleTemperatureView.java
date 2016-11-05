package com.jf.xyweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.jf.xyweather.R;
import com.jf.xyweather.util.LogUtil;

/**
 * Created by JF on 2016/10/24.
 * A circle view used to show temperature
 * valid temperature:-30℃--60℃
 */
public class CircleTemperatureView extends View{

    private static final int MIN_TEMPERATURE = -30;     //控件能接受的最低温度
    private static final int MAX_TEMPERATURE = 60;      //控件能接受的最高温度

    //将最低温设置成最高温作为初始的情况
    private int mMaxTemperature = -1;
    private int mMinTemperature = 1;
    private int mCurrentTemperature;
    private boolean isDataReady = false;    //是否准备好绘图的温度数据
    private int lineLength = 20;            //温度刻度线的长度
    private int spaceWidth = 40;            //温度刻度线到控件边缘距离，留下这个距离用来绘制最高温最低温文字

    //The member variable about draw
    private Paint mTextPaint;           //写字（温度）的笔
    private Paint mArcPaint;            //绘制温度刻度线的笔
    private int backgroundArcColor;     //背景温度线的颜色资源id
    private int temperatureArcColor;    //用户所设置温度范围内的刻度线颜色资源id

    public CircleTemperatureView(Context context) {
        super(context);
        init();
    }

    public CircleTemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        backgroundArcColor = getResources().getColor(R.color.white);
        temperatureArcColor = getResources().getColor(R.color.red);

        mArcPaint = new Paint();
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(2);
        mArcPaint.setAntiAlias(true);//去掉锯齿

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasuredSize(widthMeasureSpec), getMeasuredSize(heightMeasureSpec));
    }

    private int getMeasuredSize(int measureSpec){
        int measuredMode = MeasureSpec.getMode(measureSpec);
        int measuredSize = 500;
        if(measuredMode == MeasureSpec.EXACTLY){
            measuredSize = MeasureSpec.getSize(measureSpec);
        }else if(measuredMode == MeasureSpec.AT_MOST){
            measuredSize = Math.min(MeasureSpec.getSize(measureSpec), measuredSize);
        }
        return measuredSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //将坐标轴移到控件中心，控件宽高必须一致，所以只考虑宽度/高度就可以
        int halfMeasuredSize = getMeasuredWidth()/2;
        canvas.translate(halfMeasuredSize, halfMeasuredSize);
        canvas.save();
        //如果数据未准备好，只要画一周仅有背景的刻度线就可以了
        if(!isDataReady){
            //旋转圆周绘制温度刻度线，每一条刻度线代表1℃，90℃共占据270°，每1℃间隔3°
            canvas.rotate(-135);
            mArcPaint.setColor(backgroundArcColor);
            for(int i = 0; i < 90; i++){
                mArcPaint.setColor(backgroundArcColor);
                //注意Y轴的坐标是反的
                canvas.drawLine(0, spaceWidth-halfMeasuredSize, 0, spaceWidth+lineLength-halfMeasuredSize, mArcPaint);
                canvas.rotate(3f);
            }
            canvas.restore();
            canvas.save();
        }else{
            //如果数据准备好，除了要在绘制圆周时根据温度值来变更颜色，还要绘制温度文字
            //旋转圆周绘制温度刻度线，每一条刻度线代表1℃，90℃共占据270°，每1℃间隔3°
            canvas.rotate(-135);
            mArcPaint.setColor(backgroundArcColor);
            //循环运算的判断标志线
            int low = mMinTemperature-MIN_TEMPERATURE;
            int height = mMaxTemperature-MIN_TEMPERATURE;
            for(int i = 0; i < 90; i++){
                if(i>=low && i<height){
                    //所绘制的刻度线为用户所设置的温度范围之内，设置画笔为温度线颜色
                    mArcPaint.setColor(temperatureArcColor);
                }else if( i >= mMaxTemperature-MAX_TEMPERATURE){
                    //所绘制的刻度线为用户所设置的温度范围之内，设置画笔为背景线颜色
                    mArcPaint.setColor(backgroundArcColor);
                }
                //注意Y轴的坐标是反的
                canvas.drawLine(0, spaceWidth-halfMeasuredSize, 0, spaceWidth+lineLength-halfMeasuredSize, mArcPaint);
                canvas.rotate(3f);
            }
            canvas.restore();
            canvas.save();
            //在控件中心写上当前的温度
            mTextPaint.setTextSize(120);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            //文字baseline到文字中心的高度
            float baseLineHeight = (-fontMetrics.top+fontMetrics.bottom)/2-fontMetrics.bottom;
            canvas.drawText(mCurrentTemperature+"°", 0, baseLineHeight, mTextPaint);
            //分别在圆周最高温、最低温处绘制温度文字
            mTextPaint.setTextSize(30);
            Paint.FontMetrics maxMinFontMetrics = mTextPaint.getFontMetrics();
            float maxMinBaseLineHeight = (-maxMinFontMetrics.top+maxMinFontMetrics.bottom)/2-maxMinFontMetrics.bottom;
            canvas.save();
            //坐标移动到最低温那里，绘制文字
            int degree = -135+(mMinTemperature-MIN_TEMPERATURE)*3;
            canvas.rotate(degree);
            //注意Y轴的数字是反的
            canvas.translate(0, spaceWidth/2-halfMeasuredSize);
            canvas.rotate(-degree);
            canvas.drawText(mMinTemperature+"", 0, maxMinBaseLineHeight, mTextPaint);
            canvas.restore();
            canvas.save();
            //坐标移动到最高温哪里，绘制文字
            degree = -135+(mMaxTemperature-MIN_TEMPERATURE)*3;
            canvas.rotate(degree);
            //注意Y轴的数字是反的
            canvas.translate(0, spaceWidth/2-halfMeasuredSize);
            canvas.rotate(-degree);
            canvas.drawText(mMaxTemperature+"", 0, maxMinBaseLineHeight, mTextPaint);
            canvas.restore();
            canvas.save();
        }
    }

    /**
     * 设置温度
     * @param max 最高温
     * @param now 当前温度
     * @param min 最低温
     */
    public void setTemperature(int max, int now, int min){
        /*本来应该还要判断是否满足min<now且now<max，
        但是实际天气数据now有可能不在最高-最低温范围之内，所以去掉了该判断*/
        if( max>MAX_TEMPERATURE || min <MIN_TEMPERATURE){
            LogUtil.i(getClass().getSimpleName()+"--传入天气数据有误");
            return;
        }
        isDataReady = true;
        mMaxTemperature = max;
        mCurrentTemperature = now;
        mMinTemperature = min;
        postInvalidateDelayed(200);
    }

}
