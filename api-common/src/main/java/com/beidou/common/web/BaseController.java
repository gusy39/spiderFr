package com.beidou.common.web;

import com.beidou.common.dto.AjaxResult;
import com.beidou.common.dto.Pager;
import com.beidou.common.dto.ResultStatus;
import com.beidou.common.web.esayui.GridList;
import com.beidou.common.web.esayui.GridPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by: chenshenghong
 * Modify by: user
 * Date: 2016/1/18.
 * email:803830385@qq.com
 * mobile:15800692393
 */
public abstract class BaseController {
	
    public final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    protected HttpServletRequest request;
    
    protected void addBaseURL(Map<String, String> configMap) {
    	String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    	
    	for (Map.Entry<String, String> map : configMap.entrySet()) {
    		String key = map.getKey();
    		if (!"beidouTel".equals(key)) {
	    		String value = map.getValue();
	    		if (!value.startsWith("http")) {
	    			map.setValue(serverUrl + value);
	    		}
    		}
        }
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        datetimeFormat.setLenient(false);

        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(java.sql.Timestamp.class, new CustomTimestampEditor(datetimeFormat, true));
    }

    public static AjaxResult getAjaxSuccess(Object data) {
        return new AjaxResult(ResultStatus.SUCCESS.getCode(), "成功", data);
    }

    public static AjaxResult getAjaxSuccess() {
        return new AjaxResult(ResultStatus.SUCCESS.getCode(), "成功", "");
    }

    public static AjaxResult getAjaxGridPage(Pager<?> pages) {
        GridPage gridPage = new GridPage(pages.getTotalCount(), pages.getResult());
        return new AjaxResult(ResultStatus.SUCCESS.getCode(), "success", gridPage);
    }

    public static AjaxResult getAjaxGridList(List<?> list) {
        GridList gridList = new GridList();
        gridList.setMsg("success");
        gridList.setObj(list);
        gridList.setSuccess(true);
        return new AjaxResult(ResultStatus.SUCCESS.getCode(), "success", gridList);
    }

    public static AjaxResult getAjaxNotLogin(Object data) {
        return new AjaxResult(ResultStatus.LOGIN.getCode(), "您还未登录", data);
    }

    public static AjaxResult getAjaxNotLocation(){
        return new AjaxResult(ResultStatus.LOCATION.getCode(),"未定位","");
    }
    public static AjaxResult getAjaxInvalid(BindingResult result, Object data) {
        return new AjaxResult(ResultStatus.INVALID.getCode(), result, data);
    }

    public static AjaxResult getAjaxInvalid(BindingResult[] result, Object data) {
        return new AjaxResult(ResultStatus.INVALID.getCode(), result, data);
    }

    public static AjaxResult getAjaxInvalid(Object data, List<ObjectError> result) {
        return new AjaxResult(ResultStatus.INVALID.getCode(), result, data);
    }

    public static AjaxResult getAjaxInvalid(Object data, List<ObjectError>... result) {
        List<ObjectError> newRet = new ArrayList<>();
        if (result != null) {
            for (List<ObjectError> item : result) {
                newRet.addAll(item);
            }
        }
        return new AjaxResult(ResultStatus.INVALID.getCode(), newRet, data);
    }

    /**
     * 单项验证
     * @param message
     * @return
     */
    public static AjaxResult getAjaxInvalid(String message) {
        return new AjaxResult(ResultStatus.INVALID.getCode(), message);
    }
    public static AjaxResult getAjaxException(String message) {
        return new AjaxResult(ResultStatus.EXCEPTION.getCode(), message);
    }

    public static AjaxResult getAjaxError(String message) {
        return new AjaxResult(ResultStatus.ERROR.getCode(), message);
    }

    public static AjaxResult getAjaxInfo(String message) {
        return new AjaxResult(ResultStatus.ERROR.getCode(), message);
    }

    public static AjaxResult getAjaxRedirect(String redirectURL) {
        return new AjaxResult(ResultStatus.REDIRECT.getCode(), "redirectURL", redirectURL);
    }

}
