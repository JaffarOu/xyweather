package com.jf.xyweather.cityweather;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.jf.xyweather.model.CityInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JF on 2016/10/24.
 * Return the CityWeatherFragment instance to the ViewPager in MainActivity
 */
public class CityWeatherFragmentPageAdapter extends FragmentStatePagerAdapter{

    private Context mContext;
    //The CityInfo object will be send to CityWeatherFragment
    private List<CityInfo> mCityNameList;
    //Return the CityWeatherFragment instance to the Activity according the position
    private Map<Integer, CityWeatherFragment> mFragmentMap;

    public CityWeatherFragmentPageAdapter(Context context, FragmentManager fm, List<CityInfo> cityNameList) {
        super(fm);
        this.mContext = context;
        this.mCityNameList = cityNameList;
        mFragmentMap = new HashMap<>();
    }

    @Override
    public int getCount() {
        return mCityNameList == null ? 0 : mCityNameList.size();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CityWeatherFragment.KEY_CITY_NAME, mCityNameList.get(position));
        CityWeatherFragment fragment = (CityWeatherFragment)Fragment.instantiate(mContext, CityWeatherFragment.class.getName(), bundle);
        mFragmentMap.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mFragmentMap.remove(position);
    }

    public CityWeatherFragment getFragment(int position){
        return mFragmentMap.get(position);
    }
}
