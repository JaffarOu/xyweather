package com.jf.xyweather.weatherpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jf.xyweather.R;
import com.jf.xyweather.airqualityindex.AqiActivity;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.base.fragment.BaseFragment;
import com.jf.xyweather.customview.DailyWeatherWidget;
import com.jf.xyweather.customview.RealTimeWidget;
import com.jf.xyweather.dailyweather.SevenDayWeatherActivity;
import com.jf.xyweather.model.AirQualityIndex;
import com.jf.xyweather.model.CityName;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.model.RealTimeWeather;
import com.jf.xyweather.model.Wind;
import com.jf.xyweather.util.HttpJSONListener;
import com.jf.xyweather.util.HttpRequestUtils;
import com.jf.xyweather.util.WeatherInfoJsonParseUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/22.
 * The weather information about a city
 */
public class CityWeatherFragment extends BaseFragment
        implements View.OnClickListener, HttpJSONListener {

    //variables of view
    private View refreshHint;//it will be show during the work thread running
    private TextView airQualityIndexTv;
    private RealTimeWidget realTimeWidget;
    private DailyWeatherWidget todayDailyWeatherWidget;
    private DailyWeatherWidget tomorrowDailyWeatherWidget;

    //other variables
    private RealTimeWeather realTimeWeather;//A variables to store the information of real-time weather
    private AirQualityIndex airQualityIndex;//A variables to store the information of air quality index
    private RequestQueue requestQueue;//request queue of volley
    private boolean isHttpFinished = true;//To identity whether http request is finished or not before start http request
    private CityName cityName;//name of city that this fragment will query the weather information

    private List<DailyWeatherForecast> dailyWeatherList;

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_city_weather;
    }

    @Override
    protected void initOther() {
        //determined which city we need to query the weather information,,the default city is "guangzhou"
        Bundle arguments = getArguments();
        if(arguments != null){
            cityName = (CityName)arguments.getSerializable("cityName");
        }else{
            //if arguments == null,the city we query will determined by "GaoDe"
            cityName = new CityName("广州", "guangzhou");
        }

        //initial the requestQueue in this Fragment
        requestQueue = Volley.newRequestQueue(getActivity());
    }

    @Override
    protected void initView(View layoutView) {
        //find view
//        progressBar = (ProgressBar) layoutView.findViewById(R.id.pb_fragment_city_weather);
        refreshHint = layoutView.findViewById(R.id.ll_fragment_city_weather_refresh_hint);
        airQualityIndexTv = (TextView) layoutView.findViewById(R.id.tv_fragment_city_weather_air_quality_index);
        realTimeWidget = (RealTimeWidget) layoutView.findViewById(R.id.real_time_forecast_fragment_city_weather);
        todayDailyWeatherWidget = (DailyWeatherWidget) layoutView.findViewById(R.id.daily_weather_fragment_city_weather_today);
        tomorrowDailyWeatherWidget = (DailyWeatherWidget) layoutView.findViewById(R.id.daily_weather_fragment_city_weather_tomorrow);

        //set "OnclickListener"
//        layoutView.findViewById(R.id.rl_fragment_city_weather_search).setOnClickListener(this);
        airQualityIndexTv.setOnClickListener(this);
        todayDailyWeatherWidget.setOnClickListener(this);
        tomorrowDailyWeatherWidget.setOnClickListener(this);
        realTimeWidget.setOnClickListener(this);

        refreshWeather();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
//            case R.id.rl_fragment_city_weather_search:
//                //starting a new activity to search more content about weather if user click search button
//                MyApplications.showToast((BaseActivity) getActivity(), "该功能还没完成");
//                break;
            case R.id.tv_fragment_city_weather_air_quality_index:
                //start an Activity to show the details of the air quality index
                Intent aqiIntent = new Intent(getActivity(), AqiActivity.class);
                aqiIntent.putExtra(AqiActivity.KEY_CITY_NAME, cityName).putExtra(AqiActivity.KEY_AIR_QUALITY_INDEX, airQualityIndex);
                startActivity(aqiIntent);
                break;
            case R.id.real_time_forecast_fragment_city_weather:
                //return immediately if no data
                if (realTimeWeather == null) {
                    MyApplications.showLog("没有天气数据不能启动界面");
                    return;
                }
                //start a new activity to show real-time weather forecast
                Intent realTimeWeatherIntent = new Intent(getActivity(), RealTimeWeatherActivity.class);
                realTimeWeatherIntent.putExtra(RealTimeWeatherActivity.KEY_REAL_TIME_WEATHER_FORECAST, realTimeWeather);
                startActivity(realTimeWeatherIntent);
                break;
            case R.id.daily_weather_fragment_city_weather_today:
                //Start SevenDaiWeatherActivity and pass the data we need
                Intent todayWeatherIntent = new Intent(getActivity(), SevenDayWeatherActivity.class);
                //pass the name of city and seven-day weather information
                todayWeatherIntent.putExtra(SevenDayWeatherActivity.KEY_CITY_NAME, cityName.getCityChineseName());
                todayWeatherIntent.putExtra(SevenDayWeatherActivity.KEY_SEVEN_DAY_WEATHER, (Serializable) dailyWeatherList);
                startActivity(todayWeatherIntent);
                break;
            case R.id.daily_weather_fragment_city_weather_tomorrow:
                //Start SevenDaiWeatherActivity and pass the data we need
                Intent tomorrowWeatherIntent = new Intent(getActivity(), SevenDayWeatherActivity.class);
                //pass the name of city and seven-day weather information
                tomorrowWeatherIntent.putExtra(SevenDayWeatherActivity.KEY_CITY_NAME, cityName.getCityChineseName());
                tomorrowWeatherIntent.putExtra(SevenDayWeatherActivity.KEY_SEVEN_DAY_WEATHER, (Serializable) dailyWeatherList);
                startActivity(tomorrowWeatherIntent);
                break;
            default:
                break;
        }
    }

    /**
     * refresh the weather information on this page
     */
    public void refreshWeather() {
        if (isHttpFinished) {
//            progressBar.setVisibility(View.VISIBLE);
            refreshHint.setVisibility(View.VISIBLE);
            isHttpFinished = false;//change the flag
            HttpRequestUtils.queryWeatherByCityName(cityName.getCityPinYinName(), this, requestQueue);
        }else{
            MyApplications.showToast((BaseActivity)getActivity(), "正在拼命拉取天气信息，稍等哦亲");
        }
    }

    /**
     * return the name of city that this fragment is showing,
     * this method used by parent of this Fragment
     * @return name of city
     */
    public CityName getCityName(){
        return cityName;
    }

    /*override the method of HttpListener_start*/
    @Override
    public void onFinish(String result) {
        refreshHint.setVisibility(View.GONE);
        setWeatherInformation(result);
        isHttpFinished = true;
    }

    @Override
    public void onError(String error) {
        isHttpFinished = true;
        refreshHint.setVisibility(View.GONE);
        MyApplications.showToast((BaseActivity)getActivity(), "网络异常，请稍后再尝试刷新");
    }
    /*override the method of HttpListener_end*/

    //set various of weather information according JSONString
    private void setWeatherInformation(String JSON) {
        //get the JSON parse tool
        WeatherInfoJsonParseUtil weatherInfoJsonParseUtil = new WeatherInfoJsonParseUtil(JSON);

        //return directory if the parse of JSON is not successfully
        if (!weatherInfoJsonParseUtil.getStatus().equals("ok")) {
            MyApplications.showLog(getClass().getSimpleName() + "--the JSON string is incorrect or the pares is failed");
//            httpThread = null;
            return;
        }

        /*parse every JSON object that we need*/
        //get the real-time weather information
        realTimeWeather = weatherInfoJsonParseUtil.getRealTimeWeather();
        if (realTimeWidget != null) {
            realTimeWidget.setRealTimeWeather(realTimeWeather);
        }

        //get the air quality index
        airQualityIndex = weatherInfoJsonParseUtil.getAirQualityIndex();
        if (airQualityIndex != null) {
            airQualityIndexTv.setText(airQualityIndex.getAqi() + " " + airQualityIndex.getQlty());
        }

        //Set daily-weather forecast for today and tomorrow
        dailyWeatherList = weatherInfoJsonParseUtil.getDailyWeatherForecast();
        if (dailyWeatherList != null) {
            DailyWeatherForecast today = dailyWeatherList.get(0);
            DailyWeatherForecast tomorrow = dailyWeatherList.get(1);

            todayDailyWeatherWidget.setWhichDay(DailyWeatherWidget.TODAY);
            todayDailyWeatherWidget.setDailyWeather(today);
            tomorrowDailyWeatherWidget.setWhichDay(DailyWeatherWidget.TOMORROW);
            tomorrowDailyWeatherWidget.setDailyWeather(tomorrow);
        }
    }//setWeatherInformation()
}
