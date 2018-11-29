package com.example.musicplayer.entiy;

import org.litepal.crud.LitePalSupport;

/**
 * Created by 残渊 on 2018/11/29.
 */

public class History extends LitePalSupport {
    String history;
    int id;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
