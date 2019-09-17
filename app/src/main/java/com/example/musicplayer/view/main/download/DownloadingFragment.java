package com.example.musicplayer.view.main.download;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.DownloadingAdapter;
import com.example.musicplayer.entiy.DownloadInfo;
import com.example.musicplayer.event.DownloadEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/16
 *     desc   : 正在下载歌曲列表
 * </pre>
 */

public class DownloadingFragment extends Fragment {
    private static final String TAG ="DownloadingFragment";

    @BindView(R.id.songDowningRecycle)
    RecyclerView songDowningRecycle;
    Unbinder unbinder;
    private DownloadingAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<DownloadInfo> mDownloadInfoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_ing, container, false);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initRecycler();
        super.onActivityCreated(savedInstanceState);
    }

    private void initRecycler(){
        mDownloadInfoList = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new DownloadingAdapter(mDownloadInfoList);
        songDowningRecycle.setLayoutManager(mLinearLayoutManager);
        songDowningRecycle.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDownloadingMessage(DownloadEvent event) {
        mDownloadInfoList.clear();
        mDownloadInfoList.add(event.getDownloadInfo());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}
