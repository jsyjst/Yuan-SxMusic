package com.example.musicplayer.view;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.R;
import com.example.musicplayer.constant.BroadcastName;
import com.example.musicplayer.constant.Constant;
import com.example.musicplayer.constant.PlayerStatus;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.FileHelper;
import com.example.musicplayer.util.MediaUtil;

import org.litepal.LitePal;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private boolean isChange; //拖动进度条
    private boolean isSeek;//标记是否在暂停的时候拖动进度条
    private boolean flag; //用做暂停的标记
    private int time;   //记录暂停的时间
    private Button mPlayerBtn;
    private ImageView mNextIv;
    private TextView mSongNameTv;
    private TextView mSingerTv;
    private CircleImageView mCoverIv;//封面
    private ObjectAnimator mCircleAnimator;//动画
    private Song mSong;
    private LinearLayout mLinear;
    private MediaPlayer mMediaPlayer;
    private SeekBar mSeekBar;
    private Thread mSeekBarThread;
    private PlayerService.PlayStatusBinder mPlayStatusBinder;
    //注册广播
    private IntentFilter intentFilter;
    private SongChangeReceiver songChangeReceiver;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastName.SONG_CHANGE);
        intentFilter.addAction(BroadcastName.SONG_PAUSE);
        intentFilter.addAction(BroadcastName.SONG_RESUME);
        intentFilter.addAction(BroadcastName.ONLINE_SONG);
        intentFilter.addAction(BroadcastName.ONLINE_SONG_ERROR);
        intentFilter.addAction(BroadcastName.ONLINE_ALBUM_SONG_Change);
        songChangeReceiver = new SongChangeReceiver();
        registerReceiver(songChangeReceiver, intentFilter);
        LitePal.getDatabase();

        initView();
        onClick();
    }


    private void initView() {
        mSong = FileHelper.getSong();
        Log.d(TAG, "jsyjst: " + mSong.toString());
        mSongNameTv = findViewById(R.id.tv_song_name);
        mSingerTv = findViewById(R.id.tv_singer);
        mLinear = findViewById(R.id.linear_player);
        mSeekBar = findViewById(R.id.sb_progress);
        mNextIv = findViewById(R.id.song_next);
        mCoverIv = findViewById(R.id.circle_img);
        //设置属性动画
        mCircleAnimator = ObjectAnimator.ofFloat(mCoverIv, "rotation", 0.0f, 360.0f);
        mCircleAnimator.setDuration(30000);
        mCircleAnimator.setInterpolator(new LinearInterpolator());
        mCircleAnimator.setRepeatCount(-1);
        mCircleAnimator.setRepeatMode(ValueAnimator.RESTART);


        if (mSong.getSongName() != null) {
            //启动服务
            Intent playIntent = new Intent(MainActivity.this, PlayerService.class);
            bindService(playIntent, connection, Context.BIND_AUTO_CREATE);
            Log.d(TAG, "------initView:bindService ");

            mLinear.setVisibility(View.VISIBLE);
            mSongNameTv.setText(mSong.getSongName());
            mSingerTv.setText(mSong.getSinger());
            mSeekBar.setMax((int) mSong.getDuration());
            mSeekBar.setProgress((int) mSong.getCurrentTime());
            if (mSong.getImgUrl() == null) {
                FileHelper.setSingerImg(MainActivity.this, mSong.getSinger(), mCoverIv);
            } else {
                Glide.with(this)
                        .load(mSong.getImgUrl())
                        .apply(RequestOptions.errorOf(R.drawable.background))
                        .into(mCoverIv);
            }


        } else {
            mLinear.setVisibility(View.GONE);
        }
        mPlayerBtn = findViewById(R.id.btn_player);

        addMainFragment();
    }

    private void onClick() {
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
                    mPlayStatusBinder.getMediaPlayer().seekTo(seekBar.getProgress());
                } else {
                    time = seekBar.getProgress();
                }
                isChange = false;
                seekBarStart();
            }
        });

        //
        mPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer = mPlayStatusBinder.getMediaPlayer();
                if (mPlayStatusBinder.isPlaying()) {
                    time = mMediaPlayer.getCurrentPosition();
                    mPlayStatusBinder.pause();
                    flag = true;
                    mPlayerBtn.setSelected(false);
                    mCircleAnimator.pause();
                } else if (flag) {
                    mPlayStatusBinder.resume();
                    flag = false;
                    if (isSeek) {
                        mMediaPlayer.seekTo(time);
                    } else {
                        isSeek = false;
                    }
                    mCircleAnimator.resume();
                    mPlayerBtn.setSelected(true);
                    mSeekBarThread = new Thread(new SeekBarThread());
                    mSeekBarThread.start();
                } else {
                    if (FileHelper.getSong().isOnline()) {
                        mPlayStatusBinder.playOnline();
                    } else {
                        mPlayStatusBinder.play(FileHelper.getSong().getListType());
                    }
                    mMediaPlayer.seekTo((int) mSong.getCurrentTime());
                    mCircleAnimator.start();
                    mPlayerBtn.setSelected(true);
                    seekBarStart();
                }
            }
        });
        mNextIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayStatusBinder.next();
                if (mPlayStatusBinder.isPlaying()) {
                    mPlayerBtn.setSelected(true);
                } else {
                    mPlayerBtn.setSelected(false);
                }
            }
        });

        //点击播放栏，跳转到播放的主界面
        mLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPlayActivityIntent = new Intent(MainActivity.this, PlayActivity.class);

                //播放情况
                if (mPlayStatusBinder.isPlaying()) {
                    Song song = FileHelper.getSong();
                    song.setCurrentTime(mPlayStatusBinder.getCurrentTime());
                    FileHelper.saveSong(song);
                    toPlayActivityIntent.putExtra(PlayerStatus.PLAYER_STATUS, PlayerStatus.PLAY);
                } else {
                    //暂停情况
                    Song song = FileHelper.getSong();
                    song.setCurrentTime(mSeekBar.getProgress());
                    FileHelper.saveSong(song);
                }
                if (FileHelper.getSong().getImgUrl() != null) {
                    toPlayActivityIntent.putExtra(SearchContentFragment.IS_ONLINE, true);
                }
                startActivity(toPlayActivityIntent,
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
    }

    private void addMainFragment() {
        MainFragment mainFragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, mainFragment);
        transaction.commit();
    }

    @Override
    public void onDestroy() {
        unbindService(connection);
        unregisterReceiver(songChangeReceiver);
        Song song = FileHelper.getSong();
        song.setCurrentTime(mPlayStatusBinder.getCurrentTime());
        Log.d(TAG, "onServiceDisconnected: " + song.getCurrentTime());
        FileHelper.saveSong(song);
        super.onDestroy();


    }

    private void seekBarStart() {
        mSeekBarThread = new Thread(new SeekBarThread());
        mSeekBarThread.start();
    }

    class SeekBarThread implements Runnable {
        @Override
        public void run() {
            if (mPlayStatusBinder!=null) {
                while (!isChange && mPlayStatusBinder.isPlaying()) {
                    mSeekBar.setProgress((int) mPlayStatusBinder.getCurrentTime());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    class SongChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            mSong = FileHelper.getSong();
            mSongNameTv.setText(mSong.getSongName());
            mSingerTv.setText(mSong.getSinger());
            mSeekBar.setMax((int) mSong.getDuration());

            if (!mSong.isOnline()) {
                FileHelper.setSingerImg(MainActivity.this, mSong.getSinger(), mCoverIv);
            } else {
                Glide.with(MainActivity.this)
                        .load(mSong.getImgUrl())
                        .apply(RequestOptions.errorOf(R.drawable.background))
                        .into(mCoverIv);
            }
            if (action.equals(BroadcastName.SONG_PAUSE)) {
                mPlayerBtn.setSelected(false);
                mCircleAnimator.pause();
            } else if (action.equals(BroadcastName.SONG_RESUME)) {
                mPlayerBtn.setSelected(true);
                mCircleAnimator.resume();
                seekBarStart();

            } else {
                mPlayerBtn.setSelected(true);
                mCircleAnimator.start();
                seekBarStart();
            }
        }
    }
}
