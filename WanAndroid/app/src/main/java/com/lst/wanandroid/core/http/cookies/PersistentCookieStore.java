package com.lst.wanandroid.core.http.cookies;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.lst.wanandroid.WanAndroidApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class PersistentCookieStore {
    private static final String LOG_TAG = "PersistentCookieStore";
    private static final String COOKIE_PREFS = "Cookies_Prefs";
    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;

    PersistentCookieStore(){
        cookiePrefs = WanAndroidApp.getInstance().getSharedPreferences(COOKIE_PREFS, 0);
        cookies = new HashMap<>();

        //将持久化的cookie缓存到内存中
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            String[] cookieName = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookieName) {
                String encodedCookie = cookiePrefs.getString(name, null);
                if (encodedCookie != null) {
                    Cookie decodedCookie = decodeCookie(encodedCookie);
                    if (decodedCookie != null) {
                        if (!cookies.containsKey(entry.getKey())) {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<>());
                        }
                        cookies.get(entry.getKey()).put(name, decodedCookie);
                    }
                }
            }
        }
    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    public void add(HttpUrl url, Cookie cookie) {
        String token = getCookieToken(cookie);

        //将Cookie缓存到内存中，如果缓存过期，就重置此cookie
        if (!cookie.persistent()) {
            if (!cookies.containsKey(url.host())) {
                cookies.put(url.host(), new ConcurrentHashMap<>());
            }
            cookies.get(url.host()).put(token, cookie);
        } else if (cookies.containsKey(url.host())) {
            cookies.get(url.host()).remove(token);
        }

        //将Cookies持久化到本地
        SharedPreferences.Editor editor = cookiePrefs.edit();
        editor.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).entrySet()));
        editor.putString(token, encodeCookie(new OKHttpCookies(cookie)));
        editor.apply();
    }

    public List<Cookie> get(HttpUrl url) {
        ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(url.host())) {
            ret.addAll(cookies.get(url.host()).values());
        }
        return ret;
    }

    public void removeAll(){
        SharedPreferences.Editor editor = cookiePrefs.edit();
        editor.clear();
        editor.apply();
        cookies.clear();
    }

    boolean remove(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);
        if (cookies.containsKey(url.host()) && cookies.get(url.host()).containsKey(name)) {
            cookies.get(url.host()).remove(name);
            SharedPreferences.Editor editor = cookiePrefs.edit();
            if (cookiePrefs.contains(name)) {
                editor.remove(name);
            }
            editor.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            editor.apply();
            return true;
        } else {
            return false;
        }
    }

    List<Cookie> getCookies(){
        ArrayList<Cookie> ret = new ArrayList<>();
        for (String key : cookies.keySet()) {
            ret.addAll(cookies.get(key).values());
        }
        return ret;
    }

    private String encodeCookie(OKHttpCookies cookie) {
        if (cookie == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
            objectOutputStream.writeObject(cookie);
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in encodeCookie", e);
            e.printStackTrace();
            return null;
        }
        return byteArrayToHexString(os.toByteArray());
    }

    private Cookie decodeCookie(String cookieString){
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((OKHttpCookies) objectInputStream.readObject()).getCookies();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    //二进制数组转十六进制字符串
    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    private byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
