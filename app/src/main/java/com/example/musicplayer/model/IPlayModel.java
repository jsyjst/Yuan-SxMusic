package com.example.musicplayer.model;

import android.support.annotation.MainThread;
import android.util.Log;

import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.https.NetWork;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



/**
 * Created by 残渊 on 2018/10/26.
 */

public class IPlayModel implements IPlayContract.Model {
    private String TAG="IPlayModel";

    private IPlayContract.Presenter mPresenter;

    public IPlayModel(IPlayContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public void getSingerImg(String singer) {
        NetWork.getSingerImgApi()
                .getSingerImg(singer)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SingerImg>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SingerImg value) {
                        if(value.getCode()==200) {
                            mPresenter.getSingerImgSuccess(value.getResult().getArtists().get(0).getImg1v1Url());
                            Log.d(TAG, "onNext: "+value.getResult().getArtists().get(0).getImg1v1Url());
                        }else{
                            Log.d(TAG, "onNext: "+value.getCode());
                            mPresenter.getSingerImgFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mPresenter.getSingerImgFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
