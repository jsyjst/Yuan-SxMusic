package com.example.musicplayer.presenter;

import android.util.Log;

import com.example.musicplayer.base.BasePresenter;
import com.example.musicplayer.contract.IAlbumSongContract;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.model.AlbumSongModel;
import com.example.musicplayer.view.AlbumSongFragment;

import java.util.List;

/**
 * Created by 残渊 on 2018/11/27.
 */

public class AlbumSongPresenter extends BasePresenter<IAlbumSongContract.View> implements IAlbumSongContract.Presenter {

    private final static String TAG="AlbumSongPresenter";

    private AlbumSongModel mModel;

    public AlbumSongPresenter() {
        mModel = new AlbumSongModel(this);
    }

    @Override
    public void getAlbumDetail(String id,int type) {
        mModel.getAlbumDetail(id,type);
    }

    @Override
    public void getAlbumDetailSuccess(int type, List<AlbumSong.DataBean.SongsBean> songList,
                                      String name, String singer, String company, String desc) {
        if (isAttachView()) {
            if (type == AlbumSongFragment.ALBUM_SONG) {
                getMvpView().setAlbumSongList(songList);
                Log.d(TAG, "getAlbumDetailSuccess: "+songList.size());
            } else {
                getMvpView().showAlbumMessage(name, singer, company, desc);
            }
        }
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
            getMvpView().showNetError();
        }
    }
}
