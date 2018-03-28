package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.entity.VO.TransactionUserDealVO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionUserDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 后台交易完成记录
 * @author fk
 *
 */
@Controller
@RequestMapping("/backerWeb/transactionUserDeal")
@Scope(value="prototype")
public class BackerTransactionUserDealController {

    /** 交易完成记录 */
    @Autowired
    private ITransactionUserDealService transactionUserDealService;

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
        boolean havePower = BackerWebInterceptor.validatePower(request, 102001);
        if (!havePower) {
            request.setAttribute("code", 5);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        list(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/transactionUserDeal";
    }

    /** 查询数据 */
    public void list(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String startPendTimeStr = StringUtil.stringNullHandle(request.getParameter("startPendTime"));
        String endPendTimeStr = StringUtil.stringNullHandle(request.getParameter("endPendTime"));

        Timestamp startAddTime = null;
        Timestamp endAddTime = null;
        Timestamp startPendTime = null;
        Timestamp endPendTime = null;
        int paymentType = 0;
        int currencyId = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(startAddTimeStr)) {
            startAddTime = DateUtil.stringToTimestamp(startAddTimeStr);
        }
        if (StringUtil.isNotNull(endAddTimeStr)) {
            endAddTime = DateUtil.stringToTimestamp(endAddTimeStr);
        }
        if (StringUtil.isNotNull(startPendTimeStr)) {
            startPendTime = DateUtil.stringToTimestamp(startPendTimeStr);
        }
        if (StringUtil.isNotNull(endPendTimeStr)) {
            endPendTime = DateUtil.stringToTimestamp(endPendTimeStr);
        }
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;

        int totalNumber = transactionUserDealService.countTransactionUserDealForBack(userAccount, paymentType, currencyId, startAddTime, endAddTime, startPendTime, endPendTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<TransactionUserDealVO> transactionUserDealList = null;
        if (totalNumber > 0) {
            transactionUserDealList = transactionUserDealService.listTransactionUserDealForBack(userAccount, paymentType, currencyId, startAddTime, endAddTime, startPendTime, endPendTime, pageNumber, pageSize);
        }
        //全部币种
        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.listTransactionCurrencyAll();

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startAddTime", startAddTimeStr);
        request.setAttribute("endAddTime", endAddTimeStr);
        request.setAttribute("startPendTime", startPendTimeStr);
        request.setAttribute("endPendTime", endPendTimeStr);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("paymentType", paymentType);
        request.setAttribute("currencyId", currencyId);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("transactionUserDealList", transactionUserDealList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 102000);
    }

}
