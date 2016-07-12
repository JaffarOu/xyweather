package com.jf.xyweather.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.model.DailyWeatherForecast;

/**
 * Created by JF on 2016/6/25.
 * daily weather information widget on the "CityWeatherFragment"
 */
public class DailyWeatherWidget extends RelativeLayout{

    private TextView whichTv;//Which day? Today or tomorrow
    private TextView maxAndMinTemperatureTv;
    private TextView weatherTypeTv;
    private ImageView weatherIconIv;

    public static final String TODAY = "今天";
    public static final String TOMORROW = "明天";

//    private DailyWeatherForecast dailyWeatherForecast;

    public DailyWeatherWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPadding(8, 8, 8, 8);
        inflate(context, R.layout.layout_daily_weather_widget, this);
        //find view
        whichTv = (TextView) findViewById(R.id.tv_layout_daily_weather_which_day);
        maxAndMinTemperatureTv = (TextView)findViewById(R.id.tv_layout_daily_weather_widget_max_and_min_temperature);
        weatherTypeTv = (TextView)findViewById(R.id.tv_layout_daily_weather_widget_weather_type);
        weatherIconIv = (ImageView)findViewById(R.id.iv_layout_daily_weather_widget_weather_icon);
    }

    /**
     * Set daily weather forecast for "DailyWeatherWidget"
     * @param dailyWeatherForecast
     */
    public void setDailyWeather(DailyWeatherForecast dailyWeatherForecast){
        maxAndMinTemperatureTv.setText((int)dailyWeatherForecast.getTmp().getMax()+"/"+(int)dailyWeatherForecast.getTmp().getMin()+"℃");
        weatherTypeTv.setText(dailyWeatherForecast.getCond().getTxt_d());
    }

    public void setWhichDay(String whichDay){
        whichTv.setText(whichDay);
    }
}
