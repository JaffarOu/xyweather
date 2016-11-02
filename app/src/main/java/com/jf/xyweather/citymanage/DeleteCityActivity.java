package com.jf.xyweather.citymanage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.model.SelectedCity;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by JF on 2016/11/2.
 * A Activity to delete city
 */
public class DeleteCityActivity extends BaseActivity implements View.OnClickListener{

    private DeleteCityAdapter mDeleteCityAdapter;
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);
        init();
    }

    private void init(){
        myHandler = new MyHandler(this);
        //Initial data
        //Initial the Views
        findViewById(R.id.iv_city_manage_delete_city_return).setOnClickListener(this);
        findViewById(R.id.tv_city_manage_delete_city_confirm).setOnClickListener(this);
        List<SelectedCity> selectedCityList = (List<SelectedCity>)getIntent().getSerializableExtra(CityManageActivity.KEY_SELECTED_CITY_LIST);
        ListView listView = (ListView)findViewById(R.id.lv_city_manage_delete_list);
        mDeleteCityAdapter = new DeleteCityAdapter(this, selectedCityList);
        listView.setAdapter(mDeleteCityAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.iv_city_manage_delete_city_return){
            //cancel delete action
            finish();
        }else if(id == R.id.tv_city_manage_delete_city_confirm){
            //confirm delete city
            new GetSelectedCityThread(myHandler).deleteSelectedCity(mDeleteCityAdapter.getDeletedCityList());
        }
    }

    private static class MyHandler extends Handler{
        private WeakReference<DeleteCityActivity> weakReference;
        public MyHandler(DeleteCityActivity activity){
            weakReference = new WeakReference<DeleteCityActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DeleteCityActivity activity = weakReference.get();
            if(activity == null){
                super.handleMessage(msg);
                return;
            }
            if(msg.what == GetSelectedCityThread.WHAT_DELETE_SELECTED_CITY){
                Intent intent = new Intent();
                intent.putExtra(CityManageActivity.KEY_SELECTED_CITY_LIST, (Serializable)activity.mDeleteCityAdapter.getSelectedCityList());
                activity.setResult(RESULT_OK, intent);
                activity.finish();
            }
        }
    }
}
