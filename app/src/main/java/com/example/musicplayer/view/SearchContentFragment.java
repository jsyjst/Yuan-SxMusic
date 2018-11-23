package com.example.musicplayer.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SearchContentAdapter;
import com.example.musicplayer.contract.ISearchContentContract;
import com.example.musicplayer.entiy.SeachSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.presenter.SearchContentPresenter;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.FileHelper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentFragment extends Fragment implements ISearchContentContract.View{
    private static final String TAG="SearchContentFragment";
    public static final String IS_ONLINE="online";

    private SearchContentPresenter mPresenter;
    private RecyclerView mRecycler;
    private LinearLayoutManager manager;
    private SearchContentAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_content,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mRecycler = getActivity().findViewById(R.id.recycler_song_list);
        manager = new LinearLayoutManager(getActivity());

        mPresenter = new SearchContentPresenter();
        mPresenter.attachView(this);
        mPresenter.search(getSeekContent());

    }

    @Override
    public String getSeekContent() {
        Log.d(TAG, "getSeekContent: "+getArguments().getString("seek"));
        return getArguments().getString("seek").trim();
    }

    @Override
    public void setSongsList(final ArrayList<SeachSong.DataBean> songListBeans) {
        mAdapter = new SearchContentAdapter(songListBeans);
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(mAdapter);

        mAdapter.setItemClick(new SearchContentAdapter.ItemClick() {
            @Override
            public void onClick(int position) {
                SeachSong.DataBean dataBean = songListBeans.get(position);
                Song song= new Song();
                song.setArtist(dataBean.getSinger());
                song.setTitle(dataBean.getName());
                song.setUrl(dataBean.getUrl());
                song.setImgUrl(dataBean.getPic());
                song.setCurrent(FileHelper.getSong()==null?0:FileHelper.getSong().getCurrent());
                FileHelper.saveSong(song);

                Intent intent=new Intent(getActivity(),PlayActivity.class);
                intent.putExtra(IS_ONLINE,true);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showError() {
        CommonUtil.showToast(getActivity(),"连接超时");
    }
}
