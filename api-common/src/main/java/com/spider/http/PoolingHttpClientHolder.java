/**
 * 
 */
package com.spider.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

/**
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 10:41:09 AM Jul 22, 2014
 */
class PoolingHttpClientHolder extends HttpClientHolder {

	private PoolingClientConnectionManager connMgr;

	public 	PoolingHttpClientHolder(CookieStoreProvider cookieStoreProvider ,IpCookiePair pair, String userAgent, String refer) {
		super(cookieStoreProvider, pair, userAgent, refer);
	}

	public PoolingHttpClientHolder(CookieStoreProvider cookieStoreProvider ,IpCookiePair pair, String userAgent, String refer, DefaultHttpClient httpclient) {
		super(cookieStoreProvider, pair, userAgent, refer, httpclient);
	}

	@Override
	HttpClient createHttpClient() {
		this.connMgr = new PoolingClientConnectionManager();
		this.connMgr.setDefaultMaxPerRoute(50);
		this.connMgr.setMaxTotal(500);
		return new DefaultHttpClient(connMgr);
	}

	@Override
	public void shutdown() {
		if (this.connMgr != null) {
			try {
				this.connMgr.shutdown();
			}
			catch (Exception ignore) {
			}
		}
	}
}
