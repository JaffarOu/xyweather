package com.jf.xyweather.util;

/**
 * Created by jf on 2016/6/21.
 * the contact in this project
 */
public class Contact {

    /** the developer key of He Feng weather*/
    public static final String API_KEY = "c3c63e854e2b4faa85fec3cb6ccfefa8";

    /**the url that used to get weather information about one city*/
    public static final String CITY_INTERFACE_URL = "https://api.heweather.com/x3/weather";


    /*the code from He Feng web to describe the request result__start*/
    /**means request successfully*/
    public static final String OK = "ok";

    /**the API_KEY is incorrect*/
    public static final String INVAILD_KEY = "invalid key";

    /**means the city code or the city name is incorrect*/
    public static final String UNKNOWN_CITY = "unknown city";

    /**means send to much request to the during the day*/
    public static final String NO_MORE_REQUESTS = "no more requests";

    /**means the web server no response*/
    public static final String WEB_SERVER_NO_RESPONSE = "anr";

    /**means we have not permission to request weather information*/
    public static final String PERMISSION_DENIED = "permission denied";
    /*the code from He Feng web to describe the request result_end*/
}
