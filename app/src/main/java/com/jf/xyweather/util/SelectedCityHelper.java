package com.jf.xyweather.util;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jf.xyweather.base.MyApplications;

/**
 * Created by JF on 2016/6/25.
 * a SQLite database to store every city that users selected
 */
public class SelectedCityHelper extends SQLiteOpenHelper{

    private static final String SQLITE_FILE_NAME = "selectedCity";//the name of the SQLiteDatabase file
    private static final String SELECTED_CITY_TABLE_NAME = "selectedCity";//the name of the selected city table
    private static final String COLUMN_CITY_NAME = "cityName";//the name of the column that store city's name
    private static final String COLUMN_CITY_ID = "cityId";//the name of the column that store city's id

    public SelectedCityHelper(int version) {
        super(MyApplications.getContext(), SQLITE_FILE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSelectedCityTable = "CREATE TABLE "+ SELECTED_CITY_TABLE_NAME +" IF NOT EXIST("+
                "ID INTEGER PRIMARY NOT NULL"+","+COLUMN_CITY_NAME+" TEXT,"+
                COLUMN_CITY_ID+" TEXT);";
        db.execSQL(createSelectedCityTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
