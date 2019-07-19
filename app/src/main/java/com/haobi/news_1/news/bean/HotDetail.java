package com.haobi.news_1.news.bean;

import com.haobi.news_1.news.bean.Banner;

import java.util.List;

/**
 * Created by 15739 on 2019/7/16.
 */

public class HotDetail {

    List<Banner> ads;
    String img;
    String title;
    String source;
    int replyCount;
    String specialID;
    String docid;

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public List<Banner> getAds() {
        return ads;
    }

    public void setAds(List<Banner> ads) {
        this.ads = ads;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getSpecialID() {
        return specialID;
    }

    public void setSpecialID(String specialID) {
        this.specialID = specialID;
    }

    @Override
    public String toString() {
        return "HotDetail{" +
                "ads=" + ads +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", replyCount=" + replyCount +
                ", specialID='" + specialID + '\'' +
                '}';
    }
}
