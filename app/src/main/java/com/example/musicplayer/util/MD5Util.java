package com.example.musicplayer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/11/10
 *     desc   : MD5加密工具
 * </pre>
 */

public class MD5Util {
    public static String getFileMD5(File file){
        if(file == null||!file.exists()) return "";
        FileInputStream in = null;
        byte[] buffer = new byte[1024];
        StringBuilder res = new StringBuilder();
        int len;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len=in.read(buffer))!=-1){
                //计算文件时需要通过分段读取多次调用update来将数据更新给MessageDigest对象
                messageDigest.update(buffer,0,len);
            }
            //真正计算文件的MD5值
            byte[] bytes = messageDigest.digest();
            //将字节数组转换成16进制的字符串
            for(byte b:bytes){
                String temp = Integer.toHexString(b&0xff);
                if(temp.length()!=2){
                    temp = "0"+temp;
                }
                res.append(temp);
            }
            //返回最终的字符串
            return res.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null!=in){
                try {
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return res.toString();
    }
}
