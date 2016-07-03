package com.jf.xyweather.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jf.xyweather.R;

/**
 * Created by jf on 2016/6/16.
 * 应用所用的自定义标题
 */
public class CustomTitles extends FrameLayout implements View.OnClickListener{

    private TextView titleTv;//title text
    private ImageView leftFirstIv;//the first ImageView from left
    private ImageView leftSecondIv;//the second ImageView from left
    private ImageView rightFirstIv;//the first ImageView from right
    private ImageView rightSecondIv;//the second ImageView from right

    public static final int LEFT_FIRST = 1;
    public static final int LEFT_SECOND = 1<<1;
    public static final int RIGHT_FIRST = 1<<2;
    public static final int RIGHT_SECOND = 1<<3;

    private OnTitleClickListener listener;

    public CustomTitles(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.custom_view_custom_titles, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitles);

        //find every child view
        leftFirstIv = (ImageView)findViewById(R.id.iv_custom_titles_left_first);
        leftSecondIv = (ImageView)findViewById(R.id.iv_custom_titles_left_second);
        titleTv = (TextView)findViewById(R.id.tv_custom_titles_title);
        rightFirstIv = (ImageView)findViewById(R.id.iv_custom_titles_right_first);
        rightSecondIv = (ImageView)findViewById(R.id.iv_custom_titles_right_second);

        //initial the title's text
        String titleString = typedArray.getString(R.styleable.CustomTitles_titleText);
        titleTv.setText(titleString);

        typedArray.recycle();
    }

    /**
     * set the text for title
     * @param titleText text for title
     */
    public void setTitleText(String titleText){
        titleTv.setText(titleText);
    }

    /**
     * set title text color
     * @param color resource id of color
     */
    public void setTitleTextColor(int color){
        titleTv.setTextColor(color);
    }

    /**
     * set the icon of the title's ImageView
     * @param leftFirst the resource id of the first ImageView from left
     * @param leftSecond the resource id of the second ImageView from left
     * @param rightFirst the resource id of the first ImageView from right
     * @param rightSecond the resource id of the second ImageView from right
     */
    public void setImageViewResource(int leftFirst, int leftSecond, int rightFirst, int rightSecond){
        leftFirstIv.setImageResource(leftFirst);
        leftSecondIv.setImageResource(leftSecond);
        rightFirstIv.setImageResource(rightFirst);
        rightSecondIv.setImageResource(rightSecond);

        leftFirstIv.setVisibility(VISIBLE);
        leftSecondIv.setVisibility(VISIBLE);
        rightFirstIv.setVisibility(VISIBLE);
        rightSecondIv.setVisibility(VISIBLE);
    }

    /**
     * set the icon of title's ImageView
     * @param which which ImageView's icon you want to set
     * @param resourceId the resource id of the icon
     */
    public void setImageViewResource(int which, int resourceId){
        switch (which){
            case LEFT_FIRST:
                leftFirstIv.setImageResource(resourceId);
                leftFirstIv.setVisibility(VISIBLE);
                break;
            case LEFT_SECOND:
                leftSecondIv.setImageResource(resourceId);
                leftSecondIv.setVisibility(VISIBLE);
                break;
            case RIGHT_FIRST:
                rightFirstIv.setImageResource(resourceId);
                rightFirstIv.setVisibility(VISIBLE);
                break;
            case RIGHT_SECOND://RIGHT_SECOND
                rightSecondIv.setImageResource(resourceId);
                rightSecondIv.setVisibility(VISIBLE);
                break;
            default:break;
        }
    }

//    /**
//     * set the visibility for the ImageView in the CustomTitles
//     * @param leftFirst set the visibility for the ImageView on the left first
//     * @param leftSecond set the visibility for the ImageView on the left second
//     * @param rightFirst set the visibility for the ImageView on the right first
//     * @param rightSecond set the visibility for the ImageView on the right second
//     */
//    public void setImageViewVisibility(int leftFirst, int leftSecond, int rightFirst, int rightSecond){
//        leftSecondIv.setVisibility(leftFirst);
//        leftSecondIv.setVisibility(leftSecond);
//        rightFirstIv.setVisibility(rightFirst);
//        rightSecondIv.setVisibility(rightSecond);
//    }

    public void setOnTitleClickListener(OnTitleClickListener listener){
        if(listener == null){
            return;
        }
        this.listener = listener;
        if(leftFirstIv.getVisibility() == VISIBLE){
            leftFirstIv.setOnClickListener(this);
        }
        if(leftSecondIv.getVisibility() == VISIBLE){
            leftSecondIv.setOnClickListener(this);
        }
        if(rightFirstIv.getVisibility() == VISIBLE){
            rightFirstIv.setOnClickListener(this);
        }
        if(rightSecondIv.getVisibility() == VISIBLE){
            rightSecondIv.setOnClickListener(this);
        }
    }

    public interface OnTitleClickListener{
        public void onTitleClick(View view, int which);
    }

    @Override
    public void onClick(View v) {
        if(listener == null){
            return;
        }
        int id = v.getId();
        int which = -1;
        switch (id){
            case R.id.iv_custom_titles_left_first:
                which = LEFT_FIRST;
                break;
            case R.id.iv_custom_titles_left_second:
                which = LEFT_SECOND;
                break;
            case R.id.iv_custom_titles_right_first:
                which = RIGHT_FIRST;
                break;
            case R.id.iv_custom_titles_right_second:
                which = RIGHT_SECOND;
                break;
            default:break;
        }
        listener.onTitleClick(v, which);
    }
}
