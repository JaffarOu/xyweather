package com.jf.xyweather.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.model.CityInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2016/6/30.
 * This Activity will display all of city that selected by user
 * and user can manager(add or remove) city in this Activity
 */
public class CityManageActivity extends BaseActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener{

    /*The action used to describe what this Activity do in Intent Object__start*/
    /**If user select a city*/
    public static final String ACTION_SELECT_CITY = "action_select_city";
    /**If user add a new city*/
    public static final String ACTION_ADD_CITY = "action_add_city";
    /**If user delete a exists city*/
    public static final String ACTION_DELETE_CITY = "action_delete_city";
    /*The action used to describe what this Activity do in Intent Object__start*/

    /*The key used in the Intent Object__start*/
    /**The position that user selected*/
    public static final String KEY_SELECTED_POSITION = "keySelectedPosition";
    /**We will return a new list of CityInfo Object if user add or delete a city*/
    public static final String KEY_CITY_INFO_LIST = "keyCityInfoList";
    /*The key used in the Intent Object__end*/

    private List<CityInfo> mSrcCityInfoList;        //The list of CityInfo from starter
    private List<CityInfo> mResultCityInfoList;     //The result of CityInfo from user selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manage);
        mSrcCityInfoList = (List<CityInfo>)getIntent().getSerializableExtra(KEY_CITY_INFO_LIST);
        initView();
    }

    private void initView() {
        //initial the title
        findViewById(R.id.tv_city_manage_add_city).setOnClickListener(this);
        findViewById(R.id.tv_city_manage_delete_city).setOnClickListener(this);
//        findViewById(R.id.tv_city_manage_setting).setOnClickListener(this);

        //Initial the "ListView"
        ListView listView = (ListView)findViewById(R.id.lv_activity_city_manage_city_list);
        if(mSrcCityInfoList != null){
            listView.setOnItemClickListener(this);
            listView.setAdapter(new CityManageAdapter(mSrcCityInfoList));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_city_manage_add_city){
            //Start an Activity to show a list of cities
        }else if(id == R.id.tv_city_manage_delete_city){
            //Start an Activity to delete city
        }
    }

    //override the method of OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Telling the starter that which city used by user
        Intent intent = new Intent(ACTION_SELECT_CITY);
        intent.putExtra(KEY_SELECTED_POSITION, position);
        setResult(RESULT_OK, intent);
        finish();
    }

}
