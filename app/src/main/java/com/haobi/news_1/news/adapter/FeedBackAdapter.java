package com.haobi.news_1.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haobi.news_1.R;
import com.haobi.news_1.news.bean.FeedBack;
import com.haobi.news_1.news.bean.FeedBacks;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 15739 on 2019/7/21.
 */

public class FeedBackAdapter extends BaseAdapter{

    //要处理两种类型
    int type_title = 0;
    int type_content = 1;

    //传入的数据
    ArrayList<FeedBacks> date;
    LayoutInflater mInflater;
    //这个类的显示的配置类
    DisplayImageOptions mOptions;

    public FeedBackAdapter(ArrayList<FeedBacks> date, Context context) {
        this.date = date;
        mInflater = LayoutInflater.from(context);

        //建造者模式 -> 创建一个复杂的对象
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.biz_tie_user_avater_default)
                .showImageForEmptyUri(R.drawable.biz_tie_user_avater_default)
                .showImageOnFail(R.drawable.biz_tie_user_avater_default)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(500))
                .build();
    }

    @Override
    public int getCount() {
        return date.size();
    }

    @Override
    public Object getItem(int position) {
        return date.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //类型
        int type = getItemViewType(position);
        if(type == type_title){
            //返回一个标题类
            TitleViewHolder viewHolder;
            //如果convertView为空，则使用LayoutInflater()去加载布局
            if(convertView==null){
                convertView = mInflater.inflate(R.layout.item_feed_title,null);
                viewHolder = new TitleViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(viewHolder);
            }
            else{
                //否则，重用convertView
                //重新获取ViewHolder（利用View的getTag()方法，把ViewHolder重新取出）
                viewHolder = (TitleViewHolder) convertView.getTag();
            }
        }else{
            ContentViewHolder viewHolder;
            FeedBacks feedBacks = date.get(position);
            if(convertView==null){
                convertView = mInflater.inflate(R.layout.item_feedback,null);
                viewHolder = new ContentViewHolder();
                viewHolder.icon = (CircleImageView) convertView.findViewById(R.id.profile_image);
                viewHolder.name = (TextView) convertView.findViewById(R.id.net_name);
                viewHolder.from = (TextView) convertView.findViewById(R.id.net_from);
                viewHolder.content = (TextView) convertView.findViewById(R.id.content);
                viewHolder.vote = (TextView) convertView.findViewById(R.id.like);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ContentViewHolder) convertView.getTag();
            }

            initHoder(viewHolder,feedBacks);
        }
        return convertView;
    }

    public void initHoder(ContentViewHolder holder,FeedBacks backs ){
        FeedBack back =  backs.getLastDate();
        holder.name.setText(back.getN());
        holder.from.setText(back.getF());
        holder.content.setText(back.getB());
        holder.vote.setText(back.getV());

        ImageLoader.getInstance().displayImage(back.getTimg(),holder.icon,mOptions);
    }

    //返回页面数据的类型
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //返回什么类型的view（判断）
    @Override
    public int getItemViewType(int position) {
        //根据每一条数据的isTitle()进行判断,如果是true
        FeedBacks feedBacks =  date.get(position);
        //如果是标题
        if(feedBacks.isTitle()){
            return   type_title;
        }else{
            //如果是内容
            return  type_content;
        }
    }

    //ViewHolder通常出现在适配器里，为的是ListView滚动的时候快速设置值，而不必每次都重新创建很多对象，从而提升性能。
    class TitleViewHolder {
        TextView title;
    }
    class ContentViewHolder{
        CircleImageView icon;
        TextView name;
        TextView from;
        TextView time;
        TextView content;
        TextView vote;

    }
}
