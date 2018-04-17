package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.VO.UserRechargeCoinRecordVO;
import com.jydp.interceptor.UserWapInterceptor;
import com.jydp.service.ISylToJydpChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description: 用户充币记录
 * Author: hht
 * Date: 2018-04-16 11:16
 */
@Controller
@RequestMapping("/userWap/wapRechargeCoinRecord")
@Scope(value = "prototype")
public class WapRechargeCoinRecordController {

    /**
     * SYL转账盛源链记录(SYL-->JYDP)
     */
    @Autowired
    private ISylToJydpChainService sylToJydpChainService;

    /**
     * 进入用户充币记录页面
     */
    @RequestMapping("/show.htm")
    public String show(HttpServletRequest request) {
        UserSessionBO user = UserWapInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录！");
            return "page/wap/login";
        }
        return "page/wap/rechargeCoinRecord";
    }

    /**
     * 返回用户充币记录数据
     */
    @RequestMapping(value = "/getRechargeCoinRecordList", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject getRechargeCoinRecordList(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        UserSessionBO userSession = UserWapInterceptor.getUser(request);
        if (userSession == null) {
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

        int totalNumber = sylToJydpChainService.countUserRechargeCoinRecordForUser(userSession.getUserId());

        int pageSize = 10;
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<UserRechargeCoinRecordVO> userRechargeCoinRecordList = null;
        if (totalNumber > 0) {
            userRechargeCoinRecordList = sylToJydpChainService.listUserRechargeCoinRecordForUser(userSession.getUserId(), pageNumber, pageSize);
        }

        response.put("pageNumber", pageNumber);
        response.put("totalPageNumber", totalPageNumber);
        response.put("userRechargeCoinRecordList", userRechargeCoinRecordList);
        response.put("code", 1);
        response.put("message", "获取成功");
        return response;
    }

}
