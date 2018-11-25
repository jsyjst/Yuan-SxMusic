package com.example.musicplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.entiy.SeachSong;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.FileHelper;

import java.util.ArrayList;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentAdapter extends RecyclerView.Adapter<SearchContentAdapter.ViewHolder> {
    private static final String TAG="SearchContentAdapter";

    private ArrayList<SeachSong.DataBean> mSongListBeans;
    private static ItemClick mItemClick;
    private String mSeek;
    private Context mContext;
    private int mLastPosition = -1;


    public static void setItemClick(ItemClick itemClick){
        mItemClick = itemClick;
    }

    public SearchContentAdapter(ArrayList<SeachSong.DataBean> songListBeans, String seek, Context context){
        mContext = context;
        mSeek = seek;
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
        //设置与搜索一样的string的颜色
        CommonUtil.showStringColor(mSeek,songListBean.getSinger(),holder.artistTv);
        holder.titleTv.setText(songListBean.getName());
        CommonUtil.showStringColor(mSeek,songListBean.getName(),holder.titleTv);

        if(FileHelper.getSong().getImgUrl() !=null){
            //根据点击显示
            holder.playLine.setVisibility((position == mLastPosition||
                    (songListBean.getName().equals(FileHelper.getSong().getTitle())&&
                    songListBean.getSinger().equals(FileHelper.getSong().getArtist())))
                    ?View.VISIBLE:View.INVISIBLE);
            holder.mItemView.setBackgroundResource((position == mLastPosition||
                    (songListBean.getName().equals(FileHelper.getSong().getTitle())&&
                            songListBean.getSinger().equals(FileHelper.getSong().getArtist())))
                    ?R.color.click:R.color.translucent);
        }
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClick.onClick(position);
                equalPosition(position);
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
        View playLine;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_title);
            artistTv = itemView.findViewById(R.id.tv_artist);
            playLine = itemView.findViewById(R.id.line_play);
            mItemView = itemView;
        }
    }

    public interface ItemClick{
        void onClick(int position);
    }

    //判断点击的是否为上一个点击的项目
    public void equalPosition(int position) {
        if (position != mLastPosition) {
            if(mLastPosition!=-1) notifyItemChanged(mLastPosition);
            mLastPosition = position;
        }
        notifyItemChanged(position);
    }
}
