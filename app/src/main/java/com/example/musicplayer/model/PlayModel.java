package com.example.musicplayer.model;

import android.util.Log;

import com.example.musicplayer.configure.Constant;
import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.entiy.Love;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.https.NetWork;
import com.example.musicplayer.util.RxApiManager;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
    public void getSingerImg(String singer) {
        NetWork.getSingerImgApi()
                .getSingerImg(singer)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SingerImg>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        RxApiManager.get().add(Constant.LOCAL_IMG,d);
                    }

                    @Override
                    public void onNext(SingerImg value) {
                        if(value.getCode()==200) {
                            mPresenter.getSingerImgSuccess(value.getResult().getArtists().get(0).getImg1v1Url());
                            Log.d(TAG, "onNext: "+value.getResult().getArtists().get(0).getImg1v1Url());
                        }else{
                            Log.d(TAG, "onNext: "+value.getCode());
                            mPresenter.getSingerImgFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mPresenter.getSingerImgFail();
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
                love.setSongId(song.getOnlineId());
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
