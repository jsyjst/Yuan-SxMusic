package com.example.musicplayer.model.prefs;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/09
 *     desc   : 存储状态抽象类
 * </pre>
 */

public interface PreferencesHelper {
    void setPlayMode(int mode);  //保存播放状态
    int getPlayMode();//得到播放状态
}
