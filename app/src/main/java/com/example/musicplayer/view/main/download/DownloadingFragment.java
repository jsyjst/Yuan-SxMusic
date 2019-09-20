package com.example.musicplayer.view.main.download;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.SpeedDialog.dialog.SpeedDialog;
import com.example.SpeedDialog.listener.OnSelectClickListener;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.DownloadingAdapter;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.entiy.DownloadInfo;
import com.example.musicplayer.event.DownloadEvent;
import com.example.musicplayer.service.DownloadService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
    private static final String TAG = "DownloadingFragment";

    @BindView(R.id.songDowningRecycle)
    RecyclerView songDowningRecycle;
    Unbinder unbinder;
    private DownloadingAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<DownloadInfo> mDownloadInfoList;  //下载队列
    private List<String> mDownloadSongId; //用来判断当前下载的歌曲
    private HashMap<String,Integer> mSongPositionMap;//歌曲Id获取歌曲位置
    private boolean isTouch;

    private DownloadService.DownloadBinder mDownloadBinder;
    //绑定下载服务
    private ServiceConnection mDownloadConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mDownloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

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
        //绑定服务
        Intent downIntent = new Intent(getActivity(), DownloadService.class);
        getActivity().bindService(downIntent, mDownloadConnection, Context.BIND_AUTO_CREATE);
        super.onActivityCreated(savedInstanceState);
    }

    private void initRecycler() {
        mDownloadInfoList = new LinkedList<>();
        mDownloadSongId = new ArrayList<>();
        mDownloadSongId.add("");
        mDownloadInfoList.addAll(orderList(LitePal.findAll(DownloadInfo.class,true)));
        songDowningRecycle.setItemAnimator(null);  //解决进度刷新闪屏问题
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new DownloadingAdapter(mDownloadInfoList,mDownloadSongId);
        songDowningRecycle.setLayoutManager(mLinearLayoutManager);
        songDowningRecycle.setAdapter(mAdapter);

        //暂停
        mAdapter.setOnItemClickListener(position -> {
            DownloadInfo downloadInfo = mDownloadInfoList.get(position);
            if(downloadInfo.getSongId().equals(mDownloadSongId.get(0))){
                mDownloadBinder.pauseDownload();
            }else {
                mDownloadBinder.startDownload(mDownloadInfoList.get(position).getSong());
            }
        });

        //取消下载
        mAdapter.setOnDeleteClickListener(position -> {
            SpeedDialog speedDialog = new SpeedDialog(getActivity(),SpeedDialog.SELECT_TYPE);
            speedDialog.setTitle(getString(R.string.download_cancel))
                    .setMessage(getString(R.string.download_cancel_message))
                    .setSureText(getString(R.string.download_sure))
                    .setSureClickListener(dialog -> {
                        mDownloadBinder.cancelDownload(mDownloadInfoList.get(position).getUrl(),mDownloadInfoList.get(position).getSongId());
                    })
            .show();
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadingMessage(DownloadEvent event) {
        int status = event.getDownloadStatus();
        if (status == Constant.TYPE_DOWNLOADING) {
            mDownloadSongId.clear();
            mDownloadSongId.add(event.getDownloadInfo().getSongId());
            ((LinkedList)mDownloadInfoList).poll();
            ((LinkedList)mDownloadInfoList).addFirst(event.getDownloadInfo());
            mAdapter.notifyItemChanged(0);
        }else if(status == Constant.TYPE_DOWNLOAD_SUCCESS){
            mDownloadInfoList.clear();
            mDownloadInfoList.addAll(orderList(LitePal.findAll(DownloadInfo.class,true)));
            mAdapter.notifyDataSetChanged();
        }else if(status == Constant.TYPE_DOWNLOAD_PAUSED){
            mDownloadSongId.clear();
            mDownloadSongId.add("");
            mAdapter.notifyDataSetChanged();
        }else if(status == Constant.TYPE_DOWNLOAD_CANCELED){
            mDownloadInfoList.clear();
            mDownloadInfoList.addAll(orderList(LitePal.findAll(DownloadInfo.class,true)));
            mAdapter.notifyDataSetChanged();
        }
    }


    private List<DownloadInfo> orderList(List<DownloadInfo> tempList){
        List<DownloadInfo> downloadInfos=new ArrayList<>();
        downloadInfos.clear();
        for(int i=tempList.size()-1;i>=0;i--){
            downloadInfos.add(tempList.get(i));
        }
        return downloadInfos;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}
