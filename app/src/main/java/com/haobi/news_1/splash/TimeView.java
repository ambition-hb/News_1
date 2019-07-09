package com.haobi.news_1.splash;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.haobi.news_1.R;

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
    int pading = 5;
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
        //获取到xml定义的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeView);

        int innerColor = array.getColor(R.styleable.TimeView_innerColor, Color.BLUE);
        int outerColor = array.getColor(R.styleable.TimeView_ringColor, Color.GREEN);

        mTextPaint = new TextPaint();
        //抗锯齿
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(20);
        mTextPaint.setColor(Color.WHITE);

        //内切画笔
        circleP = new Paint();
        circleP.setFlags(Paint.ANTI_ALIAS_FLAG);
        circleP.setColor(innerColor);

        //外切画笔
        outerP = new Paint();
        outerP.setFlags(Paint.ANTI_ALIAS_FLAG);
        outerP.setColor(outerColor);
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
        //使用过后回收
        array.recycle();
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

        float y = (canvas.getHeight()/2);
        float de = mTextPaint.descent();//+
        float a = mTextPaint.ascent();//-

        //drawText的x值：左边距
        //drawText的y值：顶部到baseline的距离
        canvas.drawText(content, pading*2, y-((de+a)/2), mTextPaint);
    }

    public void setProgress(int total, int now){
        int space = 360/total;
        degree = space*now;
        //控件刷新：UI线程刷新
        invalidate();
        //控件刷新：子线程刷新
//        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            //手指按压
            case MotionEvent.ACTION_DOWN:
                //透明
                setAlpha(0.3f);
                break;
            //手指抬起
            case MotionEvent.ACTION_UP:
                //透明
                setAlpha(1.0f);
                break;
        }
        return true;
    }
}
