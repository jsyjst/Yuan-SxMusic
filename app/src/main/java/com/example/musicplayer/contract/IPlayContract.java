package com.example.musicplayer.contract;

import com.example.musicplayer.base.presenter.IPresenter;
import com.example.musicplayer.base.view.BaseView;
import com.example.musicplayer.entiy.Song;

/**
 * Created by 残渊 on 2018/10/26.
 */

public interface IPlayContract {
    interface View extends BaseView {
        String getSingerName(); //得到歌手的姓名
        void getSingerAndLrc();//按钮点击事件，获取封面和歌词
        void setSingerImg(String ImgUrl); //将图片设置成背景
        void setImgFail(String errorMessage);
        void showLove(boolean love); //判断是否显示我喜欢的图标
        void showLoveAnim(); //喜欢的动画
        void saveToLoveSuccess();//保存到我喜欢数据库成功
        void sendUpdateCollection(); //发送广播更新收藏列表
        void showLrcMessage(String lrc); //显示歌词获取信息
        void showLrc(String lrc);//显示歌词
        void getLrcError();//获取不到歌词
    }
    interface Presenter extends IPresenter<View> {
        void getSingerImg(String singer,String song,long duration);
        void getLrc(String songName, String songId,long duration); //搜索歌词
        void getSongOnlineLrc(String songId);//获取网络歌曲

        void queryLove(String songId);//查询我喜欢的数据库中有没这首歌
        void saveToLove(Song song); //添加到我喜欢的表
        void deleteFromLove(String songId); //从我喜欢的表中移除
    }
}
