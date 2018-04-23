package com.jydp.controller.userWeb;

import com.iqmkj.utils.Base64Util;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.VO.ForgetVO;
import com.jydp.service.ISystemValidatePhoneService;
import com.jydp.service.IUserService;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yqz
 * 忘记密码
 */
@RestController
@RequestMapping("/web/forget")
public class WebForgetController {

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
     * 忘记密码
     */
    @RequestMapping(value = "/forgetPassword")
    public JsonObjectBO forgetPassword(@RequestBody ForgetVO forgetVO) {
        JsonObjectBO responseJson = new JsonObjectBO();
        //是否传参
        if(forgetVO == null){
            responseJson.setCode(SystemMessageConfig.PARAMETER_ISNULL_CODE);
            responseJson.setMessage(SystemMessageConfig.PARAMETER_ISNULL_MESSAGE);
            return responseJson;
        }

        String userAccount = forgetVO.getUserAccount(); //账号
        String password = forgetVO.getPassword();  //密码

        String validateCode = forgetVO.getValidateCode();  //验证码
        String phoneNumber = forgetVO.getPhoneNumber();  //手机号
        String phoneAreaCode = forgetVO.getPhoneAreaCode();  //区域号

        // 参数是否为空
        if (!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password) ||
                !StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(phoneNumber) || !StringUtil.isNotNull(phoneAreaCode)) {
            responseJson.setCode(SystemMessageConfig.PARAMETER_ISNULL_CODE);
            responseJson.setMessage(SystemMessageConfig.PARAMETER_ISNULL_MESSAGE);
            return responseJson;
        }
        password = Base64Util.decode(password);
        // 验证参数是否合法
        if(!checkValue(userAccount) || !checkValue(password) || !checkNumber(validateCode) ||
                !checkNumber(phoneNumber) || validateCode.length() != 6 || phoneNumber.length() > 11){
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }
        // 验证码不匹配
        responseJson = systemValidatePhoneService.validatePhone(phoneAreaCode + phoneNumber, validateCode);
        if (responseJson.getCode() != 1) {
            responseJson.setCode(SystemMessageConfig.VALIDATECODE_ISERROR_CODE);
            responseJson.setMessage(SystemMessageConfig.VALIDATECODE_ISERROR_MESSAGE);
            return responseJson;
        }
        // 用户是否存在
        UserDO userDO = userService.getUserByUserAccount(userAccount);
        if (userDO == null) {
            responseJson.setCode(SystemMessageConfig.USER_ISEXIST_CODE);
            responseJson.setMessage(SystemMessageConfig.USER_ISEXIST_MESSAGE);
            return responseJson;
        }
        // 用户是否被禁止
        if (userDO.getAccountStatus() != 1) {
            responseJson.setCode(SystemMessageConfig.USER_ISDISABLED_CODE);
            responseJson.setMessage(SystemMessageConfig.USER_ISDISABLED_MESSAGE);
            return responseJson;
        }
        // 手机号与用户所绑手机号不匹配
        UserDO user = userService.validateUserPhoneNumber(userAccount, phoneAreaCode, phoneNumber);
        if (user == null) {
            responseJson.setCode(SystemMessageConfig.PHONENUMBER_AND_USERACCOUNT_NOTMATCHING_CODE);
            responseJson.setMessage(SystemMessageConfig.PHONENUMBER_AND_USERACCOUNT_NOTMATCHING_MESSAGE);
            return responseJson;
        }

        // 不可与支付密码相同
        String pwdEncrypt = MD5Util.toMd5(password);
        if (pwdEncrypt != null && pwdEncrypt.equals(user.getPayPassword())) {
            responseJson.setCode(SystemMessageConfig.PASSWORD_IDENTICAL_CODE);
            responseJson.setMessage(SystemMessageConfig.PASSWORD_IDENTICAL_MESSAGE);
            return responseJson;
        }
        // 找回成功
        boolean resetResult = userService.forgetPwd(userAccount, pwdEncrypt);
        if (resetResult) {
            responseJson.setCode(SystemMessageConfig.BACK_SUCCESS_CODE);
            responseJson.setMessage(SystemMessageConfig.BACK_SUCCESS_MESSAGE);
            return responseJson;
        } else {
            responseJson.setCode(SystemMessageConfig.BACK_FAIL_CODE);
            responseJson.setMessage(SystemMessageConfig.BACK_FAIL_MESSAGE);
            return responseJson;
        }
    }

    /**
     * 验证字符串
     * @param str
     * @return
     */
    private boolean checkValue(String str){
        String matchStr = "^[0-9a-zA-Z]{6,16}$";
        return str.matches(matchStr);
    }

    /**
     * 验证纯数字
     * @param str
     * @return
     */
    private boolean checkNumber(String str){
        String matchStr = "^?[0-9]*$";
        return str.matches(matchStr);
    }

}
