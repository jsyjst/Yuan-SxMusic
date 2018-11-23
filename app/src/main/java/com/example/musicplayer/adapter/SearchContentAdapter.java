package com.example.musicplayer.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.entiy.SeachSong;

import java.util.ArrayList;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentAdapter extends RecyclerView.Adapter<SearchContentAdapter.ViewHolder> {
    private ArrayList<SeachSong.DataBean> mSongListBeans;
    private static ItemClick mItemClick;

    public static void setItemClick(ItemClick itemClick){
        mItemClick = itemClick;
    }

    public SearchContentAdapter(ArrayList<SeachSong.DataBean> songListBeans){
        mSongListBeans = songListBeans;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_song_search_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        SeachSong.DataBean songListBean = mSongListBeans.get(position);
        holder.artistTv.setText(songListBean.getSinger());
        holder.titleTv.setText(songListBean.getName());
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongListBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        TextView artistTv;
        View mItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_title);
            artistTv = itemView.findViewById(R.id.tv_artist);
            mItemView = itemView;
        }
    }

    public interface ItemClick{
        void onClick(int position);
    }
}
