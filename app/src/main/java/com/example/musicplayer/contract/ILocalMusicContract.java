package com.example.musicplayer.contract;

import com.example.musicplayer.entiy.LocalSong;

import java.util.List;

/**
 * Created by 残渊 on 2018/10/17.
 */

public interface ILocalMusicContract {
    interface Model{
        void getLocalMp3Info(); //得到本地音乐列表
        void saveSong(List<LocalSong> localSongs);//将本地音乐放到数据库中
    }
    interface View{
        void showMusicList(List<LocalSong> mp3InfoList); //显示本地音乐
    }
    interface Presenter{
        void showMusicList(List<LocalSong> mp3InfoList); //显示本地音乐
        void getLocalMp3Info(); //得到本地音乐列表
        void saveSong(List<LocalSong> localSongs);//将本地音乐放到数据库中
    }
}
