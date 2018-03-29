package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.back.BackerDO;
import com.jydp.entity.DO.back.BackerSessionDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IBackerService;
import com.jydp.service.IBackerSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 后台管理员操作记录
 * @author zym
 *
 */
@Controller
@RequestMapping("/backerWeb/backerOperationalRecord")
@Scope(value = "prototype")
public class BackerOperationalRecordController {

    /** 系统管理员登录记录 */
    @Autowired
    private IBackerSessionService backerSessionService;


    /** 分页查询用户公告管理 */
    private void list(HttpServletRequest request) {
        // 查询参数
        String startLoginTimeStr = StringUtil.stringNullHandle(request.getParameter("startLoginTime"));
        String endLoginTimeStr = StringUtil.stringNullHandle(request.getParameter("endLoginTime"));
        String backerAccount = StringUtil.stringNullHandle(request.getParameter("backerAccount"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        Timestamp startLoginTime = null;
        if (StringUtil.isNotNull(startLoginTimeStr)) {
            startLoginTime = DateUtil.stringToTimestamp(startLoginTimeStr);
        }

        Timestamp endLoginTime = null;
        if (StringUtil.isNotNull(endLoginTimeStr)) {
            endLoginTime = DateUtil.stringToTimestamp(endLoginTimeStr);
        }

        // 查询数据
        int totalNumber = backerSessionService.countBackerSession(backerAccount, startLoginTime, endLoginTime);

        List<BackerSessionDO> backerSessionList = null;
        int pageSize = 20;

        int totalPageNumber = (int) Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }

        if (totalNumber > 0) {
            backerSessionList = backerSessionService.listBackerSessionByPage( pageNumber, pageSize, backerAccount, startLoginTime, endLoginTime);
        }

        // 返回数据
        request.setAttribute("startLoginTime", startLoginTimeStr);
        request.setAttribute("endLoginTime", endLoginTimeStr);
        request.setAttribute("backerAccount", backerAccount);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("backerSessionList", backerSessionList);

        request.getSession().setAttribute("backer_pagePowerId", 131200);
    }

    /** 展示列表页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 131201);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        list(request);
        return "page/back/backerLoginRecord";
    }
}
