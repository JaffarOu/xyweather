package com.jf.xyweather.dailyweather;


import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.fragment.BaseFragment;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.model.Temperature;

/**
 * Created by jf on 2016/7/5.
 * An Fragment to show daily weather forecast
 */
public class DailyWeatherFragment extends BaseFragment{

    //variables of view
    private ImageView weatherIconIv;//An icon express weather condition
    private TextView temperatureTv;
    private TextView weatherDescriptionTv;
    private GridView detailsGv;//

    //Object with data
    private DailyWeatherForecast dailyWeatherForecast;

    public static final String KEY_DAILY_WEATHER = "keyDailyWeather";

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_daily_weather;
    }

    @Override
    protected void initExtra() {
        //From the Arguments to get the data we need to show
        Bundle arguments = getArguments();
        if(arguments == null){
            return;
        }
        dailyWeatherForecast =  (DailyWeatherForecast)arguments.getSerializable(KEY_DAILY_WEATHER);
    }

    @Override
    protected void initView(View layoutView) {
        //find view
        weatherIconIv = (ImageView)layoutView.findViewById(R.id.iv_fragment_daily_weather_weather_icon);
        temperatureTv = (TextView)layoutView.findViewById(R.id.tv_fragment_daily_weather_temperature);
        weatherDescriptionTv = (TextView)layoutView.findViewById(R.id.tv_fragment_daily_weather_weather_description);
        detailsGv = (GridView)layoutView.findViewById(R.id.gv_fragment_daily_weather_details);

        //set data
//        weatherIconIv.setImageResource(getImageAccordingWeather());
        Temperature temperature = dailyWeatherForecast.getTmp();
        weatherIconIv.setImageResource(R.drawable.ic_test);
        temperatureTv.setText((int)temperature.getMax()+"°/"+(int)temperature.getMin()+"°");
        weatherDescriptionTv.setText(dailyWeatherForecast.getCond().getTxt_d());

        //set data for GridView
        detailsGv.setAdapter(new DetailsGridViewAdapter(dailyWeatherForecast));
    }

//    private int getImageAccordingWeather(String weatherCondition){
//        int id;
//        if(weatherCondition)
//    }
}
