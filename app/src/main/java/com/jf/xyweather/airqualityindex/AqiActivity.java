package com.jf.xyweather.airqualityindex;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.customview.CustomTitles;
import com.jf.xyweather.model.AirQualityIndex;
import com.jf.xyweather.model.CityName;

/**
 * Created by jf on 2016/7/15.
 * An Activity to display detailed information about air quality index
 */
public class AqiActivity extends BaseActivity implements CustomTitles.OnTitleClickListener{

    public static final String KEY_AIR_QUALITY_INDEX = "keyAirQualityIndex";
    public static final String KEY_CITY_NAME = "keyCityName";

    private AirQualityIndex airQualityIndex;
    private CityName cityName;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_aqi;
    }

    @Override
    protected void initOther() {
        //get data from the Intent
        Intent intent = getIntent();
        airQualityIndex = (AirQualityIndex)intent.getSerializableExtra(KEY_AIR_QUALITY_INDEX);
        cityName = (CityName)intent.getSerializableExtra(KEY_CITY_NAME);
    }

    @Override
    protected void initView() {
        //initial title
        CustomTitles customTitles = (CustomTitles)findViewById(R.id.custom_titles_activity_aqi);
        customTitles.setTitleText(cityName.getCityChineseName());
        customTitles.setImageViewResource(CustomTitles.LEFT_FIRST, R.drawable.ic_return);
        customTitles.setOnTitleClickListener(this);

        //set the air quality index condition
        setAqiCondition();

        //initial GridView
        GridView gridView = (GridView)findViewById(R.id.gv_activity_aqi);
        gridView.setAdapter(new AqiActivityAdapter(airQualityIndex));

        //setBackGround
        findViewById(R.id.activity_aqi_root_view).setBackgroundResource(R.drawable.bg_weather_fragment);
    }

    @Override
    public void onTitleClick(View view, int which) {
        //finishing the Activity if user click the return button
        if(which == CustomTitles.LEFT_FIRST){
            finish();
        }
    }

    private void setAqiCondition(){
        TextView aqiConditionTv = (TextView)findViewById(R.id.tv_activity_aqi_condition);
        aqiConditionTv.setText(airQualityIndex.getQlty());
        if(airQualityIndex.getQlty().equals("优")){
            aqiConditionTv.setTextColor(getResources().getColor(R.color.green));
        }else if(airQualityIndex.getQlty().equals("良")){
            aqiConditionTv.setTextColor(getResources().getColor(R.color.yellow));
        }
    }
}
