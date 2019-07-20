package com.example.musicplayer.app;

/**
 * Created by 残渊 on 2018/11/23.
 */

public class Constant {
    public static final int OFFSET=30;
    public static final int TYPE_SONG=1;
    public static final int TYPE_ALBUM=2;
    public static final int TYPE_ALBUM_SONG=3;
    public static final int LIST_TYPE_LOCAL=0;
    public static final int LIST_TYPE_ONLINE=1;
    public static final int LIST_TYPE_LOVE=2;
    public static final int LIST_TYPE_HISTORY=3;
    public static final int HISTORY_MAX_SIZE=100;
    public static final String LOCAL_IMG="local_img";
    public static final String SEARCH_SONG= "search_song";
    public static final String SEARCH_SONG_MORE= "search_song_more";
    public static final String SEARCH_ALBUM="search_album";
    public static final String SEARCH_ALBUM_MORE="search_album_more";
    public static final String ALBUM="album";


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



    //TAG
    public static final String TAG_ERROR = "error";
}
