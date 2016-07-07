package com.jf.xyweather.dailyweather;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.activity.BaseViewPagerActivity;
import com.jf.xyweather.baseadapter.BaseViewPagerAdapter;
import com.jf.xyweather.customview.CustomTitles;
import com.jf.xyweather.model.DailyWeatherForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/7/5.
 * An Activity to show recent seven-day weather forecast.
 * This Activity will load seven DailyWeatherFragment,every DailyWeatherFragment will show one-day weather forecast
 */
public class SevenDayWeatherActivity extends BaseViewPagerActivity implements CustomTitles.OnTitleClickListener{

    //key of the name of city from Intent
    public static final String KEY_CITY_NAME = "keyCityName";
    public static final String KEY_SEVEN_DAY_WEATHER = "keySevenDayWeather";

    //name of city that showed on title
    private String cityName = "";
    private List<DailyWeatherForecast> dailyWeatherList;

    @Override
    protected void initExtra() {
        super.initExtra();
        //Get the title of this Activity
        cityName = getIntent().getStringExtra(KEY_CITY_NAME);
        //Get data from Intent
        dailyWeatherList = (List<DailyWeatherForecast>)getIntent().getSerializableExtra(KEY_SEVEN_DAY_WEATHER);
    }

    @Override
    protected void initView() {
        super.initView();
        //initial title
        customTitles.setImageViewResource(CustomTitles.LEFT_FIRST, R.drawable.ic_return);
        customTitles.setTitleText(cityName);
        customTitles.setOnTitleClickListener(this);

        //set background
        rootView.setBackgroundResource(R.color.ff00aaff);

    }

    @Override
    protected BaseViewPagerAdapter getViewPagerAdapter() {
        //initial all fragments
        int size = dailyWeatherList.size();
        List<Fragment> fragmentList = new ArrayList<>(size);
        DailyWeatherFragment dailyWeatherFragment;
        Bundle arguments;
        for(int i = 0; i<size; i++){
            arguments = new Bundle();
            arguments.putSerializable(DailyWeatherFragment.KEY_DAILY_WEATHER, dailyWeatherList.get(i));
            dailyWeatherFragment = new DailyWeatherFragment();
            dailyWeatherFragment.setArguments(arguments);
            fragmentList.add(dailyWeatherFragment);
        }
        String[] dateString = new String[size];
        for(int i = 0; i<size; i++){
            dateString[i] = dailyWeatherList.get(i).getDate();
        }
//        return new BaseViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        return new SevenDayPagerAdapter(getSupportFragmentManager(), fragmentList, dateString);
    }

    /*override the method of OnTitleClickListener*/
    @Override
    public void onTitleClick(View view, int which) {
        finish();
    }

}
