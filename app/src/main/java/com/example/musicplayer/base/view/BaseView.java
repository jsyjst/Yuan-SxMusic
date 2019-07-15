package com.example.musicplayer.base.view;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/07/14
 *     desc   : 页面基类
 * </pre>
 */

public interface BaseView {
    void showNormalView(); //正常布局
    void showErrorView(); //错误布局
    void showLoading();//加载布局
    void reload();//重新加载
    void showToast(String message);//显示Toast
}
