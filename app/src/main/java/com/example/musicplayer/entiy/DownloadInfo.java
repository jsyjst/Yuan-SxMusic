package com.example.musicplayer.entiy;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/17
 *     desc   : 下载的信息
 * </pre>
 */

public class DownloadInfo {
    private String songName;
    private String url;
    private String songId;
    private int progress;
    private long currentSize;
    private long totalSize;

    public int getProgress() {
        return progress;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public String getSongName() {
        return songName;
    }


    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
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
}
