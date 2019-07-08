package com.haobi.news_1.splash.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 15739 on 2019/7/4.
 */

public class Ads implements Serializable{
    int next_req;
    List<AdsDetail> ads;

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }

    public List<AdsDetail> getAds() {
        return ads;
    }

    public void setAds(List<AdsDetail> ads) {
        this.ads = ads;
    }

    @Override
    public String toString() {
        return "Ads{"+"next_req="+next_req+",ads="+ads+"}";
    }
}
