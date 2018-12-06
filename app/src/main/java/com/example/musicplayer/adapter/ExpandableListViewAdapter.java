package com.example.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.musicplayer.R;
import com.example.musicplayer.callback.OnChildItemClickListener;
import com.example.musicplayer.entiy.AlbumCollection;
import com.example.musicplayer.util.CommonUtil;

import java.util.List;

/**
 * 自建歌单和收藏歌单的二级适配类
 * Created by 残渊 on 2018/9/23.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "ExpandableListViewAdapter";

    private String[] mGroupStrings;  //一级标题
    private List<List<AlbumCollection>> mAlbumCollectionList; //二级收藏歌单列表
    private Context mContext;
    private OnChildItemClickListener mChildClickListener; //二级item的点击监听


    public ExpandableListViewAdapter(Context context, String[] groupStrings, List<List<AlbumCollection>> albumCollectionList) {
        mAlbumCollectionList = albumCollectionList;
        mGroupStrings = groupStrings;
        mContext = context;
    }
    //提供给外部使用
    public  void setOnChildItemClickListener(OnChildItemClickListener onChildItemClickListener){
        mChildClickListener=onChildItemClickListener;
    }

    @Override
    public int getGroupCount() {
        return mGroupStrings.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mAlbumCollectionList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupStrings[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mAlbumCollectionList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    //绘制一级列表
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_first, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.groupTextView = view.findViewById(R.id.tv_new_song);
            groupViewHolder.pointIv = view.findViewById(R.id.iv_point);
            view.setTag(groupViewHolder);
        } else {
            view = convertView;
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.groupTextView.setText(mGroupStrings[groupPosition]);
        //根据展开的状态来改变箭头方向
        if (isExpanded) {
            groupViewHolder.pointIv.setImageResource(R.drawable.up);
        } else {
            groupViewHolder.pointIv.setImageResource(R.drawable.down);
        }
        return view;
    }


    //绘制二级列表
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_second, parent, false);
            childViewHolder.albumNameTv = view.findViewById(R.id.tv_album_name);
            childViewHolder.faceIv = view.findViewById(R.id.iv_album);
            childViewHolder.authorTv = view.findViewById(R.id.tv_author);
            childViewHolder.childView = view.findViewById(R.id.ripple);
            view.setTag(childViewHolder);
        } else {
            view = convertView;
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        view.setBackgroundResource(R.color.translucent);
        childViewHolder.albumNameTv.setText(mAlbumCollectionList.get(groupPosition).get(childPosition).getAlbumName());
        childViewHolder.authorTv.setText(mAlbumCollectionList.get(groupPosition).get(childPosition).getSingerName());
        CommonUtil.setImgWithGlide(mContext,
                mAlbumCollectionList.get(groupPosition).get(childPosition).getAlbumPic(), childViewHolder.faceIv);
        //点击水波纹效果，结束后开始点击效果
        childViewHolder.childView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                mChildClickListener.onClick(groupPosition,childPosition);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class GroupViewHolder {
        private TextView groupTextView;
        private ImageView pointIv;
    }

    class ChildViewHolder {
        TextView albumNameTv;
        ImageView faceIv;
        TextView authorTv;
        RippleView childView;
    }
}
