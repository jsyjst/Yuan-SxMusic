package com.example.musicplayer.util;

import android.database.Cursor;
import android.provider.MediaStore;

import com.example.musicplayer.constant.MyApplication;
import com.example.musicplayer.entiy.Mp3Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/10/22.
 */

public class MediaUntil{
    public static List<Mp3Info> getMp3Info(){
        List<Mp3Info> mp3InfoList=new ArrayList<>();
        Cursor cursor = MyApplication.getContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        for (int i = 0; i < cursor.getCount(); i++) {
            Mp3Info mp3Info = new Mp3Info();
            cursor.moveToNext();
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
                    mp3Info.setTitle(title.trim());
                    mp3Info.setArtist(artist);
                    mp3Info.setDuration(duration);
                    mp3Info.setSize(size);
                    mp3Info.setUrl(url);
                    mp3InfoList.add(mp3Info);
                }
            }
        }
        cursor.close();
        return mp3InfoList;
    }
}
