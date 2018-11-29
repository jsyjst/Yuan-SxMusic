package com.example.musicplayer.entiy;

import java.io.Serializable;

/**
 * Created by 残渊 on 2018/10/19.
 */

public class Song implements Serializable {
    private static final long serialVersionUID=1L;

    private String onlineId;
    private String singer;
    private long duration;
    private String songName;
    private String url;
    private long currentTime;
    private int current;//在本地音乐的位置
    private String imgUrl;
    private boolean isOnline;
    private boolean isOnlineAlbum;

    public String getImgUrl() {
        return imgUrl;
    }

    public int getCurrent() {
        return current;
    }

    public long getDuration() {
        return duration;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public String getUrl() {
        return url;
    }

    public String getSinger() {
        return singer;
    }

    public String getSongName() {
        return songName;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isOnlineAlbum() {
        return isOnlineAlbum;
    }

    public void setOnlineAlbum(boolean onlineAlbum) {
        isOnlineAlbum = onlineAlbum;
    }

    public String getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(String onlineId) {
        this.onlineId = onlineId;
    }

    public String toString(){
        return "songName="+songName+",singer="+singer+",url="+url+",imgUrl="+imgUrl
                +",duration="+duration+",currentTime="+currentTime+",current="+current
                +",onlineId="+onlineId+",isOnline="+isOnline+",isOnlineAlbum="+isOnlineAlbum;

    }
}
