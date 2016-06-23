package com.jf.xyweather.util;

import android.os.Handler;
import android.os.Message;

import com.jf.xyweather.main.WelcomeActivity;

/**
 * Created by jf on 2016/6/20.
 */
public class HttpThread extends Thread implements HttpListener{

    public static final int HTTP_REQUEST_ON_FINISH = 0;
    public static final int HTTP_REQUEST_ON_ERROR = 1;
    public static final int HTTP_REQUEST_ON_NO_RESPONSE = 1<<1;

    private Handler handler;
    private String cityName;

    public HttpThread(String cityName, Handler handler){
        this.handler = handler;
        this.cityName = cityName;
    }

    @Override
    public void run() {
        HttpRequestUtils.sendWeatherInfoRequest(cityName, this);
    }

    /*override the HttpListener method__start*/
    @Override
    public void onFinish(String result) {
        Message message = new Message();
        message.what = HTTP_REQUEST_ON_FINISH;
        message.obj = result;//the result of the http request
        handler.sendMessage(message);
    }

    @Override
    public void onError(String error) {
        Message message = new Message();
        message.what = HTTP_REQUEST_ON_ERROR;
        message.obj = error;
        handler.sendMessage(message);
    }

    @Override
    public void onNoResponse() {
        handler.sendEmptyMessage(HTTP_REQUEST_ON_NO_RESPONSE);
    }
    /*override the HttpListener method_end*/
}
