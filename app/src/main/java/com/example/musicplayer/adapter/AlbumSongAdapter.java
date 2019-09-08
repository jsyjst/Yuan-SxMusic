package com.example.musicplayer.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.musicplayer.R;
import com.example.musicplayer.callback.OnItemClickListener;
import com.example.musicplayer.app.App;
import com.example.musicplayer.entiy.AlbumSong;
import com.example.musicplayer.util.FileUtil;

import java.util.List;

/**
 * Created by 残渊 on 2018/11/27.
 */

public class AlbumSongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AlbumSong.DataBean.ListBean> mSongsBeanList;
    private int mLastPosition = -1;
    private OnItemClickListener mSongClick;
    private final int songType = 1;
    private final int footerType = 2;

    public AlbumSongAdapter(List<AlbumSong.DataBean.ListBean> songsBeans) {
        mSongsBeanList = songsBeans;
    }

    public void setSongClick(OnItemClickListener songClick) {
        mSongClick = songClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == songType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_song_search_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }else{
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_view_player_height, parent, false);
            FooterHolder footerHolder = new FooterHolder(view);
            return footerHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            AlbumSong.DataBean.ListBean songsBean = mSongsBeanList.get(position);
            //设置歌手，因为歌手可能有两个
            StringBuilder singer = new StringBuilder(songsBean.getSinger().get(0).getName());
            for (int i = 1; i < songsBean.getSinger().size(); i++) {
                singer.append("、").append(songsBean.getSinger().get(i).getName());
            }
            holder.artistTv.setText(singer.toString());
            holder.titleTv.setText(songsBean.getSongname());
            holder.mItemView.setBackgroundResource(R.color.translucent);
            //根据点击显示
                if(songsBean.getSongmid().equals(FileUtil.getSong().getSongId())){
                holder.playLine.setVisibility(View.VISIBLE);
                holder.titleTv.setTextColor(App.getContext().getResources().getColor(R.color.yellow));
                holder.artistTv.setTextColor(App.getContext().getResources().getColor(R.color.yellow));
                mLastPosition = position;
            }else{
                holder.playLine.setVisibility(View.INVISIBLE);
                holder.titleTv.setTextColor(App.getContext().getResources().getColor(R.color.white));
                holder.artistTv.setTextColor(App.getContext().getResources().getColor(R.color.white_blue));
            }
            holder.mItemView.setOnRippleCompleteListener(rippleView -> {
                mSongClick.onClick(position);
                equalPosition(position);
            });
        }
    }


    @Override
    public int getItemCount() {
        return mSongsBeanList.size() + 1;
    }
    @Override
    public int getItemViewType(int position) {
        return position + 1 == getItemCount() ? footerType : songType;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv;
        TextView artistTv;
        RippleView mItemView;
        View playLine;

        ViewHolder(View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_title);
            artistTv = itemView.findViewById(R.id.tv_artist);
            playLine = itemView.findViewById(R.id.line_play);
            mItemView = itemView.findViewById(R.id.ripple);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {
        FooterHolder(View itemView) {
            super(itemView);
        }
    }

    //判断点击的是否为上一个点击的项目
    private void equalPosition(int position) {
        if (position != mLastPosition) {
            if (mLastPosition != -1) notifyItemChanged(mLastPosition);
            mLastPosition = position;
        }
        notifyItemChanged(position);
    }
}
