package com.jf.xyweather.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.activity.BaseActivity;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    private String currentFragment;//The fragment that interact with the user now
    private String[] fragmentsTag;//All fragments' name in this Activity
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
        fragmentManager = getSupportFragmentManager();
        fragmentsTag = new String[]{WeatherFragment.class.getSimpleName(), LiveActionFragment.class.getSimpleName(), MeFragment.class.getSimpleName()};
    }

    @Override
    protected void initView() {
        initRadioGroup();
        initFragment();
    }

    private void initRadioGroup(){
        radioGroup = (RadioGroup)findViewById(R.id.rg_activity_base_view_pager);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void initFragment(){
        fragmentManager.beginTransaction().add(R.id.fl_activity_main_fragment_group, new WeatherFragment(), fragmentsTag[0]).commit();
        currentFragment = fragmentsTag[0];
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction;
        switch (checkedId){
            case R.id.rb_activity_main_weather:
                if(currentFragment.equals(fragmentsTag[0])){
                    break;
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(fragmentManager.findFragmentByTag(currentFragment));
                fragmentTransaction.show(fragmentManager.findFragmentByTag(fragmentsTag[0]));
                currentFragment = fragmentsTag[0];
                fragmentTransaction.commit();
                break;
            case R.id.rb_activity_main_live_action:
                if(currentFragment.equals(fragmentsTag[1])){
                    break;
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment liceActionFragment = fragmentManager.findFragmentByTag(fragmentsTag[1]);
                fragmentTransaction.hide(fragmentManager.findFragmentByTag(currentFragment));
                if(liceActionFragment != null){
                    fragmentTransaction.show(liceActionFragment).commit();
                    currentFragment = fragmentsTag[1];
                    break;
                }
                MyApplications.showLog("新建\"LiveActionFragment\"");
                fragmentTransaction.add(R.id.fl_activity_main_fragment_group, new LiveActionFragment(), fragmentsTag[1]).commit();
                currentFragment = fragmentsTag[1];
                break;
            case R.id.rb_activity_main_me:
                if(currentFragment.equals(fragmentsTag[2])){
                    break;
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment meFragment = fragmentManager.findFragmentByTag(fragmentsTag[2]);
                fragmentTransaction.hide(fragmentManager.findFragmentByTag(currentFragment));
                if(meFragment != null){
                    fragmentTransaction.show(meFragment).commit();
                    currentFragment = fragmentsTag[2];
                    break;
                }
                MyApplications.showLog("新建\"MeFragment\"");
                fragmentTransaction.add(R.id.fl_activity_main_fragment_group, new MeFragment(), fragmentsTag[2]).commit();
                currentFragment = fragmentsTag[2];
                break;
            default:break;
        }
    }
}
