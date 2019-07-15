package com.example.musicplayer.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.base.view.BaseView;
import com.example.musicplayer.util.CommonUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/14
 *     desc   :
 * </pre>
 */

public abstract class BaseFragment extends BaseLazyFragment implements BaseView {
    private Unbinder mBinder;
    protected Activity mActivity;
    protected abstract void initView(); //初始化控件
    protected abstract void loadData(); //加载数据
    protected abstract int getLayoutId(); //获取Fragment的布局id

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);
        mBinder = ButterKnife.bind(this,view);
        initView();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mBinder != null && mBinder != Unbinder.EMPTY){
            mBinder.unbind();
            mBinder = null;
        }
    }


    @Override
    protected void lazyLoadData() {
        loadData();
    }

    @Override
    public void showNormalView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showToast(String message) {
        CommonUtil.showToast(mActivity,message);
    }


}
