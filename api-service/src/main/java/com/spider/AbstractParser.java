/**
 *
 */
package com.spider;

import com.spider.bean.Parser;
import com.spider.bean.ParserType;
import com.spider.bean.TaskBean;
import com.spider.cache.redis.RedisCacheProvider;
import com.spider.cookie.SpiderCookie;
import com.spider.http.HttpClientInvoker;
import com.spider.http.HttpClientProvider;
import com.spider.http.HttpInvokeResult;
import com.spider.http.IpCookiePair;
import com.spider.rabbitmq.exception.SendRefuseException;
import com.spider.rabbitmq.impl.DefaultEventController;
import com.spider.utils.AuthUtils;
import com.spider.utils.Logs;
import com.spider.wlb.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 12:13:19 AM Jul 20, 2014
 */
public abstract class AbstractParser implements Parser {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractParser.class);

    protected HttpClientProvider provider;
    protected TaskBean taskBean;

    protected HttpClientInvoker invoker;
    protected HttpInvokeResult result;
    protected RedisCacheProvider redisCacheProvider;

    public AbstractParser(HttpClientProvider provider, TaskBean taskBean, RedisCacheProvider redisCacheProvider, Object InventoryService) {
        this.provider = provider;
        this.taskBean = taskBean;
        this.redisCacheProvider = redisCacheProvider;
    }

    @Override
    public void parse() {
        //判断是否是最后一条消息
        isLastMsg();

        // 轮询获取一个安全的IP地址
        final String ip = this.provider.getIpPools().pollingSafeAvaliableIp(this.taskBean.getParserType());
        Object businessCodeObject = this.taskBean.getParams().get("businessCode");
        String businessCodeStr = businessCodeObject == null ? "_wuliubao" : businessCodeObject.toString();
        IpCookiePair pair = new IpCookiePair(ip,
                SpiderCookie.CookieHolder.getCookie(
                        this.taskBean.getParams().get("shopCode").toString() + businessCodeStr, redisCacheProvider),
                this.taskBean.getParams().get("shopCode").toString());
        this.invoker = this.provider.provideSSL(this.taskBean.getParserType(), this.taskBean.getReqUrl(), null, pair);

        // 不可用，则重试
        if (!this.invoker.isUsable()) {
            LOG.warn("can not get usable invoker from provider, ignore OrderParser:{}", this.taskBean);
            return;
        }

        this.result = this.invoker.invoke();

        if (result.getException() != null) {
            if ((result.getException().getMessage().contains("Attempted read from closed stream") && 503 == this.result.getStatusCode()) ||
                    (result.getException().getMessage().contains("Read timed out") && this.result.isOK()) ||
                    ((result.getException().getMessage().contains("peer not authenticated") ||
                            result.getException().getMessage().contains("Connection to https")) && -1 == this.result.getStatusCode())) {
                retry();
            } else if (302 == result.getStatusCode()) {
                Logs.status_302.error("proxy ip:" + this.invoker.getIp() + ", bean: " + this.taskBean +
                        "," + this.result.getException().getMessage());
            } else {
                LOG.error("Request fails,statusCode:" + result.getStatusCode() + ",bean:" + this.taskBean + ","
                        + result.getException().getMessage());
            }
            return;
        }

        // 成功访问页面
        if (this.result.isOK()) {
            if (this.result.getContent() == null || this.result.getContent().length == 0) {
                Logs.status_302.error("Content of result is empty,cookie mabey invalidate,this taskBean : " + this.taskBean);
                return;
            }
            dealOK();
        }
        // 404错误，丢弃任务
        else if (this.result.getStatusCode() == 404) {
            LOG.warn("Error to invoke:{}, proxy ip:{}", this.taskBean, this.invoker.getIp());
        }
        // 禁止访问(ip失效)
        else if (result.getStatusCode() == 400 || result.getStatusCode() == 401 || result.getStatusCode() == 402
                || result.getStatusCode() == 403 || result.getStatusCode() == 405) {
            LOG.warn("Error to invoke:" + result + ",will disable proxy ip:" + this.invoker.getIp(),
                    result.getException().getMessage());

//            this.provider.disable(this.invoker);
        }
        // 需要鉴权则让当前代理闲置一下
        else if (AuthUtils.isAuthResponse(this.result.getResponse())) {
            LOG.warn("Redirect to authorize when invoke:" + result + ",will disable proxy ip:" + this.invoker.getIp(),
                    result.getException().getMessage());

//            this.provider.disable(this.invoker);
        }
        // 未知情况
        else {
            LOG.error("Error to invoke:" + this.result + ", proxy ip:" + this.invoker.getIp() + ", ignore: "
                    + this.taskBean + "StatusCode:" + this.result.getStatusCode(), this.result.getException().getMessage());
        }
    }

    /**
     * 发送消息
     *
     * @param bean
     * @throws SendRefuseException
     */
    protected void sendMsg(TaskBean bean) {
        sendMsg(bean, taskBean.getParams().get("shopCode").toString());
    }

    /**
     * 发送消息
     *
     * @param bean
     * @throws SendRefuseException
     */
    protected void sendMsg(TaskBean bean, String suffixQName) {
        try {
            DefaultEventController.getInstance(null).getEopEventTemplate()
                    .send("queue_" + suffixQName, "exchange_wlb", bean);
        } catch (SendRefuseException e) {
            LOG.error("add to rabbitmq fail, bean={}", bean, e.getMessage());
        }
    }

    /**
     * 判定大概什么时候结束
     */
    private void isLastMsg() {

        if (this.taskBean.getParams().containsKey("last")
                && "true".equals(this.taskBean.getParams().get("last").toString())) {
            TaskBean bean = new TaskBean();
            bean.setParserType(ParserType.LAST_MSG);
            sendMsg(bean);
        }
    }

    /**
     * 返回结果为200
     */
    protected abstract void dealOK();

    protected void retry() {
        if (this.taskBean.isRetry()) {
            if (this.taskBean.getRetryTimes() < 10) {
                this.taskBean.retryTimes();
                sendMsg(taskBean);
            } else {
                LOG.error("tried to reach 10 times by bean{}", this.taskBean);
            }
        } else {
            LOG.error("Not allowed to try by bean{}", this.taskBean);
        }
    }

}
