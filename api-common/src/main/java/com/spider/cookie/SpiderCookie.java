package com.spider.cookie;

import com.spider.cache.redis.RedisCacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by 许巧生 on 2016/6/6.
 */
public class SpiderCookie implements Serializable {

    private static final long serialVersionUID = 1802998747586288766L;

    private String shopName;
    private String shopCode;
    private String bsnsCode;
    private String cookie;

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getBsnsCode() {
        return bsnsCode;
    }

    public void setBsnsCode(String bsnsCode) {
        this.bsnsCode = bsnsCode;
    }

    public SpiderCookie(String shopName, String shopCode, String bsnsCode, String cookie) {
        this.shopName = shopName;
        this.shopCode = shopCode;
        this.bsnsCode = bsnsCode;
        this.cookie = cookie;
    }

    /**
     * cookie获取
     */
    public static class CookieHolder {

        private static final Logger LOG = LoggerFactory.getLogger(CookieHolder.class);

        public static String getCookie(String shopCode_businessCode, RedisCacheProvider redisCacheProvider) {
            LinkedHashMap<String, String> object = redisCacheProvider.get(shopCode_businessCode);
            while (object == null) {
                try {
                    Thread.currentThread().sleep(60000);
                } catch (InterruptedException e) {
                    LOG.error(e.getMessage());
                }
                object = redisCacheProvider.get(shopCode_businessCode);
            }
            return object.get("cookie");
        }

        public static String getShopName(String shopCode_businessCode, RedisCacheProvider redisCacheProvider) {
            LinkedHashMap<String, String> object = redisCacheProvider.get(shopCode_businessCode);
            while (object == null) {
                try {
                    Thread.currentThread().sleep(60000);
                } catch (InterruptedException e) {
                    LOG.error(e.getMessage());
                }
                object = redisCacheProvider.get(shopCode_businessCode);
            }
            return object.get("shopName");
        }
    }
}