package com.example.musicplayer.contract;

import com.example.musicplayer.entiy.Love;
import com.example.musicplayer.entiy.Song;

/**
 * Created by 残渊 on 2018/10/26.
 */

public interface IPlayContract {
    interface Model{
        void getSingerImg(String singer);//网络请求获得歌手uri
        void queryLove(String songId);//查询我喜欢的数据库中有没这首歌
        void saveToLove(Song song); //添加到我喜欢的表
        void deleteFromLove(String songId); //从我喜欢的表中移除
    }
    interface View{
        String getSingerName(); //得到歌手的姓名
        void getSingerAndLrc();//按钮点击事件，获取封面和歌词
        void setSingerImg(String ImgUrl); //将图片设置成背景
        void setImgFail(String errorMessage);
        void showLove(boolean love); //判断是否显示我喜欢的图标
        void showLoveAnim(); //喜欢的动画
        void saveToLoveSuccess();//保存到我喜欢数据库成功
    }
    interface Presenter{
        void getSingerImg(String singer);
        void getSingerImgSuccess(String ImgUrl); //成功获取图片
        void getSingerImgFail(); //请求失败
        void queryLove(String songId);//查询我喜欢的数据库中有没这首歌
        void saveToLove(Song song); //添加到我喜欢的表
        void deleteFromLove(String songId); //从我喜欢的表中移除
        void saveToLoveSuccess();//保存到我喜欢数据库成功
        void showLove(boolean love); //判断是否显示我喜欢的图标
    }
}
