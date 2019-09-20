package com.example.musicplayer.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.app.Api;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.download.DownloadListener;
import com.example.musicplayer.download.DownloadTask;
import com.example.musicplayer.entiy.DownloadInfo;
import com.example.musicplayer.entiy.DownloadSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.event.DownloadEvent;
import com.example.musicplayer.event.SongDownloadedEvent;
import com.example.musicplayer.event.SongListNumEvent;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.view.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import static com.example.musicplayer.app.Constant.TYPE_DOWNLOADING;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/04/08
 *     desc   : 下载服务，保证DownloadTask在后台运行
 * </pre>
 */

public class DownloadService extends Service {
    private static final String TAG = "DownloadService";
    private DownloadTask downloadTask;
    private String downloadUrl;
    private DownloadBinder downloadBinder = new DownloadBinder();
    private LinkedList<Song> downloadQueue = new LinkedList<>();//等待队列
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(DownloadInfo downloadInfo) {
            EventBus.getDefault().post(new DownloadEvent(TYPE_DOWNLOADING, downloadInfo)); //通知下载模块
            getNotificationManager().notify(1, getNotification("下载中......", downloadInfo.getProgress()));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            saveToDb(); //下载成功，则保存数据到数据库，顺便从下载数据库中移除
            start();//下载队列中的其它歌曲
            //下载成功通知前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载成功", -1));
        }

        @Override
        public void onDownloaded() {
            downloadTask = null;
            CommonUtil.showToast(DownloadService.this, "已下载");
        }

        @Override
        public void onFailed() {
            downloadTask = null;

            //下载失败通知前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载失败", -1));
            Toast.makeText(DownloadService.this, "下载失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            downloadQueue.poll();//从下载列表中移除该歌曲
            start();//下载下载列表中的歌曲
            EventBus.getDefault().post(new DownloadEvent(Constant.TYPE_DOWNLOAD_PAUSED)); //下载暂停
            Toast.makeText(DownloadService.this, "下载已暂停", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "下载已取消", Toast.LENGTH_SHORT).show();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    public class DownloadBinder extends Binder {
        public void startDownload(Song song) {
            downloadQueue.offer(song);//将歌曲放到等待队列中
            try {
                postDownloadEvent(song);//通知正在下载界面
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (downloadTask != null) {
                CommonUtil.showToast(DownloadService.this, "已经加入下载队列");
            } else {
                CommonUtil.showToast(DownloadService.this, "开始下载");
                start();
            }
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void resumeDownload() {
            start();
        }

        public void cancelDownload(String downloadUrl,String songId) {
            //如果该歌曲正在下载，则需要将downloadTask置为null
            if (downloadTask != null&&downloadQueue.peek().getSongId().equals(songId)) {
                downloadTask.cancelDownload();
            }
            //将该歌曲从下载队列中移除
            for (int i = 0; i < downloadQueue.size(); i++) {
                Song song = downloadQueue.get(i);
                if(song.getSongId().equals(songId)) downloadQueue.remove(i);
            }
            //将该歌曲从正在下载的数据库中移除
            LitePal.deleteAll(DownloadInfo.class, "songId=?", songId);//删除已下载歌曲的相关列
            //通知正在下载列表
            EventBus.getDefault().post(new DownloadEvent(Constant.TYPE_DOWNLOAD_CANCELED));
            //取消下载需要将文件删除并将通知关闭
            if (downloadUrl != null) {
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1, downloadUrl.indexOf("?"));
                File downloadFile = new File(Api.STORAGE_SONG_FILE);
                String directory = String.valueOf(downloadFile);
                File file = new File(fileName ,directory);
                if (file.exists()) {
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this, "下载已取消", Toast.LENGTH_SHORT).show();
            }

        }

}

    private void start() {
        if (downloadTask == null && !downloadQueue.isEmpty()) {
            Song song = downloadQueue.peek();
            DownloadInfo downloadInfo = new DownloadInfo();
            downloadInfo.setSongId(song.getSongId());
            downloadInfo.setUrl(song.getUrl());
            downloadInfo.setSongName(song.getSongName());
            downloadInfo.setSinger(song.getSinger());
            downloadInfo.setSong(song);
            downloadUrl = song.getUrl();
            downloadTask = new DownloadTask(listener);
            downloadTask.execute(downloadInfo);
            startForeground(1, getNotification("下载中......", 0));
        }
    }


    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "channel_001";
            String name = "下载通知";
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            getNotificationManager().createNotificationChannel(mChannel);
            Notification.Builder builder = new Notification.Builder(this, id);
            builder.setSmallIcon(R.mipmap.icon);
            builder.setContentIntent(pi);
            builder.setContentTitle(title);
            if (progress > 0) {
                builder.setContentText(progress + "%");
                builder.setProgress(100, progress, false);
            }
            return builder.build();
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
            builder.setSmallIcon(R.mipmap.icon);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon));
            builder.setContentIntent(pi);
            builder.setContentTitle(title);
            if (progress > 0) {
                builder.setContentText(progress + "%");
                builder.setProgress(100, progress, false);
            }
            return builder.build();
        }
    }

    private void saveToDb() {
        Song song = downloadQueue.poll();
        LitePal.deleteAll(DownloadInfo.class, "songId=?", song.getSongId());//删除已下载歌曲的相关列
        LitePal.deleteAll(Song.class, "songId=?", song.getSongId());//删除已下载歌曲的关联表中的相关列
        DownloadSong downloadSong = new DownloadSong();
        downloadSong.setName(song.getSongName());
        downloadSong.setSongId(song.getSongId());
        downloadSong.setDuration(song.getDuration());
        downloadSong.setPic(song.getImgUrl());
        downloadSong.setSinger(song.getSinger());
        downloadSong.setMediaId(song.getMediaId());
        downloadSong.setUrl(Api.STORAGE_SONG_FILE + "C400" + song.getMediaId() + ".m4a");
        downloadSong.save();
        EventBus.getDefault().post(new DownloadEvent(Constant.TYPE_DOWNLOAD_SUCCESS));//通知已下载列表
        EventBus.getDefault().post(new SongListNumEvent(Constant.LIST_TYPE_DOWNLOAD)); //通知主界面的下载个数需要改变
    }

    private void postDownloadEvent(Song song) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setSongName(song.getSongName());
        downloadInfo.setSongId(song.getSongId());
        downloadInfo.setUrl(song.getUrl());
        downloadInfo.setProgress(0);
        downloadInfo.setSinger(song.getSinger());
        downloadInfo.setSong(song);
        song.save();
        downloadInfo.save();
    }
}
