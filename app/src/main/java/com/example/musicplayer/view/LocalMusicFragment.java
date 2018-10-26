package com.example.musicplayer.view;

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

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.contract.ILocalMusicContract;
import com.example.musicplayer.entiy.Mp3Info;
import com.example.musicplayer.presenter.LocalMusicPresenter;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.FileHelper;

import java.util.ArrayList;
import java.util.List;


public class LocalMusicFragment extends Fragment implements ILocalMusicContract.View {
    private static final String TAG = "LocalFragment";
    private RecyclerView mRecycler;
    private List<Mp3Info> mMp3InfoList;
    private LocalMusicPresenter mPresenter;
    private View mView;
    private SongAdapter songAdapter;
    //在onServiceConnected中获取PlayStatusBinder的实例，从而调用服务里面的方法
    private PlayerService.PlayStatusBinder mPlayStatusBinder;

    //注册广播
    private IntentFilter intentFilter;
    private SongChangeLocalMusicReceiver songChangeReceiver;




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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_local_music, container, false);
        mRecycler = mView.findViewById(R.id.recycler_song_list);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        intentFilter=new IntentFilter();
        intentFilter.addAction("android.song.change.local.song.list");
        songChangeReceiver=new SongChangeLocalMusicReceiver();
        getActivity().registerReceiver(songChangeReceiver,intentFilter);
        initView();
        setOnClickListener();


    }

    private void initView() {
        mMp3InfoList = new ArrayList<>();
        songAdapter = new SongAdapter(mView.getContext(), mMp3InfoList);
        mPresenter = new LocalMusicPresenter();
        mPresenter.attachView(this); //与Presenter建立关系
        mPresenter.getLocalMp3Info(); //执行presenter里面的方法

        //启动服务
        Intent playIntent = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(playIntent, connection, Context.BIND_AUTO_CREATE);



    }

    //按钮事件
    private void setOnClickListener() {

        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onSongClick() {
                mPlayStatusBinder.play(0);
            }
        });
    }


    @Override
    public void showMusicList(final List<Mp3Info> mp3InfoList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMp3InfoList.addAll(mp3InfoList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext());
                mRecycler.setLayoutManager(layoutManager);
                //令recyclerView定位到当前播放的位置
                layoutManager.scrollToPositionWithOffset(FileHelper.getSong().getCurrent()+1,mRecycler.getHeight());
                songAdapter.notifyDataSetChanged();
                mRecycler.setAdapter(songAdapter);
            }
        });

    }

    class SongChangeLocalMusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            songAdapter.notifyDataSetChanged();
        }
    }

}
