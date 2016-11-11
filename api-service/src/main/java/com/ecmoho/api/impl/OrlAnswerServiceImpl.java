package com.ecmoho.api.impl;


import com.beidou.common.dto.Pager;
import com.beidou.common.persistent.BaseDaoImpl;
import com.beidou.common.persistent.BaseMapper;
import com.beidou.mapper.OrlAnswerMapper;
import com.beidou.mapper.OrlTestMapper;
import com.beidou.models.OrlAnswer;
import com.beidou.models.OrlTest;
import com.ecmoho.api.OrlAnswerService;
import com.ecmoho.api.OrlTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 16-1-19.
 */
@Service("orlAnswerServiceImpl")
public class OrlAnswerServiceImpl extends BaseDaoImpl<OrlAnswer,Integer> implements OrlAnswerService {


    @Autowired
    private OrlAnswerMapper orlAnswerMapper;

    @Override
    public BaseMapper getSupperBaseMapper() {
        return orlAnswerMapper;
    }


    @Override
    public Pager<OrlAnswer> getListMap(OrlAnswer condition,Pager page)
    {

        Pager<OrlAnswer> orlAnswerPager=this.queryBySelective(page,condition);

        List<Map<String,Object>> orlAnswerNews=new ArrayList<>();
        for(OrlAnswer orlAnswer:orlAnswerPager.getResult() )
        {
           Map<String,Object> orlAnswerMap =new HashMap<>();
            orlAnswerMap.put("answer",orlAnswer.getFanswer());
            orlAnswerMap.put("seq",orlAnswer.getFseq());
            orlAnswerNews.add(orlAnswerMap);
        }
        orlAnswerPager.setResultMap(orlAnswerNews);
        return orlAnswerPager;
    }


}
