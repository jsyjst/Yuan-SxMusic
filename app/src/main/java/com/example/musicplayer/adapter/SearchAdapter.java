package com.example.musicplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 残渊 on 2018/11/25.
 */

public class SearchAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;//顶部导航栏的内容即fragment
    private List<String> mTitle;//顶部导航栏的标题


    public SearchAdapter(FragmentManager fragmentManager, List<Fragment>fragments, List<String>title){
        super(fragmentManager);
        mFragmentList=fragments;
        mTitle=title;

    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
