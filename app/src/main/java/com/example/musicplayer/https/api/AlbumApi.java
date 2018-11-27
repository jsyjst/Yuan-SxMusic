package com.example.musicplayer.https.api;

import com.example.musicplayer.entiy.AlbumSong;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 残渊 on 2018/11/27.
 */

public interface AlbumApi {
    @GET("album?key=579621905")
    Observable<AlbumSong> getAlbumSong(@Query("id")String id);
}
