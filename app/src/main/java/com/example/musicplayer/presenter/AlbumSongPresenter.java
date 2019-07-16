package com.example.musicplayer.presenter;

import android.os.Handler;


import com.example.musicplayer.base.presenter.BasePresenter;
import com.example.musicplayer.contract.IAlbumSongContract;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.model.AlbumSongModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicplayer.view.AlbumSongFragment.ALBUM_SONG;


/**
 * Created by 残渊 on 2018/11/27.
 */

public class AlbumSongPresenter extends BasePresenter<IAlbumSongContract.View> implements IAlbumSongContract.Presenter {

    private final static String TAG = "AlbumSongPresenter";


    @Override
    public void getAlbumDetail(String id, int type) {
        mModel.getAlbumDetail(id, type);
        if (type == ALBUM_SONG) {
            if (isAttachView()) {
                getMvpView().showLoading();
            }
        }
    }

    @Override
    public void getAlbumDetailSuccess(final int type, final List<AlbumSong.DataBean.GetSongInfoBean> songList,
                                      String name,String language,String company,String albumType,String desc) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAttachView()) {
                    if (type == ALBUM_SONG) {
                        getMvpView().setAlbumSongList(songList);
                        getMvpView().hideLoading();
                    } else {
                        getMvpView().showAlbumMessage(name, language, company, albumType,desc);
                    }

                }
            }
        }, 1000);

    }

    @Override
    public void getAlbumDetailError() {
        if (isAttachView()) {
            getMvpView().showAlbumSongError();
        }
    }

    @Override
    public void getAlbumError() {
        if (isAttachView()) {
            getMvpView().showAlbumSongError();
        }
    }

    @Override
    public void insertAllAlbumSong(ArrayList<AlbumSong.DataBean.GetSongInfoBean> songList) {
        mModel.insertAllAlbumSong(songList);
    }

    @Override
    public void showNetError() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAttachView()) {
                    getMvpView().showNetError();
                }
            }
        }, 1000);

    }
}
