package com.example.musicplayer.model.https.api;

import com.example.musicplayer.app.Api;
import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.OnlineSongLrc;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.entiy.SongLrc;
import com.example.musicplayer.entiy.SongUrl;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
    /**
     *  搜索歌曲：https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=2&n=2&w=周杰伦&format=json
     */
    @GET(Api.SEARCH_SONG)
    Observable<SearchSong> search(@Query("w") String seek, @Query("p")int offset);

    /**
     * 搜索专辑：https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=2&w=林宥嘉&format=json&t=8
     * @param seek 搜索关键字
     * @param offset 页数
     */
    @GET(Api.SEARCH_ALBUM)
    Observable<Album> searchAlbum(@Query("w") String seek, @Query("p")int offset);

    /**
     * 专辑详细：https://c.y.qq.com/v8/fcg-bin/fcg_v8_album_info_cp.fcg?albummid=004YodY33zsWTT&format=json
     * @param id 专辑mid
     */
    @GET(Api.ALBUM_DETAIL)
    Observable<AlbumSong> getAlbumSong(@Query("albummid")String id);

    /**
     * 得到歌曲的播放地址，变化的只有songmid，即{}所示
     * https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data=%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22       +
     * songmid%22%3A%5B%22{003wFozn3V3Ra0} +
     * %22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%221443481947%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%2C%22comm%22%3A%7B%22uin%22%3A%221443481947%22%2C%22format%22%3A%22json%22%2C%22ct%22%3A24%2C%22cv%22%3A0%7D%7D
     */
    @Headers(Api.HEADER_USER_AGENT)
    @GET(Api.SONG_URL)
    Observable<SongUrl> getSongUrl(@Query("data") String data);

    /**
     * 根据songmid获取歌词：https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?songmid=000wocYU11tSzS&format=json&nobase64=1
     * headers中的Referer是qq用来防盗链的
     */
    @Headers(Api.HEADER_REFERER)
    @GET(Api.ONLINE_SONG_LRC)
    Observable<OnlineSongLrc> getOnlineSongLrc(@Query("songmid") String songId);

    /**
     * 搜索歌词：https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=1&w=说谎&format=json&t=7
     * @param seek 关键词
     */
    @GET(Api.SONG_LRC)
    Observable<SongLrc> getLrc(@Query("w") String seek);

    /**
     * 得到歌手照片，主要用于本地音乐：http://music.163.com/api/search/get/web?s=刘瑞琦&type=100
     * @param singer 歌手名字
     */
    @Headers(Api.HEADER_USER_AGENT)
    @POST(Api.SINGER_PIC)
    @FormUrlEncoded
    Observable<SingerImg> getSingerImg(@Field("s")String singer);

}
