package com.haobi.news_1.news.news_inner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haobi.news_1.R;
import com.haobi.news_1.news.activity.DetailActivity;
import com.haobi.news_1.news.adapter.BannerAdapter;
import com.haobi.news_1.news.adapter.HotAdapter;
import com.haobi.news_1.news.bean.Banner;
import com.haobi.news_1.news.bean.Hot;
import com.haobi.news_1.news.bean.HotDetail;
import com.haobi.news_1.util.Constant;
import com.haobi.news_1.util.HttpRespon;
import com.haobi.news_1.util.HttpUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 15739 on 2019/7/11.
 */

public class HotFragment extends Fragment implements ViewPager.OnPageChangeListener, AbsListView.OnScrollListener{

    ListView mListView;

    //放置轮播图
    ArrayList<Banner> mBanners;
    ArrayList<HotDetail> mHotDetails;
    ArrayList<View> views;
    ArrayList<ImageView> dot_imgs;

    MyHandler mHandler ;
    HotAdapter adapter;
    LayoutInflater inflater;

    boolean isToEnd = false;
    boolean isHttpRequestIng = false;

    //刷新数据成功
    private final static int INIT_SUCCESS = 0;
    //加载更多数据成功
    private final static int UPDATE_SUCCESS = 1;

    private final static int STOP_RESH = 2;

    //轮播图->相关的控件
    ViewPager viewpager;
    BannerAdapter bAdapter;
    TextView bannerTitle;
    LinearLayout dots;

    int startIndex = 0;
    int endIndex = 0;
    int pageSize = 20;
    //取页面的次数
    int count = 0;
    PtrClassicFrameLayout ptr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //将布局文件转化为view，但同时带上参数
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        //加载等待页面
        RelativeLayout loading = (RelativeLayout) view.findViewById(R.id.loading);

        ptr = (PtrClassicFrameLayout) view.findViewById(R.id.ptr);

        //如果没有数据显示这个View
        mListView.setEmptyView(loading);

