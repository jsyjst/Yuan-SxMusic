package com.example.musicplayer.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.musicplayer.R;


/**
 * Created by AchillesL on 2016/11/18.
 */

/**
 * 自定义一个控件，继承RelativeLayout
 * */
public class BackgroundAnimationRelativeLayout extends RelativeLayout {

    private final int DURATION_ANIMATION = 500;
    private final int INDEX_BACKGROUND = 0;
    private final int INDEX_FOREGROUND = 1;
    /**
     * LayerDrawable[0]: background drawable
     * LayerDrawable[1]: foreground drawable
     */
    private LayerDrawable layerDrawable;
    private ObjectAnimator objectAnimator;

    public BackgroundAnimationRelativeLayout(Context context) {
        this(context, null);
    }

    public BackgroundAnimationRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgroundAnimationRelativeLayout(Context context, AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayerDrawable();
        initObjectAnimator();
    }

    private void initLayerDrawable() {
        Drawable backgroundDrawable = getContext().getDrawable(R.drawable.ic_blackground);
        Drawable[] drawables = new Drawable[2];

        /*初始化时先将前景与背景颜色设为一致*/
        drawables[INDEX_BACKGROUND] = backgroundDrawable;
        drawables[INDEX_FOREGROUND] = backgroundDrawable;

        layerDrawable = new LayerDrawable(drawables);
    }

    private void initObjectAnimator() {
        objectAnimator = ObjectAnimator.ofFloat(this, "number", 0f, 1.0f);
        objectAnimator.setDuration(DURATION_ANIMATION);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int foregroundAlpha = (int) ((float) animation.getAnimatedValue() * 255);
                /*动态设置Drawable的透明度，让前景图逐渐显示*/
                layerDrawable.getDrawable(INDEX_FOREGROUND).setAlpha(foregroundAlpha);
                BackgroundAnimationRelativeLayout.this.setBackground(layerDrawable);
            }
        });
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /*动画结束后，记得将原来的背景图及时更新*/
                layerDrawable.setDrawable(INDEX_BACKGROUND, layerDrawable.getDrawable(
                        INDEX_FOREGROUND));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    @TargetApi(23)
    public void setForeground(Drawable drawable) {
        layerDrawable.setDrawable(INDEX_FOREGROUND, drawable);
    }

    //对外提供方法，用于开始渐变动画
    public void beginAnimation() {
        objectAnimator.start();
    }


}
