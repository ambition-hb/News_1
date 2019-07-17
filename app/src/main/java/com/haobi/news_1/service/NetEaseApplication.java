package com.haobi.news_1.service;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by 15739 on 2019/7/16.
 */

public class NetEaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        File sd = Environment.getExternalStorageDirectory();
        File image_loader_cache= new File(sd,"xmg4");
        if(!image_loader_cache.exists()){
            image_loader_cache.mkdirs();
        }

        //全局显示的设置类
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).discCache(new UnlimitedDiskCache(image_loader_cache)).diskCacheFileNameGenerator(new Md5FileNameGenerator()).build();
        //使用前必须初始化
        ImageLoader.getInstance().init(config);
    }
}
