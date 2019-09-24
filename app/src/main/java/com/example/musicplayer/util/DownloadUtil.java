package com.example.musicplayer.util;

import android.util.Log;

import com.example.musicplayer.entiy.DownloadSong;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.musicplayer.app.Api.STORAGE_SONG_FILE;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/24
 *     desc   : 下载相关工具
 * </pre>
 */

public class DownloadUtil {
    /**
     *
     * @param fileName 文件跟目录
     * @return 得到已下载的歌曲
     */
    public static final List<DownloadSong> getSongFromFile(String fileName){
        //将.m4a截取掉得到singer-songName-duration-songId-size
        List<DownloadSong> res = new ArrayList<>();
        File file=new File(fileName);
        if(!file.exists()){
            file.mkdirs();
            return res;
        }
        File[] subFile = file.listFiles();
        for (File value : subFile) {
            String songFileName = value.getName();
            String songFile = songFileName.substring(0, songFileName.lastIndexOf("."));
            String[] songValue = songFile.split("-");
            long size = Long.valueOf(songValue[4]);
            //如果文件的大小不等于实际大小，则表示该歌曲还未下载完成，被人为暂停，故跳过该歌曲，不加入到已下载集合
            if(size != value.length()) continue;
            DownloadSong downloadSong = new DownloadSong();
            downloadSong.setSinger(songValue[0]);
            downloadSong.setName(songValue[1]);
            downloadSong.setDuration(Long.valueOf(songValue[2]));
            downloadSong.setSongId(songValue[3]);
            downloadSong.setUrl(fileName + songFileName);
            res.add(downloadSong);
        }
        return res;
    }


    //组装下载歌曲的文件名
    public static final String getSaveSongFile(String singer,String songName,long duration,String songId,long size){
        return singer+"-"+songName+"-"+duration+"-"+songId+"-"+size+".m4a";
    }
}
