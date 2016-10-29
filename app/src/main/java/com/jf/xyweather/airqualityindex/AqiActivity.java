package com.jf.xyweather.airqualityindex;

import android.os.Bundle;
import android.widget.ListView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.model.AirQualityIndex;

/**
 * Created by jf on 2016/7/15.
 * An Activity to display detailed information about air quality index
 */
public class AqiActivity extends BaseActivity{

    public static final String KEY_AIR_QUALITY_INDEX = "keyAirQualityIndex";
    public static final String KEY_CITY_NAME = "keyCityName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqi);
        initView();
    }

    private void initView() {
        //get data from the Intent
        AirQualityIndex airQualityIndex = (AirQualityIndex)getIntent().getSerializableExtra(KEY_AIR_QUALITY_INDEX);
        ListView aqiListView = (ListView)findViewById(R.id.lv_air_quality_index);
        aqiListView.setAdapter(new AirQualityIndexListAdapter(airQualityIndex));
    }

}
