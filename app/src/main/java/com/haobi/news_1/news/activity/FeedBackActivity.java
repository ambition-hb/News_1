package com.haobi.news_1.news.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.haobi.news_1.R;
import com.haobi.news_1.news.adapter.FeedBackAdapter;
import com.haobi.news_1.news.bean.FeedBack;
import com.haobi.news_1.news.bean.FeedBacks;
import com.haobi.news_1.util.Constant;
import com.haobi.news_1.util.HttpRespon;
import com.haobi.news_1.util.HttpUtil;
import com.haobi.news_1.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 15739 on 2019/7/21.
 */

public class FeedBackActivity extends Activity {

    ListView listView;
    ArrayList<FeedBacks> backs;
    FeedBackAdapter mAdapter;
    InnerHander mInnerHander;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mInnerHander = new InnerHander(this);

        listView = findViewById(R.id.listView);

        String docid = getIntent().getStringExtra(DetailActivity.DOCID);
        String url = Constant.getFeedBackUrl(docid);

        //初始化
        backs = new ArrayList<>();
        //HttpUtil是运行在子线程的，所以不能来更新UI
        HttpUtil util = HttpUtil.getInstance();
        util.getDate(url, new HttpRespon<String>(String.class) {
            @Override
            public void onError(String msg) {

            }

            @Override
            public void onSuccess(String string) {
                try {
                    //获取到所有的数据
                    JSONObject js = new JSONObject(string);
                    //取出hotPosts对应的JSONArray
                    //opt与get的区别：当值为null时，opt不会爆出异常
                    JSONArray array = js.optJSONArray("hotPosts");
                    //生成一个标题的数据
                    FeedBacks title = new FeedBacks();
                    //为True则设置
                    title.setTitle(true);
                    title.setTitleS("热门跟帖");
                    //将数据加进去
                    backs.add(title);
                    for (int i = 0; i < array.length(); i++) {
                        //生成每一条回复的数据
                        FeedBacks feedBacks = new FeedBacks();
                        //逐条解析数组中的jsonObject（格式-key:value）
                        JSONObject tmp = array.optJSONObject(i);
                        //迭代器->遍历（每一个FeedBack数量不定）
                        //注意：遍历过程是无序的
                        Iterator<String> keys = tmp.keys();
                        while (keys.hasNext()) {
                            //迭代器-> key->1,2,3,4,5
                            String key = keys.next();
                            //获取每一个JSONObject对象
                            JSONObject everyJson = tmp.optJSONObject(key);
                            FeedBack feedBack = JsonUtil.parseJson(everyJson.toString(), FeedBack.class);
                            //每一个回帖的序号记录下来
                            feedBack.setIndex(Integer.valueOf(key));
                            //每解析一个，添加进去一个
                            feedBacks.add(feedBack);
                        }
                        //根据key排序
                        feedBacks.sort();
                        //每循环一次，添加进去一个
                        backs.add(feedBacks);
                    }

                    mInnerHander.sendEmptyMessage(0);

                    Log.i("测试19", "获取跟帖数据："+backs.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void init() {
        mAdapter = new FeedBackAdapter(backs,this);
        listView.setAdapter(mAdapter);
    }

    //更新UI
    static class InnerHander extends Handler {
        WeakReference<FeedBackActivity> act;

        public InnerHander(FeedBackActivity activity) {
            this.act = new WeakReference<FeedBackActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FeedBackActivity feed = act.get();
            if (feed == null) {
                return;
            }
            //进行处理
            feed.init();
        }
    }
}
