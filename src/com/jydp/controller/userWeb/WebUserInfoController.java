package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.*;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.WapUserModifyPayPasswordDTO;
import com.jydp.entity.DTO.WapUserModifyPhoneDTO;
import com.jydp.entity.VO.WapUserCurrencyAssetsVO;
import com.jydp.entity.VO.WebUserVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.interceptor.WebInterceptor;
import com.jydp.service.ISystemValidatePhoneService;
import com.jydp.service.IUserService;
import com.jydp.service.IWebUserCurrencyNumService;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * web2.0个人信息
 * Created by xushilong3623 on 2018/4/18.
 */

@Controller
@RequestMapping(value = "/web/userInfo")
public class WebUserInfoController {

    /**
     * 用户信息
     */
    @Autowired
    IUserService userService;

    /**
     * 币种信息
     */
    @Autowired
    IWebUserCurrencyNumService webUserCurrencyNumService;

    /**
     * 验证手机号
     */
    @Autowired
    ISystemValidatePhoneService systemValidatePhoneService;

    /**
     * 根据userId获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBO getUserInfo(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO user = WebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }

        UserDO userInfo = userService.getUserByUserId(user.getUserId());
        if (userInfo == null) {
            response.setCode(SystemMessageConfig.CODE_USER_NOT_EXIST);
            response.setMessage(SystemMessageConfig.MESSAGE_USER_NOT_EXIST);
            return response;
        }

        Double userBalance = NumberUtil.doubleFormat(userInfo.getUserBalance(), 2);
        Double userBalanceLock = NumberUtil.doubleFormat(userInfo.getUserBalanceLock(), 2);
        Double totalUserBalance = BigDecimalUtil.add(userBalance, userBalanceLock);

        WebUserVO userVO = new WebUserVO();
        userVO.setUserId(userInfo.getUserId());
        userVO.setPhoneAreaCode(userInfo.getPhoneAreaCode());
        userVO.setPhoneNumber(userInfo.getPhoneNumber());
        userVO.setUserAccount(userInfo.getUserAccount());
        userVO.setUserBalance(userBalance);
        userVO.setUserBalanceLock(userBalanceLock);
        userVO.setTotalUserBalance(totalUserBalance);

        JSONObject data = new JSONObject();
        data.put("userInfo", userVO);
        response.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        response.setData(data);
        return response;
    }

    /**
     * 获取指定用户币种资产
     *
     * @return
     */
    @RequestMapping(value = "/currency/assets", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBO getUserCurrencyAssets(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO user = WebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }

        List<WapUserCurrencyAssetsVO> list = webUserCurrencyNumService.listWebUserCurrencyAssets(user.getUserId());
        if (CollectionUtils.isEmpty(list)) {
            response.setCode(SystemMessageConfig.CODE_NO_RESULT);
            response.setMessage(SystemMessageConfig.MESSAGE_NO_RESULT);
            return response;
        }

        response.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        JSONObject data = new JSONObject();
        data.put("currencyAssets", list);
        response.setData(data);
        return response;
    }

