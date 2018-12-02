package com.example.musicplayer.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.entiy.SearchHistory;
import com.example.musicplayer.util.CommonUtil;

import org.litepal.LitePal;

import java.util.List;

/**
 * Created by 残渊 on 2018/11/20.
 */

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private EditText mSeekEdit;
    private TextView mSeekTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mSeekEdit = view.findViewById(R.id.edit_seek);
        mSeekTv = view.findViewById(R.id.tv_search);
        replaceFragment(new SearchHistoryFragment());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        CommonUtil.showKeyboard(mSeekEdit, getActivity());
        mSeekTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.closeKeybord(mSeekEdit, getActivity());
                mSeekEdit.setCursorVisible(false);//隐藏光标
                saveDatabase(mSeekEdit.getText().toString());
                replaceFragment(ContentFragment.newInstance(mSeekEdit.getText().toString()));
            }
        });
        mSeekEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    mSeekEdit.setCursorVisible(true);
                }
                return false;
            }
        });
    }

    private void saveDatabase(String seekHistory) {
        List<SearchHistory> searchHistoryList = LitePal.where("history=?", seekHistory).find(SearchHistory.class);
        if (searchHistoryList.size() == 1) {
            LitePal.delete(SearchHistory.class, searchHistoryList.get(0).getId());
        }
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setHistory(seekHistory);
        searchHistory.save();

    }
    public void setSeekEdit(String seek){
        mSeekEdit.setText(seek);
        mSeekEdit.setCursorVisible(false);//隐藏光标
        mSeekEdit.setSelection(seek.length());
        CommonUtil.closeKeybord(mSeekEdit, getActivity());
        saveDatabase(seek);
        replaceFragment(ContentFragment.newInstance(mSeekEdit.getText().toString()));
    }

    //搜索后的页面
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: true");
    }

}
