package com.example.musicplayer.https.api;

import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.SeachSong;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 残渊 on 2018/11/21.
 */

public interface SearchApi {
    @GET("search?key=579621905&type=song&limit=30")
    Observable<SeachSong> search(@Query("s") String seek,@Query("offset")int offset);

    @GET("search?key=579621905&type=album&limit=30")
    Observable<Album> searchAlbum(@Query("s") String seek,@Query("offset")int offset);

    /**
     * 搜索歌词
     * @param seek
     * @return
     */
    @GET("search?key=579621905&type=song&limit=10&offset=0")
    Observable<SeachSong> search(@Query("s") String seek);

    /**
     *
     * @param id 歌曲id
     * @return
     */
    @GET("lrc?key=579621905")
    Observable<String> getLrc(@Query("id") String id);
}
