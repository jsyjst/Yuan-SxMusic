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
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.callback.OnItemClickListener;
import com.example.musicplayer.configure.BroadcastName;
import com.example.musicplayer.configure.Constant;
import com.example.musicplayer.contract.ILocalContract;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.presenter.LocalPresenter;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.FileHelper;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class LocalFragment extends Fragment implements ILocalContract.View {
    private static final String TAG = "LocalFragment";
    private RecyclerView mRecycler;
    private List<LocalSong> mLocalSongsList;
    private LocalPresenter mPresenter;
    private View mView;
    private SongAdapter songAdapter;
    private LinearLayoutManager layoutManager;
    //在onServiceConnected中获取PlayStatusBinder的实例，从而调用服务里面的方法
    private PlayerService.PlayStatusBinder mPlayStatusBinder;

    //注册广播
    private IntentFilter intentFilter;
    private SongChangeLocalMusicReceiver songChangeReceiver;

    private ImageView mFindLocalMusicIv;
    private ImageView mBackIv;
    private FrameLayout mEmptyViewLinear;

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
        mView = inflater.inflate(R.layout.fragment_local_music, container, false);
        mRecycler = mView.findViewById(R.id.recycler_song_list);
        mFindLocalMusicIv = mView.findViewById(R.id.iv_find_local_song);
        mBackIv = mView.findViewById(R.id.iv_back);
        mEmptyViewLinear = mView.findViewById(R.id.linear_empty);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastName.LOCAL_SONG_CHANGE_LIST);
        songChangeReceiver = new SongChangeLocalMusicReceiver();
        getActivity().registerReceiver(songChangeReceiver, intentFilter);
        initView();
        setOnClickListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(connection);
        getActivity().unregisterReceiver(songChangeReceiver);
    }

    private void initView() {
        mLocalSongsList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        mPresenter = new LocalPresenter();
        mPresenter.attachView(this); //与Presenter建立关系
        //启动服务
        Intent playIntent = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(playIntent, connection, Context.BIND_AUTO_CREATE);

        mLocalSongsList.clear();
        mLocalSongsList.addAll(LitePal.findAll(LocalSong.class));
        if (mLocalSongsList.size() == 0) {
            mRecycler.setVisibility(View.GONE);
            mEmptyViewLinear.setVisibility(View.VISIBLE);
        } else {
            mEmptyViewLinear.setVisibility(View.GONE);
            mRecycler.setVisibility(View.VISIBLE);
            mRecycler.setLayoutManager(layoutManager);
            //令recyclerView定位到当前播放的位置
            songAdapter = new SongAdapter(mView.getContext(), mLocalSongsList);
            mRecycler.setAdapter(songAdapter);
            if (FileHelper.getSong() != null) {
                layoutManager.scrollToPositionWithOffset(FileHelper.getSong().getCurrent() - 4, mRecycler.getHeight());
            }
            songAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    //将点击的序列化到本地
                    LocalSong mp3Info = mLocalSongsList.get(position);
                    Song song = new Song();
                    song.setSongName(mp3Info.getName());
                    song.setSinger(mp3Info.getSinger());
                    song.setUrl(mp3Info.getUrl());
                    song.setDuration(mp3Info.getDuration());
                    song.setCurrent(position);
                    song.setOnline(false);
                    song.setSongId(mp3Info.getSongId());
                    song.setListType(Constant.LIST_TYPE_LOCAL);
                    FileHelper.saveSong(song);
                    mPlayStatusBinder.play(Constant.LIST_TYPE_LOCAL);
                }
            });

        }

    }

    //按钮事件
    private void setOnClickListener() {
        mFindLocalMusicIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getLocalMp3Info(); //执行presenter里面的方法
            }
        });
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }


    @Override
    public void showMusicList(final List<LocalSong> mp3InfoList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLocalSongsList.clear();
                mLocalSongsList.addAll(mp3InfoList);
                if (mLocalSongsList.size() == 0) {
                    CommonUtil.showToast(getActivity(),"本地音乐为空");
                    mRecycler.setVisibility(View.GONE);
                    mEmptyViewLinear.setVisibility(View.VISIBLE);
                } else {
                    mRecycler.setVisibility(View.VISIBLE);
                    mEmptyViewLinear.setVisibility(View.GONE);
                    mRecycler.setLayoutManager(layoutManager);
                    //令recyclerView定位到当前播放的位置
                    songAdapter = new SongAdapter(mView.getContext(), mLocalSongsList);
                    mRecycler.setAdapter(songAdapter);
                    CommonUtil.showToast(getActivity(), "成功导入本地音乐");
                    mPresenter.saveSong(mp3InfoList);//保存到数据库中
                    songAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            //将点击的序列化到本地
                            LocalSong mp3Info = mLocalSongsList.get(position);
                            Song song = new Song();
                            song.setSongName(mp3Info.getName());
                            song.setSinger(mp3Info.getSinger());
                            song.setUrl(mp3Info.getUrl());
                            song.setDuration(mp3Info.getDuration());
                            song.setCurrent(position);
                            song.setOnline(false);
                            song.setSongId(mp3Info.getSongId());
                            song.setListType(Constant.LIST_TYPE_LOCAL);
                            FileHelper.saveSong(song);
                            mPlayStatusBinder.play(Constant.LIST_TYPE_LOCAL);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void saveLocalSuccess() {
        getActivity().sendBroadcast(new Intent(BroadcastName.LOCAL_SONG_NUM_CHANGE));
    }

    class SongChangeLocalMusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            songAdapter.notifyDataSetChanged();
            if (FileHelper.getSong() != null) {
                layoutManager.scrollToPositionWithOffset(FileHelper.getSong().getCurrent() + 4, mRecycler.getHeight());
            }
        }
    }

}
