package com.example.musicplayer.view.main;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.app.BroadcastName;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.base.fragment.BaseLoadingFragment;
import com.example.musicplayer.base.fragment.BaseMvpFragment;
import com.example.musicplayer.callback.OnItemClickListener;
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
    public void onDestroy() {
        super.onDestroy();
        mActivity.unbindService(connection);
        mActivity.unregisterReceiver(songChangeReceiver);
    }

    @Override
    protected LocalPresenter getPresenter() {
        mPresenter = new LocalPresenter();
        return mPresenter;
    }

    @Override
    public void initView() {
        super.initView();
        registerAndBindReceive();
        initLocalRecycler();

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
        showToast("成功导入本地音乐");
        mLocalSongsList.clear();
        mLocalSongsList.addAll(mp3InfoList);
        mRecycler.setVisibility(View.VISIBLE);
        mEmptyViewLinear.setVisibility(View.GONE);
        mRecycler.setLayoutManager(layoutManager);
        //令recyclerView定位到当前播放的位置
        songAdapter = new SongAdapter(mActivity, mLocalSongsList);
        mRecycler.setAdapter(songAdapter);
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

    @Override
    public void showErrorView() {
        showToast("本地音乐为空");
        mRecycler.setVisibility(View.GONE);
        mEmptyViewLinear.setVisibility(View.VISIBLE);
    }

    @Override
    public void saveLocalSuccess() {
        mActivity.sendBroadcast(new Intent(BroadcastName.LOCAL_SONG_NUM_CHANGE));
    }

    /**
     * 注册服务
     */
    private void registerAndBindReceive() {
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastName.LOCAL_SONG_CHANGE_LIST);
        songChangeReceiver = new SongChangeLocalMusicReceiver();
        mActivity.registerReceiver(songChangeReceiver, intentFilter);
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
    private void onClick() {
        mFindLocalMusicIv.setOnClickListener(v -> mPresenter.getLocalMp3Info()); //得到本地列表
        mBackIv.setOnClickListener(v -> Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack()); //返回
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
