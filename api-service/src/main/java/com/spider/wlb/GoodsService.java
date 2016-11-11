package com.spider.wlb;

import com.spider.models.GoodsInf;
import com.spider.models.GoodsInfSuit;
import com.spider.models.GoodsStockFlow;

import java.util.Map;

/**
 * Created by 许巧生 on 2016/10/13.
 */
public interface GoodsService {


    Map<String, Object> SkuRuleGroupQuery(String shopCodes);

    int GoodsInfInsert(GoodsInf goodsInf);

    int GoodsSuitInfInsert(GoodsInfSuit goodsInfSuit);
}
