package com.example.musicplayer.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.callback.OnDeleteClickListener;
import com.example.musicplayer.callback.OnItemClickListener;
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
    private List<String> downloadSongId;

    private OnItemClickListener onItemClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener){
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DownloadingAdapter(List<DownloadInfo> downloadInfoList,List<String> downloadSongId) {
        this.downloadInfoList = downloadInfoList;
        this.downloadSongId = downloadSongId;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView songTv;
        TextView sizeTv;
        SeekBar seekBar;
        View itemView;
        ImageView canacleIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            songTv = itemView.findViewById(R.id.songTv);
            sizeTv = itemView.findViewById(R.id.sizeTv);
            seekBar = itemView.findViewById(R.id.seekBar);
            canacleIv = itemView.findViewById(R.id.cancelIv);
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

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (downloadInfoList.size() == 0) return;
        DownloadInfo downloadInfo = downloadInfoList.get(i);
        holder.songTv.setText(downloadInfo.getSongName());
        if (downloadSongId.size()!=0&&downloadSongId.get(0).equals(downloadInfo.getSongId())) {//如果当前歌曲正在下载
            holder.sizeTv.setText(
                    MediaUtil.formatSize(downloadInfo.getCurrentSize()) + "M"
                            + " / "
                            + MediaUtil.formatSize(downloadInfo.getTotalSize()) + "M");
            holder.seekBar.setVisibility(View.VISIBLE);
        }else {//当前歌曲并未下载
            holder.sizeTv.setText(downloadInfo.getSinger());
            holder.seekBar.setVisibility(View.GONE);
        }
        holder.seekBar.setOnTouchListener((view, motionEvent) -> true); //消费该事件，让seekBar不能拖动和点击
        holder.seekBar.setProgress(downloadInfo.getProgress());
        //点击事件
        holder.itemView.setOnClickListener(view -> {
            if(!downloadSongId.get(0).equals(downloadInfo.getSongId())) holder.sizeTv.setText("正在获取歌曲大小");
            onItemClickListener.onClick(i);
        });
        //取消
        holder.canacleIv.setOnClickListener(view -> onDeleteClickListener.onClick(i));


    }

    @Override
    public int getItemCount() {
        return downloadInfoList.size();
    }
}
