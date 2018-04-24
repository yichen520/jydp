package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.ResponseUtils;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserFeedbackDO;
import com.jydp.interceptor.WebInterceptor;
import com.jydp.service.IUserFeedbackService;
import config.SystemMessageConfig;
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
 * 联系客服
 * @Author: njx
 */
@Controller
@RequestMapping("/web/webCustomerService")
@Scope(value = "prototype")
public class CustomerServiceController {

    /**  意见反馈 */
    @Autowired
    private IUserFeedbackService userFeedbackService;

    /**  查询意见反馈列表 */
    public void showList(HttpServletRequest request, JSONObject jo, int userId) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = userFeedbackService.countUserFeedbackForUser(userId);
        int pageSize = 21;

        List<UserFeedbackDO> userFeedbackList = null;
        if (totalNumber > 0) {
            userFeedbackList = userFeedbackService.listUserFeedbackForUser(userId, pageNumber, pageSize);
        }

        if (userFeedbackList != null && userFeedbackList.size() > 0) {
            for (UserFeedbackDO userFeedback : userFeedbackList) {
                String feedbackTitle = userFeedback.getFeedbackTitle();
                String feedbackContent = userFeedback.getFeedbackContent();
                String handleContent = userFeedback.getHandleContent();
                if (StringUtil.isNotNull(feedbackTitle)) {
                    feedbackTitle = HtmlUtils.htmlEscape(feedbackTitle);
                    userFeedback.setFeedbackTitle(feedbackTitle);
                }
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

        jo.put("pageNumber", pageNumber);
        jo.put("totalNumber", totalNumber);
        jo.put("totalPageNumber", totalPageNumber);
        jo.put("userFeedbackList", userFeedbackList);
    }

    /**  意见反馈展示 */
    @RequestMapping(value = "/show.htm" , method = RequestMethod.GET)
    public @ResponseBody JsonObjectBO show(HttpServletRequest request) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();

        UserSessionBO userBo = WebInterceptor.getUser(request);
        if (userBo == null) {
            ResponseUtils.setResp(SystemMessageConfig.REDIRECT_TO_USERLOGIN_CODE, SystemMessageConfig.REDIRECT_TO_USERLOGIN_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        showList(request, jo, userBo.getUserId());
        ResponseUtils.setResp(SystemMessageConfig.SUCCESS_OPT_CODE, SystemMessageConfig.SUCCESS_OPT_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;
    }

   /*  意见反馈 */
    @RequestMapping(value = "/feedback.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO feedback(HttpServletRequest request){
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();

        UserSessionBO userBo = WebInterceptor.getUser(request);
        if (userBo == null) {
            ResponseUtils.setResp(SystemMessageConfig.NOT_LOGININ_CODE, SystemMessageConfig.NOT_LOGININ_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        boolean handleFrequent = WebInterceptor.handleFrequent(request);
        if (handleFrequent) {
            ResponseUtils.setResp(SystemMessageConfig.OPERATING_FREQUENCY_CODE, SystemMessageConfig.OPERATING_FREQUENCY_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        String feedbackTitle = StringUtil.stringNullHandle(request.getParameter("feedbackTitle"));
        String feedbackContent = StringUtil.stringNullHandle(request.getParameter("feedbackContent"));

        if (!StringUtil.isNotNull(feedbackTitle) || !StringUtil.isNotNull(feedbackContent)) {
            ResponseUtils.setResp(SystemMessageConfig.PARAMETER_NOT_BE_NULL_CODE, SystemMessageConfig.PARAMETER_NOT_BE_NULL_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }
        if (feedbackTitle.length() < 2 || feedbackTitle.length() > 32 || feedbackContent.length() > 400) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }

        boolean userFeedbackBoo = userFeedbackService.insertUserFeedback(userBo.getUserId(), userBo.getUserAccount(), feedbackTitle, feedbackContent);
        if (userFeedbackBoo) {
            ResponseUtils.setResp(SystemMessageConfig.COMMIT_SUCCESS_CODE, SystemMessageConfig.COMMIT_SUCCESS_MESSAGE, null, jsonObjectBO);
        } else {
            ResponseUtils.setResp(SystemMessageConfig.COMMIT_FAILD_CODE, SystemMessageConfig.COMMIT_FAILD_MESSAGE, null, jsonObjectBO);
        }

        return jsonObjectBO;
    }
}
