package com.ecmoho.lbs.impl;


import com.beidou.common.dto.Pager;
import com.beidou.common.exception.ServiceException;
import com.beidou.common.util.MD5Util;
import com.beidou.lbs.Point;
import com.beidou.lbs.PointRgc;
import com.beidou.lbs.PointRgc2;
import com.ecmoho.lbs.LBS;
import com.ecmoho.lbs.LbsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ecmoho.lbs.LBS.*;

/**
 * Created by Administrator on 16-1-19.
 */
@Service("LbsSearchService")
public class LbsSearchServiceImpl implements LbsSearchService {

    @Override
    public PointRgc2 getRgc2(String lng, String lat) {
        if(lng == null || lat ==null){
            return null;
        }

        PointRgc2 pointRgc2 = requestHttpRgc2(lng,lat);

        return pointRgc2;
    }

    @Override
    public PointRgc getRec(String lng, String lat) {
        if(lng == null || lat == null){
            return null;
        }

        PointRgc pointRgc = requestHttpRgc(lng,lat);

        return pointRgc;
    }

    @Override
    public PointRgc getIp(String ip) {
        if(ip == null){
            return null;
        }
        PointRgc pointRgc = requestHttpIp(ip);

        return pointRgc;
    }



}
