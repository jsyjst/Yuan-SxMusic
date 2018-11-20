package com.example.musicplayer.view;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.ExpandableListViewAdapter;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.widget.MyListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private LinearLayout mFunctionLinear;

    private MyListView myListView;
    private LocalMusicFragment mLocalMusicFragment;
    private SearchFragment mSearchFragment;
    private ExpandableListAdapter mAdapter;
    private LinearLayout mLocalMusicLinear;
    private Button playerBtn;

    private TextView mSeekBtn;
    private String[] mGroupStrings = {"自建歌单", "收藏歌单"};
    private String[][] mSongStrings = {
            {"我喜欢", "默认收藏", "哎呀不错哦", "残渊"},
            {"啦啦", "哈哈"}
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mLocalMusicLinear = view.findViewById(R.id.linear_local_music);
        playerBtn = view.findViewById(R.id.btn_player);
        mFunctionLinear = view.findViewById(R.id.linear_function);
        //获取焦点
        mFunctionLinear.setFocusableInTouchMode(true);
        myListView = view.findViewById(R.id.expand_lv_song_list);
        mSeekBtn = view.findViewById(R.id.tv_seek);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonUtil.hideStatusBar(getActivity(),true);
        mAdapter = new ExpandableListViewAdapter(getActivity(), mGroupStrings, mSongStrings);
        myListView.setAdapter(mAdapter);
        onClick();


    }
    @Override
    public void onResume(){
        super.onResume();
        CommonUtil.hideStatusBar(getActivity(),true);
        Log.d(TAG, "onResume: true");
    }

    private void onClick() {
        mLocalMusicLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(1);
            }
        });

        mSeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(2);
            }
        });
    }


    private void replaceFragment(int type) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();


        //进入和退出动画
        transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.slide_in_right, R.anim.slide_out_right);
        if (type == 1) {
            mLocalMusicFragment = new LocalMusicFragment();
            transaction.add(R.id.fragment_container, mLocalMusicFragment);
        } else if (type ==2) {
            mSearchFragment = new SearchFragment();
            transaction.add(R.id.fragment_container,mSearchFragment);
        }
        transaction.hide(this);
        //将事务提交到返回栈
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
