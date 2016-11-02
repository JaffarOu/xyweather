package com.jf.xyweather.cityweather;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.jf.xyweather.model.SelectedCity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by JF on 2016/10/24.
 * Return the CityWeatherFragment instance
 */
public class CityWeatherFragmentPageAdapter extends FragmentStatePagerAdapter{

    private Context mContext;
    //The list of selected city will be send to CityWeatherFragment
    private List<SelectedCity> mSelectedCityList;
    //Return the CityWeatherFragment instance to the Activity according the position
    private Map<Integer, CityWeatherFragment> mFragmentMap;
    private FragmentManager mFragmentManager;

    public CityWeatherFragmentPageAdapter(Context context, FragmentManager fm, List<SelectedCity> cityNameList) {
        super(fm);
        this.mContext = context;
        mFragmentManager = fm;
        this.mSelectedCityList = cityNameList;
        mFragmentMap = new HashMap<>();
    }

    @Override
    public int getCount() {
        return mSelectedCityList == null ? 0 : mSelectedCityList.size();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CityWeatherFragment.KEY_CITY_NAME, mSelectedCityList.get(position));
        CityWeatherFragment fragment = new CityWeatherFragment();
        fragment.setArguments(bundle);
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

    public void setSelectedCityList(List<SelectedCity> selectedCityList){
        mSelectedCityList = selectedCityList;
        //clear ache Fragment in FragmentManager
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Set<Integer> keySet = mFragmentMap.keySet();
        for(int key:keySet){
            transaction.remove(mFragmentMap.get(key));
        }
        transaction.commit();
        //Clear the key values "name fragment"
        mFragmentMap.clear();
        mFragmentManager.executePendingTransactions();
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
