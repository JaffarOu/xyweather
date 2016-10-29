package com.jf.xyweather.lifesuggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.model.LifeSuggestion;
import com.jf.xyweather.util.LogUtil;

/**
 * Created by JF on 2016/10/29.
 */
public class LifeSuggestionListAdapter extends BaseAdapter {

    private Context mContext;
    //Title of life suggestion type
    private String[] mLifeSuggestionTitle;
    //使用数组来表示的生活建议(LifeSuggestion)
    private LifeSuggestion.Index[] mLifeSuggestionIndex;
    private static final String DETAILS = "详情";

    public LifeSuggestionListAdapter(Context context, LifeSuggestion lifeSuggestion){
        mContext = context;
        if(lifeSuggestion == null) return;
        mLifeSuggestionTitle = new String[]{"舒适度", "洗车指数", "穿衣指数", "感冒指数", "运动指数", "旅游指数", "紫外线指数"};
        //Set dates in to an array
        mLifeSuggestionIndex = new LifeSuggestion.Index[7];
        mLifeSuggestionIndex[0] = lifeSuggestion.getComf();
        mLifeSuggestionIndex[1] = lifeSuggestion.getCw();
        mLifeSuggestionIndex[2] = lifeSuggestion.getDrsg();
        mLifeSuggestionIndex[3] = lifeSuggestion.getFlu();
        mLifeSuggestionIndex[4] = lifeSuggestion.getSport();
        mLifeSuggestionIndex[5] = lifeSuggestion.getTrav();
        mLifeSuggestionIndex[6] = lifeSuggestion.getUv();
    }

    @Override
    public int getCount() {
        return mLifeSuggestionIndex == null ? 0 : mLifeSuggestionIndex.length;
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
        TextView mLifeSuggestionTitleTv;        //title of life suggestion
        TextView mLifeSuggestionContentTv;      //details content of life suggestion
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_life_suggestion_list, null);
            viewHolder.mLifeSuggestionTitleTv = (TextView)convertView.findViewById(R.id.tv_life_suggestion_list_title);
            viewHolder.mLifeSuggestionContentTv = (TextView)convertView.findViewById(R.id.tv_life_suggestion_list_content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        LifeSuggestion.Index index =  mLifeSuggestionIndex[position];
        viewHolder.mLifeSuggestionTitleTv.setText(mLifeSuggestionTitle[position]+"："+ index.getBrf());
        viewHolder.mLifeSuggestionContentTv.setText(DETAILS+"："+index.getTxt());
        return convertView;
    }
}
