package com.jf.xyweather.cityweather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jf.xyweather.R;
import com.jf.xyweather.airqualityindex.AqiActivity;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.fragment.BaseFragment;
import com.jf.xyweather.dailyweather.DailyWeatherActivity;
import com.jf.xyweather.lifesuggestion.LifeSuggestionActivity;
import com.jf.xyweather.model.AirQualityIndex;
import com.jf.xyweather.model.SelectedCity;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.model.LifeSuggestion;
import com.jf.xyweather.model.RealTimeWeather;
import com.jf.xyweather.model.Temperature;
import com.jf.xyweather.util.DateUtil;
import com.jf.xyweather.util.HttpJSONListener;
import com.jf.xyweather.util.HttpRequestUtils;
import com.jf.xyweather.util.LogUtil;
import com.jf.xyweather.util.ToastUtil;
import com.jf.xyweather.util.WeatherIconUtil;
import com.jf.xyweather.util.WeatherInfoJsonParseUtil;
import com.jf.xyweather.view.CircleTemperature;
import com.jf.xyweather.view.DailyWeatherWidget;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

/**
 * Created by jf on 2016/6/22.
 * The weather information about a city
 */
public class CityWeatherFragment extends BaseFragment
        implements View.OnClickListener, HttpJSONListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String KEY_CITY_NAME = "cityName";
    private static final int WHAT_REFRESH_WEATHER_DATA = 1<<1;

    //view
    private SwipeRefreshLayout mRefreshLayout;
    private CircleTemperature mCircleTemperatureView;   //An circle view used to show temperature(max,now,min)
