package com.haobi.news_1.news.news_inner;

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

public class HotFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //将布局文件转化为view，但同时带上参数
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        return view;
    }
}
