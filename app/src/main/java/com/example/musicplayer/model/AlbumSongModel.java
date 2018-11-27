package com.example.musicplayer.model;

import android.util.Log;

import com.example.musicplayer.contract.IAlbumSongContract;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.https.NetWork;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 残渊 on 2018/11/27.
 */

public class AlbumSongModel implements IAlbumSongContract.Model {
    private static final String TAG="AlbumSongModel";
    private IAlbumSongContract.Presenter mPresenter;

    public AlbumSongModel(IAlbumSongContract.Presenter presenter){
        mPresenter = presenter;
    }

    @Override
    public void getAlbumDetail(String id,final int type) {
        NetWork.getAlbumApi().getAlbumSong(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AlbumSong>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AlbumSong value) {
                        if(value.getResult().equals("SUCCESS")){
                            mPresenter.getAlbumDetailSuccess(type,value.getData().getSongs(),
                                    value.getData().getName(),value.getData().getSinger(),
                                    value.getData().getCompany(),value.getData().getDesc());
                        }else{
                            mPresenter.getAlbumDetailError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.toString());
                        mPresenter.getAlbumError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