    /**
     * 通过密码修改支付密码
     *
     * @param request
     * @param userModifyPayPasswordDTO
     * @return
     */
    @RequestMapping(value = "/payPassword/modifyByPwd", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO userModifyPayPassword(HttpServletRequest request,
                                              @RequestBody WapUserModifyPayPasswordDTO userModifyPayPasswordDTO) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }
        if (userModifyPayPasswordDTO == null || !StringUtil.isNotNull(userModifyPayPasswordDTO.getOldPassword()) ||
                !StringUtil.isNotNull(userModifyPayPasswordDTO.getNewPassword()) || !StringUtil.isNotNull(userModifyPayPasswordDTO.getConfirmPassword())) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return response;
        }
        //两次密码对比
        if (!userModifyPayPasswordDTO.getNewPassword().equals(userModifyPayPasswordDTO.getConfirmPassword())) {
            response.setCode(SystemMessageConfig.CODE_PASSWORD_NOT_IDENTICAL);
            response.setMessage(SystemMessageConfig.MESSAGE_PASSWORD_NOT_IDENTICAL);
            return response;
        }
        //新密码与原密码对比
        if (userModifyPayPasswordDTO.getNewPassword().equals(userModifyPayPasswordDTO.getOldPassword())) {
            response.setCode(SystemMessageConfig.CODE_PASSWORD_OLD_NEW_COMMON);
            response.setMessage(SystemMessageConfig.MESSAGE_PASSWORD_OLD_NEW_COMMON);
            return response;
        }
        //获取用户信息
        UserDO userInfo = userService.getUserByUserId(user.getUserId());
        if (userInfo == null) {
            response.setCode(SystemMessageConfig.CODE_USER_INFO_NULL);
            response.setMessage(SystemMessageConfig.MESSAGE_USER_INFO_NULL);
            return response;
        }
        //判断支付密码是否正确
        boolean userPay = userService.validateUserPay(user.getUserAccount(), MD5Util.toMd5(userModifyPayPasswordDTO.getOldPassword()));
        if (!userPay) {
            response.setCode(SystemMessageConfig.CODE_PASSWORD_OLD_ERROR);
            response.setMessage(SystemMessageConfig.MESSAGE_PASSWORD_OLD_ERROR);
            return response;
        }
        //判断支付密码是否与登录密码相同
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), MD5Util.toMd5(userModifyPayPasswordDTO.getNewPassword()));
        if (userLog != null) {
            response.setCode(SystemMessageConfig.CODE_PAYPASSWORD_WITH_PASSWORD_COMMON);
            response.setMessage(SystemMessageConfig.MESSAGE_PASSWORD_WITH_PAYPASSWORD_COMMON);
            return response;
        }

        //修改支付密码
        boolean forgetPwd = userService.forgetPayPwd(user.getUserId(), MD5Util.toMd5(userModifyPayPasswordDTO.getNewPassword()));
        if (!forgetPwd) {
            response.setCode(SystemMessageConfig.CODE_OPERATE_ERROR);
            response.setMessage(SystemMessageConfig.MESSAGE_OPERATE_ERROR);
            return response;
        }
        response.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        return response;
    }

    /**
     * 通过手机号修改支付密码
     *
     * @param request
     * @param userModifyPayPasswordDTO
     * @return
     */
    @RequestMapping(value = "/payPassword/modifyByPhone", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO modifyPayPasswordByPhone(HttpServletRequest request,
                                                 @RequestBody WapUserModifyPayPasswordDTO userModifyPayPasswordDTO) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO user = WebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }
        if (userModifyPayPasswordDTO == null || !StringUtil.isNotNull(userModifyPayPasswordDTO.getValidCode()) ||
                !StringUtil.isNotNull(userModifyPayPasswordDTO.getNewPassword()) || !StringUtil.isNotNull(userModifyPayPasswordDTO.getConfirmPassword())) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return response;
        }
        //两次密码对比
        if (!userModifyPayPasswordDTO.getNewPassword().equals(userModifyPayPasswordDTO.getConfirmPassword())) {
            response.setCode(SystemMessageConfig.CODE_PASSWORD_NOT_IDENTICAL);
            response.setMessage(SystemMessageConfig.MESSAGE_PASSWORD_NOT_IDENTICAL);
            return response;
        }
        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            response.setCode(SystemMessageConfig.CODE_USER_INFO_NULL);
            response.setMessage(SystemMessageConfig.MESSAGE_USER_INFO_NULL);
            return response;
        }
        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber(), userModifyPayPasswordDTO.getValidCode());
        if (validatePhone.getCode() != SystemMessageConfig.SYSTEM_CODE_SUCCESS) {
            response.setCode(validatePhone.getCode());
            response.setMessage(validatePhone.getMessage());
            return response;
        }
        //判断支付密码是否与登录密码相同
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), MD5Util.toMd5(userModifyPayPasswordDTO.getNewPassword()));
        if (userLog != null) {
            response.setCode(SystemMessageConfig.CODE_PAYPASSWORD_WITH_PASSWORD_COMMON);
            response.setMessage(SystemMessageConfig.MESSAGE_PAYPASSWORD_WITH_PASSWORD_COMMON);
            return response;
        }

        //修改支付密码
        boolean forgetPwd = userService.forgetPayPwd(user.getUserId(), MD5Util.toMd5(userModifyPayPasswordDTO.getNewPassword()));
        if (!forgetPwd) {
            response.setCode(SystemMessageConfig.CODE_OPERATE_ERROR);
            response.setMessage(SystemMessageConfig.MESSAGE_OPERATE_ERROR);
            return response;
        }
        response.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        return response;
    }

    /**
     * 修改登录密码
     *
     * @param request
     * @param userModifyPasswordDTO
     * @return
     */
    @RequestMapping(value = "/password/modify", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO modifyPassword(HttpServletRequest request,
                                       @RequestBody WapUserModifyPayPasswordDTO userModifyPasswordDTO) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }
        if (userModifyPasswordDTO == null || !StringUtil.isNotNull(userModifyPasswordDTO.getValidCode()) || !StringUtil.isNotNull(userModifyPasswordDTO.getOldPassword()) ||
                !StringUtil.isNotNull(userModifyPasswordDTO.getNewPassword()) || !StringUtil.isNotNull(userModifyPasswordDTO.getConfirmPassword())) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return response;
        }
        //两次密码对比
        if (!userModifyPasswordDTO.getNewPassword().equals(userModifyPasswordDTO.getConfirmPassword())) {
            response.setCode(SystemMessageConfig.CODE_PASSWORD_NOT_IDENTICAL);
            response.setMessage(SystemMessageConfig.MESSAGE_PASSWORD_NOT_IDENTICAL);
            return response;
        }
        //新密码与原密码对比
        if (userModifyPasswordDTO.getNewPassword().equals(userModifyPasswordDTO.getOldPassword())) {
            response.setCode(SystemMessageConfig.CODE_PASSWORD_OLD_NEW_COMMON);
            response.setMessage(SystemMessageConfig.MESSAGE_PASSWORD_OLD_NEW_COMMON);
            return response;
        }
        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            response.setCode(SystemMessageConfig.CODE_USER_INFO_NULL);
            response.setMessage(SystemMessageConfig.MESSAGE_USER_INFO_NULL);
            return response;
        }
        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber(), userModifyPasswordDTO.getValidCode());
        if (validatePhone.getCode() != SystemMessageConfig.SYSTEM_CODE_SUCCESS) {
            response.setCode(validatePhone.getCode());
            response.setMessage(validatePhone.getMessage());
            return response;
        }

        //原密码判定
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), MD5Util.toMd5(userModifyPasswordDTO.getOldPassword()));
        if (userLog == null) {
            response.setCode(SystemMessageConfig.CODE_PASSWORD_OLD_ERROR);
            response.setMessage(SystemMessageConfig.MESSAGE_PASSWORD_OLD_ERROR);
            return response;
        }
        //判断新密码是否与支付密码相同
        boolean userPay = userService.validateUserPay(user.getUserAccount(), MD5Util.toMd5(userModifyPasswordDTO.getNewPassword()));
        if (userPay) {
            response.setCode(SystemMessageConfig.CODE_PASSWORD_WITH_PAYPASSWORD_COMMON);
            response.setMessage(SystemMessageConfig.MESSAGE_PASSWORD_WITH_PAYPASSWORD_COMMON);
            return response;
        }
        //更新密码
        boolean forgetPwd = userService.forgetPwd(user.getUserAccount(), MD5Util.toMd5(userModifyPasswordDTO.getNewPassword()));
        if (!forgetPwd) {
            response.setCode(SystemMessageConfig.CODE_OPERATE_ERROR);
            response.setMessage(SystemMessageConfig.MESSAGE_OPERATE_ERROR);
            return response;
        }
        response.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        return response;
    }

    /**
     * 修改手机号
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/phone/modify", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO modifyPhone(HttpServletRequest request,
                                    @RequestBody WapUserModifyPhoneDTO userModifyPhoneDTO) {
        JsonObjectBO response = new JsonObjectBO();

        UserSessionBO user = WebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }
        if (userModifyPhoneDTO == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return response;
        }
        String validateCode = StringUtil.stringNullHandle(userModifyPhoneDTO.getOldValidCode());
        String newVerifyCode = StringUtil.stringNullHandle(userModifyPhoneDTO.getNewValidCode());
        String password = StringUtil.stringNullHandle(userModifyPhoneDTO.getPassword());
        String areaCode = StringUtil.stringNullHandle(userModifyPhoneDTO.getAreaCode());
        String phone = StringUtil.stringNullHandle(userModifyPhoneDTO.getPhone());

        if (!StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(password) || !StringUtil.isNotNull(newVerifyCode)
                || !StringUtil.isNotNull(phone) || !StringUtil.isNotNull(areaCode)) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return response;
        }

        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            response.setCode(SystemMessageConfig.CODE_USER_INFO_NULL);
            response.setMessage(SystemMessageConfig.MESSAGE_USER_INFO_NULL);
            return response;
        }

        if ((areaCode + phone).equals(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber())) {
            response.setCode(SystemMessageConfig.CODE_PHONE_OLD_NEW_COMMON);
            response.setMessage(SystemMessageConfig.MESSAGE_PHONE_OLD_NEW_COMMON);
            return response;
        }
        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber(), validateCode);
        if (validatePhone.getCode() != SystemMessageConfig.SYSTEM_CODE_SUCCESS) {
            response.setCode(validatePhone.getCode());
            response.setMessage("原手机" + validatePhone.getMessage());
            return response;
        }

        //验证码判定
        validatePhone = systemValidatePhoneService.validatePhone(areaCode + phone, newVerifyCode);
        if (validatePhone.getCode() != SystemMessageConfig.SYSTEM_CODE_SUCCESS) {
            response.setCode(validatePhone.getCode());
            response.setMessage("新手机" + validatePhone.getMessage());
            return response;
        }

        //登录密码判定
        password = MD5Util.toMd5(password);
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), password);
        if (userLog == null) {
            response.setCode(SystemMessageConfig.CODE_PASSWORD_ERROR);
            response.setMessage(SystemMessageConfig.MESSAGE_PASSWORD_ERROR);
            return response;
        }

        //判定当前手机是否存在重复绑定
        UserDO userPhone = userService.getUserByPhone(phone);
        if (userPhone != null) {
            response.setCode(SystemMessageConfig.CODE_PHONE_BIND);
            response.setMessage(SystemMessageConfig.MESSAGE_PHONE_BIND);
            return response;
        }

        //手机号修改
        boolean updatePhone = userService.updatePhone(user.getUserId(), areaCode, phone);
        if (!updatePhone) {
            response.setCode(SystemMessageConfig.CODE_OPERATE_ERROR);
            response.setMessage(SystemMessageConfig.MESSAGE_OPERATE_ERROR);
            return response;
        }
        response.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        return response;
    }
}
