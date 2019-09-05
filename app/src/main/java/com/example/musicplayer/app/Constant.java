package com.example.musicplayer.app;

/**
 * Created by 残渊 on 2018/11/23.
 */

public class Constant {
    public static final String TAG = "jsyjst";

    public static final int OFFSET=30;
    public static final int TYPE_SONG=1;
    public static final int TYPE_ALBUM=2;
    public static final int TYPE_ALBUM_SONG=3;
    public static final int LIST_TYPE_LOCAL=0;
    public static final int LIST_TYPE_ONLINE=1;
    public static final int LIST_TYPE_LOVE=2;
    public static final int LIST_TYPE_HISTORY=3;
    public static final int HISTORY_MAX_SIZE=100;


    //布局
    public static final int NORMAL_STATE = 0;
    public static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;

    //playerStatus
    public static final String PLAYER_STATUS="PlayerStatus";
    public static final int SONG_PLAY = 0;
    public static final int SONG_PAUSE= 1;
    public static final int SONG_RESUME= 2;
    public static final int SONG_CHANGE = 3;


    //KEY
    public static final String ALBUM_ID_KEY = "id";
    public static final String ALBUM_NAME_KEY = "albumName";
    public static final String SINGER_NAME_KEY = "singerName";
    public static final String ALBUM_PIC_KEY = "albumPic";
    public static final String PUBLIC_TIME_KEY = "publicTime";


    //TAG
    public static final String TAG_ERROR = "error";

    //网络与非网络歌曲
    public static final int SONG_ONLINE=0;
    public static final int SONG_LOCAL=1;


}
