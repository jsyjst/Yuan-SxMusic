package com.example.musicplayer.https.api;

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
}
