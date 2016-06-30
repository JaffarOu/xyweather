package com.jf.xyweather.weatherpage;

import android.view.View;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.model.RealTimeWeather;
import com.jf.xyweather.model.Wind;

/**
 * Created by jf on 2016/6/30.
 * an Activity to show real-time weather information
 */
public class RealTimeWeatherActivity extends BaseActivity{

    private int temperature;
    private String weatherType;
    private int bodyFeelingTemperature;
    private int humility;
    private String wind;
    private int visibility;
    private String airPressure;

    public static final String KEY_REAL_TIME_WEATHER_FORECAST = "RealTimeWeather";
    private RealTimeWeather realTimeWeather;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_real_time_weather;
    }

    @Override
    protected void initExtra() {
        //get data from previous intent
        realTimeWeather = (RealTimeWeather)getIntent().getSerializableExtra(KEY_REAL_TIME_WEATHER_FORECAST);
        if(realTimeWeather == null){
            MyApplications.showLog("漏传天气的数据了");
            return;
        }
        //store the data in member variables
        temperature = realTimeWeather.getTmp();
        weatherType = realTimeWeather.getCond().getTxt();
        bodyFeelingTemperature = (int)realTimeWeather.getFl();
        humility = (int)realTimeWeather.getHum();
        Wind windModule = realTimeWeather.getWind();
        //the direction of wind and the power of the wind,such as "东风4级"
        wind = windModule.getDir()+windModule.getSc()+"级";
        visibility = (int)realTimeWeather.getVis();
        airPressure = realTimeWeather.getPres()+"";

    }

    @Override
    protected void initView() {
        //find view by id and set the text for the TextView
        ((TextView)findViewById(R.id.tv_activity_short_term_forecast_temperature)).setText(temperature+"°");
        ((TextView)findViewById(R.id.tv_activity_short_term_forecast_weather_type)).setText(weatherType);
        ((TextView)findViewById(R.id.tv_activity_real_time_weather_body_feeling_temperature)).setText(bodyFeelingTemperature+"℃");
        ((TextView)findViewById(R.id.tv_activity_real_time_weather_humility)).setText(humility+"%");
        ((TextView)findViewById(R.id.tv_activity_real_time_weather_wind)).setText(wind);
        ((TextView)findViewById(R.id.tv_activity_real_time_weather_visibility)).setText(visibility+"");
        ((TextView)findViewById(R.id.tv_activity_real_time_weather_air_pressure)).setText(airPressure);

        findViewById(R.id.ll_activity_short_term_forecast_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
