package com.haobi.news_1.news.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.haobi.news_1.R;
import com.haobi.news_1.news.adapter.DetailImageAdapter;
import com.haobi.news_1.news.bean.DetailWebImage;

import java.util.ArrayList;

/**
 * Created by 15739 on 2019/7/19.
 */

public class DetailImageActivity extends Activity {

    ViewPager viewpager;
    ArrayList<View> views;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);
        views = new ArrayList<>();

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        ArrayList<DetailWebImage> images = (ArrayList<DetailWebImage>) getIntent().getSerializableExtra("image");
        if(null!=images){
            for(DetailWebImage tmp:images){
                View view =  View.inflate(this,R.layout.item_detail_img,null);
                views.add(view);
            }
        }

        DetailImageAdapter adapter = new DetailImageAdapter(images,this,views);
        viewpager.setAdapter(adapter);

    }
}
