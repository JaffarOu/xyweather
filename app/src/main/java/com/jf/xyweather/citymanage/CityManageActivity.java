package com.jf.xyweather.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.view.CustomTitles;
import com.jf.xyweather.model.CityInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/30.
 * This Activity will display all of city that selected by user
 * and user can manager(add or remove) city in this Activity
 */
public class CityManageActivity extends BaseActivity
        implements CustomTitles.OnTitleClickListener, AdapterView.OnItemClickListener{

    private List<CityInfo> cityNameList;
    public static final String KEY_SELECTED_CITY_LIST = "keySelectedCityList";

    public static final String ACTION_SELECT_CITY = "action_select_city";
    public static final String ACTION_DELETE_CITY = "action_delete_city";
    public static final String SELECT_POSITION = "selectPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manage);
        initOther();
        initView();
    }

    private void initOther() {
        cityNameList = (List<CityInfo>)getIntent().getSerializableExtra(KEY_SELECTED_CITY_LIST);
    }

    private void initView() {
        //initial the title
        CustomTitles customTitles = (CustomTitles)findViewById(R.id.custom_titles_activity_city_manage);
        customTitles.setImageViewResource(CustomTitles.LEFT_FIRST, R.drawable.ic_return);
        customTitles.setImageViewResource(CustomTitles.RIGHT_FIRST, R.drawable.ic_selected_city_activity_add);
        customTitles.setImageViewResource(CustomTitles.RIGHT_SECOND, R.drawable.ic_selected_city_activity_edit);
        customTitles.setOnTitleClickListener(this);

        //initial the "ListView"
        ListView listView = (ListView)findViewById(R.id.lv_activity_city_manage_city_list);
        if(cityNameList != null){
            listView.setOnItemClickListener(this);
            List<String> cityNames = new ArrayList<>(cityNameList.size());
            for(CityInfo cityName:cityNameList){
                cityNames.add(cityName.getCityChineseName());
            }
            listView.setAdapter(new CityManageAdapter(cityNames));
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
        //telling the starter that which city used by user
        Intent intent = new Intent(ACTION_SELECT_CITY);
        intent.putExtra(SELECT_POSITION, position);
        setResult(RESULT_OK, intent);
        finish();
    }
}
