package com.jf.xyweather.model;

/**
 * Created by jf on 2016/6/21.
 * Daily weather forecast（一天的天气预报）
 */
public class DailyWeatherForecast {

    private Astronomy astro;//_astronomy numerical（天文数值）
    private WeatherCondition cond;//weather state（天气状况）
    private String date;//location date（当地时间）
    private float hum;//humidity（湿度）
    private float pcpn;//precipitation rainfall capacity（降雨量）
    private float pop;//the probability of precipitation（降水概率）
    private float pres;//air pressure
    private Temperature tmp;//temperature
    private int vis;//visibility（能见度）
    private Wind wind;//The state of the wind

    public DailyWeatherForecast(){

    }

    public DailyWeatherForecast(Astronomy astro, WeatherCondition cond, String date, float hum, float pcpn, float pop, float pres, Temperature tmp, int vis, Wind wind) {
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

    public float getHum() {
        return hum;
    }
    public void setHum(float hum) {
        this.hum = hum;
    }

    public float getPcpn() {
        return pcpn;
    }
    public void setPcpn(float pcpn) {
        this.pcpn = pcpn;
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

    public Temperature getTmp() {
        return tmp;
    }
    public void setTmp(Temperature tmp) {
        this.tmp = tmp;
    }

    public int getVis() {
        return vis;
    }
    public void setVis(int vis) {
        this.vis = vis;
    }

    public Wind getWind() {
        return wind;
    }
    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
