package com.jf.xyweather.model;

/**
 * Created by jf on 2016/6/21.
 * Hourly weather forecast（每小时的天气预报）
 */
public class HourlyWeatherForecast {

    private String date;//current date and time
    private float hum;//humidity
    private float pop;//probability of precipitation（降水概率）
    private float pres;//air pressure
    private float tmp;//temperature(degree centigrade)（天气--摄氏度）
    private Wind wind;//the state of wind

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public float getHum() {
        return hum;
    }
    public void setHum(float hum) {
        this.hum = hum;
    }

    public float getPop() {
        return pop;
    }
    public void setPop(float pop) {
        this.pop = pop;
    }

    public float getPres() {
        return pres;
    }
    public void setPres(float pres) {
        this.pres = pres;
    }

    public float getTmp() {
        return tmp;
    }
    public void setTmp(float tmp) {
        this.tmp = tmp;
    }

    public Wind getWind() {
        return wind;
    }
    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
