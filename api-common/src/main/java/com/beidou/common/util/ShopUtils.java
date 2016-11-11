package com.beidou.common.util;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.exception.NoneLoginException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class ShopUtils {
    public static Integer getShopId(){
        return Integer.valueOf(ContextHolder.getLoginUser().getCompanyId());
    }

    private static final ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();


    public static HttpServletRequest getRequest() {
        return requestThreadLocal.get();
    }


    public static HttpSession getHttpSession(){
        HttpServletRequest req=getRequest();
        if(req!=null){
            return req.getSession();
        }else{
            return null;
        }
    }

    public static String checkSuperAdmin(){
        IUser loginUser= ContextHolder.getLoginUser();
        if(loginUser==null){
            throw new NoneLoginException("Please login first");
        }
        String companyId=loginUser.getCompanyId();
        if (!"-1".equals(companyId)) {
            throw new RuntimeException("您的权限不足");
        }
        return companyId;
    }


    //根据权限设置 转化成 shopCode 的IN 查询
    public static IUser getLoginUser(){
       // HttpSession session=getHttpSession();
        // 超级管理员权限
//        if(session==null){
//            return null;
//        }

        IUser loginUser= ContextHolder.getLoginUser();
      return  loginUser;
//        return (IUser)session.getAttribute(ContextHolder.LOGIN_USER_SESSION_KEY);
    }

}
