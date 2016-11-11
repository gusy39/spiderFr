package com.beidou.controller.wlb;

import com.beidou.common.dto.Pager;
import com.beidou.common.dto.PagerHelper;
import com.beidou.controller.AppController;
import com.beidou.spring.interceptor.AuthPassport;
import com.beidou.spring.interceptor.ResTypeEnum;
import com.spider.wlb.GoodsService;
import com.spider.wlb.InventoryService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 16-1-19.
 */
@Controller
public class wlbController extends AppController {

    private static final Logger logger=Logger.getLogger(wlbController.class);

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private GoodsService goodsService;
    /**
     * 库存日报
     * @param shopCodes
     * @param begin
     * @param end
     * @return
     */
    @RequestMapping("/inventory/getOutInStoreLog")
    @AuthPassport(isValidate = false,resType = ResTypeEnum.JSON)
    public Object getComments( String shopCodes,String begin,String end){


       Map<String,Object> ret = inventoryService.getOutInStoreLog(shopCodes, begin, end);
        return getAjaxSuccess(ret);
    }

    /**
     * 商品基本信息
     * @param shopCodes
     * @return
     */
    @RequestMapping("/inventory/goodsInf")
    @AuthPassport(isValidate = false,resType = ResTypeEnum.JSON)
    public Object goodsInf( String shopCodes){

        Map<String,Object> ret = goodsService.SkuRuleGroupQuery(shopCodes);
        return getAjaxSuccess(ret);
    }


}
