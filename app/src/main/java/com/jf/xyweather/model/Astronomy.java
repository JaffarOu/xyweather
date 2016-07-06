package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/6/21.
 * Astronomy numerical（天文指数）
 */
public class Astronomy implements Serializable{

    private String sr;//sunrise time（日出时间）
    private String ss;//sunset time（日落时间）

    public String getSr() {
        return sr;
    }
    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSs() {
        return ss;
    }
    public void setSs(String ss) {
        this.ss = ss;
    }
}
