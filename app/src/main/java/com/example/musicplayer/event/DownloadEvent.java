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

    public DownloadEvent(int status, DownloadInfo downloadInfo){
        downloadStatus = status;
        this.downloadInfo = downloadInfo;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }
}
