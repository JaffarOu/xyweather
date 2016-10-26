package com.jf.xyweather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.model.RealTimeWeather;

/**
 * Created by JF on 2016/6/24.
 * A short-term-forecast widget in the "CityWeatherFragment"
 */
public class RealTimeWidget extends LinearLayout
        implements Animation.AnimationListener, Runnable{

    /*View widgets in "RealTimeWidget"__start*/
    private TextView weatherTypeTv;//TextView to show weather type(such as sunny or cloudy)
    private TextView temperatureTv;

    /*viewGroup include the "windOrBodyFeelingTv" and "humilityOrAirPressureTv",
      viewGroup has an animation*/
    private ViewGroup viewGroup;
    private TextView windOrBodyFeelingTv;
    private TextView humilityOrAirPressureTv;
    /*View widgets in "RealTimeWidget"__end*/

    /*data of RealTimeWeather_start*/
    private RealTimeWeather realTimeWeather;
    private String wind;
    private float humility;
    private float bodyFeelingTemperature;
    private float airPressure;
    /*data of RealTimeWeather_end*/

    private AlphaAnimation becomeTransparent;
    private AlphaAnimation becomeNoTransparent;

    private String showWhichInfo = SHOW_BODY_FELLING_AND_AIR_PRESSURE;
    private static final String SHOW_WIND_AND_HUMILITY = "windAndHumility";
    private static final String SHOW_BODY_FELLING_AND_AIR_PRESSURE = "bodyFellingAndAirPressure";

    public RealTimeWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        inflate(context, R.layout.layout_real_time_weather, this);

        //find view
        weatherTypeTv = (TextView) findViewById(R.id.tv_custom_view_real_time_weather_weather_type);
        temperatureTv = (TextView) findViewById(R.id.tv_layout_real_time_weather_temperature);
        viewGroup = (ViewGroup)findViewById(R.id.rl_layout_real_time_weather);
        windOrBodyFeelingTv = (TextView) findViewById(R.id.tv_layout_real_time_weather_wind_or_body_feeling_temperature);
        humilityOrAirPressureTv = (TextView)findViewById(R.id.tv_layout_real_time_weather_humility_or_air_pressure);
    }

    /**
     * Set real-time weather for "RealTimeWidget"
     * @param realTimeWeather
     */
    public void setRealTimeWeather(RealTimeWeather realTimeWeather){
        if(realTimeWeather == null){
            return;
        }
        //store the data
        this.realTimeWeather = realTimeWeather;
        this.wind = realTimeWeather.getWind().getSc();
        this.humility = realTimeWeather.getHum();
        this.bodyFeelingTemperature = realTimeWeather.getFl();
        this.airPressure = realTimeWeather.getPres();

        //show data
        weatherTypeTv.setText(realTimeWeather.getCond().getTxt());
        temperatureTv.setText(realTimeWeather.getTmp()+"°");
        if(becomeTransparent == null){
            setWindText(wind);
            setHumilityText(humility);
            initAnimation();
            viewGroup.startAnimation(becomeTransparent);
        }
    }

    private void setWindText(String wind){
        windOrBodyFeelingTv.setText(wind+"级");
    }
    private void setHumilityText(float humility){
        humilityOrAirPressureTv.setText((int)humility+"%");
    }
    private void setBodyFeelingText(float bodyFeelingTemperature){
        windOrBodyFeelingTv.setText((int)bodyFeelingTemperature+"℃");
    }
    private void setAirPressureText(float airPressure){
        humilityOrAirPressureTv.setText((int)airPressure+"");
    }

    //initial the animation for the viewGroup
    private void initAnimation(){
        //initial an animation to make viewGroup become transparent
        becomeTransparent = new AlphaAnimation(1f, 0f);
        becomeTransparent.setDuration(2000);
        becomeTransparent.setAnimationListener(this);

        //initial an animation to make viewGroup become no transparent
        becomeNoTransparent = new AlphaAnimation(0f, 1f);
        becomeNoTransparent.setDuration(1000);
        becomeNoTransparent.setAnimationListener(this);
    }

    /*override the method of "AnimationListener"_start*/
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(becomeTransparent == animation){
            //if the viewGroup become transparent,determined which information should be showed when it become no transparent again
            if(showWhichInfo.equals(SHOW_WIND_AND_HUMILITY)){
                setWindText(wind);
                setHumilityText(humility);
                showWhichInfo = SHOW_BODY_FELLING_AND_AIR_PRESSURE;
            }else{
                setBodyFeelingText(bodyFeelingTemperature);
                setAirPressureText(airPressure);
                showWhichInfo = SHOW_WIND_AND_HUMILITY;
            }
            viewGroup.startAnimation(becomeNoTransparent);
        }else{
            //if the viewGroup become no transparent,post a runnable to make it become transparent after four seconds
            postDelayed(this, 2000);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    /*override the method of "AnimationListener"_end*/

    //override the method of interface Runnable
    @Override
    public void run() {
        viewGroup.startAnimation(becomeTransparent);
    }
}
