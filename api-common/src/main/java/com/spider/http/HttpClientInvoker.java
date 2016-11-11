/**
 *
 */
package com.spider.http;

import com.alibaba.fastjson.JSON;
import com.spider.bean.ParserType;
import com.spider.utils.Logs;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 封装了一个 HttpClient 执行类
 * </pre>
 *
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 11:07:40 AM Jul 19, 2014
 */
public class HttpClientInvoker {

    private static final Logger log = LoggerFactory.getLogger(HttpClientInvoker.class);

    private final static ResponseHandler<HttpInvokeResult> RESPONSE_HANDLER = new DefaultResponseHandler();

    private HttpClientProvider provider;
    private HttpClientHolder httpClient;
    private ParserType parserType;
    private String cookie_key;
    private String ip;
    private String userAgent;
    private String refer;

    private String url;
    private boolean invoked = true;
    private CookieStoreProvider cookieStoreProvider;

    public HttpInvokeResult invoke() {
        return invokeGet(this.url);
    }

    public HttpInvokeResult invokeGet(final String url) {
        try {
            // httpClient 是否可用
            if (!this.httpClient.isUsable()) {
                return new HttpInvokeResult();
            }

            // 执行 get 请求
            final HttpGet httpGet = new HttpGet(url);

            //		httpGet.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5 * 1000);
            httpGet.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, Boolean.TRUE);// 手动处理自动转向.
            httpGet.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, Boolean.TRUE);
            httpGet.getParams().setParameter(ClientPNames.MAX_REDIRECTS, 10);

            final HttpInvokeResult result = this.invoke(httpGet);

            // result 不会为 null
            Logs.requestInfo.info("request:{},statusCode:{},exception:{}", result.getUrl(),
                    result.getStatusCode(), ExceptionUtils.getMessage(result.getException()));

            return result;
        } finally {
            // 仅能执行一次
            this.invoked = false;
            this.httpClient.shutdown();
        }
    }

    public HttpInvokeResult invokePost(final String url, Map<String, String> params) {
        try {
            // httpClient 是否可用
            if (!this.httpClient.isUsable()) {
                return new HttpInvokeResult();
            }

            // 执行 post 请求
            final HttpPost httpPost = new HttpPost(url);

            //		httpGet.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5 * 1000);
            httpPost.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, Boolean.TRUE);// 手动处理自动转向.
            httpPost.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, Boolean.TRUE);
            httpPost.getParams().setParameter(ClientPNames.MAX_REDIRECTS, 10);

            //添加参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            } catch (UnsupportedEncodingException e) {
                log.error("the encoding isn't supported ");
            }

            final HttpInvokeResult result = this.invoke(httpPost);

            // result 不会为 null
            Logs.requestInfo.info("request:{},statusCode:{},exception:{}", result.getUrl(),
                    result.getStatusCode(), ExceptionUtils.getMessage(result.getException()));

            return result;
        } finally {
            // 仅能执行一次
            this.invoked = false;
            this.httpClient.shutdown();
        }
    }

    private HttpInvokeResult invoke(final HttpRequestBase request) {
        final String url = request.getURI().toString();

        if (log.isDebugEnabled()) {
            log.debug("invoke url:" + url);
        }

        HttpInvokeResult result;

        try {
            final CookieStore cookieStore = this.cookieStoreProvider.provide(this.cookie_key);
            final HttpContext context = new BasicHttpContext();
            context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

            HttpClient httpClient = this.httpClient.getHttpClient();

            if (log.isDebugEnabled()) {
                log.debug("before request, cookieStore: {}", JSON.toJSONString(cookieStore, true));
            }

            result = httpClient.execute(request, RESPONSE_HANDLER, context);

            if (result.getException() != null) {
                request.abort();
//                log.error("请求失败,statusCode=" + result.getStatusCode() + ",url=" + url + ","
//                        + result.getException().getMessage());
            }
            result.setUrl(request.getURI().toString());

            if (log.isDebugEnabled()) {
                log.debug("after request, cookieStore: {}", JSON.toJSONString(cookieStore, true));
            }

            request.releaseConnection();
            return result;
        } catch (final Throwable e) {
            request.abort();
            result = new HttpInvokeResult();
            result.setUrl(url);
            result.setException(e);
            result.setReason(e.getMessage());
            return result;
        } finally {
            request.reset();
        }
    }

    void setProvider(HttpClientProvider provider) {
        this.provider = provider;
    }

    void setHttpClient(HttpClientHolder httpClient) {
        this.httpClient = httpClient;
    }

    void setParserType(ParserType parserType) {
        this.parserType = parserType;
    }

    public String getCookie_key() {
        return cookie_key;
    }

    public void setCookie_key(String cookie_key) {
        this.cookie_key = cookie_key;
    }

    void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    void setRefer(String refer) {
        this.refer = refer;
    }

    public ParserType getParserType() {
        return parserType;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getRefer() {
        return refer;
    }

    public String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    public boolean isInvoked() {
        return invoked;
    }

    void setInvoked(boolean invoked) {
        this.invoked = invoked;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    void setCookieStoreProvider(CookieStoreProvider cookieStoreProvider) {
        this.cookieStoreProvider = cookieStoreProvider;
    }

    /**
     * <pre>
     * 做流控规则限制
     * </pre>
     */
    public void disable() {
        this.provider.disable(this);
    }

    /**
     * <pre>
     * 判断当前invoker是否可用
     * </pre>
     *
     * @return
     */
    public boolean isUsable() {
        return this.httpClient != null && this.httpClient.isUsable();
    }

}
