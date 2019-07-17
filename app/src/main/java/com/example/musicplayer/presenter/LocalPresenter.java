package com.example.musicplayer.presenter;


import com.example.musicplayer.base.presenter.BasePresenter;
import com.example.musicplayer.contract.ILocalContract;
import com.example.musicplayer.entiy.LocalSong;

import java.util.List;

/**
 * Created by 残渊 on 2018/10/17.
 */

public class LocalPresenter extends BasePresenter<ILocalContract.View> implements ILocalContract.Presenter {

    @Override
    public void getLocalMp3Info() {
        if(mModel.getLocalMp3Info().size() == 0){
            mView.showErrorView();
        }else {
            mView.showMusicList(mModel.getLocalMp3Info());
        }

    }

    @Override
    public void saveSong(List<LocalSong> localSongs) {
        if(mModel.saveSong(localSongs)) mView.saveLocalSuccess();
    }
}
