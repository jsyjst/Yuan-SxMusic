package com.example.musicplayer.base;

import android.widget.Toast;

import com.example.musicplayer.constant.Constant;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.RxApiManager;

import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 残渊 on 2018/12/5.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private BaseActivity context;

    public BaseObserver(BaseActivity context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        RxApiManager.get().add(Constant.ALBUM,d);
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof UnknownHostException){
            onComplete();
            CommonUtil.showToast(context,"当前网络不可用，请检查当前网络");
        }
    }

    @Override
    public void onComplete() {
    }
}

