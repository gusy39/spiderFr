package com.beidou.mapper;

import com.beidou.common.persistent.BaseMapper;
import com.beidou.models.OrlTest;

import java.util.List;

public interface OrlTestMapper extends BaseMapper {
    int deleteByPrimaryKey(Short id);

    int insert(OrlTest record);

    int insertSelective(OrlTest record);

    OrlTest selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(OrlTest record);

    int updateByPrimaryKey(OrlTest record);

    List<OrlTest> queryBySelectiveByPage(OrlTest record);
}