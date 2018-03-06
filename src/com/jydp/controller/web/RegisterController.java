package com.jydp.controller.web;

import com.iqmkj.config.SystemHelpConfig;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.system.SystemHelpDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.service.ISystemHelpService;
import com.jydp.service.ISystemValidatePhoneService;
import com.jydp.service.IUserService;
import config.PhoneAreaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * web端用户注册
 * @author yk
 */
@Controller
@RequestMapping("/userWeb/userRegister")
@Scope(value = "prototype")
public class RegisterController {

    /** 用户账户 */
    @Autowired
    private IUserService userService;

    /** 系统手机验证码 */
    @Autowired
    private ISystemValidatePhoneService systemValidatePhoneService;

    /**  帮助中心 */
    @Autowired
    private ISystemHelpService systemHelpService;

    /**
     * 跳转至注册页面
     */
    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request) {
        Map<String, String> phoneAreaMap = PhoneAreaConfig.phoneAreaMap;
        request.setAttribute("phoneAreaMap", phoneAreaMap);
        return "page/web/register";
    }


    /**
     * 跳转至注册协议页面
     */
    @RequestMapping(value = "/registerAgree")
    public String registerAgree(HttpServletRequest request) {
        int helpId = SystemHelpConfig.REGISTER_AGREEMENT;
        SystemHelpDO systemHelpDO = systemHelpService.getSystemHelpById(helpId);

        request.setAttribute("systemHelpDO", systemHelpDO);
        request.setAttribute("helpId", helpId);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功！");
        return "page/web/helpCenter";
    }

    /** 校验用户名 */
    @RequestMapping(value = "/validateAccount",method = RequestMethod.POST)
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
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO register(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String password = StringUtil.stringNullHandle(request.getParameter("password"));
        String validateCode = StringUtil.stringNullHandle(request.getParameter("validateCode"));
        String phoneAreaCode = StringUtil.stringNullHandle(request.getParameter("phoneAreaCode"));
        String phoneNumber = StringUtil.stringNullHandle(request.getParameter("phoneNumber"));
        if (!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password) || !StringUtil.isNotNull(phoneAreaCode) ||
                !StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(phoneNumber)) {
            responseJson.setCode(2);
            responseJson.setMessage("用户注册信息不能为空");
            return responseJson;
        }

        UserDO valiUserDO = userService.getUserByUserAccount(userAccount);
        if (valiUserDO != null) {
            responseJson.setCode(2);
            responseJson.setMessage("用户名重复");
            return responseJson;
        }

        UserDO user = userService.getUserByPhone(phoneNumber);
        if (user != null) {
            responseJson.setCode(2);
            responseJson.setMessage("该手机号已被注册");
            return responseJson;
        }

        //校验用户注册信息合法性
        responseJson = userService.validateUserInfo(userAccount,password);

        if (responseJson.getCode() != 1) {
            return responseJson;
        }

        responseJson = systemValidatePhoneService.validatePhone(phoneNumber, validateCode);
        if (responseJson.getCode() != 1) {
            return responseJson;
        }

        UserDO userDO = new UserDO();
        userDO.setUserAccount(userAccount);
        userDO.setPassword(MD5Util.toMd5(password));
        userDO.setPhoneAreaCode(phoneAreaCode);
        userDO.setPhoneNumber(phoneNumber);
        userDO.setPayPassword(MD5Util.toMd5("123456"));
        userDO.setAccountStatus(2);
        userDO.setAddTime(DateUtil.getCurrentTime());

        boolean result = userService.register(userDO);


        if (result) {
            responseJson.setCode(1);
            responseJson.setMessage("注册成功");
        } else {
            responseJson.setCode(2);
            responseJson.setMessage("注册失败");
        }
        return responseJson;
    }
}
