package com.jf.xyweather.weatherpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.base.fragment.BaseViewPagerFragment;
import com.jf.xyweather.baseadapter.BaseViewPagerAdapter;
import com.jf.xyweather.customview.CustomTitles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/16
 * The "weather page" in the "MainActivity".
 */
public class WeatherFragment extends BaseViewPagerFragment implements CustomTitles.OnTitleClickListener{

    private FragmentStatePagerAdapter fragmentViewPagerAdapter;
    @Override
    protected void initView(View layoutView) {
        super.initView(layoutView);
        //initial the title
        customTitles.setTitleText("广州");
        customTitles.setTitleTextColor(getResources().getColor(R.color.white));
        customTitles.setImageViewResource(CustomTitles.LEFT_FIRST, R.drawable.ic_home_page);
        customTitles.setImageViewResource(CustomTitles.RIGHT_FIRST, R.drawable.ic_refresh);
        customTitles.setOnTitleClickListener(this);

        //initial other view
        layoutView.setBackgroundResource(R.drawable.bg_weather_fragment);
    }

    @Override
    protected FragmentStatePagerAdapter getFragmentPagerAdapter() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CityWeatherFragment());
        fragmentViewPagerAdapter = new BaseViewPagerAdapter(getChildFragmentManager(), fragmentList);
        return fragmentViewPagerAdapter;
    }

    @Override
    public void onTitleClick(View view, int which) {
        //if click the home button,start the Activity to select city
        if(which == CustomTitles.LEFT_FIRST){
            MyApplications.showToast((BaseActivity) getActivity(), "the function is not completed yet");
            return;
        }
        //if click the refresh button,refresh the weather information of current page
        if(which == CustomTitles.RIGHT_FIRST){
            CityWeatherFragment cityWeatherFragment = (CityWeatherFragment) fragmentViewPagerAdapter.instantiateItem(viewPager, viewPager.getCurrentItem());
            cityWeatherFragment.refreshWeather();
        }
    }
}
