package com.example.musicplayer.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.musicplayer.R;
import com.example.musicplayer.base.BaseActivity;
import com.example.musicplayer.contract.IPlayContract;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.presenter.PlayPresenter;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.FileHelper;

/**
 * 播放界面
 */
public class PlayActivity extends BaseActivity implements IPlayContract.View {

    private String TAG = "PlayActivity";

    private Button mPlayBtn;
    private Button mLastBtn;
    private Button mNextBtn;
    private RelativeLayout mPlayRelative;
    private PlayPresenter mPresenter;
    private boolean isPlaying;


    @Override
    protected void initViews() {
        CommonUtil.hideStatusBar(this);
        setContentView(R.layout.activity_play);

        //设置进入退出动画
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());

        //与Presenter建立关系
        mPresenter = new PlayPresenter();
        mPresenter.attachView(this);
        mPresenter.getSingerImg(getSingerName());
        //
        mPlayRelative = findViewById(R.id.relative_play);
        mPlayBtn = findViewById(R.id.btn_player);
        mLastBtn = findViewById(R.id.btn_last);
        mNextBtn = findViewById(R.id.next);

    }

    @Override
    protected void onClick() {
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaying = !isPlaying;
                mPlayBtn.setSelected(isPlaying);
            }
        });
    }

    @Override
    public String getSingerName() {
        Song song = FileHelper.getSong();
        Log.d(TAG, "getSingerName: " + "-" + song.getArtist().toString() + "-");
        return song.getArtist().toString().trim();
    }

    @Override
    public void setSingerImg(String ImgUrl) {
        Log.d(TAG, "setSingerImg: success");
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                mPlayRelative.setBackground(resource);
            }
        };

        Glide.with(this)

                .load(ImgUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.background))
                .apply(RequestOptions.errorOf(R.drawable.background))
                .into(simpleTarget);
    }

    @Override
    public void setImgFail(String errorMessage) {
        CommonUtil.showToast(this, errorMessage);
    }
}
