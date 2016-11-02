package com.jf.xyweather.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.model.SelectedCity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jf on 2016/6/30.
 * This Activity will display all of city that selected by user
 * and user can manager(add or remove) city in this Activity
 */
public class CityManageActivity extends BaseActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener{

    /*The action used to describe what this Activity do__start*/
    /**If user select a city*/
    public static final String ACTION_SELECT_CITY = "action_select_city";
    /**If user add or delete city*/
    public static final String ACTION_UPDATE_SELECTED_CITY_LIST = "action_update_selected_city_list";
    /*The action used to describe what this Activity do__end*/

    /*The key used in the Intent Object__start*/
    /**The position that user selected*/
    public static final String KEY_SELECTED_POSITION = "keySelectedPosition";

    /**The selected city object*/
    public static final String KEY_SELECTED_CITY = "keySelectedCity";

    /**We will return a new list of city if user add or delete a city*/
    public static final String KEY_SELECTED_CITY_LIST = "keySelectedCityList";
    /*The key used in the Intent Object__end*/

    private static final int REQUEST_CODE_ADD_CITY = 1<<1;
    private static final int REQUEST_CODE_DELETE_CITY = 1<<2;

    private CityManageAdapter mCityManageAdapter;
    private boolean isSelectedCityListChange = false;
    List<SelectedCity> mSelectedCityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manage);
        initView();
    }

    private void initView() {
        //initial the title
        findViewById(R.id.tv_city_manage_add_city).setOnClickListener(this);
        findViewById(R.id.tv_city_manage_delete_city).setOnClickListener(this);
//        findViewById(R.id.tv_city_manage_setting).setOnClickListener(this);

        //Initial the "ListView"
        mSelectedCityList = (List<SelectedCity>)getIntent().getSerializableExtra(KEY_SELECTED_CITY_LIST);
        ListView listView = (ListView)findViewById(R.id.lv_activity_city_manage_city_list);
        if(mSelectedCityList != null){
            listView.setOnItemClickListener(this);
            mCityManageAdapter = new CityManageAdapter(mSelectedCityList);
            listView.setAdapter(mCityManageAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_city_manage_add_city){
            //Start an Activity to show a list of cities
            startActivityForResult(new Intent(this, AddCityActivity.class), REQUEST_CODE_ADD_CITY);
        }else if(id == R.id.tv_city_manage_delete_city){
            //Start an Activity to delete city
            Intent intent = new Intent(this, DeleteCityActivity.class);
            intent.putExtra(KEY_SELECTED_CITY_LIST, (Serializable)mSelectedCityList);
            startActivityForResult(intent, REQUEST_CODE_DELETE_CITY);
        }
    }

    //override the method of OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //If user has add or delete city ever
        Intent intent;
        if(isSelectedCityListChange){
            intent = new Intent(ACTION_UPDATE_SELECTED_CITY_LIST);
            intent.putExtra(KEY_SELECTED_POSITION, position);
            intent.putExtra(KEY_SELECTED_CITY_LIST, (Serializable)mCityManageAdapter.getSelectedCityList());
        }else{
            //Telling the starter that which city used by user
            intent = new Intent(ACTION_SELECT_CITY);
            intent.putExtra(KEY_SELECTED_POSITION, position);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) return;
        if(requestCode == REQUEST_CODE_ADD_CITY && data != null){
            //If user add one city,set the flag that list of selected city is changed,update the list
            isSelectedCityListChange = true;
            mSelectedCityList = mCityManageAdapter.getSelectedCityList();
            mSelectedCityList.add((SelectedCity)data.getSerializableExtra(KEY_SELECTED_CITY));
            mCityManageAdapter.setSelectedCityList(mSelectedCityList);
            mCityManageAdapter.notifyDataSetChanged();
        }else if(requestCode == REQUEST_CODE_DELETE_CITY && data != null){
            //If user delete one city,set the flag that list of selected city is changed,update the list
            isSelectedCityListChange = true;
            mSelectedCityList = (List<SelectedCity>)data.getSerializableExtra(KEY_SELECTED_CITY_LIST);
            mCityManageAdapter.setSelectedCityList(mSelectedCityList);
            mCityManageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent;
            if(isSelectedCityListChange){
                intent = new Intent(ACTION_UPDATE_SELECTED_CITY_LIST);
                intent.putExtra(KEY_SELECTED_CITY_LIST, (Serializable)mCityManageAdapter.getSelectedCityList());
                setResult(RESULT_OK, intent);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
