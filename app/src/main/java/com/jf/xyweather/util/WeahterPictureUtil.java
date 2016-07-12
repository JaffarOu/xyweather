package com.jf.xyweather.util;

import com.jf.xyweather.R;

/**
 * Created by jf on 2016/7/11.
 * Return a id of a picture according weather condition
 */
public final class WeahterPictureUtil {

    //All weather condition that supported by this class
    public static final String HEAVY_RAIN = "大雨";
    public static final String LIGHT_RAIN = "小雨";

    public static final boolean DAY = true;
    public static final boolean NIGHT = false;

    /**
     * The only one method in this class,return a id of a picture according weather condition
     * @param weatherCondition weather condition
     * @return a id of a picture that identify the weather condition
     */
    public static final int getPictureAccordingWeather(boolean dayOrNight, String weatherCondition){
        int pictureResourceId = -1;
        if(dayOrNight){
            if(weatherCondition.equals(HEAVY_RAIN)){
                pictureResourceId = R.drawable.bg_heavy_rain_during_the_day;
            }else if(weatherCondition.equals(LIGHT_RAIN)){
                pictureResourceId = R.drawable.bg_light_rain_during_the_day;
            }
        }else{
            if(weatherCondition.equals(HEAVY_RAIN)){
                pictureResourceId = R.drawable.bg_night_heavy_rain;
            }else if(weatherCondition.equals(LIGHT_RAIN)){
                pictureResourceId = R.drawable.bg_night_light_rain;
            }
        }
        return pictureResourceId;
    }
}
