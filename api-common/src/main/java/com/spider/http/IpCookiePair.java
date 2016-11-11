package com.spider.http;

/**
 * Created by 许巧生 on 2016/6/7.
 */
public class IpCookiePair {

    private String ip;
    private String Cookie;
    private String cookie_key;//取cookie的关键字（现在为shopCode）

    public IpCookiePair(){}

    public IpCookiePair(String ip, String cookie, String cookie_key) {
        this.ip = ip;
        Cookie = cookie;
        this.cookie_key = cookie_key;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCookie() {
        return Cookie;
    }

    public void setCookie(String cookie) {
        Cookie = cookie;
    }

    public String getCookie_key() {
        return cookie_key;
    }

    public void setCookie_key(String cookie_key) {
        this.cookie_key = cookie_key;
    }
}
