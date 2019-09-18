package com.example.musicplayer.entiy;

import org.litepal.crud.LitePalSupport;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/18
 *     desc   : 下载的歌曲
 * </pre>
 */

public class DownloadSong extends LitePalSupport {
    private int id;
    private String songId ;
    private String mediaId; //下载标识符
    private String name;
    private String singer;
    private String url;
    private String pic;
    private long duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongId() {
        return songId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
