package com.jf.xyweather.util;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jf.xyweather.base.MyApplications;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jf on 2016/6/20.
 * A tool class that used to send http request
 */
public class HttpRequestUtils {

    /**
     * query weather information of a city according name of the city
     * @param cityName
     * @param httpJSONListener
     * @param requestQueue
     */
    public static void queryWeatherByCityName(String cityName, HttpJSONListener httpJSONListener, RequestQueue requestQueue){
        Map<String,String> parameter = new HashMap<>(2);
        //put the name of city and developer's key of "He Feng weather"
        parameter.put("city", cityName);
        parameter.put("key", Contact.API_KEY);
        sendHttpGetForJSON(Contact.CITY_WEATHER_URL, parameter, httpJSONListener, requestQueue);
    }

    public static void queryWeatherByCityIp(String cityIp, HttpJSONListener httpJSONListener, RequestQueue requestQueue){
        Map<String,String> parameter = new HashMap<>(2);
        parameter.put("cityip",cityIp);
        parameter.put("key", Contact.API_KEY);
        sendHttpGetForJSON(Contact.CITY_WEATHER_URL, parameter, httpJSONListener, requestQueue);
    }

    public static void queryCityByType(String cityType, HttpJSONListener httpListener, RequestQueue requestQueue){
        Map<String,String> parameter = new HashMap<>(2);
        parameter.put("search", cityType);
        parameter.put("key" ,Contact.API_KEY);
        sendHttpGetForJSON(Contact.CITY_TYPE_URL, parameter, httpListener, requestQueue);
    }

    public static void sendHttpPostForJSON(String url, HttpJSONListener httpListener, Map<String, String> requestParameter){

    }

    /**
     * send a Http request using GET method to get JSON string
     * @param url url that need to access
     * @param parameter parameter of GET method
     * @param httpListener callback interface of http request
     * @param requestQueue requestQueue from Volley
     */
    public static void sendHttpGetForJSON(String url, Map<String, String> parameter, final HttpJSONListener httpListener, RequestQueue requestQueue){
        String finalUrl;
        if(parameter == null){
            finalUrl = url;
        }else{
            finalUrl = getHttpGetUrl(url, parameter);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, finalUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        httpListener.onFinish(jsonObject.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        MyApplications.showLog(HttpRequestUtils.class.getSimpleName()+"--sendHttpGetForJSON()方法异常");
                        httpListener.onError(volleyError.getMessage());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    //return http get url that with parameter
    private static String getHttpGetUrl(String url, Map<String, String> requestParameter){
        //build the http get url
        String finalUrl;//url and parameter
        int size = requestParameter.size();
        //every recycle will call stringBuilder.append() method four times
        StringBuilder parameter = new StringBuilder(size*4);
        int i = 1;
        for(Map.Entry<String, String> entry:requestParameter.entrySet()){
            if(i<size){
                parameter.append(entry.getKey());
                parameter.append("=");
                parameter.append(entry.getValue());
                parameter.append("&");
                i++;
            }else{
                parameter.append(entry.getKey());
                parameter.append("=");
                parameter.append(entry.getValue());
            }
        }
        finalUrl = url+"?"+parameter.toString();
        return finalUrl;
    }
}
