package com.example.musicplayer.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/14
 *     desc   : 实现懒加载的Fragment
 * </pre>
 */

public abstract class BaseLazyFragment extends Fragment {
    private boolean isViewCreated = false;//布局是否被创建
    private boolean isLoadData = false;//数据是否加载
    private boolean isFirstVisible = true;//是否第一次可见
    private static final String TAG = "BaseLazyFragment";

    protected abstract void lazyLoadData(); //加载数据

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        isViewCreated = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isFragmentVisible(this)){
            if (this.getParentFragment() == null || isFragmentVisible(this.getParentFragment())) {
                Log.d(TAG, "onActivityCreated: 加载数据");
                lazyLoadData();
                isLoadData = true;
                if(isFirstVisible) isFirstVisible = false;
            }
        }
    }

    //在使用ViewPage时，加载数据
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFragmentVisible(this)&& !isLoadData && isViewCreated){
            lazyLoadData();
            isLoadData = true;
        }
    }


    //调用show方法时加载数据
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //onHiddenChanged调用在Resumed之前，所以此时可能fragment被add, 但还没调用show方法
        if(!hidden && !this.isResumed())
            return;
        //使用hide和show时，fragment的所有生命周期方法都不会调用，除了onHiddenChanged（）
        if(!hidden && isFirstVisible){
            Log.d(TAG, "onHiddenChanged: 加载数据");
            lazyLoadData();
            isFirstVisible = false;
        }
    }

    private boolean isFragmentVisible(Fragment fragment){
        return fragment.getUserVisibleHint()&&!fragment.isHidden();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstVisible = true;
        isLoadData = false;
        isViewCreated =false;
    }
}
