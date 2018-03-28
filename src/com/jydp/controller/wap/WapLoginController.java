package com.jydp.controller.wap;

import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.entity.DO.user.UserIdentificationImageDO;
import com.jydp.interceptor.UserWapInterceptor;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IUserIdentificationImageService;
import com.jydp.service.IUserIdentificationService;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * wap端用户登录
 * @author yqz
 */
@Controller
@RequestMapping("/userWap/userLogin")
@Scope(value = "prototype")
public class WapLoginController {

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户认证 */
    @Autowired
    private IUserIdentificationService userIdentificationService;

    /** 用户认证详情图 */
    @Autowired
    private IUserIdentificationImageService userIdentificationImageService;

    /**
     * 用户登录
     * @return 登录页面
     */
    @RequestMapping("/show")
    public String userLoginPage(){
        return "page/wap/login";
    }

    /**
     * 用户登录验证
     * @return wap首页
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String userLogin(HttpServletRequest request){
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String password = StringUtil.stringNullHandle(request.getParameter("password"));

        if(!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password)){
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户名或密码为空");
            return "page/wap/login";
        }

        password = MD5Util.toMd5(password);
        UserDO user = userService.validateUserLogin(userAccount, password);
        if (user == null) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "账号或密码错误");
            return "page/wap/login";
        }

        //查询用户最新认证信息
        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationByUserAccountLately(user.getUserAccount());

        //未进行认证
        if (userIdentification == null) {
            request.setAttribute("userId",user.getUserId());
            request.setAttribute("userAccount",user.getUserAccount());
            return "page/wap/identification";
        }
        //认证未通过
        if (userIdentification.getIdentificationStatus() != 2) {
            List<UserIdentificationImageDO> userIdentificationImageList =
                    userIdentificationImageService.listUserIdentificationImageByIdentificationId(userIdentification.getId());
            request.setAttribute("userId",user.getUserId());
            request.setAttribute("userAccount",user.getUserAccount());
            request.setAttribute("identification", userIdentification);
            request.setAttribute("identificationImageList", userIdentificationImageList);
            return "page/wap/identificationAfresh";
        }

        if (user.getAccountStatus() != 1) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户被禁用");
            return "page/wap/login";
        }

        UserSessionBO userSessionBO = new UserSessionBO();
        userSessionBO.setUserId(user.getUserId());
        userSessionBO.setUserAccount(user.getUserAccount());
        userSessionBO.setIsPwd(1);
        UserWapInterceptor.loginSuccess(request, userSessionBO);
        return "redirect:/userWap/homePage/show";
    }


}
