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
import com.example.musicplayer.entiy.Mp3Info;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.util.FileHelper;
import com.example.musicplayer.util.MediaUntil;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.musicplayer.constant.PlayerStatus.PAUSE;
import static com.example.musicplayer.constant.PlayerStatus.STOP;

@SuppressLint("NewApi")
public class PlayerService extends Service {

    private static final String TAG="PlayerService";
    private PlayStatusBinder mPlayStatusBinder = new PlayStatusBinder();
    private MediaPlayer mediaPlayer = new MediaPlayer();        //媒体播放器对象
    private boolean isPause;                    //暂停状态
    private Song song;
    private boolean isPlaying; //是否播放
    private List<Mp3Info> mMp3InfoList;
    private int mCurrent;

    @Override
    public void onCreate(){
        mMp3InfoList= MediaUntil.getMp3Info();

    }
    @Override
    public IBinder onBind(Intent arg0) {
        Log.d(TAG, "-------onBind: ");

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCurrent++;
                if(mCurrent<=mMp3InfoList.size()){
                    mPlayStatusBinder.play(0);
                }
            }
        });
        return mPlayStatusBinder;
    }

    @Override
    public void onRebind(Intent intent){
        Log.d(TAG, "-------onRebind: ");

    }

    public class PlayStatusBinder extends Binder {
        /**
         * 播放音乐
         *
         * @param
         */


        public void play(int currentTime) {
            try {

                song = FileHelper.getSong();
                mediaPlayer.reset();//把各项参数恢复到初始状态
                mediaPlayer.setDataSource(mMp3InfoList.get(mCurrent).getUrl());
                mediaPlayer.prepare();    //进行缓冲
                isPlaying=true;
                mediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));
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
        public void setCurrent(int current){
            mCurrent=current;
        }
    }




    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Log.d(TAG, "----onDestroy:PlayerService ");
    }
    @Override
    public boolean onUnbind(Intent intent){
        Log.d(TAG, "-----onUnbind: ");
         return true;
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


