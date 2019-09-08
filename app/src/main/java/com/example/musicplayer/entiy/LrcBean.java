package com.example.musicplayer.entiy;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/03/18
 *     desc   : 歌词实体类
 * </pre>
 */

public class LrcBean {
    private String lrc;  //歌词
    private long start;  //开始时间
    private long end;   //结束时间

    public LrcBean() {
    }

    public LrcBean(String text, long start, long end) {
        this.lrc = text;
        this.start = start;
        this.end = end;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}

