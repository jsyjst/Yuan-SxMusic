package com.example.musicplayer.entiy;

/**
 * Created by 残渊 on 2018/10/10.
 */

public class Mp3Info {
    private String artist;
    private long duration;
    private String title;
    private long size;
    private String url;

    public long getSize() {
        return size;
    }

    public String getArtist() {
        return artist;
    }

    public long getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
