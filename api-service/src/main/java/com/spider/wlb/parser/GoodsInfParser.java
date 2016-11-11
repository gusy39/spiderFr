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
import com.spider.models.GoodsStockFlow;
import com.spider.utils.Logs;
import com.spider.wlb.GoodsService;
import com.spider.wlb.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 库存对账中心/进销存台账
 * <p/>
 * Created by 许巧生 on 2016/6/13.
 */
public class GoodsInfParser extends AbstractParser {
    private static final Logger LOG = LoggerFactory.getLogger(GoodsInfParser.class);

    private GoodsService goodsService;


    public GoodsInfParser(HttpClientProvider provider, TaskBean taskBean, RedisCacheProvider redisCacheProvider, Object Servicer) {
        super(provider, taskBean, redisCacheProvider,Servicer);
        this.goodsService = (GoodsService) Servicer;
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
        String total = jsonObject.getString("total");
        boolean isFirstPage = this.taskBean.isFirstPage();
        int pageSize =this.taskBean.getPageSize();
        if ("0".equals(total)) {
            LOG.warn("Total of data is zero, this taskBean : " + this.taskBean.toString());
            return;
        }
        int totalInt = Integer.valueOf(total);
        //第一页的时候进行分页
        if(isFirstPage)
        {
            int pageCount =(int)totalInt/pageSize+2;
            int totalTemp = 0;
                while (totalTemp < pageCount) {
                    TaskBean beanCopy = this.taskBean.clone();
                    beanCopy.setParserType(ParserType.GOODSINF);
                    beanCopy.setFirstPage(false);
                    beanCopy.setReqUrl(this.taskBean.getReqUrl()
                            .replaceAll("&limit=\\d+", "&limit=" + pageSize)
                            .replaceAll("&start=\\d+", "&start=" + pageSize*totalTemp));
                    sendMsg(beanCopy);
                    totalTemp ++;
                }
        }
        //解析
            JSONArray array = jsonObject.getJSONArray("data");
            GoodsInf goodsInf=new GoodsInf();
            for (int i = 0; i < array.size(); i++) {
                JSONObject data = array.getJSONObject(i);
                JSONObject bmsItemDO = data.getJSONObject("bmsItemDO");
                JSONObject bmsSCItemDO = data.getJSONObject("bmsSCItemDO");

                goodsInf.setShopName(shopName);
                goodsInf.setGoodNo(bmsItemDO.getString("itemCode"));
                goodsInf.setShopGoodName(bmsItemDO.getString("itemName"));
                goodsInf.setTbGoodId(bmsItemDO.getString("spuId"));
                goodsInf.setTbSkuId(bmsItemDO.getString("skuId"));
                goodsInf.setWlbGoodId(bmsItemDO.getString("scItemId"));
                goodsInf.setWlbGoodName(bmsSCItemDO.getString("itemDimName"));
                goodsInf.setIsSuit(bmsItemDO.getString("matchScItem"));
                goodsInf.setCreateTime(new Date());
                goodsInf.setUpdateTime(new Date());

                //如果是组合装 获取组合装详情
                if(goodsInf.getWlbGoodId()!=null)
                {
                    TaskBean taskBean=new TaskBean();
                    taskBean.setParserType(ParserType.GOODSINFSUIT);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("shopCode", this.taskBean.getParams().get("shopCode"));
                    map.put("businessCode", "_NWLB");
                    taskBean.setParams(map);
                    String url = "http://base.work.cainiao.com/manage/goods/item/subScItemDetail.do?start=0&id=0&limit=10&csrfId=";
                    taskBean.setReqUrl(url.replaceAll("&id=\\d+", "&id=" + goodsInf.getWlbGoodId()));
                    sendMsg(taskBean);

                }

                if (goodsService.GoodsInfInsert(goodsInf) > 0) {
                    Logs.insert
                            .info("Insert into goods_inf success. reqUrl = {}",
                                    this.taskBean.getReqUrl());
                } else {
                    LOG.error("insert into goods_inf fail, bean={}", this.taskBean.toString());
                }
                //发起组合详情查询

            }
    }
}
