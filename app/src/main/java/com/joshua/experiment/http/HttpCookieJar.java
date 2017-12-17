package com.joshua.experiment.http;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2017
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2017/5/6 11:30
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class HttpCookieJar implements CookieJar {
    private Context mContext;

    public HttpCookieJar(Context context) {
        this.mContext = context;
    }

    private  PersistentCookieStore cookieStore;

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if(cookieStore==null){
            cookieStore=new PersistentCookieStore(mContext);
        }
        return cookieStore.get(url);
    }
}

