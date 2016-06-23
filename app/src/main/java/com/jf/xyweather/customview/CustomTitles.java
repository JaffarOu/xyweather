package com.jf.xyweather.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jf.xyweather.R;

/**
 * Created by jf on 2016/6/16.
 * 应用所用的自定义标题
 */
public class CustomTitles extends FrameLayout{

    private TextView titleTv;
    private ImageView leftFirstIv;
    private ImageView leftSecondIv;
    private ImageView rightFirstIv;
    private ImageView rightSecondIv;

    public static final int LEFT_FIRST = 1;
    public static final int LEFT_SECOND = 1<<1;
    public static final int RIGHT_FIRST = 1<<2;
    public static final int RIGHT_SECOND = 1<<3;

    public CustomTitles(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPadding(8, 8, 8, 8);
        LayoutInflater.from(context).inflate(R.layout.custom_titles, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitles);

        //获取各个控件所对应的资源
        int leftFirstImageSrcId = typedArray.getResourceId(R.styleable.CustomTitles_leftFirstImageSrc, R.drawable.ic_xyweather_launcher);
        int leftSecondImageSrcId = typedArray.getResourceId(R.styleable.CustomTitles_leftSecondImageSrc, R.drawable.ic_xyweather_launcher);
        String titleString = typedArray.getString(R.styleable.CustomTitles_titleText);
        int rightFirstImageSrcId = typedArray.getResourceId(R.styleable.CustomTitles_rightFirstImageSrc, R.drawable.ic_xyweather_launcher);
        int rightSecondImageSrcId = typedArray.getResourceId(R.styleable.CustomTitles_rightSecondImageSrc, R.drawable.ic_xyweather_launcher);

        //找到各控件
        leftFirstIv = (ImageView)findViewById(R.id.iv_custom_titles_left_first);
        leftSecondIv = (ImageView)findViewById(R.id.iv_custom_titles_left_second);
        titleTv = (TextView)findViewById(R.id.tv_custom_titles_title);
        rightFirstIv = (ImageView)findViewById(R.id.iv_custom_titles_right_first);
        rightSecondIv = (ImageView)findViewById(R.id.iv_custom_titles_right_second);

        //设置资源
        leftFirstIv.setImageResource(leftFirstImageSrcId);
        leftSecondIv.setImageResource(leftSecondImageSrcId);
        titleTv.setText(titleString);
        rightFirstIv.setImageResource(rightFirstImageSrcId);
        rightSecondIv.setImageResource(rightSecondImageSrcId);
        typedArray.recycle();
    }

    public void setTitleText(String titleText){
        titleTv.setText(titleText);
    }

    public void setImageViewResource(int leftFirst, int leftSecond, int rightFirst, int rightSecond){
        leftFirstIv.setImageResource(leftFirst);
        leftSecondIv.setImageResource(leftSecond);
        rightFirstIv.setImageResource(rightFirst);
        rightSecondIv.setImageResource(rightSecond);
    }

    public void setImageViewResource(int which, int resourceId){
        switch (which){
            case LEFT_FIRST:
                leftFirstIv.setImageResource(resourceId);
                break;
            case LEFT_SECOND:
                leftSecondIv.setImageResource(resourceId);
                break;
            case RIGHT_FIRST:
                rightFirstIv.setImageResource(resourceId);
                break;
            default://RIGHT_SECOND
                rightSecondIv.setImageResource(resourceId);
                break;
        }
    }
}
