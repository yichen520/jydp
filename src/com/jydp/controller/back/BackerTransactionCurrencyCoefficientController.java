package com.jydp.controller.back;

import com.iqmkj.utils.*;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyCoefficientDO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyCoefficientService;
import com.jydp.service.ITransactionCurrencyService;
import config.FileUrlConfig;
import config.SystemCommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 币种系数
 * @author fk
 *
 */
@Controller
@RequestMapping("/backerWeb/transactionCurrencyCoefficient")
@Scope(value="prototype")
public class BackerTransactionCurrencyCoefficientController {

    /** 币种系数 */
    @Autowired
    private ITransactionCurrencyCoefficientService transactionCurrencyCoefficientService;

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
        boolean havePower = BackerWebInterceptor.validatePower(request, 152001);
        if (!havePower) {
            request.setAttribute("code", 5);
            request.setAttribute("message", "您没有此权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        List(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/transactionCurrencyCoefficient";
    }

    /** 查询数据 */
    public void List(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));

        Timestamp startAddTime = null;
        Timestamp endAddTime = null;
        int currencyId = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(startAddTimeStr)) {
            startAddTime = DateUtil.stringToTimestamp(startAddTimeStr);
        }
        if (StringUtil.isNotNull(endAddTimeStr)) {
            endAddTime = DateUtil.stringToTimestamp(endAddTimeStr);
        }
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;

        int totalNumber = transactionCurrencyCoefficientService.countTransactionCurrencyCoeffieientForBack(currencyId, startAddTime, endAddTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<TransactionCurrencyCoefficientDO> currencyCoefficientList = null;
        if (totalNumber > 0) {
            currencyCoefficientList = transactionCurrencyCoefficientService.listTransactionCurrencyCoefficientForBack(currencyId,
                    startAddTime, endAddTime, pageNumber, pageSize);
        }

        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.listTransactionCurrencyAll();

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startAddTime", startAddTimeStr);
        request.setAttribute("endAddTime", endAddTimeStr);
        request.setAttribute("currencyId", currencyId);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("currencyCoefficientList", currencyCoefficientList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 152000);
    }

    /** 新增币种 */
    @RequestMapping(value = "/add.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO add(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 152002);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String currencyNameStr = StringUtil.stringNullHandle(request.getParameter("currencyNameAd"));
        String currencyCoefficientAdStr = StringUtil.stringNullHandle(request.getParameter("currencyCoefficientAd"));
        if (!StringUtil.isNotNull(currencyNameStr) || !StringUtil.isNotNull(currencyCoefficientAdStr) ){
            response.setCode(3);
            response.setMessage("参数错误");
            return response;
        }

        double currencyCoefficientAd = 0;

        currencyCoefficientAd = NumberUtil.doubleFormat(Double.parseDouble(currencyCoefficientAdStr), 8);

        TransactionCurrencyVO currurrencyName = transactionCurrencyService.getTransactionCurrencyByCurrencyName(currencyNameStr);
        if (currurrencyName == null) {
            response.setCode(3);
            response.setMessage("该币种不存在");
            return response;
        }

        //记录号
        String orderNo = SystemCommonConfig.TRANSACTION_CURRENCY_COEFFICIENT
                + DateUtil.longToTimeStr(DateUtil.getCurrentTime().getTime(), DateUtil.dateFormat10)
                + NumberUtil.createNumberStr(10);

        //新增币种系数
        boolean addBoo = transactionCurrencyCoefficientService.insertTransactionCurrencyCoefficient(orderNo, currurrencyName.getCurrencyId(),
                currencyNameStr, currencyCoefficientAd, backerSession.getBackerAccount(),
                IpAddressUtil.getIpAddress(request), DateUtil.getCurrentTime());
        if (addBoo) {
            response.setCode(1);
            response.setMessage("新增系数成功");
        } else {
            response.setCode(3);
            response.setMessage("新增系数失败");
        }

        return response;
    }

    /**
     * 删除记录
     */
    @RequestMapping(value = "/del.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO delOrder(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 152003);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String orderNoStr = StringUtil.stringNullHandle(request.getParameter("orderNo"));

        if (!StringUtil.isNotNull(orderNoStr)) {
            response.setCode(3);
            response.setMessage("参数不能为空");
            return response;
        }

        TransactionCurrencyCoefficientDO order = transactionCurrencyCoefficientService.getTransactionCurrencyCoefficientByOrderNo(orderNoStr);
        if (order == null) {
            response.setCode(3);
            response.setMessage("该记录不存在");
        }

        boolean result = transactionCurrencyCoefficientService.deleteTransactionCurrencyCoefficientByOrderNo(orderNoStr);
        if (result) {
            response.setCode(1);
            response.setMessage("删除成功");
        } else {
            response.setCode(2);
            response.setMessage("删除失败");
        }
        return response;
    }
}
