package com.example.musicplayer.model.db;

import com.example.musicplayer.app.BaseUri;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.OnlineSong;

import org.litepal.LitePal;

import java.util.ArrayList;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/16
 *     desc   : 数据库操作类
 * </pre>
 */

public class DbHelperImpl implements DbHelper {

    @Override
    public void insertAllAlbumSong(ArrayList<AlbumSong.DataBean.GetSongInfoBean> songList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LitePal.deleteAll(OnlineSong.class);
                for (int i = 0; i < songList.size(); i++) {
                    AlbumSong.DataBean.GetSongInfoBean song = songList.get(i);
                    OnlineSong onlineSong = new OnlineSong();
                    onlineSong.setId(i + 1);
                    onlineSong.setUrl(BaseUri.PLAY_URL+song.getSongmid());
                    onlineSong.setName(song.getSongname());
                    onlineSong.setPic(BaseUri.PIC_URL+song.getSongmid());
                    onlineSong.setSinger(song.getSinger().get(0).getName());
                    onlineSong.setLrc(BaseUri.LRC_URL+song.getSongmid());
                    onlineSong.setSongId(song.getSongmid());
                    onlineSong.setDuration(song.getInterval());
                    onlineSong.save();
                }
            }
        }).start();
    }
}
