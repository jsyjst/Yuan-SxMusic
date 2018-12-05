package com.example.musicplayer.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.musicplayer.R;
import com.example.musicplayer.constant.BaseUri;
import com.example.musicplayer.constant.MyApplication;
import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.entiy.Song;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 将user序列化到本地并取出的工具
 * Created by 残渊 on 2018/7/24.
 */


public class FileHelper {
    private static String TAG = "FileHelper";

    /**
     * 将person对象保存到文件中
     * params:
     * p:person类对象
     */

    public static void saveSong(Song song) {
        try {
            File file = new File(MyApplication.getContext().getExternalFilesDir("yuanmusic").getAbsolutePath());
            if (!file.exists()) {
                Log.d("Login", "------------------" + MyApplication.getContext().getExternalFilesDir(""));
                file.mkdirs();
            }
            //写对象流的对象
            File userFile = new File(file, "song.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile));
            oos.writeObject(song);//将Person对象p写入到oos中
            oos.close();                        //关闭文件流
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读出对象，并且返回Person对象
     */

    public static Song getSong() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MyApplication.getContext().getExternalFilesDir("") + "/yuanmusic/song.txt"));
            Song song = (Song) ois.readObject();//读出对象
            return song;                                       //返回对象
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Song song = new Song();
            return song;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //保存图片到本地
    public static void saveImgToNative(Context context, Bitmap bitmap, String singer) {
        File file = new File(BaseUri.STORAGE_IMG_FILE);
        if (!file.exists()) {
            file.mkdirs();
        }
        File singerImgFile = new File(file, singer + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(singerImgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "saveImgToNative: fileNotFound");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setSingerImg (Context context, String singer, ImageView view) {
        if(singer.contains("/")){
            String[] s=singer.split("/");
            singer=s[0];
        }
        singer=singer.trim();
        String imgUrl =BaseUri.STORAGE_IMG_FILE + singer + ".jpg";

        Glide.with(context)
                .load(imgUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.welcome))
                .apply(RequestOptions.errorOf(R.drawable.welcome))
                .into(view);
    }
}