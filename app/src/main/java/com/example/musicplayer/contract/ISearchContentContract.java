package com.example.musicplayer.contract;

import com.example.musicplayer.base.presenter.IPresenter;
import com.example.musicplayer.base.view.BaseView;
import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.SearchSong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/11/21.
 */

public interface ISearchContentContract {
    interface View extends BaseView {
        void setSongsList(ArrayList<SearchSong.DataBean.SongBean.ListBean> songListBeans); //显示歌曲列表
        void searchMoreSuccess(ArrayList<SearchSong.DataBean.SongBean.ListBean> songListBeans); //搜索更多内容成功
        void searchMoreError(); //搜索更多内容失败
        void searchMore();//搜索更多
        void showSearcherMoreNetworkError();//下拉刷新网络错误

        void searchAlbumSuccess(List<Album.DataBean.AlbumBean.ListBean> albumList); //获取专辑成功
        void searchAlbumMoreSuccess(List<Album.DataBean.AlbumBean.ListBean> songListBeans); //搜索更多内容成功
        void searchAlbumError(); //获取专辑失败
        void getSongUrlSuccess(String url);//成功获取歌曲url
    }
    interface Presenter extends IPresenter<View> {
        void search(String seek,int  offset); //搜索
        void searchMore(String seek,int offset); //搜索更多
        void searchAlbum(String seek,int offset); //搜索专辑
        void searchAlbumMore(String seek,int offset);//搜索更多专辑
        void getSongUrl(String songId);//得到歌曲的播放url
    }
}
