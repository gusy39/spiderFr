package com.beidou.common.form;

import com.beidou.common.dto.BaseDTO;
import com.beidou.common.exception.BeanNullException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2015/6/26.
 */
public class BaseForm {
    private final Logger logger = LoggerFactory.getLogger(BaseForm.class);

    public <T extends BaseDTO> T convertModelBean(BaseDTO baseDTO) {
        if (baseDTO == null) {
            throw new BeanNullException("model bean 不能为空!");
        }
        try {
            BeanUtils.copyProperties(baseDTO, this);
            return (T)baseDTO;
        } catch (IllegalAccessException e) {
            logger.info(ExceptionUtils.getStackTrace(e));
            throw new BeanNullException(e.getMessage());
        } catch (InvocationTargetException e) {
            logger.info(ExceptionUtils.getStackTrace(e));
            throw new BeanNullException(e.getMessage());
        }
    }
}
