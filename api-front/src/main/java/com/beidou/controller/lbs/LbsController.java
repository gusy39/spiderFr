package com.beidou.controller.lbs;

import com.beidou.common.dto.Pager;
import com.beidou.common.util.StringUtil;
import com.beidou.controller.AppController;
import com.beidou.lbs.Point;
import com.beidou.lbs.PointRgc;
import com.ecmoho.lbs.LbsSearchService;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by Administrator on 16-1-19.
 */
@Controller
public class LbsController extends AppController {

    private static final Logger logger=Logger.getLogger(LbsController.class);

    @Autowired
    private LbsSearchService lbsSearchService;

    /**
     * 第三方接口直接输出
     * @param ip
     * @return
     */
    @RequestMapping("/lbs/locationIp")
    public Object getLocationIp(String ip){
        if(ip == null || ip == ""){
            return getAjaxError("参数不能为空");
        }
        logger.log(Priority.INFO,"执行获取ip地址");
        PointRgc pointRgc = lbsSearchService.getIp(ip);
        return getAjaxSuccess(pointRgc);
    }



}
