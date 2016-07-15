package com.jf.xyweather.base.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.jf.xyweather.R;
import com.jf.xyweather.baseadapter.BaseViewPagerAdapter;
import com.jf.xyweather.customview.CustomTitles;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by jf on 2016/7/5.
 * This is a basic class of Activity that with ViewPager and use TabPageIndicator as Indicator
 */
abstract public class TabViewPagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    protected CustomTitles customTitles;
    protected TabPageIndicator tabPageIndicator;
    protected ViewPager viewPager;
    //the root view of the current interface layout file
    protected View rootView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tab_view_pager;
    }

    @Override
    protected void initOther() {
    }

    @Override
    protected void initView() {
        //find root view
        rootView = findViewById(R.id.ll_activity_tab_view_pager_root_view);

        //initial title
        customTitles = (CustomTitles)findViewById(R.id.custom_titles_activity_tab_view_pager);

        //initial ViewPager
        viewPager = (ViewPager)findViewById(R.id.vp_activity_tab_view_pager);
        viewPager.setAdapter(getViewPagerAdapter());

        //initial TabPageIndicator
        tabPageIndicator = (TabPageIndicator)findViewById(R.id.tab_page_indicator_activity_b_view_pager);
        tabPageIndicator.setViewPager(viewPager);
        tabPageIndicator.setOnPageChangeListener(this);
    }

    abstract protected BaseViewPagerAdapter getViewPagerAdapter();

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
    /*override the method of OnPageChangeListener__end*/

}
