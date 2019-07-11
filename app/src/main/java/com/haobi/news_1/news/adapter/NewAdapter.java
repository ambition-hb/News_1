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

    @Override
    public CharSequence getPageTitle(int position) {
        return  mFragments.get(position).getTitle();
    }
}
