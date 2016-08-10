package com.lz.easyui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.lz.easyui.util.CheckTool;

import java.util.ArrayList;
import java.util.List;

public abstract class LibraryBaseExpandableListViewAdapter<GroupDataSource, ChildDataSource, ViewGroupHolder, ViewChildHolder> extends BaseExpandableListAdapter {

    private List<GroupDataSource> dataList = new ArrayList<GroupDataSource>();

    private Context ctx;

    public LibraryBaseExpandableListViewAdapter(Context context) {
        this.ctx = context;
    }

    public void resetData(List<GroupDataSource> tList) {
        this.dataList.clear();
        if (!CheckTool.isEmpty(tList)) {
            this.dataList.addAll(tList);
        }
        notifyDataSetChanged();
    }

    public void addData(List<GroupDataSource> tList) {
        if (!CheckTool.isEmpty(tList)) {
            this.dataList.addAll(tList);
        }
        notifyDataSetChanged();
    }

    public void insertData(List<GroupDataSource> tList) {
        if (!CheckTool.isEmpty(tList)) {
            this.dataList.addAll(0, tList);
        }
        notifyDataSetChanged();
    }

    public void removeData(List<GroupDataSource> tList) {
        if (!CheckTool.isEmpty(tList)) {
            this.dataList.removeAll(tList);
        }
        notifyDataSetChanged();
    }

    public void removeData(GroupDataSource t) {
        if (!CheckTool.isEmpty(t)) {
            this.dataList.remove(t);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return dataList.size();
    }

    @Override
    public GroupDataSource getGroup(int groupPosition) {
        return dataList.get(groupPosition);
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
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewGroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getGroupLayoutId(), null);
            groupHolder = onCreateGroupViewHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (ViewGroupHolder) convertView.getTag();
        }

        onBindGroupViewHolder(groupHolder, (GroupDataSource)getGroup(groupPosition), groupPosition);
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getChildLayoutId(), null);
            childHolder = onCreateChildViewHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ViewChildHolder) convertView.getTag();
        }

        onBindChildViewHolder(childHolder, (ChildDataSource)getChild(groupPosition, childPosition), isLastChild, groupPosition, childPosition);
        return convertView;
    }

    protected Context getAdapterContext(){
        return ctx;
    }

    public void startActivity(Intent it) {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(it);
    }

    @Override
    public abstract int getChildrenCount(int groupPosition);

    @Override
    public abstract ChildDataSource getChild(int groupPosition, int childPosition);

    protected abstract ViewGroupHolder onCreateGroupViewHolder(View view);

    protected abstract ViewChildHolder onCreateChildViewHolder(View view);

    protected abstract int getGroupLayoutId();

    protected abstract int getChildLayoutId();

    protected abstract void onBindGroupViewHolder(ViewGroupHolder viewHolder, GroupDataSource data, int groupPosition);

    protected abstract void onBindChildViewHolder(ViewChildHolder viewHolder, ChildDataSource data, boolean isLastChild, int groupPosition, int childPosition);

}
