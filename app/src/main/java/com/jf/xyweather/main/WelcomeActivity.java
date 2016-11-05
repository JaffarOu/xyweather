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
import com.jf.xyweather.citymanage.GetSelectedCityThread;
import com.jf.xyweather.cityweather.CityWeatherActivity;
import com.jf.xyweather.model.SelectedCity;
import com.jf.xyweather.util.LocationUtil;
import com.jf.xyweather.util.LogUtil;
import com.jf.xyweather.util.SelectedCityHelper;
import com.jf.xyweather.util.ToastUtil;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/20.
 * 主模块-欢迎界面
 */
public class WelcomeActivity extends BaseActivity
        implements Animation.AnimationListener, AMapLocationListener{

    private LocationUtil mLocationUtil;
    private String locationCityName;
    private List<SelectedCity> mSelectedCityList;
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init(){
        myHandler = new MyHandler(this);
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

        //Get all selected cities from database
        new GetSelectedCityThread(myHandler).getSelectedCityList();
    }

    /*override the method of AnimationListener__start*/
    @Override
    public void onAnimationStart(Animation animation) {
        //Nothing to do
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        myHandler.sendEmptyMessage(MyHandler.WHAT_ANIMATION_FINISH);
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
                locationCityName = aMapLocation.getCity();
                myHandler.sendEmptyMessage(MyHandler.WHAT_LOCATION_FINISH);
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                LogUtil.i("location Error, ErrCode:" +
                        aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                ToastUtil.showShortToast(this, "定位失败，应用将退出");
                myHandler.sendEmptyMessageDelayed(MyHandler.WHAT_LOCATION_FAIL, 2000);
            }
        }
    }
    /*override the method of AnimationListener__end*/

    private void onAllFinished(){
        if(locationCityName == null) {
            ToastUtil.showLongToast(this, "定位失败，应用将退出");
            finish();
        }
        //删除城市名字的“市”字
        locationCityName = locationCityName.substring(0, locationCityName.length()-1);
        List<SelectedCity> cityList = new ArrayList<>();
        cityList.add(new SelectedCity(locationCityName, ""));
        if(mSelectedCityList != null && mSelectedCityList.size() != 0) {
            cityList.addAll(mSelectedCityList);
        }
        Intent intent = new Intent(WelcomeActivity.this, CityWeatherActivity.class);
        intent.putExtra(CityWeatherActivity.KEY_SELECTED_CITY_LIST, (Serializable)cityList);
        startActivity(intent);
        finish();
    }

    private static class MyHandler extends Handler{

        private final WeakReference<WelcomeActivity> mWeakReference;
        //Variable "what" in Handler
        private static final int WHAT_ANIMATION_FINISH = 1<<3;
        private static final int WHAT_LOCATION_FINISH = 1<<4;
        private static final int WHAT_LOCATION_FAIL = 1<<5;

        private boolean isAnimationFinish = false;
        private boolean isLocationFinish = false;
        private boolean isSelectedCityListFinish = false;

        public MyHandler(WelcomeActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WelcomeActivity activity = mWeakReference.get();
            if(activity == null){
                super.handleMessage(msg);
                return;
            }
            if(msg.what == WHAT_ANIMATION_FINISH){
                //Set the flag that animation is finished
                isAnimationFinish = true;
            }else if(msg.what == WHAT_LOCATION_FINISH){
                //Set the flag that location is finished and destroy the location Service
                isLocationFinish = true;
            }else if(msg.what == GetSelectedCityThread.WHAT_SELECTED_CITY_LIST){
                isSelectedCityListFinish = true;
                activity.mSelectedCityList = (List<SelectedCity>)msg.obj;
            }else if(msg.what == WHAT_LOCATION_FAIL){
                activity.finish();
            }
            //If both animation and location ard finished,start CityWeatherActivity and send it the city name
            if(isAnimationFinish && isLocationFinish && isSelectedCityListFinish){
                activity.onAllFinished();
            }
        }
    }
}
