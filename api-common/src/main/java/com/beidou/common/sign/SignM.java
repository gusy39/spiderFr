package com.beidou.common.sign;

import com.beidou.common.util.MD5Util;

import java.util.*;

/**
 * Created by chenshenghong on 2016/9/7.
 */
public class SignM {

    public  static String MakeSign(Map<String,Object> params,String appsecret)
    {
        String sign="";
        Map<String,Object> param =new HashMap<>();
        param=params;
        if(param.get("sign")!=null)
        {
            param.remove("sign");
        }else{
            return null;
        }
        sign=SortAndSignMap(param);
        System.out.println(sign+appsecret);
        sign= MD5Util.MD5(sign+appsecret).toLowerCase();
        return sign;
    }


    /**
     *排序与加密参数（升序）
     * @param params 加密参数
     * @return
     */
    public static String SortAndSignMap(Map<String,Object> params)
    {
        List<Map.Entry<String, Object>> infoIds =
                new ArrayList<Map.Entry<String, Object>>(params.entrySet());

         Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                //return (o2.getValue() - o1.getValue());
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        String sign="";
        Map<String,Object> paramsRet =new HashMap<String,Object>();
        for(Map.Entry<String,Object> entry:infoIds){
            sign +=String.format("%02d",entry.getKey().length())+"-"+entry.getKey()+":"+String.format("%04d",entry.getValue().toString().length())+"-"+entry.getValue()+";";
        }
        sign=sign.substring(0,sign.length()-1);
        return sign;
    }


    public static void main(String[] args) {

        Map<String,Object> params =new HashMap<String,Object>();

        params.put("key1","value1");
        params.put("key2","value2");
        System.out.println(MakeSign(params,"1234"));

    }
}
