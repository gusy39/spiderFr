package com.ecmoho.lbs;

import com.beidou.common.dto.Pager;
import com.beidou.common.util.HttpClientUtils;
import com.beidou.common.util.JacksonHelper;
import com.beidou.common.util.JsonUtility;
import com.beidou.lbs.Point;
import com.beidou.lbs.PointRgc;
import com.beidou.lbs.PointRgc2;
import com.beidou.lbs.PointRgc2Road;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 16-1-18.
 */
public class LBS {
    private static final Logger logger = Logger.getLogger(LBS.class);

    private static final String key = "ccc8c98575213fdca60cddb1ffda216b";

    private static final String urlLs = "http://restapi.amap.com/v3/place/text?";

    private static final String urlAround = "http://restapi.amap.com/v3/place/around?";

    private static final String urlReg2 = "http://map.icttic.cn:81//SE_RGC2?st=Rgc2&uid=bdwxsh&";

    private static final String urlReg = "http://map.icttic.cn:81//SE_RGC2?st=Rgc&uid=bdwxsh&";

    private static final String urlIp = "http://map.icttic.cn:81//SE_IP?st=SE_IP&uid=bdwxsh&";

    /**
     * 搜索城市门店
     * @param city 城市
     * @param words 门店关键字
     * @param pager 分页参数
     * @return
     */
    public static List<Point> requestHttpLs(String city,String words,Pager pager){

        String urls = urlLs + "key=" + key + "&city=" + city + "&keywords=" + words + "&page="
                + pager.getPageNo()+"&extensions=all";

        Map<String,String> param = new HashMap<String,String>();

        String request =  HttpClientUtils.sendGetRequest(urls, param,"UTF-8");

        JSONObject object = JSONObject.fromObject(request);

        JSONArray array = JSONArray.fromObject(object);

        JSONObject jobj =  (JSONObject) array.get(0);

        String result = jobj.get("pois").toString();

        List<Point> po =  JsonUtility.toJavaObjectArray(result, Point.class);

        return po;
    }

    /**
     *  RGC2逆地理返回城市周边信息
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    public static PointRgc2 requestHttpRgc2(String lng,String lat){

        String point = lng + "," + lat;

        String urls = urlReg2 + "point=" + point + "&output=json";

        Map<String,String> param = new HashMap<String,String>();

        String request =  HttpClientUtils.sendPostRequest(urls, "", param, false);

        JSONObject object = JSONObject.fromObject(request);

        String status = object.get("status").toString();

        if(status.equals("error")){
            return  null;
        }

        JSONArray array = JSONArray.fromObject(object);

        JSONObject jobj =  (JSONObject) array.get(0);

        String result = jobj.get("result").toString();

        JSONObject resultObject = JSONObject.fromObject(result);

        String district = resultObject.get("district").toString();

        String roadAddress = resultObject.get("road_address").toString();

        String districtText = resultObject.get("district_text").toString();

        JSONArray arrayPoint = JSONArray.fromObject(resultObject);

        JSONObject pointsObject = (JSONObject) arrayPoint.get(0);

        String json = pointsObject.get("point").toString();

        String road = pointsObject.get("road").toString();

        PointRgc2Road rgc2Road = JsonUtility.toJavaObject(road , PointRgc2Road.class);

        PointRgc2 po =  JsonUtility.toJavaObject(json, PointRgc2.class);

        po.setDistrict(district);

        po.setRoadAddress(roadAddress);

        po.setDistrictText(districtText);

        po.setPointRgc2Road(rgc2Road);

        return po;
    }

    /**
     *  RGC逆地理返回城市信息
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    public static PointRgc requestHttpRgc(String lng,String lat){

        String point = lng + "," + lat;

        String urls = urlReg + "point=" + point + "&output=json";

        Map<String,String> param = new HashMap<String,String>();

        String request =  HttpClientUtils.sendPostRequest(urls, "", param, false);

        JSONObject object = JSONObject.fromObject(request);

        String status = object.get("status").toString();

        if(status.equals("error")){
            return  null;
        }

        JSONArray array = JSONArray.fromObject(object);

        JSONObject jobj =  (JSONObject) array.get(0);

        String result = jobj.get("result").toString();

        JSONObject resultObject = JSONObject.fromObject(result);

        String districtText = resultObject.get("district_text").toString();

        String district = resultObject.get("district").toString();

        PointRgc pointRgc = new PointRgc();

        pointRgc.setDistrictText(districtText);

        pointRgc.setDistrict(district);

        return pointRgc;
    }

    /**
     *  ip获取地址
     * @param ip
     * @return
     */
    public static PointRgc requestHttpIp(String ip){

        String urls = urlIp + "ip=" + ip + "&output=json";

        Map<String,String> param = new HashMap<String,String>();

        String request =  HttpClientUtils.sendPostRequest(urls, "", param, false);

        JSONObject object = JSONObject.fromObject(request);

        String status = object.get("status").toString();

        if(status.equals("error")){
            return  null;
        }

        JSONArray array = JSONArray.fromObject(object);

        JSONObject jobj =  (JSONObject) array.get(0);

        String result = jobj.get("result").toString();

        JSONObject resultObject = JSONObject.fromObject(result);

        String districtText = resultObject.get("district_text").toString();

        String district = resultObject.get("district").toString();

        PointRgc pointRgc = new PointRgc();

        pointRgc.setDistrictText(districtText);

        pointRgc.setDistrict(district);

        return pointRgc;

    }

    /**
     * 周边poi搜索
     * @param lng 经度
     * @param lat 纬度
     * @param types poi类型
     * @param pager 分页
     * @return
     */
    public static List<Point> requestHttpAround(String lng,String lat,String types,Pager pager,String keywords){
        String point = lng + "," + lat;

        String urls = urlAround +"keywords="+keywords+"&key=" + key + "&location=" + point +"&radius=1500" + "&types=" + types + "&output=json" +"&offset="+ pager.getPageSize() +"&page=" + pager.getPageNo();

        Map<String,String> param = new HashMap<String,String>();

        String request =  HttpClientUtils.sendGetRequest(urls, param,"UTF-8");

        JSONObject object = JSONObject.fromObject(request);

        JSONArray array = JSONArray.fromObject(object);

        JSONObject jobj =  (JSONObject) array.get(0);

        String result = jobj.get("pois").toString();

        List<Point> po =  JsonUtility.toJavaObjectArray(result, Point.class);

        return po;
    }


}
