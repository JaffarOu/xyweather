package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/7/3.
 * A JavaBean to describe the information of one city
 * it include various forms of name of one city,and other information
 */
public class CityInfo implements Serializable{

    private String mCityChineseName;    //城市名字汉字形式
    private String mCityPinYinName;     //城市名字拼音形式
    private String mLastUpdateTime;     //最后一次更新天气信息时间

    public CityInfo(){
    }

    public CityInfo(String cityChineseName, String cityPinYinName) {
        mCityChineseName = cityChineseName;
        mCityPinYinName = cityPinYinName;
    }

    public String getCityChineseName() {
        return mCityChineseName;
    }
    public void setCityChineseName(String cityChineseName) {
        mCityChineseName = cityChineseName;
    }

    public String getCityPinYinName() {
        return mCityPinYinName;
    }
    public void setCityPinYinName(String cityPinYinName) {
        mCityPinYinName = cityPinYinName;
    }
}
