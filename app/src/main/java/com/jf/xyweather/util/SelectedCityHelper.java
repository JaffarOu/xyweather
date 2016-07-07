package com.jf.xyweather.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.model.CityName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JF on 2016/6/25.
 * A SQLite database to store every city that users selected
 */
public class SelectedCityHelper extends SQLiteOpenHelper{

    private static final String SQLITE_FILE_NAME = "selectedCity";//the name of the SQLiteDatabase file
    private static final String SELECTED_CITY_TABLE_NAME = "selectedCity";//the name of the selected city table
    private static final String COLUMN_CITY_CHINESE_NAME = "cityChineseName";//the name of column that store the name of city
    private static final String COLUMN_CITY_PIN_YIN_NAME = "cityPinYinName";
//    private static final String COLUMN_CITY_ID = "cityId";//the name of column that store the id of city
    private static final int currentVersion = 1;

    public SelectedCityHelper() {
        super(MyApplications.getContext(), SQLITE_FILE_NAME, null, currentVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSelectedCityTable = "CREATE TABLE IF NOT EXISTS "+ SELECTED_CITY_TABLE_NAME +"("+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"+","+ COLUMN_CITY_CHINESE_NAME +" TEXT,"+
                COLUMN_CITY_PIN_YIN_NAME+" TEXT);";
        db.execSQL(createSelectedCityTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * insert information of one city
     * @param cityChineseName name of city
     * @param cityPinYinName id of city
     */
    public void insertCity(String cityChineseName, String cityPinYinName){
        String insert = "INSERT INTO "+SELECTED_CITY_TABLE_NAME+
                "("+COLUMN_CITY_CHINESE_NAME+","+COLUMN_CITY_PIN_YIN_NAME+")"+
                "VALUES"+"("+"'"+cityChineseName+"'"+","+"'"+cityPinYinName+"'"+");";
        getWritableDatabase().execSQL(insert);
    }

    public List<CityName> getCityNameList(){
        String query = "SELECT * FROM "+SELECTED_CITY_TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if(cursor.getCount() == 0){
            cursor.close();
            return null;
        }
        List<CityName> cityNameList = new ArrayList<>(cursor.getCount());
        CityName cityName;
        while (cursor.moveToNext()){
            cityName = new CityName(cursor.getString(cursor.getColumnIndex(COLUMN_CITY_CHINESE_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_CITY_PIN_YIN_NAME)));
            cityNameList.add(cityName);
        }
        cursor.close();
        return cityNameList;
    }
}
