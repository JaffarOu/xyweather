package com.jf.xyweather.util;

import com.jf.xyweather.view.DailyWeatherWidget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by JF on 2016/10/25.
 * Some method about Date
 */
public class DateUtil {

    private DateUtil(){
        throw new UnsupportedOperationException("DateUtil is just a tool class and it can't be create Object");
    }

    private static final String[] weekStrings = new String[]{"周日","周一","周二","周三", "周四", "周五", "周六"};

    /**
     * Get week according date
     * @param date String used express date such as "2016-10-24"
     * @return Weekday like 周一，周二
     */
    public static String getWeekFromDate(String date, String format) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(date));
        return weekStrings[calendar.get(Calendar.DAY_OF_WEEK)-1];
    }
}
