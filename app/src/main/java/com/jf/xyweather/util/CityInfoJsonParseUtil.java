package com.jf.xyweather.util;

import com.google.gson.Gson;
import com.jf.xyweather.model.HeFengWeatherCityInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JF on 2016/10/31.
 */
public class CityInfoJsonParseUtil {

    private List<HeFengWeatherCityInfo> mHeFengWeatherCityInfo;

    public CityInfoJsonParseUtil(String cityInfoJson) throws JSONException{
        if(cityInfoJson == null) return;
        JSONObject jsonObject = new JSONObject(cityInfoJson);
        JSONArray jsonArray = jsonObject.getJSONArray("city_info");
        mHeFengWeatherCityInfo = new ArrayList<>(jsonArray.length());
    }

    public List<HeFengWeatherCityInfo> getHeFengWeatherCityInfoList() {
        if (mHeFengWeatherCityInfo == null || mHeFengWeatherCityInfo.size() == 0) return null;
        return mHeFengWeatherCityInfo;
    }
}
