package com.example.musicplayer.model;

import android.database.Cursor;
import android.provider.MediaStore;

import com.example.musicplayer.app.App;
import com.example.musicplayer.contract.ILocalContract;
import com.example.musicplayer.entiy.LocalSong;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/10/17.
 */

public class LocalModel implements ILocalContract.Model {
    private static final String TAG="LocalModel";

    private ILocalContract.Presenter mPresenter;

    public LocalModel(ILocalContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public void getLocalMp3Info() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<LocalSong> mp3InfoList = new ArrayList<>();
                Cursor cursor = App.getContext().getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    LocalSong mp3Info = new LocalSong();
                    long id = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media._ID));    //音乐id
                    String title = cursor.getString((cursor
                            .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题
                    String artist = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
                    long duration = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
                    long size = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media.SIZE));    //文件大小
                    long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    String url = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));     //文件路径
                    int isMusic = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐
                    if (isMusic != 0) {//只把音乐添加到集合当中
                        if (size > 1000 * 800) {
                            // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                            if (title.contains("-")) {
                                String[] str = title.split("-");
                                artist = str[0];
                                title = str[1];
                            }
                            mp3Info.setName(title.trim());
                            mp3Info.setSinger(artist);
                            mp3Info.setDuration(duration/1000);
                            mp3Info.setUrl(url);
                            mp3Info.setSongId(String.valueOf(id));
                            mp3InfoList.add(mp3Info);
                        }
                    }
                }
                cursor.close();
                mPresenter.showMusicList(mp3InfoList);

            }
        }).start();
    }

    @Override
    public void saveSong(final List<LocalSong> localSongs) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LitePal.deleteAll(LocalSong.class);
                for(LocalSong localSong:localSongs){
                    LocalSong song = new LocalSong();
                    song.setName(localSong.getName());
                    song.setSinger(localSong.getSinger());
                    song.setUrl(localSong.getUrl());
                    song.setDuration(localSong.getDuration());
                    song.setSongId(localSong.getSongId());
                    song.save();
                }
                mPresenter.saveLocalSuccess();
            }

        }).start();
    }

}
