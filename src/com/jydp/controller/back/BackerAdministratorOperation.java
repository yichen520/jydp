package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.back.BackerDO;
import com.jydp.entity.DO.back.BackerHandleUserRecordBalanceDO;
import com.jydp.entity.DO.back.BackerRoleDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IBackerHandleUserRecordBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 后台管理员操作管理
 * @author sy
 *
 */
@Controller
@RequestMapping("/backerWeb/backerAdministratorOperation")
@Scope(value="prototype")
public class BackerAdministratorOperation {

    /** 后台管理员增减用户余额记录 */
    @Autowired
    private IBackerHandleUserRecordBalanceService backerHandleUserRecordBalanceService;

    /** 展示 后台管理员增减用户余额记录页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 121001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }

        showList(request);
        return "page/back/userBalanceRecord";
    }

    /** 分页查询 后台管理员增减用户余额记录数据  */
    private void showList(HttpServletRequest request) {
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String typeHandleStr = StringUtil.stringNullHandle(request.getParameter("typeHandle"));
        String backerAccount = StringUtil.stringNullHandle(request.getParameter("backerAccount"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int typeHandle = 0;
        if (StringUtil.isNotNull(typeHandleStr)) {
            typeHandle = Integer.parseInt(typeHandleStr);
        }

        Timestamp startAddTime = null;
        if (StringUtil.isNotNull(startAddTimeStr)) {
            startAddTime = DateUtil.stringToTimestamp(startAddTimeStr);
        }

        Timestamp endAddTime = null;
        if (StringUtil.isNotNull(endAddTimeStr)) {
            endAddTime = DateUtil.stringToTimestamp(endAddTimeStr);
        }

        int pageSize = 20;
        int totalNumber = backerHandleUserRecordBalanceService.getUserRecordBalanceNumber(userAccount, typeHandle, backerAccount, startAddTime,endAddTime);
        int totalPageNumber = (int) Math.ceil(totalNumber/1.0/pageSize);   //总页码数
        if(totalPageNumber <= 0){
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }

        List<BackerHandleUserRecordBalanceDO> backerHandleUserRecordBalanceList = null;
        if (totalNumber > 0) {
            backerHandleUserRecordBalanceList = backerHandleUserRecordBalanceService.getUserRecordBalanceList(userAccount, typeHandle, backerAccount,
                    startAddTime,endAddTime, pageNumber, pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("backerHandleUserRecordBalanceList", backerHandleUserRecordBalanceList);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("typeHandle", typeHandle);
        request.setAttribute("backerAccount", backerAccount);
        request.setAttribute("startAddTime", startAddTimeStr);
        request.setAttribute("endAddTime", endAddTimeStr);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 121000);
    }
}
