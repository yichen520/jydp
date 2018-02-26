package com.jydp.controller.back;

import com.iqmkj.utils.*;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionMakeOrderService;
import config.SystemCommonConfig;
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
        if (totalPageNumber < pageNumber) {
            pageNumber = totalPageNumber;
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

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 103002);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String currencyNameStr = StringUtil.stringNullHandle(request.getParameter("currencyName"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String currencyNumberStr = StringUtil.stringNullHandle(request.getParameter("currencyNumber"));
        String currencyPriceStr = StringUtil.stringNullHandle(request.getParameter("currencyPrice"));
        String executeTimeStr = StringUtil.stringNullHandle(request.getParameter("executeTime"));

        if (!StringUtil.isNotNull(currencyNameStr)) {
            response.setCode(3);
            response.setMessage("参数不能为空");
            return response;
        }

        Timestamp executeTime = null;
        int paymentType = 0;
        double currencyNumber = 0;
        double currencyPrice = 0;

        if (StringUtil.isNotNull(currencyNumberStr)) {
            currencyNumber = Double.parseDouble(currencyNumberStr);
        }
        if (StringUtil.isNotNull(currencyPriceStr)) {
            currencyPrice = Double.parseDouble(currencyPriceStr);
        }
        if (StringUtil.isNotNull(executeTimeStr)) {
            executeTime = DateUtil.stringToTimestamp(executeTimeStr);
        }
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }

        Timestamp curTime = DateUtil.getCurrentTime();
        if (executeTime.getTime() <= curTime.getTime()) {
            response.setCode(3);
            response.setMessage("执行时间必须大于当前时间");
            return response;
        }

        TransactionCurrencyDO currency = transactionCurrencyService.getTransactionCurrencyByCurrencyName(currencyNameStr);
        if (currency == null) {
            response.setCode(3);
            response.setMessage("币种信息不存在");
            return response;
        }

        String orderNo = SystemCommonConfig.TRANSACTION_MAKE_ORDER
                + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10)
                + NumberUtil.createNumberStr(10);

        double currencyTotalPrice = BigDecimalUtil.mul(currencyNumber, currencyPrice);
        if (currencyTotalPrice <= 0) {
            response.setCode(3);
            response.setMessage("总价格不能为空");
            return response;
        }

        String ipAddress = IpAddressUtil.getIpAddress(request);
        if (!StringUtil.isNotNull(ipAddress)) {
            response.setCode(3);
            response.setMessage("ip地址不能为空");
            return response;
        }

        boolean result = transactionMakeOrderService.insertMakeOrder(orderNo, paymentType, currency.getCurrencyId(),
                currencyNameStr, currencyNumber, currencyTotalPrice, backerSession.getBackerAccount(), ipAddress, 1, null, executeTime, curTime);

        if (result) {
            response.setCode(1);
            response.setMessage("交易记录添加成功");
        } else {
            response.setCode(2);
            response.setMessage("交易记录添加失败");
        }
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
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 103005);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String orderNo = StringUtil.stringNullHandle(request.getParameter("orderNo"));

        if (!StringUtil.isNotNull(orderNo)) {
            response.setCode(3);
            response.setMessage("参数不能为空");
            return response;
        }

        //执行添加记录



        return response;
    }

    /** 撤回 */
    @RequestMapping(value = "/cancle.htm", method = RequestMethod.POST)
    public JsonObjectBO cancle(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 103006);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String orderNo = StringUtil.stringNullHandle(request.getParameter("orderNo"));

        if (!StringUtil.isNotNull(orderNo)) {
            response.setCode(3);
            response.setMessage("参数不能为空");
            return response;
        }

        TransactionMakeOrderDO order = transactionMakeOrderService.getTransactionMakeOrderByOrderNo(orderNo);
        if (order == null) {
            response.setCode(5);
            response.setMessage("该记录不存在");
            return response;
        }

        if (order.getExecuteStatus() != 1) {
            response.setCode(5);
            response.setMessage("该记录已被操作");
            return response;
        }

        //修改状态, 原状态必须为1


        return response;
    }

    /** 下载交易模板 */
    @RequestMapping(value = "/dowland.htm", method = RequestMethod.POST)
    public JsonObjectBO dowland(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();



        return response;
    }
}
