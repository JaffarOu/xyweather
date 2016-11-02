package com.jf.xyweather.citymanage;

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
 * The Adapter of ListView of CityManageActivity
 */
public class CityManageAdapter extends BaseAdapter{

    private List<SelectedCity> mSelectedCityList;

    public CityManageAdapter(List<SelectedCity> selectedCityList){
        this.mSelectedCityList = selectedCityList;
    }

    @Override
    public int getCount() {
        return mSelectedCityList == null ? 0 : mSelectedCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSelectedCityList.get(position);
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
            viewHolder.locationIv = (ImageView)convertView.findViewById(R.id.iv_item_city_manage_city_list_location);
            viewHolder.cityNameTv = (TextView)convertView.findViewById(R.id.tv_item_city_manage_city_list_city_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //Showing the location icon if it is the first city
        if(position == 0){
            viewHolder.locationIv.setImageResource(R.drawable.ic_location);
            viewHolder.locationIv.setVisibility(View.VISIBLE);
            viewHolder.cityNameTv.setText("当前位置（"+ mSelectedCityList.get(0).getCityName()+"）");
        }else{
            viewHolder.locationIv.setVisibility(View.GONE);
            viewHolder.cityNameTv.setText(mSelectedCityList.get(position).getCityName());
        }
        return convertView;
    }

    public void setSelectedCityList(List<SelectedCity> selectedCityList){
        mSelectedCityList = selectedCityList;
    }

    public List<SelectedCity> getSelectedCityList(){
        return mSelectedCityList;
    }
}
