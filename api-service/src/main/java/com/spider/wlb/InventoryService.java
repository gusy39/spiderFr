package com.spider.wlb;

import com.spider.models.GoodsStockFlow;

import java.util.Map;

/**
 * Created by 许巧生 on 2016/10/13.
 */
public interface InventoryService {

    /**
     * 出入库流水
     * @param shopCodes
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> getOutInStoreLog(String shopCodes, String begin, String end);

    /**
     * 进销存台账
     * @param shopCodes
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> getPurchaseSaleStorage(String shopCodes, String begin, String end);

    int InsertGoodsFlowStock(GoodsStockFlow goodsStockFlow);
}
