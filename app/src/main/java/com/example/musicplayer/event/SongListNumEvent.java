package com.example.musicplayer.event;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/20
 *     desc   : 歌曲列表数量变化消息
 * </pre>
 */

public class SongListNumEvent {
    private int type;
    public SongListNumEvent(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
