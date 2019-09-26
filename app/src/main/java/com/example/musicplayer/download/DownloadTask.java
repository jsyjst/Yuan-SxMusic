package com.example.musicplayer.download;

import android.os.AsyncTask;


import com.example.musicplayer.app.Api;
import com.example.musicplayer.entiy.DownloadInfo;
import com.example.musicplayer.util.DownloadUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.musicplayer.app.Constant.*;


/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/04/08
 *     desc   :
 * </pre>
 */

public class DownloadTask extends AsyncTask<DownloadInfo, DownloadInfo, Integer> {

    private DownloadListener mDownListener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private long lastProgress;

    public DownloadTask(DownloadListener downloadListener) {
        mDownListener = downloadListener;
    }

    @Override
    protected Integer doInBackground(DownloadInfo... downloadInfos) {
        InputStream is = null;
        RandomAccessFile saveFile = null;
        File file = null;
        DownloadInfo downloadInfo = downloadInfos[0];
        try {
            long downloadedLength = 0; //记录已下载的文件长度
            String downloadUrl = downloadInfo.getUrl();


            File downloadFile = new File(Api.STORAGE_SONG_FILE);
            if (!downloadFile.exists()) {
                downloadFile.mkdirs();
            }
            //传过来的下载地址
            // http://ws.stream.qqmusic.qq.com/C400001DI2Jj3Jqve9.m4a?guid=358840384&vkey=2B9BF114492F203C3943D8AE38C83DD8FEEA5E628B18F7F4455CA9B5059040266D74EBD43E09627AA4419D379B6A9E1FC1E5D2104AC7BB50&uin=0&fromtag=66
            long contentLength = getContentLength(downloadUrl); //实际文件长度
            String fileName = DownloadUtil.getSaveSongFile(downloadInfo.getSinger(),downloadInfo.getSongName(),downloadInfo.getDuration(),downloadInfo.getSongId(),contentLength);
            file = new File(downloadFile ,fileName);
            if (file.exists()) {
                downloadedLength = file.length();
            }
            if (contentLength == 0) {
                return TYPE_DOWNLOAD_FAILED;
            } else if (contentLength == downloadedLength) { //已下载
                return TYPE_DOWNLOADED;
            }


            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //断点下载，指定从哪个字节开始下载
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();

            if (response != null) {
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadedLength); //跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_DOWNLOAD_CANCELED;
                    } else if (isPaused) {
                        return TYPE_DOWNLOAD_PAUSED;
                    } else {
                        total += len;
                        saveFile.write(b, 0, len);
                        int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                        downloadInfo.setProgress(progress);
                        downloadInfo.setTotalSize(contentLength);
                        downloadInfo.setCurrentSize(total+downloadedLength);
                        publishProgress(downloadInfo);
                    }
                }
                response.body().close();
                return TYPE_DOWNLOAD_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (saveFile != null) {
                    saveFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_DOWNLOAD_FAILED;
    }

    @Override
    public void onProgressUpdate(DownloadInfo... downloadInfos) {
        DownloadInfo downloadInfo = downloadInfos[0];
        int progress = downloadInfo.getProgress();
        if (progress > lastProgress) {
            mDownListener.onProgress(downloadInfo);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case TYPE_DOWNLOAD_SUCCESS:
                mDownListener.onSuccess();
                break;
            case TYPE_DOWNLOAD_FAILED:
                mDownListener.onFailed();
                break;
            case TYPE_DOWNLOAD_PAUSED:
                mDownListener.onPaused();
                break;
            case TYPE_DOWNLOAD_CANCELED:
                mDownListener.onCanceled();
                break;
            case TYPE_DOWNLOADED:
                mDownListener.onDownloaded();
            default:
                break;
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }
}
