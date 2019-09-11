package com.example.musicplayer.model.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.musicplayer.app.App;
import com.example.musicplayer.app.Constant;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/09
 *     desc   : 保存状态的实现类
 * </pre>
 */

public class PreferencesHelperImpl implements PreferencesHelper{
    private SharedPreferences mPreferences;

    public PreferencesHelperImpl(){
        mPreferences = App.getContext().getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void setPlayMode(int mode) {
        mPreferences.edit().putInt(Constant.PREFS_PLAY_MODE,mode).apply();
    }

    @Override
    public int getPlayMode() {
        return mPreferences.getInt(Constant.PREFS_PLAY_MODE,0);
    }
}
