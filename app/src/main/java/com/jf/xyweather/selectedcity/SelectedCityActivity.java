package com.jf.xyweather.selectedcity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.customview.CustomTitles;
import com.jf.xyweather.model.SelectedCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/30.
 * This Activity will display all of city that selected by user
 */
public class SelectedCityActivity extends BaseActivity
        implements CustomTitles.OnTitleClickListener, AdapterView.OnItemClickListener{

    private List<SelectedCity> selectedCityList;
    public static final String KEY_SELECTED_CITY_LIST = "keySelectedCityList";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_selected_city;
    }

    @Override
    protected void initExtra() {
        selectedCityList = (List<SelectedCity>)getIntent().getSerializableExtra(KEY_SELECTED_CITY_LIST);
        //make some data for test
        selectedCityList = new ArrayList<>(4);
        selectedCityList.add(new SelectedCity("天气晴朗", "广州", "32/24"));
        selectedCityList.add(new SelectedCity("晴转多云", "深圳", "44/16"));
        selectedCityList.add(new SelectedCity("有雷阵雨", "珠海", "32/18"));
        selectedCityList.add(new SelectedCity("下冰雹啦", "江门", "32/-4"));
    }

    @Override
    protected void initView() {
        //initial the title
        CustomTitles customTitles = (CustomTitles)findViewById(R.id.custom_titles_activity_selected_city);
        customTitles.setImageViewResource(CustomTitles.LEFT_FIRST, R.drawable.ic_return);
        customTitles.setImageViewResource(CustomTitles.RIGHT_FIRST, R.drawable.ic_selected_city_activity_add);
        customTitles.setImageViewResource(CustomTitles.RIGHT_SECOND, R.drawable.ic_selected_city_activity_edit);
        customTitles.setOnTitleClickListener(this);

        //initial the "ListView"
        ListView listView = (ListView)findViewById(R.id.lv_activity_selected_city_city_list);
        if(selectedCityList != null){
            listView.setOnItemClickListener(this);
            listView.setAdapter(new SelectedCityAdapter(selectedCityList));
        }
    }

    //override the method of OnTitleClickListener
    @Override
    public void onTitleClick(View view, int which) {
        if(which == CustomTitles.LEFT_FIRST){
            finish();
            return;
        }
        if(which == CustomTitles.RIGHT_FIRST){
            //start an Activity to show all the city that can be selected
            MyApplications.showToast(this, "The function is not completed yet");
            return;
        }
        if(which == CustomTitles.RIGHT_SECOND){
            //edit the selected city
            MyApplications.showToast(this, "The function is not completed yet");
        }
    }

    //override the method of OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
