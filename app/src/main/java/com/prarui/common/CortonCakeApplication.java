package com.prarui.common;

import android.app.Application;

/**
 * Created by Prarui on 2017/8/29.
 */

public class CortonCakeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
     CrotonCake.initData(this);
    }
}
