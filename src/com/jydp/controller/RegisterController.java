package com.jydp.controller;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.service.ISystemValidatePhoneService;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * web端用户注册
 */
public class RegisterController {

    /** 用户账户 */
    @Autowired
    private IUserService userService;

    /** 系统手机验证码 */
    @Autowired
    private ISystemValidatePhoneService systemValidatePhoneService;

    /** 校验用户名 */
    @RequestMapping(value = "/validateAccount")
    public @ResponseBody JsonObjectBO validateAccount(HttpServletRequest request){
        JsonObjectBO responseJson = new JsonObjectBO();

        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        if (!StringUtil.isNotNull(userAccount)) {
            responseJson.setCode(2);
            responseJson.setMessage("用户名不能为空");
            return responseJson;
        }

        UserDO userDO = userService.getUserByUserAccount(userAccount);
        if (userDO == null) {
            responseJson.setCode(1);
            responseJson.setMessage("用户名可用");
            return responseJson;
        }

        responseJson.setCode(2);
        responseJson.setMessage("用户名重复");
        return responseJson;
    }

    /** 用户注册 */
    @RequestMapping(value = "/register")
    public @ResponseBody JsonObjectBO login(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String password = StringUtil.stringNullHandle(request.getParameter("password"));
        String validateCode = StringUtil.stringNullHandle(request.getParameter("validateCode"));
        String userPhone = StringUtil.stringNullHandle(request.getParameter("userPhone"));
        String refereeAccount = StringUtil.stringNullHandle(request.getParameter("refereeAccount"));
        if (!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password) ||
                !StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(userPhone)) {
            responseJson.setCode(2);
            responseJson.setMessage("用户注册信息不能为空");
            return responseJson;
        }

        UserDO user = userService.getUserByPhone(userPhone);
        if (user != null) {
            responseJson.setCode(2);
            responseJson.setMessage("该手机号已被注册");
            return responseJson;
        }

        JsonObjectBO resultJson = systemValidatePhoneService.validatePhone(userPhone, validateCode);
        if (resultJson.getCode() != 1) {
            responseJson.setCode(2);
            responseJson.setMessage("验证码错误");
            return responseJson;
        }

        UserDO valiUserDO = userService.getUserByUserAccount(userAccount);
        if (valiUserDO != null) {
            responseJson.setCode(102002);
            responseJson.setMessage("用户名重复");
            return responseJson;
        }

        //校验用户注册信息合法性
        JsonObjectBO jsonObjectBO = userService.validateUserInfo(userAccount,password,userPhone,refereeAccount);

        if (jsonObjectBO.getCode() != 1) {
            return jsonObjectBO;
        }
        UserDO userDO = new UserDO();
        userDO.setUserAccount(userAccount);
        return jsonObjectBO;
    }

}
