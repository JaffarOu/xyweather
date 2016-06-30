package com.jf.xyweather.weatherpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.base.fragment.BaseFragment;
import com.jf.xyweather.customview.DailyWeatherWidget;
import com.jf.xyweather.customview.RealTimeWidget;
import com.jf.xyweather.model.AirQualityIndex;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.model.RealTimeWeather;
import com.jf.xyweather.util.HttpThread;
import com.jf.xyweather.util.WeatherInfoJsonParseUtil;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by jf on 2016/6/22.
 * The weather information about a city
 */
public class CityWeatherFragment extends BaseFragment implements View.OnClickListener{

    private HttpHandler httpHandler;//handler message from work thread
    private String cityName;//name of the city that should be query the weather information
    private boolean canChangeUIs;//can MyHandler update the UI or not
    private HttpThread httpThread;//a thread to send http request that get weather information

    private ProgressBar progressBar;//it will be show during the work thread running
    private TextView airQualityIndexTv;//city's air quality index

    private RealTimeWidget realTimeWidget;//real-time weather forecast widget

    private DailyWeatherWidget todayDailyWeatherWidget;
    private DailyWeatherWidget tomorrowDailyWeatherWidget;

    private RealTimeWeather realTimeWeather;

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_city_weather;
    }

    @Override
    protected void initExtra() {
        //determined which city we need to query the weather information,,the default city is "guangzhou"
        Bundle arguments = getArguments();
        cityName = arguments == null?"guangzhou":arguments.getString("cityName", "guangzhou");

        //initial other
        canChangeUIs = true;
        httpHandler = new HttpHandler(this);
    }

    @Override
    protected void initView(View layoutView) {
        //find view
        progressBar = (ProgressBar)layoutView.findViewById(R.id.pb_fragment_city_weather);
        airQualityIndexTv = (TextView)layoutView.findViewById(R.id.tv_fragment_city_weather_air_quality_index);
        realTimeWidget = (RealTimeWidget) layoutView.findViewById(R.id.real_time_forecast_fragment_city_weather);
        todayDailyWeatherWidget = (DailyWeatherWidget)layoutView.findViewById(R.id.daily_weather_fragment_city_weather_today);
        tomorrowDailyWeatherWidget = (DailyWeatherWidget) layoutView.findViewById(R.id.daily_weather_fragment_city_weather_tomorrow);

        //set "OnclickListener"
        layoutView.findViewById(R.id.rl_fragment_city_weather_search).setOnClickListener(this);
        airQualityIndexTv.setOnClickListener(this);
        todayDailyWeatherWidget.setOnClickListener(this);
        tomorrowDailyWeatherWidget.setOnClickListener(this);
        realTimeWidget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.rl_fragment_city_weather_search:
                //starting a new activity to search more content about weather if user click search button
                MyApplications.showToast((BaseActivity) getActivity(), "the function is not completed yet");
                break;
            case R.id.real_time_forecast_fragment_city_weather:
                //return immediately if no data
                if(realTimeWeather == null){
                    MyApplications.showLog("没有天气数据不能启动界面");
                    return;
                }
                //start a new activity to show real-time weather forecast
                Intent intent = new Intent(getActivity(), RealTimeWeatherActivity.class);
                intent.putExtra(RealTimeWeatherActivity.KEY_REAL_TIME_WEATHER_FORECAST, realTimeWeather);
                startActivity(intent);
                break;
            case R.id.tv_fragment_city_weather_air_quality_index:
                break;
            case R.id.daily_weather_fragment_city_weather_today:
                break;
            case R.id.daily_weather_fragment_city_weather_tomorrow:
                break;
            default:break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        canChangeUIs = false;
    }

    /**
     * refresh the weather information on this page
     */
    public void refreshWeather(){
        progressBar.setVisibility(View.VISIBLE);
        if(httpThread == null){
            httpThread = new HttpThread(cityName, httpHandler);
            httpThread.start();
        }
    }

    private void setWeatherInfoJSON(String JSON){
        //get the JSON parse tool
        WeatherInfoJsonParseUtil weatherInfoJsonParseUtil = new WeatherInfoJsonParseUtil(JSON);

        //if JSON parse successfully
        if( !weatherInfoJsonParseUtil.getStatus().equals("ok") ){
            MyApplications.showLog(getClass().getSimpleName()+"--the JSON string is incorrect or the pares is failed");
            httpThread = null;
            return;
        }
        /*parse every JSON object we need*/
        //get the real-time weather information
        realTimeWeather = weatherInfoJsonParseUtil.getRealTimeWeather();
        if(realTimeWidget != null){
            this.realTimeWidget.setTemperature(realTimeWeather.getTmp());
            this.realTimeWidget.setWeatherType(realTimeWeather.getCond().getTxt());
            //set the short-term-forecast
            this.realTimeWidget.setRealTimeWeather(realTimeWeather.getWind().getSc(), realTimeWeather.getHum(), realTimeWeather.getFl(), realTimeWeather.getPres());
        }
        //get the air quality index
        AirQualityIndex aqi = weatherInfoJsonParseUtil.getAirQualityIndex();
        if(aqi != null){
            airQualityIndexTv.setText(aqi.getAqi()+" "+aqi.getQlty());
        }
        //get the daily weather forecast
        List<DailyWeatherForecast> dailyWeatherForecasts = weatherInfoJsonParseUtil.getDailyWeatherForecast();
        if(dailyWeatherForecasts != null){
            todayDailyWeatherWidget.setWhichDay("今天");
            todayDailyWeatherWidget.setDailyWeather(dailyWeatherForecasts.get(0));
            tomorrowDailyWeatherWidget.setWhichDay("明天");
            tomorrowDailyWeatherWidget.setDailyWeather(dailyWeatherForecasts.get(1));
        }
    }

    private static class HttpHandler extends Handler{
        private WeakReference<CityWeatherFragment> weakReference;

        public HttpHandler(CityWeatherFragment cityWeatherFragment){
            this.weakReference = new WeakReference<>(cityWeatherFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            CityWeatherFragment cityWeatherFragment = weakReference.get();
            if (cityWeatherFragment == null) {
                super.handleMessage(msg);
                return;
            }
            //if the fragment not allow to change the UI,return
            if( !cityWeatherFragment.canChangeUIs ){
                cityWeatherFragment.httpThread = null;
                super.handleMessage(msg);
                return;
            }
            cityWeatherFragment.progressBar.setVisibility(View.GONE);
            if(msg.what == HttpThread.HTTP_REQUEST_ON_FINISH){
                cityWeatherFragment.setWeatherInfoJSON((String) msg.obj);
            }else if(msg.what == HttpThread.HTTP_REQUEST_ON_ERROR){
                MyApplications.showLog(getClass().getSimpleName()+"Http请求出错");
            }else if(msg.what == HttpThread.HTTP_REQUEST_ON_NO_RESPONSE){
                MyApplications.showLog(getClass().getSimpleName()+"服务器没有响应");
            }
            cityWeatherFragment.httpThread = null;
            super.handleMessage(msg);
        }
    }
}
