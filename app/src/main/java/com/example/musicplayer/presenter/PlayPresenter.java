package com.example.musicplayer.presenter;

import com.example.musicplayer.base.BasePresenter;
import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.model.IPlayModel;

/**
 * Created by 残渊 on 2018/10/26.
 */

public class PlayPresenter extends BasePresenter<IPlayContract.View> implements IPlayContract.Presenter {
    private IPlayModel mModel;

    public PlayPresenter(){
        mModel=new IPlayModel(this);
    }


    @Override
    public void getSingerImg(String singer) {
        mModel.getSingerImg(singer);
    }

    @Override
    public void getSingerImgSuccess(String ImgUrl) {
        if(isAttachView()){
            getMvpView().setSingerImg(ImgUrl);
        }
    }

    @Override
    public void getSingerImgFail() {
        if(isAttachView()){
            getMvpView().setImgFail("获取歌手照片失败");
        }
    }
}
