package com.haobi.news_1.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 15739 on 2019/7/23.
 */

public class NoScrollGridView extends GridView {

    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height =  MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
