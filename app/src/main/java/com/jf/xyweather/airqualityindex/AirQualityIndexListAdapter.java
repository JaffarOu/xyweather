package com.jf.xyweather.airqualityindex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.model.AirQualityIndex;

/**
 * Created by jf on 2016/7/15.
 * An adapter for the ListView in AirQualityIndexActivity
 */
public class AirQualityIndexListAdapter extends BaseAdapter{

    private String[] nameString;                //used to get name according position
    private String[] valueString;               //used to get value according position

    public AirQualityIndexListAdapter(AirQualityIndex airQualityIndex){
        String ug = "μg/m³";
        nameString = new String[]{"PM10", "PM2.5", "NO2", "SO2", "O3", "CO"};
        valueString = new String[]{airQualityIndex.getPm10()+ug, airQualityIndex.getPm25()+ug,
                airQualityIndex.getNo2()+ug,airQualityIndex.getSo2()+ug,
                airQualityIndex.getO3()+ug, airQualityIndex.getCo()+ug};
    }

    @Override
    public int getCount() {
        //Now we have six chemical value
        return 6;
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
        TextView nameTv;
        TextView valueTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(MyApplications.getContext()).inflate(R.layout.item_air_quality_index_list, null);
            viewHolder.nameTv = (TextView)convertView.findViewById(R.id.tv_air_quality_index_name);
            viewHolder.valueTv = (TextView)convertView.findViewById(R.id.tv_air_quality_index_value);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.nameTv.setText(nameString[position]);
        viewHolder.valueTv.setText(valueString[position]+"");
        return convertView;
    }

}
