package com.haobi.news_1.splash.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.haobi.news_1.MainActivity;
import com.haobi.news_1.R;
import com.haobi.news_1.service.DownloadImageService;
import com.haobi.news_1.splash.bean.Action;
import com.haobi.news_1.splash.bean.Ads;
import com.haobi.news_1.splash.bean.AdsDetail;
import com.haobi.news_1.util.Constant;
import com.haobi.news_1.util.ImageUtil;
import com.haobi.news_1.util.JsonUtil;
import com.haobi.news_1.util.Md5Helper;
import com.haobi.news_1.util.SharePrenceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 15739 on 2019/7/4.
 */

public class SplashActivity extends Activity {
    //广告图片
    ImageView ads_img;
    //Json缓存
    static final String JSON_CACHE = "ads_Json";
    static final String JSON_CACHE_TIME_OUT = "ads_Json_time_out";
    static final String JSON_CACHE_LAST_SUCCESS = "ads_Json_last";
    static final String LAST_IMAGE_INDEX = "img_index";
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //开启全屏设置（通用的全屏设置方法2.0-7.0）
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ads_img = (ImageView)findViewById(R.id.ads);
        mHandler = new Handler();
        getAds();

        showImage();

    }

    Runnable NoPhotoGotoMain = new Runnable() {
        @Override
        public void run() {
//            Log.i("测试6：", "run: ");
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        mHandler.removeCallbacks(NoPhotoGotoMain);
        super.onBackPressed();
    }

    //显示图片
    public void showImage(){
        //读出缓存
        String cache = SharePrenceUtil.getString(this, JSON_CACHE);
        //如果有缓存且不为空
        if (!TextUtils.isEmpty(cache)){
            // /读出这次显示的图片中的角标
            int index = SharePrenceUtil.getInt(this, LAST_IMAGE_INDEX);
            //转化成对象
            Ads ads = JsonUtil.parseJson(cache, Ads.class);
            int size = ads.getAds().size();
            if (ads == null){
                return;
            }
            List<AdsDetail> adsDetail = ads.getAds();
            if (adsDetail != null && adsDetail.size()>0){
                //取余->防止数组越界
                final AdsDetail detail = adsDetail.get(index%size);
                List<String> urls = detail.getRes_url();
                if (urls!=null && !TextUtils.isEmpty(urls.get(0))){
                    //获取到url
                    String url = urls.get(0);
                    //计算出文件名
                    String image_name = Md5Helper.toMD5(url);
                    File image = ImageUtil.getFileByName(image_name);
                    if (image.exists()){
                        Bitmap bitmap = ImageUtil.getImageBitMapByFile(image);
                        ads_img.setImageBitmap(bitmap);
                        index++;
                        SharePrenceUtil.saveInt(this, LAST_IMAGE_INDEX, index);

                        ads_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Action action = detail.getAction_params();
                                //如果H5的数据是空的或者没有广告页面，不跳转
                                if (action != null || TextUtils.isEmpty(action.getLink_url())){
                                    Intent intent = new Intent();
                                    intent.setClass(SplashActivity.this, WebViewActivity.class);
                                    intent.putExtra(WebViewActivity.ACTION_NAME, action);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            }
        }else{
            //没有缓存，显示不了图片，3秒后跳转到首页
            mHandler.postDelayed(NoPhotoGotoMain, 3000);


        }
    }

    //判断是否需要http请求
    public void getAds(){
        String cache = SharePrenceUtil.getString(this, JSON_CACHE);
        if(TextUtils.isEmpty(cache)){
            httpRequest();
        }else{
            int time_out = SharePrenceUtil.getInt(this, JSON_CACHE_TIME_OUT);
            long now = System.currentTimeMillis();
            long last = SharePrenceUtil.getLong(this, JSON_CACHE_LAST_SUCCESS);
            if ((now-last)>time_out*60*10){
                httpRequest();
            }
        }
    }

    //获取广告数据（异步方式）
    public void httpRequest(){
        Log.i("测试5:", "httpRequest: ");
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.SPLASH_URL)
                .build();
        //开启一个异步请求
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    //请求失败
                }
                //获取数据
                String data = response.body().string();
                Ads ads = JsonUtil.parseJson(data, Ads.class);
                if (ads != null){
                    //请求成功
//                    Log.i("测试1:", ads.toString());
                    //Http成功后，缓存json
                    SharePrenceUtil.saveString(SplashActivity.this, JSON_CACHE, data);
                    ////Http成功后，缓存超时时间
                    SharePrenceUtil.saveInt(SplashActivity.this, JSON_CACHE_TIME_OUT, ads.getNext_req());
                    //Http成功后，缓存上次请求成功的时间
                    SharePrenceUtil.saveLong(SplashActivity.this, JSON_CACHE_LAST_SUCCESS, System.currentTimeMillis());

                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, DownloadImageService.class);
                    intent.putExtra(DownloadImageService.ADS_DATA, ads);
                    startService(intent);
                }else{
                    //请求失败
                }
            }
        });
    }

}
