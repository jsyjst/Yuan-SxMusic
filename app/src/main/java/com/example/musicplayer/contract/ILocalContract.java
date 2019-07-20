package com.example.musicplayer.contract;

import com.example.musicplayer.base.presenter.IPresenter;
import com.example.musicplayer.base.view.BaseView;
import com.example.musicplayer.entiy.LocalSong;

import java.util.List;

/**
 * Created by 残渊 on 2018/10/17.
 */

public interface ILocalContract {
    interface View extends BaseView {
        void showMusicList(List<LocalSong> mp3InfoList); //显示本地音乐
    }
    interface Presenter extends IPresenter<View> {
        void getLocalMp3Info(); //得到本地音乐列表
        void saveSong(List<LocalSong> localSongs);//将本地音乐放到数据库中
    }
}
