package com.example.musicplayer.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SearchHistoryAdapter;
import com.example.musicplayer.entiy.History;

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
    private List<History> mHistoryList;
    private List<History> mTempList;

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
        mHistoryList = new ArrayList<>();
        mTempList = new ArrayList<>();
        changeList();
        mAdapter = new SearchHistoryAdapter(mHistoryList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }
    private void onClick(){
        mAdapter.setFooterClickListener(new SearchHistoryAdapter.OnFooterClickListener() {
            @Override
            public void onClick() {
                LitePal.deleteAll(History.class);
                mRecycler.setVisibility(View.GONE);
            }
        });
        mAdapter.setOnDeleteClickListener(new SearchHistoryAdapter.OnDeleteClickListener() {
            @Override
            public void onClick(int position) {
                History history =mTempList.get(position);
                LitePal.deleteAll(History.class,"history = ?",history.getHistory());
                mTempList =LitePal.findAll(History.class);
                changeList();
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.setOnItemClcikListener(new SearchHistoryAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                ((SearchFragment)(getParentFragment())).setSeekEdit(mHistoryList.get(position).getHistory());
            }
        });
    }
    private void changeList(){
        mHistoryList.clear();
        mTempList = LitePal.findAll(History.class);
        if(mTempList.size()==0){
            mRecycler.setVisibility(View.INVISIBLE);
        }else{
            mRecycler.setVisibility(View.VISIBLE);
        }
        for(int i=mTempList.size()-1;i>=0;i--){
            History history = mTempList.get(i);
            mHistoryList.add(history);
        }
    }

}
