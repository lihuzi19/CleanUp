package com.example.lihuzi.cleanup.application;

import android.app.Application;

import com.seven.two.zero.yun.sdk.register.YunApi;

public class CleanUpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YunApi.getInstance();
    }
}
