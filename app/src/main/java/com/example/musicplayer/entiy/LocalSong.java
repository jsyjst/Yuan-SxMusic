package com.example.musicplayer.entiy;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

/**
 * Created by 残渊 on 2018/10/10.
 */

public class LocalSong extends LitePalSupport{
    private int id;
    private String name;
    private String singer;
    private String url;
    private String pic;
    private long duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }
}
