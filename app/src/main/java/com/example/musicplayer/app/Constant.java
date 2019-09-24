package com.example.musicplayer.app;

/**
 * Created by 残渊 on 2018/11/23.
 */

public class Constant {
    public static final String TAG = "jsyjst";

    public static final int OFFSET = 30;
    public static final int TYPE_SONG = 1;
    public static final int TYPE_ALBUM = 2;

    //播放列表
    public static final int LIST_TYPE_LOCAL = 1;   //本地列表
    public static final int LIST_TYPE_ONLINE = 2;  //专辑列表
    public static final int LIST_TYPE_LOVE = 3;    //我的收藏列表
    public static final int LIST_TYPE_HISTORY = 4;  //最近播放列表
    public static final int LIST_TYPE_DOWNLOAD = 5; //下载列表

    public static final int HISTORY_MAX_SIZE = 100;


    //布局
    public static final int NORMAL_STATE = 0;
    public static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;

    //playerStatus
    public static final String PLAYER_STATUS = "PlayerStatus";
    public static final int SONG_PLAY = 0;
    public static final int SONG_PAUSE = 1;
    public static final int SONG_RESUME = 2;
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
    public static final int SONG_ONLINE = 0;
    public static final int SONG_LOCAL = 1;


    //后缀
    public static final String LRC = ".lrc";

    //音乐id
    public static final String SONG_ID_UNFIND = "unFind";

    //播放顺序
    public static final int PLAY_ORDER = 0;//顺序播放
    public static final int PLAY_SINGLE = 1;//单曲循环
    public static final int PLAY_RANDOM = 2;//随机播放

    //Preferences
    public static final String SHARED_PREFERENCES_NAME = "prefs";
    public static final String PREFS_PLAY_MODE = "play_mode";//播放状态

    //download
    public final static int TYPE_DOWNLOADING = 0;
    public final static int TYPE_DOWNLOAD_PAUSED = 1;
    public final static int TYPE_DOWNLOAD_CANCELED = 2;
    public final static int TYPE_DOWNLOAD_SUCCESS = 3;
    public final static int TYPE_DOWNLOAD_FAILED = 4;
    public final static int TYPE_DOWNLOADED = 5;
    public final static int TYPE_DOWNLOAD_ADD=6;

    //正在下载歌曲列表的状态
    public final static int DOWNLOAD_PAUSED = 0;
    public final static int DOWNLOAD_WAIT=1;
    public final static int DOWNLOAD_ING=2;
    public final static int DOWNLOAD_READY=3;
}
