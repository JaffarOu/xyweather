package com.jf.xyweather.dailyweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.baseadapter.BaseViewPagerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jf on 2016/7/5.
 * Used as adapter of ViewPager in SevenDayWeatherActivity
 */
public class SevenDayPagerAdapter extends BaseViewPagerAdapter {

    private String[] date;
    public SevenDayPagerAdapter(FragmentManager fm, List<Fragment> fragmentsList, String[] date){
        super(fm, fragmentsList);
        this.date = date;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return date as title
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try{
            date = simpleDateFormat.parse(this.date[position]);
        }catch (ParseException e){
            MyApplications.showLog(SevenDayPagerAdapter.class.getSimpleName()+"--日期解析发生异常");
        }
        String result = "周"+date.getDay();
        return result;
    }
}
