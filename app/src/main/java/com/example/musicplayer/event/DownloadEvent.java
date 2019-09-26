package com.example.musicplayer.event;

import com.example.musicplayer.entiy.DownloadInfo;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/16
 *     desc   :
 * </pre>
 */

public class DownloadEvent {
    private int downloadStatus;//下载的状态
    private DownloadInfo downloadInfo;
    private int position;


    public DownloadEvent(int status){
        downloadStatus = status;
    }
    public DownloadEvent(int status, DownloadInfo downloadInfo){
        downloadStatus = status;
        this.downloadInfo = downloadInfo;
    }

    public DownloadEvent(int status,int position){
        downloadStatus = status;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public int getDownloadStatus() {
        return downloadStatus;
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }
}
