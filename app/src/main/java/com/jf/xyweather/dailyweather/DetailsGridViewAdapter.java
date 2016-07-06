package com.jf.xyweather.dailyweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.model.WeatherCondition;
import com.jf.xyweather.model.Wind;

/**
 * Created by jf on 2016/7/5.
 * A adapter work for GridView in DailyWeatherFragment
 */
public class DetailsGridViewAdapter extends BaseAdapter{

    private Context context;
    private DailyWeatherForecast dailyWeatherForecast;//data

    //This adapter set data in getView() method according the position,this constants said the meaning of each position
    private static final int POSITION_WIND = 0;
    private static final int POSITION_SUN = 1;
    private static final int POSITION_WEATHER_CONDITION = 2;
    private static final int POSITION_RAINFALL = 3;
    private static final int ITEM_COUNT = 4;//There are only four item for the time being

    public DetailsGridViewAdapter(){
        context = MyApplications.getContext();
    }

    public DetailsGridViewAdapter(DailyWeatherForecast dailyWeatherForecast){
        context = MyApplications.getContext();
        this.dailyWeatherForecast = dailyWeatherForecast;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView topTv;
        TextView bottomTv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_daily_weather, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.iv_item_fragment_daily_weather);
            viewHolder.topTv = (TextView)convertView.findViewById(R.id.tv_item_fragment_daily_weather_top_text);
            viewHolder.bottomTv = (TextView)convertView.findViewById(R.id.tv_item_fragment_daily_weather_bottom_text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        switch (position){
            //Show information about wind
            case POSITION_WIND:
                Wind wind = dailyWeatherForecast.getWind();
                viewHolder.imageView.setImageResource(R.drawable.ic_item_fragment_daily_weather_wind);
                viewHolder.topTv.setText(wind.getDir());
                viewHolder.bottomTv.setText("风速："+wind.getSpd()+"");
                break;
            //Show the time of sunrise and sunset
            case POSITION_SUN:
                viewHolder.imageView.setImageResource(R.drawable.ic_item_fragment_daily_weather_sunrise);
                viewHolder.topTv.setText("日出："+dailyWeatherForecast.getAstro().getSr());
                viewHolder.bottomTv.setText("日落："+dailyWeatherForecast.getAstro().getSs());
                break;
            //Show condition of weather
            case POSITION_WEATHER_CONDITION:
                WeatherCondition weatherCondition = dailyWeatherForecast.getCond();
                viewHolder.imageView.setImageResource(R.drawable.ic_item_fragment_daily_weather_day_night);
                viewHolder.topTv.setText("白天："+weatherCondition.getTxt_d());
                viewHolder.bottomTv.setText("夜晚："+weatherCondition.getTxt_n());
                break;
            //Show information about rainfall
            case POSITION_RAINFALL:
                viewHolder.imageView.setImageResource(R.drawable.ic_item_fragment_daily_weather_rain);
                viewHolder.topTv.setText("降雨量："+dailyWeatherForecast.getPcpn());
                viewHolder.bottomTv.setText("概率："+(int)dailyWeatherForecast.getPop()+"%");
                break;
            default:break;
        }
        return convertView;
    }

    /**
     * Set daily-weather forecast for adapter
     * @param dailyWeatherForecast
     */
    public void setData(DailyWeatherForecast dailyWeatherForecast){
        this.dailyWeatherForecast = dailyWeatherForecast;
    }
}
