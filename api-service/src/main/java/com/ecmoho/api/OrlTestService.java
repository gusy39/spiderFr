package com.ecmoho.api;

import com.beidou.common.dto.Pager;
import com.beidou.models.OrlAnswer;
import com.beidou.models.OrlTest;
import com.beidou.models.ShopCount;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 16-1-19.
 */
public interface OrlTestService {

    public List<OrlTest> getList(OrlTest condition,Pager page);

    public List<Map<String,Object>> getListMap(OrlTest condition,Pager page);

}
