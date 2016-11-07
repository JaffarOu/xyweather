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
import com.jf.xyweather.model.Astronomy;
import com.jf.xyweather.model.DailyWeatherForecast;
import com.jf.xyweather.model.WeatherCondition;
import com.jf.xyweather.model.Wind;

/**
 * Created by jf on 2016/7/5.
 * A adapter for ListView used to show Daily weather details
 */
public class WeatherDetailsListAdapter extends BaseAdapter {

    private static final int ITEM_COUNT = 4;    //There are only four item for the time being

    private Context mContext;

    //This adapter set data in getView() method according position,uses these array to contact position and data
    private int[] mIconIds;             //The icons of the ImageView in every item
    private String[] mTopTexts;         //The text of TextView on the top of every item
    private String[] mBottomTexts;      //The text of TextView on the bottom of every item

    public WeatherDetailsListAdapter(DailyWeatherForecast dailyWeatherForecast) {
        mContext = MyApplications.getContext();

        //Initial data,the position is 0:wind,1:sunrise and sunset,2:weather condition in day and night,3:precipitation
        mIconIds =
                new int[]{R.drawable.ic_item_fragment_daily_weather_wind, R.drawable.ic_item_fragment_daily_weather_sunrise,
                        R.drawable.ic_item_fragment_daily_weather_day_night, R.drawable.ic_item_fragment_daily_weather_rain};
        Wind wind = dailyWeatherForecast.getWind();
        Astronomy astronomy = dailyWeatherForecast.getAstro();
        WeatherCondition weatherCondition = dailyWeatherForecast.getCond();
        mTopTexts = new String[]{wind.getDir(), "日出：" + astronomy.getSr(), "白天：" + weatherCondition.getTxt_d(), "降雨量：" + dailyWeatherForecast.getPcpn()};
        mBottomTexts = new String[]{"风速：" + wind.getSpd(), "日落：" + astronomy.getSs(), "夜晚：" + weatherCondition.getTxt_n(), "概率：" + dailyWeatherForecast.getPop() + "%" };
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

    private class ViewHolder {
        ImageView mImageView;
        TextView mTopTv;
        TextView mBottomTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_daily_weather_details, null);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.iv_daily_weather_details_item_icon);
            viewHolder.mTopTv = (TextView) convertView.findViewById(R.id.tv_daily_weather_details_item_top_text);
            viewHolder.mBottomTv = (TextView) convertView.findViewById(R.id.tv_daily_weather_details_item_bottom_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImageView.setImageResource(mIconIds[position]);
        viewHolder.mTopTv.setText(mTopTexts[position]);
        viewHolder.mBottomTv.setText(mBottomTexts[position]);
        return convertView;
    }
}
