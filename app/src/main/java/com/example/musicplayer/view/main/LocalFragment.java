package com.example.musicplayer.view.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.base.fragment.BaseMvpFragment;
import com.example.musicplayer.contract.ILocalContract;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.event.SongLocalEvent;
import com.example.musicplayer.presenter.LocalPresenter;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.FileUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;


public class LocalFragment extends BaseMvpFragment<LocalPresenter> implements ILocalContract.View {
    private static final String TAG = "LocalFragment";

    @BindView(R.id.normalView)
    RecyclerView mRecycler;
    @BindView(R.id.iv_find_local_song)
    ImageView mFindLocalMusicIv;
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.linear_empty)
    RelativeLayout mEmptyViewLinear;


    private List<LocalSong> mLocalSongsList;
    private LocalPresenter mPresenter;
    private SongAdapter songAdapter;
    private LinearLayoutManager layoutManager;
    //在onServiceConnected中获取PlayStatusBinder的实例，从而调用服务里面的方法
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
    public void onDestroy() {
        super.onDestroy();
        mActivity.unbindService(connection);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected LocalPresenter getPresenter() {
        mPresenter = new LocalPresenter();
        return mPresenter;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        registerAndBindReceive();
        initLocalRecycler();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SongLocalEvent event){
        songAdapter.notifyDataSetChanged();
        if (FileUtil.getSong() != null) {
            layoutManager.scrollToPositionWithOffset(FileUtil.getSong().getCurrent() + 4, mRecycler.getHeight());
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onClick();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_music;
    }


    @Override
    public void showMusicList(final List<LocalSong> mp3InfoList) {
        mLocalSongsList.clear();
        mLocalSongsList.addAll(mp3InfoList);
        mRecycler.setVisibility(View.VISIBLE);
        mEmptyViewLinear.setVisibility(View.GONE);
        mRecycler.setLayoutManager(layoutManager);
        //令recyclerView定位到当前播放的位置
        songAdapter = new SongAdapter(mActivity, mLocalSongsList);
        mRecycler.setAdapter(songAdapter);
        songAdapter.setOnItemClickListener(position -> {
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
            FileUtil.saveSong(song);
            mPlayStatusBinder.play(Constant.LIST_TYPE_LOCAL);
        });


    }

    @Override
    public void showErrorView() {
        showToast("本地音乐为空");
        mRecycler.setVisibility(View.GONE);
        mEmptyViewLinear.setVisibility(View.VISIBLE);
    }
    /**
     * 注册服务
     */
    private void registerAndBindReceive() {
        //启动服务
        Intent playIntent = new Intent(getActivity(), PlayerService.class);
        mActivity.bindService(playIntent, connection, Context.BIND_AUTO_CREATE);
    }

    private void initLocalRecycler() {
        mLocalSongsList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
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
            songAdapter = new SongAdapter(mActivity, mLocalSongsList);
            mRecycler.setAdapter(songAdapter);
            if (FileUtil.getSong() != null) {
                layoutManager.scrollToPositionWithOffset(FileUtil.getSong().getCurrent() - 4, mRecycler.getHeight());
            }
            songAdapter.setOnItemClickListener(position -> {
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
                FileUtil.saveSong(song);
                mPlayStatusBinder.play(Constant.LIST_TYPE_LOCAL);
            });

        }
    }


    //按钮事件
    private void onClick() {
        mFindLocalMusicIv.setOnClickListener(v -> mPresenter.getLocalMp3Info()); //得到本地列表
        mBackIv.setOnClickListener(v -> Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack()); //返回
    }

}
