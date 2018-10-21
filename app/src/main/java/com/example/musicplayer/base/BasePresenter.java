package com.example.musicplayer.base;

/**
 * Created by 残渊 on 2018/10/17.
 */

public class BasePresenter<V> {
    private V view;

    public void attachView(V view){
        this.view=view;
    }

    public void detachView(){
        this.view=null;
    }
    protected boolean isAttachView(){
        return view!=null;
    }
    protected V getMvpView(){
        return view;
    }
}
