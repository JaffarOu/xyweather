package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/6/21.
 * Daily weather forecast（一天的天气预报）
 */
public class DailyWeatherForecast implements Serializable{

    private Astronomy astro;            //_astronomy numerical（天文数值）
    private WeatherCondition cond;      //weather condition（天气状况）
    private String date;                //_location date（当地时间）
    private String hum;                  //humidity（湿度）
    private String pcpn;                 //precipitation rainfall capacity（降雨量）
    private String pop;                  //the probability of precipitation（降水概率）
    private String pres;                 //air pressure
    private Temperature tmp;            //temperature
    private String vis;                    //visibility（能见度）
    private Wind wind;                  //The state of the wind

    public DailyWeatherForecast(){

    }

    public DailyWeatherForecast(Astronomy astro, WeatherCondition cond, String date, String hum, String pcpn, String pop, String pres, Temperature tmp, String vis, Wind wind) {
        this.astro = astro;
        this.cond = cond;
        this.date = date;
        this.hum = hum;
        this.pcpn = pcpn;
        this.pop = pop;
        this.pres = pres;
        this.tmp = tmp;
        this.vis = vis;
        this.wind = wind;
    }

    public Astronomy getAstro() {
        return astro;
    }
    public void setAstro(Astronomy astro) {
        this.astro = astro;
    }

    public WeatherCondition getCond() {
        return cond;
    }
    public void setCond(WeatherCondition cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
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

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }
    public void setPres(String pres) {
        this.pres = pres;
    }

    public Temperature getTmp() {
        return tmp;
    }
    public void setTmp(Temperature tmp) {
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
}
