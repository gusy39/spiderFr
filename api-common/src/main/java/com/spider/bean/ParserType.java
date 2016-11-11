package com.spider.bean;

/**
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 5:52:08 PM Jul 26, 2014
 */
public enum ParserType {

    LAST_MSG("", "LastMsgParser"),//结束
    STOCK("order", "StockParser"), //库存流水明细
    STOCK_INSERT("order", "StockInsertParser"), //库存流水明细——插入数据
    INVENTORY("order", "InventoryParser"),  //进销存报表按月、日
    INVENTORY_INSERT("order", "InventoryInsertParser"),  //进销存报表按月、日--插入数据
    GOODS("goods", "GoodsParser"),  //查询商品
    GOODS_INSERT("goods", "GoodsInsertParser"),  //插入商品
    GOODS_SUB_INSERT("goods", "SubGoodsInsertParser"),  //插入子商品
    TRADE_INSERT("order", "TradeInsertParser"),  //插入交易订单
    TRADE_ORDER_INSERT("order", "TradeOrderInsertParser"),  //插入交易订单详情

    //新的wlb重新定义解析类型
    OUTINSTORE("inventory", "OutInStoreParser"),//出入库流水台账
    OUTINSTORE_INSERT("inventory", "OutInStoreInsertParser"),//出入库流水台账——插入数据
    PURCHASESALESTORAGE("inventory", "PurchaseSaleStorageParser"),//进销存台账
    PURCHASESALESTORAGE_INSERT("inventory", "PurchaseSaleStorageInsertParser"),//进销存台账——插入数据

    GOODSINF("goodsService", "GoodsInfParser"),//物流宝货品

    GOODSINFSUIT("goodsService", "GoodsInfSuitParser"),//物流宝货品

    ORDER("orderManager", "OrderParser"),//获取主订单
    ORDER_INSERT("orderManager", "OrderInsertParser"),//主订单插入
    ORDER_ITEMS_INSERT("orderManager", "OrderItemsInsertParser");//详细订单插入

    // 成员变量
    private String service;//对应业务逻辑层
    private String parser;//对应解析层

    // 构造方法
    private ParserType(String service, String parser) {
        this.service = service;
        this.parser = parser;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getParser() {
        return parser;
    }

    public void setParser(String parser) {
        this.parser = parser;
    }
}
