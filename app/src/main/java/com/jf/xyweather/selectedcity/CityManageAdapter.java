package com.jf.xyweather.selectedcity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;

import java.util.List;

/**
 * Created by jf on 2016/7/1.
 * The Adapter of ListView of CityManageActivity
 */
public class CityManageAdapter extends BaseAdapter{

    private List<String> selectedCityNameList;

    public CityManageAdapter(List<String> selectedCityNameList){
        this.selectedCityNameList = selectedCityNameList;
    }

    @Override
    public int getCount() {
        return selectedCityNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return selectedCityNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView locationIv;
        TextView cityNameTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(MyApplications.getContext()).inflate(R.layout.item_activity_selected_city_list_view, null);
            viewHolder.locationIv = (ImageView)convertView.findViewById(R.id.iv_item_activity_selected_city_list_view_location);
            viewHolder.cityNameTv = (TextView)convertView.findViewById(R.id.tv_item_activity_selected_city_list_view_city_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //Showing the location icon if it is the first city
        if(position == 0){
            viewHolder.locationIv.setImageResource(R.drawable.ic_location);
            viewHolder.locationIv.setVisibility(View.VISIBLE);
        }else{
            viewHolder.locationIv.setVisibility(View.GONE);
        }
        viewHolder.cityNameTv.setText(selectedCityNameList.get(position));
        return convertView;
    }

    //return an id of icon according the weather condition of city
    private int getWeatherIcon(String cityWeatherCondition){
        return R.drawable.ic_location;
    }
}
