package com.example.musicplayer.util;


import android.os.Environment;
import android.util.Log;

import com.example.musicplayer.entiy.Song;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *  将user序列化到本地并取出的工具
 * Created by 残渊 on 2018/7/24.
 */


public class FileHelper {

    /**
	 * 将person对象保存到文件中
	 * params:
	 * 	p:person类对象
	 */

    public  static void saveSong(Song song){
        try {
            File file=new File(Environment.getExternalStorageDirectory()+"/yuanmusic/");
            if(!file.exists()){
                Log.d("Login","------------------"+Environment.getExternalStorageDirectory());
                file.mkdirs();
            }
            //写对象流的对象
            File userFile=new File(file,"song.txt");
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(userFile));
            oos.writeObject(song);                 //将Person对象p写入到oos中
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

    public static Song getSong(){
        try {
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(Environment.getExternalStorageDirectory()+"/yuanmusic/song.txt"));
            Song song=(Song)ois.readObject();//读出对象
            return song;                                       //返回对象
        } catch (FileNotFoundException e) {
            e.printStackTrace();
           Song song=new Song();
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

}
