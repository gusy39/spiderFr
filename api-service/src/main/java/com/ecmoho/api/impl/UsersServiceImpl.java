package com.ecmoho.api.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beidou.common.dto.Pager;
import com.beidou.common.persistent.BaseDaoImpl;
import com.beidou.common.persistent.BaseMapper;
import com.beidou.common.sign.SignM;
import com.beidou.common.util.HttpClientUtils;
import com.beidou.mapper.ShopCountMapper;
import com.beidou.mapper.UsersMapper;
import com.beidou.models.ShopCount;
import com.beidou.models.Users;
import com.bstek.dorado.data.provider.Page;
import com.ecmoho.api.ErpService;
import com.ecmoho.api.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 16-1-19.
 */
@Service("usersServiceImpl")
public class UsersServiceImpl extends BaseDaoImpl<Users,Integer> implements UsersService{


    @Autowired
    private UsersMapper usersMapper;

    @Override
    public BaseMapper getSupperBaseMapper() {
        return usersMapper;
    }

    /**
     * 获取用户列表
     * @param conditon 查询条件
     * @param page
     * @return
     */
    @Override
    public Pager<Users> getList(Users conditon, Pager<Users> page)
    {
        Pager<Users> usersList =this.queryBySelective(page,conditon);

        return  usersList;
    }
}
