package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;
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
 * wap端用户注册
 * @author cyfIverson
 * @create 2018-03-22
 */
@Controller
@RequestMapping("/userWap/userRegister")
@Scope(value = "prototype")
public class WapRegisterController {

    /**
     * 用户账户
     */
    @Autowired
    IUserService userService;

    /**
     * 手机验证码
     */
    @Autowired
    private ISystemValidatePhoneService systemValidatePhoneService;

    /**
     * 跳转至注册页面
     */
    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request) {
        request.setAttribute("selectedArea", PhoneAreaConfig.PHONE_AREA_CHINA);
        return "page/wap/register";
    }

    /**
     *  获取国家对应的手机区域号
     */
    @RequestMapping(value = "/getPhoneArea")
    public @ResponseBody JsonObjectBO getPhoneArea(HttpServletRequest request) {
        Map<String, String> phoneAreaMap = PhoneAreaConfig.phoneAreaMap;

        //对map数据进行转换
        String jsonObjectStr = JSONObject.toJSONString(phoneAreaMap);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneAreaMapJson",jsonObjectStr);

        JsonObjectBO responseJson = new JsonObjectBO();
        responseJson.setCode(1);
        responseJson.setMessage("查询成功");
        responseJson.setData(jsonObject);

        return responseJson;
    }

    /**
     * 校验用户名
     */
    @RequestMapping(value = "/validateAccount", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO validateAccount(HttpServletRequest request) {
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

    /**
     * 用户注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO register(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
        //取值
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String password = StringUtil.stringNullHandle(request.getParameter("password"));
        String payPassword = StringUtil.stringNullHandle(request.getParameter("payPassword"));
        String validateCode = StringUtil.stringNullHandle(request.getParameter("validateCode"));
        String phoneAreaCode = StringUtil.stringNullHandle(request.getParameter("phoneAreaCode"));
        String phoneNumber = StringUtil.stringNullHandle(request.getParameter("phoneNumber"));

        //校验
        if (!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password) || !StringUtil.isNotNull(phoneAreaCode) ||
                !StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(phoneNumber) || !StringUtil.isNotNull(payPassword)) {
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
        responseJson = userService.validateUserInfo(userAccount, password, payPassword);

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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userDO.getUserId());
            jsonObject.put("userAccount", userDO.getUserAccount());
            responseJson.setData(jsonObject);
            responseJson.setCode(1);
            responseJson.setMessage("注册成功");
        } else {
            responseJson.setCode(2);
            responseJson.setMessage("注册失败");
        }
        return responseJson;
    }
}
