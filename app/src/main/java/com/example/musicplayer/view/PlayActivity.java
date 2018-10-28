package com.example.musicplayer.view;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.musicplayer.R;
import com.example.musicplayer.base.BaseActivity;
import com.example.musicplayer.constant.PlayerStatus;
import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.entiy.Mp3Info;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.presenter.PlayPresenter;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.DisplayUtil;
import com.example.musicplayer.util.FastBlurUtil;
import com.example.musicplayer.util.FileHelper;
import com.example.musicplayer.util.MediaUtil;
import com.example.musicplayer.widget.BackgroundAnimationRelativeLayout;
import com.example.musicplayer.widget.DiscView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 播放界面
 */
public class PlayActivity extends BaseActivity implements IPlayContract.View {

    private String TAG = "PlayActivity";

    private boolean isChange; //拖动进度条
    private boolean isSeek;//标记是否在暂停的时候拖动进度条
    private boolean flag; //用做暂停的标记
    private int time;   //记录暂停的时间
    private boolean isPlaying;
    private Song mSong;
    private MediaPlayer mMediaPlayer;

    private TextView mSongTv; //
    private TextView mSingerTv;
    private Button mPlayBtn;
    private Button mLastBtn;
    private Button mNextBtn;
    private RelativeLayout mPlayRelative;
    private PlayPresenter mPresenter;

    private SeekBar mSeekBar;
    private TextView mCurrentTimeTv;
    private TextView mDurationTimeTv;


    private DiscView mDisc; //唱碟
    private ImageView mDiscImg; //唱碟中的歌手头像
    private Bitmap mImgBmp;
    private Button mGetImgAndLrcBtn;//获取封面和歌词
    private BackgroundAnimationRelativeLayout mRootLayout;
    private List<Mp3Info> mMp3InfoList;//用来判断是否有本地照片

