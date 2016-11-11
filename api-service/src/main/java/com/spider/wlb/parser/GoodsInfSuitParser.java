package com.spider.wlb.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spider.AbstractParser;
import com.spider.bean.ParserType;
import com.spider.bean.TaskBean;
import com.spider.cache.redis.RedisCacheProvider;
import com.spider.cookie.SpiderCookie;
import com.spider.http.HttpClientProvider;
import com.spider.models.GoodsInf;
import com.spider.models.GoodsInfSuit;
import com.spider.utils.Logs;
import com.spider.wlb.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 库存对账中心/进销存台账
 * <p/>
 * Created by 许巧生 on 2016/6/13.
 */
public class GoodsInfSuitParser extends AbstractParser {
    private static final Logger LOG = LoggerFactory.getLogger(GoodsInfSuitParser.class);

    private GoodsService goodsService;


    public GoodsInfSuitParser(HttpClientProvider provider, TaskBean taskBean, RedisCacheProvider redisCacheProvider, Object Servicer) {
        super(provider, taskBean, redisCacheProvider,Servicer);
        this.goodsService = (GoodsService) Servicer;
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
        //解析
            JSONArray array = jsonObject.getJSONArray("data");
            GoodsInfSuit goodsInfSuit = new GoodsInfSuit();
            for (int i = 0; i < array.size(); i++) {
                JSONObject data = array.getJSONObject(i);
                goodsInfSuit.setWlbGoodId(data.getString("scItemId"));
                goodsInfSuit.setNum(Integer.valueOf(data.getString("count")));
                goodsInfSuit.setWlbGoodNo(data.getString("itemCode"));
                goodsInfSuit.setWlbGoodName(data.getString("title"));
                goodsInfSuit.setCreateTime(new Date());
                goodsInfSuit.setUpdateTime(new Date());

                if (goodsService.GoodsSuitInfInsert(goodsInfSuit) > 0) {
                    Logs.insert
                            .info("Insert into goodsuit_inf success. reqUrl = {}",
                                    this.taskBean.getReqUrl());
                } else {
                    LOG.error("insert into goodsuit_inf fail, bean={}", this.taskBean.toString());
                }
                //发起组合详情查询

            }
    }
}
