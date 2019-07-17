package com.example.musicplayer.model.db;

import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.LocalSong;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/16
 *     desc   : 数据库操作接口
 * </pre>
 */

public interface DbHelper {
    /**
     *  将所有搜索专辑列表中的歌曲都保存到网络歌曲数据库中
     * @param songList 专辑列表
     */
    void insertAllAlbumSong(ArrayList<AlbumSong.DataBean.GetSongInfoBean> songList);
    List<LocalSong> getLocalMp3Info();  //得到本地列表
    boolean saveSong(List<LocalSong> localSongs);//将本地音乐放到数据库中

}
