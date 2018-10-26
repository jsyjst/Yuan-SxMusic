package com.example.musicplayer.model;

import com.example.musicplayer.contract.ILocalMusicContract;
import com.example.musicplayer.entiy.Mp3Info;
import com.example.musicplayer.util.MediaUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/10/17.
 */

public class LocalMusicModel implements ILocalMusicContract.Model {

    private ILocalMusicContract.Presenter mPresenter;
    private List<Mp3Info> mMp3InfoList=new ArrayList<>();

    public LocalMusicModel(ILocalMusicContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public void getLocalMp3Info() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMp3InfoList= MediaUtil.getMp3Info();
                mPresenter.showMusicList(mMp3InfoList);

            }
        }).start();
    }

}
