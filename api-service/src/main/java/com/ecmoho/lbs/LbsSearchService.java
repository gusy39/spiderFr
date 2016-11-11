package com.ecmoho.lbs;

import com.beidou.common.dto.Pager;
import com.beidou.lbs.Point;
import com.beidou.lbs.PointRgc;
import com.beidou.lbs.PointRgc2;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 16-1-19.
 */
public interface LbsSearchService {


    /**
     *  REG2逆地理城市周边信息
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    PointRgc2 getRgc2(String lng, String lat);

    /**
     *  REG逆地理城市信息
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    PointRgc getRec(String lng, String lat);

    /**
     *  ip获取地址
     * @param ip
     * @return
     */
    PointRgc getIp(String ip);

}
