package com.haobi.news_1.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.haobi.news_1.R;
import com.haobi.news_1.news.adapter.NewAdapter;
import com.haobi.news_1.news.bean.FragmentInfo;
import com.haobi.news_1.news.news_inner.HotFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

/**
 * Created by 15739 on 2019/7/11.
 */

public class NewsFragment extends Fragment {

    //所有页面的数据集
    ArrayList<FragmentInfo> pages;

    NewAdapter mNewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //通过LayoutInflater找布局文件
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        //初始化
        pages = new ArrayList<>();

        FrameLayout layout = (FrameLayout) getActivity().findViewById(R.id.tabs);
        layout.addView(View.inflate(getActivity(), R.layout.include_tab, null));

        //传入ViewPager,SmartTabLayout可以自己绑定
        SmartTabLayout smartTabLayout = getActivity().findViewById(R.id.smart_tab);
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);


        //获取标题字符串数组
        String[] titles = getResources().getStringArray(R.array.news_titles);
        for (int i=0;i<titles.length;i++){
            FragmentInfo info;
            //设置if-else，先生成一个页面,其余的置为空
            if (i==0){
                info = new FragmentInfo(new HotFragment(), titles[i]);
            }else{
                info = new FragmentInfo(new EmptyFragment(), titles[i]);
            }
            pages.add(info);
        }

        mNewAdapter = new NewAdapter(getFragmentManager(), pages);
        viewPager.setAdapter(mNewAdapter);

        //!!!!关键代码,自动绑定数据
        smartTabLayout.setViewPager(viewPager);

    }
}
