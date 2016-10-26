package com.jf.xyweather.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;

/**
 * Created by jf on 2016/6/20.
 * 主模块-欢迎界面
 */
public class WelcomeActivity extends BaseActivity implements Animation.AnimationListener{

    //some variables about animation
    private static final float fromAlpha = 0.4f;
    private static final float toAlpha = 1.0f;
    private static final int durationTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init(){
        //set a alpha animation for welcome activity
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(durationTime);
        alphaAnimation.setAnimationListener(this);
        findViewById(R.id.fm_activity_welcome).startAnimation(alphaAnimation);
    }

    /*override the method of AnimationListener__start*/
    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //direct to the MainActivity when the animation is finished
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
    /*override the method of AnimationListener__end*/

}
