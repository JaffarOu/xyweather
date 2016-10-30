package com.jf.xyweather.util;

import com.jf.xyweather.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JF on 2016/10/26.
 * An util return resource id according weather code
 */
public class WeatherIconUtil {

    //The first Integer is weather code and the second is resource id
    private static Map<Integer, Integer> sWeatherIconMap;

    //Initial the map
    static {
        sWeatherIconMap = new HashMap<>(20);
        sWeatherIconMap.put(100, R.mipmap.icon_weather_condition_100);
        sWeatherIconMap.put(101, R.mipmap.icon_weather_condition_101);
        sWeatherIconMap.put(102, R.mipmap.icon_weather_condition_102);
        sWeatherIconMap.put(103, R.mipmap.icon_weather_condition_103);
        sWeatherIconMap.put(104, R.mipmap.icon_weather_condition_104);
        sWeatherIconMap.put(200, R.mipmap.icon_weather_condition_200);
        sWeatherIconMap.put(201, R.mipmap.icon_weather_condition_201);
        sWeatherIconMap.put(202, R.mipmap.icon_weather_condition_202);
        sWeatherIconMap.put(203, R.mipmap.icon_weather_condition_203);
        sWeatherIconMap.put(204, R.mipmap.icon_weather_condition_204);
        sWeatherIconMap.put(205, R.mipmap.icon_weather_condition_205);
        sWeatherIconMap.put(206, R.mipmap.icon_weather_condition_206);
        sWeatherIconMap.put(207, R.mipmap.icon_weather_condition_207);
        sWeatherIconMap.put(208, R.mipmap.icon_weather_condition_208);
        sWeatherIconMap.put(209, R.mipmap.icon_weather_condition_209);
        sWeatherIconMap.put(210, R.mipmap.icon_weather_condition_210);
        sWeatherIconMap.put(211, R.mipmap.icon_weather_condition_211);
        sWeatherIconMap.put(212, R.mipmap.icon_weather_condition_212);
        sWeatherIconMap.put(213, R.mipmap.icon_weather_condition_213);
    }

    private WeatherIconUtil(){
        throw new UnsupportedOperationException("WeatherIconUtil is just a tool class and it can't be create Object");
    }

    /**
     * Get the resource id of weather picture according code of weather
     * @param weatherCode code of weather
     * @return resource id of weather picture
     */
    public static int getResourceAccordingCode(int weatherCode){
        if(sWeatherIconMap.containsKey(weatherCode)){
            return sWeatherIconMap.get(weatherCode);
        }
        //Use R.mipmap.icon_weather_condition_100 as the default icon
        return R.mipmap.icon_weather_condition_100;
    }
}
