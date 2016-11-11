/**
 *
 */
package com.spider.http;


import com.spider.utils.AuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.*;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 10:32:37 AM Jul 22, 2014
 */
class HttpClientHolder extends HttpStatics {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final HttpClient httpClient;

    /**
     * 满足ssl
     * @param pair
     * @param userAgent
     * @param refer
     */
    public HttpClientHolder(CookieStoreProvider cookieStoreProvider ,IpCookiePair pair, String userAgent, String refer) {
        this.httpClient = this.createHttpClient(cookieStoreProvider, pair, userAgent, refer);
    }

    public HttpClientHolder(CookieStoreProvider cookieStoreProvider ,IpCookiePair pair, String userAgent, String refer, DefaultHttpClient httpclient) {
        //this.httpClient = new DefaultHttpClient();
        this.httpClient = this.createHttpClient(cookieStoreProvider, pair, userAgent, refer, httpclient);
    }

    HttpClient createHttpClient(CookieStoreProvider cookieStoreProvider , final IpCookiePair pair, final String userAgent, final String refer, final DefaultHttpClient httpClient) {
        if (StringUtils.isBlank(pair.getIp())) {
            return null;
        }
        final boolean useProxy = !isLocalIp(pair.getIp());

        if (log.isDebugEnabled()) {
            log.debug("use ip:" + pair.getIp());
        }

        HttpConnectionParams.setSoKeepalive(httpClient.getParams(), true);

        HttpConnectionParams.setStaleCheckingEnabled(httpClient.getParams(), true);

        HttpConnectionParams.setTcpNoDelay(httpClient.getParams(), true);


        if (useProxy) {
            final String[] arr = StringUtils.split(pair.getIp(), ":");
            final HttpHost proxy = new HttpHost(arr[0], Integer.parseInt(arr[1]));

            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }

        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        //httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");

        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 90000);
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);

//				if (useProxy) {
//					httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
//				}
        if (httpClient instanceof DefaultHttpClient) {
            ((DefaultHttpClient) httpClient).setRedirectStrategy(new DefaultRedirectStrategy() {
                @Override
                public boolean isRedirected(HttpRequest request, HttpResponse response,
                                            HttpContext context) throws ProtocolException {
                    if (AuthUtils.isAuthResponse(response)) {
                        // 需要鉴权，redirect过去也没意义
                        return false;
                    }
                    return super.isRedirected(request, response, context);
                }
            });
        }

        final List<Header> headers = new ArrayList<Header>(5);
        headers.add(new BasicHeader("User-Agent", userAgent));
        if (userAgent.contains("Chrome")) {  // chrome
            headers.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
            headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4"));
            //headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"));
        } else { // defautl firefox
            headers.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
            headers.add(new BasicHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3"));
        }
        if (StringUtils.isNotBlank(refer)) {
            headers.add(new BasicHeader("Referer", refer));
        }
        //headers.add(new BasicHeader(CoreProtocolPNames.HTTP_CONTENT_CHARSET,"UTF8"));
        if (StringUtils.isNotBlank(pair.getCookie())) {
            headers.add(new BasicHeader("cookie", pair.getCookie()));
        }

        httpClient.getParams().setParameter(ClientPNames.DEFAULT_HEADERS, headers);
        return httpClient;
    }

    /**
     * 满足ssl
     * @param pair
     * @param userAgent
     * @param refer
     * @return
     */
    HttpClient createHttpClient(CookieStoreProvider cookieStoreProvider, final IpCookiePair pair, final String userAgent, final String refer) {
        if (StringUtils.isBlank(pair.getIp())) {
            return null;
        }
        final boolean useProxy = !isLocalIp(pair.getIp());

        if (log.isDebugEnabled()) {
            log.debug("use ip:" + pair.getIp());
        }

        // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
        HttpProtocolParams.setUseExpectContinue(params, true);

        //设置连接超时时间
        int REQUEST_TIMEOUT = 90 * 1000;  //设置请求超时90秒钟
        int SO_TIMEOUT = 90 * 1000;       //设置等待数据超时时间90秒钟
        //HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
        //HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

        //设置访问协议
        SchemeRegistry schreg = new SchemeRegistry();
        schreg.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

        //多连接的线程安全的管理器
        PoolingClientConnectionManager pccm = new PoolingClientConnectionManager(schreg);
        pccm.setDefaultMaxPerRoute(20); //每个主机的最大并行链接数
        pccm.setMaxTotal(100);          //客户端总并行链接最大数

        DefaultHttpClient httpClient2 = new DefaultHttpClient(pccm, params);

        if (httpClient2 instanceof DefaultHttpClient) {
            ((DefaultHttpClient) httpClient2).setRedirectStrategy(new DefaultRedirectStrategy() {
                @Override
                public boolean isRedirected(HttpRequest request, HttpResponse response,
                                            HttpContext context) throws ProtocolException {
                        if (AuthUtils.isAuthResponse(response)) {
                        // 需要鉴权，redirect过去也没意义
                        return false;
                    }
                    return super.isRedirected(request, response, context);
                }
            });
        }

        if (useProxy) {
            final String[] arr = StringUtils.split(pair.getIp(), ":");
            final HttpHost proxy = new HttpHost(arr[0], Integer.parseInt(arr[1]));

            httpClient2.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }

        final List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("User-Agent", userAgent));
        if (userAgent.contains("Chrome")) {  // chrome
            headers.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
            headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4"));
            //headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"));
        } else { // defautl firefox
            headers.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
            headers.add(new BasicHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3"));
        }
        if (StringUtils.isNotBlank(refer)) {
            headers.add(new BasicHeader("Referer", refer));
        }
        if (StringUtils.isNotBlank(pair.getCookie())) {
            headers.add(new BasicHeader("cookie", pair.getCookie()));
        }

        httpClient2.getParams().setParameter(ClientPNames.DEFAULT_HEADERS, headers);
        return httpClient2;
    }

    HttpClient createHttpClient() {
        return new DefaultHttpClient();
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void shutdown() {
    }

    public boolean isUsable() {
        return this.httpClient != null;
    }
}
