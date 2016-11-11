package com.spider.mapper;

import com.beidou.common.persistent.BaseMapper;
import com.spider.models.GoodsInf;

public interface GoodsInfMapper extends BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsInf record);

    int insertSelective(GoodsInf record);

    GoodsInf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsInf record);

    int updateByPrimaryKey(GoodsInf record);
}