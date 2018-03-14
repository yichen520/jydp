package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserFeedbackDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IUserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 联系客服
 * @Author: wqq
 */
@Controller
@RequestMapping("/userWeb/webCustomerService")
@Scope(value = "prototype")
public class WebCustomerServiceController {

    /**  意见反馈 */
    @Autowired
    private IUserFeedbackService userFeedbackService;

    /**  查询意见反馈列表 */
    public void showList(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = userFeedbackService.countUserFeedbackForUser();
        int pageSize = 20;

        List<UserFeedbackDO> userFeedbackList = null;
        if (totalNumber > 0) {
            userFeedbackList = userFeedbackService.listUserFeedbackForUser(pageNumber, pageSize);
        }

        if (userFeedbackList != null && userFeedbackList.size() > 0) {
            for (UserFeedbackDO userFeedback : userFeedbackList) {
                String feedbackContent = userFeedback.getFeedbackContent();
                String handleContent = userFeedback.getHandleContent();
                if (StringUtil.isNotNull(handleContent)) {
                    handleContent = HtmlUtils.htmlEscape(handleContent);
                    userFeedback.setFeedbackTitle(handleContent);
                }
                if (StringUtil.isNotNull(feedbackContent)) {
                    feedbackContent = HtmlUtils.htmlEscape(feedbackContent);
                    userFeedback.setFeedbackContent(feedbackContent);
                }
            }
        }

        int totalPageNumber = (int) Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);

        request.setAttribute("userFeedbackList", userFeedbackList);
    }

    /**  意见反馈展示 */
    @RequestMapping("/show.htm")
    public String show(HttpServletRequest request) {
        UserSessionBO userBo = UserWebInterceptor.getUser(request);
        if (userBo == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录！");
            return "page/web/login";
        }

        showList(request);
        request.setAttribute("code", 1);
        request.setAttribute("message","查询成功!");
        return "page/web/customerService";
    }

    /**  意见反馈 */
    @RequestMapping("/feedback.htm")
    public @ResponseBody
    JsonObjectBO feedback(HttpServletRequest request){
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO userBo = UserWebInterceptor.getUser(request);
        if (userBo == null) {
            responseJson.setCode(4);
            responseJson.setMessage("未登录！");
            return responseJson;
        }

        boolean handleFrequent = UserWebInterceptor.handleFrequent(request);
        if (handleFrequent) {
            responseJson.setCode(3);
            responseJson.setMessage("您的操作太频繁！");
            return responseJson;
        }

        String feedbackTitle = StringUtil.stringNullHandle(request.getParameter("feedbackTitle"));
        String feedbackContent = StringUtil.stringNullHandle(request.getParameter("feedbackContent"));

        if (!StringUtil.isNotNull(feedbackTitle) || !StringUtil.isNotNull(feedbackContent)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数不能为空");
            return responseJson;
        }
        if (feedbackTitle.length() < 2 || feedbackTitle.length() > 32 || feedbackContent.length() > 400) {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误！");
            return responseJson;
        }

        boolean userFeedbackBoo = userFeedbackService.insertUserFeedback(userBo.getUserId(), userBo.getUserAccount(), feedbackTitle, feedbackContent);
        if (userFeedbackBoo) {
            responseJson.setCode(1);
            responseJson.setMessage("提交成功！");
        } else {
            responseJson.setCode(5);
            responseJson.setMessage("提交失败！");
        }

        return responseJson;
    }
}
