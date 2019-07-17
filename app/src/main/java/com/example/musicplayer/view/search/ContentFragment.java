package com.example.musicplayer.view.search;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SearchAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/11/25.
 */

public class ContentFragment extends Fragment {

    private List<String> mTitleList;
    private List<Fragment> mFragments;
    private ViewPager mPager;
    private SearchAdapter mAdapter;
    private TabLayout mTabLayout;
    private String[] mTitles = {"歌曲", "专辑"};
    private String[] mTypes = {"song", "album"};
    private Bundle mBundle;
    private String mSeek;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        mBundle = getArguments();
        if (mBundle != null) {
            mSeek = mBundle.getString(SearchContentFragment.SEEK_KEY);
        }

        mPager = view.findViewById(R.id.page);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mTitleList = new ArrayList<>();
        mFragments = new ArrayList<>();

        initTab();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTitleList.add(mTitles[i]);
            mFragments.add(SearchContentFragment.newInstance(mSeek, mTypes[i]));
        }
        mAdapter = new SearchAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mPager);



    }

    public static Fragment newInstance(String seek) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SearchContentFragment.SEEK_KEY, seek);
        fragment.setArguments(bundle);
        return fragment;
    }


    //修改tab指示线大小
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("slidingTabIndicator");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        assert tabStrip != null;
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


}
