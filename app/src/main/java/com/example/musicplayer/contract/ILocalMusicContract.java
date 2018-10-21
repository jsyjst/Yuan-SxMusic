package com.example.musicplayer.contract;

import com.example.musicplayer.entiy.Mp3Info;

import java.util.ArrayList;

/**
 * Created by 残渊 on 2018/10/17.
 */

public interface ILocalMusicContract {
    interface Model{
        void getLocalMp3Info(); //得到本地音乐列表
    }
    interface View{
        void showMusicList(ArrayList<Mp3Info> mp3InfoList); //显示本地音乐
    }
    interface Presenter{
        void showMusicList(ArrayList<Mp3Info> mp3InfoList); //显示本地音乐
        void getLocalMp3Info(); //得到本地音乐列表
    }
}
