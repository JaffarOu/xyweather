package com.jf.xyweather.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.cityweather.CityWeatherFragmentPageAdapter;
import com.jf.xyweather.model.CityName;
import com.jf.xyweather.cityweather.CityWeatherFragment;
import com.jf.xyweather.cityweather.RealTimeWeatherActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/7/1.
 * The main activity in this App,It used ViewPager with Fragment to show various city's weather information.
 * It also has some other function
 */
public class MainActivity extends BaseActivity
        implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView mCityNameTv;           //To show the city's name of current page
    private TextView mLastUpdateTimeTv;     //To show the last time that information was update
    private ViewPager viewPager;            //Hold different CityWeatherFragment
    private CityWeatherFragmentPageAdapter cityWeatherFragmentPageAdapter;
    private List<CityName> cityNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //Initial the title
        findViewById(R.id.tv_main_more_index).setOnClickListener(this);
        findViewById(R.id.tv_main_manage_city).setOnClickListener(this);
        mCityNameTv = (TextView) findViewById(R.id.tv_main_city_name);
        mLastUpdateTimeTv = (TextView) findViewById(R.id.tv_main_last_update_time);
        //Get city list from database,this is some date used to test
        cityNameList = new ArrayList<>();
        cityNameList.add(new CityName("广州", "guangzhou"));
        cityNameList.add(new CityName("深圳", "shenzhen"));
        cityNameList.add(new CityName("惠州", "huizhou"));
        cityNameList.add(new CityName("珠海", "zhuhai"));

        //Initial the ViewPager
        viewPager = (ViewPager) findViewById(R.id.vp_main_city_weather);
        cityWeatherFragmentPageAdapter = new CityWeatherFragmentPageAdapter(this, getSupportFragmentManager(), cityNameList);
        viewPager.setAdapter(cityWeatherFragmentPageAdapter);
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circle_page_indicator_main_city_weather);
        circlePageIndicator.setViewPager(viewPager);
        circlePageIndicator.setOnPageChangeListener(this);
        if (cityNameList.size() < 2) {
            circlePageIndicator.setVisibility(View.INVISIBLE);
        }
        //Set the first city's name as title
        mCityNameTv.setText(cityNameList.get(0).getCityChineseName());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_main_more_index){
            //Start An Activity to show more real-time-weather information
            Intent intent = new Intent(this, RealTimeWeatherActivity.class);
            CityWeatherFragment fragment = cityWeatherFragmentPageAdapter.getFragment(viewPager.getCurrentItem());
            intent.putExtra(RealTimeWeatherActivity.KEY_REAL_TIME_WEATHER_FORECAST, fragment.getRealTimeWeather());
            startActivity(intent);
        }else if(id == R.id.tv_main_manage_city){

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCityNameTv.setText(cityWeatherFragmentPageAdapter.getFragment(position).getCityName().getCityChineseName());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
