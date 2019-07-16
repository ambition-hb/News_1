package com.haobi.news_1.news.news_inner;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.haobi.news_1.R;
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

/**
 * Created by 15739 on 2019/7/11.
 */

public class HotFragment extends Fragment {

    ListView mListView;

    //放置轮播图
    ArrayList<Banner> mBanners;
    ArrayList<HotDetail> mHotDetails;
    MyHandler mHandler ;
    HotAdapter adapter;
    //刷新数据成功
    private final static int INIT_SUCCESS = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //将布局文件转化为view，但同时带上参数
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBanners = new ArrayList<>();
        mHotDetails = new ArrayList<>();
        mHandler = new MyHandler(this);

        //请求热点新闻数据
        HttpUtil util = HttpUtil.getInstance();
        util.getDate(Constant.HOT_URL, new HttpRespon<Hot>(Hot.class) {
            @Override
            public void onError(String msg) {

            }

            @Override
            public void onSuccess(Hot hot) {

                //获取列表的数据
                if(null!=hot&&null!=hot.getT1348647909107()){
                    Log.i("测试10", "热点新闻数据获取成功");
                    List<HotDetail> details = hot.getT1348647909107();
                    //取出第0位包含轮播图的数据
                    HotDetail tmp_baner = details.get(0);
                    List<Banner> banners = tmp_baner.getAds();
                    mBanners.addAll(banners);
                    //获取轮播图片成功

                    //删除轮播图片数据
                    details.remove(0);
                    mHotDetails.addAll(details);
                    //列表数据加载完成

                    //获取数据完毕，发送消息更新UI(异步线程无法更改UI)
                    mHandler.sendEmptyMessage(INIT_SUCCESS);
                }
            }
        });
    }

    public  void initDate(){
        adapter = new HotAdapter(mHotDetails,getActivity());
        mListView.setAdapter(adapter);
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
                    break;

                default:
                    break;
            }
        }
    }


}
