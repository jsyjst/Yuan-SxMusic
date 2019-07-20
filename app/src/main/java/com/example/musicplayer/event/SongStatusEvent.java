package com.example.musicplayer.event;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/19
 *     desc   : 歌曲播放事件
 * </pre>
 */

public class SongStatusEvent {
    private int songStatus;

    public SongStatusEvent(int songStatus){
        this.songStatus = songStatus;
    }

    public int getSongStatus() {
        return songStatus;
    }
}
