package com.jf.xyweather.dailyweather;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.fragment.BaseFragment;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.model.Temperature;
import com.jf.xyweather.util.WeatherIconUtil;

/**
 * Created by jf on 2016/7/5.
 * An Fragment to show daily weather forecast
 */
public class DailyWeatherFragment extends BaseFragment{

    //variables of view
    private ImageView mWeatherIconIv;                //An icon express weather condition
    private TextView mTemperatureTv;                 //temperature
    private TextView mWeatherConditionTv;          //weather description such as sunny,windy
    private ListView mDetailsLv;                     //details of weather condition

    //Object with data
    private DailyWeatherForecast dailyWeatherForecast;

    public static final String KEY_DAILY_WEATHER = "keyDailyWeather";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_daily_weather, container, false);
        init(layoutView);
        return layoutView;
    }

    private void init(View layoutView){
        //Get date we need from the arguments
        Bundle arguments = getArguments();
        if(arguments == null) return;
        dailyWeatherForecast =  (DailyWeatherForecast)arguments.getSerializable(KEY_DAILY_WEATHER);
        if(dailyWeatherForecast == null) return;

        //Initial view
        mWeatherIconIv = (ImageView)layoutView.findViewById(R.id.iv_daily_weather_weather_icon);
        mTemperatureTv = (TextView)layoutView.findViewById(R.id.tv_daily_weather_temperature);
        mWeatherConditionTv = (TextView)layoutView.findViewById(R.id.tv_daily_weather_weather_condition);
        mDetailsLv = (ListView) layoutView.findViewById(R.id.lv_daily_weather_details);

        mWeatherIconIv.setImageResource(WeatherIconUtil.getResourceAccordingCode(dailyWeatherForecast.getCond().getCode_d()));
        Temperature temperature = dailyWeatherForecast.getTmp();
        mTemperatureTv.setText((int)temperature.getMax()+"°/"+(int)temperature.getMin()+"°");
        mWeatherConditionTv.setText(dailyWeatherForecast.getCond().getTxt_d());

        //set data for GridView
        mDetailsLv.setAdapter(new WeatherDetailsListAdapter(dailyWeatherForecast));
    }
}
