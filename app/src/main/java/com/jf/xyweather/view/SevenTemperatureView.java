package com.jf.xyweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jf on 2016/10/28.
 */
public class SevenTemperatureView extends View{

    private int space = 20;                 //肉眼所见的坐标轴距离View底边和左边的间隙
    private float[] mMaxTemperatures;       //七天、七个最高温
    private float[] mMinTemperatures;       //七天、七个最低温
    private boolean isCalculate = false;    //标记是否已对设置的温度值进行坐标运算

    private Paint mPaint;
    private Path mMaxTemperaturePath;       //最高温绘制路径
    private Path mMinTemperaturePath;       //最低温绘制路径

    public SevenTemperatureView(Context context) {
        super(context);
        init();
    }

    public SevenTemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mMaxTemperaturePath = new Path();
        mMinTemperaturePath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //处理AT_MOST情况尺寸
        int measuredWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measuredHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (measuredWidthMode == MeasureSpec.AT_MOST && measuredHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, 400);
        } else if (measuredWidthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, MeasureSpec.getSize(heightMeasureSpec));
        } else if (measuredHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 400);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMaxTemperatures == null || mMinTemperatures == null) return;
        if (!isCalculate) {
            calculate();
            isCalculate = true;
        }
        //温度坐标系的原点，在View左下角，距离左边和底边"space"距离
        canvas.translate(space, getMeasuredHeight() - space);
        //绘制横竖坐标轴
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(0, 0, getMeasuredWidth() - space, 0, mPaint);
        canvas.drawLine(0, 0, 0, space - getMeasuredHeight(), mPaint);
        //绘制温度线，先绘制最高温再绘制最低温
        mPaint.setColor(Color.RED);
        canvas.drawPath(mMaxTemperaturePath, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawPath(mMinTemperaturePath, mPaint);
    }

    public void setTemperature(float maxTemperatures[], float minTemperatures[]) {
        mMaxTemperatures = maxTemperatures;
        mMinTemperatures = minTemperatures;
        isCalculate = false;
        postInvalidateDelayed(1000);
    }

    //根据温度值来计算绘图坐标
    private void calculate(){
        //各个温度值对应的X轴坐标
        float[] temperatureX;
        //各个温度值对应的Y轴坐标，为了计算方便里面的值都是按照正数计算，由于安卓坐标系的原因，是好用是要取相反数
        float[] maxTemperatureY, minTemperatureY;
        //根据设置的温度值进行计算，由于"temperatureX"、"maxTemperatureY"都是绘图所用的坐标系，所以它们都是按照canvas的原点计算的
        temperatureX = new float[7];
        //横坐标点间的间隔，除以8，为了让点不要画在控件边上
        float horizontalSpace = (getMeasuredWidth() - space)/8;
        for(int i = 0; i < 7; i++){
            temperatureX[i] = horizontalSpace*(i+1);
        }
        //七天最高温的最高温，七天最高温的最低温，十四个数据里面找出最大值，最小值
        float maxTem = mMaxTemperatures[0], minTem = mMaxTemperatures[0];
        for(int i = 1; i < mMaxTemperatures.length; i++){
            if(maxTem < mMaxTemperatures[i]) maxTem = mMaxTemperatures[i];
            if(minTem > mMaxTemperatures[i]) minTem = mMaxTemperatures[i];
        }
        for(int i = 0; i < mMinTemperatures.length; i++){
            if(maxTem < mMinTemperatures[i]) maxTem = mMinTemperatures[i];
            if(minTem > mMinTemperatures[i]) minTem = mMinTemperatures[i];
        }
        //计算这些温度所对应的坐标
        maxTemperatureY = new float[7];
        minTemperatureY = new float[7];
        float minTemperaturePosition = (getMeasuredHeight()-space)/8;//最低温对应的坐标
        float temperatureRange = maxTem-minTem;     //这是温度的跨度
        float validHeight = getMeasuredHeight()-space-minTemperaturePosition*2;//曲线的有效区间
        //七天最高温对应坐标点
        for(int i = 0; i < maxTemperatureY.length; i++){
            maxTemperatureY[i] = (mMaxTemperatures[i]-minTem)*validHeight/temperatureRange+minTemperaturePosition;
        }
        //七天最低温对应坐标点
        for(int i = 0; i < maxTemperatureY.length; i++){
            minTemperatureY[i] = (mMinTemperatures[i]-minTem)*validHeight/temperatureRange+minTemperaturePosition;
        }
        //这个地方比较特别，这个移动不能放到onDraw()方法里面，只能在这里，因为它只能移一次，而onDraw()则会被多次调用
        mMaxTemperaturePath.reset();
        mMinTemperaturePath.reset();
        mMaxTemperaturePath.moveTo(temperatureX[0], -maxTemperatureY[0]);
        mMinTemperaturePath.moveTo(temperatureX[0], -minTemperatureY[0]);
        //填充lineTo()
        for(int i = 1;i < temperatureX.length; i++){
            mMaxTemperaturePath.lineTo(temperatureX[i], -maxTemperatureY[i]);
        }
        for(int i = 1;i < temperatureX.length; i++){
            mMinTemperaturePath.lineTo(temperatureX[i], -minTemperatureY[i]);
        }
    }
}
