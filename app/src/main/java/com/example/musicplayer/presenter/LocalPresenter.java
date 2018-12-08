package com.example.musicplayer.presenter;

import com.example.musicplayer.base.BasePresenter;
import com.example.musicplayer.contract.ILocalContract;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.model.LocalModel;

import java.util.List;

/**
 * Created by 残渊 on 2018/10/17.
 */

public class LocalPresenter extends BasePresenter<ILocalContract.View> implements ILocalContract.Presenter {

    private LocalModel mModel;
    public LocalPresenter(){
        mModel=new LocalModel(this);
    }


    @Override
    public void showMusicList(List<LocalSong> mp3InfoList) {
        if(isAttachView()){
            getMvpView().showMusicList(mp3InfoList);
        }
    }

    @Override
    public void getLocalMp3Info() {
        mModel.getLocalMp3Info();
    }

    @Override
    public void saveSong(List<LocalSong> localSongs) {
        mModel.saveSong(localSongs);
    }

    @Override
    public void saveLocalSuccess() {
        if(isAttachView()){
            getMvpView().saveLocalSuccess();
        }
    }
}
