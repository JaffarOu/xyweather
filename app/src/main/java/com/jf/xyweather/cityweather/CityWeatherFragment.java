package com.jf.xyweather.cityweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jf.xyweather.R;
import com.jf.xyweather.airqualityindex.AqiActivity;
import com.jf.xyweather.base.fragment.BaseFragment;
import com.jf.xyweather.dailyweather.SevenDayWeatherActivity;
import com.jf.xyweather.lifesuggestion.LifeSuggestionActivity;
import com.jf.xyweather.model.AirQualityIndex;
import com.jf.xyweather.model.CityInfo;
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
import com.jf.xyweather.view.CircleTemperatureView;
import com.jf.xyweather.view.DailyWeatherWidget;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

/**
 * Created by jf on 2016/6/22.
 * The weather information about a city
 */
public class CityWeatherFragment extends BaseFragment
        implements View.OnClickListener, HttpJSONListener {

    public static final String KEY_CITY_NAME = "cityName";

    //view
    private CircleTemperatureView mCircleTemperatureView;   //An circle view used to show temperature(max,now,min)
    private ImageView mWeatherIconIv;                       //An icon used to describe the weather condition
    private TextView mWeatherConditionTv;                   //Weather condition such as sunny,windy or other
    private TextView mWeekTv;                               //week
    private TextView mAirQualityConditionTv;                //air quality condition
    private TextView mAirQualityIndexTv;                    //air quality index
    private DailyWeatherWidget[] mDailyWeatherWidgets;      //Show four days weather information in future

    //other
    private CityInfo mCityInfo;
    //To identity whether http request is finished or not before start http request
    private boolean mIsHttpFinished = true;
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
        /*Get CityInfo Object from the arguments,
        It will initial the UI and request the weather information after get CityInfo Object,
        or it will do nothing if the CityInfo == null*/
        Bundle arguments = getArguments();
        if (arguments == null) {
            LogUtil.i(getClass().getSimpleName()+"--"+"没有传入要查询的城市名字");
            return;
        }
        mCityInfo = (CityInfo) arguments.getSerializable(KEY_CITY_NAME);
        if (mCityInfo == null) {
            LogUtil.i(getClass().getSimpleName()+"--"+"没有传入要查询的城市名字");
            return;
        }

        //initial the Volley in this Fragment
        mRequestQueue = Volley.newRequestQueue(getActivity());

        initView(layoutView);
    }

    //Find view and set listener and others
    private void initView(View layoutView) {
        //initiate view
        layoutView.findViewById(R.id.ll_city_weather_air_quality_index).setOnClickListener(this);
        mCircleTemperatureView = (CircleTemperatureView)layoutView.findViewById(R.id.circle_temperature_view_city_weather);
        mWeatherIconIv = (ImageView)layoutView.findViewById(R.id.iv_city_weather_weather_icon);
        mWeatherConditionTv = (TextView)layoutView.findViewById(R.id.tv_city_weather_weather_condition);
        mWeekTv = (TextView)layoutView.findViewById(R.id.tv_city_weather_week);
        mAirQualityConditionTv = (TextView)layoutView.findViewById(R.id.tv_city_weather_air_quality_condition);
        mAirQualityIndexTv = (TextView)layoutView.findViewById(R.id.tv_city_weather_air_quality_index);
        //Four days in the future
        mDailyWeatherWidgets = new DailyWeatherWidget[4];
        mDailyWeatherWidgets[0] = (DailyWeatherWidget)layoutView.findViewById(R.id.daily_weather_widget_first);
        mDailyWeatherWidgets[1] = (DailyWeatherWidget)layoutView.findViewById(R.id.daily_weather_widget_second);
        mDailyWeatherWidgets[2] = (DailyWeatherWidget)layoutView.findViewById(R.id.daily_weather_widget_third);
        mDailyWeatherWidgets[3] = (DailyWeatherWidget)layoutView.findViewById(R.id.daily_weather_widget_forth);
        mDailyWeatherWidgets[0].setOnClickListener(this);
        mDailyWeatherWidgets[1].setOnClickListener(this);
        mDailyWeatherWidgets[2].setOnClickListener(this);
        mDailyWeatherWidgets[3].setOnClickListener(this);
        layoutView.findViewById(R.id.tv_city_weather_life_suggestion).setOnClickListener(this);
        //Get new data
        refreshWeather();
    }

    /**
     * refresh the weather information on this page
     */
    public void refreshWeather() {
        if (mIsHttpFinished) {
//            refreshHint.setVisibility(View.VISIBLE);
            mIsHttpFinished = false;//change the flag
            HttpRequestUtils.queryWeatherByCityName(mCityInfo.getCityPinYinName(), this, mRequestQueue);
        }else{
            ToastUtil.showShortToast(getActivity(), "正在拼命拉取天气信息，稍等哦亲");
        }
    }

    /*override the method of HttpListener_start*/
    @Override
    public void onFinish(String jsonString) {
        mIsHttpFinished = true;
        setWeatherInformation(jsonString);
    }

    @Override
    public void onError(String error) {
        mIsHttpFinished = true;
        ToastUtil.showShortToast(getActivity(), "网络异常，请稍后再尝试刷新");
    }
    /*override the method of HttpListener_end*/

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.ll_city_weather_air_quality_index){
            //Start an Activity to show more air quality index
            Intent intent = new Intent(getActivity(), AqiActivity.class);
            intent.putExtra(AqiActivity.KEY_AIR_QUALITY_INDEX, mAirQualityIndex);
            startActivity(intent);
        }else if(id == R.id.daily_weather_widget_first || id == R.id.daily_weather_widget_second
                || id == R.id.daily_weather_widget_third || id == R.id.daily_weather_widget_forth){
            //Start an Activity to show seven daily weather,send city's Chinese name and a list with seven daily weather
            Intent intent = new Intent(getActivity(), SevenDayWeatherActivity.class);
            intent.putExtra(SevenDayWeatherActivity.KEY_CITY_NAME, mCityInfo.getCityChineseName());
            intent.putExtra(SevenDayWeatherActivity.KEY_SEVEN_DAY_WEATHER, (Serializable)mDailyWeatherForecastList);
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
    public CityInfo getCityInfo(){
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
            mWeatherIconIv.setImageResource(WeatherIconUtil.getResourceAccordingCode(condition.getCode()));
            mWeatherConditionTv.setText(condition.getTxt());
        }

        //Get the air quality index
        mAirQualityIndex = weatherInfoJsonParseUtil.getAirQualityIndex();
        if (mAirQualityIndex != null) {
            getView().findViewById(R.id.tv_city_weather_air_quality_hint).setVisibility(View.VISIBLE);
            mAirQualityConditionTv.setText(mAirQualityIndex.getQlty());
            mAirQualityIndexTv.setText(mAirQualityIndex.getAqi()+"");
        }

        //Get seven day's daily weather forecast
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
            //Set future weather forecast
            mDailyWeatherWidgets[0].setDailyWeather(mDailyWeatherForecastList.get(1));
            mDailyWeatherWidgets[1].setDailyWeather(mDailyWeatherForecastList.get(2));
            mDailyWeatherWidgets[2].setDailyWeather(mDailyWeatherForecastList.get(3));
            mDailyWeatherWidgets[3].setDailyWeather(mDailyWeatherForecastList.get(4));
        }

        //Set current temperature,max temperature,min temperature for the CircleTemperatureView
        if(mRealTimeWeather!=null && mDailyWeatherForecastList!=null){
            Temperature todayTemperature = mDailyWeatherForecastList.get(0).getTmp();
            mCircleTemperatureView.setTemperature((int)todayTemperature.getMax(), mRealTimeWeather.getTmp(), (int)todayTemperature.getMin());
        }

        //Get and keep the life suggestion
        mLifeSuggestion = weatherInfoJsonParseUtil.getLifeSuggestion();
    }//setWeatherInformation()
}
