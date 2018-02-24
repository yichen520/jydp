package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionMakeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 后台做单
 * @author fk
 *
 */
@Controller
@RequestMapping("/backerWeb/transactionMakeOrder")
@Scope(value="prototype")
public class BackerTransactionMakeOrderController {

    /** 后台做单 */
    @Autowired
    private ITransactionMakeOrderService transactionMakeOrderService;

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
        boolean havePower = BackerWebInterceptor.validatePower(request, 103001);
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
        String backAccount = StringUtil.stringNullHandle(request.getParameter("backAccount"));
        String currencyNameStr = StringUtil.stringNullHandle(request.getParameter("currencyName"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String executeStatusStr = StringUtil.stringNullHandle(request.getParameter("executeStatus"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String startExecuteTimeStr = StringUtil.stringNullHandle(request.getParameter("startExecuteTime"));
        String endExecuteTimeStr = StringUtil.stringNullHandle(request.getParameter("endExecuteTime"));

        Timestamp startAddTime = null;
        Timestamp endAddTime = null;
        Timestamp startExecuteTime = null;
        Timestamp endExecuteTime = null;
        int paymentType = 0;
        int executeStatus = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(startAddTimeStr)) {
            startAddTime = DateUtil.stringToTimestamp(startAddTimeStr);
        }
        if (StringUtil.isNotNull(endAddTimeStr)) {
            endAddTime = DateUtil.stringToTimestamp(endAddTimeStr);
        }
        if (StringUtil.isNotNull(startExecuteTimeStr)) {
            startExecuteTime = DateUtil.stringToTimestamp(startExecuteTimeStr);
        }
        if (StringUtil.isNotNull(endExecuteTimeStr)) {
            endExecuteTime = DateUtil.stringToTimestamp(endExecuteTimeStr);
        }
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }
        if (StringUtil.isNotNull(executeStatusStr)) {
            executeStatus = Integer.parseInt(executeStatusStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;

        int totalNumber = transactionMakeOrderService.countTransactionMakeOrderForBack(currencyNameStr, executeStatus, paymentType, backAccount, startAddTime, endAddTime, startExecuteTime, endExecuteTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        List<TransactionMakeOrderDO> transactionMakeOrderList = null;
        if (totalNumber > 0) {
            transactionMakeOrderList = transactionMakeOrderService.listTransactionMakeOrderForBack(currencyNameStr, executeStatus, paymentType, backAccount, startAddTime, endAddTime, startExecuteTime, endExecuteTime, pageNumber, pageSize);
        }

        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startAddTime", startAddTime);
        request.setAttribute("endAddTime", endAddTime);
        request.setAttribute("startExecuteTime", startExecuteTime);
        request.setAttribute("endExecuteTime", endExecuteTime);
        request.setAttribute("backAccount", backAccount);
        request.setAttribute("paymentType", paymentType);
        request.setAttribute("executeStatus", executeStatus);
        request.setAttribute("currencyName", currencyNameStr);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("transactionMakeOrderList", transactionMakeOrderList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 103000);
    }

    /** 新增交易 */
    @RequestMapping(value = "/addOrder.htm", method = RequestMethod.POST)
    public JsonObjectBO addOrder(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();



        return response;
    }

    /** 导入交易数据 */
    @RequestMapping(value = "/importOrder.htm", method = RequestMethod.POST)
    public JsonObjectBO importOrder(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();



        return response;
    }

    /** 立即执行 */
    @RequestMapping(value = "/execute.htm", method = RequestMethod.POST)
    public JsonObjectBO execute(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();



        return response;
    }

    /** 撤回 */
    @RequestMapping(value = "/cancle.htm", method = RequestMethod.POST)
    public JsonObjectBO cancle(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();



        return response;
    }

    /** 下载交易模板 */
    @RequestMapping(value = "/dowland.htm", method = RequestMethod.POST)
    public JsonObjectBO dowland(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();



        return response;
    }
}
