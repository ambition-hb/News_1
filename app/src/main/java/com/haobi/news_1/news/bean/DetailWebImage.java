package com.haobi.news_1.news.bean;

import java.io.Serializable;

/**
 * Created by 15739 on 2019/7/18.
 */

public class DetailWebImage implements Serializable{

    /**
     * alt :
     * pixel : 560*372
     * ref : <!--IMG#0-->
     * src : http://dingyue.nosdn.127.net/JzJOk8Qoa0XzDXftbEK=b34CIxAiSDGEM419f7ufw9GlD1467253888440.jpg
     */

    private String alt;
    private String pixel;
    private String ref;
    private String src;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getPixel() {
        return pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
