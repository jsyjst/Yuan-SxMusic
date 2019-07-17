package com.example.musicplayer.view.main;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.HistoryAdapter;

import com.example.musicplayer.callback.OnItemClickListener;
import com.example.musicplayer.app.BroadcastName;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.entiy.HistorySong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.FileHelper;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/12/2.
 */

public class HistoryFragment extends Fragment {
    private static final String TAG="HistoryFragment";

    private RecyclerView mRecycler;
    private ImageView mBackIv;
    private LinearLayoutManager mManager;
    private HistoryAdapter mAdapter;
    private LinearLayout mSongListLinear;
    private RelativeLayout mEmptyRelative;
    private List<HistorySong> mHistoryList;
    private TextView mTitleTv;
    //注册广播
    private IntentFilter intentFilter;
    private SongChangeReceiver songChangeReceiver;

    private PlayerService.PlayStatusBinder mPlayStatusBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayStatusBinder = (PlayerService.PlayStatusBinder) service;
            mPlayStatusBinder.getHistoryList();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_love_music, container, false);
        mRecycler = view.findViewById(R.id.recycler_love_songs);
        mBackIv = view.findViewById(R.id.iv_back);
        mEmptyRelative = view.findViewById(R.id.relative_empty);
        mSongListLinear = view.findViewById(R.id.linear_song_list);
        mTitleTv = view.findViewById(R.id.tv_title);
        mTitleTv.setText("最近播放");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //启动服务
        Intent playIntent = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(playIntent, connection, Context.BIND_AUTO_CREATE);
        //注册广播
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastName.SONG_CHANGE);
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
        mHistoryList = orderList(LitePal.findAll(HistorySong.class));
        mAdapter = new HistoryAdapter(getActivity(), mHistoryList);
        mManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
    }

    private void onClick() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                HistorySong history = mHistoryList.get(position);
                Song song = new Song();
                song.setSongId(history.getSongId());
                song.setSongName(history.getName());
                song.setSinger(history.getSinger());
                song.setOnline(history.isOnline());
                song.setUrl(history.getUrl());
                song.setImgUrl(history.getPic());
                song.setCurrent(position);
                song.setDuration(history.getDuration());
                song.setListType(Constant.LIST_TYPE_HISTORY);
                FileHelper.saveSong(song);

                mPlayStatusBinder.play(Constant.LIST_TYPE_HISTORY);
            }
        });
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
    private List<HistorySong> orderList(List<HistorySong> tempList){
        List<HistorySong> historyList=new ArrayList<>();
        historyList.clear();
        for(int i=tempList.size()-1;i>=0;i--){
            historyList.add(tempList.get(i));
        }
        return historyList;
    }

    private class SongChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
