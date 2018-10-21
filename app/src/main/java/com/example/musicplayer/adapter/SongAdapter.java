package com.example.musicplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.entiy.Song;
import com.example.musicplayer.service.PlayerService;
import com.example.musicplayer.R;
import com.example.musicplayer.constant.PlayerStatus;
import com.example.musicplayer.entiy.Mp3Info;
import com.example.musicplayer.util.FileHelper;

import java.util.List;

/**
 * Created by 残渊 on 2018/10/10.
 */

public class SongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int footerViewType = 1;
    private int itemViewType = 0;
    private List<Mp3Info> mMp3InfoList;
    private Context mContext;
    private int mLastPosition = -1;
    private Song song=new Song();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public SongAdapter(Context context, List<Mp3Info> mp3InfoList) {
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
            TypedValue typedValue = new TypedValue();
            mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
            view.setBackgroundResource(typedValue.resourceId);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else {
            View footerView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_local_songs_footer, parent, false);
            FooterHolder footerHolder = new FooterHolder(footerView);
            return footerHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder=(ViewHolder)viewHolder;
            final Mp3Info mp3Info = mMp3InfoList.get(position);

            holder.songNameTv.setText(mp3Info.getTitle());
            holder.artistTv.setText(mp3Info.getArtist());
            holder.playingIv.setVisibility(position == mLastPosition ? View.VISIBLE : View.GONE);
            holder.songView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //将点击的序列化到本地
                    song.setArtist(mp3Info.getArtist());
                    song.setDuration(mp3Info.getDuration());
                    song.setSize(mp3Info.getSize());
                    song.setTitle(mp3Info.getTitle());
                    song.setUrl(mp3Info.getUrl());

                    Log.d("jsysjt","------"+song.getArtist()+"/"+song.getTitle()+"/"+song.getDuration()+"/"+song.getSize());
                    FileHelper.saveSong(song);
                    equalPosition(position);
                    onItemClickListener.onSongClick();



                }
            });
        }else{
            FooterHolder footerHolder=(FooterHolder)viewHolder;
            footerHolder.numTv.setText("共"+mMp3InfoList.size()+"首音乐");
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


    public interface OnItemClickListener{
        void onSongClick();
    }

}
