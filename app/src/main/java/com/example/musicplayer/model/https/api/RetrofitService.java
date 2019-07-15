package com.example.musicplayer.model.https.api;

import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.SingerImg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/15
 *     desc   :
 * </pre>
 */

public interface RetrofitService {
    @GET("album?")
    Observable<AlbumSong> getAlbumSong(@Query("id")String id);

    /**
     *  搜索歌曲
     *  https://v1.itooi.cn/tencent/search?keyword=周杰伦&type=song&page=1&pageSize=30
     */
    @GET("search?type=song")
    Observable<SearchSong> search(@Query("keyword") String seek, @Query("page")int offset);

    @GET("search?type=album")
    Observable<Album> searchAlbum(@Query("keyword") String seek, @Query("page")int offset);

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
    @GET("lrc?")
    Observable<String> getLrc(@Query("id") String id);

    @POST("web?csrf_token=&type=100")
    @FormUrlEncoded
    Observable<SingerImg> getSingerImg(@Field("s")String singer);
}
