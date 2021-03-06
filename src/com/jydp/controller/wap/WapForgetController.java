package com.jydp.controller.wap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.Base64Util;
import com.iqmkj.utils.LogUtil;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 忘记密码
 *
 * @author yqz
 */
@Controller
@RequestMapping("/userWap/forgetPassword")
@Scope(value = "prototype")
public class WapForgetController {

    /**
     * 用户账号
     **/
    @Autowired
    private IUserService userService;

    /**
     * 系统手机验证码
     */
    @Autowired
    private ISystemValidatePhoneService systemValidatePhoneService;

    /**
     * 跳转至忘记密码页面
     */
    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request) {
        Map<String, String> phoneAreaMap = PhoneAreaConfig.phoneAreaMap;
        request.setAttribute("selectedArea", PhoneAreaConfig.PHONE_AREA_CHINA);
        request.setAttribute("phoneAreaMap", phoneAreaMap);
        return "page/wap/forget";
    }

    /**
     * 获取电话域
     *
     * @return 返回电话域
     */
    @RequestMapping(value = "/phoneArea")
    public
    @ResponseBody
    JsonObjectBO getPhoneArea(HttpServletRequest request) {
        Map<String, String> phoneAreaMap = PhoneAreaConfig.phoneAreaMap;
        Map<String, String> newMap = new LinkedHashMap<>();
        String condition = null;
        try {
            if (StringUtil.isNotNull(request.getParameter("condition"))) {
                condition = StringUtil.stringNullHandle(new String(request.getParameter("condition").getBytes("iso8859-1"), "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            LogUtil.printErrorLog(e);
        }
        if (StringUtil.isNotNull(condition)) {
            Iterator<Map.Entry<String, String>> iterator = phoneAreaMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                if (entry.getKey().toString().contains(condition) || entry.getValue().toString().contains(condition)) {
                    newMap.put(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        } else {
            newMap.putAll(phoneAreaMap);
        }
        JsonObjectBO responseJson = new JsonObjectBO();
        responseJson.setCode(1);
        JSONObject object = new JSONObject();
        object.put("phoneAreaMap", newMap);
        responseJson.setData(object);
        return responseJson;
    }

    /**
     * 忘记密码
     */
    @RequestMapping(value = "/forgetPassword")
    public
    @ResponseBody
    JsonObjectBO forgetPassword(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String password = StringUtil.stringNullHandle(request.getParameter("password"));
        password = Base64Util.decode(password);
        String validateCode = StringUtil.stringNullHandle(request.getParameter("validateCode"));
        String phoneNumber = StringUtil.stringNullHandle(request.getParameter("phoneNumber"));
        String phoneAreaCode = StringUtil.stringNullHandle(request.getParameter("phoneAreaCode"));
        if (!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password) ||
                !StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(phoneNumber) || !StringUtil.isNotNull(phoneAreaCode)) {
            responseJson.setCode(2);
            responseJson.setMessage("提交信息存在为空选项");
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

        UserDO user = userService.validateUserPhoneNumber(userAccount, phoneAreaCode, phoneNumber);
        if (user == null) {
            responseJson.setCode(2);
            responseJson.setMessage("手机号与用户所绑手机号不匹配");
            return responseJson;
        }

        responseJson = systemValidatePhoneService.validatePhone(phoneAreaCode + phoneNumber, validateCode);
        if (responseJson.getCode() != 1) {
            return responseJson;
        }

        String pwdEncrypt = MD5Util.toMd5(password);
        if (pwdEncrypt != null && pwdEncrypt.equals(user.getPayPassword())) {
            responseJson.setCode(2);
            responseJson.setMessage("不可与支付密码相同！");
            return responseJson;
        }

        boolean resetResult = userService.forgetPwd(userAccount, pwdEncrypt);
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
