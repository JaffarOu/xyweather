package com.jf.xyweather.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jf.xyweather.base.MyApplications;
import com.jf.xyweather.util.LogUtil;

/**
 * Created by jf on 2016/6/16.
 * The basic Fragment of all Fragments in this App
 */
abstract public class BaseFragment extends Fragment {

    //The View returned in onCreateView() method
    protected View layoutView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("which_fragment", getClass().getSimpleName());
    }
}
