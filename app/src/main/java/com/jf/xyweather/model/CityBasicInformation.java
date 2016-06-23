package com.jf.xyweather.model;

/**
 * Created by jf on 2016/6/21.
 * The basic information of the city
 */
public class CityBasicInformation {

    private String city;//city's name（城市名字）
    private String cnty;//country's name（城市所在国家名字）
    private String id;//city's id（城市ID）
    private double lat;//the latitude of the city（纬度）
    private double lon;//the longitude of the city（经度）
    private Update update;//the time of the data update

    public CityBasicInformation(){

    }

    public CityBasicInformation(String city, String cnty, String id, double lat, double lon, Update update) {
        this.city = city;
        this.cnty = cnty;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.update = update;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }
    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }

    public Update getUpdate() {
        return update;
    }
    public void setUpdate(Update update) {
        this.update = update;
    }
}
