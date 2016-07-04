package com.jf.xyweather.weatherpage;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.jf.xyweather.R;
import com.jf.xyweather.base.fragment.BaseViewPagerFragment;
import com.jf.xyweather.baseadapter.BaseViewPagerAdapter;
import com.jf.xyweather.customview.CustomTitles;
import com.jf.xyweather.model.CityName;
import com.jf.xyweather.model.SelectedCity;
import com.jf.xyweather.selectedcity.SelectedCityActivity;
import com.jf.xyweather.util.SelectedCityHelper;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/16
 * The "weather page" Fragment in the MainActivity.
 * Its two primary functions:
 * 1>locating which city that we stay in.
 * 2>By reading the database to obtain all the cities that selected by user
 * Two functions on above will determined how many CityWeatherFragment should be loaded on this WeatherFragment
 */
public class WeatherFragment extends BaseViewPagerFragment implements CustomTitles.OnTitleClickListener{

    //adapter for ViewPager to load Fragment
    private BaseViewPagerAdapter baseViewPagerAdapter;
    //An AsyncTask to read the list of city that we should query the weather information
    private MyAsyncTasks myAsyncTasks;
    //other variables
    private static final int REQUEST_CODE_START_SELECTED_CITY_ACTIVITY = 1;
    private String currentCity = "广州市";
    private List<CityName> cityNameList;//used to send selected city to the

    @Override
    protected void initView(View layoutView) {
        super.initView(layoutView);

        //initial the title
        customTitles.setTitleText(currentCity);
        customTitles.setImageViewResource(CustomTitles.LEFT_FIRST, R.drawable.ic_home_page);
        customTitles.setImageViewResource(CustomTitles.RIGHT_FIRST, R.drawable.ic_refresh);
        customTitles.setOnTitleClickListener(this);

        //set a background for this interface
        layoutView.setBackgroundResource(R.drawable.bg_weather_fragment);

        //start an AsyncTask to read list of city that we need to query weather information
        myAsyncTasks = new MyAsyncTasks(this);
        myAsyncTasks.execute();
    }

    @Override
    protected FragmentStatePagerAdapter getFragmentPagerAdapter() {
        baseViewPagerAdapter = new BaseViewPagerAdapter(getChildFragmentManager());
        return baseViewPagerAdapter;
    }

    @Override
    public void onTitleClick(View view, int which) {
        //if click the home button,start the Activity to select city
        if(which == CustomTitles.LEFT_FIRST){
            Intent intent = new Intent(getActivity(), SelectedCityActivity.class);
            intent.putExtra(SelectedCityActivity.KEY_SELECTED_CITY_LIST, (Serializable)cityNameList);
            startActivityForResult(intent, REQUEST_CODE_START_SELECTED_CITY_ACTIVITY);
            return;
        }

        //if click the refresh button,refresh the weather information of current page
        if(which == CustomTitles.RIGHT_FIRST){
            CityWeatherFragment cityWeatherFragment = (CityWeatherFragment)baseViewPagerAdapter.instantiateItem(viewPager, viewPager.getCurrentItem());
            cityWeatherFragment.refreshWeather();
        }
    }

    /*override the method of OnPageChangeListener__start*/
    @Override
    public void onPageSelected(int position) {
        CityName cityName = ((CityWeatherFragment) baseViewPagerAdapter.instantiateItem(viewPager, position)).getCityName();
        customTitles.setTitleText(cityName.getCityChineseName());
    }
    /*override the method of OnPageChangeListener_end*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myAsyncTasks.cancel(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_START_SELECTED_CITY_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                //show the city that selected by user
                if(data.getAction().equals(SelectedCityActivity.ACTION_SELECT_CITY)){
                    circlePageIndicator.setCurrentItem(data.getIntExtra(SelectedCityActivity.SELECT_POSITION, viewPager.getCurrentItem()));
                }else if(data.getAction().equals(SelectedCityActivity.ACTION_DELETE_CITY)){
                    //delete the Fragment of city that selected by user
                }
            }
        }
    }

    //An AsyncTask to read the list of city
    private static class MyAsyncTasks extends AsyncTask<Void,Void,List<CityName>>{

        private WeakReference<WeatherFragment> weakReference;

        public MyAsyncTasks(WeatherFragment weatherFragment){
            weakReference = new WeakReference<>(weatherFragment);
        }

        @Override
        protected List<CityName> doInBackground(Void... params) {
            //get the names of all cities that we need to query weather information
            return new SelectedCityHelper().getCityNameList();
        }

        @Override
        protected void onPostExecute(List<CityName> cityNames) {
            WeatherFragment weatherFragment = weakReference.get();
            if(weatherFragment == null){
                return;
            }
            //set the list of fragment for the BaseViewPagerAdapter and keep the name of city
            List<Fragment> fragmentList;
            if(cityNames == null){
                //if there is not thing in database,just load one fragment for the default city
                fragmentList = new ArrayList<>(1);
                fragmentList.add(new CityWeatherFragment());
                //keep the data of cityNameList
                weatherFragment.cityNameList = new ArrayList<>(1);
                weatherFragment.cityNameList.add(new CityName("广州", "guangzhou"));//"guangzhou" is the default city
            }else{
                fragmentList = new ArrayList<>(cityNames.size()+1);
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
                weatherFragment.cityNameList = new ArrayList<>(cityNames.size()+1);
                //keep the data of cityNameList
                weatherFragment.cityNameList.add(new CityName("广州", "guangzhou"));
                weatherFragment.cityNameList.addAll(cityNames);
            }

            //hide the CirclePageIndicator if it just one Fragment in ViewPager
            if(fragmentList.size() == 1){
                weatherFragment.circlePageIndicator.setVisibility(View.GONE);
            }
            //update adapter of Viewpager
            weatherFragment.baseViewPagerAdapter.setFragmentList(fragmentList);
            weatherFragment.baseViewPagerAdapter.notifyDataSetChanged();
        }//onPostExecute()

    }//MyAsyncTask
}
