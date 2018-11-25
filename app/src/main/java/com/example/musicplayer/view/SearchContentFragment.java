package com.example.musicplayer.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.SyncStateContract;
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
import com.example.musicplayer.constant.BroadcastName;
import com.example.musicplayer.constant.Constant;
import com.example.musicplayer.contract.ISearchContentContract;
import com.example.musicplayer.entiy.SeachSong;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.presenter.SearchContentPresenter;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.FileHelper;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentFragment extends Fragment implements ISearchContentContract.View{
    private static final String TAG="SearchContentFragment";
    public static final String IS_ONLINE="online";
    private int mOffset=1; //用于翻页搜索

    private SearchContentPresenter mPresenter;
    private LRecyclerView mRecycler;
    private LinearLayoutManager manager;
    private SearchContentAdapter mAdapter;
    private ArrayList<SeachSong.DataBean> mSongList=new ArrayList<>();
    private SongFinishReceiver songChangeReceiver;
    private IntentFilter intentFilter;

    private LRecyclerViewAdapter mLRecyclerViewAdapter;//下拉刷新

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
        mPresenter.search(getSeekContent(),1);

        //注册广播
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastName.ONLINE_SONG_FINISH);
        SongFinishReceiver songFinishReceiver = new SongFinishReceiver();
        getActivity().registerReceiver(songFinishReceiver,intentFilter);

        searchMore();

    }

    @Override
    public String getSeekContent() {
        Log.d(TAG, "getSeekContent: "+getArguments().getString("seek"));
        return getArguments().getString("seek").trim();
    }

    @Override
    public void setSongsList(final ArrayList<SeachSong.DataBean> songListBeans) {
        mSongList.addAll(songListBeans);
        mAdapter = new SearchContentAdapter(mSongList,getSeekContent(),getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(mLRecyclerViewAdapter);




        mAdapter.setItemClick(new SearchContentAdapter.ItemClick() {
            @Override
            public void onClick(int position) {
                SeachSong.DataBean dataBean = mSongList.get(position);
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
    public void searchMoreSuccess(ArrayList<SeachSong.DataBean> songListBeans) {
        Log.d(TAG, "searchMoreSuccess: success="+songListBeans.size());
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
        mRecycler.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mOffset += 1;
                Log.d(TAG, "onLoadMore: mOffset="+mOffset);
                mPresenter.searchMore(getSeekContent(),mOffset);
            }
        });
        //设置底部加载颜色
        mRecycler.setFooterViewColor(R.color.colorAccent, R.color.musicStyle_low ,R.color.translucent);
        //设置底部加载文字提示
        mRecycler.setFooterViewHint("拼命加载中","已经全部为你呈现了","网络不给力啊，点击再试一次吧");

    }

    @Override
    public void showError() {
        CommonUtil.showToast(getActivity(),"连接超时");
    }

    @Override
    public void showSearcherMoreNetworkError() {
        mRecycler.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
            @Override
            public void reload() {
                mOffset += 1;
                mPresenter.searchMore(getSeekContent(),mOffset);
            }
        });
    }
    class SongFinishReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
