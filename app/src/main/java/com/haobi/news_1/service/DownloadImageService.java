package com.haobi.news_1.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.haobi.news_1.splash.bean.Ads;
import com.haobi.news_1.splash.bean.AdsDetail;
import com.haobi.news_1.util.Constant;
import com.haobi.news_1.util.ImageUtil;
import com.haobi.news_1.util.Md5Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by 15739 on 2019/7/8.
 */

public class DownloadImageService extends IntentService{

    public static final String ADS_DATA = "ads";

    //注意：在使用IntentService时，一定要实现一个默认的构造方法
    public DownloadImageService(){
        super("DownloadImageService");
    }

    //启动Service
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //接收到Http请求的对象
        Ads ads = (Ads)intent.getSerializableExtra(ADS_DATA);
        //下载
        List<AdsDetail> list = ads.getAds();
        for(int i=0;i<list.size();i++){
            AdsDetail detail = list.get(i);
            List<String> imgs = detail.getRes_url();
            if(null != imgs){
                String img_url = imgs.get(0);
                if (!TextUtils.isEmpty(img_url)){
//                    Log.i("测试2：", img_url);
                    //图片地址转换成唯一的MD5文件名
                    String catch_name = Md5Helper.toMD5(img_url);
                    //先判断图片是否存在，如果存在则不下载
                    if(!ImageUtil.checkImageIsDownload(catch_name)){
                        Log.d("测试4：", "正在下载");
                        //下载图片（1文件是完整的2内容也是完整的）
                        downloadImage(img_url, catch_name);

                    }

                }
            }
        }

    }

    public void downloadImage(String url, String MD5_name){
        try{
            URL uri = new URL(url);
            URLConnection urlConnection = uri.openConnection();
            //拿到一张图片
            Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
            //写入SD卡
            saveToSD(bitmap, MD5_name);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //保存到SD卡
    public void saveToSD(Bitmap bitmap, String MD5_name){
        if(bitmap == null){
            return;
        }
        //判断手机SD卡是否装载
        //我们使用的缓存目录使用“.xxx”是为了隐藏文件夹，防止一般用户误删除
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File SD = Environment.getExternalStorageDirectory();
            File cacheFile = new File(SD, Constant.CACHE);
            if (!cacheFile.exists()){
                cacheFile.mkdirs();
            }
            File image = new File(cacheFile, MD5_name+".jpg");
            //如果图片存在
            if(image.exists()){
                return;
            }
            FileOutputStream image_out = null;
            try {
                image_out = new FileOutputStream(image);
                //大图片的压缩(Bitmap->SD卡)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, image_out);
                image_out.flush();
                image_out.close();
                Log.d("测试3：", "下载完成");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            this.getCacheDir();
        }
    }

}
