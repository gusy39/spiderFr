package com.ecmoho.api.impl;


import com.beidou.common.dto.Pager;
import com.beidou.common.exception.ServiceException;
import com.beidou.common.persistent.BaseDaoImpl;
import com.beidou.common.persistent.BaseMapper;
import com.beidou.common.sign.SignM;
import com.beidou.common.util.HttpClientUtils;
import com.beidou.mapper.ShopCountMapper;
import com.beidou.mapper.UserCommentMapper;
import com.beidou.models.ShopCount;
import com.beidou.models.UserComment;
import com.ecmoho.api.ErpService;
import com.ecmoho.api.UserCommentService;
import com.alibaba.fastjson.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 16-1-19.
 */
@Service("erpServiceImpl")
public class ErpServiceImpl extends BaseDaoImpl<ShopCount,Integer> implements ErpService{


    @Autowired
    private ShopCountMapper shopCountMapper;

    @Override
    public BaseMapper getSupperBaseMapper() {
        return shopCountMapper;
    }

    private static  String baseUrl="http://58.246.199.94:8096/openapi/public";
    private static  String appSecret="0deeb38d2d10d4039d8102634a86ed05";
    private static  String appKey="dlsc";


    /**
     * 获取数据
     * @param params
     * @return
     */
    @Override
    public List<ShopCount> getShopCount(Map<String,Object> params)
    {
        String url=baseUrl+"/pay/v1/check";
        List<ShopCount> shopCounts=new ArrayList<>();

            params.put("appkey",appKey);
            params.put("timestamp",new Date().getTime()/1000);
            params.put("sign","");
            String sign= SignM.MakeSign(params,appSecret);
            params.put("sign",sign);

            Map<String, String> sendParams = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {

                sendParams.put(entry.getKey(),entry.getValue().toString());
            }
            String request =  HttpClientUtils.sendPostRequest(url, sendParams,"UTF-8","UTF-8");

            JSONObject object = JSONObject.parseObject(request);
            String code = object.get("code").toString();
            String list =object.get("list").toString();
           JSONArray array = JSON.parseArray(list);
            Iterator<Object> arrayIterator = array.iterator();

            while (arrayIterator.hasNext()) {
                ShopCount shopCount=new ShopCount();
                JSONObject obj = (JSONObject) arrayIterator.next();
                if(obj.getString("count")!=null)
                {
                    shopCount.setCount(new BigDecimal(obj.getString("count").toString()));
                }else{
                    shopCount.setCount(new BigDecimal("0.00"));
                }
                if(obj.getString("shop_name")!=null)
                {
                    shopCount.setShopName(obj.getString("shop_name").toString());
                }
                if(obj.getString("total_in_amount")!=null)
                {
                    shopCount.setTotalInAmount(new BigDecimal(obj.getString("total_in_amount").toString()));
                }
                else{
                    shopCount.setTotalInAmount(new BigDecimal("0.00"));
                }
                if(obj.getString("total_out_amount")!=null)
                {
                    shopCount.setTotalOutAmount(new BigDecimal(obj.getString("total_out_amount").toString()));
                }
                else{
                    shopCount.setTotalOutAmount(new BigDecimal("0.00"));
                }

                shopCounts.add(shopCount);
            }

        return shopCounts;
    }


    @Transactional
    @Override
    public boolean saveShopCount(List<ShopCount> shopCounts)
    {

        for(ShopCount shopCount:shopCounts)
        {
            ShopCount condition=new ShopCount();
            condition.setShopName(shopCount.getShopName());
            condition.setTotalOutAmount(shopCount.getTotalOutAmount());
            condition.setTotalInAmount(shopCount.getTotalInAmount());
            condition.setCount(shopCount.getCount());
            condition.setCreateTime(new Date());
            condition.setUpdateTime(new Date());
            shopCountMapper.insertSelective(condition);
        }
        return true;
    }

}
