package com.example.musicplayer.model.db;

import android.database.Cursor;
import android.provider.MediaStore;

import com.example.musicplayer.app.App;
import com.example.musicplayer.app.Api;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.entiy.Love;
import com.example.musicplayer.entiy.OnlineSong;
import com.example.musicplayer.entiy.Song;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/16
 *     desc   : 数据库操作类
 * </pre>
 */

public class DbHelperImpl implements DbHelper {

    @Override
    public void insertAllAlbumSong(List<AlbumSong.DataBean.ListBean> songList) {
        LitePal.deleteAll(OnlineSong.class);
        for (int i = 0; i < songList.size(); i++) {
            AlbumSong.DataBean.ListBean song = songList.get(i);
            OnlineSong onlineSong = new OnlineSong();
            onlineSong.setId(i + 1);
            onlineSong.setName(song.getSongname());
            onlineSong.setSinger(song.getSinger().get(0).getName());
            onlineSong.setSongId(song.getSongmid());
            onlineSong.setDuration(song.getInterval());
            onlineSong.setPic(Api.ALBUM_PIC + song.getAlbummid()+Api.JPG);
            onlineSong.setUrl(null);
            onlineSong.setLrc(null);
            onlineSong.save();
        }
    }

    @Override
    public List<LocalSong> getLocalMp3Info() {

        List<LocalSong> mp3InfoList = new ArrayList<>();
        Cursor cursor = App.getContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            LocalSong mp3Info = new LocalSong();
            String title = cursor.getString((cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题
            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
            long duration = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
            long size = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.SIZE));    //文件大小
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
                    mp3Info.setDuration(duration / 1000);
                    mp3Info.setUrl(url);
                    mp3Info.setSongId(i+"");
                    mp3InfoList.add(mp3Info);
                }
            }
        }
        cursor.close();
        return mp3InfoList;
    }

    @Override
    public boolean saveSong(List<LocalSong> localSongs) {
        LitePal.deleteAll(LocalSong.class);
        for (LocalSong localSong : localSongs) {
            LocalSong song = new LocalSong();
            song.setName(localSong.getName());
            song.setSinger(localSong.getSinger());
            song.setUrl(localSong.getUrl());
            song.setSongId(localSong.getSongId());
            song.setDuration(localSong.getDuration());
            if(!song.save()) return false;
        }
        return true;
    }

    @Override
    public boolean queryLove(String songId) {
         List<Love> love=LitePal.where("songId=?",songId).find(Love.class);
        return love.size() != 0;
    }

    @Override
    public boolean saveToLove(Song song) {
        Love love =new Love();
        love.setName(song.getSongName());
        love.setSinger(song.getSinger());
        love.setUrl(song.getUrl());
        love.setPic(song.getImgUrl());
        love.setDuration(song.getDuration());
        love.setSongId(song.getSongId());
        love.setOnline(song.isOnline());
        love.setQqId(song.getQqId());
        love.setMediaId(song.getMediaId());
        return love.save();
    }

    @Override
    public boolean deleteFromLove(String songId) {
        return LitePal.deleteAll(Love.class,"songId=?",songId) !=0;
    }
}
