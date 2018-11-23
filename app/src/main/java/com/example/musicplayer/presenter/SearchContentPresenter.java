package com.example.musicplayer.presenter;

import com.example.musicplayer.base.BasePresenter;
import com.example.musicplayer.contract.ISearchContentContract;
import com.example.musicplayer.entiy.SeachSong;
import com.example.musicplayer.model.SearchContentModel;

import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentPresenter extends BasePresenter<ISearchContentContract.View>
        implements ISearchContentContract.Presenter {
    private SearchContentModel mModel;
    private android.os.Handler mHandler = new android.os.Handler();

    public SearchContentPresenter(){
        mModel = new SearchContentModel(this);
    }

    @Override
    public void search(String seek,int offset) {
        mModel.search(seek,offset);
    }

    @Override
    public void searchMore(String seek, int offset) {
        mModel.searchMore(seek,offset);
    }

    @Override
    public void searchSuccess(ArrayList<SeachSong.DataBean> songListBeans) {

        if(isAttachView()){
            getMvpView().setSongsList(songListBeans);
        }
    }

    @Override
    public void searchError() {
        getMvpView().showError();
    }

    @Override
    public void searchMoreSuccess(final ArrayList<SeachSong.DataBean> songListBeans) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isAttachView()){
                    getMvpView().searchMoreSuccess(songListBeans);
                }
            }
        },500);

    }

    @Override
    public void searchMoreError() {
        if(isAttachView()){
            getMvpView().searchMoreError();
        }
    }

    @Override
    public void showSearchMoreNetworkError() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isAttachView()){
                    getMvpView().showSearcherMoreNetworkError();
                }
            }
        },500);

    }
}
