package com.example.musicplayer.model;

import android.util.Log;

import com.example.musicplayer.contract.ISearchContentContract;
import com.example.musicplayer.entiy.SeachSong;
import com.example.musicplayer.https.NetWork;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentModel implements ISearchContentContract.Model {
    private static final String TAG = "SearchContentModel";
    private ISearchContentContract.Presenter mPresenter;

    public SearchContentModel(ISearchContentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void search(String seek, int offset) {
        NetWork.getSearchApi().search(seek, offset)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SeachSong>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SeachSong value) {
                        mPresenter.searchSuccess((ArrayList<SeachSong.DataBean>) value.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.searchError();
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void searchMore(String seek, int offset) {
        NetWork.getSearchApi().search(seek, offset)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SeachSong>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SeachSong value) {
                        if (value.getResult().equals("SUCCESS")) {
                            Log.d(TAG, "onNext: success");
                            if (value.getData().size() == 0) {
                                mPresenter.searchMoreError();
                            } else {
                                mPresenter.searchMoreSuccess((ArrayList<SeachSong.DataBean>) value.getData());
                            }
                        } else {
                            mPresenter.searchMoreError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.showSearchMoreNetworkError();
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
