package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserRegisterDO;
import com.jydp.service.ISystemValidatePhoneService;
import com.jydp.service.IUserService;
import config.SystemCommonConfig;
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
    @RequestMapping(value = "/register")
    public JsonObjectBO register(@RequestBody UserRegisterDO userRegisterDO) {
        JsonObjectBO responseJson = new JsonObjectBO();
        JSONObject jsonObject = new JSONObject();

        if (userRegisterDO == null){
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }

        if (!StringUtil.isNotNull(userRegisterDO.getUserAccount()) || !StringUtil.isNotNull(userRegisterDO.getPassword()) || !StringUtil.isNotNull(userRegisterDO.getPhoneAreaCode()) ||
                !StringUtil.isNotNull(userRegisterDO.getValidateCode()) || !StringUtil.isNotNull(userRegisterDO.getPhoneNumber()) || !StringUtil.isNotNull(userRegisterDO.getPayPassword())) {
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }

        UserDO validateUserDO = userService.getUserByUserAccount(userRegisterDO.getUserAccount());
        if (validateUserDO != null) {
            responseJson.setCode(SystemMessageConfig.ACCOUNT_REPEAT_CODE);
            responseJson.setMessage(SystemMessageConfig.ACCOUNT_REPEAT_MESSAGE);
            return responseJson;
        }

        UserDO user = userService.getUserByPhone(userRegisterDO.getPhoneNumber());
        if (user != null) {
            responseJson.setCode(SystemMessageConfig.PHONE_REGISTERED_CODE);
            responseJson.setMessage(SystemMessageConfig.PHONE_REGISTERED_MESSAGE);
            return responseJson;
        }

        //校验用户注册信息合法性
        responseJson = userService.validateUserInfo(userRegisterDO.getUserAccount(),userRegisterDO.getPassword(),userRegisterDO.getPayPassword());
        if (responseJson.getCode() != 1) {
            return responseJson;
        }

        responseJson = systemValidatePhoneService.validatePhone(userRegisterDO.getPhoneAreaCode()+userRegisterDO.getPhoneNumber(), userRegisterDO.getValidateCode());
        if (responseJson.getCode() != 1) {
            return responseJson;
        }

        UserDO userDO = new UserDO();
        userDO.setUserAccount(userRegisterDO.getUserAccount());
        userDO.setPassword(MD5Util.toMd5(userRegisterDO.getPassword()));
        userDO.setPhoneAreaCode(userRegisterDO.getPhoneAreaCode());
        userDO.setPhoneNumber(userRegisterDO.getPhoneNumber());
        userDO.setPayPassword(MD5Util.toMd5(userRegisterDO.getPayPassword()));

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
    @RequestMapping(value = "/validateAccount")
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
