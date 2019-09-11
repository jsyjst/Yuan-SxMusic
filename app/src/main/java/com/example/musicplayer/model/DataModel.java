package com.example.musicplayer.model;

import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.entiy.OnlineSongLrc;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.entiy.SongLrc;
import com.example.musicplayer.entiy.SongUrl;
import com.example.musicplayer.model.db.DbHelper;
import com.example.musicplayer.model.db.DbHelperImpl;
import com.example.musicplayer.model.https.NetworkHelper;
import com.example.musicplayer.model.https.NetworkHelperImpl;
import com.example.musicplayer.model.prefs.PreferencesHelper;
import com.example.musicplayer.model.prefs.PreferencesHelperImpl;

import java.util.List;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/15
 *     desc   : 数据操作集合接口类
 * </pre>
 */

public class DataModel implements NetworkHelper, DbHelper,PreferencesHelper {
    private NetworkHelperImpl mNetworkHelper;
    private DbHelperImpl mDbHelper;
    private PreferencesHelperImpl mPreferencesHelper;

    public DataModel(NetworkHelperImpl networkHelper,DbHelperImpl dbHelper,PreferencesHelperImpl preferencesHelper){
        mNetworkHelper = networkHelper;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
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
    public Observable<SongLrc> getLrc(String seek) {
        return mNetworkHelper.getLrc(seek);
    }

    @Override
    public Observable<OnlineSongLrc> getOnlineSongLrc(String songId) {
        return mNetworkHelper.getOnlineSongLrc(songId);
    }

    @Override
    public Observable<SingerImg> getSingerImg(String singer) {
        return mNetworkHelper.getSingerImg(singer);
    }

    @Override
    public Observable<SongUrl> getSongUrl(String data) {
        return mNetworkHelper.getSongUrl(data);
    }

    @Override
    public void insertAllAlbumSong(List<AlbumSong.DataBean.ListBean> songList) {
         mDbHelper.insertAllAlbumSong(songList);
    }

    @Override
    public List<LocalSong> getLocalMp3Info() {
        return mDbHelper.getLocalMp3Info();
    }

    @Override
    public boolean saveSong(List<LocalSong> localSongs) {
        return mDbHelper.saveSong(localSongs);
    }

    @Override
    public boolean queryLove(String songId) {
        return mDbHelper.queryLove(songId);
    }

    @Override
    public boolean saveToLove(Song song) {
        return mDbHelper.saveToLove(song);
    }

    @Override
    public boolean deleteFromLove(String songId) {
        return mDbHelper.deleteFromLove(songId);
    }

    @Override
    public void setPlayMode(int mode) {
        mPreferencesHelper.setPlayMode(mode);
    }

    @Override
    public int getPlayMode() {
        return mPreferencesHelper.getPlayMode();
    }
}
