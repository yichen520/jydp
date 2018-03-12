package com.jydp.controller.web;

import com.google.code.kaptcha.Constants;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.entity.DO.user.UserIdentificationImageDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    /** 用户认证 */
    @Autowired
    private IUserIdentificationService userIdentificationService;

    /** 用户认证详情图 */
    @Autowired
    private IUserIdentificationImageService userIdentificationImageService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

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
        String validateCode = StringUtil.stringNullHandle(request.getParameter("validateCode"));
        String sessionCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        request.setAttribute("userAccount", userAccount);

        if(!StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(sessionCode)){
            request.setAttribute("code", 2);
            request.setAttribute("message", "验证码错误");
            return "page/web/login";
        }

        validateCode = validateCode.toLowerCase();
        sessionCode = sessionCode.toLowerCase();
        if(!validateCode.equals(sessionCode)){
            request.setAttribute("code", 3);
            request.setAttribute("message", "验证码错误");
            return "page/web/login";
        }

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

        //查询用户最新认证信息
        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationByUserAccountLately(user.getUserAccount());

        //未进行认证
        if (userIdentification == null) {
            request.setAttribute("userId",user.getUserId());
            request.setAttribute("userAccount",user.getUserAccount());
            return "page/web/identification";
        }
        //认证未通过
        if (userIdentification.getIdentificationStatus() != 2) {
            List<UserIdentificationImageDO> userIdentificationImageList =
                    userIdentificationImageService.listUserIdentificationImageByIdentificationId(userIdentification.getId());
            request.setAttribute("userId",user.getUserId());
            request.setAttribute("userAccount",user.getUserAccount());
            request.setAttribute("identification", userIdentification);
            request.setAttribute("identificationImageList", userIdentificationImageList);
            return "page/web/identificationAfresh";
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
    @RequestMapping(value = "/loginOut")
    public String loginOut(HttpServletRequest request) {
        UserWebInterceptor.loginOut(request);
        return "page/web/login";
    }
}
