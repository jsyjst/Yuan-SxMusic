package com.example.musicplayer.presenter;

import com.example.musicplayer.base.BasePresenter;
import com.example.musicplayer.app.BaseUri;
import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.model.PlayModel;

import java.util.List;

/**
 * Created by 残渊 on 2018/10/26.
 */

public class PlayPresenter extends BasePresenter<IPlayContract.View> implements IPlayContract.Presenter {
    private static final String TAG = "PlayPresenter";
    private PlayModel mModel;

    public PlayPresenter(){
        mModel=new PlayModel(this);
    }

    @Override
    public void getSingerImg(String singer, String song,long duration) {
        mModel.getSingerImg(singer,song,duration);
    }

    @Override
    public void getLrcUrl(String song, long duration) {
        mModel.getLrcUrl(song,duration);
    }

    @Override
    public void getSingerImgSuccess(String ImgUrl) {
        if(isAttachView()){
            getMvpView().setSingerImg(ImgUrl);
        }
    }

    @Override
    public void getSongLrcSuccess(List<SearchSong.DataBean.ListBean> dataBeans, long duration) {
        if(isAttachView()){
            boolean isLrc =false;
            for(SearchSong.DataBean.ListBean dataBean : dataBeans){
                if(dataBean.getPubtime() == duration){
                    isLrc = true;
                    getMvpView().showLrcMessage(BaseUri.LRC_URL+dataBean.getSongmid(),dataBean.getSongmid());
                    break;
                }
            }
            if(!isLrc) getMvpView().showLrcMessage(BaseUri.LRC_URL+dataBeans.get(0).getSongmid(),
                    dataBeans.get(0).getSongmid());
        }
    }


    @Override
    public void getSongLrcFail() {
        if(isAttachView()){
            getMvpView().showLrcMessage(null,null);
        }
    }

    @Override
    public void getSingerImgFail() {
        if(isAttachView()){
            getMvpView().setImgFail("获取歌手照片失败");
        }
    }

    @Override
    public void showNetWorkError() {

    }

    @Override
    public void queryLove(String songId) {
        mModel.queryLove(songId);
    }

    @Override
    public void saveToLove(Song song) {
        mModel.saveToLove(song);
    }

    @Override
    public void deleteFromLove(String songId) {
        mModel.deleteFromLove(songId);
    }

    @Override
    public void saveToLoveSuccess() {
        if(isAttachView()){
            getMvpView().saveToLoveSuccess();
        }
    }

    @Override
    public void showLove(boolean love) {
        if(isAttachView()){
            getMvpView().showLove(love);
        }
    }

    @Override
    public void deleteSuccess() {
        if(isAttachView()){
            getMvpView().sendUpdateCollection();
        }
    }
}
