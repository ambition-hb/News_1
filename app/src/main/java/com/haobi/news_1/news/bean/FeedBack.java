package com.haobi.news_1.news.bean;

/**
 * @author 王维波
 * @time 2016/8/16  15:36
 * @desc ${TODD}
 */
public class FeedBack {

    /**
     * timg : http://img0.ph.126.net/IkYdWw4BLJKO9SsXqcXVUA==/6631318454352964161.jpg
     * f : 网易湖南省长沙市手机网友&nbsp;将军谈笑弯弓：
     * v : 35139
     * t : 2016-08-16 06:57:52
     * b : 大黑牛，看着点你的经纪人啊
     * fi : 0
     * n : 将军谈笑弯弓
     * l : 0
     */
    //头像
    private String timg;
    //from
    private String f;
    //点赞数量
    private String v;
    //时间
    private String t;
    //内容
    private String b;
    private String fi;
    //名称
    private String n;
    private String l;
    //是否是vip
    private String vip;

    //序号
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public void setTimg(String timg) {
        this.timg = timg;
    }

    public void setF(String f) {
        this.f = f;
    }

    public void setV(String v) {
        this.v = v;
    }

    public void setT(String t) {
        this.t = t;
    }

    public void setB(String b) {
        this.b = b;
    }

    public void setFi(String fi) {
        this.fi = fi;
    }

    public void setN(String n) {
        this.n = n;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getTimg() {
        return timg;
    }

    public String getF() {
        return f;
    }

    public String getV() {
        return v;
    }

    public String getT() {
        return t;
    }

    public String getB() {
        return b;
    }

    public String getFi() {
        return fi;
    }

    public String getN() {
        return n;
    }

    public String getL() {
        return l;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "timg='" + timg + '\'' +
                ", f='" + f + '\'' +
                ", v='" + v + '\'' +
                ", t='" + t + '\'' +
                ", b='" + b + '\'' +
                ", fi='" + fi + '\'' +
                ", n='" + n + '\'' +
                ", l='" + l + '\'' +
                ", vip='" + vip + '\'' +
                ", index=" + index +
                '}';
    }
}
