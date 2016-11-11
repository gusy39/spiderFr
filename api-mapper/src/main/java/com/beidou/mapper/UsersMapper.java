package com.beidou.mapper;

import com.beidou.common.persistent.BaseMapper;
import com.beidou.models.Users;

public interface UsersMapper extends BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
}