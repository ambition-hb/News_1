package com.haobi.news_1.news.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.haobi.news_1.R;
import com.haobi.news_1.news.adapter.NewAdapter;
import com.haobi.news_1.news.adapter.ShowAdapter;
import com.haobi.news_1.news.bean.FragmentInfo;
import com.haobi.news_1.news.bean.ShowTabEvent;
import com.haobi.news_1.news.news_inner.HotFragment;
import com.haobi.news_1.util.NoScrollGridView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by 15739 on 2019/7/11.
 */

public class NewsFragment extends Fragment {

    //所有页面的数据集
    ArrayList<FragmentInfo> pages;
    NewAdapter mNewAdapter;
    ImageView add;
    //标示是否显示菜单
    boolean isShowMenu = false;
    RelativeLayout menu_title;
    FrameLayout menu;

    NoScrollGridView show,not_show;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //通过LayoutInflater找布局文件
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        add = (ImageView) view.findViewById(R.id.add);
        menu_title  = (RelativeLayout) view.findViewById(R.id.menu_title);
        menu = (FrameLayout) view.findViewById(R.id.menu);

        show = (NoScrollGridView) view.findViewById(R.id.show);
        not_show = (NoScrollGridView) view.findViewById(R.id.not_show);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //menu没显示
                if(!isShowMenu){

                    EventBus.getDefault().post(new ShowTabEvent(false));

                    Animation add_up = AnimationUtils.loadAnimation(getContext(),R.anim.add_up);
                    //控件放置在动画的最后一帧
                    add_up.setFillAfter(true);
                    add.startAnimation(add_up);

                    //顶部的标签先显示
                    menu_title.setVisibility(View.VISIBLE);
                    Animation top_menu_show = AnimationUtils.loadAnimation(getContext(),R.anim.top_menu_show);
                    menu_title.startAnimation(top_menu_show);



                    menu.setVisibility(View.VISIBLE);
                    Animation from_top = AnimationUtils.loadAnimation(getContext(),R.anim.from_top);
                    menu.startAnimation(from_top);


                    isShowMenu = true;
                }else{

                    EventBus.getDefault().post(new ShowTabEvent(true));

                    //menu没显示
                    Animation add_down = AnimationUtils.loadAnimation(getContext(),R.anim.add_down);
                    add_down.setFillAfter(true);
                    add.startAnimation(add_down);

                    //顶部的标签先隐藏
                    menu_title.setVisibility(View.VISIBLE);
                    Animation top_menu_hide = AnimationUtils.loadAnimation(getContext(),R.anim.top_menu_hide);
                    top_menu_hide.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            //执行完动画后,隐藏控件
                            menu_title.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    menu_title.startAnimation(top_menu_hide);




                    Animation to_top = AnimationUtils.loadAnimation(getContext(),R.anim.to_top);
                    to_top.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            menu.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    menu.startAnimation(to_top);
                    isShowMenu = false;
                }
            }
        });
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

        ShowAdapter showAdapter = new ShowAdapter(titles, getContext());
        show.setAdapter(showAdapter);
        not_show.setAdapter(showAdapter);

        mNewAdapter = new NewAdapter(getFragmentManager(), pages);
        viewPager.setAdapter(mNewAdapter);

        //!!!!关键代码,自动绑定数据
        smartTabLayout.setViewPager(viewPager);
        smartTabLayout.setDividerColors(Color.TRANSPARENT);
    }
}
