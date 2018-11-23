package com.example.musicplayer.contract;

import com.example.musicplayer.entiy.SeachSong;

import java.util.ArrayList;

/**
 * Created by 残渊 on 2018/11/21.
 */

public interface ISearchContentContract {
    interface Model{
        void search(String seek); //搜索
    }

    interface View{
        String getSeekContent(); //获得搜索的内容
        void setSongsList(ArrayList<SeachSong.DataBean> songListBeans); //显示歌曲列表
        void showError();
    }
    interface Presenter{
        void search(String seek); //搜索
        void searchSuccess(ArrayList<SeachSong.DataBean> songListBeans); //搜索成功
        void searchError(); //搜索失败
    }
}
