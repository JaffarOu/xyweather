package com.jf.xyweather.base.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jf.xyweather.R;
import com.jf.xyweather.customview.CustomTitles;
import com.jf.xyweather.model.CityName;
import com.jf.xyweather.weatherpage.CityWeatherFragment;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

/**
 * Created by jf on 2016/6/20.
 * The base class of the fragment that load ViewPager
 */
abstract public class BaseViewPagerFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    protected CustomTitles customTitles;
    protected ViewPager viewPager;
    protected CirclePageIndicator circlePageIndicator;

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_base_view_pager;
    }

    @Override
    protected void initExtra() {
    }

    /**
     * initial the "CustomTitles","ViewPager" and the "ViewPagerIndicator",
     * if you want to set something for the title or viewpager,
     * you can override this method and do what you want to after call super.initView()
     * @param layoutView the Id of layout that return by "getLayoutViewId()" method
     */
    @Override
    protected void initView(View layoutView) {
        //initial the title
        customTitles = (CustomTitles)layoutView.findViewById(R.id.custom_titles_fragment_base_view_pager);

        //initial the ViewPager
        viewPager = (ViewPager)layoutView.findViewById(R.id.vp_fragment_base_view_pager);
        viewPager.setAdapter(getFragmentPagerAdapter());

        //initial the CirclePageIndicator
        circlePageIndicator = (CirclePageIndicator)layoutView.findViewById(R.id.circle_page_indicator_fragment_base_view_pager);
        circlePageIndicator.setViewPager(viewPager);
        circlePageIndicator.setOnPageChangeListener(this);
    }

    abstract protected FragmentStatePagerAdapter getFragmentPagerAdapter();

    /*override the method of OnPageChangeListener__start*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
    /*override the method of OnPageChangeListener_end*/
}
