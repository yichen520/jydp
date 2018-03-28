package com.jydp.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IJydpUserCoinOutRecordService;
import com.jydp.service.ITransactionCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * JYDP用户币种转出记录
 * @author zym
 *
 */

@Controller
@RequestMapping("/backerWeb/backerUserCoinOut")
@Scope(value="prototype")
public class BackerUserCoinOutController {

    /** JYDP用户币种转出记录	*/
    @Autowired
    IJydpUserCoinOutRecordService jydpUserCoinOutRecordService;

    /** 交易币种 */
    @Autowired
    ITransactionCurrencyService transactionCurrencyService;

    /** 后台币种转出管理 */
    private void showList(HttpServletRequest request) {
        String coinRecordNo = StringUtil.stringNullHandle(request.getParameter("coinRecordNo"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String walletAccount = StringUtil.stringNullHandle(request.getParameter("walletAccount"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String handleStatusStr = StringUtil.stringNullHandle(request.getParameter("handleStatus"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String startFinishTimeStr = StringUtil.stringNullHandle(request.getParameter("startFinishTime"));
        String endFinishTimeStr = StringUtil.stringNullHandle(request.getParameter("endFinishTime"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        Timestamp startAddTime = null;
        if (StringUtil.isNotNull(startAddTimeStr)) {
            startAddTime = DateUtil.stringToTimestamp(startAddTimeStr);
        }
        Timestamp endAddTime = null;
        if (StringUtil.isNotNull(endAddTimeStr)) {
            endAddTime = DateUtil.stringToTimestamp(endAddTimeStr);
        }
        Timestamp startFinishTime = null;
        if (StringUtil.isNotNull(startFinishTimeStr)) {
            startFinishTime = DateUtil.stringToTimestamp(startFinishTimeStr);
        }
        Timestamp endFinishTime = null;
        if (StringUtil.isNotNull(endFinishTimeStr)) {
            endFinishTime = DateUtil.stringToTimestamp(endFinishTimeStr);
        }

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int handleStatus = 0;
        if (StringUtil.isNotNull(handleStatusStr)) {
            handleStatus = Integer.parseInt(handleStatusStr);
        }

        int currencyId = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        //查询数据
        int totalNumber = jydpUserCoinOutRecordService.countJydpUserCoinOutRecordForBack(coinRecordNo, userAccount, walletAccount, currencyId, handleStatus,
                                                                                    startAddTime, endAddTime, startFinishTime, endFinishTime);

        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = null;
        int pageSize = 20;

        int totalPageNumber = (int) Math.ceil(totalNumber/(pageSize*1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }
        if (totalNumber > 0) {
            jydpUserCoinOutRecordList = jydpUserCoinOutRecordService.listJydpUserCoinOutRecord(coinRecordNo, userAccount, walletAccount, currencyId, handleStatus,
                    startAddTime, endAddTime, startFinishTime, endFinishTime, pageNumber, pageSize);
        }
        //获取币种信息
        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.listTransactionCurrencyAll();

        request.setAttribute("coinRecordNo", coinRecordNo);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("walletAccount", walletAccount);
        request.setAttribute("currencyId", currencyId);
        request.setAttribute("handleStatus", handleStatus);
        request.setAttribute("startAddTime", startAddTimeStr);
        request.setAttribute("endAddTime", endAddTimeStr);
        request.setAttribute("startFinishTime", startFinishTimeStr);
        request.setAttribute("endFinishTime", endFinishTimeStr);

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("jydpUserCoinOutRecordList", jydpUserCoinOutRecordList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);

        request.getSession().setAttribute("backer_pagePowerId", 162000);
    }

    /** 打开币种转出审核页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 162001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }
        showList(request);
        return "page/back/jydpUserCoinOut";
    }

    /** 审核通过 */
    @RequestMapping(value = "/auditPassed.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject auditPassed(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 162002);
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

        String coinRecordNos = StringUtil.stringNullHandle(request.getParameter("coinRecordNo"));
        String remark = StringUtil.stringNullHandle(request.getParameter("passRemark"));

        String[] coinRecordNoStr = coinRecordNos.split(",");
        List<String> coinRecordNoList = new ArrayList<String>();
        for(String coinRecordNo : coinRecordNoStr){
            if(!StringUtil.isNotNull(coinRecordNo)){
                continue;
            }
            coinRecordNoList.add(coinRecordNo);
        }
        if(coinRecordNoList == null || coinRecordNoList.size() == 0){
            response.put("code", 3);
            response.put("message", "请选择审核订单！");
            return response;
        }

        Timestamp handleTime = DateUtil.getCurrentTime();
        boolean resultBoo = jydpUserCoinOutRecordService.updateHandleStatus(coinRecordNoList, remark, handleTime);
        if (resultBoo) {
            response.put("code", 1);
            response.put("message", "审核成功！");
        } else {
            response.put("code", 5);
            response.put("message", "审核失败！");
        }

        return response;
    }

    /** 审核拒绝 */
    @RequestMapping(value = "/auditRefuse.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject auditRefuse(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 162003);
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

        String coinRecordNos = StringUtil.stringNullHandle(request.getParameter("coinRecordNo"));
        String remarks = StringUtil.stringNullHandle(request.getParameter("refuseRemark"));
        String[] coinRecordNoStr = coinRecordNos.split(",");
        List<String> coinRecordNoList = new ArrayList<String>();
        for(String coinRecordNo : coinRecordNoStr){
            if(!StringUtil.isNotNull(coinRecordNo)){
                continue;
            }
            coinRecordNoList.add(coinRecordNo);
        }

        if(coinRecordNoList == null || coinRecordNoList.size() == 0){
            response.put("code", 3);
            response.put("message", "请选择审核订单！");
            return response;
        }

        Timestamp handleTime = DateUtil.getCurrentTime();
        boolean resultBoo = jydpUserCoinOutRecordService.updateRefuseHandleStatus(coinRecordNoList, remarks, handleTime);
        if (resultBoo) {
            response.put("code", 1);
            response.put("message", "审核成功！");
        } else {
            response.put("code", 5);
            response.put("message", "审核失败！");
        }

        return response;
    }
}
