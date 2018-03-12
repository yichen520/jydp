package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.IpAddressUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.back.BackerDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.BackerUserCurrencyNumDTO;
import com.jydp.entity.DTO.TransactionCurrencyBasicDTO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 用户账户币种余额
 * Author: hht
 * Date: 2018-03-08 20:05
 */
@Controller
@RequestMapping("/backerWeb/backerUserAccountDetail")
@Scope(value="prototype")
public class BackerUserAccountDetailController {

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

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 展示账户明细页面 */
    @RequestMapping(value = "/showDetail.htm", method = RequestMethod.POST)
    public String showDetail(HttpServletRequest request, RedirectAttributes attributes) {
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
            attributes.addFlashAttribute("code", 2);
            attributes.addFlashAttribute("message", "参数为空");
            return "redirect:/backer/backerUserAccount/show.htm";
        }

        int userId = Integer.parseInt(userIdStr);
        UserDO userDO = userService.getUserByUserId(userId);
        if (userDO == null || userDO.getAccountStatus() <= 0) {
            attributes.addFlashAttribute("code", 3);
            attributes.addFlashAttribute("message", "用户不存在");
            return "redirect:/backer/backerUserAccount/show.htm";
        }
        //查询用户币数量，带货币名称
        List<BackerUserCurrencyNumDTO> userCurrencyNumList = userCurrencyNumService.getUserCurrencyNumByUserIdForBacker(userId);

        List<TransactionCurrencyBasicDTO> transactionCurrencyBasicList = transactionCurrencyService.listAllTransactionCurrencyBasicInfor();


