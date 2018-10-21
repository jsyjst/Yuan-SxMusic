package com.example.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.R;

/**
 * Created by 残渊 on 2018/9/23.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private String [] mGroupStrings;
    private String [][] mChildStrings;
    private Context mContext;
    public ExpandableListViewAdapter(Context context, String [] groupStrings, String [][] childStrings){
        mChildStrings=childStrings;
        mGroupStrings=groupStrings;
        mContext=context;
    }

    @Override
    public int getGroupCount() {
        return mGroupStrings.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildStrings[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupStrings[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildStrings[groupPosition][childPosition];
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        GroupViewHolder groupViewHolder;
        if(convertView==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_first,parent,false);
            groupViewHolder=new GroupViewHolder();
            groupViewHolder.groupTextView=view.findViewById(R.id.tv_new_song);
            view.setTag(groupViewHolder);
        }else{
            view=convertView;
            groupViewHolder=(GroupViewHolder)view.getTag();
        }
        groupViewHolder.groupTextView.setText(mGroupStrings[groupPosition]);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        ChildViewHolder childViewHolder;
        if(convertView==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_second,parent,false);
            childViewHolder=new ChildViewHolder();
            childViewHolder.childTextView=view.findViewById(R.id.tv_song_list_name);
            view.setTag(childViewHolder);
        }else{
            view=convertView;
            childViewHolder=(ChildViewHolder) view.getTag();
        }
        childViewHolder.childTextView.setText(mChildStrings[groupPosition][childPosition]);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    class GroupViewHolder{
        private TextView groupTextView;
        private ImageView pointIv;
        private ImageView addIv;
    }
    class ChildViewHolder{
        private TextView childTextView;
        private ImageView faceIv;
    }
}
