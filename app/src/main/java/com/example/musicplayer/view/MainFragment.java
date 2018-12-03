package com.example.musicplayer.view;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.musicplayer.adapter.HistoryAdapter;
import com.example.musicplayer.constant.BroadcastName;
import com.example.musicplayer.entiy.HistorySong;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.entiy.Love;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.widget.MyListView;

import org.litepal.LitePal;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private LinearLayout mFunctionLinear;

    private MyListView myListView;
    private ExpandableListAdapter mAdapter;
    private LinearLayout mLocalMusicLinear,mCollectionLinear,mHistoryMusicLinear;
    private Button playerBtn;
    private TextView mLocalMusicNum,mLoveMusicNum,mHistoryMusicNum;

    private TextView mSeekBtn;
    private String[] mGroupStrings = {"自建歌单", "收藏歌单"};
    private String[][] mSongStrings = {
            {"我喜欢", "默认收藏", "哎呀不错哦", "残渊"},
            {"啦啦", "哈哈"}
    };
    //注册广播
    private IntentFilter intentFilter;
    private SongChangeReceiver songChangeReceiver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mLocalMusicLinear = view.findViewById(R.id.linear_local_music);
        mCollectionLinear = view.findViewById(R.id.linear_collection);
        playerBtn = view.findViewById(R.id.btn_player);
        mFunctionLinear = view.findViewById(R.id.linear_function);
        mHistoryMusicLinear = view.findViewById(R.id.linear_history);
        //获取焦点
        mFunctionLinear.setFocusableInTouchMode(true);
        myListView = view.findViewById(R.id.expand_lv_song_list);
        mSeekBtn = view.findViewById(R.id.tv_seek);
        mLocalMusicNum = view.findViewById(R.id.tv_local_music_num);
        mLoveMusicNum = view.findViewById(R.id.tv_love_num);
        mHistoryMusicNum = view.findViewById(R.id.tv_history_num);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ExpandableListViewAdapter(getActivity(), mGroupStrings, mSongStrings);
        myListView.setAdapter(mAdapter);
        //注册广播
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastName.SONG_CHANGE);
        songChangeReceiver = new SongChangeReceiver();
        getActivity().registerReceiver(songChangeReceiver, intentFilter);
        onClick();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        getActivity().unregisterReceiver(songChangeReceiver);
    }
    @Override
    public void onResume(){
        super.onResume();
        showMusicNum();
        Log.d(TAG, "onResume: true");
    }

    private void onClick() {
        mLocalMusicLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new LocalMusicFragment());
            }
        });

        mSeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SearchFragment());
            }
        });

        mCollectionLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CollectionFragment());
            }
        });

        mHistoryMusicLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HistoryFragment());
            }
        });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //进入和退出动画
        transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.slide_in_right, R.anim.slide_out_right);
        transaction.add(R.id.fragment_container, fragment);
        transaction.hide(this);
        //将事务提交到返回栈
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void showMusicNum(){
        mLoveMusicNum.setText(""+LitePal.findAll(LocalSong.class).size());
        mLoveMusicNum.setText(""+LitePal.findAll(Love.class).size());
        mHistoryMusicNum.setText(""+LitePal.findAll(HistorySong.class).size());

    }
    private class SongChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mHistoryMusicNum.setText(""+LitePal.findAll(HistorySong.class).size());
        }
    }

}
