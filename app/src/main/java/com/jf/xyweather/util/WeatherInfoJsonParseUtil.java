package com.jf.xyweather.util;

import com.google.gson.Gson;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.model.AirQualityIndex;
import com.jf.xyweather.model.CityBasicInformation;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.model.RealTimeWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/22.
 * A tool class to parse JSON string that describe weather information
 */
public class WeatherInfoJsonParseUtil {

//    private String weatherInfoJson;//the json string that will be parse
    private JSONObject realJSONObject;
    private String status = "";//let the status never equals null

    public WeatherInfoJsonParseUtil(String weatherInfoJson){
        if(weatherInfoJson == null){
//            this.weatherInfoJson = null;
            return;
        }
        try{
            //get the valid JSON string from the "weatherInfoJson"
            realJSONObject = new JSONObject(weatherInfoJson).getJSONArray("HeWeather data service 3.0").getJSONObject(0);
            //the "status" will equals the status that from JSON string,or it will equals "" char
            status = realJSONObject.getString("status");
        }catch (JSONException e) {
            realJSONObject = null;
            MyApplications.showLog("异常--" + getClass().getSimpleName() + "--构造方法发生异常--" + e.toString());
        }
    }

    /**
     * get the status code from the He Feng web server
     * @return the status code from He Feng web server to describe the result of our request
     *          （用来描述我们的请求结果的状态码）
     */
    public String getStatus(){
        return status;
    }

    /**
     * get urban air quality index（获取城市空气质量指数）
     * @return AirQualityIndex object that describe the urban air quality index,null if the json parse has error
     *          （返回空气质量指数对象，如果JSON解析出错，将返回null）
     */
    public AirQualityIndex getAirQualityIndex(){
        if( !status.equals(Contact.OK) ){
            return null;
        }
        AirQualityIndex airQualityIndex = null;
        try{
            JSONObject aqiJsonObject = realJSONObject.getJSONObject("aqi").getJSONObject("city");
            airQualityIndex = new Gson().fromJson(aqiJsonObject.toString(), AirQualityIndex.class);
        }catch (JSONException e){
            MyApplications.showLog(getClass().getSimpleName()+"--getCityAqi()方法异常-"+e.toString());
            return null;
        }
        return airQualityIndex;
    }

    public CityBasicInformation getCityBasicInfo(){
        if( !status.equals(Contact.OK) ){
            return null;
        }
        CityBasicInformation cityBasicInformation = null;
        try{
            JSONObject aqiJsonObject = realJSONObject.getJSONObject("basic");
            cityBasicInformation = new Gson().fromJson(aqiJsonObject.toString(), CityBasicInformation.class);
        }catch (JSONException e){
            MyApplications.showLog("异常--"+getClass().getSimpleName()+"--getCityBasicInfo()方法异常-"+e.toString());
            return null;
        }
        return cityBasicInformation;
    }

    public RealTimeWeather getRealTimeWeather(){
        if( !status.equals(Contact.OK) ){
            return null;
        }
        RealTimeWeather realTimeWeather = null;
        try{
            JSONObject aqiJsonObject = realJSONObject.getJSONObject("now");
            realTimeWeather = new Gson().fromJson(aqiJsonObject.toString(), RealTimeWeather.class);
        }catch (JSONException e){
            MyApplications.showLog("异常--"+getClass().getSimpleName()+"--getRealTimeWeather()方法异常-"+e.toString());
            return null;
        }
        return realTimeWeather;
    }

    public List<DailyWeatherForecast> getDailyWeatherForecast(){
        if( !status.equals(Contact.OK) ){
            return null;
        }
        List<DailyWeatherForecast> dailyWeatherForecastList = null;
        try{
            JSONArray jsonArray = realJSONObject.getJSONArray("daily_forecast");
            int length = jsonArray.length();
            dailyWeatherForecastList = new ArrayList<>(length);
            Gson gson = new Gson();
            for(int i = 0; i<length; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                dailyWeatherForecastList.add(gson.fromJson(jsonObject.toString(), DailyWeatherForecast.class));
            }
        }catch (JSONException e){
            MyApplications.showLog("异常--"+getClass().getSimpleName()+"--\"getDailyWeatherForecast\"方法");
            dailyWeatherForecastList = null;
        }
        return dailyWeatherForecastList;
    }

}
