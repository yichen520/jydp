package com.jydp.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.*;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import com.jydp.entity.VO.TransactionMakeOrderVO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyService;
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
            request.setAttribute("message", "您没有此权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        List(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/transactionMakeOrder";
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

        List<TransactionMakeOrderVO> transactionMakeOrderList = null;
        if (totalNumber > 0) {
            transactionMakeOrderList = transactionMakeOrderService.listTransactionMakeOrderForBack(currencyNameStr, executeStatus, paymentType, backAccount, startAddTime, endAddTime, startExecuteTime, endExecuteTime, pageNumber, pageSize);
            for (TransactionMakeOrderVO order: transactionMakeOrderList) {
                order.setTransactionPrice(Double.parseDouble(BigDecimalUtil.div(order.getCurrencyTotalPrice(), order.getCurrencyNumber(), 8)));
            }
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
    public @ResponseBody JsonObjectBO addOrder(HttpServletRequest request) {
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

        String currencyNameStr = StringUtil.stringNullHandle(request.getParameter("addCurrencyName"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("addPaymentType"));
        String currencyNumberStr = StringUtil.stringNullHandle(request.getParameter("addCurrencyNumber"));
        String currencyPriceStr = StringUtil.stringNullHandle(request.getParameter("addCurrencyPrice"));
        String executeTimeStr = StringUtil.stringNullHandle(request.getParameter("addExecuteTime"));

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
    public @ResponseBody JsonObjectBO importOrder(HttpServletRequest request, MultipartFile myfile) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 103004);
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

        InputStream stream = null;
        Workbook wb = null;
        try {
            stream = myfile.getInputStream();
            boolean isExcel2003 = myfile.getOriginalFilename().toLowerCase().endsWith("xls") ? true : false;
            if(isExcel2003){
                wb = new HSSFWorkbook(stream);
            }else{
                wb = new XSSFWorkbook(stream);
            }

            Sheet sheet = wb.getSheetAt(0);

            List<TransactionMakeOrderDO> transactionMakeOrderList = new ArrayList<TransactionMakeOrderDO>();
            //循环表的每一行读取数据,从第2行开始读取，跳过了第一行的表头
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if(row == null){
                    continue;
                }

                Cell currencyNameStr = row.getCell(0);
                Cell paymentTypeStr = row.getCell(1);
                Cell currencyNumberStr = row.getCell(2);
                Cell currencyPriceStr = row.getCell(3);
                Cell executeTimeStr = row.getCell(4);

                Timestamp executeTime = null;
                int paymentType = 0;
                double currencyNumber = 0;
                double currencyPrice = 0;

                if (currencyNameStr != null) {
                    currencyNumber = Double.parseDouble(currencyNumberStr.toString());
                }
                if (currencyPriceStr != null) {
                    currencyPrice = Double.parseDouble(currencyPriceStr.toString());
                }
                if (executeTimeStr != null) {
                    executeTime = DateUtil.stringToTimestamp(executeTimeStr.toString());
                }
                if (paymentTypeStr != null) {
                    double type = Double.parseDouble(paymentTypeStr.toString());
                    if (type == 1) {
                        paymentType = 1;
                    } else if (type == 2) {
                        paymentType = 2;
                    } else {
                        response.setCode(3);
                        response.setMessage("收支类型不正确");
                        return response;
                    }
                }

                Timestamp curTime = DateUtil.getCurrentTime();
                if (executeTime.getTime() <= curTime.getTime()) {
                    response.setCode(3);
                    response.setMessage("执行时间必须大于当前时间");
                    return response;
                }

                TransactionCurrencyDO currency = transactionCurrencyService.getTransactionCurrencyByCurrencyName(currencyNameStr.toString());
                if (currency == null) {
                    response.setCode(3);
                    response.setMessage("币种信息不存在");
                    return response;
                }

                String orderNo = SystemCommonConfig.TRANSACTION_MAKE_ORDER
                        + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10)
                        + NumberUtil.createNumberStr(10);

                double currencyTotalPrice = NumberUtil.doubleFormat(BigDecimalUtil.mul(currencyNumber, currencyPrice), 8);
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

                TransactionMakeOrderDO transactionMakeOrderDO = new TransactionMakeOrderDO();
                transactionMakeOrderDO.setOrderNo(orderNo);
                transactionMakeOrderDO.setPaymentType(paymentType);
                transactionMakeOrderDO.setCurrencyId(currency.getCurrencyId());
                transactionMakeOrderDO.setCurrencyName(currency.getCurrencyName());
                transactionMakeOrderDO.setCurrencyNumber(currencyNumber);
                transactionMakeOrderDO.setCurrencyTotalPrice(currencyTotalPrice);
                transactionMakeOrderDO.setBackerAccount(backerSession.getBackerAccount());
                transactionMakeOrderDO.setIpAddress(ipAddress);
                transactionMakeOrderDO.setExecuteStatus(1);
                transactionMakeOrderDO.setRemark("后台导入");
                transactionMakeOrderDO.setExecuteTime(executeTime);
                transactionMakeOrderDO.setAddTime(curTime);
                transactionMakeOrderList.add(transactionMakeOrderDO);
            }

            //批量导入
            boolean result = transactionMakeOrderService.insertTransactionMakeOrderList(transactionMakeOrderList);
            if (result) {
                response.setCode(1);
                response.setMessage("添加成功");
            } else {
                response.setCode(2);
                response.setMessage("添加失败");
            }
        } catch (Exception e) {
            LogUtil.printErrorLog(e);

            response.setCode(5);
            response.setMessage("操作失败");
        } finally {
            try {
                wb.close();
                stream.close();
            } catch (IOException e) {
                LogUtil.printErrorLog(e);
            }
        }

        return response;
    }

    /** 立即执行 */
    @RequestMapping(value = "/execute.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO execute(HttpServletRequest request) {
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
        boolean result = transactionMakeOrderService.executeMakeOrder(orderNo);
        if (result) {
            response.setCode(1);
            response.setMessage("执行成功");
        } else {
            response.setCode(2);
            response.setMessage("执行失败");
        }
        return response;
    }

    /** 撤回 */
    @RequestMapping(value = "/cancle.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO cancle(HttpServletRequest request) {
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

        //修改状态
        boolean result = transactionMakeOrderService.updateOrderExecuteStatusByOrderNo(orderNo, 5);
        if (result) {
            response.setCode(1);
            response.setMessage("撤销成功");
        } else {
            response.setCode(2);
            response.setMessage("撤销失败");
        }
        return response;
    }

    /** 立即执行 多条*/
    @RequestMapping(value = "/executeMore.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO executeMore(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录时间过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 103005);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String orderNoListStr = StringUtil.stringNullHandle(request.getParameter("orderNoList"));

        if (!StringUtil.isNotNull(orderNoListStr)) {
            response.setCode(3);
            response.setMessage("参数不能为空");
            return response;
        }

        List<String> orderNoList = new ArrayList<String>();
        String[] orderNoArray = orderNoListStr.split(",");
        for (String orderNo : orderNoArray) {
            if (StringUtil.isNotNull(orderNo)) {
                orderNoList.add(orderNo);
            }
        }

        //执行多条添加记录
        boolean result = transactionMakeOrderService.executeMakeOrderMore(orderNoList);
        if (result) {
            response.setCode(1);
            response.setMessage("执行多条成功");
        } else {
            response.setCode(2);
            response.setMessage("执行多条失败");
        }
        return response;
    }

    /** 撤回 */
    @RequestMapping(value = "/cancleMore.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO cancleMore(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录时间过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 103006);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String orderNoListStr = StringUtil.stringNullHandle(request.getParameter("orderNoList"));

        if (!StringUtil.isNotNull(orderNoListStr)) {
            response.setCode(3);
            response.setMessage("参数不能为空");
            return response;
        }

        List<String> orderNoList = new ArrayList<String>();
        String[] orderNoArray = orderNoListStr.split(",");
        for (String orderNo : orderNoArray) {
            if (StringUtil.isNotNull(orderNo)) {
                orderNoList.add(orderNo);
            }
        }

        boolean result = transactionMakeOrderService.updateMakeOrderExecuteStatusByOrderNoList(orderNoList, 5);
        if (result) {
            response.setCode(1);
            response.setMessage("撤销多条成功");
        } else {
            response.setCode(2);
            response.setMessage("撤销多条失败");
        }
        return response;
    }

    /** 下载交易模板 */
    @RequestMapping(value = "/dowland.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO dowland(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 103003);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("downLoadUrl","/upload/excel/make_order.xlsx");

        response.setData(jsonObject);
        response.setCode(1);
        response.setMessage("导出成功");
        return response;
    }
}
