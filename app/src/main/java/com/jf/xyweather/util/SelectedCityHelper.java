package com.jf.xyweather.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jf.xyweather.base.MyApplications;

/**
 * Created by JF on 2016/6/25.
 * A SQLite database to store every city that users selected
 */
public class SelectedCityHelper extends SQLiteOpenHelper{

    private static final String SQLITE_FILE_NAME = "selectedCity";//the name of the SQLiteDatabase file
    private static final String SELECTED_CITY_TABLE_NAME = "selectedCity";//the name of the selected city table
    private static final String COLUMN_CITY_NAME = "cityName";//the name of column that store the name of city
    private static final String COLUMN_CITY_ID = "cityId";//the name of column that store the id of city

    public SelectedCityHelper(int version) {
        super(MyApplications.getContext(), SQLITE_FILE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSelectedCityTable = "CREATE TABLE "+ SELECTED_CITY_TABLE_NAME +" IF NOT EXIST("+
                "ID INTEGER PRIMARY KEY NOT NULL"+","+COLUMN_CITY_NAME+" TEXT,"+
                COLUMN_CITY_ID+" INTEGER);";
        db.execSQL(createSelectedCityTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * insert information of one city
     * @param cityName name of city
     * @param cityId id of city
     */
    public void insertCity(String cityName, int cityId){
        String insert = "INSERT INTO "+SELECTED_CITY_TABLE_NAME+"("+"'"+cityName+"'"+","+cityId+");";
        getWritableDatabase().execSQL(insert);
    }

    /**
     * get the name of city according the id of city
     * @param cityId id of city
     * @return name of city,it will return null if the id is not exists
     */
    public String getCityName(int cityId){
        String select = "SELECT "+COLUMN_CITY_NAME+" FROM "+SELECTED_CITY_TABLE_NAME+" where "+COLUMN_CITY_ID+"="+cityId;
        Cursor cityNameCursor = getReadableDatabase().rawQuery(select, null);
        if(cityNameCursor.getCount() == 0){
            cityNameCursor.close();
            return null;
        }
        cityNameCursor.moveToFirst();
        String cityName = cityNameCursor.getString(cityNameCursor.getColumnIndex(COLUMN_CITY_NAME));
        cityNameCursor.close();
        return cityName;
    }
}
