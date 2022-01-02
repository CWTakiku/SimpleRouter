package com.stl.simplerrouterdemo;

import android.app.Application;

import com.stl.router.SimpleRouterApp;

/**
 * @des:
 * @author: chengwl
 * @date: 2020/10/10
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SimpleRouterApp.init(this);
    }
}
