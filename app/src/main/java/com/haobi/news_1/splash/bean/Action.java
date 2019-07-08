package com.haobi.news_1.splash.bean;

import java.io.Serializable;

/**
 * Created by 15739 on 2019/7/4.
 */

public class Action implements Serializable{
    public String link_url;

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    @Override
    public String toString() {
        return "Action{"+"link_url="+link_url+"}";
    }
}
