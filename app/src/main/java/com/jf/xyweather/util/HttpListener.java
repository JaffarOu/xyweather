package com.jf.xyweather.util;

/**
 * Created by jf on 2016/6/20.
 * A call back interface for http request
 */
public interface HttpListener {

    /**
     * call when http request finish
     * @param result the result from web server
     */
    public void onFinish(String result);

    /**
     * call when http request occur error
     * @param error the describe of the error
     */
    public void onError(String error);

    /**
     * call when web server no response the http request
     */
    public void onNoResponse();
}
