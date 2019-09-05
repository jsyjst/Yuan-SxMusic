package com.example.musicplayer.entiy;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/04
 *     desc   : qq音乐平台的网络歌词
 * </pre>
 */

public class OnlineSongLrc {
    private int code;
    private String lyric;

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getLyric() {
        return lyric;
    }
}
