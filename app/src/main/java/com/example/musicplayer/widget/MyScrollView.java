package com.example.musicplayer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by 残渊 on 2018/9/25.
 */

public class MyScrollView extends ScrollView {
    private int mLastYIntercept=0;

    public MyScrollView(Context context) {
        super(context);

    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        super.onInterceptTouchEvent(event);
        boolean intercepted = false;
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                intercepted=true;
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        Log.d("jsyjst", "----------------"+intercepted);
        mLastYIntercept=y;
        return intercepted;
    }
}
