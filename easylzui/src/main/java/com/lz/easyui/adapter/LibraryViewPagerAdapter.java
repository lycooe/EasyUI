package com.lz.easyui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lz.easyui.util.CheckTool;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class LibraryViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    public LibraryViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void resetData(List<Fragment> tList) {
        if (CheckTool.isEmpty(tList)) {
            tList = new ArrayList<Fragment>();
        }
        this.fragmentList.clear();
        this.fragmentList.addAll(tList);
        notifyDataSetChanged();
    }

    public void addData(List<Fragment> tList) {
        if (CheckTool.isEmpty(tList)) {
            tList = new ArrayList<Fragment>();
        }
        this.fragmentList.addAll(tList);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
