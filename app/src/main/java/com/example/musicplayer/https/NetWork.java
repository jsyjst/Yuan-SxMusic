package com.example.musicplayer.https;

import com.example.musicplayer.constant.BaseUri;
import com.example.musicplayer.https.api.SingerImgApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.musicplayer.constant.BaseUri.SINGER_URL;

/**
 * Created by 残渊 on 2018/10/26.
 */

public class NetWork {
    private static okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS);
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static  SingerImgApi singerImgApi;

    public static SingerImgApi getSingerImgApi(){
        if(singerImgApi==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(SINGER_URL)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            singerImgApi=retrofit.create(SingerImgApi.class);
        }
        return singerImgApi;
    }


}
