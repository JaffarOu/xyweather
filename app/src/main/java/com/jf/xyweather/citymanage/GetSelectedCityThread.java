package com.jf.xyweather.citymanage;

import android.os.Handler;
import android.os.Message;

import com.jf.xyweather.model.SelectedCity;
import com.jf.xyweather.util.SelectedCityHelper;

import java.util.List;

/**
 * Created by JF on 2016/11/1.
 * A work thread to get information about selected city
 */
public class GetSelectedCityThread extends Thread {

    /*It may be various information about selected city, these flag used to identify*/
    /**Get all selected city*/
    public static final int WHAT_SELECTED_CITY_LIST = 1<<1;
    /**Insert a city in to database*/
    public static final int WHAT_INSERT_SELECTED_CITY = 1<<2;
    /**Delete a city from database*/
    public static final int WHAT_DELETE_SELECTED_CITY = 1<<3;

    private Handler mHandler;
    private int mType;
    private SelectedCity mSelectedCity;
    private List<SelectedCity> mDeleteCityList;

    /**
     * Create a work thread to read selected city information
     * @param handler
     */
    public GetSelectedCityThread(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void run() {
        if(mType == WHAT_SELECTED_CITY_LIST){
            List<SelectedCity> selectedCityList = new SelectedCityHelper().getSelectedCityList();
            Message msg = Message.obtain();
            msg.what = WHAT_SELECTED_CITY_LIST;
            //A list of selected cities
            msg.obj = selectedCityList;
            mHandler.sendMessage(msg);
            mHandler = null;
        }else if(mType == WHAT_INSERT_SELECTED_CITY){
            new SelectedCityHelper().insertCity(mSelectedCity);
            Message msg = Message.obtain();
            msg.what = WHAT_INSERT_SELECTED_CITY;
            //The city that was inserted
            msg.obj = mSelectedCity;
            mHandler.sendMessage(msg);
            mHandler = null;
        }else if(mType == WHAT_DELETE_SELECTED_CITY){
            new SelectedCityHelper().deleteSelectedCity(mDeleteCityList);
            Message msg = Message.obtain();
            msg.what = WHAT_DELETE_SELECTED_CITY;
            mHandler.sendMessage(msg);
            mHandler = null;
        }
    }

    /**
     * Get a list of selected city,
     * if you call this method,you can receive the list of selected from message.obj
     */
    public void getSelectedCityList(){
        mType = WHAT_SELECTED_CITY_LIST;
        start();
    }

    /**
     * Insert a selected city in to database,
     * it will return back the city to you in the message.obj
     * @param selectedCity The selected city you want to insert
     */
    public void insertSelectedCity(SelectedCity selectedCity){
        mType = WHAT_INSERT_SELECTED_CITY;
        mSelectedCity = selectedCity;
        start();
    }

    public void deleteSelectedCity(List<SelectedCity> deleteCityList){
        mType = WHAT_DELETE_SELECTED_CITY;
        mDeleteCityList = deleteCityList;
        start();
    }
}
