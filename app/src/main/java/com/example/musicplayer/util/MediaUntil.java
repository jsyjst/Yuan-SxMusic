package com.example.musicplayer.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.example.musicplayer.R;
import com.example.musicplayer.constant.MyApplication;
import com.example.musicplayer.entiy.Mp3Info;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/10/22.
 */

public class MediaUntil {
    //获取专辑封面的Uri

    private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");


    public static List<Mp3Info> getMp3Info() {
        List<Mp3Info> mp3InfoList = new ArrayList<>();
        Cursor cursor = MyApplication.getContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        for (int i = 0; i < cursor.getCount(); i++) {

            cursor.moveToNext();
            Mp3Info mp3Info = new Mp3Info();
            long id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media._ID));    //音乐id
            String title = cursor.getString((cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题
            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
            long duration = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
            long size = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.SIZE));    //文件大小
            long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            String url = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA));     //文件路径
            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐
            if (isMusic != 0) {//只把音乐添加到集合当中
                if (size > 1000 * 800) {
                    // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                    if (title.contains("-")) {
                        String[] str = title.split("-");
                        artist = str[0];
                        title = str[1];
                    }
                    mp3Info.setTitle(title.trim());
                    mp3Info.setArtist(artist);
                    mp3Info.setDuration(duration);
                    mp3Info.setSize(size);
                    mp3Info.setUrl(url);
                    mp3Info.setAlbumId(albumId);
                    mp3Info.setId(id);
                    mp3InfoList.add(mp3Info);
                }
            }
        }
        cursor.close();
        return mp3InfoList;
    }

    public static Bitmap getDefaultArtwork(Context context, boolean small) {

        return (Bitmap) BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
    }

    /**
     * 从文件当中获取专辑封面位图
     * * @param context
     * * @param songid
     * * @param albumid
     * * @return
     */
    private static Bitmap getArtworkFromFile(Context context, long songid, long albumid) {
        Bitmap bm = null;
        if (albumid < 0) {
            throw new IllegalArgumentException("Must specify an album or a song id");
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            FileDescriptor fd = null;
            if (albumid < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    fd = pfd.getFileDescriptor();
                }
            } else {
                Uri uri = ContentUris.withAppendedId(albumArtUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    fd = pfd.getFileDescriptor();
                }
            }
            options.inSampleSize = 1;            // 只进行大小判断
            options.inJustDecodeBounds = true;
            // 调用此方法得到options得到图片大小
            BitmapFactory.decodeFileDescriptor(fd, null, options);
            //我们的目标是在800pixel的画面上显示
            // 所以需要调用computeSampleSize得到图片缩放的比例
            options.inSampleSize = 100;
            //我们得到了缩放的比例，现在开始正式读入Bitmap数据
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;                        //根据options参数，减少所需要的内存
            bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bm;
    }

    /**
     * 获取专辑封面位图对象	 * @param context	 * @param song_id	 * @param album_id	 * @param allowdefalut	 * @return
     */
    public static Bitmap getArtwork(Context context, long song_id, long album_id, boolean allowdefalut, boolean small) {
        if (album_id < 0) {
            if (song_id < 0) {
                Bitmap bm = getArtworkFromFile(context, song_id, -1);
                if (bm != null) {
                    return bm;
                }
            }
            if (allowdefalut) {
                return getDefaultArtwork(context, small);
            }
            return null;
        }
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(albumArtUri, album_id);
        if (uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                //先制定原始大小
                options.inSampleSize = 1;
                //只进行大小判断
                options.inJustDecodeBounds = true;
                //调用此方法得到options得到图片的大小
                BitmapFactory.decodeStream(in, null, options);
                /** 我们的目标是在你N pixel的画面上显示。 所以需要调用computeSampleSize得到图片缩放的比例 **/
                /** 这里的target为800是根据默认专辑图片大小决定的，800只是测试数字但是试验后发现完美的结合 **/
                if (small) {
                    options.inSampleSize = computeSampleSize(options, 40);
                } else {
                    options.inSampleSize = computeSampleSize(options, 600);
                }
                // 我们得到了缩放比例，现在开始正式读入Bitmap数据
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                in = res.openInputStream(uri);
                return BitmapFactory.decodeStream(in, null, options);
            } catch (FileNotFoundException e) {
                Bitmap bm = getArtworkFromFile(context, song_id, album_id);
                if (bm != null) {
                    if (bm.getConfig() == null) {
                        bm = bm.copy(Bitmap.Config.RGB_565, false);
                        if (bm == null && allowdefalut) {
                            return getDefaultArtwork(context, small);
                        }
                    }
                } else if (allowdefalut) {
                    bm = getDefaultArtwork(context, small);
                }
                return bm;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 对图片进行合适的缩放	 * @param options	 * @param target	 * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options, int target) {
        int w = options.outWidth;
        int h = options.outHeight;
        int candidateW = w / target;
        int candidateH = h / target;
        int candidate = Math.max(candidateW, candidateH);
        if (candidate == 0) {
            return 1;
        }
        if (candidate > 1) {
            if ((w > target) && (w / candidate) < target) {
                candidate -= 1;
            }
        }
        if (candidate > 1) {
            if ((h > target) && (h / candidate) < target) {
                candidate -= 1;
            }
        }
        return candidate;
    }

    //
    public static Bitmap getMusicBitemp(Context context, long songid,
                                        long albumid) {
        Bitmap bm = null;
// 专辑id和歌曲id小于0说明没有专辑、歌曲，并抛出异常
        if (albumid < 0 && songid < 0) {
            throw new IllegalArgumentException(
                    "Must specify an album or a song id");
        }
        try {
            if (albumid < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/"
                        + songid + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver()
                        .openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            } else {
                Uri uri = ContentUris.withAppendedId(albumArtUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver()
                        .openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);


                } else {
                    return null;
                }
            }
        } catch (FileNotFoundException ex) {
        }
        //如果获取的bitmap为空，则返回一个默认的bitmap
        if (bm == null) {
            Resources resources = context.getResources();
            Drawable drawable = resources.getDrawable(R.drawable.background);
            //Drawable 转 Bitmap
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            bm = bitmapDrawable.getBitmap();
        }

        return Bitmap.createScaledBitmap(bm, 150, 150, true);
    }

}


