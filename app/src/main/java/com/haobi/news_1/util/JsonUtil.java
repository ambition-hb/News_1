package com.haobi.news_1.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Objects;

/**
 * Created by 15739 on 2019/7/4.
 */

public class JsonUtil {

    private static Gson mGson;
    //<T> 代表声明使用泛型
    //第二个T 代表返回的类型是我们使用的类型
    //Class<T> 代表类型.class
    public static <T> T parseJson(String json, Class<T> tClass){
        if (mGson == null){
            mGson = new Gson();
        }
        if (TextUtils.isEmpty(json)){
            return null;
        }
        return mGson.fromJson(json, tClass);

    }

}
