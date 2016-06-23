package com.jf.xyweather.model;

/**
 * Created by jf on 2016/6/23.
 * The real-time weather condition
 */
public class RealTimeWeatherCondition {

    private int code;
    private String txt;

    public RealTimeWeatherCondition(){

    }

    public RealTimeWeatherCondition(int code, String txt) {
        this.code = code;
        this.txt = txt;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
