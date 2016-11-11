package com.beidou.mapper;

import com.beidou.common.persistent.BaseMapper;
import com.beidou.models.OrlAnswer;

public interface OrlAnswerMapper extends BaseMapper {
    int insert(OrlAnswer record);

    int insertSelective(OrlAnswer record);
}