package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserFeedbackDO;
import com.jydp.interceptor.UserWapInterceptor;
import com.jydp.service.IUserFeedbackService;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * wap端联系客服
 * @author njx
 **/
@Controller
@RequestMapping("/userWap/wapCustomerService")
@Scope(value = "prototype")
public class WapCustomerServiceController {
    /**  意见反馈 */
    @Autowired
    private IUserFeedbackService userFeedbackService;

    /**
     * 进入反馈页面
     */
    @RequestMapping("/show")
    public String show(HttpServletRequest request){
        UserSessionBO user = UserWapInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录！");
            return "page/wap/login";
        }
        return "page/wap/wapCustomerService";
    }

    /**
     * 返回反馈数据
     */
    @RequestMapping(value = "/getServiceInfo", method = RequestMethod.POST)
    public  @ResponseBody JSONObject getServiceInfo(HttpServletRequest request){
        JSONObject response  = new JSONObject();
        UserSessionBO user = UserWapInterceptor.getUser(request);
        if (user == null) {
            response.put("code", 4);
            response.put("message", "未登录！");
            return response;
        }
        String webAppPath = request.getServletContext().getContextPath();
        response.put("webAppPath", webAppPath);
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }
        int totalNumber = userFeedbackService.countUserFeedbackForUser(user.getUserId());
        int pageSize = 10;
        List<UserFeedbackDO> userFeedbackList = null;
        if (totalNumber > 0) {
            userFeedbackList = userFeedbackService.listUserFeedbackForWapUser(user.getUserId(), pageNumber, pageSize);
        }

        if (userFeedbackList != null && userFeedbackList.size() > 0) {
            for (UserFeedbackDO userFeedback : userFeedbackList) {
                String feedbackContent = userFeedback.getFeedbackContent();
                String handleContent = userFeedback.getHandleContent();
                if (StringUtil.isNotNull(handleContent)) {
                    handleContent = HtmlUtils.htmlEscape(handleContent);
                    userFeedback.setHandleContent(handleContent);
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
        response.put("pageNumber", pageNumber);
        response.put("totalNumber", totalNumber);
        response.put("totalPageNumber", totalPageNumber);
        response.put("userFeedbackList", userFeedbackList);
        response.put("code", 0);
        response.put("message", "获取成功");
        return  response;
    }

    /**  意见反馈 */
    @RequestMapping("/feedback.htm")
    public @ResponseBody JSONObject feedback(HttpServletRequest request){
        JSONObject response = new JSONObject();
        UserSessionBO user = UserWapInterceptor.getUser(request);
        if (user == null) {
            response.put("code", 4);
            response.put("message", "未登录！");
            return response;
        }
        boolean handleFrequent = UserWapInterceptor.handleFrequent(request);
        if (handleFrequent) {
            response.put("code", 3);
            response.put("message", "您的操作太频繁！");
            return response;
        }

        String feedbackTitle = StringUtil.stringNullHandle(request.getParameter("feedbackTitle"));
        String feedbackContent = StringUtil.stringNullHandle(request.getParameter("feedbackContent"));
        if (!StringUtil.isNotNull(feedbackTitle) || !StringUtil.isNotNull(feedbackContent)) {
            response.put("code", 2);
            response.put("message", "参数不能为空！");
            return response;
        }
        if (feedbackTitle.length() < 2 || feedbackTitle.length() > 32 || feedbackContent.length() > 400) {
            response.put("code", 3);
            response.put("message", "参数错误");
            return response;
        }
        String webAppPath = request.getServletContext().getContextPath();
        response.put("webAppPath", webAppPath);

        UserFeedbackDO userFeedbackDO = new UserFeedbackDO();
        userFeedbackDO.setFeedbackTitle(feedbackTitle);
        userFeedbackDO.setFeedbackContent(feedbackContent);
        userFeedbackDO.setUserId(user.getUserId());
        userFeedbackDO.setUserAccount(user.getUserAccount());
        boolean userFeedbackBoo = userFeedbackService.insertUserFeedbackForWap(userFeedbackDO);
        response.put("userFeedback", userFeedbackDO);
        if (userFeedbackBoo) {
                response.put("code", 0);
                response.put("message", "提交成功！");
                return response;
            } else {
                response.put("code", 5);
                response.put("message", "提交失败！");
                return response;
        }
    }
}
