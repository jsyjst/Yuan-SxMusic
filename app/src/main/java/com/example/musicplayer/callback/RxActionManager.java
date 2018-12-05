package com.example.musicplayer.callback;


import io.reactivex.disposables.Disposable;

/**
 * 主要管理rxJava的Subscription描述
 * Created by 残渊 on 2018/12/5.
 */

public interface RxActionManager<T> {
    void add(T tag, Disposable d);
    void remove(T tag);
    void cancel(T tag);
    void cancelAll();
}

