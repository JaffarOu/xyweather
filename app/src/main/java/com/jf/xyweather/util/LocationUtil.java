package com.jf.xyweather.util;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jf.xyweather.base.MyApplications;

/**
 * Created by JF on 2016/10/30.
 * A tool used provide the location service
 */
public class LocationUtil {

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient;

    /**
     * Create and initial an location service tool
     */
    public LocationUtil(){
        //初始化定位客户端对象，选项对象
        mLocationClient = new AMapLocationClient(MyApplications.getContext());
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置选项
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    /**
     * Set a listener to receive callback after location finish
     * @param listener A class that implements AMapLocationListener interface
     */
    public void setLocationListener(AMapLocationListener listener){
        mLocationClient.setLocationListener(listener);
    }

    /**
     * Start location service
     */
    public void startLocation(){
        mLocationClient.startLocation();
    }

    /**
     * Destroy location service
     */
    public void onDestroy(){
        mLocationClient.onDestroy();
    }
}
