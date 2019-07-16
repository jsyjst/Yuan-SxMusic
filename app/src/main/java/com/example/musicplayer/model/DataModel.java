package com.example.musicplayer.model;

import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.model.db.DbHelper;
import com.example.musicplayer.model.db.DbHelperImpl;
import com.example.musicplayer.model.https.NetworkHelper;
import com.example.musicplayer.model.https.NetworkHelperImpl;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/15
 *     desc   : 数据操作集合接口类
 * </pre>
 */

public class DataModel implements NetworkHelper, DbHelper {
    private NetworkHelperImpl mNetworkHelper;
    private DbHelperImpl mDbHelper;

    public DataModel(NetworkHelperImpl networkHelper,DbHelperImpl dbHelper){
        mNetworkHelper = networkHelper;
        mDbHelper = dbHelper;
    }

    @Override
    public Observable<AlbumSong> getAlbumSong(String id) {
        return mNetworkHelper.getAlbumSong(id);
    }

    @Override
    public Observable<SearchSong> search(String seek, int offset) {
        return mNetworkHelper.search(seek, offset);
    }

    @Override
    public Observable<Album> searchAlbum(String seek, int offset) {
        return mNetworkHelper.searchAlbum(seek, offset);
    }

    @Override
    public Observable<SearchSong> search(String seek) {
        return mNetworkHelper.search(seek);
    }

    @Override
    public Observable<String> getLrc(String id) {
        return mNetworkHelper.getLrc(id);
    }

    @Override
    public Observable<SingerImg> getSingerImg(String singer) {
        return mNetworkHelper.getSingerImg(singer);
    }

    @Override
    public void insertAllAlbumSong(ArrayList<AlbumSong.DataBean.GetSongInfoBean> songList) {
         mDbHelper.insertAllAlbumSong(songList);
    }
}
