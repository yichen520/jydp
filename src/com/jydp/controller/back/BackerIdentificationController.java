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

        Timestamp startTime = null;
        Timestamp endTime = null;
        int identificationStatus = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(startTimeStr)) {
            startTime = DateUtil.stringToTimestamp(startTimeStr);
        }
        if (StringUtil.isNotNull(endTimeStr)) {
            endTime = DateUtil.stringToTimestamp(endTimeStr);
        }
        if (StringUtil.isNotNull(identificationStatusStr)) {
            identificationStatus = Integer.parseInt(identificationStatusStr);
        }
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = userIdentificationService.countUserIdentificationForBacker(userAccount, userPhone, identificationStatus, startTime, endTime);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber -1;
        }

        List<UserIdentificationDO> userIdentificationList = null;
        if (totalNumber > 0) {
            userIdentificationList = userIdentificationService.listUserIdentificationForBacker(userAccount, userPhone, identificationStatus, startTime, endTime, pageNumber, pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("startTime", startTimeStr);
        request.setAttribute("endTime", endTimeStr);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("userPhone", userPhone);
        request.setAttribute("identificationStatus", identificationStatusStr);

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("userIdentificationList", userIdentificationList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 141000);
    }

    /** 实名认证详情页面 */
    @RequestMapping(value = "/detail.htm", method = RequestMethod.POST)
    public String detail(HttpServletRequest request) {
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
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String startTimeStr = StringUtil.stringNullHandle(request.getParameter("startTime"));
        String endTimeStr = StringUtil.stringNullHandle(request.getParameter("endTime"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String userPhone = StringUtil.stringNullHandle(request.getParameter("userPhone"));
        String identificationStatusStr = StringUtil.stringNullHandle(request.getParameter("identificationStatus"));
        request.setAttribute("pageNumber", pageNumberStr);
        request.setAttribute("startTime", startTimeStr);
        request.setAttribute("endTime", endTimeStr);
        request.setAttribute("userAccount", userAccount);
        request.setAttribute("userPhone", userPhone);
        request.setAttribute("identificationStatus", identificationStatusStr);

        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        if (!StringUtil.isNotNull(idStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            showList(request);
            return "page/back/userIdentification";
        }

        long id = Long.parseLong(idStr);
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

        boolean passResult = userIdentificationService.updateUserIdentificationStatus
                (userIdentification.getId(), 2, DateUtil.getCurrentTime(), remark);
        if (!passResult) {
            responseJson.setCode(5);
            responseJson.setMessage("操作失败");
            return responseJson;
        }

        String messageContent = "您在交易大盘中提交的实名认证信息审核已通过。";
        passResult = SendMessage.send(userIdentification.getUserPhone(), messageContent);
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

        boolean passResult = userIdentificationService.updateUserIdentificationStatus
                (userIdentification.getId(), 3, DateUtil.getCurrentTime(), remark);
        if (!passResult) {
            responseJson.setCode(5);
            responseJson.setMessage("操作失败");
            return responseJson;
        }

        String messageContent = "您在交易大盘中提交的实名认证信息审核被拒绝。";
        passResult = SendMessage.send(userIdentification.getUserPhone(), messageContent);
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
