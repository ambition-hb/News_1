package com.haobi.news_1.news.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/18  9:52
 * @desc ${TODD}
 */
public class FeedBacks {
    ArrayList<FeedBack> hot;

    //加入判断
    boolean isTitle =false;
    //标题（如果为true则设置）
    String titleS ;


    public String getTitleS() {
        return titleS;
    }

    public void setTitleS(String titleS) {
        this.titleS = titleS;
    }

    public FeedBacks() {
        this.hot = new ArrayList<>();
    }

    public void add(FeedBack feedBack){
        hot.add(feedBack);
    }

    public void sort(){
        //Collections是针对集合类的一个包装类，它提供一系列静态方法以实现对各种集合的搜索、排序、线程安全化等操作
        Collections.sort(hot,new FeedBackSort());
    }

    public ArrayList<FeedBack> getHot() {
        return hot;
    }

    public void setHot(ArrayList<FeedBack> hot) {
        this.hot = hot;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    //比较器
    class  FeedBackSort implements Comparator{

        @Override
        public int compare(Object lhs, Object rhs) {
            if(((FeedBack)lhs).getIndex()>((FeedBack)rhs).getIndex()){
                //大于
                return  1;
            }else if(((FeedBack)lhs).getIndex()==((FeedBack)rhs).getIndex()){
                // 相等
                return  0;
            }else {
                //小于
                return  -1;
            }

        }
    }

    public FeedBack  getLastDate(){

        return  hot.get(hot.size()-1);
    }


    @Override
    public String toString() {
        return "FeedBacks{" +
                "hot=" + hot +
                '}';
    }
}
