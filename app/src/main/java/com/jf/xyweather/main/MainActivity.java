package com.jf.xyweather.main;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;

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
        initRadioGroup();
        initFragment();
    }

    private void initRadioGroup(){
        radioGroup = (RadioGroup)findViewById(R.id.rg_activity_base_view_pager);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void initFragment(){
        getSupportFragmentManager().beginTransaction().add(R.id.fl_activity_main, new WeatherFragment()).commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_activity_main_weather:

                break;
            case R.id.rb_activity_main_live_action:
                break;
            case R.id.rb_activity_main_me:
                break;
            default:break;
        }
    }
}
