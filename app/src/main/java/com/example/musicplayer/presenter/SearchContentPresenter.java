package com.example.musicplayer.presenter;


import android.util.Log;

import com.example.musicplayer.app.Api;
import com.example.musicplayer.base.observer.BaseObserver;
import com.example.musicplayer.base.presenter.BasePresenter;
import com.example.musicplayer.contract.ISearchContentContract;
import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.entiy.SongUrl;
import com.example.musicplayer.model.DataModel;
import com.example.musicplayer.model.db.DbHelperImpl;
import com.example.musicplayer.model.https.NetworkHelperImpl;
import com.example.musicplayer.model.https.RetrofitFactory;
import com.example.musicplayer.model.prefs.PreferencesHelperImpl;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentPresenter extends BasePresenter<ISearchContentContract.View>
        implements ISearchContentContract.Presenter {
    private static final String TAG = "SearchContentPresenter";

    @Override
    public void search(String seek, int offset) {
        addRxSubscribe(
                mModel.search(seek, offset)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<SearchSong>(mView, true, true) {
                            @Override
                            public void onNext(SearchSong searchSong) {
                                super.onNext(searchSong);
                                if (searchSong.getCode() == 0) {
                                    mView.setSongsList((ArrayList<SearchSong.DataBean.SongBean.ListBean>)
                                            searchSong.getData().getSong().getList());
                                }
                            }
                        }));
    }

    @Override
    public void searchMore(String seek, int offset) {
        addRxSubscribe(
                mModel.search(seek, offset)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<SearchSong>(mView, false, true) {
                            @Override
                            public void onNext(SearchSong searchSong) {
                                super.onNext(searchSong);
                                if (searchSong.getCode() == 0) {
                                    ArrayList<SearchSong.DataBean.SongBean.ListBean> songListBeans =
                                            (ArrayList<SearchSong.DataBean.SongBean.ListBean>) searchSong.getData().getSong().getList();
                                    if (songListBeans.size() == 0) {
                                        mView.searchMoreError();
                                    } else {
                                        mView.searchMoreSuccess(songListBeans);
                                    }
                                } else {
                                    mView.searchMoreError();
                                }
                            }

                            @Override
                            public void onError(Throwable e){
                                super.onError(e);
                                mView.showSearcherMoreNetworkError();
                            }
                        }));
    }


    @Override
    public void searchAlbum(String seek, int offset) {
        addRxSubscribe(
                mModel.searchAlbum(seek, offset)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<Album>(mView, true, true) {
                            @Override
                            public void onNext(Album album) {
                                super.onNext(album);
                                if (album.getCode() == 0) {
                                    mView.searchAlbumSuccess(album.getData().getAlbum().getList());
                                } else {
                                    mView.searchAlbumError();
                                }
                            }
                        }));
    }

    @Override
    public void searchAlbumMore(String seek, int offset) {
        addRxSubscribe(
                mModel.searchAlbum(seek, offset)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<Album>(mView, false, true) {
                            @Override
                            public void onNext(Album album) {
                                super.onNext(album);
                                if (album.getCode() == 0) {
                                    mView.searchAlbumMoreSuccess(album.getData().getAlbum().getList());
                                } else {
                                    mView.searchMoreError();
                                }
                            }
                            @Override
                            public void onError(Throwable e){
                                super.onError(e);
                                mView.showSearcherMoreNetworkError();
                            }
                        }));
    }

    @Override
    public void getSongUrl(Song song) {
        Log.d(TAG, "getSongUrl: "+Api.SONG_URL_DATA_LEFT+song.getSongId()+Api.SONG_URL_DATA_RIGHT);
        addRxSubscribe(
                RetrofitFactory.createRequestOfSongUrl().getSongUrl(Api.SONG_URL_DATA_LEFT+song.getSongId()+Api.SONG_URL_DATA_RIGHT)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<SongUrl>(mView,false,false){
                            @Override
                            public void onNext(SongUrl songUrl){
                                super.onNext(songUrl);
                                if(songUrl.getCode() == 0){
                                    String sip = songUrl.getReq_0().getData().getSip().get(0);
                                    String purl = songUrl.getReq_0().getData().getMidurlinfo().get(0).getPurl();
                                    if(purl.equals("")){
                                        mView.showToast("该歌曲暂时没有版权，搜搜其它歌曲吧");
                                    }else {
                                        mView.getSongUrlSuccess(song,sip+purl);
                                    }

                                }else {
                                    mView.showToast(songUrl.getCode()+":获取不到歌曲播放地址");
                                }
                            }
                        })
        );
    }

}
