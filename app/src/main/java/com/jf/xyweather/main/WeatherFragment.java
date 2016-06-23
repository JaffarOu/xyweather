package com.jf.xyweather.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.BaseAdapter;

import com.jf.xyweather.R;
import com.jf.xyweather.base.fragment.BaseFragment;
import com.jf.xyweather.base.fragment.BaseViewPagerFragment;
import com.jf.xyweather.baseadapter.BaseViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/16
 * The "weather page" in the "MainActivity".
 */
public class WeatherFragment extends BaseViewPagerFragment {

    @Override
    protected void initView(View layoutView) {
        super.initView(layoutView);
        customTitles.setTitleText("广州/天河");
        layoutView.setBackgroundResource(R.color.ff3299cc);
    }

    @Override
    protected FragmentStatePagerAdapter getFragmentPagerAdapter() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CityWeatherFragment());
        return new BaseViewPagerAdapter(getChildFragmentManager(), fragmentList);
    }
}
