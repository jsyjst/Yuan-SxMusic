package com.example.musicplayer.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by 残渊 on 2018/10/26.
 */

//通用的工具类
public class CommonUtil {
    private static Toast toast;

    public static void hideStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= 22) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //
    public static void showToast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /*
     * 得到屏幕的宽度

     */

    public static int getScreenWidth(Context context) {
        if (null == context) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    /**
     * 得到屏幕的高度
     */

    public static int getScreenHeight(Context context) {
        if (null == context) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
