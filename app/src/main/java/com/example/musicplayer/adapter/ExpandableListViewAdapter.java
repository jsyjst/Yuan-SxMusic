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
import com.example.musicplayer.entiy.AlbumCollection;
import com.example.musicplayer.util.CommonUtil;

import java.util.List;

/**
 * Created by 残渊 on 2018/9/23.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "ExpandableListViewAdapter";

    private String[] mGroupStrings;
    private List<List<AlbumCollection>> mAlbumCollectionList;
    private Context mContext;
    private OnChildItemClickListener mChildClickListener;


    public ExpandableListViewAdapter(Context context, String[] groupStrings, List<List<AlbumCollection>> albumCollectionList) {
        mAlbumCollectionList = albumCollectionList;
        mGroupStrings = groupStrings;
        mContext = context;
    }
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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_first, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.groupTextView = view.findViewById(R.id.tv_new_song);
            groupViewHolder.pointIv = view.findViewById(R.id.iv_point);
            groupViewHolder.addIv = view.findViewById(R.id.iv_add);
            view.setTag(groupViewHolder);
        } else {
            view = convertView;
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.groupTextView.setText(mGroupStrings[groupPosition]);
        if (isExpanded) {
            groupViewHolder.pointIv.setImageResource(R.drawable.up);
        } else {
            groupViewHolder.pointIv.setImageResource(R.drawable.down);
        }
        return view;
    }


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
        private ImageView addIv;
    }

    class ChildViewHolder {
        TextView albumNameTv;
        ImageView faceIv;
        TextView authorTv;
        RippleView childView;
    }
    public interface OnChildItemClickListener{
        void onClick(int groupPosition,int childPosition);
    }

}
