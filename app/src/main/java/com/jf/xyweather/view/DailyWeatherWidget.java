package com.jf.xyweather.view;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.model.Temperature;
import com.jf.xyweather.model.WeatherCondition;
import com.jf.xyweather.util.DateUtil;
import com.jf.xyweather.util.LogUtil;
import com.jf.xyweather.util.WeatherIconUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by JF on 2016/6/25.
 * daily weather information widget on the "CityWeatherFragment"
 */
public class DailyWeatherWidget extends LinearLayout{

    private TextView mWeekTv;//Which day? Today or tomorrow
    private TextView mMaxAndMinTemperatureTv;
    private TextView mWeatherCondition;
    private ImageView mWeatherIconIv;

    public DailyWeatherWidget(Context context){
        super(context);
        init();
    }

    public DailyWeatherWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //Set attributes
    private void init(){
        setPadding(8, 8, 8, 8);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        //Initial view
        inflate(getContext(), R.layout.layout_daily_weather_widget, this);
        mWeatherIconIv = (ImageView)findViewById(R.id.iv_layout_daily_weather_widget_weather_icon);
        mWeatherCondition = (TextView) findViewById(R.id.tv_daily_weather_widget_weather_condition);
        mMaxAndMinTemperatureTv = (TextView)findViewById(R.id.tv_daily_weather_widget_max_and_min_temperature);
        mWeekTv = (TextView)findViewById(R.id.tv_daily_weather_widget_weather_week);
    }

    /**
     * Set daily weather forecast for "DailyWeatherWidget"
     * @param dailyWeatherForecast
     */
    public void setDailyWeather(DailyWeatherForecast dailyWeatherForecast){
        WeatherCondition weatherCondition = dailyWeatherForecast.getCond();
        mWeatherIconIv.setImageResource(WeatherIconUtil.getResourceAccordingCode(weatherCondition.getCode_d()));
        mWeatherCondition.setText(weatherCondition.getTxt_d());
        Temperature temperature = dailyWeatherForecast.getTmp();
        mMaxAndMinTemperatureTv.setText(temperature.getMax()+"/"+temperature.getMin()+"℃");
        String week;
        try{
            week = DateUtil.getWeekFromDate(dailyWeatherForecast.getDate(), "yyyy-MM-dd");
        }catch (ParseException e){
            week = "";
            LogUtil.i(DailyWeatherWidget.class.getName()+"--日期解析发生异常");
        }
        mWeekTv.setText(week);
    }

}
