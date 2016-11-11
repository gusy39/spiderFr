package com.spider.rabbitmq;


import com.spider.rabbitmq.impl.HessionCodecFactory;
import com.spider.rabbitmq.util.StringUtils;

/**
 * User: poplar
 * Date: 14-7-4 &#x4e0b;&#x5348;5:59
 */
public class EventControlConfig {

    private final static String DEFAULT_SERVERHOST = "localhost";

    private final static int DEFAULT_PORT = 5672;

    private final static String DEFAULT_USERNAME = "guest";

    private final static String DEFAULT_PASSWORD = "guest";

    private final static int DEFAULT_PROCESS_THREAD_NUM = Runtime.getRuntime().availableProcessors() * 2;

    private static final int PREFETCH_SIZE = 1;

    private String serverHost = DEFAULT_SERVERHOST;

    private int port = DEFAULT_PORT;

    private String username = DEFAULT_USERNAME;

    private String password = DEFAULT_PASSWORD;

    private String virtualHost;

    /**
     * 和rabbitmq建立连接的超时时间
     */
    private int connectionTimeout = 0;

    /**
     * 事件消息处理线程数，默认是 CPU核数 * 2
     */
    private int eventMsgProcessNum;

    /**
     * 每次消费消息的预取值
     */
    private int prefetchSize;


    public EventControlConfig(String serverHost) {
        this(serverHost, DEFAULT_PORT);
    }

    public EventControlConfig(String serverHost, int port) {
        this(serverHost, port, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public EventControlConfig(String serverHost, int port, String userName, String password){
        this(serverHost, port, userName, password, null);
    }

    public EventControlConfig(String serverHost, int port, String userName, String password, String vhost) {
        this(serverHost, port, userName, password, vhost, 0, DEFAULT_PROCESS_THREAD_NUM, PREFETCH_SIZE, new HessionCodecFactory());
    }

    public EventControlConfig(String serverHost, int port, String username,
                              String password, String virtualHost, int connectionTimeout,
                              int eventMsgProcessNum, int prefetchSize, CodecFactory defaultCodecFactory) {
        super();
        this.serverHost = StringUtils.isBlank(serverHost) ? DEFAULT_SERVERHOST : serverHost;
        this.port = port > 0 ? port : DEFAULT_PORT;
        this.username = StringUtils.isBlank(username) ? DEFAULT_USERNAME : username;
        this.password = StringUtils.isBlank(password) ? DEFAULT_PASSWORD : password;
        this.virtualHost = virtualHost;
        this.connectionTimeout = connectionTimeout > 0 ? connectionTimeout : 0;
        this.eventMsgProcessNum = eventMsgProcessNum > 0 ? eventMsgProcessNum : DEFAULT_PROCESS_THREAD_NUM;
        this.prefetchSize = prefetchSize > 0 ? prefetchSize : PREFETCH_SIZE;
    }

    public String getServerHost() {
        return serverHost;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getEventMsgProcessNum() {
        return eventMsgProcessNum;
    }

    public int getPrefetchSize() {
        return prefetchSize;
    }

}
