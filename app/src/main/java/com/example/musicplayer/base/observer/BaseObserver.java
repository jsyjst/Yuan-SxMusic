package com.example.musicplayer.base.observer;

import android.net.ParseException;
import android.os.Handler;
import android.util.Log;

import com.example.musicplayer.R;
import com.example.musicplayer.app.App;
import com.example.musicplayer.base.view.BaseView;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.UnknownHostException;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

import static com.example.musicplayer.app.Constant.TAG_ERROR;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/16
 *     desc   : 对RxJava的下游即数据进行处理
 * </pre>
 */

public class BaseObserver<T> extends ResourceObserver<T> {
    private static final String TAG = "BaseObserver";
    private boolean isShowLoadingView = true;
    private boolean isShowErrorView =true;

    private BaseView baseView;

    private BaseObserver(){}

    protected BaseObserver(BaseView baseView){
        this(baseView,false,false);
    }

    protected BaseObserver(BaseView baseView,boolean isShowLoadingView){
        this(baseView,isShowLoadingView,false);
    }

    protected BaseObserver(BaseView baseView,boolean isShowLoadingView, boolean isShowErrorView){
        this.baseView = baseView;
        this.isShowLoadingView = isShowLoadingView;
        this.isShowErrorView = isShowErrorView;
    }

    @Override
    protected void onStart() {
        if(isShowLoadingView) baseView.showLoading();
    }



    @Override
    public void onNext(T t) {
        new Handler().postDelayed(()->{
            baseView.showNormalView();
        },500);

    }

    @Override
    public void onError(Throwable e) {
        new Handler().postDelayed(()->{
            if(isShowErrorView) baseView.showErrorView();
        },500);
        e.printStackTrace();
        if (e instanceof UnknownHostException) {
            Log.e(TAG_ERROR, "networkError：" + e.getMessage());
            networkError();
        } else if (e instanceof InterruptedException) {
            Log.e(TAG_ERROR, "timeout：" + e.getMessage());
            timeoutError();
        } else if (e instanceof HttpException) {
            Log.e(TAG_ERROR, "http错误：" + e.getMessage());
            httpError();
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            Log.e(TAG_ERROR, "解析错误：" + e.getMessage());
            parseError();
        }else {
            Log.e(TAG_ERROR, "未知错误：" + e.getMessage());
            unknown();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 未知错误
     */
    protected void unknown() {
        baseView.showToast(App.getContext().getString(R.string.error_unknown));

    }

    /**
     * 解析错误
     */
    protected void parseError() {
        baseView.showToast(App.getContext().getString(R.string.error_parse));
    }

    /**
     * http错误
     */
    protected void httpError() {
        baseView.showToast(App.getContext().getString(R.string.error_http));
    }

    /**
     * 网络超时异常
     */
    protected void timeoutError() {
        baseView.showToast(App.getContext().getString(R.string.error_timeout));
    }

    /**
     * 网络不可用异常
     */
    protected void networkError() {
        baseView.showToast(App.getContext().getString(R.string.error_network));
    }

}
