package com.jydp.controller;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.service.ISystemValidatePhoneService;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 忘记密码
 */
@Controller
@RequestMapping("/web/user")
@Scope(value = "prototype")
public class ForgetController {

    /** 用户账号 **/
    @Autowired
    private IUserService userService;

    /** 系统手机验证码 */
    @Autowired
    private ISystemValidatePhoneService systemValidatePhoneService;

    /** 忘记密码 */
    @RequestMapping(value = "/forgetPassword")
    public @ResponseBody
    JsonObjectBO forgetPassword(@RequestBody String requestJsonString){
        JsonObjectBO responseJson = new JsonObjectBO();

        JSONObject requestJson = null;
        try {
            requestJson = JSONObject.parseObject(requestJsonString);
        } catch (Exception e) {
            responseJson.setCode(2);
            responseJson.setMessage("JSON格式错误");
            return responseJson;
        }

        String userAccount = StringUtil.stringNullHandle(requestJson.getString("userAccount"));
        String password = StringUtil.stringNullHandle(requestJson.getString("password"));
        String validateCode = StringUtil.stringNullHandle(requestJson.getString("validateCode"));
        String userPhone = StringUtil.stringNullHandle(requestJson.getString("userPhone"));
        if (!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password) ||
                !StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(userPhone)) {
            responseJson.setCode(2);
            responseJson.setMessage("提交信息存在为空选项");
            return responseJson;
        }

        JsonObjectBO resultJson = systemValidatePhoneService.validatePhone(userPhone, validateCode);
        if (resultJson.getCode() != 1) {
            responseJson.setCode(2);
            responseJson.setMessage("验证码错误");
            return responseJson;
        }

        UserDO userDO = userService.getUserByUserAccount(userAccount);
        if (userDO == null) {
            responseJson.setCode(2);
            responseJson.setMessage("用户不存在");
            return responseJson;
        }

        if (userDO.getAccountStatus() != 1) {
            responseJson.setCode(2);
            responseJson.setMessage("用户被禁用");
            return responseJson;
        }
        boolean resetResult = userService.forgetPwd(userAccount, password);
        if (resetResult) {
            responseJson.setCode(1);
            responseJson.setMessage("找回密码成功");
        } else {
            responseJson.setCode(2);
            responseJson.setMessage("找回密码失败");
        }
        return responseJson;
    }
}
