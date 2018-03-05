package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 币种管理
 * @author fk
 *
 */
@Controller
@RequestMapping("/backerWeb/transactionCurrency")
@Scope(value="prototype")
public class BackerTransactionCurrencyController {

    /** 币种管理 */
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
        boolean havePower = BackerWebInterceptor.validatePower(request, 104001);
        if (!havePower) {
            request.setAttribute("code", 5);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        List(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/transactionCurrencyManage";
    }

    /** 查询数据 */
    public void List(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String currencyNameStr = StringUtil.stringNullHandle(request.getParameter("currencyName"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String backAccount = StringUtil.stringNullHandle(request.getParameter("backAccount"));
        String upStatusStr = StringUtil.stringNullHandle(request.getParameter("upStatus"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String startUpTimeStr = StringUtil.stringNullHandle(request.getParameter("startUpTime"));
        String endUpTimeStr = StringUtil.stringNullHandle(request.getParameter("endUpTime"));

        Timestamp startAddTime = null;
        Timestamp endAddTime = null;
        Timestamp startUpTime = null;
        Timestamp endUpTime = null;
        int paymentType = 0;
        int upStatus = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(startAddTimeStr)) {
            startAddTime = DateUtil.stringToTimestamp(startAddTimeStr);
        }
        if (StringUtil.isNotNull(endAddTimeStr)) {
            endAddTime = DateUtil.stringToTimestamp(endAddTimeStr);
        }
        if (StringUtil.isNotNull(startUpTimeStr)) {
            startUpTime = DateUtil.stringToTimestamp(startUpTimeStr);
        }
        if (StringUtil.isNotNull(endUpTimeStr)) {
            endUpTime = DateUtil.stringToTimestamp(endUpTimeStr);
        }
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }
        if (StringUtil.isNotNull(upStatusStr)) {
            upStatus = Integer.parseInt(upStatusStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;

        int totalNumber = transactionCurrencyService.countTransactionCurrencyForBack(currencyNameStr, paymentType, upStatus,
                backAccount, startAddTime, endAddTime, startUpTime, endUpTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<TransactionCurrencyVO> transactionCurrencyVOList = null;
        if (totalNumber > 0) {
            transactionCurrencyVOList = transactionCurrencyService.listTransactionCurrencyForBack(currencyNameStr, paymentType,
                    upStatus, backAccount, startAddTime, endAddTime,  startUpTime, endUpTime, pageNumber, pageSize);
        }

        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startAddTime", startAddTimeStr);
        request.setAttribute("endAddTime", endAddTimeStr);
        request.setAttribute("startUpTime", startUpTimeStr);
        request.setAttribute("endUpTime", endUpTimeStr);
        request.setAttribute("backAccount", backAccount);
        request.setAttribute("paymentType", paymentType);
        request.setAttribute("upStatus", upStatus);
        request.setAttribute("currencyName", currencyNameStr);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("transactionCurrencyVOList", transactionCurrencyVOList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 104000);
    }

    /** 新增币种 */
    @RequestMapping(value = "/add.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO add(HttpServletRequest request, MultipartFile myfile) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 104002);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        if (myfile == null) {
            response.setCode(3);
            response.setMessage("参数错误");
            return response;
        }








        return response;
    }
}
