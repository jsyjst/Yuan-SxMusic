package com.example.musicplayer.model;

import android.util.Log;

import com.example.musicplayer.app.BaseUri;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.contract.IAlbumSongContract;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.OnlineSong;
import com.example.musicplayer.model.https.RetrofitFactory;
import com.example.musicplayer.util.RxApiManager;

import org.litepal.LitePal;

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
        RetrofitFactory.createRequest().getAlbumSong(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AlbumSong>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        RxApiManager.get().add(Constant.ALBUM, d);
                    }

                    @Override
                    public void onNext(AlbumSong value) {
                        if (value.getCode()==200) {

                            mPresenter.getAlbumDetailSuccess(type,
                                    value.getData().getGetSongInfo(),
                                    value.getData().getGetSongInfo().get(0).getAlbumname(),
                                    value.getData().getLanguage(),
                                    value.getData().getGetCompanyInfo().getFcompany_name(),
                                    value.getData().getAlbumtype(),
                                    value.getData().getGetAlbumDesc().getFalbum_desc());
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
    public void insertAllAlbumSong(final ArrayList<AlbumSong.DataBean.GetSongInfoBean> songList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LitePal.deleteAll(OnlineSong.class);
                for (int i = 0; i < songList.size(); i++) {
                    AlbumSong.DataBean.GetSongInfoBean song = songList.get(i);
                    OnlineSong onlineSong = new OnlineSong();
                    onlineSong.setId(i + 1);
                    onlineSong.setUrl(BaseUri.PLAY_URL+song.getSongmid());
                    onlineSong.setName(song.getSongname());
                    onlineSong.setPic(BaseUri.PIC_URL+song.getSongmid());
                    onlineSong.setSinger(song.getSinger().get(0).getName());
                    onlineSong.setLrc(BaseUri.LRC_URL+song.getSongmid());
                    onlineSong.setSongId(song.getSongmid());
                    onlineSong.setDuration(song.getInterval());
                    onlineSong.save();
                }
            }
        }).start();

    }
}
