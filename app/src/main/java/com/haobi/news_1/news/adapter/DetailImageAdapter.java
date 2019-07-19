package com.haobi.news_1.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.haobi.news_1.R;
import com.haobi.news_1.news.bean.DetailWebImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by 15739 on 2019/7/19.
 */

public class DetailImageAdapter extends PagerAdapter{

    ArrayList<DetailWebImage> images;
    ArrayList<View> mViews;
    Context mContext;
    //
    DisplayImageOptions mOptions;
    public DetailImageAdapter(ArrayList<DetailWebImage> images, Context context,ArrayList<View> views) {
        this.images = images;
        mContext = context;
        this.mViews = views;

        //建造者模式 -> 创建一个复杂的对象
        mOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)//缓存在硬盘
                .bitmapConfig(Bitmap.Config.RGB_565)//解码方式565
                .build();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photo);
        DetailWebImage detailWebImage = images.get(position);
        ImageLoader.getInstance().displayImage(detailWebImage.getSrc(),photoView,mOptions);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
