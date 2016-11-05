package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/6/23.
 * The real-time weather information
 */
public class RealTimeWeather implements Serializable{

//    private RealTimeWeatherCondition cond;
    private WeatherCondition cond;//real-time weather condition
    private String fl;//body feeling temperature
    private String hum;//humidity(%)
    private String pcpn;//precipitation rainfall capacity（mm）
    private String pres;//air pressure
    private String tmp;//temperature （℃）
    private String vis;//visibility
    private Wind wind;//wind condition

    public RealTimeWeather(){

    }

    public RealTimeWeather(WeatherCondition cond, String fl, String hum, String pcpn, String pres, String tmp, String vis, Wind wind) {
        this.cond = cond;
        this.fl = fl;
        this.hum = hum;
        this.pcpn = pcpn;
        this.pres = pres;
        this.tmp = tmp;
        this.vis = vis;
        this.wind = wind;
    }

    public WeatherCondition getCond() {
        return cond;
    }

    public void setCond(WeatherCondition cond) {
        this.cond = cond;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * real-time weather condition
     */
    public class WeatherCondition implements Serializable{
        private int code;//weather code that from the He Feng web server
        private String txt;//weather type

        public WeatherCondition(){
        }

        public WeatherCondition(int code, String txt) {
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
}
