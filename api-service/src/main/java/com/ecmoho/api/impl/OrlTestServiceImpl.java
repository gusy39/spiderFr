package com.ecmoho.api.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beidou.common.dto.Pager;
import com.beidou.common.persistent.BaseDaoImpl;
import com.beidou.common.persistent.BaseMapper;
import com.beidou.common.sign.SignM;
import com.beidou.common.util.HttpClientUtils;
import com.beidou.mapper.OrlAnswerMapper;
import com.beidou.mapper.OrlTestMapper;
import com.beidou.mapper.ShopCountMapper;
import com.beidou.models.OrlAnswer;
import com.beidou.models.OrlTest;
import com.beidou.models.ShopCount;
import com.ecmoho.api.ErpService;
import com.ecmoho.api.OrlTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 16-1-19.
 */
@Service("orlTestServiceImpl")
public class OrlTestServiceImpl extends BaseDaoImpl<OrlTest,Integer> implements OrlTestService {


    @Autowired
    private OrlTestMapper orlTestMapper;

    @Override
    public BaseMapper getSupperBaseMapper() {
        return orlTestMapper;
    }

    /**
     * 获取数据
     * @param
     * @return
     */
    @Override
    public List<OrlTest> getList(OrlTest condition,Pager page)
    {

        List<OrlTest> orlTestPager=orlTestMapper.queryBySelectiveByPage(condition);

        List<OrlTest> orlTestNews=new ArrayList<>();
        for(OrlTest orlTest:orlTestPager )
        {
            OrlTest orlTestNew = new OrlTest();
            orlTestNew.setUsername(orlTest.getUsername());
            orlTestNew.setSex(orlTest.getSex());
            orlTestNew.removeExtCondition("password");
            orlTestNews.add(orlTestNew);
        }

        return orlTestNews;
    }

    @Override
    public List<Map<String,Object>> getListMap(OrlTest condition,Pager page)
    {

        List<OrlTest> orlTestPager=orlTestMapper.queryBySelectiveByPage(condition);

        List<Map<String,Object>> orlTestNews=new ArrayList<>();
        for(OrlTest orlTest:orlTestPager )
        {
           Map<String,Object> orlTestMap =new HashMap<>();
            orlTestMap.put("u",orlTest.getUsername());
            orlTestMap.put("sex",orlTest.getSex());
            orlTestNews.add(orlTestMap);
        }
        return orlTestNews;
    }


}
