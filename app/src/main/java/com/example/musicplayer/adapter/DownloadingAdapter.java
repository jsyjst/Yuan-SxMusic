package com.example.musicplayer.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.entiy.DownloadInfo;
import com.example.musicplayer.util.MediaUtil;

import java.util.List;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/17
 *     desc   : 正在下载歌曲适配器
 * </pre>
 */

public class DownloadingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DownloadInfo> downloadInfoList;

    public DownloadingAdapter(List<DownloadInfo> downloadInfoList) {
        this.downloadInfoList = downloadInfoList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView songTv;
        TextView currentSizeTv;
        TextView totalSizeTv;
        SeekBar seekBar;
        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            songTv = itemView.findViewById(R.id.songTv);
            currentSizeTv = itemView.findViewById(R.id.currentSizeTv);
            totalSizeTv = itemView.findViewById(R.id.totalSizeTv);
            seekBar = itemView.findViewById(R.id.seekBar);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_downing_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if(downloadInfoList.size() == 0) return;
        DownloadInfo downloadInfo = downloadInfoList.get(i);
        holder.itemView.setVisibility(downloadInfo.getProgress() == 100 ? View.GONE : View.VISIBLE);
        holder.songTv.setText(downloadInfo.getSongName());
        holder.currentSizeTv.setText(MediaUtil.formatSize(downloadInfo.getCurrentSize()) + "M");
        holder.totalSizeTv.setText(MediaUtil.formatSize(downloadInfo.getTotalSize()) + "M");
        holder.seekBar.setProgress(downloadInfo.getProgress());
    }

    @Override
    public int getItemCount() {
        return downloadInfoList.size();
    }
}
