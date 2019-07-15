package com.example.musicplayer.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.base.presenter.IPresenter;
import com.wang.avi.AVLoadingIndicatorView;

import static com.example.musicplayer.app.Constant.ERROR_STATE;
import static com.example.musicplayer.app.Constant.LOADING_STATE;
import static com.example.musicplayer.app.Constant.NORMAL_STATE;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/15
 *     desc   :
 * </pre>
 */

public abstract class BaseLoadingFragment<T extends IPresenter> extends BaseMvpFragment<T> {
    private View mNormalView;  //正常布局
    private View mErrorView; //错误布局
    private AVLoadingIndicatorView mLoadingView;  //加载布局
    private View mLoadingText; //加载文字

    private int mCurrentState;//当前布局状态

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getView() == null) return;
        mNormalView = view.findViewById(R.id.normalView);
        if(mNormalView == null){
            throw new IllegalStateException("The subclass of BaseLoadFragment must contain a View it's id is named normal_view");
        }
        if(!(mNormalView.getParent() instanceof ViewGroup)){
            throw new IllegalStateException("mNormalView's parentView should be a ViewGroup");
        }

        ViewGroup parentPanel = (ViewGroup) mNormalView.getParent();
        View.inflate(mActivity,R.layout.error_view,parentPanel); //加载错误布局
        View.inflate(mActivity,R.layout.loading_view,parentPanel);//加载loading布局

        mLoadingView = parentPanel.findViewById(R.id.loadingView);
        mLoadingText = parentPanel.findViewById(R.id.loadingText);
        mErrorView = parentPanel.findViewById(R.id.errorView);
        TextView reloadBtn = parentPanel.findViewById(R.id.reloadBtn);
        reloadBtn.setOnClickListener(view1 -> reload()); //重新加载
    }

    @Override
    public void showNormalView() {
        super.showNormalView();
        if(mCurrentState == NORMAL_STATE) return;
        hideViewByState(mCurrentState);
        mCurrentState = NORMAL_STATE;
        showViewByState(mCurrentState);
    }

    @Override
    public void showErrorView() {
        super.showErrorView();
        if(mCurrentState == ERROR_STATE) return;
        showViewByState(mCurrentState);
        mCurrentState = ERROR_STATE;
        showViewByState(mCurrentState);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        if(mCurrentState == LOADING_STATE) return;
        showViewByState(mCurrentState);
        mCurrentState = LOADING_STATE;
        showViewByState(mCurrentState);
    }

    private void hideViewByState(int state){
        if(state == NORMAL_STATE){
            if(mNormalView == null) return;
            mNormalView.setVisibility(View.GONE);
        }else if(state == LOADING_STATE){
            if(mLoadingView == null||mLoadingText == null) return;
             mLoadingView.hide();
             mLoadingText.setVisibility(View.GONE);
        }else {
            if(mErrorView == null ) return;
            mErrorView.setVisibility(View.GONE);
        }
    }
    private void showViewByState(int state){
        if(state == NORMAL_STATE){
            if(mNormalView == null) return;
            mNormalView.setVisibility(View.VISIBLE);
        }else if(state == LOADING_STATE){
            if(mLoadingView == null||mLoadingText == null) return;
            mLoadingView.show();
            mLoadingText.setVisibility(View.VISIBLE);
        }else {
            if(mErrorView == null ) return;
            mErrorView.setVisibility(View.VISIBLE);
        }
    }
}
