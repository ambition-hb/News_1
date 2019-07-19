package com.haobi.news_1.news.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haobi.news_1.R;
import com.haobi.news_1.news.bean.DetailWeb;
import com.haobi.news_1.news.bean.DetailWebImage;
import com.haobi.news_1.util.Constant;
import com.haobi.news_1.util.HttpRespon;
import com.haobi.news_1.util.HttpUtil;
import com.haobi.news_1.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by 15739 on 2019/7/18.
 */

public class DetailActivity extends SwipeBackActivity {

    public static final String DOCID = "doc";
    String doc_Id;

    //新闻的html的内容
    String body;
    MyHandler mHandler;

    private static int INITSUCCESS = 0;

    WebView mWebView;

    int replayCount;

    TextView replayCountTextView;
    LinearLayout share_outer;
    EditText feeback;
    TextView send;
    RelativeLayout parent;
    boolean hasFocus =false;

    //滑动退出的控件
    private SwipeBackLayout mSwipeBackLayout;
    ArrayList<DetailWebImage> images;

    @JavascriptInterface
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mWebView = (WebView) findViewById(R.id.webview);
        feeback = (EditText) findViewById(R.id.feeback);
        share_outer = (LinearLayout) findViewById(R.id.share_outer);
        send = (TextView) findViewById(R.id.send);
        parent = (RelativeLayout) findViewById(R.id.parent);

        final Drawable left = getResources().getDrawable(R.drawable.biz_pc_main_tie_icon);
        //setBounds->设置DrawAbleLeft
        left.setBounds(0,0,30,30);
        feeback.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean fouces) {
                hasFocus = fouces;
                if(fouces){
                    //有焦点
                    feeback.setCompoundDrawables(null,null,null,null);
                    feeback.setHint("");
                    share_outer.setVisibility(View.GONE);
                    send.setVisibility(View.VISIBLE);
                }
                else{
                    //无焦点
                    feeback.setCompoundDrawables(left,null,null,null);
                    feeback.setHint("写跟帖");
                    share_outer.setVisibility(View.VISIBLE);
                    send.setVisibility(View.GONE);
                }
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        replayCountTextView = (TextView) findViewById(R.id.replayCount);
        mWebView.addJavascriptInterface(this,"demo");


        mHandler = new MyHandler(this);
        doc_Id = getIntent().getStringExtra(DOCID);

        HttpUtil util = HttpUtil.getInstance();
        String url = Constant.getDetailUrl(doc_Id);
        util.getDate(url, new HttpRespon<String>(String.class) {
            @Override
            public void onError(String msg) {
                Log.i("测试17", "获取详情新闻失败");
            }

            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject js = new JSONObject(json);
                    JSONObject need_js = js.optJSONObject(doc_Id);
                    DetailWeb web = JsonUtil.parseJson(need_js.toString(), DetailWeb.class);
                    Log.i("测试18", "获取详情新闻成功 ");
                    if (web != null) {
                        body = web.getBody();
                        images = (ArrayList<DetailWebImage>) web.getImg();
                        for (int i = 0; i < images.size(); i++) {
                            String src = images.get(i).getSrc();
                            String imageTag = "<img src='" + src + "'onclick=\"show()\"/>";
                            String tag = "<!--IMG#" + i + "-->";
                            body = body.replace(tag, imageTag);
                        }
                        //p 标签代表一个段落
                        String titleHTML = "<p><span style='font-size:18px;'><strong>" + web.getTitle() + "</strong></span></p>";// 标题

                        titleHTML = titleHTML+ "<p><span style='color:#666666;'>"+web.getSource()+"&nbsp&nbsp"+web.getPtime()+"</span></p>";//来源与时间

                        body = titleHTML + body;
                        body = "<html><head><style>img{width:100%}</style><script type='text/javascript'>function show(){window.demo.javaShow()} </script></head><body>" + body + "</body></html>";
                        replayCount = web.getReplyCount();
                        mHandler.sendEmptyMessage(INITSUCCESS);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //绑定SwipeBackActivity
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    public void onBackPressed() {
        if(hasFocus){
            //有焦点,失去焦点的最关键的做法就是让另外一个控件获取焦点
            mWebView.requestFocus();
        }else{
            finish();
        }
    }

    @JavascriptInterface
    public void javaShow(){
        Log.i("测试19", "javaShow: ");
        Intent intent = new Intent();
        intent.setClass(this,DetailImageActivity.class);
        intent.putExtra("image",images);
        startActivity(intent);
    }


    public void initWebview() {
        mWebView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
        //更新回复数
        replayCountTextView.setText(String.valueOf(replayCount));
    }

    static class MyHandler extends Handler {
        WeakReference<DetailActivity> activity;

        public MyHandler(DetailActivity dActivity) {
            this.activity = new WeakReference(dActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            DetailActivity detailActivity = activity.get();
            if (null == detailActivity) {
                return;
            }
            detailActivity.initWebview();
        }
    }
}
