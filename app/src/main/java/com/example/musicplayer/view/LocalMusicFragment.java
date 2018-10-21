package com.example.musicplayer.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.contract.ILocalMusicContract;
import com.example.musicplayer.entiy.Mp3Info;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.presenter.LocalMusicPresenter;
import com.example.musicplayer.util.FileHelper;

import java.util.ArrayList;
import java.util.List;


public class LocalMusicFragment extends Fragment implements ILocalMusicContract.View {
    private RecyclerView mRecycler;
    private List<Mp3Info> mMp3InfoList;
    private LocalMusicPresenter mPresenter;
    private View mView;
    private TextView mSongNameTv;
    private TextView mSinger;
    private Song mSong;
    private SongAdapter songAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_local_music, container, false);
        mRecycler = mView.findViewById(R.id.recycler_song_list);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSongNameTv = getActivity().findViewById(R.id.tv_song_name);
        mSinger = getActivity().findViewById(R.id.tv_singer);
        mMp3InfoList = new ArrayList<>();
        songAdapter = new SongAdapter(mView.getContext(), mMp3InfoList);
        mPresenter = new LocalMusicPresenter();
        mPresenter.attachView(this);
        mPresenter.getLocalMp3Info();

        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
                @Override
                public void onSongClick() {
                    mSong = FileHelper.getSong();
                    Log.d("jsyjsy","----------------"+mSong.getTitle());
                    mSongNameTv.setText(mSong.getTitle());
                    mSinger.setText(mSong.getArtist());
                }
            });

    }

    @Override
    public void showMusicList(final ArrayList<Mp3Info> mp3InfoList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMp3InfoList.addAll(mp3InfoList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext());
                mRecycler.setLayoutManager(layoutManager);
                songAdapter.notifyDataSetChanged();
                mRecycler.setAdapter(songAdapter);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
