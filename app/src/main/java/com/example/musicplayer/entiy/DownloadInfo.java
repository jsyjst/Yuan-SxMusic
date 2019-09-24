package com.example.musicplayer.entiy;

import org.litepal.crud.LitePalSupport;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/17
 *     desc   : 下载的信息
 * </pre>
 */

public class DownloadInfo extends LitePalSupport {
    private long id;
    private String songName;
    private String singer;
    private String url;
    private String songId;
    private int progress;
    private long currentSize;
    private long totalSize;
//    private Song song;
    private long duration;
    private int position;//正在下载歌曲列表中的位置
    private int status;//下载歌曲的状态

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
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

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

//    public Song getSong() {
//        return song;
//    }
//
//    public void setSong(Song song) {
//        this.song = song;
//    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
