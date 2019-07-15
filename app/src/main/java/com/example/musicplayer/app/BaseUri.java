package com.example.musicplayer.app;

/**
 * Created by 残渊 on 2018/10/26.
 */

public class BaseUri {
    public static String SINGER_URL="http://music.163.com/api/search/get/";


    public static String QQ_URL="https://v1.itooi.cn/tencent/";
    public static String PLAY_URL="https://v1.itooi.cn/tencent/url?id=";
    public static String PIC_URL=QQ_URL+"pic?id=";
    public static String LRC_URL=QQ_URL+"lrc?id=";

    public static String STORAGE_IMG_FILE= App.getContext().getExternalFilesDir("") + "/yuanmusic/img/";
    public static String STORAGE_LRC_FILE= App.getContext().getExternalFilesDir("") + "/yuanmusic/lrc/";
    public static String STORAGE_SONG_FILE= App.getContext().getExternalFilesDir("") + "/yuanmusic/song/";

    public static final String BASE_API_URL_LASTFM = "http://ws.audioscrobbler.com/2.0/";
}
