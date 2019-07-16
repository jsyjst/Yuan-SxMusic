package com.example.musicplayer.base.presenter;

import android.provider.ContactsContract;

import com.example.musicplayer.base.view.BaseView;
import com.example.musicplayer.model.DataModel;
import com.example.musicplayer.model.db.DbHelperImpl;
import com.example.musicplayer.model.https.NetworkHelperImpl;
import com.example.musicplayer.model.https.RetrofitFactory;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/14
 *     desc   :
 * </pre>
 */

public class BasePresenter<T extends BaseView> implements IPresenter<T>{

    protected T mView;
    protected DataModel mModel;

    //得到model
    public BasePresenter(){
        if(mModel == null){
            mModel = new DataModel(new NetworkHelperImpl(RetrofitFactory.createRequest()),new DbHelperImpl());
        }
    }
    private CompositeDisposable mCompositeDisposable;
    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public boolean isAttachView() {
        return mView != null;
    }

    //在presenter与View解除时将订阅事件切断
    @Override
    public void detachView() {
        mView = null;
        //清除
        if(mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }
    }

    //网络请求时将订阅事件添加到容器中
    @Override
    public void addRxSubscribe(Disposable disposable) {
        if(mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
}
