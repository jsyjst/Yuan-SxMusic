package com.example.musicplayer.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.musicplayer.app.Api;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.base.observer.BaseObserver;
import com.example.musicplayer.entiy.HistorySong;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.entiy.Love;
import com.example.musicplayer.entiy.OnlineSong;
import com.example.musicplayer.entiy.SingerImg;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.entiy.SongUrl;
import com.example.musicplayer.event.OnlineSongChangeEvent;
import com.example.musicplayer.event.OnlineSongErrorEvent;
import com.example.musicplayer.event.SongAlbumEvent;
import com.example.musicplayer.event.SongCollectionEvent;
import com.example.musicplayer.event.SongHistoryEvent;
import com.example.musicplayer.event.SongLocalEvent;
import com.example.musicplayer.event.SongStatusEvent;
import com.example.musicplayer.model.https.RetrofitFactory;
import com.example.musicplayer.util.FileHelper;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("NewApi")
public class PlayerService extends Service {

    private static final String TAG = "PlayerService";
    private PlayStatusBinder mPlayStatusBinder = new PlayStatusBinder();
    private MediaPlayer mediaPlayer = new MediaPlayer();       //媒体播放器对象
    private boolean isPause;                    //暂停状态
    private boolean isPlaying; //是否播放
    private List<LocalSong> mLocalSongList;
    private List<OnlineSong> mSongList;
    private List<Love> mLoveList;
    private List<HistorySong> mHistoryList;
    private int mCurrent;
    private int mListType;