        //2设置刷新回调
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                getData(true);
            }

            //1控件包住需要下拉刷新的控件
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, mListView, header);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCollection();
        initView();
        getData(true);
    }

    private void initCollection() {
        mBanners = new ArrayList<>();
        mHotDetails = new ArrayList<>();
        views = new ArrayList<>();
        dot_imgs = new ArrayList<>();
    }

    private void initView() {
        mHandler = new MyHandler(this);

        inflater = LayoutInflater.from(getActivity());
        View head = inflater.inflate(R.layout.include_banner, null);
        //将轮播图控件加入ListView
        mListView.addHeaderView(head);
        //为ListView增加滚动监督
        mListView.setOnScrollListener(this);
        //为ListView添加点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("测试16", "捕获ListView点击事件 ");
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                HotDetail detail = adapter.getDateByIndex(position-mListView.getHeaderViewsCount());
                intent.putExtra(DetailActivity.DOCID, detail.getDocid());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });
        //将ViewPager加到ListView头部
        viewpager = (ViewPager) head.findViewById(R.id.viewpager);
        viewpager.addOnPageChangeListener(this);
        bannerTitle = (TextView) head.findViewById(R.id.title);
        dots = (LinearLayout)head.findViewById(R.id.dots);
    }

    //isInit 来标示是否是第一次取数据
    private void getData(final boolean isInit) {
        if(isHttpRequestIng){
            return;
        }
        isHttpRequestIng = true;
        if(isInit){
            count = 0;
        }
        //请求热点新闻数据
        HttpUtil util = HttpUtil.getInstance();
        calIndex();
        String url = Constant.getHotUrl(startIndex,endIndex);
        util.getDate(url, new HttpRespon<Hot>(Hot.class) {
            @Override
            public void onError(String msg) {
                mHandler.sendEmptyMessage(STOP_RESH);
                isHttpRequestIng = false;
            }

            @Override
            public void onSuccess(Hot hot) {
                mHandler.sendEmptyMessage(STOP_RESH);
                isHttpRequestIng = false;
                //获取列表的数据
                if(null!=hot&&null!=hot.getT1348647909107()){
                    count++;
                    Log.i("测试10", "热点新闻数据获取成功");
                    List<HotDetail> details = hot.getT1348647909107();
                    //取的是第一页的数据->取出轮播图的数据->删除->显示listView
                    if (isInit){
                        //取出第0位包含轮播图的数据
                        HotDetail tmp_baner = details.get(0);
                        List<Banner> banners = tmp_baner.getAds();
                        //取的是第一页的数据->取出轮播图的数据->删除->显示listView
                        if(mBanners!=null){
                            //把我们上次的结果清空
                            mBanners.clear();
                            //把这次请求的数据添加进mBanners
                            mBanners.addAll(banners);
                        }

                        //删除轮播图片数据
                        details.remove(2);
                        details.remove(1);
                        //列表数据加载完成
                        mHotDetails.addAll(details);
                        //获取数据完毕，发送消息更新UI(异步线程无法更改UI)
                        mHandler.sendEmptyMessage(INIT_SUCCESS);
                    }else{
                        Message message = mHandler.obtainMessage(UPDATE_SUCCESS);
                        message.obj = details;
                        mHandler.sendMessage(message);
                    }
                }
            }
        });
    }

    public void stopResh(){
        ptr.refreshComplete();
    }

    //计算我们的url角标，每次下拉刷新重新取数据，我们置count=0
    public void calIndex(){
        if(count==0){
            startIndex = 0;
            endIndex = startIndex+20;

        }else{
            startIndex = endIndex;
            endIndex = startIndex+20;
        }

    }

    //处理listView的数据
    public  void initDate(){
        adapter = new HotAdapter(mHotDetails,getActivity());
        mListView.setAdapter(adapter);
    }

    public void update(List<HotDetail> newDate){
        if(null==adapter){
            mHotDetails = new ArrayList<>();
            mHotDetails.addAll(newDate);
            adapter = new HotAdapter(mHotDetails, getActivity());
            mListView.setAdapter(adapter);
        }else{
            adapter.addData(newDate);
        }
    }

    public void initBanner() {
        //清除上次显示的点
        dots.removeAllViews();
        //清除上次显示的imageview
        dot_imgs.clear();
        views.clear();

        if (null != mBanners && mBanners.size() > 0) {
            for (int i = 0; i < mBanners.size(); i++) {
                View view = inflater.inflate(R.layout.item_banner, null);
                views.add(view);
                ImageView dot = new ImageView(getActivity());
                dot.setImageResource(R.drawable.gray_dot);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                p.setMargins(0,0,10,0);
                dots.addView(dot,p);
                dot_imgs.add(dot);
            }
            bAdapter = new BannerAdapter(views,mBanners);
            viewpager.setAdapter(bAdapter);
            int half = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2)%mBanners.size();
            viewpager.setCurrentItem(half);

            //设置默认显示的数据
            setImageDot(0);
            setBannerTitle(0);
        }
    }

    public void setImageDot(int index){
        int size = dot_imgs.size();
        int realPosition = index%size;
        for(int i = 0;i<size;i++){
            ImageView dot = dot_imgs.get(i);
            if(i== realPosition){
                dot.setImageResource(R.drawable.white_dot);
            }else{
                dot.setImageResource(R.drawable.gray_dot);
            }
        }
    }

    public void setBannerTitle(int index){
        int size = dot_imgs.size();
        int realPosition = index%size;
        //显示默认数据
        bannerTitle.setText(mBanners.get(realPosition).getTitle());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageDot(position);
        setBannerTitle(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE&&isToEnd){
            //获取更多数据
            getData(false);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(view.getLastVisiblePosition()==totalItemCount-1){
            isToEnd = true;
        }else{
            isToEnd = false;
        }
    }

    static class MyHandler extends Handler {
        WeakReference<HotFragment> weak_fragment ;

        public MyHandler(HotFragment fragment) {
            this.weak_fragment = new WeakReference(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            HotFragment hot = weak_fragment.get();
            if(hot==null){
                return;
            }
            switch (msg.what) {
                case INIT_SUCCESS:
                    Log.i("测试11","成功更新热点新闻");
                    hot.initDate();
                    hot.initBanner();
                    break;
                case UPDATE_SUCCESS:
                    List<HotDetail> date = (List<HotDetail>) msg.obj;
                    hot.update(date);
                    break;
                case STOP_RESH:
                    hot.stopResh();
                    break;
                default:
                    break;
            }
        }
    }
}
