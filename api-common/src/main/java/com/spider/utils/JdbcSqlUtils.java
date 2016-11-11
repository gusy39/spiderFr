package com.spider.utils;

/**
 * Created by 许巧生 on 2016/8/11.
 */
public class JdbcSqlUtils {

    public static String formartField(String field){
        return field.replace("\\","\\\\").replace("'", "\\'");
    }
}
