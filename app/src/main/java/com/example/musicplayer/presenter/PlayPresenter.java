package com.example.musicplayer.presenter;


import com.example.musicplayer.app.Constant;
import com.example.musicplayer.base.observer.BaseObserver;
import com.example.musicplayer.base.presenter.BasePresenter;
import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.entiy.OnlineSongLrc;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.model.https.RetrofitFactory;

import java.util.ArrayList;
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
                        .doOnError(SingerImg -> mView.showToast("获取不到歌手图片"))
                        .observeOn(Schedulers.io())
                        .flatMap((Function<SingerImg, ObservableSource<SearchSong>>) singerImg -> RetrofitFactory.createRequest().search(song,1))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<SearchSong>(mView) {
                            @Override
                            public void onNext(SearchSong value) {
                                super.onNext(value);
                                if (value.getCode() == 0) {
                                    matchLrc(value.getData().getSong().getList(),duration);
                                } else {
                                    mView.getLrcError(null);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mView.getLrcError(null);
                            }
                        })
        );
    }

    @Override
    public void getLrc(String songId,int type) {
        mModel.getOnlineSongLrc(songId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<OnlineSongLrc>(mView,false,false){
                    @Override
                    public void onNext(OnlineSongLrc onlineSongLrc){
                        if(onlineSongLrc.getCode() == 0){
                            String lrc = onlineSongLrc.getLyric();
                            //如果是本地音乐，就将歌词保存起来
                            if(type == Constant.SONG_LOCAL) mView.saveLrc(lrc);
                            mView.showLrc(lrc);
                        }else {
                            mView.getLrcError(null);
                        }
                    }
                });
    }

    @Override
    public void getSongId(String song, long duration) {
        addRxSubscribe(
                mModel.search(song, 1)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<SearchSong>(mView, true, true) {
                            @Override
                            public void onNext(SearchSong searchSong) {
                                super.onNext(searchSong);
                                if (searchSong.getCode() == 0) {
                                    matchSong(searchSong.getData().getSong().getList(),duration);
                                } else {
                                    mView.getLrcError(null);
                                }
                            }
                        }));
    }

    @Override
    public void setPlayMode(int mode) {
        mModel.setPlayMode(mode);
    }

    @Override
    public int getPlayMode() {
        return mModel.getPlayMode();
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

    //匹配歌词
    private void matchLrc(List<SearchSong.DataBean.SongBean.ListBean> listBeans,long duration){
        boolean isFind = false;
        for(SearchSong.DataBean.SongBean.ListBean listBean:listBeans){
            if(duration == listBean.getInterval()){
                isFind = true;
                mView.setLocalSongId(listBean.getSongmid());
            }
        }
        //如果找不到歌曲id就传输找不到歌曲的消息
        if(!isFind) {
            mView.getLrcError(Constant.SONG_ID_UNFIND);
        }
    }

    private void matchSong(List<SearchSong.DataBean.SongBean.ListBean> listBeans,long duration){
        boolean isFind = false;
        for(SearchSong.DataBean.SongBean.ListBean listBean:listBeans){
            if(duration == listBean.getInterval()){
                isFind = true;
                mView.getSongIdSuccess(listBean.getSongmid());
            }
        }
        //如果找不到歌曲id就传输找不到歌曲的消息
        if(!isFind) {
            mView.getLrcError(Constant.SONG_ID_UNFIND);
        }
    }
}
