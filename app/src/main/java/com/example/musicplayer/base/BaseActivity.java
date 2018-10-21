package com.example.musicplayer.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by 残渊 on 2018/10/10.
 */

public  abstract class BaseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        initVariables();
        initViews();
        loadData();

    }
    protected abstract void initVariables();
    protected abstract void initViews();
    protected abstract void loadData();
}
