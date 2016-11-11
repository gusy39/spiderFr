package com.spider.mapper;

import com.beidou.common.persistent.BaseMapper;
import com.spider.models.GoodsStockFlow;

public interface GoodsStockFlowMapper extends BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsStockFlow record);

    int insertSelective(GoodsStockFlow record);

    GoodsStockFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsStockFlow record);

    int updateByPrimaryKey(GoodsStockFlow record);
}