package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/6/21.
 * The JavaBean of city air quality index（城市空气质量指数的JavaBean）
 */
public class AirQualityIndex implements Serializable{

    private String aqi;        //air quality index（城市空气质量指数）
    private String co;         //average carbon monoxide for an hour（一氧化碳一小时平均值）
    private String no2;        //the average nitrogen dioxide an hour（二氧化氮一小时平均值）
    private String o3;         //average ozone for an hour(臭氧一小时平均值）
    private String pm10;       //average pm10 for an hour
    private String pm25;       //average pm2.5 for an hour
    private String qlty;    //air quality category（空气质量类别）
    private String so2;        //average sulfur dioxide an hour（二氧化硫一小时平均值）

    public AirQualityIndex(){

    }

    public AirQualityIndex(String aqi, String co, String no2, String o3, String pm10, String pm25, String qlty, String so2) {
        this.aqi = aqi;
        this.co = co;
        this.no2 = no2;
        this.o3 = o3;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.qlty = qlty;
        this.so2 = so2;
    }

    public String getAqi() {
        return aqi;
    }
    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCo() {
        return co;
    }
    public void setCo(String co) {
        this.co = co;
    }

    public String getNo2() {
        return no2;
    }
    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }
    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPm10() {
        return pm10;
    }
    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }
    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQlty() {
        return qlty;
    }
    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getSo2() {
        return so2;
    }
    public void setSo2(String so2) {
        this.so2 = so2;
    }
}
