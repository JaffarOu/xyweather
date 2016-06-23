package com.jf.xyweather.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.fragment.BaseFragment;
import com.jf.xyweather.model.AirQualityIndex;
import com.jf.xyweather.model.CityBasicInformation;
import com.jf.xyweather.util.HttpThread;
import com.jf.xyweather.util.WeatherInfoJsonParseUtil;

import java.lang.ref.WeakReference;

/**
 * Created by jf on 2016/6/22.
 * The weather information about a city
 */
public class CityWeatherFragment extends BaseFragment implements View.OnClickListener{

    private CityWeatherHandler cityWeatherHandler;
    private String cityName;
    private boolean canChangeUis;

    private ProgressBar progressBar;
    private TextView airQualityIndexTv;//city's air quality index

    private View shortTermForecastView;

    private View todayWeatherInfoView;
    private View tomorrowWeatherInfoView;

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_city_weather;
    }

    @Override
    protected void initExtra() {
        Bundle arguments = getArguments();
        if (arguments == null){
            cityName = "guangzhou";
        }else{
            cityName = getArguments().getString("cityName","guangzhou");
        }
        canChangeUis = true;
        cityWeatherHandler = new CityWeatherHandler(this);
    }

    @Override
    protected void initView(View layoutView) {
        progressBar = (ProgressBar)layoutView.findViewById(R.id.pb_fragment_city_weather);
        layoutView.findViewById(R.id.rl_fragment_city_weather_search).setOnClickListener(this);
        airQualityIndexTv = (TextView)layoutView.findViewById(R.id.tv_fragment_city_weather_air_quality_index);
        airQualityIndexTv.setOnClickListener(this);
        shortTermForecastView = layoutView.findViewById(R.id.view_fragment_city_weather_short_term_forecast);
        todayWeatherInfoView = layoutView.findViewById(R.id.view_fragment_city_weather_today);
        tomorrowWeatherInfoView = layoutView.findViewById(R.id.view_fragment_city_weather_tomorrow);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.rl_fragment_city_weather_search:
                //starting a new activity to search more content about weather
                progressBar.setVisibility(View.VISIBLE);
                new HttpThread(cityName, cityWeatherHandler).start();
                break;
            case R.id.tv_fragment_city_weather_air_quality_index:
                break;
            default:break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        canChangeUis = false;
    }

    private static class CityWeatherHandler extends Handler{
        private WeakReference<CityWeatherFragment> weakReference;

        public CityWeatherHandler(CityWeatherFragment cityWeatherFragment){
            this.weakReference = new WeakReference<>(cityWeatherFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            CityWeatherFragment cityWeatherFragment = weakReference.get();
            if (cityWeatherFragment == null) {
                super.handleMessage(msg);
                return;
            }
            if(cityWeatherFragment.canChangeUis){
                cityWeatherFragment.progressBar.setVisibility(View.GONE);
            }
            switch (msg.what) {
                case HttpThread.HTTP_REQUEST_ON_FINISH:
                    MyApplications.showLog("解析完成");
                    WeatherInfoJsonParseUtil weatherInfoJsonParseUtil = new WeatherInfoJsonParseUtil((String)msg.obj);
                    AirQualityIndex airQualityIndex = weatherInfoJsonParseUtil.getCityAqi();
                    if(airQualityIndex != null){
                        MyApplications.showLog("pm2.5=="+ airQualityIndex.getPm25()+"--pm10=="+ airQualityIndex.getPm10());
                    }
                    CityBasicInformation cityBasicInformation = weatherInfoJsonParseUtil.getCityBasicInfo();
                    if(cityBasicInformation != null){
                        MyApplications.showLog("city-"+cityBasicInformation.getCity()+"--country-"+cityBasicInformation.getCnty());
                    }
                    break;
                case HttpThread.HTTP_REQUEST_ON_ERROR:
                    MyApplications.showLog("解析失败");
                    break;
                case HttpThread.HTTP_REQUEST_ON_NO_RESPONSE:
                    MyApplications.showLog("服务器没有响应");
                    break;
                default:break;
            }
            super.handleMessage(msg);
        }
    }
}
