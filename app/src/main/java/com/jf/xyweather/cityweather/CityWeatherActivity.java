package com.jf.xyweather.cityweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.model.CityInfo;
import com.jf.xyweather.util.LogUtil;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/7/1.
 * The main activity in this App,It used ViewPager with Fragment to show various city's weather information.
 * It also has some other function
 */
public class CityWeatherActivity extends BaseActivity
        implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String KEY_CITY_NAME = "keyCityName";
    private TextView mCityNameTv;           //To show the city's name of current page
    private TextView mLastUpdateTimeTv;     //To show the last time that information was update
    private ViewPager viewPager;            //Hold different CityWeatherFragment
    private CityWeatherFragmentPageAdapter cityWeatherFragmentPageAdapter;
    private List<CityInfo> mCityInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        init();
    }

    private void init() {
        //Get city name from Intent Object
        String cityName = getIntent().getStringExtra(KEY_CITY_NAME);
        if(cityName == null) {
            LogUtil.i(getClass().getSimpleName()+"--"+"没有传入要查询的城市名字");
            return;
        }

        mCityInfoList = new ArrayList<>();
        mCityInfoList.add(new CityInfo(cityName, cityName));

        //Initial the title
        findViewById(R.id.tv_city_weather_more_index).setOnClickListener(this);
        findViewById(R.id.tv_city_weather_manage_city).setOnClickListener(this);
        mCityNameTv = (TextView) findViewById(R.id.tv_city_weather_city_name);
        mLastUpdateTimeTv = (TextView) findViewById(R.id.tv_city_weather_last_update_time);

        //Initial the ViewPager
        viewPager = (ViewPager) findViewById(R.id.vp_city_weather_city_weather);
        cityWeatherFragmentPageAdapter =
                new CityWeatherFragmentPageAdapter(this, getSupportFragmentManager(), mCityInfoList);
        viewPager.setAdapter(cityWeatherFragmentPageAdapter);
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circle_page_indicator_city_weather);
        circlePageIndicator.setViewPager(viewPager);
        circlePageIndicator.setOnPageChangeListener(this);
        if (mCityInfoList.size() < 2) {
            circlePageIndicator.setVisibility(View.INVISIBLE);
        }
        //Set the first city's name as title
        mCityNameTv.setText(mCityInfoList.get(0).getCityChineseName());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_city_weather_more_index){
            //Start An Activity to show more real-time-weather information
            Intent intent = new Intent(this, RealTimeWeatherActivity.class);
            CityWeatherFragment fragment = cityWeatherFragmentPageAdapter.getFragment(viewPager.getCurrentItem());
            intent.putExtra(RealTimeWeatherActivity.KEY_REAL_TIME_WEATHER_FORECAST, fragment.getRealTimeWeather());
            startActivity(intent);
        }else if(id == R.id.tv_city_weather_manage_city){

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCityNameTv.setText(cityWeatherFragmentPageAdapter.getFragment(position).getCityInfo().getCityChineseName());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
