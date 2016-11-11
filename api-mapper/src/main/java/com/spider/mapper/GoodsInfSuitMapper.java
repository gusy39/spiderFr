package com.spider.mapper;

import com.beidou.common.persistent.BaseMapper;
import com.spider.models.GoodsInfSuit;

public interface GoodsInfSuitMapper extends BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsInfSuit record);

    int insertSelective(GoodsInfSuit record);

    GoodsInfSuit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsInfSuit record);

    int updateByPrimaryKey(GoodsInfSuit record);
}