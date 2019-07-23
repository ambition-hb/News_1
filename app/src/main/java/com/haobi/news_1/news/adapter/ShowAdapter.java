package com.haobi.news_1.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haobi.news_1.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 15739 on 2019/7/23.
 */

public class ShowAdapter extends BaseAdapter{

    ArrayList<String> titles;
    Context mContext;
    boolean isShowDel = false;


    public ShowAdapter(ArrayList<String> titles, Context context) {
        this.titles = titles;
        mContext = context;
    }
    public ShowAdapter(String[] title , Context context){
        this.titles = new ArrayList<>();
        titles.addAll(Arrays.asList(title));
        mContext = context;
    }

    public ShowAdapter(Context context){
        this.titles = new ArrayList<>();
        mContext = context;
    }

    public String getContent(){
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<titles.size();i++){
            String tmp = titles.get(i);
            builder.append(tmp);
            if(i!=titles.size()-1){
                builder.append("-");
            }

        }

        return  builder.toString();
    }

    public String delATitle(int index){
        String title = titles.get(index);
        titles.remove(index);
        notifyDataSetChanged();
        return title;
    }

    public void setShowDel(){
        this.isShowDel = !isShowDel;
        notifyDataSetChanged();
    }

    public void setShowDelUnable(){
        this.isShowDel = false;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isShowDel(){
        return  isShowDel;
    }

    public void addADate(String title){
        if(null==titles){
            titles = new ArrayList<>();
        }
        titles.add(title);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = titles.get(position);
        ViewHoder hoder;
        if(convertView==null){
            convertView = View.inflate(mContext, R.layout.item_show,null);
            hoder = new ViewHoder();
            hoder.title = (TextView) convertView.findViewById(R.id.title);
            //删除
            hoder.del = (ImageView) convertView.findViewById(R.id.del);
            convertView.setTag(hoder);
        }else{
            hoder = (ViewHoder) convertView.getTag();
        }
        hoder.title.setText(name);

        if(isShowDel){
            hoder.del.setVisibility(View.VISIBLE);
        }else{
            hoder.del.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHoder{
        TextView title;
        ImageView del;
    }
}
