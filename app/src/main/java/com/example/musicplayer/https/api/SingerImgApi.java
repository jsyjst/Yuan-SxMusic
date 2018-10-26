package com.example.musicplayer.https.api;

import com.example.musicplayer.entiy.SingerImg;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 残渊 on 2018/10/26.
 */

public interface SingerImgApi {
    @POST("web?csrf_token=&type=100")
    @FormUrlEncoded
    Observable<SingerImg> getSingerImg(@Field("s")String singer);
}
