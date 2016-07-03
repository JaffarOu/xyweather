package com.jf.xyweather.weatherpage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jf.xyweather.R;
import com.jf.xyweather.base.fragment.BaseFragment;
import com.jf.xyweather.baseadapter.BaseViewPagerAdapter;
import com.jf.xyweather.customview.CustomTitles;
import com.jf.xyweather.model.CityName;
import com.jf.xyweather.selectedcity.SelectedCityActivity;
import com.jf.xyweather.util.SelectedCityHelper;
import com.viewpagerindicator.CirclePageIndicator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/16
 * The "weather page" Fragment in the MainActivity.
 */
public class WeatherFragment extends BaseFragment
        implements CustomTitles.OnTitleClickListener, ViewPager.OnPageChangeListener{

    //variables of view
    private CustomTitles customTitles;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter fragmentViewPagerAdapter;
    private CirclePageIndicator circlePageIndicator;

    //An AsyncTask to read the list of city that we should query the weather information
    private MyAsyncTasks myAsyncTasks;

    //other variables
    private static final int REQUEST_CODE_WHICH_CITY = 1;
    private String currentCity = "广州市";

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void initExtra() {
    }

    @Override
    protected void initView(View layoutView) {
        //initial the title
        customTitles = (CustomTitles)layoutView.findViewById(R.id.custom_titles_fragment_weather);
        customTitles.setTitleText(currentCity);
        customTitles.setTitleTextColor(getResources().getColor(R.color.white));
        customTitles.setImageViewResource(CustomTitles.LEFT_FIRST, R.drawable.ic_home_page);
        customTitles.setImageViewResource(CustomTitles.RIGHT_FIRST, R.drawable.ic_refresh);
        customTitles.setOnTitleClickListener(this);

        //find the ViewPager and the CirclePageIndicator
        viewPager = (ViewPager)layoutView.findViewById(R.id.vp_fragment_weather);
        circlePageIndicator = (CirclePageIndicator)layoutView.findViewById(R.id.circle_page_indicator_fragment_weather);
        circlePageIndicator.setOnPageChangeListener(this);

        //set a background for this interface
        layoutView.setBackgroundResource(R.drawable.bg_weather_fragment);

        //start an AsyncTask to read list of city that we need to query weather information
        myAsyncTasks = new MyAsyncTasks(this);
        myAsyncTasks.execute();
    }

    @Override
    public void onTitleClick(View view, int which) {
        //if click the home button,start the Activity to select city
        if(which == CustomTitles.LEFT_FIRST){
            Intent intent = new Intent(getActivity(), SelectedCityActivity.class);
            startActivityForResult(intent, REQUEST_CODE_WHICH_CITY);
            return;
        }

        //if click the refresh button,refresh the weather information of current page
        if(which == CustomTitles.RIGHT_FIRST){
            CityWeatherFragment cityWeatherFragment = (CityWeatherFragment) fragmentViewPagerAdapter.instantiateItem(viewPager, viewPager.getCurrentItem());
            cityWeatherFragment.refreshWeather();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myAsyncTasks.cancel(true);
    }

    /*override the method of OnPageChangeListener__start*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //if it is the first fragment
        CityName cityName = ((CityWeatherFragment) fragmentViewPagerAdapter.instantiateItem(viewPager, position)).getCityName();
        if (cityName != null) {
            customTitles.setTitleText(cityName.getCityChineseName());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
    /*override the method of OnPageChangeListener_end*/

    //An AsyncTask to read the list of city
    private static class MyAsyncTasks extends AsyncTask<Void,Void,List<CityName>>{

        private WeakReference<WeatherFragment> weakReference;

        public MyAsyncTasks(WeatherFragment weatherFragment){
            weakReference = new WeakReference<>(weatherFragment);
        }

        @Override
        protected List<CityName> doInBackground(Void... params) {
            //get the names of all cities we need to query weather information
            return new SelectedCityHelper().getCityNameList();
        }

        @Override
        protected void onPostExecute(List<CityName> cityNames) {
            if(cityNames == null){
                return;
            }
            WeatherFragment weatherFragment = weakReference.get();
            if(weatherFragment == null){
                return;
            }
            //add the first fragment to the list,the weather information of this fragment determined by "GaoDe" locate
            List<Fragment> fragmentList = new ArrayList<>(cityNames.size()+1);
            fragmentList.add(new CityWeatherFragment());

            //initial the list of fragment according the names of cities
            CityWeatherFragment cityWeatherFragment;
            Bundle bundle;
            for(CityName cityName:cityNames){
                bundle = new Bundle();
                bundle.putSerializable("cityName", cityName);
                cityWeatherFragment = new CityWeatherFragment();
                cityWeatherFragment.setArguments(bundle);
                fragmentList.add(cityWeatherFragment);
            }

            //set adapter for ViewPager and CirclePageIndicator
            weatherFragment.fragmentViewPagerAdapter = new BaseViewPagerAdapter(weatherFragment.getChildFragmentManager(), fragmentList);
            weatherFragment.viewPager.setAdapter(weatherFragment.fragmentViewPagerAdapter);
            if(fragmentList.size() == 1){
                weatherFragment.circlePageIndicator.setVisibility(View.INVISIBLE);
            }
            weatherFragment.circlePageIndicator.setViewPager(weatherFragment.viewPager);
        }//onPostExecute()

    }//MyAsyncTask
}
