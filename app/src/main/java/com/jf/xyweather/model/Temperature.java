package com.jf.xyweather.model;

import java.io.Serializable;

/**
 * Created by jf on 2016/6/21.
 * The max temperature and the min temperature
 */
public class Temperature implements Serializable{

    private String max;
    private String min;

    public String getMax() {
        return max;
    }
    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }
    public void setMin(String min) {
        this.min = min;
    }
}
