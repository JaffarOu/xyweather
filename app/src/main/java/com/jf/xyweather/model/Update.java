package com.jf.xyweather.model;

/**
 * Created by jf on 2016/6/21.
 */
public class Update {

    private String loc;//the _location time of the data update（数据更新的当地时间）
    private String utc;//the UTC time of the data update（数据更新的UTC时间）

    public String getLoc() {
        return loc;
    }
    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }
    public void setUtc(String utc) {
        this.utc = utc;
    }
}
