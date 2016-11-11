package com.ecmoho.api;

import com.beidou.common.dto.Pager;
import com.beidou.models.ShopCount;
import com.beidou.models.UserComment;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 16-1-19.
 */
public interface ErpService {

    public List<ShopCount> getShopCount(Map<String,Object> params);

    public boolean saveShopCount(List<ShopCount> shopCounts);
}
