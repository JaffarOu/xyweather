package com.jf.xyweather.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jf.xyweather.R;
import com.jf.xyweather.util.DimenUtil;
import com.jf.xyweather.util.LogUtil;

/**
 * Created by JF on 2016/10/24.
 * 一个显示温度用的圆形View，
 * 圆形从左下角（第三象限距离Y轴45°）开始，到右下角（第四象限距离Y轴45°）结束，
 * 温度有效范围是-30℃到60℃
 */
public class CircleTemperature extends View{

    private static final int MIN_TEMPERATURE = -30;     //控件能接受的最低温度
    private static final int MAX_TEMPERATURE = 60;      //控件能接受的最高温度
    private static final int START_ANGLE = -135;        //圆形绘制起始角度，该角度以Y轴向上为0°计算
    private static final int ANGLE_PER_DEGREE = 3;      //每一摄氏度对应于圆形多少角度

    //记录用户设置的最高温、最低温、当前温度
    private int mMaxTemperature;
    private int mMinTemperature;
    private int mCurrentTemperature;

    //绘图相关的变量
    private boolean isDataReady = false;        //是否准备好绘图的温度数据
    private float lineLength;                   //温度刻度线的长度
    private float spaceWidth;                   //温度刻度线到控件边缘距离，留下这个距离用来绘制最高温最低温文字
    private Paint mTextPaint;                   //写字（温度）的笔
    private Paint mLinePaint;                   //绘制温度刻度线的笔
    private int mLineBackgroundColor;           //温度刻度线的背景颜色
    private int mLineColor;                     //温度刻度线在用户所设置的范围内的颜色
    private float mCurrentTemperatureTextSize;  //当前温度文字的尺寸
    private float mMaxMinTemperatureTextSize;   //最高最低温文字的尺寸

    public CircleTemperature(Context context) {
        super(context);
        init();
    }

    public CircleTemperature(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        Resources resources = getResources();
        mLineBackgroundColor = resources.getColor(R.color.white);
        mLineColor = resources.getColor(R.color.red);
        //这些尺寸只是挑了一个大概尺寸，没有什么特殊意味
        spaceWidth = DimenUtil.dpToPx(16);
        lineLength = DimenUtil.dpToPx(16);
        mCurrentTemperatureTextSize = DimenUtil.spToPx(80);
        mMaxMinTemperatureTextSize = DimenUtil.spToPx(12);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setAntiAlias(true);//去掉锯齿

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(resources.getColor(R.color.white));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasuredSize(widthMeasureSpec), getMeasuredSize(heightMeasureSpec));
    }

    private int getMeasuredSize(int measureSpec){
        int measuredMode = MeasureSpec.getMode(measureSpec);
        int measuredSize = (int)DimenUtil.dpToPx(260);
        if(measuredMode == MeasureSpec.EXACTLY){
            measuredSize = MeasureSpec.getSize(measureSpec);
        }else if(measuredMode == MeasureSpec.AT_MOST){
            measuredSize = Math.min(MeasureSpec.getSize(measureSpec), measuredSize);
        }
        return measuredSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //确定温度刻度线的长度
        //将坐标轴移到控件中心，控件宽高必须一致，所以只考虑宽度/高度就可以
        int halfMeasuredSize = getMeasuredWidth()/2;
        canvas.translate(halfMeasuredSize, halfMeasuredSize);
        canvas.save();
        //先绘制一周背景圆
        //旋转圆周绘制温度刻度线，每一条刻度线代表1℃，90℃共占据270°，每1℃间隔3°
        canvas.rotate(START_ANGLE);
        mLinePaint.setColor(mLineBackgroundColor);
        for(int i = 0, target = MAX_TEMPERATURE-MIN_TEMPERATURE; i < target; i++){
            //注意Y轴的坐标是反的
            canvas.drawLine(0, spaceWidth-halfMeasuredSize, 0, spaceWidth+lineLength-halfMeasuredSize, mLinePaint);
            canvas.rotate(ANGLE_PER_DEGREE);
        }
        canvas.restore();
        canvas.save();
        //如果数据为准备好，操作就到此结束了，后面的操作不用做
        if(!isDataReady) return;

        //根据所设置的温度范围，将刻度线画成不同颜色
        mLinePaint.setColor(mLineColor);
        canvas.rotate((mMinTemperature-MIN_TEMPERATURE)*ANGLE_PER_DEGREE+START_ANGLE);
        for(int i = 0, limit = mMaxTemperature-mMinTemperature+1; i < limit; i++){
            canvas.drawLine(0, spaceWidth-halfMeasuredSize, 0, spaceWidth+lineLength-halfMeasuredSize, mLinePaint);
            canvas.rotate(3f);
        }
        canvas.restore();
        canvas.save();
        //在控件中心写上当前的温度
        mTextPaint.setTextSize(mCurrentTemperatureTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        //文字baseline到文字中心的高度
        float baseLineHeight = (-fontMetrics.top+fontMetrics.bottom)/2-fontMetrics.bottom;
        canvas.drawText(mCurrentTemperature+"°", 0, baseLineHeight, mTextPaint);
        //分别在圆周最高温、最低温处绘制温度文字
        mTextPaint.setTextSize(mMaxMinTemperatureTextSize);
        Paint.FontMetrics maxMinFontMetrics = mTextPaint.getFontMetrics();
        float maxMinBaseLineHeight = (-maxMinFontMetrics.top+maxMinFontMetrics.bottom)/2-maxMinFontMetrics.bottom;
        canvas.save();
        //坐标移动到最低温那里，绘制文字
        int degree = START_ANGLE+(mMinTemperature-MIN_TEMPERATURE)*ANGLE_PER_DEGREE;
        canvas.rotate(degree);
        //注意Y轴的坐标是反的
        canvas.translate(0, spaceWidth/2-halfMeasuredSize);
        canvas.rotate(-degree);
        canvas.drawText(mMinTemperature+"", 0, maxMinBaseLineHeight, mTextPaint);
        canvas.restore();
        canvas.save();
        //坐标移动到最高温哪里，绘制文字
        degree = START_ANGLE+(mMaxTemperature-MIN_TEMPERATURE)*ANGLE_PER_DEGREE;
        canvas.rotate(degree);
        //注意Y轴的数字是反的
        canvas.translate(0, spaceWidth/2-halfMeasuredSize);
        canvas.rotate(-degree);
        canvas.drawText(mMaxTemperature+"", 0, maxMinBaseLineHeight, mTextPaint);
        canvas.restore();
        canvas.save();
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
