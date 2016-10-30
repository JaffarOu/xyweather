package com.jf.xyweather.util;

import com.google.gson.Gson;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.model.AirQualityIndex;
import com.jf.xyweather.model.CityBasicInformation;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.model.LifeSuggestion;
import com.jf.xyweather.model.RealTimeWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/22.
 * A tool class to parse JSON string that describe weather information from He Feng weather
 */
public class WeatherInfoJsonParseUtil {

//    private String weatherInfoJson;//the json string that will be parse
    private JSONObject mRealJsonObject;
    private String status = "";//let the status never equals null

    public WeatherInfoJsonParseUtil(String weatherInfoJson){
        if(weatherInfoJson == null) return;
        try{
            //get the valid JSON string from the "weatherInfoJson"
            mRealJsonObject = new JSONObject(weatherInfoJson).getJSONArray("HeWeather data service 3.0").getJSONObject(0);
            //the "status" will equals the status that from JSON string,or it will equals "" char
            status = mRealJsonObject.getString("status");
        }catch (JSONException e) {
            mRealJsonObject = null;
            LogUtil.i(getClass().getSimpleName() + "--构造方法发生异常--" + e.toString());
        }
    }

    /**
     * Get the status code that used described the result of our request
     * @return (1)ok：接口正常、(2)invalid key：错误的用户key、 (3)unknown city：未知城市
     *          (4)no more requests：超过访问次数、(5)anr：服务无响应或超时、(6)permission denied：没有访问权限
     *          (7)""：其他
     */
    public String getStatus(){
        return status;
    }

    /**
     * get air quality index of city（获取城市空气质量指数）
     * @return AirQualityIndex Object that describe the air quality index of city,null if the json parse has error
     *          （返回空气质量指数对象，如果JSON解析出错，将返回null）
     */
    public AirQualityIndex getAirQualityIndex(){
        if( !status.equals(WeatherContact.OK) ) return null;
        AirQualityIndex airQualityIndex = null;
        try{
            JSONObject aqiJsonObject = mRealJsonObject.getJSONObject("aqi").getJSONObject("city");
            airQualityIndex = new Gson().fromJson(aqiJsonObject.toString(), AirQualityIndex.class);
        }catch (JSONException e){
            MyApplications.showLog(getClass().getSimpleName()+"--getCityAqi()方法异常-"+e.toString());
            return null;
        }
        return airQualityIndex;
    }

    public CityBasicInformation getCityBasicInfo(){
        if( !status.equals(WeatherContact.OK) ) return null;
        CityBasicInformation cityBasicInformation;
        try{
            JSONObject aqiJsonObject = mRealJsonObject.getJSONObject("basic");
            cityBasicInformation = new Gson().fromJson(aqiJsonObject.toString(), CityBasicInformation.class);
        }catch (JSONException e){
            MyApplications.showLog(getClass().getSimpleName()+"--getCityBasicInfo()方法异常-"+e.toString());
            return null;
        }
        return cityBasicInformation;
    }

    public RealTimeWeather getRealTimeWeather(){
        if( !status.equals(WeatherContact.OK) ) return null;
        RealTimeWeather realTimeWeather;
        try{
            JSONObject aqiJsonObject = mRealJsonObject.getJSONObject("now");
            realTimeWeather = new Gson().fromJson(aqiJsonObject.toString(), RealTimeWeather.class);
        }catch (JSONException e){
            MyApplications.showLog("异常--"+getClass().getSimpleName()+"--getRealTimeWeather()方法异常-"+e.toString());
            return null;
        }
        return realTimeWeather;
    }

    public List<DailyWeatherForecast> getDailyWeatherForecast(){
        if( !status.equals(WeatherContact.OK) ) return null;
        List<DailyWeatherForecast> dailyWeatherForecastList;
        try{
            JSONArray jsonArray = mRealJsonObject.getJSONArray("daily_forecast");
            int length = jsonArray.length();
            dailyWeatherForecastList = new ArrayList<>(length);
            Gson gson = new Gson();
            for(int i = 0; i<length; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                dailyWeatherForecastList.add(gson.fromJson(jsonObject.toString(), DailyWeatherForecast.class));
            }
        }catch (JSONException e){
            LogUtil.i(getClass().getSimpleName()+"--getDailyWeatherForecast()方法异常-"+e.toString());
            dailyWeatherForecastList = null;
        }
        return dailyWeatherForecastList;
    }

    /**
     * Get suggestion of life
     * @return
     */
    public LifeSuggestion getLifeSuggestion(){
        if( !status.equals(WeatherContact.OK) ) return null;
        LifeSuggestion lifeSuggestion;
        try{
            JSONObject jsonObject = mRealJsonObject.getJSONObject("suggestion");
            lifeSuggestion = new Gson().fromJson(jsonObject.toString(), LifeSuggestion.class);
        }catch (JSONException e){
            LogUtil.i(getClass().getSimpleName()+"--getLifeSuggestion()方法异常"+e.toString());
            lifeSuggestion = null;
        }
        return lifeSuggestion;
    }
}
