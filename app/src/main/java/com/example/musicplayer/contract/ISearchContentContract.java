package com.example.musicplayer.contract;

import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.SeachSong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/11/21.
 */

public interface ISearchContentContract {
    interface Model{
        void search(String seek,int offset); //搜索
        void searchMore(String seek,int offset); //搜索更多

        void searchAlbum(String seek,int offset); //搜索专辑
        void searchAlbumMore(String seek,int offset);//搜索更多专辑

    }

    interface View{
        void setSongsList(ArrayList<SeachSong.DataBean> songListBeans); //显示歌曲列表
        void searchMoreSuccess(ArrayList<SeachSong.DataBean> songListBeans); //搜索更多内容成功
        void searchMoreError(); //搜索更多内容失败
        void searchMore();//搜索更多
        void showError();
        void showSearcherMoreNetworkError();//下拉刷新网络错误

        void searchAlbumSuccess(List<Album.DataBean> albumList); //获取专辑成功
        void searchAlbumMoreSuccess(List<Album.DataBean> songListBeans); //搜索更多内容成功
        void searchAlbumError(); //获取专辑失败
    }
    interface Presenter{
        void search(String seek,int  offset); //搜索
        void searchMore(String seek,int offset); //搜索更多
        void searchSuccess(ArrayList<SeachSong.DataBean> songListBeans); //搜索成功
        void searchError(); //搜索失败
        void searchMoreSuccess(ArrayList<SeachSong.DataBean> songListBeans); //搜索更多内容成功
        void searchMoreError(); //搜索更多内容失败
        void showSearchMoreNetworkError();//下拉刷新网络错误

        void searchAlbum(String seek,int offset); //搜索专辑
        void searchAlbumSuccess(List<Album.DataBean> albumList); //获取专辑成功
        void searchAlbumError(); //获取专辑失败

        void searchAlbumMore(String seek,int offset);//搜索更多专辑
        void searchAlbumMoreSuccess(List<Album.DataBean> songListBeans); //搜索更多内容成功
    }
}
