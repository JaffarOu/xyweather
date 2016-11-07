package com.jf.xyweather.model;

/**
 * Created by JF on 2016/10/31.
 * A JavaBean corresponding to the JsonObject from He Feng Weather
 */
public class HeFengWeatherCityInfo {

    private String city;    //name of city
    private String cnty;    //name of country that city belong to
    private String id;      //id of city
    private String lat;     //latitude（经度）
    private String lon;     //longitude（纬度）
    private String prov;    //name of province that city belong to

    public HeFengWeatherCityInfo(){
    }

    public HeFengWeatherCityInfo(String city, String cnty, String id, String lat, String lon, String prov) {
        this.city = city;
        this.cnty = cnty;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.prov = prov;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty == null ? "" : cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getProv() {
        return prov == null ? "" : prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }
}
