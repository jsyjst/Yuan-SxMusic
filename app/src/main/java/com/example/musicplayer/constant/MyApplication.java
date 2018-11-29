package com.example.musicplayer.constant;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * 获取全局Context
 * Created by 残渊 on 2018/7/17.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
        LitePal.initialize(this);
    }

    public static Context getContext(){
        return context;
    }
}
