package com.example.musicplayer.model;

import android.util.Log;

import com.example.musicplayer.constant.Constant;
import com.example.musicplayer.contract.IAlbumSongContract;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.OnlineSong;
import com.example.musicplayer.https.NetWork;
import com.example.musicplayer.util.RxApiManager;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.net.UnknownHostException;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.musicplayer.view.AlbumSongFragment.ALBUM_SONG;

/**
 * Created by 残渊 on 2018/11/27.
 */

public class AlbumSongModel implements IAlbumSongContract.Model {
    private static final String TAG = "AlbumSongModel";
    private IAlbumSongContract.Presenter mPresenter;

    public AlbumSongModel(IAlbumSongContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getAlbumDetail(String id, final int type) {
        NetWork.getAlbumApi().getAlbumSong(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AlbumSong>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        RxApiManager.get().add(Constant.ALBUM, d);
                    }

                    @Override
                    public void onNext(AlbumSong value) {
                        if (value.getResult().equals("SUCCESS")) {
                            mPresenter.getAlbumDetailSuccess(type, value.getData().getSongs(),
                                    value.getData().getName(), value.getData().getSinger(),
                                    value.getData().getCompany(), value.getData().getDesc());
                        } else {
                            mPresenter.getAlbumDetailError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                        if (e instanceof UnknownHostException && type == ALBUM_SONG) {
                            mPresenter.showNetError();
                        } else {
                            mPresenter.getAlbumError();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void insertAllAlbumSong(final ArrayList<AlbumSong.DataBean.SongsBean> songList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LitePal.deleteAll(OnlineSong.class);
                for (int i = 0; i < songList.size(); i++) {
                    AlbumSong.DataBean.SongsBean song = songList.get(i);
                    OnlineSong onlineSong = new OnlineSong();
                    onlineSong.setId(i + 1);
                    onlineSong.setUrl(song.getUrl());
                    onlineSong.setName(song.getName());
                    onlineSong.setPic(song.getPic());
                    onlineSong.setSinger(song.getSinger());
                    onlineSong.setLrc(song.getLrc());
                    onlineSong.setSongId(song.getId());
                    onlineSong.save();
                }
            }
        }).start();

    }
}
