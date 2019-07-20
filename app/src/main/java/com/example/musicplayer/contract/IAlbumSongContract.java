package com.example.musicplayer.contract;

import com.example.musicplayer.base.presenter.IPresenter;
import com.example.musicplayer.base.view.BaseView;
import com.example.musicplayer.entiy.AlbumSong;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 残渊 on 2018/11/27.
 */

public interface IAlbumSongContract {
    interface View extends BaseView {
        void setAlbumSongList(List<AlbumSong.DataBean.GetSongInfoBean> songList); //成功获取专辑歌曲后填充列表
        void showAlbumSongError();//获取专辑失败
        void showAlbumMessage(String name,String language,String company,String albumType,String desc); //展示专辑详细
        void showLoading();  //显示进度
        void hideLoading(); //隐藏进度
        void showNetError(); //显示网络错误
    }
    interface Presenter extends IPresenter<View> {
        void getAlbumDetail(String id,int type); //获取专辑的更多信息
        void insertAllAlbumSong(List<AlbumSong.DataBean.GetSongInfoBean> songList); //将专辑歌曲添加到数据库

    }
}
