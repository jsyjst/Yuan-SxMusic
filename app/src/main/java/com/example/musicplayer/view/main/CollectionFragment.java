package com.example.musicplayer.view.main;


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

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.LoveSongAdapter;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.callback.OnItemClickListener;
import com.example.musicplayer.entiy.Love;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.event.SongCollectionEvent;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.FileUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    private List<Love> mLoveList;

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
        EventBus.getDefault().register(this); //注册事件订阅者
        mRecycler = mView.findViewById(R.id.recycler_love_songs);
        mBackIv = mView.findViewById(R.id.iv_back);
        mLoveList = new ArrayList<>();
        return mView;
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
        EventBus.getDefault().unregister(this);
        getActivity().unbindService(connection);
    }

    @Subscribe(threadMode = ThreadMode.MAIN )
    public void onMessageEvent(SongCollectionEvent songCollectionEvent){
        mLoveList.clear();
        mLoveList.addAll(orderList(LitePal.findAll(Love.class)));
        mAdapter.notifyDataSetChanged();
        if(songCollectionEvent.isLove()){//定位歌曲
            if (FileUtil.getSong() != null) {
                mManager.scrollToPositionWithOffset(FileUtil.getSong().getCurrent() + 4, mRecycler.getHeight());
            }
        }
    }

    private void showSongList() {
        mRecycler.setHasFixedSize(true);
        mLoveList.addAll(orderList(LitePal.findAll(Love.class)));
        mAdapter = new LoveSongAdapter(getActivity(), mLoveList);
        mManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Love love = mLoveList.get(position);
                Song song = new Song();
                song.setSongId(love.getSongId());
                song.setQqId(love.getQqId());
                song.setSongName(love.getName());
                song.setSinger(love.getSinger());
                song.setOnline(love.isOnline());
                song.setUrl(love.getUrl());
                song.setImgUrl(love.getPic());
                song.setCurrent(position);
                song.setDuration(love.getDuration());
                song.setListType(Constant.LIST_TYPE_LOVE);
                FileUtil.saveSong(song);

                mPlayStatusBinder.play(Constant.LIST_TYPE_LOVE);
            }
        });
    }

    private void onClick() {
        mBackIv.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
    }
    private List<Love> orderList(List<Love> tempList){
        List<Love> loveList=new ArrayList<>();
        loveList.clear();
        for(int i=tempList.size()-1;i>=0;i--){
            loveList.add(tempList.get(i));
        }
        return loveList;
    }
}
