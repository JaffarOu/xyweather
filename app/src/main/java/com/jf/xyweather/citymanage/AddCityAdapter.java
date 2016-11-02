package com.jf.xyweather.citymanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jf.xyweather.R;

import java.util.List;

/**
 * Created by JF on 2016/11/1.
 * Show a list of cities in AddCityActivity
 */
public class AddCityAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> mCityNameList;

    public AddCityAdapter(Context context, List<String> cityList){
        mContext = context;
        mCityNameList = cityList;
    }

    @Override
    public int getCount() {
        return mCityNameList == null ? 0 : mCityNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCityNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView cityNameTv;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_city_manage_add_city, null);
            cityNameTv = (TextView)convertView.findViewById(R.id.tv_city_manage_city_name);
            convertView.setTag(cityNameTv);
        }else{
            cityNameTv = (TextView)convertView.getTag();
        }
        cityNameTv.setText(mCityNameList.get(position));
        return convertView;
    }
}
