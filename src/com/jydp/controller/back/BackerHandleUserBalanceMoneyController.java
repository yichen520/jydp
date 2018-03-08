package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.VO.BackerHandleUserBalanceMoneyVO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IBackerHandleUserBalanceMoneyService;
import com.jydp.service.ITransactionCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 后台管理员增减用户可用币记录
 * @author sy
 *
 */
@Controller
@RequestMapping("/backerWeb/backerHandleUserBalanceMoney")
@Scope(value="prototype")
public class BackerHandleUserBalanceMoneyController {
    /** 后台管理员增减用户可用币记录 */
    @Autowired
    private IBackerHandleUserBalanceMoneyService backerHandleUserBalanceMoneyService;

    /** 币种管理 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;


    /** 展示 后台管理员增减用户可用币记录页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 121201);
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
        return "page/back/userBalanceCoinRecord";
    }

    /** 分页查询 后台管理员增减用户可用币记录数据  */
    private void showList(HttpServletRequest request) {
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String typeHandleStr = StringUtil.stringNullHandle(request.getParameter("typeHandle"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
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

        int currencyId = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
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
        int totalNumber = backerHandleUserBalanceMoneyService.getUserRecordBalanceNumber(userAccount, typeHandle, currencyId, backerAccount, startAddTime,endAddTime);
        int totalPageNumber = (int) Math.ceil(totalNumber/1.0/pageSize);   //总页码数
        if(totalPageNumber <= 0){
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }

        List<BackerHandleUserBalanceMoneyVO> backerHandleUserBalanceMoneyList = null;
        if (totalNumber > 0) {
            backerHandleUserBalanceMoneyList = backerHandleUserBalanceMoneyService.getUserRecordBalanceList(userAccount, typeHandle, currencyId, backerAccount,
                    startAddTime,endAddTime, pageNumber, pageSize);
        }

        //获取币种信息
        List<TransactionCurrencyDO> transactionCurrencyList= transactionCurrencyService.listTransactionCurrencyAll();

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("backerHandleUserBalanceMoneyList", backerHandleUserBalanceMoneyList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("typeHandle", typeHandle);
        request.setAttribute("currencyId", currencyId);
        request.setAttribute("backerAccount", backerAccount);
        request.setAttribute("startAddTime", startAddTimeStr);
        request.setAttribute("endAddTime", endAddTimeStr);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 121200);
    }
}
