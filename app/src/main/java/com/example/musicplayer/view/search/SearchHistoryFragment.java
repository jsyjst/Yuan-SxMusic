package com.example.musicplayer.view.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.SpeedDialog.dialog.SpeedDialog;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SearchHistoryAdapter;
import com.example.musicplayer.callback.*;
import com.example.musicplayer.entiy.SearchHistory;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/11/29.
 */

public class SearchHistoryFragment extends Fragment {

    private RecyclerView mRecycler;
    private SearchHistoryAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<SearchHistory> mSearchHistoryList;
    private List<SearchHistory> mTempList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_history, container, false);
        mRecycler = view.findViewById(R.id.recycler_seek_history);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        showHistory();
        onClick();
    }


    private void showHistory(){
        mSearchHistoryList = new ArrayList<>();
        mTempList = new ArrayList<>();
        changeList();
        mAdapter = new SearchHistoryAdapter(mSearchHistoryList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }
    private void onClick(){
        mAdapter.setFooterClickListener(new OnFooterClickListener() {
            @Override
            public void onClick() {
                SpeedDialog deleteDialog = new SpeedDialog(getActivity());
                deleteDialog.setTitle("删除")
                        .setMessage("确定清空所有搜索历史吗？")
                        .setSureClickListener(dialog -> {
                            //删除数据库中的历史记录
                            LitePal.deleteAll(SearchHistory.class);
                            mRecycler.setVisibility(View.GONE);
                        }).show();
            }
        });
        mAdapter.setOnDeleteClickListener(new OnDeleteClickListener() {
            @Override
            public void onClick(int position) {
                SearchHistory searchHistory = mSearchHistoryList.get(position);
                if(searchHistory.isSaved()){
                    searchHistory.delete();
                }
                mTempList =LitePal.findAll(SearchHistory.class);
                changeList();
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                ((AlbumContentFragment.SearchFragment)(getParentFragment())).setSeekEdit(mSearchHistoryList.get(position).getHistory());
            }
        });
    }
    private void changeList(){
        mSearchHistoryList.clear();
        mTempList = LitePal.findAll(SearchHistory.class);
        if(mTempList.size()==0){
            mRecycler.setVisibility(View.INVISIBLE);
        }else{
            mRecycler.setVisibility(View.VISIBLE);
        }
        for(int i=mTempList.size()-1;i>=0;i--){
            SearchHistory searchHistory = mTempList.get(i);
            mSearchHistoryList.add(searchHistory);
        }
    }
}
