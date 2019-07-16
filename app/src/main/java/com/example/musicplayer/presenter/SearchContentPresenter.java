package com.example.musicplayer.presenter;


import com.example.musicplayer.base.observer.BaseObserver;
import com.example.musicplayer.base.presenter.BasePresenter;
import com.example.musicplayer.contract.ISearchContentContract;
import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.SearchSong;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentPresenter extends BasePresenter<ISearchContentContract.View>
        implements ISearchContentContract.Presenter {

    @Override
    public void search(String seek, int offset) {
        addRxSubscribe(
                mModel.search(seek, offset)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseObserver<SearchSong>(mView, true, true) {
                            @Override
                            public void onNext(SearchSong searchSong) {
                                super.onNext(searchSong);
                                if (searchSong.getCode() == 200) {
                                    mView.setSongsList((ArrayList<SearchSong.DataBean.ListBean>)
                                            searchSong.getData().getList());
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
                                if (searchSong.getCode() == 200) {
                                    ArrayList<SearchSong.DataBean.ListBean> songListBeans =
                                            (ArrayList<SearchSong.DataBean.ListBean>) searchSong.getData().getList();
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
                                if (album.getCode() == 200) {
                                    mView.searchAlbumSuccess(album.getData().getList());
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
                                if (album.getCode() == 200) {
                                    mView.searchAlbumMoreSuccess(album.getData().getList());
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

}
