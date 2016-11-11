package com.spider.wlb.impl;

import com.spider.bean.ParserType;
import com.spider.bean.TaskBean;
import com.spider.mapper.GoodsStockFlowMapper;
import com.spider.models.GoodsStockFlow;
import com.spider.rabbitmq.EventTemplate;
import com.spider.rabbitmq.RabbitMqInit;
import com.spider.rabbitmq.exception.SendRefuseException;
import com.spider.wlb.InventoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsStockFlowMapper goodsStockFlowMapper;


    @Override
    public int InsertGoodsFlowStock(GoodsStockFlow goodsStockFlow)
    {
        return goodsStockFlowMapper.insert(goodsStockFlow);
    }
    @Override
    public Map<String, Object> getOutInStoreLog(String shopCodes, String begin, String end) {
        final Map<String, Object> result = new HashMap<String, Object>();

        Calendar beginCal = Calendar.getInstance(), endCal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        getCorrectTime(begin, end, df);

        StringBuffer sb = new StringBuffer();
        sb.append("http://dip.work.cainiao.com/inventory/GetOutInStoreLogJson.do");
        sb.append("?scItemCode=&scItemName=&storeCodes=&orderCode=&begin=#{beginEnd}");
        sb.append("&end=#{beginEnd}");
        sb.append("&start=0&limit=10");
//            sb.append("&csrfId=70358833eea3");

        for (String shopCode : shopCodes.split(",")) {
            splitByDate(begin, end, beginCal, endCal, df, sb, shopCode, ParserType.OUTINSTORE);
        }

        result.put("statusCode", 200);
        return result;
    }

    @Override
    public Map<String, Object> getPurchaseSaleStorage(String shopCodes, String begin, String end) {
        final Map<String, Object> result = new HashMap<String, Object>();

        Calendar beginCal = Calendar.getInstance(), endCal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        getCorrectTime(begin, end, df);

        StringBuffer sb = new StringBuffer();
        sb.append("http://dip.work.cainiao.com/inventory/GetPurchaseSaleStorageJson.do");
        sb.append("?scItemCodes=&scItemName=&storeCodes=&begin=#{beginEnd}");
        sb.append("&end=#{beginEnd}");
        sb.append("&start=0&limit=10");

        for (String shopCode : shopCodes.split(",")) {
            splitByDate(begin, end, beginCal, endCal, df, sb, shopCode, ParserType.PURCHASESALESTORAGE);
        }

        result.put("statusCode", 200);
        return result;
    }

    /**
     * 根据天来拆分爬取页面
     *
     * @param begin
     * @param end
     * @param beginCal
     * @param endCal
     * @param df
     * @param sb
     * @param shopCode
     */
    private void splitByDate(String begin, String end, Calendar beginCal, Calendar endCal, SimpleDateFormat df, StringBuffer sb, String shopCode, ParserType parserType) {
        String[] beginArr = begin.split("-");
        beginCal.set(Integer.valueOf(beginArr[0]), Integer.valueOf(beginArr[1]) - 1, Integer.valueOf(beginArr[2]));
        String[] endArr = end.split("-");
        endCal.set(Integer.valueOf(endArr[0]), Integer.valueOf(endArr[1]) - 1, Integer.valueOf(endArr[2]));

        Calendar beginCalTemp = beginCal;

        while (beginCalTemp.before(endCal)) {
            sendMsg(shopCode, sb.toString(), df.format(beginCalTemp.getTime()), parserType);
            beginCalTemp.add(Calendar.DAY_OF_MONTH, 1);
        }

        sendMsg(shopCode, sb.toString(), end, parserType);
    }


    /**
     * 时间参数缺省修改
     *
     * @param begin
     * @param end
     */
    private void getCorrectTime(String begin, String end, SimpleDateFormat df) {

        if (StringUtils.isNoneBlank(begin) && StringUtils.isNoneBlank(end)) {

        } else if (StringUtils.isNoneBlank(begin) && StringUtils.isBlank(end)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            calendar.setTime(new Date());
            end = df.format(calendar.getTime());

        } else if (StringUtils.isBlank(begin)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            calendar.setTime(new Date());
            end = df.format(calendar.getTime());
            begin = df.format(calendar.getTime());
        }
    }

    /**
     * 发送消息
     *
     * @param beginEnd
     * @param shopCode
     */
    private void sendMsg(String shopCode, String url, String beginEnd, ParserType parserType) {

        TaskBean taskBean = new TaskBean();
        taskBean.setParserType(parserType);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("shopCode", shopCode);
        map.put("businessCode", "_NWLB");
        taskBean.setParams(map);
        url = url.replace("#{beginEnd}", beginEnd);

        taskBean.setReqUrl(url);
        try {
            RabbitMqInit.eventTemplate.send("queue_" + shopCode, "exchange_wlb", taskBean);
        } catch (SendRefuseException e) {
            log.error("add to rabbitmq fail, bean:", taskBean, e.getMessage());
        }
    }
}
