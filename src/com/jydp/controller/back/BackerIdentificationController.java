package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IUserIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * Description: 用户实名认证
 * Author: hht
 * Date: 2018-02-08 9:50
 */
@Controller
@RequestMapping("/backerWeb/backerIndex")
@Scope(value = "prototype")
public class BackerIdentificationController {

    /** 用户认证 */
    @Autowired
    private IUserIdentificationService userIdentificationService;

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
        boolean havePower = BackerWebInterceptor.validatePower(request, 141001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        showList(request);
        return "page/back/userIdentification";
    }

    /** 获取列表数据 */
    private void showList(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String startTimeStr = StringUtil.stringNullHandle(request.getParameter("startTime"));
        String endTimeStr = StringUtil.stringNullHandle(request.getParameter("endTime"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String userPhone = StringUtil.stringNullHandle(request.getParameter("userPhone"));
        String identificationStatusStr = StringUtil.stringNullHandle(request.getParameter("identificationStatus"));

        Timestamp startTime = null;
        Timestamp endTime = null;
        int identificationStatus = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(startTimeStr)) {
            startTime = DateUtil.stringToTimestamp(startTimeStr);
        }
        if (StringUtil.isNotNull(endTimeStr)) {
            endTime = DateUtil.stringToTimestamp(endTimeStr);
        }
        if (StringUtil.isNotNull(identificationStatusStr)) {
            identificationStatus = Integer.parseInt(identificationStatusStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = userIdentificationService.countUserIdentificationForBacker(userAccount, userPhone, identificationStatus, startTime, endTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber -1;
        }

        List<UserIdentificationDO> userIdentificationList = null;
        if (totalNumber > 0) {
            userIdentificationList = userIdentificationService.listUserIdentificationForBacker(userAccount, userPhone, identificationStatus, startTime, endTime, pageNumber, pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startTime", startTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("userPhone", userPhone);
        request.setAttribute("identificationStatus", identificationStatusStr);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("userIdentificationList", userIdentificationList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 141000);
    }

    /** 实名认证详情页面 */
    @RequestMapping(value = "/detail.htm")
    public String detail(HttpServletRequest request) {
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141002);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        if (!StringUtil.isNotNull(userIdStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            return "page/back/index";
        }

        long userId = Long.parseLong(userIdStr);
        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationById(userId);
        if (userIdentification == null) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/index";
        }

        request.setAttribute("userIdentification", userIdentification);
        return "page/back/userIdentificationDetail";
    }

    /** 审核通过 */
    @RequestMapping(value = "/pass.htm")
    public String pass(HttpServletRequest request) {
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141003);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        if (!StringUtil.isNotNull(userIdStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            return "page/back/index";
        }

        boolean passResult = false;
        //TODO
        if (passResult) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "操作成功");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "操作失败");
        }
        showList(request);
        return "page/back/userIdentification";
    }

    /** 审核拒绝 */
    @RequestMapping(value = "/refuse.htm")
    public String refuse(HttpServletRequest request) {
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141004);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        if (!StringUtil.isNotNull(userIdStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            return "page/back/index";
        }

        boolean passResult = false;
        //TODO
        if (passResult) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "操作成功");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "操作失败");
        }
        showList(request);
        return "page/back/userIdentification";
    }
}
