package com.spider.http;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

//用于进行Https请求的HttpClient
public class SSLClient extends DefaultHttpClient {

    public SSLClient(){
        super();
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = this.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }
}

//
//package com.vdlm.spider.http;
//
//        import java.security.KeyManagementException;
//        import java.security.NoSuchAlgorithmException;
//        import java.security.cert.CertificateException;
//        import java.security.cert.X509Certificate;
//        import javax.net.ssl.SSLContext;
//        import javax.net.ssl.TrustManager;
//        import javax.net.ssl.X509TrustManager;
//
//        import org.apache.http.HttpVersion;
//        import org.apache.http.conn.ClientConnectionManager;
//        import org.apache.http.conn.scheme.PlainSocketFactory;
//        import org.apache.http.conn.scheme.Scheme;
//        import org.apache.http.conn.scheme.SchemeRegistry;
//        import org.apache.http.conn.ssl.SSLSocketFactory;
//        import org.apache.http.impl.client.DefaultHttpClient;
//        import org.apache.http.impl.conn.PoolingClientConnectionManager;
//        import org.apache.http.params.BasicHttpParams;
//        import org.apache.http.params.CoreConnectionPNames;
//        import org.apache.http.params.HttpParams;
//        import org.apache.http.params.HttpProtocolParams;
//
////用于进行Https请求的HttpClient
//public class SSLClient{
//
//    private static DefaultHttpClient httpClient = null;
//
//    public static DefaultHttpClient getHttpClient() {
//
//        if (httpClient == null) {
//            synchronized (SSLClient.class) {
//                if (httpClient == null) {
//                    // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9
//                    HttpParams params = new BasicHttpParams();
//                    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//                    HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
//                    HttpProtocolParams.setUseExpectContinue(params, true);
//
//                    //设置连接超时时间
//                    int REQUEST_TIMEOUT = 30 * 1000;  //设置请求超时10秒钟
//                    int SO_TIMEOUT = 30 * 1000;       //设置等待数据超时时间10秒钟
//                    //HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
//                    //HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
//                    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);
//                    params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
//
//                    //设置访问协议
//                    SchemeRegistry schreg = new SchemeRegistry();
//                    schreg.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
//                    schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
//
//                    //多连接的线程安全的管理器
//                    PoolingClientConnectionManager pccm = new PoolingClientConnectionManager(schreg);
//                    pccm.setDefaultMaxPerRoute(20); //每个主机的最大并行链接数
//                    pccm.setMaxTotal(100);          //客户端总并行链接最大数
//
//                    httpClient = new DefaultHttpClient(pccm, params);
//                }
//            }
//        }
//        return httpClient;
//    }
//    //    public SSLClient(){
////        super();
////        try {
////            SSLContext ctx = SSLContext.getInstance("TLS");
////            X509TrustManager tm = new X509TrustManager() {
////                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
////                }
////
////                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
////                }
////
////                public X509Certificate[] getAcceptedIssuers() {
////                    return null;
////                }
////            };
////            ctx.init(null, new TrustManager[]{tm}, null);
////            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
////            ClientConnectionManager ccm = this.getConnectionManager();
////            SchemeRegistry sr = ccm.getSchemeRegistry();
////            sr.register(new Scheme("https", 443, ssf));
////        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////        }catch (KeyManagementException e) {
////            e.printStackTrace();
////        }
////
////    }
//
//
//
//}
