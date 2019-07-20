package com.example.musicplayer.event;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/19
 *     desc   : 收藏歌曲事件
 * </pre>
 */

public class SongCollectionEvent {
    private boolean isLove;
    public SongCollectionEvent(boolean isLove){
        this.isLove = isLove;
    }

    public boolean isLove() {
        return isLove;
    }
}
