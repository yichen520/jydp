package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.user.UserFeedbackDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IUserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 意见反馈
 * @author zym
 *
 */
@Controller
@RequestMapping("/backerWeb/backerFeedback")
@Scope(value="prototype")
public class BackerFeedbackController {

    /** 意见反馈 */
    @Autowired
    private IUserFeedbackService userFeedbackService;

    /** 首页展示 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 116001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        showList(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/feedback";
    }

    /**意见反馈列表*/
    private void showList(HttpServletRequest request){
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String feedbackTitle = StringUtil.stringNullHandle(request.getParameter("feedbackTitle"));
        String handleStatusStr = StringUtil.stringNullHandle(request.getParameter("handleStatus"));
        String startTimeStr = StringUtil.stringNullHandle(request.getParameter("startTime"));
        String endTimeStr = StringUtil.stringNullHandle(request.getParameter("endTime"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        int handleStatus = 0;
        if (StringUtil.isNotNull(handleStatusStr)) {
            handleStatus = Integer.parseInt(handleStatusStr);
        }
        Timestamp startTime = null;
        if (StringUtil.isNotNull(startTimeStr)) {
            startTime = Timestamp.valueOf(startTimeStr);
        }
        Timestamp endTime = null;
        if (StringUtil.isNotNull(endTimeStr)) {
            endTime = Timestamp.valueOf(endTimeStr);
        }

        int pageSize = 20;
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        List<UserFeedbackDO> feedbackList = null;
        int totalNumber = userFeedbackService.countUserFeedback(userAccount, feedbackTitle, handleStatus, startTime, endTime);
        if (totalNumber > 0) {
            feedbackList = userFeedbackService.listUserFeedbackByPage(userAccount, feedbackTitle, handleStatus, pageNumber,
                    pageSize, startTime, endTime);
        }

        int totalPageNumber = (int) Math.ceil(totalNumber/(pageSize*1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }

        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);

        request.setAttribute("userAccount", userAccount);
        request.setAttribute("feedbackTitle", feedbackTitle);
        request.setAttribute("handleStatus", handleStatus);
        request.setAttribute("startTime", startTimeStr);
        request.setAttribute("endTime", endTimeStr);

        request.setAttribute("feedbackList", feedbackList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 116000);
    }

    /** 回复 */
    @RequestMapping(value = "/reply.htm")
    public String reply(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 116002);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("replyId"));
        String handleStatusStr = StringUtil.stringNullHandle(request.getParameter("replyHandleStatus"));
        String handleContent = StringUtil.stringNullHandle(request.getParameter("replyHandleContent"));

        if (!StringUtil.isNotNull(idStr) || !StringUtil.isNotNull(handleStatusStr)) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "未接受到参数");
            return "page/back/feedback";
        }
        long id = Long.parseLong(idStr);
        int handleStatus = Integer.parseInt(handleStatusStr);

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }

        boolean updateResult = userFeedbackService.updateUserFeedbackById(id, handleStatus,
                handleContent, backerSession.getBackerAccount(), DateUtil.getCurrentTime());
        if (updateResult) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "回复成功！");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "回复失败！");
        }

        showList(request);
        return "page/back/feedback";
    }
}
