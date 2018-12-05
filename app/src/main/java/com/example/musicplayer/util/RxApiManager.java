package com.example.musicplayer.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.util.Log;


import com.example.musicplayer.callback.RxActionManager;

import org.reactivestreams.Subscription;

import java.util.Set;

import io.reactivex.disposables.Disposable;


/**
 * Created by 残渊 on 2018/12/5.
 */

public class RxApiManager implements RxActionManager<Object> {
    private static final String TAG="RxApiManager";

    private static RxApiManager sInstance = null;

    private ArrayMap<Object, Disposable> maps;

    public static RxApiManager get() {

        if (sInstance == null) {
            synchronized (RxApiManager.class) {
                if (sInstance == null) {
                    sInstance = new RxApiManager();
                }
            }
        }
        return sInstance;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private RxApiManager() {
        maps = new ArrayMap<>();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void add(Object tag, Disposable disposable) {
        maps.put(tag, disposable);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void remove(Object tag) {
        if (!maps.isEmpty()) {
            maps.remove(tag);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void removeAll() {
        if (!maps.isEmpty()) {
            maps.clear();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override public void cancel(Object tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        if (!maps.get(tag).isDisposed()) {
            Log.d(TAG, "cancel: "+tag);
            maps.get(tag).dispose();
            maps.remove(tag);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        Set<Object> keys = maps.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
        removeAll();
    }
}

