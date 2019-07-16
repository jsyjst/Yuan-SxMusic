package com.example.musicplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.R;
import com.example.musicplayer.callback.OnAlbumItemClickListener;
import com.example.musicplayer.callback.OnItemClickListener;
import com.example.musicplayer.app.Constant;
import com.example.musicplayer.entiy.Album;
import com.example.musicplayer.entiy.SearchSong;
import com.example.musicplayer.util.CommonUtil;
import com.example.musicplayer.util.FileHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/11/21.
 */

public class SearchContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "SearchContentAdapter";

    private ArrayList<SearchSong.DataBean.ListBean> mSongListBeans;
    private List<Album.DataBean.ListBean> mAlbumList;
    private static OnItemClickListener mItemClick;
    private static OnAlbumItemClickListener mAlbumClick;
    private String mSeek;
    private Context mContext;
    private int mLastPosition = -1;
    private int mType;


    public static void setItemClick(OnItemClickListener itemClick) {
        mItemClick = itemClick;
    }

    public static void setAlbumClick(OnAlbumItemClickListener albumClick) {
        mAlbumClick = albumClick;
    }

    public SearchContentAdapter(List<Album.DataBean.ListBean> dataBeans, String seek, Context context, int type) {
        mContext = context;
        mSeek = seek;
        mAlbumList = dataBeans;
        mType = type;
    }


    public SearchContentAdapter(ArrayList<SearchSong.DataBean.ListBean> songListBeans, String seek, Context context, int type) {
        mContext = context;
        mSeek = seek;
        mSongListBeans = songListBeans;
        mType = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        if (viewType == Constant.TYPE_SONG ) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_song_search_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else if (viewType == Constant.TYPE_ALBUM) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_album_item, parent, false);
            AlbumHolder albumHolder = new AlbumHolder(view);
            return albumHolder;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder songHolder = (ViewHolder) holder;
            SearchSong.DataBean.ListBean songListBean = mSongListBeans.get(position);

            //设置歌手，因为歌手可能有两个
            String singer = songListBean.getSinger().get(0).getName();
            for (int i = 1; i < songListBean.getSinger().size(); i++) {
                singer+="、"+songListBean.getSinger().get(i).getName();
            }
            songHolder.artistTv.setText(singer);
            //设置与搜索一样的string的颜色
            CommonUtil.showStringColor(mSeek, singer, songHolder.artistTv);
            songHolder.titleTv.setText(songListBean.getSongname());
            CommonUtil.showStringColor(mSeek, songListBean.getSongname(), songHolder.titleTv);

                //根据点击显示
            if(songListBean.getSongmid().equals(FileHelper.getSong().getSongId())){
                songHolder.playLine.setVisibility(View.VISIBLE);
                mLastPosition =position;
                songHolder.mItemView.setBackgroundResource(R.color.translucent);
            }else {
                songHolder.playLine.setVisibility(View.INVISIBLE);
                songHolder.mItemView.setBackgroundResource(R.color.transparent);
            }
            songHolder.mItemView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    mItemClick.onClick(position);
                    equalPosition(position);
                }
            });
        } else {
            AlbumHolder albumHolder = (AlbumHolder) holder;
            Album.DataBean.ListBean albumList = mAlbumList.get(position);
            Glide.with(mContext).load(albumList.getAlbumPic())
                    .apply(RequestOptions.errorOf(R.drawable.background)).into(albumHolder.albumIv);
            albumHolder.albumName.setText(albumList.getAlbumName());
            albumHolder.singerName.setText(albumList.getSingerName());
            albumHolder.publicTime.setText(albumList.getPublicTime());
            CommonUtil.showStringColor(mSeek, albumList.getAlbumName(), albumHolder.albumName);
            CommonUtil.showStringColor(mSeek, albumList.getSingerName(), albumHolder.singerName);
            CommonUtil.showStringColor(mSeek, albumList.getPublicTime(), albumHolder.publicTime);
            albumHolder.item.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    Log.d(TAG, "onClick: album");
                    mAlbumClick.onClick(position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mType == Constant.TYPE_SONG) {
            return mSongListBeans.size();
        } else if (mType == Constant.TYPE_ALBUM) {
            return mAlbumList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;
        TextView artistTv;
        RippleView mItemView;
        View playLine;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_title);
            artistTv = itemView.findViewById(R.id.tv_artist);
            playLine = itemView.findViewById(R.id.line_play);
            mItemView = itemView.findViewById(R.id.ripple);
        }
    }

    class AlbumHolder extends RecyclerView.ViewHolder {
        ImageView albumIv;
        TextView singerName;
        TextView albumName;
        TextView publicTime;
        RippleView item;

        public AlbumHolder(View itemView) {
            super(itemView);
            albumIv = itemView.findViewById(R.id.iv_album);
            singerName = itemView.findViewById(R.id.tv_singer_name);
            albumName = itemView.findViewById(R.id.tv_album_name);
            publicTime = itemView.findViewById(R.id.tv_public_time);
            item = itemView.findViewById(R.id.ripple);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mType;
    }

    //判断点击的是否为上一个点击的项目
    public void equalPosition(int position) {
        if (position != mLastPosition) {
            if (mLastPosition != -1) notifyItemChanged(mLastPosition);
            mLastPosition = position;
        }
        notifyItemChanged(position);
    }
}
