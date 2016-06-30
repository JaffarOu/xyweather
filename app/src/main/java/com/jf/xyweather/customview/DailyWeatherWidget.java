package com.jf.xyweather.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.model.DailyWeatherForecast;

/**
 * Created by JF on 2016/6/25.
 * daily weather information widget on the "CityWeatherFragment"
 */
public class DailyWeatherWidget extends FrameLayout{

    private TextView whichTv;
    private TextView maxAndMinTemperatureTv;
    private TextView weatherTypeTv;
    private ImageView weatherIconIv;

    private DailyWeatherForecast dailyWeatherForecast;

    public DailyWeatherWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_daily_weather, this);
        //find view
        whichTv = (TextView) findViewById(R.id.tv_layout_daily_weather_which_day);
        maxAndMinTemperatureTv = (TextView)findViewById(R.id.tv_layout_daily_weather_max_and_min_temperature);
        weatherTypeTv = (TextView)findViewById(R.id.tv_layout_daily_weather_weather_type);
        weatherIconIv = (ImageView)findViewById(R.id.iv_layout_daily_weather_weather_icon);
    }

    public void setDailyWeather(DailyWeatherForecast dailyWeatherForecast){
        this.dailyWeatherForecast = dailyWeatherForecast;
        //set the max temperature and the minimum temperature
        maxAndMinTemperatureTv.setText((int)dailyWeatherForecast.getTmp().getMax()+"/"+(int)dailyWeatherForecast.getTmp().getMin()+"℃");
        weatherTypeTv.setText(dailyWeatherForecast.getCond().getTxt_d());
    }

    public void setWhichDay(String whichDay){
        whichTv.setText(whichDay);
    }
}
