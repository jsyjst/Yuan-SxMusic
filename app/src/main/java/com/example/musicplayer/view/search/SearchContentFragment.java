package com.example.musicplayer.view.search;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ImageView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SearchContentAdapter;
import com.example.musicplayer.app.Api;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.base.fragment.BaseLoadingFragment;
import com.example.musicplayer.contract.ISearchContentContract;
import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.event.OnlineSongChangeEvent;
import com.example.musicplayer.event.OnlineSongErrorEvent;
import com.example.musicplayer.presenter.SearchContentPresenter;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.FileHelper;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentFragment extends BaseLoadingFragment<SearchContentPresenter> implements ISearchContentContract.View {
    private static final String TAG = "SearchContentFragment";
    public static final String TYPE_KEY = "type";
    public static final String SEEK_KEY = "seek";
    public static final String IS_ONLINE = "online";
    private int mOffset = 1; //用于翻页搜索



    private SearchContentPresenter mPresenter;

    private LinearLayoutManager manager;
    private SearchContentAdapter mAdapter;
    private ArrayList<SearchSong.DataBean.SongBean.ListBean> mSongList = new ArrayList<>();
    private List<Album.DataBean.AlbumBean.ListBean> mAlbumList;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;//下拉刷新

    @BindView(R.id.normalView)
    LRecyclerView mRecycler;
    @BindView(R.id.iv_background)
    ImageView mBackgroundIv;

    private Bundle mBundle;
    private String mSeek;
    private String mType;

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
        if (mType.equals("song")) {
            mPresenter.search(mSeek, 1);
        } else if (mType.equals("album")) {
            mPresenter.searchAlbum(mSeek, 1);
        }
        searchMore();
    }

    @Override
    public void reload() {
        super.reload();
        if (mType.equals("song")) {
            mPresenter.search(mSeek, 1);
        } else if (mType.equals("album")) {
            mPresenter.searchAlbum(mSeek, 1);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_content;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        mBundle = getArguments();
        if (mBundle != null) {
            mSeek = mBundle.getString(SEEK_KEY);
            mType = mBundle.getString(TYPE_KEY);
        }
        manager = new LinearLayoutManager(mActivity);
        //启动服务
        Intent playIntent = new Intent(getActivity(), PlayerService.class);
        mActivity.bindService(playIntent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected SearchContentPresenter getPresenter() {
        mPresenter = new SearchContentPresenter();
        return mPresenter ;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOnlineSongChangeEvent(OnlineSongChangeEvent event){
        if(mAdapter!= null) mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOnlineSongErrorEvent(OnlineSongErrorEvent event){
        showToast("抱歉该歌曲暂没有版权，搜搜其他歌曲吧");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unbindService(connection);
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void setSongsList(final ArrayList<SearchSong.DataBean.SongBean.ListBean> songListBeans) {
        mSongList.addAll(songListBeans);
        mAdapter = new SearchContentAdapter(mSongList, mSeek, getActivity(), Constant.TYPE_SONG);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(mLRecyclerViewAdapter);

        //点击播放
        SearchContentAdapter.setItemClick(position -> {
            SearchSong.DataBean.SongBean.ListBean dataBean = mSongList.get(position);
            Song song = new Song();
            song.setSongId(dataBean.getSongmid());
            song.setSinger(getSinger(dataBean));
            song.setSongName(dataBean.getSongname());
            song.setImgUrl(Api.ALBUM_PIC+dataBean.getAlbummid()+Api.JPG);
            song.setCurrent(position);
            song.setDuration(dataBean.getInterval());
            song.setOnline(true);
            FileHelper.saveSong(song);
            //网络获取歌曲地址
            mPresenter.getSongUrl(dataBean.getSongmid());
        });
    }

    @Override
    public void searchMoreSuccess(ArrayList<SearchSong.DataBean.SongBean.ListBean> songListBeans) {
        mSongList.addAll(songListBeans);
        mAdapter.notifyDataSetChanged();
        mRecycler.refreshComplete(Constant.OFFSET);
    }

    @Override
    public void searchMoreError() {
        mRecycler.setNoMore(true);
    }

    @Override
    public void searchMore() {

        mRecycler.setPullRefreshEnabled(false);
        mRecycler.setOnLoadMoreListener(() -> {
            mOffset += 1;
            Log.d(TAG, "onLoadMore: mOffset=" + mOffset);
            if (mType.equals("song")) {
                mPresenter.searchMore(mSeek, mOffset);
            } else {
                mPresenter.searchAlbumMore(mSeek, mOffset);
            }
        });
        //设置底部加载颜色
        mRecycler.setFooterViewColor(R.color.colorAccent, R.color.musicStyle_low, R.color.transparent);
        //设置底部加载文字提示
        mRecycler.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

    }

    @Override
    public void showSearcherMoreNetworkError() {
        mRecycler.setOnNetWorkErrorListener(() -> {
            mOffset += 1;
            mPresenter.searchMore(mSeek, mOffset);
        });
    }

    @Override
    public void searchAlbumSuccess(final List<Album.DataBean.AlbumBean.ListBean> albumList) {
        mAlbumList = new ArrayList<>();
        mAlbumList.addAll(albumList);
        mAdapter = new SearchContentAdapter(mAlbumList, mSeek, getActivity(), Constant.TYPE_ALBUM);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(mLRecyclerViewAdapter);
        SearchContentAdapter.setAlbumClick(position -> toAlbumContentFragment(mAlbumList.get(position)));
    }

    @Override
    public void searchAlbumMoreSuccess(List<Album.DataBean.AlbumBean.ListBean> songListBeans) {
        mAlbumList.addAll(songListBeans);
        mAdapter.notifyDataSetChanged();
        mRecycler.refreshComplete(Constant.OFFSET);
    }

    @Override
    public void searchAlbumError() {
        CommonUtil.showToast(getActivity(), "获取专辑信息失败");
    }

    @Override
    public void getSongUrlSuccess(String url) {
        Song song=FileHelper.getSong();
        assert song != null;
        song.setUrl(url);
        FileHelper.saveSong(song);
        mPlayStatusBinder.playOnline();
    }


    /**
     * 构造带参数的fragment
     *
     * @param type 参数
     */
    public static Fragment newInstance(String seek, String type) {
        SearchContentFragment fragment = new SearchContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_KEY, type);
        bundle.putString(SEEK_KEY, seek);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void toAlbumContentFragment(Album.DataBean.AlbumBean.ListBean album) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.slide_in_right, R.anim.slide_out_right);
        transaction.add(R.id.fragment_container, AlbumContentFragment.
                newInstance(album.getAlbumMID(), album.getAlbumName(), album.getAlbumPic(), album.getSingerName(), album.getPublicTime()));
        transaction.hide(this);
        //将事务提交到返回栈
        transaction.addToBackStack(null);
        transaction.commit();
    }


    //获取歌手，因为歌手可能有很多个
    private String getSinger( SearchSong.DataBean.SongBean.ListBean dataBean){
        StringBuilder singer = new StringBuilder(dataBean.getSinger().get(0).getName());
        for (int i = 1; i < dataBean.getSinger().size(); i++) {
            singer.append("、").append(dataBean.getSinger().get(i).getName());
        }
        return singer.toString();
    }
}
