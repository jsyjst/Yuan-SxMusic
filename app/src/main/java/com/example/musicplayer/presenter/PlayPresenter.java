package com.example.musicplayer.presenter;


import android.util.Log;

import com.example.musicplayer.app.Constant;
import com.example.musicplayer.base.observer.BaseObserver;
import com.example.musicplayer.base.presenter.BasePresenter;
import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.entiy.OnlineSong;
import com.example.musicplayer.entiy.OnlineSongLrc;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.entiy.SongLrc;
import com.example.musicplayer.model.https.RetrofitFactory;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 残渊 on 2018/10/26.
 */

public class PlayPresenter extends BasePresenter<IPlayContract.View> implements IPlayContract.Presenter {


    @Override
    public void getSingerImg(String singer, String song, long duration) {
        addRxSubscribe(
                RetrofitFactory.createRequestOfSinger().getSingerImg(singer)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(singerImg -> mView.setSingerImg(singerImg.getResult().getArtists().get(0).getImg1v1Url()))
                        .observeOn(Schedulers.io())
                        .flatMap((Function<SingerImg, ObservableSource<SongLrc>>) singerImg -> RetrofitFactory.createRequest().getLrc(song))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<SongLrc>(mView) {
                            @Override
                            public void onNext(SongLrc value) {
                                super.onNext(value);
                                if (value.getCode() == 200) {
                                    getSongLrcSuccess(value.getData().getLyric().getList(),null,duration);
                                } else {
                                    mView.showLrcMessage(null);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mView.showLrcMessage(null);
                            }
                        })
        );
    }

    @Override
    public void getLrc(String songName, String songId,long duration) {
        addRxSubscribe(
                mModel.getLrc(songName)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<SongLrc>(mView){
                            @Override
                            public void onNext(SongLrc value) {
                                super.onNext(value);
                                if (value.getCode() == 0) {
                                    getSongLrcSuccess(value.getData().getLyric().getList(),songId,duration);
                                } else {
                                    mView.showLrcMessage(null);
                                }
                            }
                        })
        );
    }

    @Override
    public void getSongOnlineLrc(String songId) {
        mModel.getOnlineSongLrc(songId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<OnlineSongLrc>(mView,false,false){
                    @Override
                    public void onNext(OnlineSongLrc onlineSongLrc){
                        if(onlineSongLrc.getCode() == 0){
                            mView.showLrc(onlineSongLrc.getLyric());
                        }else {
                            mView.getLrcError();
                        }
                    }
                });
    }


    @Override
    public void queryLove(String songId) {
        mView.showLove(mModel.queryLove(songId));
    }

    @Override
    public void saveToLove(Song song) {
        if(mModel.saveToLove(song)){
            mView.saveToLoveSuccess();
        }
    }

    @Override
    public void deleteFromLove(String songId) {
        if(mModel.deleteFromLove(songId)){
            mView.sendUpdateCollection();
        }
    }


    private void getSongLrcSuccess(List<SongLrc.DataBean.LyricBean.ListBean> listBeans, String songId,long duration) {
        boolean isLrc =false;
        for(SongLrc.DataBean.LyricBean.ListBean listBean : listBeans){
            if(null != songId){
                if(listBean.getSongmid().equals(songId)){
                    isLrc = true;
                    mView.showLrcMessage(listBean.getContent());
                    break;
                }
            }else {
                if(listBean.getInterval() == duration){
                    isLrc = true;
                    mView.showLrcMessage(listBean.getContent());
                    break;
                }
            }

        }
        if(!isLrc) mView.showLrcMessage(null);
    }
}
