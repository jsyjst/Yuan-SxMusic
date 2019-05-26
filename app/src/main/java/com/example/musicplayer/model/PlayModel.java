package com.example.musicplayer.model;

import android.util.Log;

import com.example.musicplayer.configure.Constant;
import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.entiy.Love;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.https.NetWork;
import com.example.musicplayer.util.RxApiManager;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by 残渊 on 2018/10/26.
 */

public class PlayModel implements IPlayContract.Model {
    private String TAG="PlayModel";

    private IPlayContract.Presenter mPresenter;

    public PlayModel(IPlayContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public void getSingerImg(final String singer, final String song, final long duration) {
        NetWork.getSingerImgApi()
                .getSingerImg(singer)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<SingerImg>() {
                    @Override
                    public void accept(SingerImg singerImg) throws Exception {
                        mPresenter.getSingerImgSuccess(singerImg.getResult().getArtists().get(0).getImg1v1Url());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<SingerImg, ObservableSource<SearchSong>>() {
                    @Override
                    public ObservableSource<SearchSong> apply(SingerImg singerImg) throws Exception {
                        return NetWork.getSearchApi().search(song);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchSong>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        RxApiManager.get().add(Constant.LOCAL_IMG, d);
                    }

                    @Override
                    public void onNext(SearchSong value) {
                        if (value.getCode() == 200) {
                            mPresenter.getSongLrcSuccess(value.getData().getList(),duration);
                        } else {
                            mPresenter.getSongLrcFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                        if (e instanceof UnknownHostException) {
                            mPresenter.getSongLrcFail();
                        } else {
                            mPresenter.getSongLrcFail();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void getLrcUrl(String song, long duration) {
        NetWork.getSearchApi().search(song)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchSong>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        RxApiManager.get().add(Constant.LOCAL_IMG, d);
                    }

                    @Override
                    public void onNext(SearchSong value) {
                        if (value.getCode() == 200) {
                            mPresenter.getSongLrcSuccess(value.getData().getList(),duration);
                        } else {
                            mPresenter.getSongLrcFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                        if (e instanceof UnknownHostException) {
                            mPresenter.getSongLrcFail();
                        } else {
                            mPresenter.getSongLrcFail();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void queryLove(final String songId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LitePal.where("songId=?",songId).findAsync(Love.class).listen(new FindMultiCallback<Love>() {
                    @Override
                    public void onFinish(List<Love> list) {
                        if(list.size()==0){
                            mPresenter.showLove(false);
                        }else{
                            mPresenter.showLove(true);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void saveToLove(final Song song) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Love love =new Love();
                love.setName(song.getSongName());
                love.setSinger(song.getSinger());
                love.setUrl(song.getUrl());
                love.setPic(song.getImgUrl());
                love.setDuration(song.getDuration());
                love.setSongId(song.getSongId());
                love.setOnline(song.isOnline());
                love.saveAsync().listen(new SaveCallback() {
                    @Override
                    public void onFinish(boolean success) {
                        if(success){
                            mPresenter.saveToLoveSuccess();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void deleteFromLove(final String songId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LitePal.deleteAllAsync(Love.class,"songId=?",songId)
                        .listen(new UpdateOrDeleteCallback() {
                            @Override
                            public void onFinish(int rowsAffected) {
                                mPresenter.deleteSuccess();
                            }
                        });
            }
        }).start();
    }
}
