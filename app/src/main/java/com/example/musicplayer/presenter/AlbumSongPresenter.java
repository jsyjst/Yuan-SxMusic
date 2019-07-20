package com.example.musicplayer.presenter;

import android.util.Log;

import com.example.musicplayer.base.observer.BaseObserver;
import com.example.musicplayer.base.presenter.BasePresenter;
import com.example.musicplayer.contract.IAlbumSongContract;
import com.example.musicplayer.entiy.AlbumSong;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.musicplayer.view.search.AlbumSongFragment.ALBUM_SONG;


/**
 * Created by 残渊 on 2018/11/27.
 */

public class AlbumSongPresenter extends BasePresenter<IAlbumSongContract.View> implements IAlbumSongContract.Presenter {

    private final static String TAG = "AlbumSongPresenter";


    @Override
    public void getAlbumDetail(String id, int type) {
        addRxSubscribe(
                mModel.getAlbumSong(id)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<AlbumSong>(mView) {
                            @Override
                            public void onStart() {
                                mView.showLoading();
                            }

                            @Override
                            public void onNext(AlbumSong albumSong) {
                                super.onNext(albumSong);
                                mView.hideLoading();
                                if (albumSong.getCode() == 200) {
                                    if (type == ALBUM_SONG) {
                                        insertAllAlbumSong(albumSong.getData().getGetSongInfo());
                                    } else {
                                        mView.showAlbumMessage(
                                                albumSong.getData().getGetSongInfo().get(0).getAlbumname(),
                                                albumSong.getData().getLanguage(),
                                                albumSong.getData().getGetCompanyInfo().getFcompany_name(),
                                                albumSong.getData().getAlbumtype(),
                                                albumSong.getData().getGetAlbumDesc().getFalbum_desc());
                                    }
                                } else {
                                    mView.showAlbumSongError();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Log.d(TAG, "onError: " + e.toString());
                                mView.hideLoading();
                                if (e instanceof UnknownHostException && type == ALBUM_SONG) {
                                    mView.showNetError();
                                } else {
                                    mView.showAlbumSongError();
                                }
                            }
                        })
        );
    }

    @Override
    public void insertAllAlbumSong(List<AlbumSong.DataBean.GetSongInfoBean> songList) {
        mModel.insertAllAlbumSong(songList);
        mView.setAlbumSongList(songList);
    }
}
