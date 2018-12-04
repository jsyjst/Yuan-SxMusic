package com.example.musicplayer.view;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.ExpandableListViewAdapter;
import com.example.musicplayer.adapter.HistoryAdapter;
import com.example.musicplayer.constant.BroadcastName;
import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.AlbumCollection;
import com.example.musicplayer.entiy.HistorySong;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.entiy.Love;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.widget.MyListView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private LinearLayout mFunctionLinear;

    private MyListView myListView;
    private ExpandableListViewAdapter mAdapter;
    private LinearLayout mLocalMusicLinear, mCollectionLinear, mHistoryMusicLinear;
    private Button playerBtn;
    private TextView mLocalMusicNum, mLoveMusicNum, mHistoryMusicNum;

    private TextView mSeekBtn;
    private List<List<AlbumCollection>> mAlbumCollectionList;
    private List<AlbumCollection> mLoveAlbumList;
    private boolean oneExpand;
    private boolean twoExpand;
    private String[] mGroupStrings = {"自建歌单", "收藏歌单"};
    private String[][] mSongStrings = {
            {"我喜欢"},
            {"Jay", "魔杰座"}
    };
    //注册广播
    private IntentFilter intentFilter;
    private SongChangeReceiver songChangeReceiver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mLocalMusicLinear = view.findViewById(R.id.linear_local_music);
        mCollectionLinear = view.findViewById(R.id.linear_collection);
        playerBtn = view.findViewById(R.id.btn_player);
        mFunctionLinear = view.findViewById(R.id.linear_function);
        mHistoryMusicLinear = view.findViewById(R.id.linear_history);
        //获取焦点
        mFunctionLinear.setFocusableInTouchMode(true);
        myListView = view.findViewById(R.id.expand_lv_song_list);
        mSeekBtn = view.findViewById(R.id.tv_seek);
        mLocalMusicNum = view.findViewById(R.id.tv_local_music_num);
        mLoveMusicNum = view.findViewById(R.id.tv_love_num);
        mHistoryMusicNum = view.findViewById(R.id.tv_history_num);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLoveAlbumList = new ArrayList<>();
        mAlbumCollectionList = new ArrayList<>();
        AlbumCollection albumCollection = new AlbumCollection();
        albumCollection.setAlbumName("我喜欢");
        albumCollection.setSingerName("袁健策");
        mLoveAlbumList.add(albumCollection);
        mAlbumCollectionList.add(mLoveAlbumList);
        mAlbumCollectionList.add(orderCollection(LitePal.findAll(AlbumCollection.class)));
        mAdapter = new ExpandableListViewAdapter(getActivity(), mGroupStrings, mAlbumCollectionList);
        myListView.setAdapter(mAdapter);
        //注册广播
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastName.SONG_CHANGE);
        intentFilter.addAction(BroadcastName.COLLECTION_ALBUM_CHANGE);
        songChangeReceiver = new SongChangeReceiver();
        getActivity().registerReceiver(songChangeReceiver, intentFilter);
        onClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(songChangeReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        showMusicNum();
        Log.d(TAG, "onResume: true");
    }

    private void onClick() {
        mLocalMusicLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new LocalMusicFragment());
            }
        });

        mSeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SearchFragment());
            }
        });

        mCollectionLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CollectionFragment());
            }
        });

        mHistoryMusicLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HistoryFragment());
            }
        });
        myListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition == 0) {
                    oneExpand = true;
                } else if (groupPosition == 1) {
                    twoExpand = true;
                }
            }
        });
        myListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                if (groupPosition == 0) {
                    oneExpand = false;
                } else if (groupPosition == 1) {
                    twoExpand = false;
                }
            }
        });
        myListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        mAdapter.setOnChildItemClickListener(new ExpandableListViewAdapter.OnChildItemClickListener() {
            @Override
            public void onClick(int groupPosition, int childPosition) {
                if (groupPosition == 0 && childPosition == 0) {
                    replaceFragment(new CollectionFragment());
                } else if (groupPosition == 1) {
                    AlbumCollection albumCollection = mAlbumCollectionList.get(groupPosition).get(childPosition);
                    replaceFragment(AlbumContentFragment.newInstance(
                            albumCollection.getAlbumId(),
                            albumCollection.getAlbumName(),
                            albumCollection.getAlbumPic(),
                            albumCollection.getSingerName(),
                            albumCollection.getPublicTime()
                    ));
                }
            }
        });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //进入和退出动画
        transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.slide_in_right, R.anim.slide_out_right);
        transaction.add(R.id.fragment_container, fragment);
        transaction.hide(this);
        //将事务提交到返回栈
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showMusicNum() {
        mLoveMusicNum.setText("" + LitePal.findAll(LocalSong.class).size());
        mLoveMusicNum.setText("" + LitePal.findAll(Love.class).size());
        mHistoryMusicNum.setText("" + LitePal.findAll(HistorySong.class).size());

    }

    private List<AlbumCollection> orderCollection(List<AlbumCollection> tempList) {
        List<AlbumCollection> mAlbumCollectionList = new ArrayList<>();
        for (int i = tempList.size() - 1; i >= 0; i--) {
            mAlbumCollectionList.add(tempList.get(i));
        }
        return mAlbumCollectionList;
    }

    private class SongChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadcastName.SONG_CHANGE)) {
                mHistoryMusicNum.setText("" + LitePal.findAll(HistorySong.class).size());
            } else if (action.equals(BroadcastName.COLLECTION_ALBUM_CHANGE)) {
                mAlbumCollectionList.clear();
                mAlbumCollectionList.add(mLoveAlbumList);
                mAlbumCollectionList.add(orderCollection(LitePal.findAll(AlbumCollection.class)));
                if (twoExpand) {
                    myListView.collapseGroup(1);
                    myListView.expandGroup(1);
                } else {
                    myListView.expandGroup(1);
                    myListView.collapseGroup(1);
                }
            }

        }
    }

}
