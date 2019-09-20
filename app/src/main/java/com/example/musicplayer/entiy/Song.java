package com.example.musicplayer.entiy;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by 残渊 on 2018/10/19.
 */

public class Song extends LitePalSupport implements Serializable {
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
    private int listType; //歌曲列表类别,0表示当前没有列表，即可能在播放网络歌曲
    private boolean isDownload;//是否为下载的歌曲


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

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public String toString(){
        return "songName="+songName+",singer="+singer+",url="+url+",imgUrl="+imgUrl
                +",duration="+duration+",currentTime="+currentTime+",current="+current
                +",songId="+songId+",isOnline="+isOnline+",listType="+listType;

    }
}
