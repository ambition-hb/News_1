package com.haobi.news_1.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.haobi.news_1.news.bean.FragmentInfo;

import java.util.ArrayList;

/**
 * Created by 15739 on 2019/7/11.
 */

public class NewAdapter extends FragmentStatePagerAdapter {
    ArrayList<FragmentInfo> mFragments;

    public NewAdapter(FragmentManager fm, ArrayList<FragmentInfo> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    //需要刷新ViewPager的话,我们必须重写getItemPosition->POSITION_NONE
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  mFragments.get(position).getTitle();
    }

    public void setDate(ArrayList<FragmentInfo> mFragments){
        this.mFragments = mFragments;
        notifyDataSetChanged();
    }
}
