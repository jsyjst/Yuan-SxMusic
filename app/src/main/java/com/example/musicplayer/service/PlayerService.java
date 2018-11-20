package com.example.musicplayer.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.musicplayer.entiy.Mp3Info;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.util.FileHelper;
import com.example.musicplayer.util.MediaUtil;

import java.util.List;

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

    private IntentFilter intentFilter;

    @Override
    public void onCreate(){
        mMp3InfoList= MediaUtil.getMp3Info();

    }
    @Override
    public IBinder onBind(Intent arg0) {


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCurrent++;
                Log.d(TAG, "-------onBind: 结束");
                //将歌曲的信息保存起来
                Song song=FileHelper.getSong();
                song.setCurrent(mCurrent);
                song.setTitle(mMp3InfoList.get(mCurrent).getTitle());
                song.setArtist(mMp3InfoList.get(mCurrent).getArtist());
                song.setDuration(mMp3InfoList.get(mCurrent).getDuration());
                song.setUrl(mMp3InfoList.get(mCurrent).getUrl());
                song.setId(mMp3InfoList.get(mCurrent).getId());
                song.setAlbumId(mMp3InfoList.get(mCurrent).getAlbumId());
                FileHelper.saveSong(song);
                if(mCurrent<=mMp3InfoList.size()){
                    mPlayStatusBinder.play(0);
                }else{
                    mPlayStatusBinder.stop();
                }
                sendBroadcast(new Intent("android.song.change")); //发送广播改变播放栏的信息
                sendBroadcast(new Intent("android.song.change.local.song.list"));//发送广播改变当地列表的显示
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
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

                mCurrent = FileHelper.getSong().getCurrent();
                mediaPlayer.reset();//把各项参数恢复到初始状态
                mediaPlayer.setDataSource(mMp3InfoList.get(mCurrent).getUrl());
                mediaPlayer.prepare();    //进行缓冲
                isPlaying=true;
                mediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));
                sendBroadcast(new Intent("android.song.change"));
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
                sendBroadcast(new Intent("SONG_PAUSE"));//发送暂停的广播给主活动
            }
        }

        public  void resume(){
            if(isPause){
                mediaPlayer.start();
                isPlaying=true;
                isPause=false;
                sendBroadcast(new Intent("SONG_RESUME"));
            }
        }
        public void next(){
            mCurrent=FileHelper.getSong().getCurrent();
            mCurrent++;
            if(mCurrent>=mMp3InfoList.size()){
                mCurrent=0;
            }

            //将歌曲的信息保存起来
            Song song=FileHelper.getSong();
            song.setCurrent(mCurrent);
            song.setTitle(mMp3InfoList.get(mCurrent).getTitle());
            song.setArtist(mMp3InfoList.get(mCurrent).getArtist());
            song.setDuration(mMp3InfoList.get(mCurrent).getDuration());
            song.setUrl(mMp3InfoList.get(mCurrent).getUrl());
            song.setId(mMp3InfoList.get(mCurrent).getId());
            song.setAlbumId(mMp3InfoList.get(mCurrent).getAlbumId());
            FileHelper.saveSong(song);
            mPlayStatusBinder.play(0);


            sendBroadcast(new Intent("android.song.change")); //发送广播改变播放栏的信息
            sendBroadcast(new Intent("android.song.change.local.song.list"));//发送广播改变当地列表的显示
        }
        public  void last(){
            mCurrent=FileHelper.getSong().getCurrent();
            mCurrent--;
            if(mCurrent==-1){
                mCurrent=mMp3InfoList.size()-1;
            }

            //将歌曲的信息保存起来
            Song song=FileHelper.getSong();
            song.setCurrent(mCurrent);
            song.setTitle(mMp3InfoList.get(mCurrent).getTitle());
            song.setArtist(mMp3InfoList.get(mCurrent).getArtist());
            song.setDuration(mMp3InfoList.get(mCurrent).getDuration());
            song.setUrl(mMp3InfoList.get(mCurrent).getUrl());
            song.setId(mMp3InfoList.get(mCurrent).getId());
            song.setAlbumId(mMp3InfoList.get(mCurrent).getAlbumId());
            FileHelper.saveSong(song);
            mPlayStatusBinder.play(0);


            sendBroadcast(new Intent("android.song.change")); //发送广播改变播放栏的信息
            sendBroadcast(new Intent("android.song.change.local.song.list"));//发送广播改变当地列表的显示
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

            return isPlaying;
        }
        public MediaPlayer getMediaPlayer(){

            return mediaPlayer;
        }
        public long getCurrentTime(){
            return mediaPlayer.getCurrentPosition();
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