    //服务
    private Thread mSeekBarThread;
    private IntentFilter mIntentFilter;
    private SongChangeReceiver songChangeReceiver;
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
    private Handler mMusicHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSeekBar.setProgress((int) mPlayStatusBinder.getCurrentTime());
            mCurrentTimeTv.setText(MediaUtil.formatTime(mSeekBar.getProgress()));
            startUpdateSeekBarProgress();
        }
    };


    @Override
    protected void initViews() {
        CommonUtil.hideStatusBar(this);
        setContentView(R.layout.activity_play);

        //设置进入退出动画
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());

        //与Presenter建立关系
        mPresenter = new PlayPresenter();
        mPresenter.attachView(this);

        //注册广播
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.song.change");
        songChangeReceiver = new SongChangeReceiver();
        registerReceiver(songChangeReceiver, mIntentFilter);


        mRootLayout = findViewById(R.id.relative_root);
        mSongTv = findViewById(R.id.tv_song);
        mSingerTv = findViewById(R.id.tv_singer);
        mPlayBtn = findViewById(R.id.btn_player);
        mLastBtn = findViewById(R.id.btn_last);
        mNextBtn = findViewById(R.id.next);
        mGetImgAndLrcBtn = findViewById(R.id.btn_get_img_lrc);

        mSeekBar = findViewById(R.id.seek);
        mDurationTimeTv = findViewById(R.id.tv_duration_time);
        mCurrentTimeTv = findViewById(R.id.tv_current_time);


        mDisc = findViewById(R.id.disc_view);
        mDiscImg = findViewById(R.id.iv_disc_background);


        //绑定服务
        Intent playIntent = new Intent(PlayActivity.this, PlayerService.class);
        bindService(playIntent, connection, Context.BIND_AUTO_CREATE);

        mSong = FileHelper.getSong();
        mMp3InfoList = MediaUtil.getMp3Info();
        mSingerTv.setText(mSong.getArtist());
        mSongTv.setText(mSong.getTitle());
        mCurrentTimeTv.setText(MediaUtil.formatTime(mSong.getCurrentTime()));
        mDurationTimeTv.setText(MediaUtil.formatTime(mSong.getDuration()));
        mSeekBar.setMax((int) mSong.getDuration());
        mSeekBar.setProgress((int) mSong.getCurrentTime());
        setLocalImg(mSong.getArtist());

        if (getIntent().getIntExtra(PlayerStatus.PLAYER_STATUS, 2) == 1) {
            mDisc.play();
            mPlayBtn.setSelected(true);
            startUpdateSeekBarProgress();
        }


    }

    private void try2UpdateMusicPicBackground(final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Drawable drawable = getForegroundDrawable(bitmap);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRootLayout.setForeground(drawable);
                        mRootLayout.beginAnimation();
                    }
                });
            }
        }).start();
    }

    private Drawable getForegroundDrawable(Bitmap bitmap) {
        /*得到屏幕的宽高比，以便按比例切割图片一部分*/
        final float widthHeightSize = (float) (DisplayUtil.getScreenWidth(PlayActivity.this)
                * 1.0 / DisplayUtil.getScreenHeight(this) * 1.0);

        int cropBitmapWidth = (int) (widthHeightSize * bitmap.getHeight());
        int cropBitmapWidthX = (int) ((bitmap.getWidth() - cropBitmapWidth) / 2.0);

        /*切割部分图片*/
        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, cropBitmapWidthX, 0, cropBitmapWidth,
                bitmap.getHeight());
        /*缩小图片*/
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(cropBitmap, bitmap.getWidth() / 50, bitmap
                .getHeight() / 50, false);
        /*模糊化*/
        final Bitmap blurBitmap = FastBlurUtil.doBlur(scaleBitmap, 8, true);

        final Drawable foregroundDrawable = new BitmapDrawable(blurBitmap);
        /*加入灰色遮罩层，避免图片过亮影响其他控件*/
        foregroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        return foregroundDrawable;
    }


    @Override
    protected void onClick() {
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaying = !isPlaying;
                mPlayBtn.setSelected(isPlaying);
            }
        });

        mGetImgAndLrcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSingerAndLrc();
            }
        });

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
                startUpdateSeekBarProgress();
            }
        });

        //
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer = mPlayStatusBinder.getMediaPlayer();
                if (mPlayStatusBinder.isPlaying()) {
                    time = mMediaPlayer.getCurrentPosition();
                    mPlayStatusBinder.pause();
                    flag = true;
                    mPlayBtn.setSelected(false);
                    mDisc.pause();
                } else if (flag) {
                    mPlayStatusBinder.resume();
                    flag = false;
                    if (isSeek) {
                        mMediaPlayer.seekTo(time);
                    } else {
                        isSeek = false;
                    }
                    mDisc.play();
                    mPlayBtn.setSelected(true);
                    startUpdateSeekBarProgress();
                } else {
                    mPlayStatusBinder.play(0);
                    mMediaPlayer.seekTo((int) mSong.getCurrentTime());
                    mDisc.play();
                    mPlayBtn.setSelected(true);
                    startUpdateSeekBarProgress();
                }
            }
        });
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayStatusBinder.next();
                if (mPlayStatusBinder.isPlaying()) {
                    mPlayBtn.setSelected(true);
                } else {
                    mPlayBtn.setSelected(false);
                }
                mDisc.next();
            }
        });
        mLastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayStatusBinder.last();
                mPlayBtn.setSelected(true);
                mDisc.last();
            }
        });
    }

    @Override
    public String getSingerName() {
        Song song = FileHelper.getSong();
        if (song.getArtist().contains("/")) {
            String[] s = song.getArtist().split("/");
            return s[0].trim();
        } else {
            return song.getArtist().trim();
        }

    }

    @Override
    public void getSingerAndLrc() {
        mGetImgAndLrcBtn.setText("正在获取...");
        mPresenter.getSingerImg(getSingerName());
    }

    @Override
    public void setSingerImg(String ImgUrl) {
        Log.d(TAG, "setSingerImg: success");
        SimpleTarget target = new SimpleTarget<Drawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(@Nullable Drawable resource, Transition<? super Drawable> transition) {

                mImgBmp = ((BitmapDrawable) resource).getBitmap();
                //保存图片到本地
                FileHelper.saveImgToNative(PlayActivity.this, mImgBmp, getSingerName());
                Song song = FileHelper.getSong();
                mMp3InfoList.get(song.getCurrent()).setImgSave(true);
                try2UpdateMusicPicBackground(mImgBmp);
                setDiscImg(mImgBmp);
                CommonUtil.showToast(PlayActivity.this, "获取封面歌词成功");
                mGetImgAndLrcBtn.setVisibility(View.GONE);
            }
        };
        Glide.with(this)
                .load(ImgUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.background))
                .apply(RequestOptions.errorOf(R.drawable.background))
                .into(target);

    }

    @Override
    public void setImgFail(String errorMessage) {
        CommonUtil.showToast(this, errorMessage);
    }


    //设置唱碟中歌手头像
    private void setDiscImg(Bitmap bitmap) {
        mDiscImg.setImageDrawable(mDisc.getDiscDrawable(bitmap));

        int marginTop = (int) (DisplayUtil.SCALE_DISC_MARGIN_TOP * CommonUtil.getScreenHeight(PlayActivity.this));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mDiscImg
                .getLayoutParams();
        layoutParams.setMargins(0, marginTop, 0, 0);

        mDiscImg.setLayoutParams(layoutParams);
    }

    private class SongChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Song mSong = FileHelper.getSong();
            mSongTv.setText(mSong.getTitle());
            mSingerTv.setText(mSong.getArtist());
            mDurationTimeTv.setText(MediaUtil.formatTime(mSong.getDuration()));
            mPlayBtn.setSelected(true);
            setLocalImg(mSong.getArtist());//显示照片
            mSeekBar.setMax((int) mSong.getDuration());
            startUpdateSeekBarProgress();

        }
    }

    private void startUpdateSeekBarProgress() {
        /*避免重复发送Message*/
        stopUpdateSeekBarProgress();
        mMusicHandler.sendEmptyMessageDelayed(0, 1000);
    }

    private void stopUpdateSeekBarProgress() {
        mMusicHandler.removeMessages(0);
    }

    private void setLocalImg(String singer) {

        String imgUrl = Environment.getExternalStorageDirectory() + "/yuanmusic/img/"
                + MediaUtil.formatSinger(singer) + ".jpg";
        SimpleTarget target = new SimpleTarget<Drawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(@Nullable Drawable resource, Transition<? super Drawable> transition) {
                mGetImgAndLrcBtn.setVisibility(View.GONE);
                mImgBmp = ((BitmapDrawable) resource).getBitmap();
                try2UpdateMusicPicBackground(mImgBmp);
                setDiscImg(mImgBmp);
            }
        };
            Glide.with(this)
                    .load(imgUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            mGetImgAndLrcBtn.setVisibility(View.VISIBLE);
                            mGetImgAndLrcBtn.setText("获取封面和歌词");
                            setDiscImg(BitmapFactory.decodeResource(getResources(),R.drawable.default_disc));
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .apply(RequestOptions.errorOf(R.drawable.background))
                    .into(target);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        unregisterReceiver(songChangeReceiver);

    }

}
