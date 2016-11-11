package com.spider.wlb.impl;

import com.spider.bean.ParserType;
import com.spider.bean.TaskBean;
import com.spider.mapper.GoodsInfMapper;
import com.spider.mapper.GoodsInfSuitMapper;
import com.spider.mapper.GoodsStockFlowMapper;
import com.spider.models.GoodsInf;
import com.spider.models.GoodsInfSuit;
import com.spider.models.GoodsStockFlow;
import com.spider.rabbitmq.RabbitMqInit;
import com.spider.rabbitmq.exception.SendRefuseException;
import com.spider.wlb.GoodsService;
import com.spider.wlb.InventoryService;
import com.spider.wlb.parser.GoodsInfSuitParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsInfMapper goodsInfMapper;

    @Autowired
    private GoodsInfSuitMapper goodsInfSuitMapper;

    @Override
    public Map<String, Object> SkuRuleGroupQuery(String shopCodes) {
        final Map<String, Object> result = new HashMap<String, Object>();


        StringBuffer sb = new StringBuffer();
        sb.append("http://base.work.cainiao.com/manage/goods/item/itemQueryManage.do?");
        sb.append("_input_charset=utf-8&start=0&limit=50&csrfId=");
        for (String shopCode : shopCodes.split(",")) {
            sendMsg(shopCode, sb.toString() , ParserType.GOODSINF, true,50);
        }

        result.put("statusCode", 200);
        return result;
    }


    @Override
    public int GoodsInfInsert(GoodsInf goodsInf)
    {
            return goodsInfMapper.insert(goodsInf);
    }

    @Override
    public int GoodsSuitInfInsert(GoodsInfSuit goodsInfSuit)
    {
        return goodsInfSuitMapper.insert(goodsInfSuit);
    }

    /**
     * 发送消息
     * @param shopCode
     */
    private void sendMsg(String shopCode, String url, ParserType parserType,boolean isFistPage,int Pagesize) {

        TaskBean taskBean = new TaskBean();
        taskBean.setParserType(parserType);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("shopCode", shopCode);
        map.put("businessCode", "_NWLB");
        taskBean.setParams(map);
        taskBean.setReqUrl(url);
        taskBean.setFirstPage(isFistPage);
        taskBean.setPageSize(Pagesize);
        try {
            RabbitMqInit.eventTemplate.send("queue_" + shopCode, "exchange_wlb", taskBean);
        } catch (SendRefuseException e) {
            log.error("add to rabbitmq fail, bean:", taskBean, e.getMessage());
        }
    }
}