//    private ImageView mWeatherIconIv;                       //An icon used to describe the weather condition
    private TextView mWeatherConditionTv;                   //Weather condition such as sunny,windy or other
    private TextView mWeekTv;                               //week
    private TextView airQualityIndexHint;                   //hint-“空气质量”
    private TextView mAirQualityConditionTv;                //air quality condition
    private TextView mAirQualityIndexTv;                    //air quality index
    private LinearLayout mDailyWeatherLl;

    //other
    private SelectedCity mCityInfo;
    //request queue of volley
    private RequestQueue mRequestQueue;

    //Use member variables to keep this data because its may be send to other Activity
    private AirQualityIndex mAirQualityIndex;           //Air quality index
    private RealTimeWeather mRealTimeWeather;           //Real-time-weather information
    private List<DailyWeatherForecast> mDailyWeatherForecastList; //A list with daily weather forecast
    private LifeSuggestion mLifeSuggestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_city_weather, container, false);
        init(layoutView);
        return layoutView;
    }

    private void init(View layoutView) {
        /*Get city's name from the arguments,
        It will initial the UI and request the weather information after get city's information,
        or it will do nothing if it can't get the city's information*/
        Bundle arguments = getArguments();
        if (arguments == null) {
            LogUtil.i(getClass().getSimpleName()+"--"+"没有传入要查询的城市名字");
            return;
        }
        mCityInfo = (SelectedCity) arguments.getSerializable(KEY_CITY_NAME);
        if (mCityInfo == null) {
            LogUtil.i(getClass().getSimpleName()+"--"+"没有传入要查询的城市名字");
            return;
        }
        //initial the Volley in this Fragment
        mRequestQueue = Volley.newRequestQueue(getActivity());
        initView(layoutView);
        postRefreshData();
    }

    //Find view and set listener and others
    private void initView(View layoutView) {
        //initiate view
        layoutView.findViewById(R.id.ll_city_weather_air_quality_index).setOnClickListener(this);
        mRefreshLayout = (SwipeRefreshLayout)layoutView.findViewById(R.id.swipe_refresh_layout_city_weather);
        mCircleTemperatureView = (CircleTemperature)layoutView.findViewById(R.id.circle_temperature_view_city_weather);
//        mWeatherIconIv = (ImageView)layoutView.findViewById(R.id.iv_city_weather_weather_icon);
        mWeatherConditionTv = (TextView)layoutView.findViewById(R.id.tv_city_weather_weather_condition);
        mWeekTv = (TextView)layoutView.findViewById(R.id.tv_city_weather_week);
        airQualityIndexHint = (TextView)layoutView.findViewById(R.id.tv_city_weather_air_quality_hint);
        mAirQualityConditionTv = (TextView)layoutView.findViewById(R.id.tv_city_weather_air_quality_condition);
        mAirQualityIndexTv = (TextView)layoutView.findViewById(R.id.tv_city_weather_air_quality_index);
        mDailyWeatherLl = (LinearLayout)layoutView.findViewById(R.id.ll_city_weather_daily_weather);
        mDailyWeatherLl.setOnClickListener(this);
//        //Daily weather forecast in the future
//        mDailyWeatherWidgets = new DailyWeatherWidget[4];
//        mDailyWeatherWidgets[0] = (DailyWeatherWidget)layoutView.findViewById(R.id.daily_weather_widget_first);
//        mDailyWeatherWidgets[1] = (DailyWeatherWidget)layoutView.findViewById(R.id.daily_weather_widget_second);
//        mDailyWeatherWidgets[2] = (DailyWeatherWidget)layoutView.findViewById(R.id.daily_weather_widget_third);
//        mDailyWeatherWidgets[3] = (DailyWeatherWidget)layoutView.findViewById(R.id.daily_weather_widget_forth);
//        mDailyWeatherWidgets[0].setOnClickListener(this);
//        mDailyWeatherWidgets[1].setOnClickListener(this);
//        mDailyWeatherWidgets[2].setOnClickListener(this);
//        mDailyWeatherWidgets[3].setOnClickListener(this);
        layoutView.findViewById(R.id.tv_city_weather_life_suggestion).setOnClickListener(this);
        //Initial SwipeRefreshLayout
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        mRefreshLayout.setOnRefreshListener(this);
    }

    //Send a message that refresh city data 200ms in the future
    private void postRefreshData(){
        MyHandler myHandler = new MyHandler();
        myHandler.sendEmptyMessageDelayed(WHAT_REFRESH_WEATHER_DATA, 200);
    }

    /**
     * refresh the weather information on this page
     */
    public void refreshWeather() {
        HttpRequestUtils.queryWeatherByCityName(mCityInfo.getCityName(), this, mRequestQueue);
    }

    /*override the method of HttpListener_start*/
    @Override
    public void onFinish(String jsonString) {
        mRefreshLayout.setRefreshing(false);
        setWeatherInformation(jsonString);
    }

    @Override
    public void onError(String error) {
        mRefreshLayout.setRefreshing(false);
        ToastUtil.showShortToast(MyApplications.getContext(), "网络异常，请稍后再尝试刷新");
    }
    /*override the method of HttpListener_end*/

    @Override
    public void onRefresh() {
        refreshWeather();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.ll_city_weather_air_quality_index){
            //Start an Activity to show more air quality index
            Intent intent = new Intent(getActivity(), AqiActivity.class);
            intent.putExtra(AqiActivity.KEY_AIR_QUALITY_INDEX, mAirQualityIndex);
            startActivity(intent);
        }else if(id == R.id.ll_city_weather_daily_weather){
            //Start an Activity to show seven daily weather,send city's Chinese name and a list with seven daily weather
            Intent intent = new Intent(getActivity(), DailyWeatherActivity.class);
            intent.putExtra(DailyWeatherActivity.KEY_CITY_NAME, mCityInfo.getCityName());
            intent.putExtra(DailyWeatherActivity.KEY_SEVEN_DAY_WEATHER, (Serializable)mDailyWeatherForecastList);
            startActivity(intent);
        }else if(id == R.id.tv_city_weather_life_suggestion){
            Intent intent = new Intent(getActivity(), LifeSuggestionActivity.class);
            intent.putExtra(LifeSuggestionActivity.KEY_LIFE_SUGGESTION, mLifeSuggestion);
            startActivity(intent);
        }
    }

    /**
     * return the information of city that this fragment is showing,
     * this method used by parent of this Fragment
     * @return name of city
     */
    public SelectedCity getCityInfo(){
        return mCityInfo;
    }

    /**
     * Return real-time-weather of current city
     * @return RealTimeWeather Object
     */
    public RealTimeWeather getRealTimeWeather(){
        return mRealTimeWeather;
    }

    //set various of weather information according JSONString
    private void setWeatherInformation(String JSON) {
        //get the JSON parse tool
        WeatherInfoJsonParseUtil weatherInfoJsonParseUtil = new WeatherInfoJsonParseUtil(JSON);

        //return directory if the parse of JSON is not successfully
        if (!weatherInfoJsonParseUtil.getStatus().equals("ok")) {
            LogUtil.i(getClass().getSimpleName() + "--the JSON string is incorrect or the pares is failed");
            return;
        }

        /*parse every JSON object that we need*/
        //get the real-time weather information
        mRealTimeWeather = weatherInfoJsonParseUtil.getRealTimeWeather();
        if (mRealTimeWeather != null) {
            RealTimeWeather.WeatherCondition condition = mRealTimeWeather.getCond();
//            mWeatherIconIv.setImageResource(WeatherIconUtil.getResourceAccordingCode(condition.getCode()));
            mWeatherConditionTv.setText(condition.getTxt());
        }

        //Get the air quality index
        mAirQualityIndex = weatherInfoJsonParseUtil.getAirQualityIndex();
        if (mAirQualityIndex != null) {
            airQualityIndexHint.setVisibility(View.VISIBLE);
            mAirQualityConditionTv.setText(mAirQualityIndex.getQlty());
            mAirQualityIndexTv.setText(mAirQualityIndex.getAqi()+"");
        }

        //Get and set Daily weather forecast
        mDailyWeatherForecastList = weatherInfoJsonParseUtil.getDailyWeatherForecast();
        if (mDailyWeatherForecastList != null) {
            //Set today's weather information
            String week;
            try {
                week = DateUtil.getWeekFromDate(mDailyWeatherForecastList.get(0).getDate(), "yyyy-MM-dd");
            }catch (ParseException e){
                week = "";
                LogUtil.i(CityWeatherFragment.class.getName()+"--日期解析发生异常");
            }
            mWeekTv.setText(week);
            // Add view to show daily weather forecast according the number of list
            if(mDailyWeatherLl.getChildCount() != 0) mDailyWeatherLl.removeAllViews();
            DailyWeatherWidget dailyWeatherWidget;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            // Start from index 1,because index 0 means today,we need start from tomorrow,
            // the max daily we can show is four
            for(int i = 1, length = mDailyWeatherForecastList.size(); i < length && i < 5; i++){
                dailyWeatherWidget = new DailyWeatherWidget(MyApplications.getContext());
                dailyWeatherWidget.setDailyWeather(mDailyWeatherForecastList.get(i));
                mDailyWeatherLl.addView(dailyWeatherWidget, layoutParams);
            }
        }

        //Set current temperature,max temperature,min temperature for the CircleTemperatureView
        if(mRealTimeWeather!=null && mDailyWeatherForecastList!=null){
            Temperature todayTemperature = mDailyWeatherForecastList.get(0).getTmp();
            mCircleTemperatureView.setTemperature(Integer.valueOf(todayTemperature.getMax()), Integer.valueOf(mRealTimeWeather.getTmp()), Integer.valueOf(todayTemperature.getMin()));
        }

        //Get and keep the life suggestion
        mLifeSuggestion = weatherInfoJsonParseUtil.getLifeSuggestion();
    }//setWeatherInformation()

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == WHAT_REFRESH_WEATHER_DATA){
                mRefreshLayout.setRefreshing(true);
                refreshWeather();
            }else{
                super.handleMessage(msg);
            }
        }
    }
}
