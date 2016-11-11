package com.beidou.spring.interceptor;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2015/7/15.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthPassport {
    /** 是否需要校验 */
    boolean isValidate() default true;

    /** htm,json,xml */
    ResTypeEnum resType() default ResTypeEnum.JSON;


}

