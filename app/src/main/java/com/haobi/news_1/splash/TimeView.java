package com.haobi.news_1.splash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by 15739 on 2019/7/8.
 */

public class TimeView extends View {

    //文字画笔
    TextPaint mTextPaint;
    //内圆
    Paint circleP;
    Paint outerP;
    String content = "跳过";
    //文字的间距
    int pading = 10;
    //内圆的直径
    int inner;
    //外圈的直径
    int all;
    //外圈的角度
    int degree;
    //矩形区域
    RectF outerRect;

    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTextPaint = new TextPaint();
        //抗锯齿
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(Color.WHITE);

        //内切画笔
        circleP = new Paint();
        circleP.setFlags(Paint.ANTI_ALIAS_FLAG);
        circleP.setColor(Color.BLUE);

        //外切画笔
        outerP = new Paint();
        outerP.setFlags(Paint.ANTI_ALIAS_FLAG);
        outerP.setColor(Color.GREEN);
        //空心
        outerP.setStyle(Paint.Style.STROKE);
        outerP.setStrokeWidth(pading);

        //文字的宽度
        float text_Width = mTextPaint.measureText(content);
        //内圆圈的直径
        inner = (int)text_Width+2*pading;
        //外圈的直径
        all = inner+2*pading;

        outerRect = new RectF(pading/2, pading/2, all-pading/2, all-pading/2);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //文字的宽度+内圆的边距*2+画笔的宽度*2
        setMeasuredDimension(all, all);
    }

    public void setD(int d){
        this.degree = d;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(Color.RED);
        canvas.drawCircle(all/2, all/2, inner/2, circleP);
        canvas.save();
        //画布旋转
        canvas.rotate(-90, all/2, all/2);
        canvas.drawArc(outerRect, 0f, degree, false,outerP);
        canvas.restore();
    }
}