        request.setAttribute("userCurrencyNumList", userCurrencyNumList);
        request.setAttribute("userId", userIdStr);
        request.setAttribute("userAccount", userDO.getUserAccount());
        return "page/back/userAccountDetail";
    }

    /** 增加用户货币数量 */
    @RequestMapping(value = "/addCurrencyNumber.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO addCurrencyNumber(HttpServletRequest request) {
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
        boolean havePower = BackerWebInterceptor.validatePower(request, 141110);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String currencyNumberStr = StringUtil.stringNullHandle(request.getParameter("currencyNumber"));
        String remark = StringUtil.stringNullHandle(request.getParameter("remark"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));  //币种Id
        String currencyName = StringUtil.stringNullHandle(request.getParameter("currencyName"));  //货币名称
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)
                || !StringUtil.isNotNull(currencyNumberStr)|| !StringUtil.isNotNull(currencyIdStr)
                || !StringUtil.isNotNull(currencyName)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        int currencyId = Integer.parseInt(currencyIdStr);
        double currencyNumber = Double.parseDouble(currencyNumberStr);
        String ipAddress = IpAddressUtil.getIpAddress(request);

        BackerDO backer = backerService.getBackerById(backerSession.getBackerId());
        if (backer == null || backer.getAccountStatus() != 1) {
            responseJson.setCode(3);
            responseJson.setMessage("操作非法，您的账号不可用");
            return responseJson;
        }

        boolean updateResult = userService.addUserCurrencyNumberForBack(userId, userAccount, currencyId, currencyName,
                currencyNumber, backer.getBackerId(), backer.getBackerAccount(), remark, ipAddress);
        if (updateResult) {
            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
            return responseJson;
        }

        responseJson.setCode(5);
        responseJson.setMessage("操作失败");
        return responseJson;
    }

    /** 减少用户货币数量 */
    @RequestMapping(value = "/reduceCurrencyNumber.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO reduceCurrencyNumber(HttpServletRequest request) {
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
        boolean havePower = BackerWebInterceptor.validatePower(request, 141111);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String currencyNumberStr = StringUtil.stringNullHandle(request.getParameter("currencyNumber"));
        String remark = StringUtil.stringNullHandle(request.getParameter("remark"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));  //币种Id
        String currencyName = StringUtil.stringNullHandle(request.getParameter("currencyName"));  //货币名称
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)
                || !StringUtil.isNotNull(currencyNumberStr)|| !StringUtil.isNotNull(currencyIdStr)
                || !StringUtil.isNotNull(currencyName)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        int currencyId = Integer.parseInt(currencyIdStr);
        double currencyNumber = Double.parseDouble(currencyNumberStr);
        String ipAddress = IpAddressUtil.getIpAddress(request);

        BackerDO backer = backerService.getBackerById(backerSession.getBackerId());
        if (backer == null || backer.getAccountStatus() != 1) {
            responseJson.setCode(3);
            responseJson.setMessage("操作非法，您的账号不可用");
            return responseJson;
        }

        boolean updateResult = userService.reduceUserCurrencyNumberForBack(userId, userAccount, currencyId, currencyName,
                currencyNumber, backer.getBackerId(), backer.getBackerAccount(), remark, ipAddress);
        if (updateResult) {
            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
            return responseJson;
        }

        responseJson.setCode(5);
        responseJson.setMessage("操作失败");
        return responseJson;
    }

    /** 增加用户货币冻结数量 */
    @RequestMapping(value = "/addLockCurrencyNumber.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO addCurrencyNumberLock(HttpServletRequest request) {
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
        boolean havePower = BackerWebInterceptor.validatePower(request, 141112);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String currencyNumberStr = StringUtil.stringNullHandle(request.getParameter("currencyNumber"));
        String remark = StringUtil.stringNullHandle(request.getParameter("remark"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));  //币种Id
        String currencyName = StringUtil.stringNullHandle(request.getParameter("currencyName"));  //货币名称
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)
                || !StringUtil.isNotNull(currencyNumberStr)|| !StringUtil.isNotNull(currencyIdStr)
                || !StringUtil.isNotNull(currencyName)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        int currencyId = Integer.parseInt(currencyIdStr);
        double currencyNumber = Double.parseDouble(currencyNumberStr);
        String ipAddress = IpAddressUtil.getIpAddress(request);

        BackerDO backer = backerService.getBackerById(backerSession.getBackerId());
        if (backer == null || backer.getAccountStatus() != 1) {
            responseJson.setCode(3);
            responseJson.setMessage("操作非法，您的账号不可用");
            return responseJson;
        }

        boolean updateResult = userService.addUserCurrencyNumberLockForBack(userId, userAccount, currencyId, currencyName,
                currencyNumber, backer.getBackerId(), backer.getBackerAccount(), remark, ipAddress);
        if (updateResult) {
            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
            return responseJson;
        }

        responseJson.setCode(5);
        responseJson.setMessage("操作失败");
        return responseJson;
    }

    /** 减少用户货币冻结数量 */
    @RequestMapping(value = "/reduceLockCurrencyNumber.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO reduceCurrencyNumberLock(HttpServletRequest request) {
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
        boolean havePower = BackerWebInterceptor.validatePower(request, 141113);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String currencyNumberStr = StringUtil.stringNullHandle(request.getParameter("currencyNumber"));
        String remark = StringUtil.stringNullHandle(request.getParameter("remark"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));  //币种Id
        String currencyName = StringUtil.stringNullHandle(request.getParameter("currencyName"));  //货币名称
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)
                || !StringUtil.isNotNull(currencyNumberStr)|| !StringUtil.isNotNull(currencyIdStr)
                || !StringUtil.isNotNull(currencyName)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        int currencyId = Integer.parseInt(currencyIdStr);
        double currencyNumber = Double.parseDouble(currencyNumberStr);
        String ipAddress = IpAddressUtil.getIpAddress(request);

        BackerDO backer = backerService.getBackerById(backerSession.getBackerId());
        if (backer == null || backer.getAccountStatus() != 1) {
            responseJson.setCode(3);
            responseJson.setMessage("操作非法，您的账号不可用");
            return responseJson;
        }

        boolean updateResult = userService.reduceUserCurrencyNumberLockForBack(userId, userAccount, currencyId, currencyName,
                currencyNumber, backer.getBackerId(), backer.getBackerAccount(), remark, ipAddress);
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
