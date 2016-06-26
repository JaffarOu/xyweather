package com.jf.xyweather.model;

/**
 * Created by jf on 2016/6/23.
 * The real-time weather information
 */
public class RealTimeWeatherForecast {

//    private RealTimeWeatherCondition cond;
    private WeatherCondition cond;//real-time weather condition
    private float fl;//body feeling temperature
    private float hum;//humidity(%)
    private double pcpn;//precipitation rainfall capacity（降雨量）
    private float pres;//air pressure
    private int tmp;//temperature （℃）
    private float vis;//visibility
    private Wind wind;//wind condition

    public RealTimeWeatherForecast(){

    }

    public RealTimeWeatherForecast(WeatherCondition cond, float fl, float hum, double pcpn, float pres, int tmp, float vis, Wind wind) {
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

    public float getFl() {
        return fl;
    }

    public void setFl(float fl) {
        this.fl = fl;
    }

    public float getHum() {
        return hum;
    }

    public void setHum(float hum) {
        this.hum = hum;
    }

    public double getPcpn() {
        return pcpn;
    }

    public void setPcpn(double pcpn) {
        this.pcpn = pcpn;
    }

    public float getPres() {
        return pres;
    }

    public void setPres(float pres) {
        this.pres = pres;
    }

    public int getTmp() {
        return tmp;
    }

    public void setTmp(int tmp) {
        this.tmp = tmp;
    }

    public float getVis() {
        return vis;
    }

    public void setVis(float vis) {
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
    public class WeatherCondition{
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
