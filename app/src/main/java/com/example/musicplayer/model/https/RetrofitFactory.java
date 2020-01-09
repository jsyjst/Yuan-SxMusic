package com.example.musicplayer.model.https;

import com.example.musicplayer.app.Api;
import com.example.musicplayer.model.https.api.RetrofitService;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/15
 *     desc   : 封装Retrofit，方便外部调用
 * </pre>
 */

public class RetrofitFactory {
    private static OkHttpClient sOkHttpClient;
    private static Retrofit sRetrofit;
    private static Retrofit songUrlRetrofit;
    private static Retrofit sSingerPicRetrofit;


    // 创建网络请求Observable
    public static RetrofitService createRequest() {
        return getRetrofit().create(RetrofitService.class);
    }

    public static RetrofitService createRequestOfSinger() {
        return getRetrofitOfSinger().create(RetrofitService.class);
    }

    public static RetrofitService createRequestOfSongUrl(){
        return getRetrofitOfSongUrl().create(RetrofitService.class);
    }
    // 配置Retrofit
    private synchronized static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(Api.FIDDLER_BASE_QQ_URL) // 对应服务端的host
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())) // 这里还结合了Gson
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 把Retrofit请求转化成RxJava的Observable
                    .build();
        }
        return sRetrofit;
    }

    // 获取歌手照片
    private synchronized static Retrofit getRetrofitOfSinger() {
        if (sSingerPicRetrofit == null) {
            sSingerPicRetrofit = new Retrofit.Builder()
                    .baseUrl(Api.SINGER_PIC_BASE_URL) // 对应服务端的host
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create()) // 这里还结合了Gson
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 把Retrofit请求转化成RxJava的Observable
                    .build();
        }
        return sSingerPicRetrofit;
    }

    //得到播放地址
    private synchronized static Retrofit getRetrofitOfSongUrl() {
        if (songUrlRetrofit == null) {
            songUrlRetrofit = new Retrofit.Builder()
                    .baseUrl(Api.FIDDLER_BASE_SONG_URL) // 对应服务端的host
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create()) // 这里还结合了Gson
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 把Retrofit请求转化成RxJava的Observable
                    .build();
        }
        return songUrlRetrofit;
    }

    //配置OkHttp
    private synchronized static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            sOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100,TimeUnit.SECONDS)
                    .writeTimeout(100,TimeUnit.SECONDS)
                    .build();
        }
        return sOkHttpClient;
    }
}
