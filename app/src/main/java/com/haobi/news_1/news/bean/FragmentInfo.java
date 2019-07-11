package com.haobi.news_1.news.bean;


import android.support.v4.app.Fragment;

/**
 * Created by 15739 on 2019/7/11.
 */

public class FragmentInfo {

    Fragment mFragment;
    String title;

    public FragmentInfo(Fragment mFragment, String title) {
        this.mFragment = mFragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FragmentInfo{" +
                "mFragment=" + mFragment +
                ", title='" + title + '\'' +
                '}';
    }
}
