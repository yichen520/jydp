package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.entity.DO.user.UserIdentificationImageDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.other.SendMessage;
import com.jydp.service.IUserIdentificationImageService;
import com.jydp.service.IUserIdentificationService;
import config.PhoneAreaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Description: 用户实名认证
 * Author: hht
 * Date: 2018-02-08 9:50
 */
@Controller
@RequestMapping("/backerWeb/backerIdentification")
@Scope(value = "prototype")
public class BackerIdentificationController {

    /** 用户认证 */
    @Autowired
    private IUserIdentificationService userIdentificationService;

    /** 用户认证详情图 */
    @Autowired
    private IUserIdentificationImageService userIdentificationImageService;

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
        boolean havePower = BackerWebInterceptor.validatePower(request, 141001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        showList(request);
        return "page/back/userIdentification";
    }

    /** 获取列表数据 */
    private void showList(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String startTimeStr = StringUtil.stringNullHandle(request.getParameter("startTime"));
        String endTimeStr = StringUtil.stringNullHandle(request.getParameter("endTime"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String userPhone = StringUtil.stringNullHandle(request.getParameter("userPhone"));
        String identificationStatusStr = StringUtil.stringNullHandle(request.getParameter("identificationStatus"));
        String userCertTypeStr = StringUtil.stringNullHandle(request.getParameter("userCertType"));
        String phoneAreaCode = StringUtil.stringNullHandle(request.getParameter("phoneAreaCode"));

        Timestamp startTime = null;
        Timestamp endTime = null;
        int userCertType = 0;
        int identificationStatus = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(startTimeStr)) {
            startTime = DateUtil.stringToTimestamp(startTimeStr);
        }
        if (StringUtil.isNotNull(endTimeStr)) {
            endTime = DateUtil.stringToTimestamp(endTimeStr);
        }
        if (StringUtil.isNotNull(userCertTypeStr)) {
            userCertType = Integer.parseInt(userCertTypeStr);
        }
        if (StringUtil.isNotNull(identificationStatusStr)) {
            identificationStatus = Integer.parseInt(identificationStatusStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = userIdentificationService.countUserIdentificationForBacker(userAccount, phoneAreaCode, userPhone,
                userCertType, identificationStatus, startTime, endTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber -1;
        }

        List<UserIdentificationDO> userIdentificationList = null;
        if (totalNumber > 0) {
            userIdentificationList = userIdentificationService.listUserIdentificationForBacker(userAccount,
                    phoneAreaCode, userPhone, userCertType, identificationStatus, startTime, endTime, pageNumber, pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startTime", startTimeStr);
        request.setAttribute("endTime", endTimeStr);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("userPhone", userPhone);
        request.setAttribute("identificationStatus", identificationStatusStr);
        request.setAttribute("userCertType", userCertTypeStr);
        request.setAttribute("phoneAreaCode", phoneAreaCode);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("userIdentificationList", userIdentificationList);
        Map<String, String> phoneAreaMap = PhoneAreaConfig.phoneAreaMap;
        request.setAttribute("phoneAreaMap", phoneAreaMap);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 141000);
    }

    /** 实名认证详情页面 */
    @RequestMapping(value = "/detail.htm/{idStr}", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, @PathVariable String idStr) {
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 141002);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        //查询条件 回显
//        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
//        String startTimeStr = StringUtil.stringNullHandle(request.getParameter("startTime"));
//        String endTimeStr = StringUtil.stringNullHandle(request.getParameter("endTime"));
//        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
//        String userPhone = StringUtil.stringNullHandle(request.getParameter("userPhone"));
//        String phoneAreaCode = StringUtil.stringNullHandle(request.getParameter("phoneAreaCode"));
//        String identificationStatusStr = StringUtil.stringNullHandle(request.getParameter("identificationStatus"));
//        String userCertTypeStr = StringUtil.stringNullHandle(request.getParameter("userCertType"));
//        request.setAttribute("pageNumber", pageNumberStr);
//        request.setAttribute("startTime", startTimeStr);
//        request.setAttribute("endTime", endTimeStr);
//        request.setAttribute("userAccount", userAccount);
//        request.setAttribute("userPhone", userPhone);
//        request.setAttribute("phoneAreaCode", phoneAreaCode);
//        request.setAttribute("identificationStatus", identificationStatusStr);
//        request.setAttribute("userCertType", userCertTypeStr);

        //String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        if (!StringUtil.isNotNull(idStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            showList(request);
            return "page/back/userIdentification";
        }
        long id = 0L;
        String reg = "[0-9]*";
        if (idStr.length() < 18 && Pattern.matches(reg,idStr)) {
            id = Long.parseLong(idStr);
        }

        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationById(id);
        if (userIdentification == null) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            showList(request);
            return "page/back/userIdentification";
        }

        List<UserIdentificationImageDO> userIdentificationImageList =
                userIdentificationImageService.listUserIdentificationImageByIdentificationId(userIdentification.getId());

        request.setAttribute("userIdentification", userIdentification);
        request.setAttribute("userIdentificationImageList", userIdentificationImageList);
        return "page/back/userIdentificationDetail";
    }

    /** 审核通过 */
    @RequestMapping(value = "/pass.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO pass(HttpServletRequest request) {
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
        boolean havePower = BackerWebInterceptor.validatePower(request, 141003);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        String remark = StringUtil.stringNullHandle(request.getParameter("remark"));
        if (!StringUtil.isNotNull(idStr)) {
            responseJson.setCode(2);
            responseJson.setMessage("未接收到参数");
            return responseJson;
        }

        long id = Long.parseLong(idStr);
        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationById(id);
        if (userIdentification == null) {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误，该认证信息不存在");
            return responseJson;
        }
        if (userIdentification.getIdentificationStatus() != 1) {
            responseJson.setCode(3);
            responseJson.setMessage("认证信息状态异常，请刷新页面");
            return responseJson;
        }

        boolean passResult = userIdentificationService.
                passUserIdentification(userIdentification.getId(), userIdentification.getUserId(), remark, 1);
        if (!passResult) {
            responseJson.setCode(5);
            responseJson.setMessage("操作失败");
            return responseJson;
        }

        String messageContent = "";
        if (userIdentification.getPhoneAreaCode().equals(PhoneAreaConfig.PHONE_AREA_CHINA)) {
            messageContent = "您在盛源交易所中提交的实名认证信息审核已通过。";
        } else {
            messageContent = "The verification of your identity information " +
                    "submitted by Monetary Union Exchange has passed.";
        }

        passResult = SendMessage.send(userIdentification.getPhoneAreaCode() + userIdentification.getUserPhone(), messageContent);
        if (!passResult) {
            responseJson.setCode(5);
            responseJson.setMessage("短信发送失败，请拨打电话或发送短信通知用户");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("操作成功");
        return responseJson;
    }

    /** 审核拒绝 */
    @RequestMapping(value = "/refuse.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO refuse(HttpServletRequest request) {
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
        boolean havePower = BackerWebInterceptor.validatePower(request, 141004);
        if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return responseJson;
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        String remark = StringUtil.stringNullHandle(request.getParameter("remark"));
        if (!StringUtil.isNotNull(idStr)) {
            responseJson.setCode(2);
            responseJson.setMessage("未接收到参数");
            return responseJson;
        }

        long id = Long.parseLong(idStr);
        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationById(id);
        if (userIdentification == null) {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误，该认证信息不存在");
            return responseJson;
        }
        if (userIdentification.getIdentificationStatus() != 1) {
            responseJson.setCode(3);
            responseJson.setMessage("认证信息状态异常，请刷新页面");
            return responseJson;
        }

        boolean passResult = userIdentificationService.
                passUserIdentification(userIdentification.getId(), userIdentification.getUserId(), remark, 2);
        if (!passResult) {
            responseJson.setCode(5);
            responseJson.setMessage("操作失败");
            return responseJson;
        }

        String messageContent = "";
        if (userIdentification.getPhoneAreaCode().equals(PhoneAreaConfig.PHONE_AREA_CHINA)) {
            messageContent = "您在盛源交易所中提交的实名认证信息审核被拒绝。";
        } else {
            messageContent = "The verification of your identity information " +
                    "submitted by Monetary Union Exchange has unaccepted.";
        }

        passResult = SendMessage.send(userIdentification.getPhoneAreaCode() + userIdentification.getUserPhone(), messageContent);
        if (!passResult) {
            responseJson.setCode(5);
            responseJson.setMessage("短信发送失败，请拨打电话或发送短信通知用户");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("操作成功");
        return responseJson;
    }
}
