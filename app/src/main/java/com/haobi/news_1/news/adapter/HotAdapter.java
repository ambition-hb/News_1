package com.haobi.news_1.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haobi.news_1.R;
import com.haobi.news_1.news.bean.HotDetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15739 on 2019/7/16.
 */

public class HotAdapter extends BaseAdapter {

    ArrayList<HotDetail> mHotDetails;
    LayoutInflater mInflater;
    //这个类的显示的配置类（单次）
    DisplayImageOptions mOptions;


    public HotAdapter(ArrayList<HotDetail> hotDetails, Context context) {
        mHotDetails = hotDetails;
        mInflater = LayoutInflater.from(context);

        //建造者模式 -> 创建一个复杂的对象
        //描述UniversalImageLoader如何展示图片，在需要显示图片的地方初始化UniversalImageLoader的配置
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(500))
                .build();
    }

    @Override
    public int getCount() {
        return mHotDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return mHotDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //通过ViewHolder来提升ListView效率
        ViewHolder holder;

        HotDetail detail = mHotDetails.get(position);

        //如果convertView为空，则使用LayoutInflater去加载布局
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_hot, null);
            holder = new ViewHolder();
            //通过ViewHolder获取实例
            holder.icon = (ImageView) convertView.findViewById(R.id.img);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.source = (TextView) convertView.findViewById(R.id.source);
            holder.reply_count = (TextView) convertView.findViewById(R.id.reply_count);
            holder.special = (TextView) convertView.findViewById(R.id.special);
            //将ViewHolder存储在view中
            convertView.setTag(holder);
        } else {
            //否则，重用convertView
            holder = (ViewHolder) convertView.getTag();
        }
        //初始化View(设置图片和文字)
        initViews(holder, detail);
        return convertView;
    }

    //初始化
    public void initViews(ViewHolder hoder, HotDetail detail) {

        hoder.title.setText(detail.getTitle());
        hoder.source.setText(detail.getSource());
        hoder.reply_count.setText(detail.getReplyCount() + "跟帖");

        //显示图片的方法，使用displayImage()方法
        ImageLoader.getInstance().displayImage(detail.getImg(), hoder.icon, mOptions, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
//                Log.i("测试12","开始加载");
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
//                Log.i("测试13", "加载失败");
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//                Log.i("测试14","加载成功");
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
//                Log.i("测试15", "加载取消");
            }
        });

    }

    public void addData(List<HotDetail> add) {
        if(mHotDetails==null){
            mHotDetails = new ArrayList<>();
        }
        mHotDetails.addAll(add);
        notifyDataSetChanged();
    }

    public HotDetail getDateByIndex(int index){
        HotDetail detail = mHotDetails.get(index);
        return  detail;
    }

    //定义ViewHolder内部类，用于对控件实例进行缓存
    class ViewHolder {
        ImageView icon;
        TextView title;
        TextView source;
        TextView reply_count;
        TextView special;
    }
}
