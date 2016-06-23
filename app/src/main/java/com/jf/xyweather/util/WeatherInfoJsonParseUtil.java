package com.jf.xyweather.util;

import com.google.gson.Gson;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.model.AirQualityIndex;
import com.jf.xyweather.model.CityBasicInformation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jf on 2016/6/22.
 * A tool class to parse JSON string that describe weather information
 */
public class WeatherInfoJsonParseUtil {

    private final String weatherInfoJson;
    private JSONObject realJsonObject;

    public WeatherInfoJsonParseUtil(String weatherInfoJson){
        this.weatherInfoJson = weatherInfoJson;
        try{
            realJsonObject = new JSONObject(weatherInfoJson).getJSONArray("HeWeather data service 3.0").getJSONObject(0);
        }catch (JSONException e){
            realJsonObject = null;
            MyApplications.showLog("异常--"+getClass().getSimpleName()+"--构造方法发生异常--"+e.toString());
        }
    }

    public AirQualityIndex getCityAqi(){
        if(weatherInfoJson == null){
            return null;
        }
        AirQualityIndex airQualityIndex = null;
        try{
            JSONObject aqiJsonObject = realJsonObject.getJSONObject("aqi").getJSONObject("city");
            airQualityIndex = new Gson().fromJson(aqiJsonObject.toString(), AirQualityIndex.class);
        }catch (JSONException e){
            MyApplications.showLog("异常--"+getClass().getSimpleName()+"--getCityAqi()方法异常-"+e.toString());
            return null;
        }
        return airQualityIndex;
    }

    public CityBasicInformation getCityBasicInfo(){
        if(weatherInfoJson == null){
            return null;
        }
        CityBasicInformation cityBasicInformation = null;
        try{
            JSONObject aqiJsonObject = realJsonObject.getJSONObject("basic");
            cityBasicInformation = new Gson().fromJson(aqiJsonObject.toString(), CityBasicInformation.class);
        }catch (JSONException e){
            MyApplications.showLog("异常--"+getClass().getSimpleName()+"--getCityAqi()方法异常-"+e.toString());
            return null;
        }
        return cityBasicInformation;
    }
}
