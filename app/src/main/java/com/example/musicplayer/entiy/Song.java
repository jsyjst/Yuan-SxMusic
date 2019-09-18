package com.example.musicplayer.entiy;

import java.io.Serializable;

/**
 * Created by 残渊 on 2018/10/19.
 */

public class Song implements Serializable {
    private static final long serialVersionUID=1L;

    private String songId; //歌曲id
    private String qqId;//专属本地音乐，本地音乐在qq音乐中的songId
    private String mediaId;//播放id,下载需要用到
   private String singer; //歌手
    private long duration; //总时长
    private String songName; //歌曲名字
    private String url;  //歌曲url
    private long currentTime; //歌曲播放时长位置
    private int current;//在音乐列表的位置
    private String imgUrl; //歌曲照片
    private boolean isOnline; //是否为网络歌曲
    private int listType; //歌曲列表类别

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

    public int getListType() {
        return listType;
    }

    public void setListType(int listType) {
        this.listType = listType;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String toString(){
        return "songName="+songName+",singer="+singer+",url="+url+",imgUrl="+imgUrl
                +",duration="+duration+",currentTime="+currentTime+",current="+current
                +",songId="+songId+",isOnline="+isOnline+",listType="+listType;

    }
}
