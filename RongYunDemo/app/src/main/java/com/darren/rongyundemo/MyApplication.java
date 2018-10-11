package com.darren.rongyundemo;

import android.app.Application;

import io.rong.imkit.RongIM;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
    }
}
