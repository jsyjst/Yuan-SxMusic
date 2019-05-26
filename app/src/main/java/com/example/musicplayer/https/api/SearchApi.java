package com.example.musicplayer.https.api;

import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.SearchSong;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 残渊 on 2018/11/21.
 */

public interface SearchApi {
    /**
     *  搜索歌曲
     *  https://v1.itooi.cn/tencent/search?keyword=周杰伦&type=song&page=1&pageSize=30
     */
    @GET("search?type=song")
    Observable<SearchSong> search(@Query("keyword") String seek, @Query("page")int offset);

    @GET("search?type=album")
    Observable<Album> searchAlbum(@Query("keyword") String seek,@Query("page")int offset);

    /**
     * 搜索歌词
     * @param seek
     * @return
     */
    @GET("search?type=song&page=1")
    Observable<SearchSong> search(@Query("keyword") String seek);

    /**
     *
     * @param id 歌曲id
     * @return
     */
    @GET("lrc?key=579621905")
    Observable<String> getLrc(@Query("id") String id);
}
