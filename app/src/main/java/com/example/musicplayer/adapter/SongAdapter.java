package com.example.musicplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.constant.Constant;
import com.example.musicplayer.constant.MyApplication;
import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.R;
import com.example.musicplayer.entiy.LocalSong;
import com.example.musicplayer.util.FileHelper;

import java.util.List;

/**
 * Created by 残渊 on 2018/10/10.
 */

public class SongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "SongAdapter";
    private int footerViewType = 1;
    private int itemViewType = 0;
    private List<LocalSong> mMp3InfoList;
    private Context mContext;
    private int mLastPosition = -1;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SongAdapter(Context context, List<LocalSong> mp3InfoList) {
        mContext = context;
        mMp3InfoList = mp3InfoList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView songNameTv;
        TextView artistTv;
        ImageView playingIv;
        View songView;

        public ViewHolder(View itemView) {
            super(itemView);
            songView = itemView;
            songNameTv = itemView.findViewById(R.id.tv_song_name);
            artistTv = itemView.findViewById(R.id.tv_artist);
            playingIv = itemView.findViewById(R.id.iv_playing);
        }
    }

    /**
     * 底部holder
     */
    static class FooterHolder extends RecyclerView.ViewHolder {

        TextView numTv;

        public FooterHolder(View itemView) {
            super(itemView);
            numTv = itemView.findViewById(R.id.tv_song_num);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == itemViewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_song_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else {
            View footerView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_local_songs_item, parent, false);
            FooterHolder footerHolder = new FooterHolder(footerView);
            return footerHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            final LocalSong mp3Info = mMp3InfoList.get(position);

            holder.songNameTv.setText(mp3Info.getName());
            holder.artistTv.setText(mp3Info.getSinger());
            //根据播放的歌曲是否为当前列表的歌曲显示
            if(mp3Info.getSongId().equals(FileHelper.getSong().getOnlineId())){
                holder.songNameTv.setTextColor(MyApplication.getContext().
                        getResources().getColor(R.color.musicStyle_low));
                holder.artistTv.setTextColor(MyApplication.getContext().
                        getResources().getColor(R.color.musicStyle_low));
                holder.playingIv.setVisibility(View.VISIBLE);
            }else {
                holder.songNameTv.setTextColor(MyApplication.getContext().
                        getResources().getColor(R.color.white));
                holder.artistTv.setTextColor(MyApplication.getContext().
                        getResources().getColor(R.color.white));
                holder.playingIv.setVisibility(View.GONE);
            }
            holder.songView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //将点击的序列化到本地
                    Song song = new Song();
                    song.setSongName(mp3Info.getName());
                    song.setSinger(mp3Info.getSinger());
                    song.setUrl(mp3Info.getUrl());
                    song.setDuration(mp3Info.getDuration());
                    song.setCurrent(position);
                    song.setOnline(false);
                    song.setOnlineId(mp3Info.getSongId());
                    song.setListType(Constant.LIST_TYPE_LOCAL);
                    FileHelper.saveSong(song);

                    onItemClickListener.onSongClick();
                    equalPosition(position);
                }
            });
        } else {
            FooterHolder footerHolder = (FooterHolder) viewHolder;
            int num=mMp3InfoList.size()-1;
            footerHolder.numTv.setText("共" + num + "首音乐");
        }
    }

    //判断点击的是否为上一个点击的项目
    public void equalPosition(int position) {
        if (position != mLastPosition) {
            notifyItemChanged(mLastPosition);
            mLastPosition = position;
        }
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mMp3InfoList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position + 1 == getItemCount() ? footerViewType : itemViewType;
    }


    public interface OnItemClickListener {
        void onSongClick();
    }

}
