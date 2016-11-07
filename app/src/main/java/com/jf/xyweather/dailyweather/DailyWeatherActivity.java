package com.jf.xyweather.dailyweather;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.viewpagerindicator.TabPageIndicator;

import java.util.List;

/**
 * Created by jf on 2016/7/5.
 * An Activity to show recent seven-day weather forecast.
 * This Activity will load seven DailyWeatherFragment,every DailyWeatherFragment will show one-day weather forecast
 */
public class DailyWeatherActivity extends BaseActivity{

    /**Key in Intent Object,city's name*/
    public static final String KEY_CITY_NAME = "keyCityName";
    /**Key in Intent Object,seven daily weather*/
    public static final String KEY_SEVEN_DAY_WEATHER = "keySevenDayWeather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);
        init();
    }

    private void init(){
        //Get the title of this Activity
        String cityName = getIntent().getStringExtra(KEY_CITY_NAME);
        if(cityName == null){
            cityName = "";
        }
        ((TextView)findViewById(R.id.tv_daily_weather_city_name)).setText(cityName);
        //Get data from Intent
        List<DailyWeatherForecast> dailyWeatherList = (List<DailyWeatherForecast>)getIntent().getSerializableExtra(KEY_SEVEN_DAY_WEATHER);
        //Initial ViewPager
        ViewPager viewPager = (ViewPager)findViewById(R.id.vp_daily_weather);
        TabPageIndicator tabPageIndicator = (TabPageIndicator)findViewById(R.id.tab_page_indicator_daily_weather);
        viewPager.setAdapter(new DailyWeatherFragmentPagerAdapter(this, getSupportFragmentManager(), dailyWeatherList));
        tabPageIndicator.setViewPager(viewPager);
    }
}
