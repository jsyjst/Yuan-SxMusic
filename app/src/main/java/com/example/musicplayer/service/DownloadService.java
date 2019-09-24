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
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.app.Api;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.download.DownloadListener;
import com.example.musicplayer.download.DownloadTask;
import com.example.musicplayer.entiy.DownloadInfo;
import com.example.musicplayer.event.DownloadEvent;
import com.example.musicplayer.event.SongListNumEvent;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.DownloadUtil;
import com.example.musicplayer.view.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    private LinkedList<DownloadInfo> downloadQueue = new LinkedList<>();//等待队列
    private int position = 0;//下载歌曲在下载歌曲列表的位置
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(DownloadInfo downloadInfo) {
            downloadInfo.setStatus(Constant.DOWNLOAD_ING);
            EventBus.getDefault().post(new DownloadEvent(TYPE_DOWNLOADING, downloadInfo)); //通知下载模块
            if(downloadInfo.getProgress()!=100){
                getNotificationManager().notify(1, getNotification("正在下载: "+downloadInfo.getSongName(), downloadInfo.getProgress()));
            }else {
                if(downloadQueue.isEmpty()) getNotificationManager().notify(1, getNotification("下载成功",-1));
            }

        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            DownloadInfo downloadInfo = downloadQueue.poll();
            operateDb(downloadInfo); //操作数据库
            start();//下载队列中的其它歌曲
            //下载成功通知前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            if(downloadQueue.isEmpty()) getNotificationManager().notify(1, getNotification("下载成功",-1));
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
            getNotificationManager().notify(1, getNotification("下载失败",-1));
            Toast.makeText(DownloadService.this, "下载失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            DownloadInfo downloadInfo=downloadQueue.poll();//从下载列表中移除该歌曲
            updateDbOfPause(downloadInfo.getSongId());
            getNotificationManager().notify(1, getNotification("下载已暂停："+downloadInfo.getSongName(), -1));
            start();//下载下载列表中的歌曲
            downloadInfo.setStatus(Constant.DOWNLOAD_PAUSED);
            EventBus.getDefault().post(new DownloadEvent(Constant.TYPE_DOWNLOAD_PAUSED, downloadInfo)); //下载暂停
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
        public void startDownload(DownloadInfo song) {
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

        public void pauseDownload(String songId) {
            //暂停的歌曲是否为当前下载的歌曲
            if (downloadTask != null &&downloadQueue.peek().getSongId().equals(songId)) {
                downloadTask.pauseDownload();
            }else {
                listener.onPaused();
            }
        }


        public void cancelDownload(DownloadInfo song) {
            String songId = song.getSongId();
            //如果该歌曲正在下载，则需要将downloadTask置为null
            if (downloadTask != null && downloadQueue.peek().getSongId().equals(songId)) {
                downloadTask.cancelDownload();
            }
            //将该歌曲从下载队列中移除
            for (int i = 0; i < downloadQueue.size(); i++) {
                DownloadInfo downloadInfo = downloadQueue.get(i);
                if (downloadInfo.getSongId().equals(songId)) downloadQueue.remove(i);
            }
            updateDb(songId);
            deleteDb(songId);
            //取消下载需要将文件删除并将通知关闭
            if (song.getUrl() != null) {
                checkoutFile(song,song.getUrl()); //实际文件长度
            }
            //通知正在下载列表
            EventBus.getDefault().post(new DownloadEvent(Constant.TYPE_DOWNLOAD_CANCELED));

        }

    }
    private void start() {
        if (downloadTask == null && !downloadQueue.isEmpty()) {
            DownloadInfo downloadInfo = downloadQueue.peek();
            List<DownloadInfo> songList =
                    LitePal.where("songId = ?",downloadInfo.getSongId()).find(DownloadInfo.class);
            DownloadInfo currentDownloadInfo = songList.get(0);
            currentDownloadInfo.setStatus(Constant.DOWNLOAD_READY);
            EventBus.getDefault().post(new DownloadEvent(TYPE_DOWNLOADING,currentDownloadInfo));
            downloadUrl = currentDownloadInfo.getUrl();
            downloadTask = new DownloadTask(listener);
            downloadTask.execute(currentDownloadInfo);
            getNotificationManager().notify(1, getNotification("正在下载："+downloadInfo.getSongName(), 0));
        }
    }


    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title,int progress) {
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
            if(progress>0){
                builder.setContentText(progress +"%");
                builder.setProgress(100, progress, false);
            }
            return builder.build();
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
            builder.setSmallIcon(R.mipmap.icon);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon));
            builder.setContentIntent(pi);
            builder.setContentTitle(title);
            if(progress>0){
                builder.setContentText(progress +"%");
                builder.setProgress(100, progress, false);
            }
            return builder.build();
        }
    }

    private void operateDb(DownloadInfo downloadInfo) {
        updateDb(downloadInfo.getSongId());
        deleteDb(downloadInfo.getSongId());
        EventBus.getDefault().post(new DownloadEvent(Constant.TYPE_DOWNLOAD_SUCCESS));//通知已下载列表
        EventBus.getDefault().post(new SongListNumEvent(Constant.LIST_TYPE_DOWNLOAD)); //通知主界面的下载个数需要改变
    }

    //更新数据库中歌曲列表的位置，即下载完成歌曲后的位置都要减去1；
    private void updateDb(String songId) {
        long id = LitePal.select("id").where("songId = ?", songId).find(DownloadInfo.class).get(0).getId();
        List<DownloadInfo> songIdList = LitePal.where("id > ?", id + "").find(DownloadInfo.class);
        for (DownloadInfo song : songIdList) {
            song.setPosition(song.getPosition() - 1);
            song.save();
        }
    }

    //暂停时更新列表歌曲状态
    private void updateDbOfPause(String songId){
        List<DownloadInfo> statusList =
                LitePal.where("songId = ?",songId).find(DownloadInfo.class,true);
        DownloadInfo downloadInfo = statusList.get(0);
        downloadInfo.setStatus(Constant.DOWNLOAD_PAUSED);
        downloadInfo.save();
    }

    //下载完成时要删除下载歌曲表中的数据以及关联表中的数据
    private void deleteDb(String songId) {
        LitePal.deleteAll(DownloadInfo.class, "songId=?", songId);//删除已下载歌曲的相关列
    }

    private void postDownloadEvent(DownloadInfo downloadInfo) {
        //如果需要下载的表中有该条歌曲，则添加到下载队列后跳过
        List<DownloadInfo> downloadInfoList =
                LitePal.where("songId = ?",downloadInfo.getSongId()).find(DownloadInfo.class,true);
        if (downloadInfoList.size() != 0){
            DownloadInfo historyDownloadInfo = downloadInfoList.get(0);
            historyDownloadInfo.setStatus(Constant.DOWNLOAD_WAIT);
            historyDownloadInfo.save();
            EventBus.getDefault().post(new DownloadEvent(Constant.DOWNLOAD_PAUSED,historyDownloadInfo));
            downloadQueue.offer(downloadInfoList.get(0));
            return;
        }

        position = LitePal.findAll(DownloadInfo.class).size();
        downloadInfo.setPosition(position);
        downloadInfo.setStatus(Constant.DOWNLOAD_WAIT); //等待
        downloadInfo.save();
        downloadQueue.offer(downloadInfo);//将歌曲放到等待队列中
        EventBus.getDefault().post(new DownloadEvent(Constant.TYPE_DOWNLOAD_ADD));
    }


    //获取歌曲实际大小，然后判断是否存在于文件中
    public void checkoutFile(DownloadInfo song, String downloadUrl){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(downloadUrl)
                    .build();
                Call call= client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            long size = response.body().contentLength();
                            String fileName = DownloadUtil.getSaveSongFile(song.getSinger(),song.getSongName(),song.getDuration(),song.getSongId(),size);
                            File downloadFile = new File(Api.STORAGE_SONG_FILE);
                            String directory = String.valueOf(downloadFile);
                            File file = new File(fileName, directory);
                            if (file.exists()) {
                                file.delete();
                            }
                            getNotificationManager().cancel(1);
                            stopForeground(true);
                        }
                    }
                });
    }

}
