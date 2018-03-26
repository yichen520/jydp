package com.jydp.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.IpAddressUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transfer.JydpCoinConfigDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IJydpCoinConfigService;
import com.jydp.service.ITransactionCurrencyService;
import config.SystemCommonConfig;
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
 * 后台币种转出管理
 * @author zym
 *
 */

@Controller
@RequestMapping("/backerWeb/backerCoinConfig")
@Scope(value="prototype")
public class BackerCoinConfigController {

    /** 交易币种 */
    @Autowired
    ITransactionCurrencyService transactionCurrencyService;

    /** JYDP币种转出管理	*/
    @Autowired
    IJydpCoinConfigService jydpCoinConfigService;

    /** 后台币种转出管理 */
    private void showList(HttpServletRequest request) {
        String addTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String backerAccount = StringUtil.stringNullHandle(request.getParameter("backerAccount"));
        String currencyName = StringUtil.stringNullHandle(request.getParameter("currencyName"));

        Timestamp startAddTime = null;
        if (StringUtil.isNotNull(addTimeStr)) {
            startAddTime = DateUtil.stringToTimestamp(addTimeStr);
        }
        Timestamp endAddTime = null;
        if (StringUtil.isNotNull(endTimeStr)) {
            endAddTime = DateUtil.stringToTimestamp(endTimeStr);
        }

        List<JydpCoinConfigDO> jydpCoinConfigList = null;

        jydpCoinConfigList = jydpCoinConfigService.getJydpCoinConfigServiceList(startAddTime, endAddTime, backerAccount, currencyName);
        //获取币种信息
        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.listTransactionCurrencyAll();

        request.setAttribute("jydpCoinConfigList", jydpCoinConfigList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        request.setAttribute("backerAccount", backerAccount);
        request.setAttribute("addTime", addTimeStr);
        request.setAttribute("endTime", endTimeStr);
        request.setAttribute("currencyName", currencyName);

        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 161000);
    }

    /** 后台币种转出管理页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 161001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        showList(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/coinConfig";
    }

    /** 新增后台币种管理 */
    @RequestMapping(value = "/addCoinConfig.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject addCoinConfig(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 161002);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            return response;

        }

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.put("code", 4);
            response.put("message", "登录过期");
            return response;
        }
        //获取参数
        String currencyName = StringUtil.stringNullHandle(request.getParameter("currencyName"));
        String freeCurrencyNumberStr = StringUtil.stringNullHandle(request.getParameter("freeCurrencyNumber"));
        String minCurrencyNumberStr = StringUtil.stringNullHandle(request.getParameter("minCurrencyNumber"));
        if (!StringUtil.isNotNull(currencyName) || !StringUtil.isNotNull(freeCurrencyNumberStr) || !StringUtil.isNotNull(minCurrencyNumberStr)){
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        TransactionCurrencyVO transactionCurrencyVO = transactionCurrencyService.getTransactionCurrencyByCurrencyName(currencyName);
        if(transactionCurrencyVO == null){
            response.put("code", 3);
            response.put("message", "该币种不存在！");
            return response;
        }

        double freeCurrencyNumber = Double.parseDouble(freeCurrencyNumberStr);
        double minCurrencyNumber = Double.parseDouble(minCurrencyNumberStr);

        JydpCoinConfigDO jydpCoinConfigDO = new JydpCoinConfigDO();

        Timestamp curTime = DateUtil.getCurrentTime();
        String recordNo = SystemCommonConfig.COIN_CONFIG_RECORDNO + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                NumberUtil.createNumberStr(7);

        int currencyId = transactionCurrencyVO.getCurrencyId();
        String ipAddress = IpAddressUtil.getIpAddress(request);

        jydpCoinConfigDO.setRecordNo(recordNo);
        jydpCoinConfigDO.setCurrencyId(transactionCurrencyVO.getCurrencyId());
        jydpCoinConfigDO.setCurrencyName(currencyName);
        jydpCoinConfigDO.setFreeCurrencyNumber(freeCurrencyNumber);
        jydpCoinConfigDO.setMinCurrencyNumber(minCurrencyNumber);
        jydpCoinConfigDO.setIpAddress(ipAddress);
        jydpCoinConfigDO.setBackerId(backerSession.getBackerId());
        jydpCoinConfigDO.setBackerAccount(backerSession.getBackerAccount());
        jydpCoinConfigDO.setConfigStatus(1);
        jydpCoinConfigDO.setAddTime(curTime);

        boolean addResult = jydpCoinConfigService.insertJydpCoinConfig(jydpCoinConfigDO);
        if (addResult) {
            response.put("code", 1);
            response.put("message", "新增成功！");
        } else {
            response.put("code", 5);
            response.put("message", "新增失败！");
        }

        return response;
    }

    /** 删除后台币种管理 */
    @RequestMapping(value = "/deleteCoinConfig.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject deleteCoinConfig(HttpServletRequest request) {
        JSONObject response = new JSONObject();

        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 161003);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            return response;

        }

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.put("code", 4);
            response.put("message", "登录过期");
            return response;
        }

        String recordNo = StringUtil.stringNullHandle(request.getParameter("recordNo"));
        if (!StringUtil.isNotNull(recordNo)){
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        JydpCoinConfigDO jydpCoinConfigDO = jydpCoinConfigService.getJydpCoinConfig(recordNo);
        if(jydpCoinConfigDO == null){
            response.put("code", 3);
            response.put("message", "该记录不存在！");
            return response;
        }

        boolean result = jydpCoinConfigService.deleteJydpCoinConfig(recordNo);
        if (result) {
            response.put("code", 1);
            response.put("message", "删除成功！");
        } else {
            response.put("code", 5);
            response.put("message", "删除失败！");
        }

        return response;
    }

}
