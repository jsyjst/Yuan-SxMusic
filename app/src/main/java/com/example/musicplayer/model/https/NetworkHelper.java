package com.example.musicplayer.model.https;

import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.OnlineSong;
import com.example.musicplayer.entiy.OnlineSongLrc;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.entiy.SongLrc;
import com.example.musicplayer.entiy.SongUrl;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/15
 *     desc   : 网络操作接口集合
 * </pre>
 */

public interface NetworkHelper {
    Observable<AlbumSong> getAlbumSong(String id); //得到专辑
    Observable<SearchSong> search(String seek, int offset); //搜索歌曲
    Observable<Album> searchAlbum(String seek,int offset);//搜索照片
    Observable<SongLrc> getLrc(String seek);//获取歌词
    Observable<OnlineSongLrc> getOnlineSongLrc(String songId);//获取网络歌曲的歌词
    Observable<SingerImg> getSingerImg(String singer);//获取歌手头像
    Observable<SongUrl> getSongUrl(String data);//获取播放地址
}

