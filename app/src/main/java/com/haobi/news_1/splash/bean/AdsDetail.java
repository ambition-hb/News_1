package com.haobi.news_1.splash.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 15739 on 2019/7/4.
 */

public class AdsDetail implements Serializable{
    //H5页面
    Action action_params;
    //图片
    List<String> res_url;

    public Action getAction_params() {
        return action_params;
    }

    public void setAction_params(Action action_params) {
        this.action_params = action_params;
    }

    public List<String> getRes_url() {
        return res_url;
    }

    public void setRes_url(List<String> res_url) {
        this.res_url = res_url;
    }

    @Override
    public String toString() {
        return "AdsDetail{"+"action_params="+action_params+",res_url="+res_url+"}";
    }
}
