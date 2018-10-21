package com.example.musicplayer.entiy;

import java.io.Serializable;

/**
 * Created by 残渊 on 2018/10/19.
 */

public class Song implements Serializable {
    private static final long serialVersionUID=1L;

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

    public String toString(){
        return
                "artist="+artist+",title="+title+",url="+url+",duration="+duration+",size"+size;

    }
}
