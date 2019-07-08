package com.haobi.news_1.splash.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.haobi.news_1.R;
import com.haobi.news_1.splash.bean.Action;

/**
 * Created by 15739 on 2019/7/8.
 */

public class WebViewActivity extends Activity {

    public static final String ACTION_NAME = "action";

    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Action action = (Action)getIntent().getSerializableExtra(ACTION_NAME);
        setContentView(R.layout.activity_webview);
        webview = (WebView)findViewById(R.id.webview);
        //启用JavaScript
        webview.getSettings().setJavaScriptEnabled(true);
        //设置全屏显示
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.loadUrl(action.getLink_url());
        //处理url重定向问题（不要抛到系统浏览器）
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //如果webview能够回退到上一个页面
        if (webview.canGoBack()){
            webview.goBack();
            return;
        }
        super.onBackPressed();
    }
}
