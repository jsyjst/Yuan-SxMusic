package com.example.musicplayer.presenter;

import com.example.musicplayer.base.BasePresenter;
import com.example.musicplayer.contract.ISearchContentContract;
import com.example.musicplayer.entiy.SeachSong;
import com.example.musicplayer.model.SearchContentModel;

import java.util.ArrayList;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentPresenter extends BasePresenter<ISearchContentContract.View>
        implements ISearchContentContract.Presenter {
    private SearchContentModel mModel;

    public SearchContentPresenter(){
        mModel = new SearchContentModel(this);
    }

    @Override
    public void search(String seek) {
        mModel.search(seek);
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
}