    @Override
    public void onCreate() {
        mListType = FileHelper.getSong().getListType();
        if (mListType == Constant.LIST_TYPE_ONLINE) {
            mSongList = LitePal.findAll(OnlineSong.class);
        } else if (mListType == Constant.LIST_TYPE_LOCAL) {
            mLocalSongList = LitePal.findAll(LocalSong.class);
        } else if (mListType == Constant.LIST_TYPE_LOVE) {
            mLoveList = LitePal.findAll(Love.class);
        } else if (mListType == Constant.LIST_TYPE_HISTORY) {
            mHistoryList = orderHistoryList(LitePal.findAll(HistorySong.class));
            //保证最近播放列表一开始总是第一个
            Song song = FileHelper.getSong();
            song.setCurrent(0);
            FileHelper.saveSong(song);
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                EventBus.getDefault().post(new SongStatusEvent(Constant.SONG_PAUSE));//暂停广播
                mCurrent = FileHelper.getSong().getCurrent();
                mCurrent++;
                //将歌曲的信息保存起来
                if (mListType == Constant.LIST_TYPE_LOCAL) {
                    if (mCurrent < mLocalSongList.size()) {
                        saveLocalSongInfo(mCurrent);
                        mPlayStatusBinder.play(Constant.LIST_TYPE_LOCAL);
                    } else {
                        mPlayStatusBinder.stop();
                    }
                } else if (mListType == Constant.LIST_TYPE_ONLINE) {
                    if (mCurrent < mSongList.size()) {
                        saveOnlineSongInfo(mCurrent);
                        mPlayStatusBinder.play(Constant.LIST_TYPE_ONLINE);
                    } else {
                        mPlayStatusBinder.stop();
                    }

                } else if (mListType == Constant.LIST_TYPE_LOVE) {
                    if (mCurrent < mLoveList.size()) {
                        saveLoveInfo(mCurrent);
                        mPlayStatusBinder.play(Constant.LIST_TYPE_LOVE);
                    } else {
                        mPlayStatusBinder.stop();
                    }
                } else {
                    if (mCurrent < mHistoryList.size()) {
                        saveHistoryInfo(mCurrent);
                        mPlayStatusBinder.play(Constant.LIST_TYPE_HISTORY);
                    } else {
                        mPlayStatusBinder.stop();
                    }
                }
            }
        });
        /**
         * MediaPlayer切歌进入setOnCompletionListener的问题
         * 因为直接切歌会发生错误，所以增加错误监听器。返回true。就不会回调onCompletion方法了。
         */
        mediaPlayer.setOnErrorListener((mp, what, extra) -> true);
        return mPlayStatusBinder;
    }


    public class PlayStatusBinder extends Binder {


        public void getHistoryList() {
            mHistoryList = orderHistoryList(LitePal.findAll(HistorySong.class));
            //保证最近播放列表一开始总是第一个
            Song song = FileHelper.getSong();
            song.setCurrent(0);
            FileHelper.saveSong(song);
        }

        /**
         * 播放音乐
         *
         * @param
         */

        public void play(int listType) {
            try {
                mListType = listType;
                if (mListType == Constant.LIST_TYPE_ONLINE) {
                    mSongList = LitePal.findAll(OnlineSong.class);
                    EventBus.getDefault().post(new SongAlbumEvent());
                } else if (mListType == Constant.LIST_TYPE_LOCAL) {
                    mLocalSongList = LitePal.findAll(LocalSong.class);
                    EventBus.getDefault().post(new SongLocalEvent()); //发送本地歌曲改变事件
                } else if (mListType == Constant.LIST_TYPE_LOVE) {
                    mLoveList = orderList(LitePal.findAll(Love.class));
                    EventBus.getDefault().post(new SongCollectionEvent(true));//发送歌曲改变事件
                } else if (mListType == Constant.LIST_TYPE_HISTORY) {
                    EventBus.getDefault().post(new SongHistoryEvent());  //发送随机歌曲改变事件
                }
                mCurrent = FileHelper.getSong().getCurrent();
                mediaPlayer.reset();//把各项参数恢复到初始状态
                if (mListType == Constant.LIST_TYPE_LOCAL) {
                    mediaPlayer.setDataSource(mLocalSongList.get(mCurrent).getUrl());
                    startPlay();
                } else if (mListType == Constant.LIST_TYPE_ONLINE) {
                    getSongUrl(mSongList.get(mCurrent).getSongId());
                } else if (mListType == Constant.LIST_TYPE_LOVE) {
                    mediaPlayer.setDataSource(mLoveList.get(mCurrent).getUrl());
                } else {
                    mediaPlayer.setDataSource(mHistoryList.get(mCurrent).getUrl());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //播放搜索歌曲
        public void playOnline() {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(FileHelper.getSong().getUrl());
                mediaPlayer.prepare();
                isPlaying = true;
                saveToHistoryTable();
                mediaPlayer.start();
                EventBus.getDefault().post(new OnlineSongChangeEvent()); //发送网络歌曲改变事件
                EventBus.getDefault().post(new SongStatusEvent(Constant.SONG_CHANGE));
            } catch (Exception e) {
                EventBus.getDefault().post(new OnlineSongErrorEvent());
                e.printStackTrace();
            }

        }


        /**
         * 暂停音乐
         */

        public void pause() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                isPlaying = false;
                mediaPlayer.pause();
                isPause = true;
                EventBus.getDefault().post(new SongStatusEvent(Constant.SONG_PAUSE));//发送暂停的广播给主活动
            }
        }

        public void resume() {
            if (isPause) {
                mediaPlayer.start();
                isPlaying = true;
                isPause = false;
                EventBus.getDefault().post(new SongStatusEvent(Constant.SONG_RESUME));
            }
        }


        public void next() {
            EventBus.getDefault().post(new SongStatusEvent(Constant.SONG_RESUME));
            mCurrent = FileHelper.getSong().getCurrent();
            mCurrent++;
            Log.d(TAG, "next: "+mCurrent);
            if (mListType == Constant.LIST_TYPE_LOCAL) {
                if (mCurrent >= mLocalSongList.size()) {
                    mCurrent = 0;
                }
                saveLocalSongInfo(mCurrent);
                mPlayStatusBinder.play(Constant.LIST_TYPE_LOCAL);
            } else if (mListType == Constant.LIST_TYPE_ONLINE) {
                if (mCurrent >= mSongList.size()) {
                    mCurrent = 0;
                }
                saveOnlineSongInfo(mCurrent);
                mPlayStatusBinder.play(Constant.LIST_TYPE_ONLINE);
            } else if (mListType == Constant.LIST_TYPE_LOVE) {
                if (mCurrent >= mLoveList.size()) {
                    mCurrent = 0;
                }
                saveLoveInfo(mCurrent);
                mPlayStatusBinder.play(Constant.LIST_TYPE_LOVE);
            } else {
                if (mCurrent >= mHistoryList.size()) {
                    mCurrent = 0;
                }
                saveHistoryInfo(mCurrent);
                mPlayStatusBinder.play(Constant.LIST_TYPE_HISTORY);
            }
        }

        public void last() {
            EventBus.getDefault().post(new SongStatusEvent(Constant.SONG_RESUME));//暂停广播
            mCurrent = FileHelper.getSong().getCurrent();
            mCurrent--;
            if (mCurrent == -1) {
                if (mListType == Constant.LIST_TYPE_LOCAL) {
                    mCurrent = mLocalSongList.size() - 1;
                } else if (mListType == Constant.LIST_TYPE_ONLINE) {
                    mCurrent = mSongList.size() - 1;
                } else {
                    mCurrent = mLoveList.size() - 1;
                }
            }
            if (mListType == Constant.LIST_TYPE_LOCAL) {
                saveLocalSongInfo(mCurrent);
                mPlayStatusBinder.play(mListType);
            } else if (mListType == Constant.LIST_TYPE_ONLINE) {
                saveOnlineSongInfo(mCurrent);
                mPlayStatusBinder.play(mListType);
            } else if (mListType == Constant.LIST_TYPE_LOVE) {
                saveLoveInfo(mCurrent);
                mPlayStatusBinder.play(mListType);
            } else {
                saveHistoryInfo(mCurrent);
                mPlayStatusBinder.play(mListType);
            }
        }

        /**
         * 停止音乐
         */

        public void stop() {
            if (mediaPlayer != null) {
                isPlaying = false;
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }

        public boolean isPlaying() {

            return isPlaying;
        }

        public MediaPlayer getMediaPlayer() {

            return mediaPlayer;
        }

        public long getCurrentTime() {
            return mediaPlayer.getCurrentPosition()/1000;
        }
    }


    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    //保存本地音乐列表的信息
    private void saveLocalSongInfo(int current) {
        //将歌曲的信息保存起来
        mLocalSongList = LitePal.findAll(LocalSong.class);
        Song song = new Song();
        LocalSong localSong = mLocalSongList.get(current);
        song.setCurrent(current);
        song.setSongName(localSong.getName());
        song.setSinger(localSong.getSinger());
        song.setDuration(localSong.getDuration());
        song.setUrl(localSong.getUrl());
        song.setImgUrl(localSong.getPic());
        song.setSongId(localSong.getSongId());
        song.setOnline(false);
        song.setListType(Constant.LIST_TYPE_LOCAL);
        FileHelper.saveSong(song);
    }

    //保存网络专辑列表的信息
    private void saveOnlineSongInfo(int current) {
        mSongList = LitePal.findAll(OnlineSong.class);
        Song song = new Song();
        song.setCurrent(current);
        song.setSongId(mSongList.get(current).getSongId());
        song.setSongName(mSongList.get(current).getName());
        song.setSinger(mSongList.get(current).getSinger());
        song.setDuration(mSongList.get(current).getDuration());
        song.setUrl(mSongList.get(current).getUrl());
        song.setImgUrl(mSongList.get(current).getPic());
        song.setOnline(true);
        song.setListType(Constant.LIST_TYPE_ONLINE);
        FileHelper.saveSong(song);
    }

    //保存我的收藏的列表的信息
    private void saveLoveInfo(int current) {
        mLoveList = orderList(LitePal.findAll(Love.class));
        Love love = mLoveList.get(current);
        Song song = new Song();
        song.setCurrent(current);
        song.setSongId(love.getSongId());
        song.setSongName(love.getName());
        song.setSinger(love.getSinger());
        song.setUrl(love.getUrl());
        song.setImgUrl(love.getPic());
        song.setListType(Constant.LIST_TYPE_LOVE);
        song.setOnline(love.isOnline());
        song.setDuration(love.getDuration());
        FileHelper.saveSong(song);
    }

    //保存我的收藏的列表的信息
    private void saveHistoryInfo(int current) {
        HistorySong historySong = mHistoryList.get(current);
        Song song = new Song();
        song.setCurrent(current);
        song.setSongId(historySong.getSongId());
        song.setSongName(historySong.getName());
        song.setSinger(historySong.getSinger());
        song.setUrl(historySong.getUrl());
        song.setImgUrl(historySong.getPic());
        song.setListType(Constant.LIST_TYPE_HISTORY);
        song.setOnline(historySong.isOnline());
        song.setDuration(historySong.getDuration());
        FileHelper.saveSong(song);
    }

    //将歌曲保存到最近播放的数据库中
    private void saveToHistoryTable() {

        final Song song = FileHelper.getSong();
        LitePal.where("songId=?", song.getSongId()).findAsync(HistorySong.class)
                .listen(new FindMultiCallback<HistorySong>() {
                    @Override
                    public void onFinish(List<HistorySong> list) {
                        if (list.size() == 1) {
                            LitePal.deleteAll(HistorySong.class, "songId=?", song.getSongId());
                        }
                        final HistorySong history = new HistorySong();
                        history.setSongId(song.getSongId());
                        history.setName(song.getSongName());
                        history.setSinger(song.getSinger());
                        history.setUrl(song.getUrl());
                        history.setPic(song.getImgUrl());
                        history.setOnline(song.isOnline());
                        history.setDuration(song.getDuration());
                        history.saveAsync().listen(new SaveCallback() {
                            @Override
                            public void onFinish(boolean success) {
                                if (success) {
                                    if (LitePal.findAll(HistorySong.class).size() > Constant.HISTORY_MAX_SIZE) {
                                        LitePal.delete(HistorySong.class, LitePal.findFirst(HistorySong.class).getId());
                                    }
                                }
                            }
                        });

                    }
                });

    }

    //对数据库进行倒叙排序
    private List<Love> orderList(List<Love> tempList) {
        List<Love> loveList = new ArrayList<>();
        loveList.clear();
        for (int i = tempList.size() - 1; i >= 0; i--) {
            loveList.add(tempList.get(i));
        }
        return loveList;
    }

    private List<HistorySong> orderHistoryList(List<HistorySong> tempList) {
        List<HistorySong> historySongList = new ArrayList<>();
        historySongList.clear();
        for (int i = tempList.size() - 1; i >= 0; i--) {
            historySongList.add(tempList.get(i));
        }
        return historySongList;
    }

    //网络请求获取播放地址
    private void getSongUrl(String songId){
        RetrofitFactory.createRequestOfSongUrl().getSongUrl(Api.SONG_URL_DATA_LEFT+songId+Api.SONG_URL_DATA_RIGHT)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<SongUrl>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(SongUrl songUrl) {
                        if(songUrl.getCode() == 0){
                            String sip = songUrl.getReq_0().getData().getSip().get(0);
                            String purl = songUrl.getReq_0().getData().getMidurlinfo().get(0).getPurl();
                            Song song = FileHelper.getSong();
                            assert song != null;
                            song.setUrl(sip+purl);
                            FileHelper.saveSong(song);
                            try {
                                mediaPlayer.setDataSource(sip+purl);
                                startPlay();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            Log.d(TAG, "onNext:"+ songUrl.getCode()+":获取不到歌曲播放地址");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    private void startPlay() throws IOException {
        mediaPlayer.prepare();    //进行缓冲
        isPlaying = true;
        mediaPlayer.start();
        saveToHistoryTable();
        EventBus.getDefault().post(new SongStatusEvent(Constant.SONG_CHANGE));//发送所有歌曲改变事件
        EventBus.getDefault().post(new OnlineSongChangeEvent()); //发送网络歌曲改变事件
    }
}


