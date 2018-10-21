package com.example.musicplayer.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.musicplayer.constant.PlayerStatus;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.util.FileHelper;

import static com.example.musicplayer.constant.PlayerStatus.PAUSE;
import static com.example.musicplayer.constant.PlayerStatus.STOP;

@SuppressLint("NewApi")
public class PlayerService extends Service {

    private PlayStatusBinder mPlayStatusBinder = new PlayStatusBinder();
    private MediaPlayer mediaPlayer = new MediaPlayer();        //媒体播放器对象
    private boolean isPause;                    //暂停状态
    private Song song;
    private boolean isPlaying; //是否播放


    @Override
    public IBinder onBind(Intent arg0) {
        return mPlayStatusBinder;
    }

    public class PlayStatusBinder extends Binder {
        /**
         * 播放音乐
         *
         * @param
         */

        public void play() {
            try {
                song = FileHelper.getSong();
                mediaPlayer.reset();//把各项参数恢复到初始状态
                mediaPlayer.setDataSource(song.getUrl());
                mediaPlayer.prepare();    //进行缓冲
                isPlaying=true;
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        /**
         * 暂停音乐
         */

        public void pause() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                isPlaying=false;
                mediaPlayer.pause();
                isPause = true;
            }
        }

        public  void resume(){
            if(isPause){
                mediaPlayer.start();
                isPlaying=true;
                isPause=false;
            }
        }


        /**
         * 停止音乐
         */

        public void stop() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

        }

        public boolean isPlaying(){
            if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration()){
                isPlaying=false;
            }
            return isPlaying;
        }
        public MediaPlayer getMediaPlayer(){
            return mediaPlayer;
        }
    }

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer.isPlaying()) {
            stop();
        }
        path = intent.getStringExtra("url");
        int msg = intent.getIntExtra("MSG", 0);
        if (msg ==PlayerStatus.PLAY) {
            play(0);
        } else if (msg == PAUSE) {
            pause();
        } else if (msg ==STOP) {
            stop();
        }
        return super.onStartCommand(intent, flags, startId);
    }
*/


    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    /**
     * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
     */
    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int position;

        public PreparedListener(int position) {
            this.position = position;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();    //开始播放
            if (position > 0) {    //如果音乐不是从头播放
                mediaPlayer.seekTo(position);
            }
        }
    }
}


