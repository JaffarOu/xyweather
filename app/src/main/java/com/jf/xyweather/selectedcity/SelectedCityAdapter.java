package com.jf.xyweather.selectedcity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.model.SelectedCity;

import java.util.List;

/**
 * Created by jf on 2016/7/1.
 */
public class SelectedCityAdapter extends BaseAdapter{

    private List<SelectedCity> selectedCityList;

    public SelectedCityAdapter(List<SelectedCity> selectedCityList){
        this.selectedCityList = selectedCityList;
    }

    @Override
    public int getCount() {
        return selectedCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return selectedCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView weatherIv;
        ImageView locationIv;
        TextView cityNameTv;
        TextView temperatureTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(MyApplications.getContext()).inflate(R.layout.item_activity_selected_city, null);
            viewHolder.weatherIv = (ImageView)convertView.findViewById(R.id.iv_item_activity_selected_city_weather_icon);
            viewHolder.locationIv = (ImageView)convertView.findViewById(R.id.iv_item_activity_selected_city_location);
            viewHolder.cityNameTv = (TextView)convertView.findViewById(R.id.tv_item_activity_selected_city_city_name);
            viewHolder.temperatureTv = (TextView)convertView.findViewById(R.id.tv_item_activity_selected_city_temperature);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //Set weather icon according the weather condition of city
        viewHolder.weatherIv.setImageResource(getWeatherIcon(selectedCityList.get(position).getCityWeatherCondition()));
        //Showing the location icon if it is the first city
        if(position == 0){
            viewHolder.locationIv.setImageResource(R.drawable.ic_location);
            viewHolder.locationIv.setVisibility(View.VISIBLE);
        }else{
            viewHolder.locationIv.setVisibility(View.GONE);
        }
        viewHolder.cityNameTv.setText(selectedCityList.get(position).getCityName());
        viewHolder.temperatureTv.setText(selectedCityList.get(position).getTemperature()+"℃");
        return convertView;
    }

    //return an id of icon according the weather condition of city
    private int getWeatherIcon(String cityWeatherCondition){
        return R.drawable.ic_location;
    }
}
