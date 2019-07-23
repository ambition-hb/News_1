package com.haobi.news_1.news.bean;

/**
 * Created by 15739 on 2019/7/23.
 */

public class ShowTabEvent {
    boolean isShow = false;

    public ShowTabEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
