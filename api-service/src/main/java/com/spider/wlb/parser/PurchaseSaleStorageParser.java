package com.spider.wlb.parser;

import com.alibaba.fastjson.JSONObject;
import com.spider.bean.ParserType;
import com.spider.bean.TaskBean;
import com.spider.cache.redis.RedisCacheProvider;
import com.spider.http.HttpClientProvider;
import com.spider.AbstractParser;
import com.spider.wlb.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * 库存对账中心/进销存台账
 * <p/>
 * Created by 许巧生 on 2016/6/13.
 */
public class PurchaseSaleStorageParser extends AbstractParser {
    private static final Logger LOG = LoggerFactory.getLogger(PurchaseSaleStorageParser.class);

    private InventoryService InventoryService;

//    public PurchaseSaleStorageParser(HttpClientProvider provider, TaskBean taskBean, RedisCacheProvider redisCacheProvider, Object InventoryService) {
//        this(provider, taskBean, redisCacheProvider);
//        this.InventoryService = (InventoryService) InventoryService;
//    }

    public PurchaseSaleStorageParser(HttpClientProvider provider, TaskBean taskBean, RedisCacheProvider redisCacheProvider, Object InventoryServicer) {
        super(provider, taskBean, redisCacheProvider,InventoryServicer);
    }

    @Override
    protected void dealOK() {
        String str = null;
        try {
            str = new String(result.getContent(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("Encoded fail by bean: {},{}", this.taskBean.toString(), e.getMessage());
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(str);
        String total = jsonObject.getString("total");
        if ("0".equals(total)) {
            LOG.warn("Total of data is zero, this taskBean : " + this.taskBean.toString());
            return;
        }
        int totalInt = Integer.valueOf(total);
        int totalTemp = 0;
        if (totalInt > 1000) {
            while (totalInt > 1000 && totalTemp < totalInt) {
                TaskBean beanCopy = this.taskBean.clone();
                beanCopy.setParserType(ParserType.PURCHASESALESTORAGE_INSERT);
                beanCopy.setReqUrl(this.taskBean.getReqUrl()
                        .replaceAll("&start=\\d+", "&start=" + totalTemp)
                        .replaceAll("&limit=\\d+", "&limit=" + 1000));
                sendMsg(beanCopy);
                totalTemp += 1000;
            }
        } else {
            TaskBean beanCopy = this.taskBean.clone();
            beanCopy.setParserType(ParserType.PURCHASESALESTORAGE_INSERT);
            beanCopy.setReqUrl(this.taskBean.getReqUrl()
                    .replaceAll("&limit=\\d+", "&limit=" + totalInt));
            sendMsg(beanCopy);
        }
    }
}
