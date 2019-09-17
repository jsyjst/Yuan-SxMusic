package com.example.musicplayer.view.main.history;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.entiy.HistorySong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.event.SongHistoryEvent;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.FileUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
        View view = inflater.inflate(R.layout.fragment_love, container, false);
        EventBus.getDefault().register(this);
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
        showSongList();
        onClick();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        getActivity().unbindService(connection);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN )
    public void onMessageEvent(SongHistoryEvent event){
        mAdapter.notifyDataSetChanged();
    }

    private void showSongList() {
        mHistoryList = orderList(LitePal.findAll(HistorySong.class));
        mAdapter = new HistoryAdapter(getActivity(), mHistoryList);
        mManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
    }

    private void onClick() {
        mAdapter.setOnItemClickListener(position -> {
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
            FileUtil.saveSong(song);

            mPlayStatusBinder.play(Constant.LIST_TYPE_HISTORY);
        });
        mBackIv.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
    }
    private List<HistorySong> orderList(List<HistorySong> tempList){
        List<HistorySong> historyList=new ArrayList<>();
        historyList.clear();
        for(int i=tempList.size()-1;i>=0;i--){
            historyList.add(tempList.get(i));
        }
        return historyList;
    }
}
