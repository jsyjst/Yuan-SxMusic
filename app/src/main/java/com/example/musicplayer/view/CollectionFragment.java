package com.example.musicplayer.view;


import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.LoveSongAdapter;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.constant.BroadcastName;
import com.example.musicplayer.constant.Constant;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.entiy.Love;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.FileHelper;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/11/30.
 */

public class CollectionFragment extends Fragment {
    private static final String TAG="CollectionFragment";

    private RecyclerView mRecycler;
    private ImageView mBackIv;
    private LinearLayoutManager mManager;
    private LoveSongAdapter mAdapter;
    private LinearLayout mSongListLinear;
    private RelativeLayout mEmptyRelative;
    private List<Love> mLoveList;
    //注册广播
    private IntentFilter intentFilter;
    private SongChangeReceiver songChangeReceiver;

    private PlayerService.PlayStatusBinder mPlayStatusBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayStatusBinder = (PlayerService.PlayStatusBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_love_music, container, false);
        mRecycler = mView.findViewById(R.id.recycler_love_songs);
        mBackIv = mView.findViewById(R.id.iv_back);
        mEmptyRelative = mView.findViewById(R.id.relative_empty);
        mSongListLinear = mView.findViewById(R.id.linear_song_list);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //启动服务
        Intent playIntent = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(playIntent, connection, Context.BIND_AUTO_CREATE);
        //注册广播
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastName.LOVE_SONG_CHANGE);
        intentFilter.addAction(BroadcastName.LOVE_SONG_CANCEL);
        songChangeReceiver = new SongChangeReceiver();
        getActivity().registerReceiver(songChangeReceiver, intentFilter);
        showSongList();
        onClick();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        getActivity().unbindService(connection);
        getActivity().unregisterReceiver(songChangeReceiver);
    }

    private void showSongList() {
        mLoveList = orderList(LitePal.findAll(Love.class));
        mAdapter = new LoveSongAdapter(getActivity(), mLoveList);
        mManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
    }

    private void onClick() {
        mAdapter.setOnItemClickListener(new LoveSongAdapter.OnItemClickListener() {
            @Override
            public void onSongClick(int position) {
                Love love = mLoveList.get(position);
                Song song = new Song();
                song.setOnlineId(love.getSongId());
                song.setSongName(love.getName());
                song.setSinger(love.getSinger());
                song.setOnline(love.isOnline());
                song.setUrl(love.getUrl());
                song.setImgUrl(love.getPic());
                song.setCurrent(position);
                song.setListType(Constant.LIST_TYPE_LOVE);
                FileHelper.saveSong(song);

                mPlayStatusBinder.play(Constant.LIST_TYPE_LOVE);
            }
        });
    }
    private List<Love> orderList(List<Love> tempList){
        List<Love> loveList=new ArrayList<>();
        loveList.clear();
        for(int i=tempList.size()-1;i>=0;i--){
            loveList.add(tempList.get(i));
        }
        return loveList;
    }

    private class SongChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadcastName.LOVE_SONG_CHANGE)) {
                mAdapter.notifyDataSetChanged();
                if (FileHelper.getSong() != null) {
                    mManager.scrollToPositionWithOffset(FileHelper.getSong().getCurrent() + 4, mRecycler.getHeight());
                }
            }else if(action.equals(BroadcastName.LOVE_SONG_CANCEL)){
                showSongList();
            }
        }
    }
}
