package com.beidou.controller.api;

import com.beidou.common.dto.Pager;
import com.beidou.common.dto.PagerHelper;
import com.beidou.common.util.CalendarUtils;
import com.beidou.common.util.StringUtil;
import com.beidou.controller.AppController;
import com.beidou.lbs.PointRgc;
import com.beidou.models.ShopCount;
import com.beidou.models.UserComment;
import com.beidou.models.Users;
import com.beidou.spring.interceptor.AuthPassport;
import com.beidou.spring.interceptor.ResTypeEnum;
import com.ecmoho.api.ErpService;
import com.ecmoho.api.UserCommentService;
import com.ecmoho.api.UsersService;
import com.ecmoho.lbs.LbsSearchService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 16-1-19.
 */
@Controller
public class apiController extends AppController {

    private static final Logger logger=Logger.getLogger(apiController.class);

    @Autowired
    private UserCommentService userCommentService;

    @Autowired
    private ErpService erpService;

    @Autowired
    private UsersService usersService;
    /**
     * 输出接口
     * @param content
     * @param startData
     * @param endData
     * @param pageSize
     * @return
     */
    @RequestMapping("/user/getComments")
    @AuthPassport(isValidate = false,resType = ResTypeEnum.JSON)
    public Object getComments(String content,String startData,String endData,Integer pageSize){

        Pager<UserComment> pager = PagerHelper.createPage(request);
        if(pageSize!=null && pageSize>0)
        {
            pager.setPageSize(pageSize);
        }else{
            pager.setPageSize(5);
        }
        //时间处理
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UserComment condition =new UserComment();
        if(startData!=null && endData!=null )
        {
            try {
                format.setLenient(false);
                format.parse(startData);
                format.parse(endData);
            } catch (ParseException e) {
                return getAjaxError("时间格式有误");
            }
        }else{
            //默认当天全天
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Date start =calendar.getTime();
            startData = format.format(start);

            condition.setFieldBeginDate(startData);
            calendar.set(Calendar.HOUR_OF_DAY, 24);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Date end =calendar.getTime();
            endData = format.format(end);
            condition.setFieldEndDate(endData);
        }
        if(content!=null)
        {
            condition.setContent(content);
        }

        Pager<UserComment> userComments = userCommentService.getList(condition,pager);

        //日志执行
        logger.log(Level.INFO,"执行查询"+condition);

        Map<String, Object> pagerMap = new HashMap<>();
        pagerMap.put("pageNo", pager.getPageNo());//当前页
        pagerMap.put("count", pager.getTotalCount());//总记录
        pagerMap.put("pageSize", pager.getPageSize());//总页数
       // pagerMap.put("isPre", pager.isHasPre());//是否有上一页
       // pagerMap.put("isNext", pager.isHasNext());//是否有下一页
        if (pager.getResult() != null) {
            pagerMap.put("result", userComments);
        }else {
            pagerMap.put("result", new ArrayList<>());
        }
        return getAjaxSuccess(pagerMap);
    }

    /**
     * 调取第三方接口并输出
     * @param start 开始日期
     * @param end 结束日期
     * @return
     */
    @RequestMapping("/user/getShopCounts")
    @AuthPassport(isValidate = false,resType = ResTypeEnum.JSON)
      public Object getShopCounts(String start, String end, HttpServletRequest request)
      {

          HttpSession session = request.getSession();
          if(session.getAttribute("isOperation")!=null)
          {
              return getAjaxError("正在运行");
          }else{
              //开启独占
              session.setAttribute("isOperation",true);
          }

          try
          {
              Thread.sleep(10000);
          }
          catch (InterruptedException e)
          {
          }

          Map<String, Object> pagerMap = new HashMap<>();

          Map<String, Object> params = new HashMap<>();
          params.put("start",start);
          params.put("end",end);
          List<ShopCount> shopCounts=erpService.getShopCount(params);

          pagerMap.put("result", shopCounts);
          //释放资源
          session.removeAttribute("isOperation");
          return getAjaxSuccess(pagerMap);
      }

    /**
     * 调取第三方接口并存储
     * @param start 开始日期
     * @param end 结束日期
     * @return
     */
    @RequestMapping("/user/saveShopCounts")
    @AuthPassport(isValidate = false,resType = ResTypeEnum.JSON)
    public Object saveShopCounts(String start,String end)
    {
        Map<String, Object> pagerMap = new HashMap<>();

        Map<String, Object> params = new HashMap<>();
        params.put("start",start);
        params.put("end",end);
        List<ShopCount> shopCounts=erpService.getShopCount(params);
        boolean ret =erpService.saveShopCount(shopCounts);
        pagerMap.put("result", ret);

        return getAjaxSuccess();
    }

    /**
     *用户信息接口
     * @param userName
     * @param startTime
     * @param endTime
     * @param pageSize
     * @return
     */
    @RequestMapping("/user/pushUsers")
    @AuthPassport(isValidate = false)
    public Object getUsers(String userName,String startTime,String endTime,String pageSize)
    {
        Users condition=new Users();

        //分页
        Pager<Users> pager = PagerHelper.createPage(request);
        if(pageSize!=null && Integer.valueOf(pageSize)>0)
        {
            pager.setPageSize( Integer.valueOf(pageSize));
        }
        //查询条件
        if(!StringUtil.isEmpty(userName))
        {
            condition.setUserName(userName);
        }

        //时间处理
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime))
        {
            try {
                format.setLenient(false);
                format.parse(startTime);
                format.parse(endTime);
            } catch (ParseException e) {
                return getAjaxError("时间格式有误");
            }
            condition.setFieldBeginDate(startTime);
            condition.setFieldEndDate(endTime);
        }
      //调用service
        Pager<Users> users=usersService.getList(condition,pager);

        Map<String, Object> pagerMap = new HashMap<>();
        pagerMap.put("pageNo", pager.getPageNo());//当前页
        pagerMap.put("count", pager.getTotalCount());//总记录
        pagerMap.put("pageSize", pager.getPageSize());//总页数
        if (pager.getResult() != null) {
            pagerMap.put("result", users);
        }else {
            pagerMap.put("result", new ArrayList<>());
        }

        //对接第三方数据接口

        return getAjaxSuccess(pagerMap);
    }

}
