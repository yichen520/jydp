package com.jydp.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.*;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.entity.VO.TransactionMakeOrderVO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionDealRedisService;
import com.jydp.service.ITransactionMakeOrderService;
import config.SystemCommonConfig;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    /**
     * 后台做单
     */
    @Autowired
    private ITransactionMakeOrderService transactionMakeOrderService;

    /**
     * 币种信息
     */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /**
     * redis成交记录
     */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

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
        boolean havePower = BackerWebInterceptor.validatePower(request, 151001);
        if (!havePower) {
            request.setAttribute("code", 5);
            request.setAttribute("message", "您没有此权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        List(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/transactionMakeOrder";
    }


    /**
     * 查询数据
     */
    public void List(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String orderNoStr = StringUtil.stringNullHandle(request.getParameter("orderNo"));
        String currencyNameStr = StringUtil.stringNullHandle(request.getParameter("currencyName"));
        String executeStatusStr = StringUtil.stringNullHandle(request.getParameter("executeStatus"));
        String startExecuteTimeStr = StringUtil.stringNullHandle(request.getParameter("startExecuteTime"));
        String endExecuteTimeStr = StringUtil.stringNullHandle(request.getParameter("endExecuteTime"));

        Timestamp startExecuteTime = null;
        Timestamp endExecuteTime = null;
        int executeStatus = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(startExecuteTimeStr)) {
            startExecuteTime = DateUtil.stringToTimestamp(startExecuteTimeStr);
        }
        if (StringUtil.isNotNull(endExecuteTimeStr)) {
            endExecuteTime = DateUtil.stringToTimestamp(endExecuteTimeStr);
        }
        if (StringUtil.isNotNull(executeStatusStr)) {
            executeStatus = Integer.parseInt(executeStatusStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;

        int totalNumber = transactionMakeOrderService.countTransactionMakeOrderForBack(orderNoStr, currencyNameStr, executeStatus, startExecuteTime, endExecuteTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<TransactionMakeOrderDO> transactionMakeOrderList = null;
        if (totalNumber > 0) {
            transactionMakeOrderList = transactionMakeOrderService.listTransactionMakeOrderForBack(orderNoStr, currencyNameStr, executeStatus, startExecuteTime, endExecuteTime, pageNumber, pageSize);
        }

        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.listTransactionCurrencyAll();

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startExecuteTime", startExecuteTimeStr);
        request.setAttribute("endExecuteTime", endExecuteTimeStr);
        request.setAttribute("orderNo", orderNoStr);
        request.setAttribute("executeStatus", executeStatus);
        request.setAttribute("currencyName", currencyNameStr);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("transactionMakeOrderList", transactionMakeOrderList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 151000);
    }

    /**
     * 去新增页面
     */
    @RequestMapping("/goAdd.htm")
    public String goAdd(HttpServletRequest request) {
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 151002);
        if (!havePower) {
            request.setAttribute("code", 5);
            request.setAttribute("message", "您没有此功能权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.listTransactionCurrencyAll();

        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        request.setAttribute("code", 1);
        request.setAttribute("message", "操作成功");
        return "page/back/transactionMakeOrderAdd";
    }

    /**
     * 新增交易
     */
    @RequestMapping(value = "/addOrder.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO addOrder(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 151002);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String currencyNameStr = StringUtil.stringNullHandle(request.getParameter("addCurrencyName"));
        String textArStr = StringUtil.stringNullHandle(request.getParameter("textAr"));
        String executeTimeStr = StringUtil.stringNullHandle(request.getParameter("addExecuteTime"));

        if (!StringUtil.isNotNull(currencyNameStr) || !StringUtil.isNotNull(textArStr) || !StringUtil.isNotNull(executeTimeStr)) {
            response.setCode(3);
            response.setMessage("参数不能为空");
            return response;
        }

        Timestamp executeDate = null;
        Long curTimel = DateUtil.lingchenLong();
        String[] oneArr = textArStr.split("\\$");
        List<TransactionMakeOrderDO> transactionMakeOrderList = new ArrayList<TransactionMakeOrderDO>();

        String ipAddress = IpAddressUtil.getIpAddress(request);
        if (!StringUtil.isNotNull(ipAddress)) {
            response.setCode(3);
            response.setMessage("ip地址不能为空");
            return response;
        }
        //判断币种
        TransactionCurrencyDO currency = transactionCurrencyService.getTransactionCurrencyByCurrencyName(currencyNameStr);
        if (currency == null) {
            response.setCode(3);
            response.setMessage("币种信息不存在");
            return response;
        }
        //拆分执行命令
        if (oneArr == null || oneArr.length <= 0) {
            response.setCode(3);
            response.setMessage("执行命令错误");
            return response;
        }
        for (String orderDo : oneArr) {
            String[] twoArr = orderDo.split(",");
            if (twoArr.length < 3) {
                continue;
            }

            String[] timeArr = twoArr[0].split(":");
            if (timeArr.length < 2){
                continue;
            }

            executeDate = DateUtil.stringToTimestampByFormatStr(executeTimeStr, DateUtil.dateFormat4);
            long hour = Integer.parseInt(StringUtil.stringNullHandle(timeArr[0])) * 1000L * 60 * 60;
            long minu = Integer.parseInt(StringUtil.stringNullHandle(timeArr[1])) * 1000L * 60;
            long execTimeL = hour + minu;

            double currencyPrice = Double.parseDouble(StringUtil.stringNullHandle(twoArr[1]));
            double currencyNumber = Double.parseDouble(StringUtil.stringNullHandle(twoArr[2]));
            if (execTimeL < 0 || currencyPrice < 0 || currencyNumber < 0) {
                response.setCode(5);
                response.setMessage("执行命令内容错误");
                return response;
            }

            Timestamp curTime = DateUtil.getCurrentTime();
            String orderNo = SystemCommonConfig.TRANSACTION_MAKE_ORDER
                    + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10)
                    + NumberUtil.createNumberStr(10);
            Timestamp executeTime = DateUtil.longToTimestamp(executeDate.getTime() + execTimeL);

            TransactionMakeOrderDO makeOrder = new TransactionMakeOrderDO();
            makeOrder.setOrderNo(orderNo);
            makeOrder.setCurrencyId(currency.getCurrencyId());
            makeOrder.setCurrencyName(currencyNameStr);
            makeOrder.setCurrencyPrice(currencyPrice);
            makeOrder.setCurrencyNumber(currencyNumber);
            makeOrder.setBackerAccount(backerSession.getBackerAccount());
            makeOrder.setIpAddress(ipAddress);
            makeOrder.setExecuteStatus(1);
            makeOrder.setRemark(null);
            makeOrder.setExecuteTime(executeTime);
            makeOrder.setAddTime(curTime);

            transactionMakeOrderList.add(makeOrder);
        }
        if (transactionMakeOrderList == null || transactionMakeOrderList.isEmpty()) {
            response.setCode(5);
            response.setMessage("数据获取失败");
            return response;
        }

        boolean result = transactionMakeOrderService.insertTransactionMakeOrderList(transactionMakeOrderList);
        if (result) {
            response.setCode(1);
            response.setMessage("交易记录添加成功");
        } else {
            response.setCode(2);
            response.setMessage("交易记录添加失败");
        }
        return response;
    }

    /**
     * 查看详情
     */
    @RequestMapping("/showDetail/{detailNo}")
    public String showDetail(HttpServletRequest request, @PathVariable("detailNo") String orderNoStr) {
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 151004);
        if (!havePower) {
            request.setAttribute("code", 5);
            request.setAttribute("message", "您没有权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        if (!StringUtil.isNotNull(orderNoStr)) {
            List(request);

            request.setAttribute("code", 3);
            request.setAttribute("message", "参数不能为空");
            return "page/back/transactionMakeOrder";
        }

        TransactionMakeOrderDO order = transactionMakeOrderService.getTransactionMakeOrderByOrderNo(orderNoStr);
        if (order == null) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "该记录不存在");
            return "page/back/transactionMakeOrderDetail";
        }

        int count = 0;

        List<TransactionDealRedisDO> resultList = transactionDealRedisService.listTransactionDealRedisByOrderNo(orderNoStr);
        if (resultList != null && !resultList.isEmpty()) {
            count = resultList.size();
        }

        request.setAttribute("count", count);
        request.setAttribute("order", order);
        request.setAttribute("resultList", resultList);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/transactionMakeOrderDetail";
    }

    /**
     * 删除交易
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
        boolean havePower = BackerWebInterceptor.validatePower(request, 151003);
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

        TransactionMakeOrderDO order = transactionMakeOrderService.getTransactionMakeOrderByOrderNo(orderNoStr);
        if (order == null || order.getExecuteStatus() != 3 || order.getExecuteStatus() != 4) {
            response.setCode(3);
            response.setMessage("该记录不能删除");
        }

        boolean result = transactionMakeOrderService.deleteMakeOrderByOrderNo(orderNoStr);
        if (result) {
            response.setCode(1);
            response.setMessage("交易记录删除成功");
        } else {
            response.setCode(2);
            response.setMessage("交易记录删除失败");
        }
        return response;
    }
}
