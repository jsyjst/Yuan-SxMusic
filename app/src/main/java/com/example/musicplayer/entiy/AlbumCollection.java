package com.example.musicplayer.entiy;

import org.litepal.crud.LitePalSupport;

/**
 * Created by 残渊 on 2018/12/4.
 */

public class AlbumCollection extends LitePalSupport {
    private long id;
    private String albumId;
    private String albumName;
    private String singerName;
    private String albumPic;
    private String publicTime;
    private String SongNum;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumPic() {
        return albumPic;
    }

    public String getPublicTime() {
        return publicTime;
    }

    public String getSingerName() {
        return singerName;
    }

    public String getSongNum() {
        return SongNum;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setAlbumPic(String albumPic) {
        this.albumPic = albumPic;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public void setSongNum(String songNum) {
        SongNum = songNum;
    }
}
