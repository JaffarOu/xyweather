package com.jf.xyweather.util;

/**
 * Created by jf on 2016/6/20.
 * A call back interface of a http request that send for get JSON
 */
public interface HttpJSONListener {

    /**
     * call when http request finish
     * @param JSONString the JSONString from web server
     */
    public void onFinish(String JSONString);

    /**
     * call when http request occur error
     * @param error the describe of the error
     */
    public void onError(String error);

}
