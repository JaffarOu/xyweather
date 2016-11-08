package com.jf.xyweather.citymanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.model.SelectedCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JF on 2016/11/2.
 * A Activity to delete cities
 */
public class DeleteCityAdapter extends BaseAdapter implements View.OnClickListener{

    private Context mContext;
    private List<SelectedCity> mSelectedCityList;
    private List<SelectedCity> mDeletedCityList;

    public DeleteCityAdapter(Context context, List<SelectedCity> cityNameList){
        mContext = context;
        mSelectedCityList = cityNameList;
        mDeletedCityList = new ArrayList<>();
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

    private class ViewHolder{
        TextView cityNameTv;        //city name
        ImageView deleteIv;         //delete icon
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_city_manage_delete_city, null);
            viewHolder.cityNameTv = (TextView)convertView.findViewById(R.id.tv_city_manage_delete_city_city_name);
            viewHolder.deleteIv = (ImageView)convertView.findViewById(R.id.iv_city_manage_delete_city_delete_icon);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        if(position == 0){
            viewHolder.cityNameTv.setText("当前位置"+"("+mSelectedCityList.get(position).getCityName()+")");
            viewHolder.deleteIv.setVisibility(View.GONE);
        }else {
            viewHolder.cityNameTv.setText(mSelectedCityList.get(position).getCityName());
            viewHolder.deleteIv.setVisibility(View.VISIBLE);
            viewHolder.deleteIv.setTag(position);
            viewHolder.deleteIv.setOnClickListener(this);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_city_manage_delete_city_delete_icon){
            int position = (int)v.getTag();
            mDeletedCityList.add(mSelectedCityList.remove(position));
            notifyDataSetChanged();
        }
    }

    public List<SelectedCity> getSelectedCityList(){
        return mSelectedCityList;
    }

    public List<SelectedCity> getDeletedCityList(){
        return mDeletedCityList;
    }
}
