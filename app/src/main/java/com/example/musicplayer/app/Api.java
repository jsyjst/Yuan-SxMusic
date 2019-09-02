package com.example.musicplayer.app;

/**
 * Created by 残渊 on 2018/10/26.
 */

public class Api {
    public static String SINGER_URL="http://music.163.com/api/search/get/";

    //MessAPI
    public static String QQ_URL="https://v1.itooi.cn/tencent/";

    public static String PLAY_URL="https://v1.itooi.cn/tencent/url?id=";
    public static String PIC_URL=QQ_URL+"pic?id=";
    public static String LRC_URL=QQ_URL+"lrc?id=";

    public static String STORAGE_IMG_FILE= App.getContext().getExternalFilesDir("") + "/yuanmusic/img/";
    public static String STORAGE_LRC_FILE= App.getContext().getExternalFilesDir("") + "/yuanmusic/lrc/";
    public static String STORAGE_SONG_FILE= App.getContext().getExternalFilesDir("") + "/yuanmusic/song/";

    public static final String BASE_API_URL_LASTFM = "http://ws.audioscrobbler.com/2.0/";

    //Fiddler抓包qq音乐网站后的地址
    public static final String FIDDLER_BASE_QQ_URL ="https://c.y.qq.com/";
    public static final String FIDDLER_BASE_SONG_URL="https://u.y.qq.com/"; //获取播放地址的baseUrl
    //搜索功能
    public static final String SEARCH_SONG ="soso/fcgi-bin/client_search_cp?n=30&format=json"; //歌曲，n为一页30首
    public static final String SEARCH_ALBUM="soso/fcgi-bin/client_search_cp?n=20&format=json&t=8";//专辑，n为一页20张
    //得到歌曲的播放地址
    public static final String SONG_URL="cgi-bin/musicu.fcg?format=json";
    public static final String SONG_URL_DATA_LEFT="%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22songmid%22%3A%5B%22";
    public static final String SONG_URL_DATA_RIGHT="%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%221443481947%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%2C%22comm%22%3A%7B%22uin%22%3A%221443481947%22%2C%22format%22%3A%22json%22%2C%22ct%22%3A24%2C%22cv%22%3A0%7D%7D ";
    //专辑照片
    public static final String ALBUM_PIC="http://y.gtimg.cn/music/photo_new/T002R180x180M000";
    public static final String JPG=".jpg";
    //专辑详细
    public static final String ALBUM_DETAIL="v8/fcg-bin/fcg_v8_album_info_cp.fcg?format=json";

}
