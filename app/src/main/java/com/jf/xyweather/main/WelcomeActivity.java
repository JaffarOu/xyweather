package com.jf.xyweather.main;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.jf.xyweather.R;
import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.base.activity.BaseActivity;
import com.jf.xyweather.util.HttpThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by jf on 2016/6/20.
 * 主模块-欢迎界面
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener{

    public static final int HTTP_REQUEST_ON_FINISH = 0;
    public static final int HTTP_REQUEST_ON_ERROR = 1;
    public static final int HTTP_REQUEST_ON_NO_RESPONSE = 1<<1;

    private String weatherForeCastJson;
    private TextView contentTv;
    private HttpHandler httpHandler;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initExtra() {
        httpHandler = new HttpHandler(this);
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_activity_welcome_get_weather_json).setOnClickListener(this);
        findViewById(R.id.btn_activity_welcome_parse_json).setOnClickListener(this);
        contentTv = (TextView)findViewById(R.id.tv_activity_welcome_json_resource);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_activity_welcome_get_weather_json:
                contentTv.setText("请求中");
                String city = "jiangmen";
                new HttpThread(city, httpHandler).start();
                break;
            case R.id.btn_activity_welcome_parse_json:
                break;
            default:break;
        }
    }

    private void jsonParser(String jsonData){
        try{
            //去掉外层多余元素
            JSONArray jsonArray = new JSONObject(jsonData).getJSONArray("HeWeather data service 3.0");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            contentTv.setText(jsonObject.toString());
        }catch (JSONException e){
            MyApplications.showLog(e.toString());
        }
    }

    private static class HttpHandler extends Handler{

        WeakReference<WelcomeActivity> mWeakReferenceActivity;

        public HttpHandler(WelcomeActivity activity) {
            mWeakReferenceActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (null != mWeakReferenceActivity) {
                WelcomeActivity welcomeActivity = mWeakReferenceActivity.get();
                if(msg.what == HTTP_REQUEST_ON_FINISH){
                    welcomeActivity.jsonParser((String)msg.obj);
                    return;
                }
                if(msg.what == HTTP_REQUEST_ON_ERROR){
                    welcomeActivity.contentTv.setText("请求异常：" + msg.obj);
                    return;
                }
                if(msg.what == HTTP_REQUEST_ON_NO_RESPONSE){
                    welcomeActivity.contentTv.setText("没有响应");
                    return;
                }
            }
            super.handleMessage(msg);
        }
    }
}
