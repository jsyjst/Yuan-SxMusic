package com.example.musicplayer.view;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.AlbumSongAdapter;
import com.example.musicplayer.app.BaseUri;
import com.example.musicplayer.app.BroadcastName;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.base.fragment.BaseMvpFragment;
import com.example.musicplayer.callback.OnItemClickListener;
import com.example.musicplayer.contract.IAlbumSongContract;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.presenter.AlbumSongPresenter;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.FileHelper;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.wang.avi.AVLoadingIndicatorView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 残渊 on 2018/11/25.
 */

public class AlbumSongFragment extends BaseMvpFragment<AlbumSongPresenter> implements IAlbumSongContract.View{
    private static final String TYPE_KEY = "type_key";
    public static final int ALBUM_SONG = 0;
    public static final int ALBUM_INFORATION = 1;
    public static final String IS_ONLINE_ALBUM="online_album";

    private AlbumSongPresenter mPresenter;
    private String mId;

    private NestedScrollView mScrollView;
    private TextView mNameTv, mLanguageTv,mDescTv,mCompany,mPublicTimeTv,mTypeTv;
    private int mType;
    private String mPublicTime;
    private String mDesc;

    //用来判断网络问题及加载问题
    private AVLoadingIndicatorView mLoading;
    private TextView mLoadingTv;
    private ImageView mNetworkErrorIv;



    private List<AlbumSong.DataBean.GetSongInfoBean> mSongsList;
    private RecyclerView mRecycle;
    private LinearLayoutManager mLinearManager;
    private AlbumSongAdapter mAdapter;

    private IntentFilter intentFilter;
    private Intent playIntent;
    private AlbumSongChangeReceiver albumSongChangeReceiver;

    private PlayerService.PlayStatusBinder mPlayStatusBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayStatusBinder = (PlayerService.PlayStatusBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void loadData() {
        mPresenter =new AlbumSongPresenter();
        mPresenter.attachView(this);
        mPresenter.getAlbumDetail(mId,mType);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getBundle();
        View view = null;
        if (mType == ALBUM_SONG) {
            view = inflater.inflate(R.layout.fragment_album_recycler, container, false);
            mRecycle = view.findViewById(R.id.normalView);
            LitePal.getDatabase();
        } else {
            view = inflater.inflate(R.layout.fragment_album_song, container, false);
            mScrollView = view.findViewById(R.id.scrollView);
            mDescTv = view.findViewById(R.id.tv_desc);
            mNameTv = view.findViewById(R.id.tv_album_name);
            mLanguageTv = view.findViewById(R.id.tv_language);
            mCompany = view.findViewById(R.id.tv_company);
            mPublicTimeTv = view.findViewById(R.id.tv_public_time);
            mTypeTv = view.findViewById(R.id.tv_album_type);
        }
        mLoading = view.findViewById(R.id.avi);
        mLoadingTv = view.findViewById(R.id.tv_loading);
        mNetworkErrorIv = view.findViewById(R.id.iv_network_error);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle save) {
        super.onActivityCreated(save);
        if(mType==ALBUM_SONG){
            intentFilter=new IntentFilter();
            intentFilter.addAction(BroadcastName.ONLINE_ALBUM_SONG_Change);
            albumSongChangeReceiver = new AlbumSongChangeReceiver();
            getActivity().registerReceiver(albumSongChangeReceiver,intentFilter);
            //启动服务
            playIntent = new Intent(getActivity(), PlayerService.class);
            getActivity().bindService(playIntent, connection, Context.BIND_AUTO_CREATE);
        }else{
            MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);
        }


    }

    @Override
    public void onDestroyView(){
        if(albumSongChangeReceiver!=null){
            Objects.requireNonNull(getActivity()).unregisterReceiver(albumSongChangeReceiver);
        }
        if(playIntent!=null){
            Objects.requireNonNull(getActivity()).unbindService(connection);
        }
        super.onDestroyView();
    }

    private void getBundle(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt(TYPE_KEY);
            mId =bundle.getString(AlbumContentFragment.ALBUM_ID_KEY);
            mPublicTime = bundle.getString(AlbumContentFragment.PUBLIC_TIEM_KEY);
        }
    }

    public static Fragment newInstance(int type, String id,String publicTime) {
        AlbumSongFragment fragment = new AlbumSongFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, type);
        bundle.putString(AlbumContentFragment.ALBUM_ID_KEY,id);
        bundle.putString(AlbumContentFragment.PUBLIC_TIEM_KEY, publicTime);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setAlbumSongList(final List<AlbumSong.DataBean.GetSongInfoBean> songList) {
        mPresenter.insertAllAlbumSong((ArrayList<AlbumSong.DataBean.GetSongInfoBean>) songList);//存到数据库中
        mLinearManager =new LinearLayoutManager(getActivity());
        mRecycle.setLayoutManager(mLinearManager);
        mAdapter =new AlbumSongAdapter(songList);
        mRecycle.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecycle.setAdapter(mAdapter);

        mAdapter.setSongClick(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                AlbumSong.DataBean.GetSongInfoBean dataBean= songList.get(position);
                Song song = new Song();
                song.setSongId(dataBean.getSongmid());
                song.setSinger(getSinger(dataBean));
                song.setSongName(dataBean.getSongname());
                song.setUrl(BaseUri.PLAY_URL+dataBean.getSongmid());
                song.setImgUrl(BaseUri.PIC_URL+dataBean.getSongmid());
                song.setCurrent(position);
                song.setDuration(dataBean.getInterval());
                song.setOnline(true);
                song.setListType(Constant.LIST_TYPE_ONLINE);
                FileHelper.saveSong(song);

                mPlayStatusBinder.play(Constant.LIST_TYPE_ONLINE);

            }
        });
    }

    @Override
    public void showAlbumSongError() {
        CommonUtil.showToast(getActivity(),"获取专辑信息失败");
    }

    @Override
    public void showAlbumMessage(String name, String language, String company, String type,String desc) {
        mNameTv.setText(name);
        mLanguageTv.setText(language);
        mCompany.setText(company);
        mDescTv.setText(desc);
        mPublicTimeTv.setText(mPublicTime);
        mTypeTv.setText(type);
    }

    @Override
    public void showLoading() {
        mLoading.show();
    }

    @Override
    public void hideLoading() {
        mLoading.hide();
        mLoadingTv.setVisibility(View.GONE);
        if(mType==ALBUM_SONG){
            mRecycle.setVisibility(View.VISIBLE);
        }else {
            mScrollView.setVisibility(View.VISIBLE);
        }
        mNetworkErrorIv.setVisibility(View.GONE);
    }

    @Override
    public void showNetError() {
        mLoadingTv.setVisibility(View.GONE);
        mLoading.hide();
        mNetworkErrorIv.setVisibility(View.VISIBLE);
    }

    @Override
    protected AlbumSongPresenter getPresenter() {
        return null;
    }

    class AlbumSongChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mAdapter.notifyDataSetChanged();
        }
    }

    //获取歌手，因为歌手可能有很多个
    private String getSinger(AlbumSong.DataBean.GetSongInfoBean dataBean){
        StringBuilder singer = new StringBuilder(dataBean.getSinger().get(0).getName());
        for (int i = 1; i < dataBean.getSinger().size(); i++) {
            singer.append("、").append(dataBean.getSinger().get(i).getName());
        }
        return singer.toString();
    }
}
