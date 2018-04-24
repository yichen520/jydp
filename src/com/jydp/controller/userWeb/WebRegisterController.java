package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.Base64Util;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserRegisterDO;
import com.jydp.service.ISystemValidatePhoneService;
import com.jydp.service.IUserService;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

/**
 *
 * web2.0用户注册
 * @author cyfIverson
 * @create 2018-04-18
 */
@RestController
@RequestMapping("/web/userRegister")
@Scope(value = "prototype")
public class WebRegisterController{

    /** 用户账户 */
    @Autowired
    private IUserService userService;

    /** 系统手机验证码 */
    @Autowired
    private ISystemValidatePhoneService systemValidatePhoneService;

    /**
     * 用户注册
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public JsonObjectBO register(@RequestBody UserRegisterDO userRegisterDO) {
        JsonObjectBO responseJson = new JsonObjectBO();
        JSONObject jsonObject = new JSONObject();

        if (userRegisterDO == null){
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }

        String userAccount = StringUtil.stringNullHandle(userRegisterDO.getUserAccount());
        String password = StringUtil.stringNullHandle(userRegisterDO.getPassword());
        password = Base64Util.decode(password);
        String payPassword = StringUtil.stringNullHandle(userRegisterDO.getPayPassword());
        payPassword = Base64Util.decode(payPassword);
        String validateCode = StringUtil.stringNullHandle(userRegisterDO.getValidateCode());
        String phoneAreaCode = StringUtil.stringNullHandle(userRegisterDO.getPhoneAreaCode());
        String phoneNumber = StringUtil.stringNullHandle(userRegisterDO.getPhoneNumber());

        if (!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password) || !StringUtil.isNotNull(phoneAreaCode) ||
                !StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(phoneNumber) || !StringUtil.isNotNull(payPassword)) {
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }

        UserDO validateUserDO = userService.getUserByUserAccount(userAccount);
        if (validateUserDO != null) {
            responseJson.setCode(SystemMessageConfig.ACCOUNT_REPEAT_CODE);
            responseJson.setMessage(SystemMessageConfig.ACCOUNT_REPEAT_MESSAGE);
            return responseJson;
        }

        UserDO user = userService.getUserByPhone(phoneNumber);
        if (user != null) {
            responseJson.setCode(SystemMessageConfig.PHONE_REGISTERED_CODE);
            responseJson.setMessage(SystemMessageConfig.PHONE_REGISTERED_MESSAGE);
            return responseJson;
        }

        //校验用户注册信息合法性
        responseJson = userService.validateUserInfo(userAccount,password,payPassword);
        if (responseJson.getCode() != 1) {
            return responseJson;
        }

        responseJson = systemValidatePhoneService.validatePhone(phoneAreaCode+phoneNumber, validateCode);
        if (responseJson.getCode() != 1) {
            return responseJson;
        }

        UserDO userDO = new UserDO();
        userDO.setUserAccount(userAccount);
        userDO.setPassword(MD5Util.toMd5(password));
        userDO.setPhoneAreaCode(phoneAreaCode);
        userDO.setPhoneNumber(phoneNumber);
        userDO.setPayPassword(MD5Util.toMd5(payPassword));

        userDO.setPayPasswordStatus(1);
        userDO.setAccountStatus(2);
        userDO.setPayPasswordStatus(1);
        userDO.setAddTime(DateUtil.getCurrentTime());
        userDO.setAuthenticationStatus(4);

        userDO = userService.register(userDO);

        if (userDO != null) {
            jsonObject.put("userId",userDO.getUserId());
            jsonObject.put("userAccount",userDO.getUserAccount());
            responseJson.setData(jsonObject);
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);//1
            responseJson.setMessage(SystemMessageConfig.REGISTER_SUCCESS_MESSAGE);
        } else {
            responseJson.setCode(SystemMessageConfig.REGISTER_FAIL_CODE);
            responseJson.setMessage(SystemMessageConfig.REGISTER_FAIL_MESSAGE);
        }
        return responseJson;
    }

    /** 校验用户名 */
    @RequestMapping(value = "/validateAccount",method = RequestMethod.POST)
    public JsonObjectBO validateAccount(@RequestBody String requestJson){
        JsonObjectBO responseJson = new JsonObjectBO();

        JSONObject requestJsonObject = (JSONObject) JSONObject.parse(requestJson);
        String userAccount = (String) requestJsonObject.get("userAccount");

        if (!StringUtil.isNotNull(userAccount)) {
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }

        UserDO userDO = userService.getUserByUserAccount(userAccount);
        if (userDO == null) {
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);//1
            responseJson.setMessage(SystemMessageConfig.ACCOUNT_AVAILABLE_MESSAGE);
            return responseJson;
        }

        responseJson.setCode(SystemMessageConfig.ACCOUNT_REPEAT_CODE);
        responseJson.setMessage(SystemMessageConfig.ACCOUNT_REPEAT_MESSAGE);
        return responseJson;
    }
}
