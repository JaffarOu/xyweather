package com.jf.xyweather.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jf.xyweather.R;
import com.jf.xyweather.view.CustomTitles;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by jf on 2016/6/20.
 * The basic class of the fragment that with ViewPager and use CirclePageIndicator as indicator
 */
abstract public class CircleViewPagerFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    protected CustomTitles customTitles;
    protected ViewPager viewPager;
    protected CirclePageIndicator circlePageIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_circle_view_pager, container, false);
        initView(layoutView);
        return layoutView;
    }

    /**
     * initial the "CustomTitles","ViewPager" and the "ViewPagerIndicator",
     * if you want to set something for the title or viewpager,
     * you can override this method and do what you want to after call super.initView()
     * @param layoutView the Id of layout that return by "getLayoutViewId()" method
     */
    private void initView(View layoutView) {
        //initial the title
        customTitles = (CustomTitles)layoutView.findViewById(R.id.custom_titles_fragment_circle_view_pager);

        //initial the ViewPager
        viewPager = (ViewPager)layoutView.findViewById(R.id.vp_fragment_circle_view_pager);
        viewPager.setAdapter(getFragmentPagerAdapter());

        //initial the CirclePageIndicator
        circlePageIndicator = (CirclePageIndicator)layoutView.findViewById(R.id.circle_page_indicator_fragment_circle_view_pager);
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
