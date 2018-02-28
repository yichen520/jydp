package com.jydp.controller.web;

import com.google.code.kaptcha.Constants;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.back.BackerDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * web端用户登录
 * @author yk
 */
@Controller
@RequestMapping("/userWeb/userLogin")
@Scope(value = "prototype")
public class LoginController {

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /**  跳转至登录页面 */
    @RequestMapping(value = "/show")
    public String show() {
        return "page/web/login";
    }

    /** 登录 */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String password = StringUtil.stringNullHandle(request.getParameter("password"));

        if(!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password)){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/web/login";
        }

        password = MD5Util.toMd5(password);
        UserDO user = userService.validateUserLogin(userAccount, password);
        if (user == null) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "账号或密码错误");
            return "page/web/login";
        }

        if (user.getAccountStatus() != 1) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户被禁用");
            return "page/web/login";
        }

        UserSessionBO userSessionBO = new UserSessionBO();
        userSessionBO.setUserId(user.getUserId());
        userSessionBO.setUserAccount(user.getUserAccount());
        UserWebInterceptor.loginSuccess(request, userSessionBO);
        return "redirect:/userWeb/homePage/show";
    }

    /** 退出登录 */
    @RequestMapping(value = "/loginOut.htm")
    public String loginOut(HttpServletRequest request) {
        UserSessionBO userSession =(UserSessionBO)request.getSession().getAttribute("userSession");
        if (userSession == null) {
            request.getSession().invalidate();
            return "page/web/login";
        }

        UserWebInterceptor.loginOut(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "退出登录成功");
        return "page/web/login";
    }
}
