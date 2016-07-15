package com.jf.xyweather.airqualityindex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.model.AirQualityIndex;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jf on 2016/7/15.
 */
public class AqiActivityAdapter extends BaseAdapter{

    private String[] chemicalName;
    private String[] chineseName;
    private Map<String, String> valuesMap;//A map to connect the name and his value
    private AirQualityIndex airQualityIndex;//data

    public AqiActivityAdapter(AirQualityIndex airQualityIndex){
        this.airQualityIndex = airQualityIndex;
        chemicalName = new String[]{"PM2.5", "PM10", "NO2", "SO2", "CO", "O3"};
        chineseName = new String[]{"入肺颗粒物", "可吸入颗粒物", "二氧化氮", "二氧化硫", "一氧化碳", "臭氧"};
        initMap();
    }

    @Override
    public int getCount() {
        return chemicalName.length;
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
        TextView chemicalNameTv;
        TextView chineseNameTv;
        TextView valueTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(MyApplications.getContext()).inflate(R.layout.item_aqi_activity_grid_view, null);
            viewHolder.chemicalNameTv = (TextView)convertView.findViewById(R.id.tv_item_aqi_activity_grid_view_chemical_name);
            viewHolder.chineseNameTv = (TextView)convertView.findViewById(R.id.tv_item_aqi_activity_grid_view_chinese_name);
            viewHolder.valueTv = (TextView)convertView.findViewById(R.id.tv_item_aqi_activity_grid_view_value);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.chemicalNameTv.setText(chemicalName[position]);
        viewHolder.chineseNameTv.setText(chineseName[position]);
        viewHolder.valueTv.setText(valuesMap.get(chemicalName[position]));
        return convertView;
    }

    //To store the values according its name
    private void initMap(){
        valuesMap = new HashMap<>(chemicalName.length);
        valuesMap.put(chemicalName[0], airQualityIndex.getPm25()+"");
        valuesMap.put(chemicalName[1], airQualityIndex.getPm10()+"");
        valuesMap.put(chemicalName[2], airQualityIndex.getNo2()+"");
        valuesMap.put(chemicalName[3], airQualityIndex.getSo2()+"");
        valuesMap.put(chemicalName[4], airQualityIndex.getCo()+"");
        valuesMap.put(chemicalName[5], airQualityIndex.getO3()+"");
    }
}
