package com.example.musicplayer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.musicplayer.R;
import com.example.musicplayer.callback.OnItemClickListener;
import com.example.musicplayer.entiy.Love;
import com.example.musicplayer.util.FileHelper;

import java.util.List;

/**
 * Created by 残渊 on 2018/11/30.
 */

public class LoveSongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "LoveSongAdapter";
    private int footerViewType = 1;
    private int itemViewType = 0;
    private List<Love> mLoveList;
    private Context mContext;
    private int mLastPosition = -1;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public LoveSongAdapter(Context context, List<Love> loveList) {
        mContext = context;
        mLoveList = loveList;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView songNameTv;
        TextView singerTv;
        View playLine;
        RippleView item;

        public ViewHolder(View itemView) {
            super(itemView);
            songNameTv = itemView.findViewById(R.id.tv_title);
            singerTv = itemView.findViewById(R.id.tv_artist);
            playLine = itemView.findViewById(R.id.line_play);
            item = itemView.findViewById(R.id.ripple);
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
                    .inflate(R.layout.recycler_song_search_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else {
            View footerView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_local_songs_item, parent, false);
            FooterHolder footerHolder = new FooterHolder(footerView);
            return footerHolder;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            final Love love = mLoveList.get(position);
            Log.d(TAG, "onBindViewHolder: " + position);

            holder.songNameTv.setText(love.getName());
            holder.singerTv.setText(love.getSinger());
            //根据点击显示
            if (love.getSongId().equals(FileHelper.getSong().getSongId())) {
                holder.playLine.setVisibility(View.VISIBLE);
                mLastPosition = position;
                holder.songNameTv.setTextColor(mContext.getResources()
                        .getColor(R.color.yellow));
                holder.singerTv.setTextColor(mContext.getResources()
                        .getColor(R.color.yellow));
            } else {
                holder.playLine.setVisibility(View.INVISIBLE);
                holder.songNameTv.setTextColor(mContext.getResources()
                        .getColor(R.color.white));
                holder.singerTv.setTextColor(mContext.getResources()
                        .getColor(R.color.white_blue));
            }
            holder.item.setOnRippleCompleteListener(rippleView -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(position);
                }
                equalPosition(position);
            });
        } else {
            FooterHolder footerHolder = (FooterHolder) viewHolder;
            footerHolder.numTv.setText("共" + mLoveList.size() + "首音乐");
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
        return mLoveList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position + 1 == getItemCount() ? footerViewType : itemViewType;
    }
}
