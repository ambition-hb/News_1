package com.haobi.news_1.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haobi.news_1.R;

/**
 * Created by 15739 on 2019/7/11.
 */

public class NewsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //通过LayoutInflater找布局文件
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        return view;
    }
}
