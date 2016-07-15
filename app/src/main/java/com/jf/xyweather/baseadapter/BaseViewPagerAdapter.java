package com.jf.xyweather.baseadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by jf on 2016/6/22.
 * The basic adapter for ViewPager that load fragment
 */
public class BaseViewPagerAdapter extends FragmentStatePagerAdapter{

    protected List<Fragment> fragmentList;

    public BaseViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    public BaseViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList == null? 0: fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList == null? null : fragmentList.get(position);
    }

    public void setFragmentList(List<Fragment> fragmentList){
        this.fragmentList = fragmentList;
    }

}
