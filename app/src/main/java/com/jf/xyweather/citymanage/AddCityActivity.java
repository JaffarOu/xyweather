package com.jf.xyweather.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.model.SelectedCity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AddCityActivity extends BaseActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener{

    private List<String> mCityNameList;     //A list of city can be choose
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        init();
    }

    private void init(){
        myHandler = new MyHandler(this);
        //Initial data
        initData();
        //Initial the Views
        findViewById(R.id.iv_city_manage_add_city_return).setOnClickListener(this);
        GridView cityListGv = (GridView)findViewById(R.id.gv_city_manage_city_list_from_web);
        cityListGv.setOnItemClickListener(this);
        cityListGv.setAdapter(new AddCityAdapter(this, mCityNameList));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.iv_city_manage_add_city_return){
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Insert a city to database
        SelectedCity selectedCity = new SelectedCity(mCityNameList.get(position), null);
        new GetSelectedCityThread(myHandler).insertSelectedCity(selectedCity);
    }

    private void initData(){
        mCityNameList = new ArrayList<>(20);
        mCityNameList.add("北京");
        mCityNameList.add("天津");
        mCityNameList.add("上海");
        mCityNameList.add("重庆");
        mCityNameList.add("广州");
        mCityNameList.add("深圳");
        mCityNameList.add("杭州");
        mCityNameList.add("惠州");
        mCityNameList.add("昆明");
        mCityNameList.add("济南");
        mCityNameList.add("大理");
        mCityNameList.add("成都");
        mCityNameList.add("厦门");
        mCityNameList.add("南京");
        mCityNameList.add("太原");
        mCityNameList.add("乌鲁木齐");
        mCityNameList.add("长沙");
        mCityNameList.add("桂林");
        mCityNameList.add("南宁");
        mCityNameList.add("兰州");
    }

    private static class MyHandler extends Handler{

        private WeakReference<AddCityActivity> mWeakReference;

        public MyHandler(AddCityActivity activity){
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AddCityActivity addCityActivity = mWeakReference.get();
            if(addCityActivity == null){
                super.handleMessage(msg);
                return;
            }
            if(msg.what == GetSelectedCityThread.WHAT_INSERT_SELECTED_CITY){
                //Return the selected city to the starter
                Intent intent = new Intent();
                intent.putExtra(CityManageActivity.KEY_SELECTED_CITY, (SelectedCity)msg.obj);
                addCityActivity.setResult(RESULT_OK, intent);
                addCityActivity.finish();
            }
        }
    }
}
