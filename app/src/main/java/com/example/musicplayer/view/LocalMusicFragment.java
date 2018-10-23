package com.example.musicplayer.view;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.contract.ILocalMusicContract;
import com.example.musicplayer.entiy.Mp3Info;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.presenter.LocalMusicPresenter;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.FileHelper;

import java.util.ArrayList;
import java.util.List;


public class LocalMusicFragment extends Fragment implements ILocalMusicContract.View {
    private static final String TAG = "LocalFragment";

    private MediaPlayer mMediaPlayer;
    private SeekBar mSeekBar;
    private boolean isChange; //拖动进度条
    private boolean isSeek;//标记是否在暂停的时候拖动进度条
    private boolean flag; //用做暂停的标记
    private int time;   //记录暂停的时间
    private Thread mSeekBarThread;
    private Button mPlayerBtn;
    private LinearLayout mPlayerLinear;
    private TextView mNextTv;
    private RecyclerView mRecycler;
    private List<Mp3Info> mMp3InfoList;
    private LocalMusicPresenter mPresenter;
    private View mView;
    private TextView mSongNameTv;
    private TextView mSinger;
    private Song mSong;
    private int mCurrent;//记录播放歌曲的位置
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
        mSongNameTv = getActivity().findViewById(R.id.tv_song_name);
        mSinger = getActivity().findViewById(R.id.tv_singer);
        mSeekBar = getActivity().findViewById(R.id.sb_progress);
        mPlayerBtn = getActivity().findViewById(R.id.btn_player);
        mNextTv = getActivity().findViewById(R.id.tv_new_song);
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

        //进度条的监听事件
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //防止在拖动进度条进行进度设置时与Thread更新播放进度条冲突
                isChange = true;
                isSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mPlayStatusBinder.isPlaying()) {
                    mMediaPlayer.seekTo(seekBar.getProgress());
                } else {
                    time = seekBar.getProgress();
                }
                isChange = false;
                mSeekBarThread = new Thread(new SeekBarThread());
                mSeekBarThread.start();
            }
        });

        //
        mPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayStatusBinder.isPlaying()) {
                    time = mMediaPlayer.getCurrentPosition();
                    mPlayStatusBinder.pause();
                    flag = true;
                    mPlayerBtn.setSelected(false);
                } else if (flag) {
                    mPlayStatusBinder.resume();
                    flag = false;
                    if (isSeek) {
                        mMediaPlayer.seekTo(time);
                    } else {
                        isSeek = false;
                    }

                    mPlayerBtn.setSelected(true);
                    mSeekBarThread = new Thread(new SeekBarThread());
                    mSeekBarThread.start();
                } else {
                    mPlayStatusBinder.play(0);
                    mMediaPlayer=mPlayStatusBinder.getMediaPlayer();
                    mMediaPlayer.seekTo((int)FileHelper.getSong().getCurrentTime());
                    mPlayerBtn.setSelected(true);
                    mSeekBarThread = new Thread(new SeekBarThread());
                    mSeekBarThread.start();
                }
            }
        });
        //
        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onSongClick() {
                mSong = FileHelper.getSong();
                mSongNameTv.setText(mSong.getTitle());
                mSinger.setText(mSong.getArtist());
                mPlayStatusBinder.play(0);
                mMediaPlayer = mPlayStatusBinder.getMediaPlayer();

                mPlayerBtn.setSelected(true);
                mSeekBar.setMax((int) mSong.getDuration());
                mSeekBarThread = new Thread(new SeekBarThread());
                mSeekBarThread.start();
            }
        });
    }


    @Override
    public void showMusicList(final ArrayList<Mp3Info> mp3InfoList) {
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

    @Override
    public void onResume() {
        super.onResume();

    }

    class SeekBarThread implements Runnable {
        @Override
        public void run() {
            while (!isChange && mPlayStatusBinder.isPlaying()) {
                mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class SongChangeLocalMusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: songhange");
            songAdapter.notifyDataSetChanged();
        }
    }

}
