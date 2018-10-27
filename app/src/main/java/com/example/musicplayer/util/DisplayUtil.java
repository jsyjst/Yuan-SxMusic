package com.example.musicplayer.util;

import android.content.Context;

/**
 * Created by 残渊 on 2018/10/27.
 */

public class DisplayUtil {

    /*手柄起始角度*/
    public static final float ROTATION_INIT_NEEDLE = -30;

    /*截图屏幕宽高*/
    private static final float BASE_SCREEN_WIDTH = (float) 1080.0;
    private static final float BASE_SCREEN_HEIGHT = (float) 1920.0;

    /*唱针宽高、距离等比例*/
    public static final float SCALE_NEEDLE_WIDTH = (float) (276.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_MARGIN_LEFT = (float) (500.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_PIVOT_X = (float) (43.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_PIVOT_Y = (float) (43.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_HEIGHT = (float) (413.0 / BASE_SCREEN_HEIGHT);
    public static final float SCALE_NEEDLE_MARGIN_TOP = (float) (43.0 / BASE_SCREEN_HEIGHT);

    /*唱盘比例*/
    public static final float SCALE_DISC_SIZE = (float) (813.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_DISC_MARGIN_TOP = (float) (190 / BASE_SCREEN_HEIGHT);

    /*专辑图片比例*/
    public static final float SCALE_MUSIC_PIC_SIZE = (float) (533.0 / BASE_SCREEN_WIDTH);

    /*设备屏幕宽度*/
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /*设备屏幕高度*/
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
