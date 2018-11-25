package com.example.musicplayer.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.musicplayer.R;
import com.example.musicplayer.constant.MyApplication;

/**
 * Created by 残渊 on 2018/10/26.
 */

//通用的工具类
public class CommonUtil {
    private static Toast toast;

    public static void hideStatusBar(Activity activity, boolean isHide) {
        View decorView = activity.getWindow().getDecorView();
        if (isHide) {
            if (Build.VERSION.SDK_INT >= 22) {

                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }else {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            activity.getWindow().setStatusBarColor(MyApplication.getContext().getResources().getColor(R.color.actionBarColor));
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

    /**
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
    /**
     * EditText获取焦点并显示软键盘
     */
    //弹出软键盘
    public static void showKeyboard(EditText editText,Context context) {
        //其中editText为dialog中的输入框的 EditText
        if(editText!=null){
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }

    /**
     * 关闭软键盘
     * @param mEditText 输入框
     * @param context 上下文
     */
    public static void closeKeybord(EditText mEditText, Context context) {
        mEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 使指定的字符串显示不同的颜色
     */
    public static void showStringColor(String appointStr, String originalStr, TextView textView){
        originalStr = originalStr.replaceAll(appointStr, "<font color='#FFC66D'>" + appointStr+ "</font>");
        textView.setText(Html.fromHtml(originalStr));
    }

}
