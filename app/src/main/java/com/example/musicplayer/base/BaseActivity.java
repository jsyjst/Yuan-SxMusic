package com.example.musicplayer.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 残渊 on 2018/10/10.
 */

public  abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        initViews();
        onClick();

    }

    protected abstract void initViews();
    protected abstract void onClick();
}
