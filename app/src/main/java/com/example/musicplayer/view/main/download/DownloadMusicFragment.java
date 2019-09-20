package com.example.musicplayer.view.main.download;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.DownloadSongAdapter;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.entiy.DownloadSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.event.DownloadEvent;
import com.example.musicplayer.event.SongDownloadedEvent;
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
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/16
 *     desc   : 下载歌曲列表
 * </pre>
 */

public class DownloadMusicFragment extends Fragment {
    private static final String TAG = "DownloadMusicFragment";

    @BindView(R.id.songRecycle)
    RecyclerView songRecycle;
    Unbinder unbinder;

    private LinearLayoutManager mManager;
    private DownloadSongAdapter mAdapter;
    private List<DownloadSong> mDownloadSongList;  //已下载歌曲列表

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_music, container, false);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);
        mDownloadSongList = new ArrayList<>();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //启动服务
        Intent playIntent = new Intent(getActivity(), PlayerService.class);
        Objects.requireNonNull(getActivity()).bindService(playIntent, connection, Context.BIND_AUTO_CREATE);
        showSongList();
    }

    private void showSongList(){
        mDownloadSongList.addAll(orderList(LitePal.findAll(DownloadSong.class)));
        mAdapter = new DownloadSongAdapter(getActivity(), mDownloadSongList);
        mManager = new LinearLayoutManager(getActivity());
        songRecycle.setLayoutManager(mManager);
        songRecycle.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(position -> {
            DownloadSong downloadSong = mDownloadSongList.get(position);
            Song song = new Song();
            song.setSongId(downloadSong.getSongId());
            song.setSongName(downloadSong.getName());
            song.setSinger(downloadSong.getSinger());
            song.setOnline(false);
            song.setUrl(downloadSong.getUrl());
            song.setImgUrl(downloadSong.getPic());
            song.setCurrent(position);
            song.setDuration(downloadSong.getDuration());
            song.setListType(Constant.LIST_TYPE_DOWNLOAD);
            song.setMediaId(downloadSong.getMediaId());
            song.setDownload(true);
            FileUtil.saveSong(song);

            mPlayStatusBinder.play(Constant.LIST_TYPE_DOWNLOAD);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadSuccessEvent(DownloadEvent event){
        if(event.getDownloadStatus() == Constant.TYPE_DOWNLOAD_SUCCESS){
            mDownloadSongList.clear();
            mDownloadSongList.addAll(orderList(LitePal.findAll(DownloadSong.class)));
            mAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSongDownloadedEvent(SongDownloadedEvent event){
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
    private List<DownloadSong> orderList(List<DownloadSong> tempList){
        List<DownloadSong> loveList=new ArrayList<>();
        loveList.clear();
        for(int i=tempList.size()-1;i>=0;i--){
            loveList.add(tempList.get(i));
        }
        return loveList;
    }
}
