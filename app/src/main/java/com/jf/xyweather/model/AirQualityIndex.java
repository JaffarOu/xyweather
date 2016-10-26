package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/6/21.
 * The JavaBean of city air quality index（城市空气质量指数的JavaBean）
 */
public class AirQualityIndex implements Serializable{

    private int aqi;        //air quality index（城市空气质量指数）
    private int co;         //average carbon monoxide for an hour（一氧化碳一小时平均值）
    private int no2;        //the average nitrogen dioxide an hour（二氧化氮一小时平均值）
    private int o3;         //average ozone for an hour(臭氧一小时平均值）
    private int pm10;       //average pm10 for an hour
    private int pm25;       //average pm2.5 for an hour
    private String qlty;    //air quality category（空气质量类别）
    private int so2;        //average sulfur dioxide an hour（二氧化硫一小时平均值）

    public AirQualityIndex(){

    }

    public AirQualityIndex(int aqi, int co, int no2, int o3, int pm10, int pm25, String qlty, int so2) {
        this.aqi = aqi;
        this.co = co;
        this.no2 = no2;
        this.o3 = o3;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.qlty = qlty;
        this.so2 = so2;
    }

    public int getAqi() {
        return aqi;
    }
    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public int getCo() {
        return co;
    }
    public void setCo(int co) {
        this.co = co;
    }

    public int getNo2() {
        return no2;
    }
    public void setNo2(int no2) {
        this.no2 = no2;
    }

    public int getO3() {
        return o3;
    }
    public void setO3(int o3) {
        this.o3 = o3;
    }

    public int getPm10() {
        return pm10;
    }
    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public int getPm25() {
        return pm25;
    }
    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public String getQlty() {
        return qlty;
    }
    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public int getSo2() {
        return so2;
    }
    public void setSo2(int so2) {
        this.so2 = so2;
    }
}
