package com.jf.xyweather.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.liveactionpage.LiveActionFragment;
import com.jf.xyweather.settingpage.SettingFragment;
import com.jf.xyweather.weatherpage.WeatherFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    private String currentFragmentTag;//The fragment that interact with the user now
    private String[] fragmentsTag;//All fragments' tags in this Activity
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initExtra() {
    }

    @Override
    protected void initView() {
        //initial "RadioGroup"
        radioGroup = (RadioGroup)findViewById(R.id.rg_activity_base_view_pager);
        radioGroup.setOnCheckedChangeListener(this);

        //initial "Fragment"
        fragmentManager = getSupportFragmentManager();
        fragmentsTag = new String[]{WeatherFragment.class.getSimpleName(), LiveActionFragment.class.getSimpleName(), SettingFragment.class.getSimpleName()};
        fragmentManager.beginTransaction().add(R.id.fl_activity_main_fragment_group, new WeatherFragment(), fragmentsTag[0]).commit();
        currentFragmentTag = fragmentsTag[0];
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_activity_main_weather:
                changeFragment(fragmentsTag[0]);
                break;
            case R.id.rb_activity_main_live_action:
                changeFragment(fragmentsTag[1]);
                break;
            case R.id.rb_activity_main_me:
                changeFragment(fragmentsTag[2]);
                break;
            default:break;
        }
    }

    //change fragment according fragmentTag
    private void changeFragment(String fragmentTag){
        if(currentFragmentTag.equals(fragmentTag)){
            return;
        }
        //hide current fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragmentManager.findFragmentByTag(currentFragmentTag));
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        //show the fragment that point by fragmentTag
        if(fragment != null){
            transaction.show(fragment).commit();
            currentFragmentTag = fragmentTag;
            return;
        }
        if(fragmentTag.equals(fragmentsTag[1])){
            fragment = new LiveActionFragment();
        }else if(fragmentTag.equals(fragmentsTag[2])){
            fragment = new SettingFragment();
        }
        transaction.add(R.id.fl_activity_main_fragment_group, fragment, fragmentTag).commit();
        currentFragmentTag = fragmentTag;
    }
}
