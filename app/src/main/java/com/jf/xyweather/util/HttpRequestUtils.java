package com.jf.xyweather.util;

import com.jf.xyweather.base.MyApplications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jf on 2016/6/20.
 * A tool class that use to send http request
 */
public class HttpRequestUtils {

    /**
     * send a http request with "get" method
     * @param cityName The name of the city that you want to get the weather information
     * @param httpListener http request result listener
     */
    public static void sendWeatherInfoRequest(String cityName, HttpListener httpListener){
//        String httpUrl = "https://api.heweather.com/x3/weather?city="+cityName+"&key="+ Contact._apiKey;
        String httpUrl = Contact.CITY_INTERFACE_URL+"?"+"city="+cityName+"&key="+Contact.API_KEY;
        //print the http request url make our debug easy
        MyApplications.showLog(httpUrl);
        String result = null;//result of http request
        BufferedReader bfrd = null;
        StringBuffer sb = new StringBuffer();
        boolean hasException = false;//if occur exception ever
        String threadString = null;//the information of thread
        try{
            //send the http request
            URL url = new URL(httpUrl);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            //get the http result
            InputStream is = connection.getInputStream();
            bfrd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String httpString = null;
            while( (httpString = bfrd.readLine()) != null ){
                sb.append(httpString);
                sb.append("\r\n");
            }
            result = sb.toString();
        }catch (IOException e){
            hasException = true;
            threadString = e.toString();
        }finally {
            if(bfrd != null){
                try{
                    bfrd.close();
                }catch (IOException e){
                    MyApplications.showLog(HttpRequestUtils.class.getSimpleName()+"-关流失败");
                }
            }
        }//try{}
        if(httpListener == null){
            return;
        }
        if(hasException){
            httpListener.onError(threadString);
            MyApplications.showLog(HttpRequestUtils.class.getSimpleName()+"请求异常-"+threadString);
            return;
        }
        httpListener.onFinish(result);
    }//_sendHttpGetRequest()
}
