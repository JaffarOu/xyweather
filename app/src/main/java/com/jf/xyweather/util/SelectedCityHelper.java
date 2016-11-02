package com.jf.xyweather.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.model.SelectedCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JF on 2016/6/25.
 * A SQLite database to store every city that users selected
 */
public class SelectedCityHelper extends SQLiteOpenHelper{

    private static final String SQLITE_FILE_NAME = "selectedCityFile";//the name of the SQLiteDatabase file
    private static final String SELECTED_CITY_TABLE_NAME = "selectedCity";//the name of the selected city table
    private static final String COLUMN_CITY_NAME = "cityName";//the name of column that store the name of city
    private static final String COLUMN_LAST_UPDATE_WEATHER_INFO_TIME = "lastUpdateWeatherInfoTime";

//    private static final String COLUMN_CITY_ID = "cityId";//the name of column that store the id of city
    private static final int currentVersion = 1;

    public SelectedCityHelper() {
        super(MyApplications.getContext(), SQLITE_FILE_NAME, null, currentVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSelectedCityTable = "CREATE TABLE IF NOT EXISTS "+ SELECTED_CITY_TABLE_NAME +"("+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"+","+ COLUMN_CITY_NAME +" TEXT NOT NULL,"+
                COLUMN_LAST_UPDATE_WEATHER_INFO_TIME +" TEXT);";
        db.execSQL(createSelectedCityTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * insert information of one city
     * @param selectedCity The city selected by user
     */
    public void insertCity(SelectedCity selectedCity){
        String insert = "INSERT INTO "+SELECTED_CITY_TABLE_NAME+
                "("+ COLUMN_CITY_NAME +","+ COLUMN_LAST_UPDATE_WEATHER_INFO_TIME +")"+
                "VALUES"+"("+"'"+selectedCity.getCityName()+"'"+","+"'"+selectedCity.getLastUpdateWeatherTime()+"'"+");";
        getWritableDatabase().execSQL(insert);
    }

    public List<SelectedCity> getSelectedCityList(){
        String query = "SELECT * FROM "+SELECTED_CITY_TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if(cursor.getCount() == 0){
            cursor.close();
            return null;
        }
        List<SelectedCity> cityNameList = new ArrayList<>(cursor.getCount());
        SelectedCity selectedCIty;
        while (cursor.moveToNext()){
            selectedCIty = new SelectedCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_LAST_UPDATE_WEATHER_INFO_TIME)));
            cityNameList.add(selectedCIty);
        }
        cursor.close();
        return cityNameList;
    }

    public void deleteSelectedCity(List<SelectedCity> selectedCityList){
        int length = selectedCityList.size();
        StringBuilder stringBuilder = new StringBuilder(6*length);
        for(int i = 0; i < length-1; i++){
            stringBuilder.append(COLUMN_CITY_NAME);
            stringBuilder.append(" = ");
            stringBuilder.append("'");
            stringBuilder.append(selectedCityList.get(i).getCityName());
            stringBuilder.append("'");
            stringBuilder.append(" or ");
        }
        stringBuilder.append(COLUMN_CITY_NAME);
        stringBuilder.append(" = ");
        stringBuilder.append("'");
        stringBuilder.append(selectedCityList.get(length-1).getCityName());
        stringBuilder.append("'");
        String delete = "DELETE FROM "+SELECTED_CITY_TABLE_NAME+" where "+stringBuilder.toString()+";";
        getWritableDatabase().execSQL(delete);
    }
}
