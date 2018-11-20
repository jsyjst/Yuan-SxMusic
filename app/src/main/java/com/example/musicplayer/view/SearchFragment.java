package com.example.musicplayer.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.musicplayer.R;
import com.example.musicplayer.util.CommonUtil;

/**
 * Created by 残渊 on 2018/11/20.
 */

public class SearchFragment extends Fragment {
    private static final String TAG="SearchFragment";
    private EditText mSeekEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        mSeekEdit = view.findViewById(R.id.edit_seek);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        CommonUtil.showKeyboard(mSeekEdit,getActivity());
        Log.d(TAG, "onActivityCreated: true");
    }
}
