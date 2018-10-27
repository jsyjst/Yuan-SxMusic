package com.example.musicplayer.contract;

/**
 * Created by 残渊 on 2018/10/26.
 */

public interface IPlayContract {
    interface Model{
        void getSingerImg(String singer);//网络请求获得歌手uri
    }
    interface View{
        String getSingerName(); //得到歌手的姓名
        void getSingerAndLrc();//按钮点击事件，获取封面和歌词
        void setSingerImg(String ImgUrl); //将图片设置成背景
        void setImgFail(String errorMessage);
    }
    interface Presenter{
        void getSingerImg(String singer);
        void getSingerImgSuccess(String ImgUrl); //成功获取图片
        void getSingerImgFail(); //请求失败
    }
}
