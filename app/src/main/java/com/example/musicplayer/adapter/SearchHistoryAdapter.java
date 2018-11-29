package com.example.musicplayer.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.entiy.History;

import java.util.List;

/**
 * 历史记录的适配器
 * Created by 残渊 on 2018/11/29.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<History> mHistoryList;
    private static final int mHistoryType =0;
    private static final int mFooterType = 1;
    private static OnItemClickListener mOnItemClcikListener;
    private static OnDeleteClickListener mOnDeleteClickListener;
    private static OnFooterClickListener mFooterClickListener;

    public SearchHistoryAdapter(List<History> historyList){
        mHistoryList = historyList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType ==mHistoryType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_seek_history_item,
                    parent,false);
            HistoryHolder historyHolder = new HistoryHolder(view);
            return historyHolder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_delete_all_history_item,
                    parent,false);
            FooterHolder footerHolder = new FooterHolder(view);
            return footerHolder;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof HistoryHolder){
            HistoryHolder historyHolder =(HistoryHolder) holder;
            historyHolder.historyTv.setText(mHistoryList.get(position).getHistory());
            historyHolder.deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnDeleteClickListener.onClick(position);
                }
            });

            historyHolder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClcikListener.onClick(position);
                }
            });
        }else{
            FooterHolder footerHolder =(FooterHolder) holder;
            footerHolder.deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFooterClickListener.onClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size()+1;
    }
    @Override
    public int getItemViewType(int position) {
        return position + 1 == getItemCount() ? mFooterType :mHistoryType;
    }

    private class HistoryHolder extends RecyclerView.ViewHolder {
        TextView historyTv;
        ImageView deleteIv;
        View mItemView;

        public HistoryHolder(View itemView) {
            super(itemView);
            historyTv = itemView.findViewById(R.id.tv_seek_history);
            deleteIv = itemView.findViewById(R.id.iv_history_delete);
            mItemView = itemView;
        }
    }

    private class FooterHolder extends RecyclerView.ViewHolder {
        View deleteView;

        public FooterHolder(View itemView) {
            super(itemView);
            deleteView = itemView;
        }
    }

    public static void setOnItemClcikListener(OnItemClickListener onItemClcikListener){
        mOnItemClcikListener =onItemClcikListener;
    }
    public static void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener){
        mOnDeleteClickListener =onDeleteClickListener;
    }
    public static void setFooterClickListener(OnFooterClickListener onFooterClickListener){
        mFooterClickListener =onFooterClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
    public interface OnDeleteClickListener{
        void onClick(int position);
    }
    public interface OnFooterClickListener{
        void onClick();
    }


}
