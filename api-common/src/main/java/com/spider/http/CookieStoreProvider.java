/**
 * 
 */
package com.spider.http;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *
 * </pre>
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 10:06:47 PM May 5, 2015
 */
public class CookieStoreProvider {

	ConcurrentHashMap<String, CookieStore> cookieStores = new ConcurrentHashMap<String, CookieStore>();

	/**
	 * 每个cookie_key分配一个cookie
	 * @param cookie_key shopCode
	 * @return
	 */
	public CookieStore provide(String cookie_key) {

		if(cookie_key == null){
			return  new BasicCookieStore();
		}
		CookieStore result = new BasicCookieStore();

		CookieStore old = cookieStores.putIfAbsent(cookie_key, result);
		if (old != null) {
			return old;
		}

		return result;
	}

	/**
	 * 清空cookieStores的缓存
	 */
	public void clear(){
		cookieStores = new ConcurrentHashMap<String, CookieStore>();
	}

}
