package com.example.musicplayer.presenter;


import com.example.musicplayer.base.presenter.BasePresenter;
import com.example.musicplayer.contract.ILocalContract;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.event.SongLocalSizeChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by 残渊 on 2018/10/17.
 */

public class LocalPresenter extends BasePresenter<ILocalContract.View> implements ILocalContract.Presenter {

    @Override
    public void getLocalMp3Info() {
        List<LocalSong> localSongList = mModel.getLocalMp3Info();
        if(localSongList.size() == 0){
            mView.showErrorView();
        }else {
            saveSong(localSongList);
        }

    }

    @Override
    public void saveSong(List<LocalSong> localSongs) {
        if(mModel.saveSong(localSongs)) {
            EventBus.getDefault().post(new SongLocalSizeChangeEvent());
            mView.showToast("成功导入本地音乐");
            mView.showMusicList(localSongs);
        }
    }
}
