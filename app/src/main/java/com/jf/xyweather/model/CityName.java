package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/7/3.
 * A JavaBean to describe the name of one city
 * it include various forms of name of one city
 */
public class CityName implements Serializable{

    private String cityChineseName;//城市名字汉字形式
    private String cityPinYinName;//城市名字拼音形式

    public CityName(){

    }

    public CityName(String cityChineseName, String cityPinYinName) {
        this.cityChineseName = cityChineseName;
        this.cityPinYinName = cityPinYinName;
    }

    public String getCityChineseName() {
        return cityChineseName;
    }
    public void setCityChineseName(String cityChineseName) {
        this.cityChineseName = cityChineseName;
    }

    public String getCityPinYinName() {
        return cityPinYinName;
    }
    public void setCityPinYinName(String cityPinYinName) {
        this.cityPinYinName = cityPinYinName;
    }
}
