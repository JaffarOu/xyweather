package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/6/21.
 * The state of the wind
 */
public class Wind implements Serializable{

    private String deg;//degree
    private String dir;//wind direction
    private String sc;//wind power
    private String spd;//wind speed

    public String getDeg() {
        return deg;
    }
    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getDir() {
        return dir;
    }
    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSc() {
        return sc;
    }
    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSpd() {
        return spd;
    }
    public void setSpd(String spd) {
        this.spd = spd;
    }
}
