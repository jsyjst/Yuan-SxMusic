package com.example.musicplayer.configure;

/**
 * Created by 残渊 on 2018/10/26.
 */

public class BaseUri {
    public static String SINGER_URL="http://music.163.com/api/search/get/";

    public static String SEARCH_URL="https://api.bzqll.com/music/netease/";

    public static String QQ_URL="https://api.bzqll.com/music/tencent/";

    public static String STORAGE_IMG_FILE=MyApplication.getContext().getExternalFilesDir("") + "/yuanmusic/img/";
    public static String STORAGE_LRC_FILE=MyApplication.getContext().getExternalFilesDir("") + "/yuanmusic/lrc/";
    public static String STORAGE_SONG_FILE=MyApplication.getContext().getExternalFilesDir("") + "/yuanmusic/song/";

    public static final String BASE_API_URL_LASTFM = "http://ws.audioscrobbler.com/2.0/";
}
