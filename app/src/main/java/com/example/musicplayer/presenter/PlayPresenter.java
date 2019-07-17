package com.example.musicplayer.presenter;


import com.example.musicplayer.app.BaseUri;
import com.example.musicplayer.base.observer.BaseObserver;
import com.example.musicplayer.base.presenter.BasePresenter;
import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.entiy.Song;
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
                        .flatMap((Function<SingerImg, ObservableSource<SearchSong>>) singerImg -> RetrofitFactory.createRequest().search(song))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<SearchSong>(mView) {
                            @Override
                            public void onNext(SearchSong value) {
                                super.onNext(value);
                                if (value.getCode() == 200) {
                                    getSongLrcSuccess(value.getData().getList(),duration);
                                } else {
                                    mView.showLrcMessage(null, null);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mView.showLrcMessage(null,null);
                            }
                        })
        );
    }

    @Override
    public void getLrcUrl(String song, long duration) {
        addRxSubscribe(
                mModel.search(song)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<SearchSong>(mView){
                            @Override
                            public void onNext(SearchSong value) {
                                super.onNext(value);
                                if (value.getCode() == 200) {
                                    getSongLrcSuccess(value.getData().getList(),duration);
                                } else {
                                    mView.showLrcMessage(null,null);
                                }
                            }
                        })
        );
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


    private void getSongLrcSuccess(List<SearchSong.DataBean.ListBean> dataBeans, long duration) {
        boolean isLrc =false;
        for(SearchSong.DataBean.ListBean dataBean : dataBeans){
            if(dataBean.getPubtime() == duration){
                isLrc = true;
                mView.showLrcMessage(BaseUri.LRC_URL+dataBean.getSongmid(),dataBean.getSongmid());
                break;
            }
        }
        if(!isLrc) mView.showLrcMessage(BaseUri.LRC_URL+dataBeans.get(0).getSongmid(),
                dataBeans.get(0).getSongmid());
    }
}
