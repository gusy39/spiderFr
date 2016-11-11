package com.spider.rabbitmq;

import com.spider.bean.TaskBean;
import com.spider.cache.redis.RedisCacheProvider;
import com.spider.http.HttpClientProvider;
import com.spider.bean.Parser;
import com.spider.rabbitmq.exception.SendRefuseException;
import com.spider.rabbitmq.impl.DefaultEventController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

/**
 * Created by 许巧生 on 2016/7/6.
 */
public class RabbitMqInit {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMqInit.class);

    private EventController eventController;

    public static EventTemplate eventTemplate;

    private String port;

    private String serverhost;

    private String userName;

    private String password;

    private String vhost;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServerhost() {
        return serverhost;
    }

    public void setServerhost(String serverhost) {
        this.serverhost = serverhost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

    @Autowired
    private RedisCacheProvider redisCacheProvider;

    private HttpClientProvider provider;

    @Value("#{'${rabbitmq.shops}'.split(',')}")
    private List<String> shops;

    private Map<String, Object> serviceMap;

    private Map<String, String> parserMap;

    public void init() {

        int port = StringUtils.isNoneBlank(this.getPort()) ?
                Integer.valueOf(this.getPort()) : 0;

        EventControlConfig config = new EventControlConfig(
                this.getServerhost(),
                port,
                this.getUserName(),
                this.getPassword(),
                this.getVhost());
        eventController = DefaultEventController.getInstance(config);

        this.eventTemplate=eventController.getEopEventTemplate();

        if (shops == null) {
            return;
        }
        for (String shopCode : shops) {
            eventController.add("queue_" + shopCode, "exchange_wlb", processer);
//            eventController.add("queue_" + shopCode + "_order", "exchange_wlb", processer);
//            eventController.add("queue_goods_" + shopCode, "exchange_priceControl", processer);
        }
        eventController.start();
    }

    private EventProcesser processer = new EventProcesser() {

        @Override
        public void process(Object msg) {
            if (msg == null) {
                LOG.error("message is null.");
                return;
            }
            TaskBean bean = (TaskBean) msg;

            Parser parser = null;
            try {
                if (bean.getParserType() == null) {
                    LOG.error("this parserType is null where bean :", bean);
                    return;
                }
                Class cls = Class.forName(parserMap.get(bean.getParserType().getParser()));

                Constructor[] cons = cls.getDeclaredConstructors();
                for (int i = 0; i < cons.length; i++) {
                    Constructor con = cons[i]; //取出第i个构造方法。
                    Class[] parameterTypes = con.getParameterTypes();

                    if (3 == parameterTypes.length) {
                        Class[] paramTypes = {HttpClientProvider.class, TaskBean.class, Object.class};
                        Object[] params = {provider, bean, redisCacheProvider}; // 方法传入的参数
                        parser = (Parser) con.newInstance(params);
//                        break;
                    } else if (4 == parameterTypes.length) {
                        Class[] paramTypes = {HttpClientProvider.class, TaskBean.class, Object.class};
                        Object[] params = {provider, bean, redisCacheProvider, serviceMap.get(bean.getParserType().getService())}; // 方法传入的参数
                        parser = (Parser) con.newInstance(params);
                        break;
                    } else {
                        continue;
                    }
                }

            } catch (Exception e) {
                LOG.error("find errer about class :" + parserMap.get(bean.getParserType().getParser()));
                return;
            }

            if (parser == null) {
                LOG.error("Can't find parse class");
                return;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("take from ShopTaskQueue:", bean);
            }
            try {
                parser.parse();
            } catch (Exception e) {
//                e.printStackTrace();
                if(bean.isRetry() && bean.getRetryTimes() <= 4){
                    try {
                        bean.setRetryTimes(bean.retryTimes() + 1);
                        eventTemplate.send("queue_" + bean.getParams().get("shopCode"), "exchange_wlb", bean);
                    } catch (SendRefuseException ex) {
                        LOG.error("add to rabbitmq fail, bean:", bean, e.getMessage());
                    }
                }
//                LOG.error("Error to parse the taskBean : " + bean.toString() + e.getMessage());
                return;
            }
        }
    };

    public void destroy() {
        try {
            if (LOG.isInfoEnabled()) {
                LOG.info("start to destroy " + this.getClass().getSimpleName());
            }

            this.eventController.destroy();

            if (LOG.isInfoEnabled()) {
                LOG.info("success to destroy " + this.getClass().getSimpleName());
            }
        } catch (Exception e) {
            LOG.error("fail to destroy " + this.getClass().getSimpleName());
        }
    }

    public HttpClientProvider getProvider() {
        return provider;
    }

    public void setProvider(HttpClientProvider provider) {
        this.provider = provider;
    }

    public List<String> getShops() {
        return shops;
    }

    public void setShops(List<String> shops) {
        this.shops = shops;
    }

    public Map<String, Object> getServiceMap() {
        return serviceMap;
    }

    public void setServiceMap(Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public Map<String, String> getParserMap() {
        return parserMap;
    }

    public void setParserMap(Map<String, String> parserMap) {
        this.parserMap = parserMap;
    }
}
