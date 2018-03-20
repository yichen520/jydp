package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.transaction.TransactionStatisticsDO;
import com.jydp.entity.DTO.TransactionCurrencyBasicDTO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * Description: 交易统计记录表
 * Author: hht
 * Date: 2018-02-19 18:19
 */
@Controller
@RequestMapping("/backerWeb/transactionStatistics")
@Scope(value = "prototype")
public class BackerTransactionStatisticsController {

    /**
     * 交易统计记录表
     */
    @Autowired
    private ITransactionStatisticsService transactionStatisticsService;

    /**
     * 币种信息
     */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /**
     * 展示页面
     */
    @RequestMapping("/show.htm")
    public String show(HttpServletRequest request) {
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 153001);
        if (!havePower) {
            request.setAttribute("code", 5);
            request.setAttribute("message", "您没有此权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        list(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/transactionStatistics";
    }

    /**
     * 查询数据
     */
    public void list(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String startTimeStr = StringUtil.stringNullHandle(request.getParameter("startTime"));
        String endTimeStr = StringUtil.stringNullHandle(request.getParameter("endTime"));

        Timestamp startTime = null;
        Timestamp endTime = null;
        int pageNumber = 0;
        int currencyId = 0;

        if (StringUtil.isNotNull(startTimeStr)) {
            startTime = DateUtil.stringToTimestamp(startTimeStr);
        }
        if (StringUtil.isNotNull(endTimeStr)) {
            endTime = DateUtil.stringToTimestamp(endTimeStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        int pageSize = 20;

        int totalNumber = transactionStatisticsService.countTransactionStatisticsForBack(currencyId, startTime, endTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<TransactionStatisticsDO> statisticsList = null;
        if (totalNumber > 0) {
            statisticsList = transactionStatisticsService.listTransactionStatisticsForBack(currencyId,
                    startTime, endTime, pageNumber, pageSize);
        }

        List<TransactionCurrencyBasicDTO> currencyList = transactionCurrencyService.listAllTransactionCurrencyBasicInfor();

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startTime", startTimeStr);
        request.setAttribute("endTime", endTimeStr);
        request.setAttribute("currencyId", currencyIdStr);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("statisticsList", statisticsList);
        request.setAttribute("currencyList", currencyList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 153000);
    }
}
