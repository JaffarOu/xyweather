package com.jf.xyweather.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.cityweather.CityWeatherActivity;
import com.jf.xyweather.model.CityInfo;
import com.jf.xyweather.util.LocationUtil;
import com.jf.xyweather.util.LogUtil;
import com.jf.xyweather.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/20.
 * 主模块-欢迎界面
 */
public class WelcomeActivity extends BaseActivity
        implements Animation.AnimationListener, AMapLocationListener{

    //Variable "what" in Handler
    private static final int WHAT_ANIMATION_FINISH = 1;
    private static final int WHAT_LOCATION_FINISH = 2;

    //Flag used judge if animation and location are finished
    private boolean isAnimationFinish = false;
    private boolean isLocationFinish = false;

    private LocationUtil mLocationUtil;
    private String cityName;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == WHAT_ANIMATION_FINISH){
                //Set the flag that animation is finished
                isAnimationFinish = true;
            }else if(msg.what == WHAT_LOCATION_FINISH){
                //Set the flag that location is finished and destroy the location Service
                isLocationFinish = true;
                mLocationUtil.onDestroy();
            }
            //If both animation and location ard finished,start CityWeatherActivity and send it the city name
            if(isAnimationFinish && isLocationFinish){
                //If cityName != null means the location service is successful
                if(cityName != null){
                    //删除城市名字的“市”字
                    cityName = cityName.substring(0, cityName.length()-1);
                    List<CityInfo> cityInfoList = new ArrayList<>();
                    cityInfoList.add(new CityInfo(cityName, cityName));
                    Intent intent = new Intent(WelcomeActivity.this, CityWeatherActivity.class);
                    intent.putExtra(CityWeatherActivity.KEY_CITY_INFO_LIST, (Serializable)cityInfoList);
                    startActivity(intent);
                    finish();
                }else{
                    ToastUtil.showLongToast(WelcomeActivity.this, "定位失败，应用将退出");
                    finish();
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init(){
        //Start a alpha animation for welcome activity
        float fromAlpha = 0.4f;
        float toAlpha = 1.0f;
        int duration = 2000;
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setAnimationListener(this);
        findViewById(R.id.fm_activity_welcome).startAnimation(alphaAnimation);

        //Get location
        mLocationUtil = new LocationUtil();
        mLocationUtil.setLocationListener(this);
        mLocationUtil.startLocation();
    }

    /*override the method of AnimationListener__start*/
    @Override
    public void onAnimationStart(Animation animation) {
        //Nothing to do
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        handler.sendEmptyMessage(WHAT_ANIMATION_FINISH);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //Nothing to do
    }
    /*override the method of AnimationListener__end*/

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析aMapLocation获取相应内容。
                cityName = aMapLocation.getCity();
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                LogUtil.i("location Error, ErrCode:" +
                        aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
        handler.sendEmptyMessage(WHAT_LOCATION_FINISH);
    }
    /*override the method of AnimationListener__end*/

}
