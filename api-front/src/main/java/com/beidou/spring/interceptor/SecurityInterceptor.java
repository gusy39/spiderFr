package com.beidou.spring.interceptor;

import com.beidou.common.constant.Constants;
import com.beidou.common.dto.AjaxResult;
import com.beidou.common.dto.ResultStatus;
import com.beidou.common.exception.ServiceException;
import com.beidou.common.sign.SignM;
import com.beidou.common.util.RequestUtils;
import com.beidou.token.RedisSessionCache;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenshenghong
 * tel 15800692393
 */
public class SecurityInterceptor implements HandlerInterceptor {
    private final Logger logger = Logger.getLogger(getClass());
    public static final String CURRENT_SESSION = "_current_session";
    public static final String CURRENT_SESSION_ID = "_current_session_id";

    public static  final String appsecret="tongyi";
    @Autowired
    private RedisSessionCache sessionCache;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        req.setAttribute("reqBegin", System.currentTimeMillis());
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);

            if(authPassport.isValidate() == true)
            {
                Map<String,Object> params = new HashMap<>();
                Enumeration paramNames = req.getParameterNames();
                while (paramNames.hasMoreElements()) {
                    String paramName = (String) paramNames.nextElement();

                    String[] paramValues = req.getParameterValues(paramName);
                    if (paramValues.length == 1) {
                        String paramValue = paramValues[0];
                        if (paramValue.length() != 0) {
                            params.put(paramName, paramValue);
                        }
                    }
                }
                String sign =SignM.MakeSign(params,appsecret);
                if(sign!=null && params.get("sign")!=null)
                {
                    if(!sign.equals(params.get("sign").toString()))
                    {
                        throw new ServiceException("签名验证失败");
                    }
                }else{
                    throw new ServiceException("签名验证失败");
                }
                //接口有效性验证
                if(params.get("timestamp")!=null)
                {
                    Long timestamp=Long.valueOf(params.get("timestamp").toString());
                    Long nowTime=new Date().getTime();
                    //误差超过10分钟失效
                    if(nowTime-timestamp>1000*5*60)
                    {
                        throw new ServiceException("访问已过期");
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView arg3) throws Exception {
        String token = (String) req.getAttribute(CURRENT_SESSION_ID);
        if (token != null) {
            if (arg3 == null) {
                arg3 = new ModelAndView();
            }
            arg3.addObject(Constants.JSESSION_URL, token);
            res.addCookie(createCookie(req, token));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3) throws Exception {
        Long reqBeginTime = (Long) req.getAttribute("reqBegin");
        logger.info((System.currentTimeMillis() - reqBeginTime) + "-" + req.getRequestURI());
    }

    private static int cookieMaxAge = 3600 * 24 * 7;

    private Cookie createCookie(HttpServletRequest request, String value) {
        Cookie cookie = new Cookie(Constants.JSESSION_COOKIE, value);
        cookie.setMaxAge(cookieMaxAge);
        String ctx = request.getContextPath();
        cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
        return cookie;
    }


}
