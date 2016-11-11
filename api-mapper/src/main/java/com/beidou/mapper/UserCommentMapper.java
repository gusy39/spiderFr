package com.beidou.mapper;

import com.beidou.common.persistent.BaseMapper;
import com.beidou.models.UserComment;

public interface UserCommentMapper extends BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserComment record);

    int insertSelective(UserComment record);

    UserComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserComment record);

    int updateByPrimaryKey(UserComment record);
}