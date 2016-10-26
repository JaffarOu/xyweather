package com.jf.xyweather.dailyweather;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.util.DateUtil;
import com.jf.xyweather.util.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jf on 2016/7/5.
 * Return DailyWeatherFragment for SevenDayWeatherActivity
 */
public class DailyWeatherFragmentPagerAdapter extends FragmentStatePagerAdapter {

    //The member variable show on bottom is used in the DailyWeatherFragment
    private Context mContext;
    private List<DailyWeatherForecast> mDailyWeatherForecastList;

    public DailyWeatherFragmentPagerAdapter(Context context, FragmentManager fm, List<DailyWeatherForecast> dailyWeatherForecastList) {
        super(fm);
        mContext = context;
        mDailyWeatherForecastList = dailyWeatherForecastList;
    }

    @Override
    public int getCount() {
        return mDailyWeatherForecastList == null ? 0 : mDailyWeatherForecastList.size();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(DailyWeatherFragment.KEY_DAILY_WEATHER, mDailyWeatherForecastList.get(position));
        Fragment fragment = DailyWeatherFragment.instantiate(mContext, DailyWeatherFragment.class.getName(), arguments);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return date as title
        String title;
        try{
            title = DateUtil.getWeekFromDate(mDailyWeatherForecastList.get(position).getDate(), "yyyy-MM-dd");
        }catch (ParseException e){
            title = "";
        }
        return title;
    }
}
