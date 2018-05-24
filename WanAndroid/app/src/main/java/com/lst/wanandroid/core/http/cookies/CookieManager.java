package com.lst.wanandroid.core.http.cookies;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieManager implements CookieJar {

    private static final PersistentCookieStore COOKIE_STORE = new PersistentCookieStore();
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (Cookie item : cookies) {
                COOKIE_STORE.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return COOKIE_STORE.get(url);
    }

    public static void clearAllCookies(){
        COOKIE_STORE.removeAll();
    }

    public static boolean clearCookies(HttpUrl url, Cookie cookie) {
        return COOKIE_STORE.remove(url, cookie);
    }

    public static List<Cookie> getCookies(){
        return COOKIE_STORE.getCookies();
    }
}
