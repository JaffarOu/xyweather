package com.jf.xyweather.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jf.xyweather.R;

/**
 * Created by jf on 2016/6/16.
 * The Custom title of this app
 */
public class CustomTitles extends RelativeLayout implements View.OnClickListener{

    private TextView titleTv;//title text
    private ImageView leftFirstIv;//the first ImageView from left
    private ImageView leftSecondIv;//the second ImageView from left
    private ImageView rightFirstIv;//the first ImageView from right
    private ImageView rightSecondIv;//the second ImageView from right

    //To identify which ImageView that selected by user
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
        if(which == LEFT_FIRST){
            leftFirstIv.setImageResource(resourceId);
            leftFirstIv.setVisibility(VISIBLE);
            return;
        }
        if(which == LEFT_SECOND){
            leftSecondIv.setImageResource(resourceId);
            leftSecondIv.setVisibility(VISIBLE);
            return;
        }
        if(which == RIGHT_FIRST){
            rightFirstIv.setImageResource(resourceId);
            rightFirstIv.setVisibility(VISIBLE);
            return;
        }
        if(which == RIGHT_SECOND){
            rightSecondIv.setImageResource(resourceId);
            rightSecondIv.setVisibility(VISIBLE);
        }
    }

    /**
     * Set OnTitleClickListener Listener for title,
     * The ImageView won't response the click event except you set icon to it before you call OnTitleClickListener
     * @param listener
     */
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
        /**
         * The call back method of OnTitleClickListener
         * @param view The view object that was clicked
         * @param which A integer variables to identify which ImageView that was clicked,
         *              it only may be LEFT_FIRST,LEFT_SECOND,RIGHT_FIRST or RIGHT_SECOND
         */
        public void onTitleClick(View view, int which);
    }

    @Override
    public void onClick(View v) {
        if(listener == null){
            return;
        }
        int id = v.getId();
//        int which = -1;
        if(id == R.id.iv_custom_titles_left_first){
            listener.onTitleClick(v, LEFT_FIRST);
            return;
        }
        if(id == R.id.iv_custom_titles_left_second){
            listener.onTitleClick(v, LEFT_SECOND);
            return;
        }
        if(id == R.id.iv_custom_titles_right_first){
            listener.onTitleClick(v, RIGHT_FIRST);
            return;
        }
        if(id == R.id.iv_custom_titles_right_second){
            listener.onTitleClick(v, RIGHT_SECOND);
        }
    }
}
