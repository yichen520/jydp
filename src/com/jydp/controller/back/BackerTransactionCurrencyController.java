package com.jydp.controller.back;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.ImageReduceUtil;
import com.iqmkj.utils.IpAddressUtil;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ITransactionCurrencyService;
import config.FileUrlConfig;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String backAccount = StringUtil.stringNullHandle(request.getParameter("backerAccount"));
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
        int currencyId = 0;
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
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;

        int totalNumber = transactionCurrencyService.countTransactionCurrencyForBack(currencyId, paymentType, upStatus,
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
            transactionCurrencyVOList = transactionCurrencyService.listTransactionCurrencyForBack(currencyId, paymentType,
                    upStatus, backAccount, startAddTime, endAddTime,  startUpTime, endUpTime, pageNumber, pageSize);

            if (transactionCurrencyVOList != null && transactionCurrencyVOList.size() > 0) {
                for (TransactionCurrencyVO curr: transactionCurrencyVOList) {
                    curr.setBuyFee(BigDecimalUtil.mul(curr.getBuyFee(), 100));
                    curr.setSellFee(BigDecimalUtil.mul(curr.getSellFee(), 100));
                }
            }
        }
        int totalCurrNumber = transactionCurrencyService.countTransactionCurrencyForBack(0, 0, 0,
                null, null, null, null, null);

        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.listTransactionCurrencyAll();

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startAddTime", startAddTimeStr);
        request.setAttribute("endAddTime", endAddTimeStr);
        request.setAttribute("startUpTime", startUpTimeStr);
        request.setAttribute("endUpTime", endUpTimeStr);
        request.setAttribute("backAccount", backAccount);
        request.setAttribute("paymentType", paymentType);
        request.setAttribute("upStatus", upStatus);
        request.setAttribute("currencyId", currencyId);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalCurrNumber", totalCurrNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("transactionCurrencyVOList", transactionCurrencyVOList);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 104000);
    }

    /** 新增币种 */
    @RequestMapping(value = "/add.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO add(HttpServletRequest request, MultipartFile adsImageUrl) {
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

        String currencyNameStr = StringUtil.stringNullHandle(request.getParameter("currencyNameAd"));
        String currencyShortNameStr = StringUtil.stringNullHandle(request.getParameter("currencyShortNameAd"));
        String buyFeeStr = StringUtil.stringNullHandle(request.getParameter("buyFeeAd"));
        String sellFeeStr = StringUtil.stringNullHandle(request.getParameter("sellFeeAd"));
        String guidancePriceStr =  StringUtil.stringNullHandle(request.getParameter("guidAd"));
        String statusStr = StringUtil.stringNullHandle(request.getParameter("status"));
        String upTimeStr = StringUtil.stringNullHandle(request.getParameter("upTimeAd"));
        if (!StringUtil.isNotNull(currencyNameStr) || !StringUtil.isNotNull(currencyShortNameStr) || !StringUtil.isNotNull(buyFeeStr)
                || !StringUtil.isNotNull(sellFeeStr) || !StringUtil.isNotNull(statusStr) || !StringUtil.isNotNull(guidancePriceStr)
                || adsImageUrl == null || adsImageUrl.isEmpty()){
            response.setCode(3);
            response.setMessage("参数错误");
            return response;
        }

        int status = 0;
        double buyFee = 0;
        double sellFee = 0;
        double guidancePrice = 0;
        Timestamp upTime = null;

        status = Integer.parseInt(statusStr);
        buyFee = NumberUtil.doubleFormat(Double.parseDouble(buyFeeStr) / 100, 8);
        sellFee = NumberUtil.doubleFormat(Double.parseDouble(sellFeeStr) / 100, 8);
        guidancePrice = NumberUtil.doubleFormat(Double.parseDouble(guidancePriceStr), 8);

        if (status == 2) {
            if (!StringUtil.isNotNull(upTimeStr)) {
                response.setCode(3);
                response.setMessage("参数错误");
                return response;
            }
            upTime = DateUtil.stringToTimestamp(upTimeStr);
            if (upTime.getTime() < DateUtil.getCurrentTime().getTime()){
                response.setCode(3);
                response.setMessage("上线时间应大于当前时间");
                return response;
            }
        }
        if (guidancePrice <= 0) {
            response.setCode(3);
            response.setMessage("指导价必须大于0");
            return response;
        }
        if (buyFee < 0 || sellFee < 0) {
            response.setCode(3);
            response.setMessage("费率不能小于0");
            return response;
        }

        TransactionCurrencyVO currurrencyName = transactionCurrencyService.getTransactionCurrencyByCurrencyName(currencyNameStr);
        TransactionCurrencyVO currencyByshortName = transactionCurrencyService.getTransactionCurrencyByCurrencyShortName(currencyShortNameStr);
        if (currurrencyName != null || currencyByshortName != null) {
            response.setCode(3);
            response.setMessage("该币种已存在");
            return response;
        }

        String imageUrl = ImageReduceUtil.reduceImageUploadRemote(adsImageUrl, FileUrlConfig.file_remote_transactionCyrrency_url);

        if(imageUrl == "" || imageUrl == null){
            response.setCode(3);
            response.setMessage("图片新增失败！");
            return response;
        }

        //新增币种
        boolean result = transactionCurrencyService.addTransactionCurrency(currencyShortNameStr, currencyNameStr, imageUrl,
                buyFee, sellFee,2, 1, backerSession.getBackerAccount(),
                IpAddressUtil.getIpAddress(request), guidancePrice, upTime, DateUtil.getCurrentTime());
        if (result) {
            response.setCode(1);
            response.setMessage("新增成功");
        } else {
            response.setCode(3);
            response.setMessage("新增失败");
        }

        return response;
    }

    /** 修改币种信息 */
    @RequestMapping(value = "/update.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO update(HttpServletRequest request, MultipartFile imgUrl) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 104003);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String currencyNameStr = StringUtil.stringNullHandle(request.getParameter("currencyNameUp"));
        String currencyShortNameStr = StringUtil.stringNullHandle(request.getParameter("currencyShortNameUp"));
        String buyFeeStr = StringUtil.stringNullHandle(request.getParameter("buyFeeUp"));
        String sellFeeStr = StringUtil.stringNullHandle(request.getParameter("sellFeeUp"));
        String guidancePriceStr =  StringUtil.stringNullHandle(request.getParameter("guidUp"));
        String upTimeStr = StringUtil.stringNullHandle(request.getParameter("upTimeUp"));
        if (!StringUtil.isNotNull(currencyIdStr) || !StringUtil.isNotNull(currencyNameStr) || !StringUtil.isNotNull(currencyShortNameStr) || !StringUtil.isNotNull(buyFeeStr)
                || !StringUtil.isNotNull(sellFeeStr)){
            response.setCode(3);
            response.setMessage("参数错误");
            return response;
        }

        int currencyId = 0;
        double buyFee = 0;
        double sellFee = 0;
        double guidancePrice = 0;
        Timestamp upTime = null;

        currencyId = Integer.parseInt(currencyIdStr);
        buyFee = NumberUtil.doubleFormat(Double.parseDouble(buyFeeStr) / 100, 8);
        sellFee = NumberUtil.doubleFormat(Double.parseDouble(sellFeeStr) / 100, 8);
        TransactionCurrencyVO currency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if (currency == null) {
            response.setCode(3);
            response.setMessage("币种不存在");
            return response;
        }

        if (currency.getUpStatus() != 2 && StringUtil.isNotNull(upTimeStr)) {
            upTime = DateUtil.stringToTimestamp(upTimeStr);
            if (upTime.getTime() <= DateUtil.getCurrentTime().getTime()) {
                response.setCode(3);
                response.setMessage("执行时间应大于当前时间");
                return response;
            }

            currency.setPaymentType(2);
            currency.setUpStatus(1);
            currency.setUpTime(upTime);
        }
        if (StringUtil.isNotNull(guidancePriceStr)) {
            guidancePrice = Double.parseDouble(guidancePriceStr);
            currency.setGuidancePrice(guidancePrice);
        }

        if (guidancePrice < 0) {
            response.setCode(3);
            response.setMessage("指导价不能小于0");
            return response;
        }
        if (buyFee < 0 || sellFee < 0) {
            response.setCode(3);
            response.setMessage("费率不能小于0");
            return response;
        }

        String imageUrl = "";
        if (imgUrl != null && !imgUrl.isEmpty()) {
            imageUrl = ImageReduceUtil.reduceImageUploadRemote(imgUrl, FileUrlConfig.file_remote_transactionCyrrency_url);
            if(imageUrl == "" || imageUrl == null){
                response.setCode(3);
                response.setMessage("图片新增失败！");
                return response;
            }
            FileWriteRemoteUtil.deleteFile(currency.getCurrencyImg());
        }

        if (StringUtil.isNotNull(imageUrl)) {
            currency.setCurrencyImg(imageUrl);
        }

        currency.setCurrencyShortName(currencyShortNameStr);
        currency.setCurrencyName(currencyNameStr);
        currency.setBuyFee(buyFee);
        currency.setSellFee(sellFee);
        currency.setBackerAccount(backerSession.getBackerAccount());
        currency.setIpAddress(IpAddressUtil.getIpAddress(request));

        boolean result = transactionCurrencyService.updateTransactionCurrency(currency);
        if (result) {
            response.setCode(1);
            response.setMessage("修改成功");
        } else {
            response.setCode(5);
            response.setMessage("修改失败");
        }

        return response;
    }

    /** 修改交易状态 */
    @RequestMapping(value = "/editPayType.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO editPayType(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }

        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        if (!StringUtil.isNotNull(currencyIdStr) || !StringUtil.isNotNull(paymentTypeStr)){
            response.setCode(3);
            response.setMessage("参数错误");
            return response;
        }

        int currencyId = 0;
        int paymentType = 0;
        currencyId = Integer.parseInt(currencyIdStr);
        paymentType = Integer.parseInt(paymentTypeStr);

        if (paymentType == 2) {
            //业务功能权限
            boolean havePower = BackerWebInterceptor.validatePower(request, 104007);
            if (!havePower) {
                response.setCode(5);
                response.setMessage("您没有该权限");
                return response;
            }
            response.setMessage("停牌成功");
        } else {
            //业务功能权限
            boolean havePower = BackerWebInterceptor.validatePower(request, 104008);
            if (!havePower) {
                response.setCode(5);
                response.setMessage("您没有该权限");
                return response;
            }
            response.setMessage("复牌成功");
        }

        TransactionCurrencyVO currency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if (currency == null) {
            response.setCode(3);
            response.setMessage("币种不存在");
            return response;
        }
        if (currency.getPaymentType() == paymentType) {
            response.setCode(3);
            response.setMessage("币种已修改");
            return response;
        }

        boolean result = transactionCurrencyService.updatePaymentType(currencyId, paymentType,
                backerSession.getBackerAccount(), IpAddressUtil.getIpAddress(request));
        if (result) {
            response.setCode(1);
        } else {
            response.setCode(5);
            response.setMessage("修改停复牌状态失败");
        }
        return response;
    }

    /** 修改上线状态 */
    @RequestMapping(value = "/editUpType.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO editUpType(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }

        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String upStatusStr = StringUtil.stringNullHandle(request.getParameter("upStatus"));
        if (!StringUtil.isNotNull(upStatusStr) || !StringUtil.isNotNull(currencyIdStr)){
            response.setCode(3);
            response.setMessage("参数错误");
            return response;
        }

        int currencyId = 0;
        int upStatus = 0;
        currencyId = Integer.parseInt(currencyIdStr);
        upStatus = Integer.parseInt(upStatusStr);
        if (upStatus == 2) {
            //业务功能权限
            boolean havePower = BackerWebInterceptor.validatePower(request, 104005);
            if (!havePower) {
                response.setCode(5);
                response.setMessage("您没有该权限");
                return response;
            }
            response.setMessage("上线成功");
        }
        if (upStatus == 4) {
            //业务功能权限
            boolean havePower = BackerWebInterceptor.validatePower(request, 104006);
            if (!havePower) {
                response.setCode(5);
                response.setMessage("您没有该权限");
                return response;
            }
            response.setMessage("下线成功");
        }

        boolean result = transactionCurrencyService.updateUpStatus(currencyId, upStatus,
                backerSession.getBackerAccount(), IpAddressUtil.getIpAddress(request), DateUtil.getCurrentTime());
        if (result) {
            response.setCode(1);
        } else {
            response.setCode(5);
            response.setMessage("修改状态失败");
        }
        return response;
    }

    /** 导出数据 */
    @RequestMapping(value = "/export.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO export(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 104004);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String backAccount = StringUtil.stringNullHandle(request.getParameter("backerAccount"));
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
        int currencyId = 0;
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
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;

        int totalNumber = transactionCurrencyService.countTransactionCurrencyForBack(currencyId, paymentType, upStatus,
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
            transactionCurrencyVOList = transactionCurrencyService.listTransactionCurrencyForBack(currencyId, paymentType,
                    upStatus, backAccount, startAddTime, endAddTime, startUpTime, endUpTime, pageNumber, pageSize);
        }

        String path = request.getServletContext().getRealPath("/upload") + "/excel/";

        XSSFWorkbook workBook = null;
        OutputStream stream = null;
        try {
            workBook = new XSSFWorkbook();
            XSSFSheet sheet1 = workBook.createSheet("币种信息");
            // 设置列宽
            sheet1.setColumnWidth(0, 5000);
            sheet1.setColumnWidth(1, 5000);
            sheet1.setColumnWidth(2, 5000);
            sheet1.setColumnWidth(3, 5000);
            sheet1.setColumnWidth(4, 5000);
            sheet1.setColumnWidth(5, 5000);
            sheet1.setColumnWidth(6, 5000);
            sheet1.setColumnWidth(7, 5000);
            sheet1.setColumnWidth(8, 5000);

            int rowNumber = 0;// 行
            // 先列出表头
            XSSFRow row = sheet1.createRow(rowNumber);

            XSSFCell cell = row.createCell(0);
            cell.setCellValue("添加时间");
            cell = row.createCell(1);
            cell.setCellValue("币种名称");
            cell = row.createCell(2);
            cell.setCellValue("英文标识");
            cell = row.createCell(3);
            cell.setCellValue("买入手续费(%)");
            cell = row.createCell(4);
            cell.setCellValue("卖出手续费(%)");
            cell = row.createCell(5);
            cell.setCellValue("交易状态");
            cell = row.createCell(6);
            cell.setCellValue("上线时间");
            cell = row.createCell(7);
            cell.setCellValue("上线状态");
            cell = row.createCell(8);
            cell.setCellValue("上市指导价");

            rowNumber++;

            // 循环导出数据
            for (TransactionCurrencyVO transactionCurrency : transactionCurrencyVOList) {
                row = sheet1.createRow(rowNumber);

                cell = row.createCell(0);
                cell.setCellValue(DateUtil.longToTimeStr(transactionCurrency.getAddTime().getTime(), DateUtil.dateFormat2));
                cell = row.createCell(1);
                cell.setCellValue(transactionCurrency.getCurrencyName());
                cell = row.createCell(2);
                cell.setCellValue(transactionCurrency.getCurrencyShortName());
                cell = row.createCell(3);
                cell.setCellValue(transactionCurrency.getBuyFee() * 100);
                cell = row.createCell(4);
                cell.setCellValue(transactionCurrency.getSellFee() * 100);
                cell = row.createCell(5);
                if (transactionCurrency.getPaymentType() == 1) {
                    cell.setCellValue("正常");
                } else {
                    cell.setCellValue("停牌");
                }
                cell = row.createCell(6);
                cell.setCellValue(DateUtil.longToTimeStr(transactionCurrency.getUpTime().getTime(), DateUtil.dateFormat2));
                cell = row.createCell(7);
                if (transactionCurrency.getUpStatus() == 1) {
                    cell.setCellValue("待上线");
                } else if (transactionCurrency.getUpStatus() == 2) {
                    cell.setCellValue("上线中");
                } else if (transactionCurrency.getUpStatus() == 3) {
                    cell.setCellValue("停牌");
                } else if (transactionCurrency.getUpStatus() == 4) {
                    cell.setCellValue("已下线");
                }
                cell = row.createCell(8);
                cell.setCellValue(transactionCurrency.getGuidancePrice());

                rowNumber++;
            }

            StringBuffer url = new StringBuffer();
            url.append(path);
            url.append("transactionCurrency_");
            String dateStr = DateUtil.longToTimeStr(DateUtil.getCurrentTimeMillis(), DateUtil.dateFormat6);
            url.append(dateStr);
            url.append(".xlsx");

            stream = new FileOutputStream(url.toString());
            workBook.write(stream);

            response.setCode(1);
            response.setMessage("/upload/excel/transactionCurrency_" + dateStr + ".xlsx");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);

            response.setCode(5);
            response.setMessage("导出失败，请重试");
        } finally {
            try {
                workBook.close();
                stream.close();
            } catch (IOException e) {
                LogUtil.printErrorLog(e);
            }
        }
        return response;
    }

    /** 上移币种 */
    @RequestMapping(value = "/up.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO uprank(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 104009);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        if (!StringUtil.isNotNull(currencyIdStr)){
            response.setCode(3);
            response.setMessage("参数错误");
            return response;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);

        boolean result =  transactionCurrencyService.upCurrencyRankNumber(currencyId);
        if (result) {
            response.setCode(1);
            response.setMessage("上移成功");
        } else {
            response.setCode(5);
            response.setMessage("上移失败");
        }
        return response;
    }

    /** 下移币种 */
    @RequestMapping(value = "/down.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO downrank(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 104010);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        if (!StringUtil.isNotNull(currencyIdStr)){
            response.setCode(3);
            response.setMessage("参数错误");
            return response;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);

        boolean result =  transactionCurrencyService.downCurrencyRankNumber(currencyId);
        if (result) {
            response.setCode(1);
            response.setMessage("下移成功");
        } else {
            response.setCode(5);
            response.setMessage("下移失败");
        }
        return response;
    }

    /** 置顶币种 */
    @RequestMapping(value = "/top.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO toprank(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 104011);
        if (!havePower) {
            response.setCode(5);
            response.setMessage("您没有该权限");
            return response;
        }

        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        if (!StringUtil.isNotNull(currencyIdStr)){
            response.setCode(3);
            response.setMessage("参数错误");
            return response;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);

        boolean result =  transactionCurrencyService.topCurrencyRankNumber(currencyId);
        if (result) {
            response.setCode(1);
            response.setMessage("置顶成功");
        } else {
            response.setCode(5);
            response.setMessage("置顶失败");
        }
        return response;
    }
}
