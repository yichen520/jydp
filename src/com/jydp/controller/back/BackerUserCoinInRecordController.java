package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.syl.SylToJydpChainDO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ISylToJydpChainService;
import com.jydp.service.ITransactionCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 用户充币成功记录
 * @Author: wqq
 */
@Controller
@RequestMapping("/backerWeb/backerUserCoinInRecord")
@Scope(value = "prototype")
public class BackerUserCoinInRecordController {

    /** SYL转账盛源链记录(SYL-->JYDP) */
    @Autowired
    private ISylToJydpChainService sylToJydpChainService;

    /** 交易币种 */
    @Autowired
    ITransactionCurrencyService transactionCurrencyService;

    /** 用户充币成功记录列表展示 */
    @RequestMapping("/show.htm")
    public String show(HttpServletRequest request){
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }

        // 业务功能权限
//        boolean havePower = BackerWebInterceptor.validatePower(request, 162001);
//        if (!havePower) {
//            request.setAttribute("code", 6);
//            request.setAttribute("message", "您没有该权限");
//            request.getSession().setAttribute("backer_pagePowerId", 0);
//            return "page/back/index";
//        }

        showList(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/userCoinInRecord";
    }

    /** 用户充币成功记录列表查询 */
    public void showList(HttpServletRequest request){
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String orderNo = StringUtil.stringNullHandle(request.getParameter("orderNo"));
        String walletOrderNo = StringUtil.stringNullHandle(request.getParameter("walletOrderNo"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String startTimeStr = StringUtil.stringNullHandle(request.getParameter("startTime"));
        String endTimeStr = StringUtil.stringNullHandle(request.getParameter("endTime"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int currencyId = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        Timestamp startTime = null;
        Timestamp endTime = null;
        if (StringUtil.isNotNull(startTimeStr)) {
            startTime = DateUtil.stringToTimestamp(startTimeStr);
        }
        if (StringUtil.isNotNull(endTimeStr)) {
            endTime = DateUtil.stringToTimestamp(endTimeStr);
        }

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = sylToJydpChainService.countSylToJydpChainForBack(userAccount, orderNo, walletOrderNo, currencyId, startTime, endTime);

        int pageSize = 20;
        int totalPageNumber = (int) Math.ceil(totalNumber/(pageSize*1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }

        List<SylToJydpChainDO> sylToJydpChainList = null;
        if (totalNumber > 0) {
            sylToJydpChainList = sylToJydpChainService.listSylToJydpChainForBack(userAccount, orderNo, walletOrderNo, currencyId, startTime, endTime, pageNumber, pageSize);
        }

        //获取币种信息
        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.listTransactionCurrencyAll();

        request.setAttribute("userAccount", userAccount);
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("walletOrderNo", walletOrderNo);
        request.setAttribute("currencyId", currencyId);
        request.setAttribute("startTime", startTimeStr);
        request.setAttribute("endTime", endTimeStr);

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);

        request.setAttribute("sylToJydpChainList", sylToJydpChainList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);

//        request.getSession().setAttribute("backer_pagePowerId", 162000);
    }
}
