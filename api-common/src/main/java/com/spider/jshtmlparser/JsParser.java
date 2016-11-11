package com.spider.jshtmlparser;

/**
 * js 解析html
 *
 * Created by 许巧生 on 2016/5/25.
 */
public class JsParser {

    public static Object parse(String jsName, Object[] args){
        return JsParser.parse("parse",jsName, args);
    }

    public static Object parse(String methodName, String jsName, Object[] args){
        RhinoScaper rs = new RhinoScaper();
        try {
            rs.init();
            rs.setJsFile("/js/" + jsName);
            rs.run();
            return rs.callMethod(methodName, args);
        }finally {
            rs.exit();
        }
    }
}
