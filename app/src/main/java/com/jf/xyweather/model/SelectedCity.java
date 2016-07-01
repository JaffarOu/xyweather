package com.jf.xyweather.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by jf on 2016/7/1.
 * This is a java bean used as an item of ListView in SelectedCityActivity
 */
public class SelectedCity implements Serializable{

    private String cityWeatherCondition;
    private String cityName;
    private String temperature;

    public SelectedCity() {
    }

    public SelectedCity(String cityWeatherCondition, String cityName, String temperature) {
        this.cityWeatherCondition = cityWeatherCondition;
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public String getCityWeatherCondition() {
        return cityWeatherCondition;
    }
    public void setCityWeatherCondition(String cityWeatherCondition) {
        this.cityWeatherCondition = cityWeatherCondition;
    }

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
