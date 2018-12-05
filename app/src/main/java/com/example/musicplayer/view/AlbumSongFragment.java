package com.example.musicplayer.view;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.AlbumSongAdapter;
import com.example.musicplayer.adapter.SearchContentAdapter;
import com.example.musicplayer.constant.BroadcastName;
import com.example.musicplayer.constant.Constant;
import com.example.musicplayer.contract.IAlbumSongContract;
import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.entiy.SeachSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.presenter.AlbumSongPresenter;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.FileHelper;
import com.example.musicplayer.util.RxApiManager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.wang.avi.AVLoadingIndicatorView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicplayer.view.SearchContentFragment.IS_ONLINE;

/**
 * Created by 残渊 on 2018/11/25.
 */

public class AlbumSongFragment extends Fragment implements IAlbumSongContract.View{
    private static final String TYPE_KEY = "type_key";
    public static final int ALBUM_SONG = 0;
    public static final int ALBUM_INFORATION = 1;
    public static final String IS_ONLINE_ALBUM="online_album";

    private AlbumSongPresenter mPresenter;
    private String mId;

    private NestedScrollView mScrollView;
    private TextView mNameTv,mSingerTv,mDescTv,mCompany,mPublicTimeTv;
    private int mType;
    private String mPublicTime;
    private String mDesc;

    //用来判断网络问题及加载问题
    private AVLoadingIndicatorView mLoading;
    private TextView mLoadingTv;
    private ImageView mNetworkErrorIv;



    private List<AlbumSong.DataBean.SongsBean> mSongsList;
    private RecyclerView mRecycle;
    private LinearLayoutManager mLinearManager;
    private AlbumSongAdapter mAdapter;

    private IntentFilter intentFilter;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getBundle();
        View view = null;
        if (mType == ALBUM_SONG) {
            view = inflater.inflate(R.layout.fragment_album_recycler, container, false);
            mRecycle = view.findViewById(R.id.recycler_song_list);
            mLoading = view.findViewById(R.id.avi);
            mLoadingTv = view.findViewById(R.id.tv_loading);
            mNetworkErrorIv = view.findViewById(R.id.iv_network_error);
            LitePal.getDatabase();
        } else {
            view = inflater.inflate(R.layout.fragment_album_song, container, false);
            mScrollView = view.findViewById(R.id.scrollView);
            mDescTv = view.findViewById(R.id.tv_desc);
            mNameTv = view.findViewById(R.id.tv_album_name);
            mSingerTv = view.findViewById(R.id.tv_singer);
            mCompany = view.findViewById(R.id.tv_company);
            mPublicTimeTv = view.findViewById(R.id.tv_public_time);

        }

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
            Intent playIntent = new Intent(getActivity(), PlayerService.class);
            getActivity().bindService(playIntent, connection, Context.BIND_AUTO_CREATE);
        }else{
            MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);
        }

        mPresenter =new AlbumSongPresenter();
        mPresenter.attachView(this);
        mPresenter.getAlbumDetail(mId,mType);
    }

    @Override
    public void onDestroyView(){
        RxApiManager.get().cancel(Constant.ALBUM);
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
    public void setAlbumSongList(final List<AlbumSong.DataBean.SongsBean> songList) {
        mPresenter.insertAllAlbumSong((ArrayList<AlbumSong.DataBean.SongsBean>) songList);//存到数据库中
        mLinearManager =new LinearLayoutManager(getActivity());
        mRecycle.setLayoutManager(mLinearManager);
        mAdapter =new AlbumSongAdapter(songList);
        mRecycle.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecycle.setAdapter(mAdapter);

        mAdapter.setSongClick(new AlbumSongAdapter.SongClick() {
            @Override
            public void onClick(int position) {
                AlbumSong.DataBean.SongsBean dataBean= songList.get(position);
                Song song = new Song();
                song.setOnlineId(dataBean.getId());
                song.setSinger(dataBean.getSinger());
                song.setSongName(dataBean.getName());
                song.setUrl(dataBean.getUrl());
                song.setImgUrl(dataBean.getPic());
                song.setCurrent(position);
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
    public void showAlbumMessage(String name, String singer, String company, String desc) {
        mNameTv.setText(name);
        mSingerTv.setText(singer);
        mCompany.setText(company);
        mDescTv.setText(desc);
        mPublicTimeTv.setText(mPublicTime);
    }

    @Override
    public void showLoading() {
        mLoading.show();
    }

    @Override
    public void hideLoading() {
        mLoading.hide();
        mLoadingTv.setVisibility(View.GONE);
        mRecycle.setVisibility(View.VISIBLE);
        mNetworkErrorIv.setVisibility(View.GONE);
    }

    @Override
    public void showNetError() {
        mLoadingTv.setVisibility(View.GONE);
        mLoading.hide();
        mNetworkErrorIv.setVisibility(View.VISIBLE);
    }

    class AlbumSongChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
