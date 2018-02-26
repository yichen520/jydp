package com.jydp.controller.back;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionPendOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 后台挂单记录
 * @author hz
 *
 */
@Controller
@RequestMapping("/backerWeb/backerTransactionPendOrder")
@Scope(value="prototype")
public class BackerTransactionPendOrderController {

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 展示 后台挂单记录页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 101000);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }

        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();
        if(transactionCurrencyList != null){
            request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        }

        showList(request);
        return "page/back/transactionPendOrder";
    }

    /** 撤销挂单 */
    @RequestMapping(value = "/revoke.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO revoke(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 101002);
        if (!havePower) {
            resultJson.setCode(6);
            resultJson.setMessage("您没有该权限");
            return resultJson;
        }

        String pendingOrderNo = StringUtil.stringNullHandle(request.getParameter("pendingOrderNo"));
        if (!StringUtil.isNotNull(pendingOrderNo)) {
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        TransactionPendOrderDO transactionPendOrder = transactionPendOrderService.getPendOrderByPendingOrderNo(pendingOrderNo);
        if(transactionPendOrder == null){
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        boolean updateResult = transactionPendOrderService.revokePendOrder(pendingOrderNo);
        if (updateResult) {
            resultJson.setCode(1);
            resultJson.setMessage("撤单成功");
        } else {
            resultJson.setCode(5);
            resultJson.setMessage("撤单失败");
        }

        return resultJson;
    }

    /** 列表数据 */
    private void showList(HttpServletRequest request) {
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String pendingStatusStr = StringUtil.stringNullHandle(request.getParameter("pendingStatus"));
        String pageNumberStr  = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String startFinishTimeStr = StringUtil.stringNullHandle(request.getParameter("startFinishTime"));
        String endFinishTimeStr = StringUtil.stringNullHandle(request.getParameter("endFinishTime"));

        Timestamp startAddTime = null;
        if (StringUtil.isNotNull(startAddTimeStr)) {
            startAddTime = Timestamp.valueOf(startAddTimeStr);
        }
        Timestamp endAddTime = null;
        if (StringUtil.isNotNull(endAddTimeStr)) {
            endAddTime = Timestamp.valueOf(endAddTimeStr);
        }
        Timestamp startFinishTime = null;
        if (StringUtil.isNotNull(startFinishTimeStr)) {
            startFinishTime = Timestamp.valueOf(startFinishTimeStr);
        }
        Timestamp endFinishTime = null;
        if (StringUtil.isNotNull(endFinishTimeStr)) {
            endFinishTime = Timestamp.valueOf(endFinishTimeStr);
        }
        int currencyId = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }
        int paymentType = 0;
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }
        int pendingStatus = 0;
        if (StringUtil.isNotNull(pendingStatusStr)) {
            pendingStatus = Integer.parseInt(pendingStatusStr);
        }
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = transactionPendOrderService.countPendOrderForBack(userAccount, currencyId, paymentType,
                pendingStatus, startAddTime, endAddTime, startFinishTime, endFinishTime);

        List<TransactionPendOrderDO> transactionPendOrderRecord = null;
        if (totalNumber > 0) {
            transactionPendOrderRecord = transactionPendOrderService.listPendOrderForBack(userAccount, currencyId,
                    paymentType, pendingStatus, startAddTime, endAddTime, startFinishTime, endFinishTime, pageNumber,
                    pageSize);
        }

        int totalPageNumber = (int) Math.ceil(totalNumber/(pageSize*1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        //返回数据
        request.setAttribute("pageNumber",pageNumber);
        request.setAttribute("totalNumber",totalNumber);
        request.setAttribute("totalPageNumber",totalPageNumber);

        request.setAttribute("startAddTime", startAddTimeStr);
        request.setAttribute("endAddTime", endAddTimeStr);
        request.setAttribute("startFinishTime", startFinishTimeStr);
        request.setAttribute("endFinishTime", endFinishTimeStr);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("currencyId", currencyId);
        request.setAttribute("paymentType", paymentType);
        request.setAttribute("pendingStatus", pendingStatus);
        request.setAttribute("transactionPendOrderRecord", transactionPendOrderRecord);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 101000);
    }

}
