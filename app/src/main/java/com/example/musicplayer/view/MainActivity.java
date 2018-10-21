package com.example.musicplayer.view;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.util.FileHelper;

public class MainActivity extends AppCompatActivity {
    private boolean play=false;
    private Button playerBtn;
    private TextView mSongNameTv;
    private TextView mSingerTv;
    private Song mSong;
    private LinearLayout mLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        mSong= FileHelper.getSong();
        mSongNameTv=findViewById(R.id.tv_song_name);
        mSingerTv=findViewById(R.id.tv_singer);
        mLinear=findViewById(R.id.linear_player);
        if(mSong!=null){
            mLinear.setVisibility(View.VISIBLE);
            mSongNameTv.setText(mSong.getTitle());
            mSingerTv.setText(mSong.getArtist());
        }else {
            mLinear.setVisibility(View.GONE);
        }
        playerBtn=findViewById(R.id.btn_player);
        playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play=!play;
                playerBtn.setSelected(play);
            }
        });
        addMainFragment();
    }

    private void addMainFragment(){
        MainFragment mainFragment=new MainFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,mainFragment);
        transaction.commit();
    }
}
