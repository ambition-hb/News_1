package com.haobi.news_1.news.bean;

/**
 * Created by 15739 on 2019/7/16.
 */

public class Banner {

    /**
     * imgsrc : bigimg
     * subtitle :
     * tag : photoset
     * title : 万人龙虾宴！1吨小龙虾拼成超大龙虾
     * url : 00AN0001|2302877
     */

    private String imgsrc;
    private String subtitle;
    private String tag;
    private String title;
    private String url;

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "imgsrc='" + imgsrc + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
