package com.example.musicplayer.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.util.CommonUtil;

/**
 * Created by 残渊 on 2018/11/20.
 */

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private EditText mSeekEdit;
    private TextView mSeekTv;
    private SearchContentFragment mSearchContentFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mSeekEdit = view.findViewById(R.id.edit_seek);
        mSeekTv = view.findViewById(R.id.tv_search);
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
                replaceFragment();
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

    //搜索后的页面
    private void replaceFragment() {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mSearchContentFragment = new SearchContentFragment();
        Bundle bundle = new Bundle();
        Log.d(TAG, "seek:" + mSeekEdit.getText().toString());
        bundle.putString("seek", mSeekEdit.getText().toString());
        mSearchContentFragment.setArguments(bundle);
        transaction.replace(R.id.container, mSearchContentFragment);
        transaction.commit();
    }
}
