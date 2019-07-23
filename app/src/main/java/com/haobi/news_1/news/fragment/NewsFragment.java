package com.haobi.news_1.news.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.haobi.news_1.R;
import com.haobi.news_1.news.adapter.NewAdapter;
import com.haobi.news_1.news.adapter.ShowAdapter;
import com.haobi.news_1.news.bean.FragmentInfo;
import com.haobi.news_1.news.bean.ShowTabEvent;
import com.haobi.news_1.news.news_inner.HotFragment;
import com.haobi.news_1.util.NoScrollGridView;
import com.haobi.news_1.util.SharePrenceUtil;

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
    ShowAdapter showAdapter;
    ShowAdapter not_showAdapter;
    Button sort;

    SmartTabLayout smartTabLayout;
    ViewPager viewPager;

    //显示标题的缓存key
    final static  String SHOW_CONTENT="show";
    //不显示标题的缓存key
    final static  String NOT_SHOW_CONTENT="not_show";
    //当前显示的标题的顺序
    String lastTile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //通过LayoutInflater找布局文件
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        add = (ImageView) view.findViewById(R.id.add);
        menu_title  = (RelativeLayout) view.findViewById(R.id.menu_title);
        menu = (FrameLayout) view.findViewById(R.id.menu);

        show = (NoScrollGridView) view.findViewById(R.id.show);
        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isShowDel = showAdapter.isShowDel();
                if(isShowDel){
                    //热门栏目不能删除
                    if(position==0){
                        Toast.makeText(getContext(),"热门栏目不能删除!!!!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String title = showAdapter.delATitle(position);
                    not_showAdapter.addADate(title);
                }
            }
        });
        not_show = (NoScrollGridView) view.findViewById(R.id.not_show);
        not_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isShowDel = showAdapter.isShowDel();
                if(isShowDel){
                    String title = not_showAdapter.delATitle(position);
                    showAdapter.addADate(title);
                }
            }
        });
        sort =(Button) view.findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdapter.setShowDel();
                boolean isShow = showAdapter.isShowDel();
                if(isShow){
                    sort.setText("完成");
                }else{
                    sort.setText("删除排序");
                }
            }
        });

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
                    //让删除按钮回复状态
                    showAdapter.setShowDelUnable();
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
                    //修改后的内容
                    String content = showAdapter.getContent();
                    String not_content =not_showAdapter.getContent();
                    //
                    String[] newTiles = content.split("-");

                    //缓存修改后的内容
                    SharePrenceUtil.saveString(getContext(),SHOW_CONTENT,content);
                    SharePrenceUtil.saveString(getContext(),NOT_SHOW_CONTENT,not_content);

                    //做一个判断
                    //做了改变->刷新
                    //不刷新
                    if(lastTile.equals(content)){
                        return;
                    }
                    pages.clear();
                    for (int i = 0; i < newTiles.length; i++) {
                        FragmentInfo info;
                        if (i == 0) {
                            info = new FragmentInfo(new HotFragment(), newTiles[i]);
                        } else {
                            info = new FragmentInfo(new EmptyFragment(), newTiles[i]);
                        }
                        pages.add(info);
                    }
                    mNewAdapter.setDate(pages);
                    smartTabLayout.setViewPager(viewPager);
                    lastTile = content;
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
        smartTabLayout = (SmartTabLayout) getActivity().findViewById(R.id.smart_tab);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);

        //需要显示的
        String conetnt = SharePrenceUtil.getString(getActivity(),SHOW_CONTENT);
        //不需要显示的
        String not_content = SharePrenceUtil.getString(getActivity(),NOT_SHOW_CONTENT);
        String[] titles;
        //缓存为空,没有修改过标题
        if(TextUtils.isEmpty(conetnt)){
            titles = getResources().getStringArray(R.array.news_titles);
            StringBuilder builder = new StringBuilder();
            for(int i = 0 ;i<titles.length;i++){
                builder.append(titles[i]);
                if(i!=titles.length-1){
                    builder.append("-");
                }
            }
            //获取到上次显示的标题
            lastTile = builder.toString();
        }else{
            titles =  conetnt.split("-");
            //获取到上次显示的标题
            lastTile = conetnt;
        }

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

        showAdapter = new ShowAdapter(titles, getContext());
        if(TextUtils.isEmpty(not_content)){
            not_showAdapter = new ShowAdapter(getContext());
        }else{
            String [] not = not_content.split("-");
            not_showAdapter = new ShowAdapter(not,getContext());
        }
        show.setAdapter(showAdapter);
        not_show.setAdapter(not_showAdapter);

        mNewAdapter = new NewAdapter(getFragmentManager(), pages);
        viewPager.setAdapter(mNewAdapter);

        //!!!!关键代码,自动绑定数据
        smartTabLayout.setViewPager(viewPager);
        smartTabLayout.setDividerColors(Color.TRANSPARENT);
    }
}
