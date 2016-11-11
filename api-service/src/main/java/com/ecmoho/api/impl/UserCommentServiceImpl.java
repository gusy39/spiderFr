package com.ecmoho.api.impl;


import com.beidou.common.dto.Pager;
import com.beidou.common.persistent.BaseDao;
import com.beidou.common.persistent.BaseDaoImpl;
import com.beidou.common.persistent.BaseMapper;
import com.beidou.common.service.BaseServiceImpl;
import com.beidou.mapper.UserCommentMapper;
import com.beidou.models.UserComment;
import com.ecmoho.api.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 16-1-19.
 */
@Service("userCommentService")
public class UserCommentServiceImpl extends BaseDaoImpl<UserComment,Integer> implements UserCommentService{


    @Autowired
    private UserCommentMapper userCommentMapper;

    @Override
    public BaseMapper getSupperBaseMapper() {
        return userCommentMapper;
    }
    /**
     * 获取列表
     * @param content
     * @return
     */
    @Override
    public Pager<UserComment> getList(UserComment condition,Pager<UserComment> page)
    {

        Pager<UserComment> userComments =this.queryBySelective(page,condition);



        return userComments;
    }



}
