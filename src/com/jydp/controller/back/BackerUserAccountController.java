package com.jydp.controller.back;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.IpAddressUtil;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.back.BackerDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.BackerUserCurrencyNumDTO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IBackerService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.IUserService;
import com.jydp.service.IUserSessionService;
import config.PhoneAreaConfig;
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

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户账户
 * Author: hht
 * Date: 2018-02-09 11:05
 */
@Controller
@RequestMapping("/backerWeb/backerUserAccount")
@Scope(value="prototype")
public class BackerUserAccountController {

    /** 用户账户 */
    @Autowired
    private IUserService userService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 后台管理员 */
    @Autowired
    private IBackerService backerService;

    /** 用户登录记录 */
    @Autowired
    private IUserSessionService userSessionService;

    /** 展示列表页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141101);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        showList(request);
        return "page/back/userAccount";
    }

    /** 展示账户明细页面 */
    @RequestMapping(value = "/showDetail.htm", method = RequestMethod.POST)
    public String showDetail(HttpServletRequest request) {
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141103);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        if (!StringUtil.isNotNull(userIdStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数为空");
            showList(request);
            return "page/back/userAccount";
        }

        int userId = Integer.parseInt(userIdStr);
        UserDO userDO = userService.getUserByUserId(userId);
        if (userDO == null || userDO.getAccountStatus() <= 0) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "用户不存在");
            showList(request);
            return "page/back/userAccount";
        }

        List<BackerUserCurrencyNumDTO> userCurrencyNumList = userCurrencyNumService.getUserCurrencyNumByUserIdForBacker(userId);

        request.setAttribute("userCurrencyNumList", userCurrencyNumList);
        request.setAttribute("userId", userIdStr);
        request.setAttribute("userAccount", userDO.getUserAccount());
        return "page/back/userAccountDetail";
    }

    /** 获取列表数据 */
    private void showList(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String startTimeStr = StringUtil.stringNullHandle(request.getParameter("startTime"));
        String endTimeStr = StringUtil.stringNullHandle(request.getParameter("endTime"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String phoneNumber = StringUtil.stringNullHandle(request.getParameter("phoneNumber"));
        String accountStatusStr = StringUtil.stringNullHandle(request.getParameter("accountStatus"));
        String authenticationStatusStr = StringUtil.stringNullHandle(request.getParameter("authenticationStatus"));
        String phoneAreaCode = StringUtil.stringNullHandle(request.getParameter("phoneAreaCode"));

        Timestamp startTime = null;
        Timestamp endTime = null;
        int accountStatus = 0;
        int authenticationStatus = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(startTimeStr)) {
            startTime = DateUtil.stringToTimestamp(startTimeStr);
        }
        if (StringUtil.isNotNull(endTimeStr)) {
            endTime = DateUtil.stringToTimestamp(endTimeStr);
        }
        if (StringUtil.isNotNull(accountStatusStr)) {
            accountStatus = Integer.parseInt(accountStatusStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }
        if (StringUtil.isNotNull(authenticationStatusStr)) {
            authenticationStatus = Integer.parseInt(authenticationStatusStr);
        }

        int pageSize = 20;
        int totalNumber = userService.countUserForBacker(userAccount, phoneAreaCode, phoneNumber,
                accountStatus, authenticationStatus, startTime, endTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber -1;
        }

        List<UserDO> userList = null;
        if (totalNumber > 0) {
            userList = userService.listUserForBacker(userAccount, phoneAreaCode,
                    phoneNumber, accountStatus, authenticationStatus, startTime, endTime, pageNumber, pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startTime", startTimeStr);
        request.setAttribute("endTime", endTimeStr);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("phoneNumber", phoneNumber);
        request.setAttribute("phoneAreaCode", phoneAreaCode);
        request.setAttribute("accountStatus", accountStatus);
        request.setAttribute("authenticationStatus", authenticationStatus);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("userList", userList);
        Map<String, String> phoneAreaMap = PhoneAreaConfig.phoneAreaMap;
        request.setAttribute("phoneAreaMap", phoneAreaMap);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 141100);
    }

    /** 用户账户-启用 */
    @RequestMapping(value = "/unlock.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO unlock(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            responseJson.setCode(4);
            responseJson.setMessage("登录过期，请重新登录");
            return responseJson;
        }
        boolean handleFrequent = BackerWebInterceptor.handleFrequent(request);
        if (handleFrequent) {
            responseJson.setCode(6);
            responseJson.setMessage("您的操作太频繁");
            return responseJson;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141105);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        if (!StringUtil.isNotNull(userIdStr)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        boolean updateResult = userService.updateUserAccountStatus(userId, 1,2);
        if (updateResult) {
            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
            return responseJson;
        }

        responseJson.setCode(5);
        responseJson.setMessage("操作失败");
        return responseJson;
    }

    /** 用户账户-禁用 */
    @RequestMapping(value = "/lock.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO lock(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            responseJson.setCode(4);
            responseJson.setMessage("登录过期，请重新登录");
            return responseJson;
        }
        boolean handleFrequent = BackerWebInterceptor.handleFrequent(request);
        if (handleFrequent) {
            responseJson.setCode(6);
            responseJson.setMessage("您的操作太频繁");
            return responseJson;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141104);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        if (!StringUtil.isNotNull(userIdStr)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        boolean updateResult = userService.updateUserAccountStatus(userId, 2,1);
        if (updateResult) {
            //删除用户session
            userSessionService.deleteRedisSession(userId);

            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
            return responseJson;
        }

        responseJson.setCode(5);
        responseJson.setMessage("操作失败");
        return responseJson;
    }

    /** 增加账户余额 */
    @RequestMapping(value = "/addAmount.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO addAmount(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            responseJson.setCode(4);
            responseJson.setMessage("登录过期，请重新登录");
            return responseJson;
        }
        boolean handleFrequent = BackerWebInterceptor.handleFrequent(request);
        if (handleFrequent) {
            responseJson.setCode(6);
            responseJson.setMessage("您的操作太频繁");
            return responseJson;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141106);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String balanceNumberStr = StringUtil.stringNullHandle(request.getParameter("balanceNumber"));
        String remark = StringUtil.stringNullHandle(request.getParameter("remark"));
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)
                || !StringUtil.isNotNull(balanceNumberStr)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        double balanceNumber = Double.parseDouble(balanceNumberStr);
        String ipAddress = IpAddressUtil.getIpAddress(request);

        BackerDO backer = backerService.getBackerById(backerSession.getBackerId());
        if (backer == null || backer.getAccountStatus() != 1) {
            responseJson.setCode(3);
            responseJson.setMessage("操作非法");
            return responseJson;
        }

        boolean updateResult = userService.addBalanceNumberForBack(userId, userAccount, balanceNumber,
                backer.getBackerId(), backer.getBackerAccount(), remark, ipAddress);
        if (updateResult) {
            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
            return responseJson;
        }

        responseJson.setCode(5);
        responseJson.setMessage("操作失败");
        return responseJson;
    }

    /** 减少账户余额 */
    @RequestMapping(value = "/reduceAmount.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO reduceAmount(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            responseJson.setCode(4);
            responseJson.setMessage("登录过期，请重新登录");
            return responseJson;
        }
        boolean handleFrequent = BackerWebInterceptor.handleFrequent(request);
        if (handleFrequent) {
            responseJson.setCode(6);
            responseJson.setMessage("您的操作太频繁");
            return responseJson;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141107);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String balanceNumberStr = StringUtil.stringNullHandle(request.getParameter("balanceNumber"));
        String remark = StringUtil.stringNullHandle(request.getParameter("remark"));
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)
                || !StringUtil.isNotNull(balanceNumberStr)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        double balanceNumber = Double.parseDouble(balanceNumberStr);
        String ipAddress = IpAddressUtil.getIpAddress(request);

        BackerDO backer = backerService.getBackerById(backerSession.getBackerId());
        if (backer == null || backer.getAccountStatus() != 1) {
            responseJson.setCode(3);
            responseJson.setMessage("操作非法，您的账号不可用");
            return responseJson;
        }

        boolean updateResult = userService.reduceBalanceNumberForBack(userId, userAccount, balanceNumber,
                backerSession.getBackerId(), backerSession.getBackerAccount(), remark, ipAddress);
        if (updateResult) {
            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
            return responseJson;
        }

        responseJson.setCode(5);
        responseJson.setMessage("操作失败");
        return responseJson;
    }

    /** 导出数据 */
    @RequestMapping(value = "/exportData.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO exportData(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            responseJson.setCode(4);
            responseJson.setMessage("登录过期，请重新登录");
            return responseJson;
        }
        boolean handleFrequent = BackerWebInterceptor.handleFrequent(request);
        if (handleFrequent) {
            responseJson.setCode(6);
            responseJson.setMessage("您的操作太频繁");
            return responseJson;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141102);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String startTimeStr = StringUtil.stringNullHandle(request.getParameter("startTime"));
        String endTimeStr = StringUtil.stringNullHandle(request.getParameter("endTime"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String phoneNumber = StringUtil.stringNullHandle(request.getParameter("phoneNumber"));
        String phoneAreaCode = StringUtil.stringNullHandle(request.getParameter("phoneAreaCode"));
        String accountStatusStr = StringUtil.stringNullHandle(request.getParameter("accountStatus"));
        String authenticationStatusStr = StringUtil.stringNullHandle(request.getParameter("authenticationStatus"));

        Timestamp startTime = null;
        Timestamp endTime = null;
        int accountStatus = 0;
        int authenticationStatus = 0;

        if (StringUtil.isNotNull(startTimeStr)) {
            startTime = DateUtil.stringToTimestamp(startTimeStr);
        }
        if (StringUtil.isNotNull(endTimeStr)) {
            endTime = DateUtil.stringToTimestamp(endTimeStr);
        }
        if (StringUtil.isNotNull(accountStatusStr)) {
            accountStatus = Integer.parseInt(accountStatusStr);
        }
        if (StringUtil.isNotNull(authenticationStatusStr)) {
            authenticationStatus = Integer.parseInt(authenticationStatusStr);
        }

        List<UserDO> userList = userService.listUserForBacker(userAccount, phoneAreaCode, phoneNumber, accountStatus,
                authenticationStatus, startTime, endTime, 0, 1000000);
        if(userList == null || userList.size() <= 0){
            responseJson.setCode(3);
            responseJson.setMessage("未查询到数据");
            return responseJson;
        }

        String path = request.getServletContext().getRealPath("/upload") + "/excel/";
        XSSFWorkbook workBook = null;
        OutputStream stream = null;
        try {
            workBook = new XSSFWorkbook();
            XSSFSheet sheet1 = workBook.createSheet("用户账号");

            //创建表头
            XSSFRow row = sheet1.createRow(0);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("注册时间");
            cell = row.createCell(1);
            cell.setCellValue("用户账号");
            cell = row.createCell(2);
            cell.setCellValue("手机号");
            cell = row.createCell(3);
            cell.setCellValue("账号可用余额（美元$）");
            cell = row.createCell(4);
            cell.setCellValue("冻结金额（美元$）");
            cell = row.createCell(5);
            cell.setCellValue("账号总金额（美元$）");
            cell = row.createCell(6);
            cell.setCellValue("审核状态");
            cell = row.createCell(7);
            cell.setCellValue("账号状态");
            //设置列宽
            sheet1.setColumnWidth(0, 5000);
            sheet1.setColumnWidth(1, 5000);
            sheet1.setColumnWidth(2, 6000);
            sheet1.setColumnWidth(3, 6000);
            sheet1.setColumnWidth(4, 6000);
            sheet1.setColumnWidth(5, 6000);
            sheet1.setColumnWidth(6, 3000);
            sheet1.setColumnWidth(7, 3000);

            int rowNumber = 1;
            for (UserDO user : userList) {
                row = sheet1.createRow(rowNumber);

                cell = row.createCell(0);
                cell.setCellValue(DateUtil.longToTimeStr(user.getAddTime().getTime(), DateUtil.dateFormat2));
                cell = row.createCell(1);
                cell.setCellValue(user.getUserAccount());
                cell = row.createCell(2);
                cell.setCellValue(user.getPhoneAreaCode() + " " + user.getPhoneNumber());
                cell = row.createCell(3);
                cell.setCellValue(user.getUserBalance());
                cell = row.createCell(4);
                cell.setCellValue(user.getUserBalanceLock());
                cell = row.createCell(5);
                double countBalance = BigDecimalUtil.add(user.getUserBalance(), user.getUserBalanceLock());
                cell.setCellValue(countBalance);
                cell = row.createCell(6);
                if (user.getAuthenticationStatus() == 1) {
                    cell.setCellValue("待审核");
                }
                if (user.getAuthenticationStatus() == 2) {
                    cell.setCellValue("审核通过");
                }
                if (user.getAuthenticationStatus() == 3) {
                    cell.setCellValue("审核拒绝");
                }
                if (user.getAuthenticationStatus() == 4) {
                    cell.setCellValue("未提交");
                }

                cell = row.createCell(7);
                if (user.getAccountStatus() == 1) {
                    cell.setCellValue("启用");
                }
                if (user.getAccountStatus() == 2) {
                    cell.setCellValue("禁用");
                }

                rowNumber++;
            }

            StringBuffer url = new StringBuffer();
            url.append(path);
            url.append("userAccount_");
            String dateStr = DateUtil.longToTimeStr(DateUtil.getCurrentTimeMillis(), DateUtil.dateFormat6);
            url.append(dateStr);
            url.append(".xlsx");

            stream = new FileOutputStream(url.toString());
            workBook.write(stream);

            responseJson.setCode(1);
            responseJson.setMessage("/upload/excel/userAccount_" + dateStr + ".xlsx");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);

            responseJson.setCode(5);
            responseJson.setMessage("导出失败，请重试");
            return responseJson;
        }finally{
            try {
                workBook.close();
                stream.close();
            } catch (IOException e) {
                LogUtil.printErrorLog(e);
            }
        }

        return responseJson;
    }

    /** 增加账户锁定余额 */
    @RequestMapping(value = "/addLockAmount.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO addLockAmount(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            responseJson.setCode(4);
            responseJson.setMessage("登录过期，请重新登录");
            return responseJson;
        }
        boolean handleFrequent = BackerWebInterceptor.handleFrequent(request);
        if (handleFrequent) {
            responseJson.setCode(6);
            responseJson.setMessage("您的操作太频繁");
            return responseJson;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141108);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String balanceNumberStr = StringUtil.stringNullHandle(request.getParameter("balanceNumber"));
        String remark = StringUtil.stringNullHandle(request.getParameter("remark"));
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)
                || !StringUtil.isNotNull(balanceNumberStr)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        double balanceNumber = Double.parseDouble(balanceNumberStr);
        String ipAddress = IpAddressUtil.getIpAddress(request);

        BackerDO backer = backerService.getBackerById(backerSession.getBackerId());
        if (backer == null || backer.getAccountStatus() != 1) {
            responseJson.setCode(3);
            responseJson.setMessage("操作非法");
            return responseJson;
        }

        boolean updateResult = userService.addBalanceNumberLockForBack(userId, userAccount, balanceNumber,
                backer.getBackerId(), backer.getBackerAccount(), remark, ipAddress);
        if (updateResult) {
            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
            return responseJson;
        }

        responseJson.setCode(5);
        responseJson.setMessage("操作失败");
        return responseJson;
    }

    /** 减少账户锁定余额 */
    @RequestMapping(value = "/reduceLockAmount.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO reduceLockAmount(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            responseJson.setCode(4);
            responseJson.setMessage("登录过期，请重新登录");
            return responseJson;
        }
        boolean handleFrequent = BackerWebInterceptor.handleFrequent(request);
        if (handleFrequent) {
            responseJson.setCode(6);
            responseJson.setMessage("您的操作太频繁");
            return responseJson;
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141109);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String balanceNumberStr = StringUtil.stringNullHandle(request.getParameter("balanceNumber"));
        String remark = StringUtil.stringNullHandle(request.getParameter("remark"));
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)
                || !StringUtil.isNotNull(balanceNumberStr)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        double balanceNumber = Double.parseDouble(balanceNumberStr);
        String ipAddress = IpAddressUtil.getIpAddress(request);

        BackerDO backer = backerService.getBackerById(backerSession.getBackerId());
        if (backer == null || backer.getAccountStatus() != 1) {
            responseJson.setCode(3);
            responseJson.setMessage("操作非法，您的账号不可用");
            return responseJson;
        }

        boolean updateResult = userService.reduceBalanceNumberLockForBack(userId, userAccount, balanceNumber,
                backerSession.getBackerId(), backerSession.getBackerAccount(), remark, ipAddress);
        if (updateResult) {
            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
            return responseJson;
        }

        responseJson.setCode(5);
        responseJson.setMessage("操作失败");
        return responseJson;
    }

}
