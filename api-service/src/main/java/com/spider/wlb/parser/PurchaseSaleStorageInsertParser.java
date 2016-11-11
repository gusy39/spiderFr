package com.spider.wlb.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spider.bean.TaskBean;
import com.spider.cache.redis.RedisCacheProvider;
import com.spider.cookie.SpiderCookie;
import com.spider.http.HttpClientProvider;
import com.spider.AbstractParser;
import com.spider.models.GoodsStockFlow;
import com.spider.utils.JdbcSqlUtils;
import com.spider.utils.Logs;
import com.spider.wlb.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存对账中心/进销存台账
 * <p/>
 * Created by 许巧生 on 2016/6/13.
 */
public class PurchaseSaleStorageInsertParser extends AbstractParser {
    private static final Logger LOG = LoggerFactory.getLogger(PurchaseSaleStorageInsertParser.class);


    private InventoryService inventoryService;

//    public PurchaseSaleStorageInsertParser(HttpClientProvider provider, TaskBean taskBean, RedisCacheProvider redisCacheProvider, Object orderService) {
//        this(provider, taskBean, redisCacheProvider);
//        this.inventoryService = (InventoryService) orderService;
//    }

    public PurchaseSaleStorageInsertParser(HttpClientProvider provider, TaskBean taskBean, RedisCacheProvider redisCacheProvider, Object orderService) {
        super(provider, taskBean, redisCacheProvider,orderService);
        this.inventoryService = (InventoryService) orderService;
    }

    @Override
    protected void dealOK() {

        String shopName = SpiderCookie.CookieHolder.getShopName(
                this.taskBean.getParams().get("shopCode").toString() +
                        this.taskBean.getParams().get("businessCode").toString(), redisCacheProvider);

        String str = null;
        try {
            str = new String(result.getContent(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("Encoded fail by bean: {},{}", this.taskBean.toString(), e.getMessage());
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONArray array = jsonObject.getJSONArray("data");
        StringBuilder sqlSB = new StringBuilder();
        GoodsStockFlow goodsStockFlow = new GoodsStockFlow();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);

//
//          String[] dateArr = object.getString("gmtCreateStr").split("-");
//            String year = dateArr[0];
//            String month = dateArr[1];
//            String day = "0";
//            if (dateArr.length > 2) {
//                day = dateArr[2];
//            }
            String goods_name = object.getString("itemName");
            goodsStockFlow.setGoodsName(goods_name);

            String goods_no_platform = object.getString("scItemId");
            goodsStockFlow.setGoodsNoPlatform(goods_no_platform);
            String goods_no_wlb = object.getString("itemCode");
            goodsStockFlow.setGoodsNoWlb(goods_no_wlb);
            String warehouse = object.getString("storeName");
            goodsStockFlow.setWarehouse(warehouse);

            if (inventoryService.InsertGoodsFlowStock(goodsStockFlow) > 0) {
                Logs.insert
                        .info("Insert into goods_stock_statics success. reqUrl = {}",
                                this.taskBean.getReqUrl());
            } else {
                LOG.error("insert into goods_stock_flow fail, bean={}", this.taskBean.toString());
            }

        }


    }
}
