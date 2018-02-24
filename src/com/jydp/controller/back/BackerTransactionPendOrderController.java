package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionPendOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 后台挂单记录
 * @author fk
 *
 */
@Controller
@RequestMapping("/backerWeb/transactionPendOrder")
@Scope(value="prototype")
public class BackerTransactionPendOrderController {

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    /** 币种信息 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 展示页面 */
    @RequestMapping("/show.htm")
    public String show(HttpServletRequest request){
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 101001);
        if (!havePower) {
            request.setAttribute("code", 5);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        List(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/transactionPendOrder";
    }

    /** 查询数据 */
    public void List(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String pendingStatusStr = StringUtil.stringNullHandle(request.getParameter("pendingStatus"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String startEndTimeStr = StringUtil.stringNullHandle(request.getParameter("startEndTime"));
        String endEndTimeStr = StringUtil.stringNullHandle(request.getParameter("endEndTime"));

        Timestamp startAddTime = null;
        Timestamp endAddTime = null;
        Timestamp startEndTime = null;
        Timestamp endEndTime = null;
        int paymentType = 0;
        int currencyId = 0;
        int pendingStatus = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(startAddTimeStr)) {
            startAddTime = DateUtil.stringToTimestamp(startAddTimeStr);
        }
        if (StringUtil.isNotNull(endAddTimeStr)) {
            endAddTime = DateUtil.stringToTimestamp(endAddTimeStr);
        }
        if (StringUtil.isNotNull(startEndTimeStr)) {
            startEndTime = DateUtil.stringToTimestamp(startEndTimeStr);
        }
        if (StringUtil.isNotNull(endEndTimeStr)) {
            endEndTime = DateUtil.stringToTimestamp(endEndTimeStr);
        }
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }
        if (StringUtil.isNotNull(pendingStatusStr)) {
            pendingStatus = Integer.parseInt(pendingStatusStr);
        }
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = transactionPendOrderService.countPendOrderForBack(userAccount, currencyId, paymentType, pendingStatus, startAddTime, endAddTime, startEndTime, endEndTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        List<TransactionPendOrderDO> transactionPendOrderList = null;
        if (totalNumber > 0) {
            transactionPendOrderList = transactionPendOrderService.listPendOrderForBack(userAccount, currencyId, paymentType, pendingStatus, startAddTime, endAddTime, startEndTime, endEndTime, pageNumber, pageSize);
        }

        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startAddTime", startAddTime);
        request.setAttribute("endAddTime", endAddTime);
        request.setAttribute("startEndTime", startEndTime);
        request.setAttribute("endEndTime", endEndTime);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("paymentType", paymentType);
        request.setAttribute("pendingStatus", pendingStatus);
        request.setAttribute("currencyId", currencyId);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("transactionPendOrderList", transactionPendOrderList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 101000);
    }

    /** 撤销挂单 */
    @RequestMapping("/cancle.htm")
    public String cancle(HttpServletRequest request){
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录超时");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 101002);
        if (!havePower) {
            request.setAttribute("code", 5);
            request.setAttribute("message", "您没有权限进行此操作");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        String pendingOrderNo = StringUtil.stringNullHandle(request.getParameter("pendingOrderNo"));
        if (StringUtil.isNotNull(pendingOrderNo)) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误");
            request.getSession().setAttribute("backer_rolePowerId", 101000);
            return "page/back/transactionPendOrder";
        }
        //获取订单并判断状态是否可以撤单
        TransactionPendOrderDO pendOrder = transactionPendOrderService.getPendOrderByPendingOrderNo(pendingOrderNo);
        if (pendOrder == null || pendOrder.getPendingStatus() >= 3) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "该订单不存在或已完成");
            request.getSession().setAttribute("backer_rolePowerId", 101000);
            return "page/back/transactionPendOrder";
        }
        //撤单
        boolean result = transactionPendOrderService.revokePendOrder(pendingOrderNo);
        if (result) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "撤单成功");
        } else {
            request.setAttribute("code", 2);
            request.setAttribute("message", "撤单失败");
        }

        List(request);

        return "page/back/transactionPendOrder";
    }

}
