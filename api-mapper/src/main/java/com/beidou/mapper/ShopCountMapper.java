package com.beidou.mapper;

import com.beidou.common.persistent.BaseMapper;
import com.beidou.models.ShopCount;

public interface ShopCountMapper extends BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopCount record);

    int insertSelective(ShopCount record);

    ShopCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopCount record);

    int updateByPrimaryKey(ShopCount record);
}