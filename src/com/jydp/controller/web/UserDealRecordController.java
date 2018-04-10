package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionUserDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户成交记录
 * @Author: sy
 */
@Controller
@RequestMapping("/userWeb/userDealRecord")
@Scope(value = "prototype")
public class UserDealRecordController {

    /**  场外交易成交记录 */
    @Autowired
    private IOtcTransactionUserDealService otcTransactionUserDealService;

    /**  场外交易成交记录页面展示 */
    @RequestMapping("show.htm")
    public String show(HttpServletRequest request) {
        UserSessionBO userBo = UserWebInterceptor.getUser(request);
        if (userBo == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录！");
            return "page/web/login";
        }
        return "page/web/login";
    }

    /**  用户确认（出售 回购（收款，收货）） */
    @RequestMapping(value = "userConfirm.htm")
    public @ResponseBody JsonObjectBO userConfirm(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO userBo = UserWebInterceptor.getUser(request);
        if (userBo == null) {
            response.setCode(4);
            response.setMessage("未登录");
            return response;
        }

        String otcOrderNo = StringUtil.stringNullHandle(request.getParameter("otcOrderNo"));
        if (!StringUtil.isNotNull(otcOrderNo)){
            response.setCode(2);
            response.setMessage("参数错误");
            return response;
        }


        return response;
    }
}
