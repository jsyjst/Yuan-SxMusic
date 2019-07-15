package com.example.musicplayer.model.https;

import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.model.https.api.RetrofitService;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/15
 *     desc   : 网络操作实现类
 * </pre>
 */

public class NetworkHelperImpl implements NetworkHelper {
    private RetrofitService mRetrofitService;

    public NetworkHelperImpl(RetrofitService retrofitService){
        mRetrofitService = retrofitService;
    }

    @Override
    public Observable<AlbumSong> getAlbumSong(String id) {
        return mRetrofitService.getAlbumSong(id);
    }

    @Override
    public Observable<SearchSong> search(String seek, int offset) {
        return mRetrofitService.search(seek, offset);
    }

    @Override
    public Observable<Album> searchAlbum(String seek, int offset) {
        return mRetrofitService.searchAlbum(seek, offset);
    }

    @Override
    public Observable<SearchSong> search(String seek) {
        return mRetrofitService.search(seek);
    }

    @Override
    public Observable<String> getLrc(String id) {
        return mRetrofitService.getLrc(id);
    }

    @Override
    public Observable<SingerImg> getSingerImg(String singer) {
        return mRetrofitService.getSingerImg(singer);
    }
}
