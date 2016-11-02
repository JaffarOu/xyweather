package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/7/3.
 * A JavaBean to describe the information of city that selected by user
 * it include various forms of name of one city,and other information
 */
public class SelectedCity implements Serializable{

    private String mCityChineseName;    //城市名字汉字形式
    private String mLastUpdateWeatherTime;     //最后一次更新天气信息时间

    public SelectedCity(){
    }

    public SelectedCity(String cityName, String lastUpdateWeatherInfoTime){
        mCityChineseName = cityName;
        mLastUpdateWeatherTime = lastUpdateWeatherInfoTime;
    }

    public String getCityName() {
        return mCityChineseName;
    }
    public void setCityName(String cityName) {
        mCityChineseName = cityName;
    }

    public String getLastUpdateWeatherTime() {
        return mLastUpdateWeatherTime;
    }

    public void setLastUpdateWeatherTime(String mLastUpdateWeatherTime) {
        this.mLastUpdateWeatherTime = mLastUpdateWeatherTime;
    }
}
