package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/6/21.
 * the weather of a day
 */
public class WeatherCondition implements Serializable{

    private int code_d;//weather code of the day
    private int code_n;//weather code of the night
    private String txt_d;//weather type of a day,such as sunny or cloudy
    private String txt_n;//weather type of a night such as sunny or cloudy

    public WeatherCondition(){

    }

    public WeatherCondition(int code_d, int code_n, String txt_d, String txt_n){
        this.code_d = code_d;
        this.code_n = code_n;
        this.txt_d = txt_d;
        this.txt_n = txt_n;
    }

    public int getCode_d() {
        return code_d;
    }
    public void setCode_d(int code_d) {
        this.code_d = code_d;
    }

    public int getCode_n() {
        return code_n;
    }
    public void setCode_n(int code_n) {
        this.code_n = code_n;
    }

    public String getTxt_d() {
        return txt_d;
    }
    public void setTxt_d(String txt_d) {
        this.txt_d = txt_d;
    }

    public String getTxt_n() {
        return txt_n;
    }
    public void setTxt_n(String txt_n) {
        this.txt_n = txt_n;
    }
}
