package com.example.musicplayer.model.db;

import com.example.musicplayer.entiy.AlbumSong;

import java.util.ArrayList;

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

}
