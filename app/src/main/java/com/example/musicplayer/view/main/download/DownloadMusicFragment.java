package com.example.musicplayer.view.main.download;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.R;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/16
 *     desc   : 下载歌曲列表
 * </pre>
 */

public class DownloadMusicFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_music,container,false);
        return view;
    }
}
