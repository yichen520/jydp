package com.jydp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.IpAddressUtil;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.controller.SendCodeController;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.BackerUserCurrencyNumDTO;
import com.jydp.entity.VO.UserCurrencyNumVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.other.SendMessage;
import com.jydp.service.ISystemValidatePhoneService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息
 * @author sy
 *
 */
@Controller
@RequestMapping("/userWeb/userMessage")
@Scope(value="prototype")
public class UserMessageController {

    /** 用户管理 */
    @Autowired
    private IUserService userService;

    /** 用户币管理 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 系统手机验证 */
    @Autowired
    private ISystemValidatePhoneService systemValidatePhoneService;

    /** 手机验证 */
    @Autowired
    public ISystemValidatePhoneService validatePhoneService;

    /** 用户个人信息查询 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录");
            return "page/web/login";
        }

        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if(userMessage == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户信息查询失败，请重试！");
            return "page/web/userMessage";
        }

        //原密码判定TODO
        String password = MD5Util.toMd5("123456");
        boolean userPay = userService.validateUserPay(user.getUserAccount(), password);
        if(userPay){
            userMessage.setPayPassword("1");
        }

        //手机号格式化
        if(StringUtil.isNotNull(userMessage.getPhoneNumber())){
            String PhoneNumber = userMessage.getPhoneNumber();
            PhoneNumber = PhoneNumber.substring(0, 3) + "***" + PhoneNumber.substring(PhoneNumber.length() - 3, PhoneNumber.length());
            userMessage.setPhoneNumber(PhoneNumber);
        } else {
            userMessage.setPhoneNumber("未绑定手机");
        }

        //总金额计算
        double userBalanceSum = BigDecimalUtil.add(userMessage.getUserBalance(), userMessage.getUserBalanceLock());


        //查询用户币信息
        List<BackerUserCurrencyNumDTO> currencyList = userCurrencyNumService.getUserCurrencyNumByUserIdForBacker(user.getUserId());
        if(currencyList == null || currencyList.size()<=0){
            request.setAttribute("code", 2);
            request.setAttribute("message", "未查询到用户相关币种信息");
            request.setAttribute("userBalanceSum", userBalanceSum);
            request.setAttribute("userMessage", userMessage);
            return "page/web/userMessage";
        }

        List<UserCurrencyNumVO> userCurrencyList = new ArrayList<>();
        for(BackerUserCurrencyNumDTO userCurrency : currencyList){
            UserCurrencyNumVO userCurrencyNum = new UserCurrencyNumVO();
            userCurrencyNum.setCurrencyName(userCurrency.getCurrencyName());
            userCurrencyNum.setCurrencyNumber(userCurrency.getCurrencyNumber());
            userCurrencyNum.setCurrencyId(userCurrency.getCurrencyId());
            userCurrencyNum.setCurrencyNumberLock(userCurrency.getCurrencyNumberLock());
            double currencyNumberSum = BigDecimalUtil.add(userCurrency.getCurrencyNumber(), userCurrency.getCurrencyNumberLock());
            //保留四位小数
            BigDecimal b = new BigDecimal(currencyNumberSum);
            currencyNumberSum = b.setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
            userCurrencyNum.setCurrencyNumberSum(currencyNumberSum);
            userCurrencyList.add(userCurrencyNum);
        }

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        request.setAttribute("userBalanceSum", userBalanceSum);
        request.setAttribute("userMessage", userMessage);
        request.setAttribute("userCurrencyList", userCurrencyList);
        return "page/web/userMessage";
    }

    /** 用户登陆密码修改 */
    @RequestMapping(value = "/updateLogPassword.htm", method= RequestMethod.POST)
    public @ResponseBody JsonObjectBO updateLogPassword(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            responseJson.setCode(2);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        String password = StringUtil.stringNullHandle(request.getParameter("password"));
        String newPassword = StringUtil.stringNullHandle(request.getParameter("newPassword"));
        String repetitionPassword = StringUtil.stringNullHandle(request.getParameter("repetitionPassword"));

        if (!StringUtil.isNotNull(password) || !StringUtil.isNotNull(newPassword)
                || !StringUtil.isNotNull(repetitionPassword)) {
            responseJson.setCode(3);
            responseJson.setMessage("未接受到参数");
            return responseJson;
        }

        //两次密码对比
        if(!newPassword.equals(repetitionPassword)){
            responseJson.setCode(3);
            responseJson.setMessage("密码输入不一致！");
            return responseJson;
        }

        //原密码判定
        password = MD5Util.toMd5(password);
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), password);
        if(userLog == null){
            responseJson.setCode(3);
            responseJson.setMessage("原密码错误！");
            return responseJson;
        }

        //新密码修改
        newPassword = MD5Util.toMd5(newPassword);
        boolean forgetPwd = userService.forgetPwd(user.getUserAccount(), newPassword);
        if(!forgetPwd){
            responseJson.setCode(3);
            responseJson.setMessage("修改失败，请重试");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("修改成功");
        return responseJson;
    }

    /** 根据原支付密码修改用户支付密码 */
    @RequestMapping(value = "/updatePayPasswordByPassword.htm", method= RequestMethod.POST)
    public @ResponseBody JsonObjectBO updatePayPasswordByPassword(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            responseJson.setCode(2);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        String password = StringUtil.stringNullHandle(request.getParameter("password"));
        String newPassword = StringUtil.stringNullHandle(request.getParameter("newPassword"));
        String repetitionPassword = StringUtil.stringNullHandle(request.getParameter("repetitionPassword"));

        if (!StringUtil.isNotNull(password) || !StringUtil.isNotNull(newPassword)
                || !StringUtil.isNotNull(repetitionPassword)) {
            responseJson.setCode(3);
            responseJson.setMessage("未接受到参数");
            return responseJson;
        }

        //初始密码判定
        if(newPassword.equals("123456")){
            responseJson.setCode(3);
            responseJson.setMessage("不可设为初始密码！");
            return responseJson;
        }

        //两次密码对比
        if(!newPassword.equals(repetitionPassword)){
            responseJson.setCode(3);
            responseJson.setMessage("密码输入不一致！");
            return responseJson;
        }

        //原密码判定
        password = MD5Util.toMd5(password);
        boolean userLog = userService.validateUserPay(user.getUserAccount(), password);
        if(!userLog){
            responseJson.setCode(3);
            responseJson.setMessage("原密码错误！");
            return responseJson;
        }

        //新密码修改
        newPassword = MD5Util.toMd5(newPassword);
        boolean forgetPwd = userService.forgetPayPwd(user.getUserId(), newPassword);
        if(!forgetPwd){
            responseJson.setCode(3);
            responseJson.setMessage("修改失败，请重试");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("修改成功");
        return responseJson;
    }

    /** 修改支付密码短信发送 */
    @RequestMapping(value = "/payNoteVerify.htm", method= RequestMethod.POST)
    public @ResponseBody JsonObjectBO payNoteVerify(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            responseJson.setCode(2);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if(userMessage == null){
            responseJson.setCode(3);
            responseJson.setMessage("用户信息查询失败，请稍后重试");
            return responseJson;
        }

        String phoneNumber = userMessage.getPhoneNumber();
        String ipAddress = IpAddressUtil.getIpAddress(request);
        if(!StringUtil.isNotNull(phoneNumber) || !StringUtil.isNotNull(ipAddress)){
            responseJson.setCode(3);
            responseJson.setMessage("参数为空！");
            return responseJson;
        }

        String messageCode = SendMessage.createMessageCode();

        JsonObjectBO addValidatePhone = validatePhoneService.addValidatePhone(phoneNumber, messageCode, ipAddress);
        if(addValidatePhone.getCode() == 1){
            boolean sendBoo = SendMessage.send(phoneNumber, SendMessage.getMessageCodeContent(messageCode, 1));
            if(sendBoo){
                responseJson.setCode(1);
                responseJson.setMessage("短信验证码发送成功！");
                return responseJson;
            }else{
                responseJson.setCode(5);
                responseJson.setMessage("短信验证码发送失败！");
                return responseJson;
            }
        }else{
            responseJson.setCode(addValidatePhone.getCode());
            responseJson.setMessage(addValidatePhone.getMessage());
            return responseJson;
        }
    }

    /** 根据手机号修改用户支付密码 */
    @RequestMapping(value = "/updatePhoneByPassword.htm", method= RequestMethod.POST)
    public @ResponseBody JsonObjectBO updatePhoneByPassword(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            responseJson.setCode(2);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        String validateCode = StringUtil.stringNullHandle(request.getParameter("validateCode"));
        String newPassword = StringUtil.stringNullHandle(request.getParameter("newPassword"));
        String repetitionPassword = StringUtil.stringNullHandle(request.getParameter("repetitionPassword"));

        if (!StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(newPassword)
                || !StringUtil.isNotNull(repetitionPassword)) {
            responseJson.setCode(3);
            responseJson.setMessage("未接受到参数");
            return responseJson;
        }

        //两次密码对比
        if(!newPassword.equals(repetitionPassword)){
            responseJson.setCode(3);
            responseJson.setMessage("密码输入不一致！");
            return responseJson;
        }

        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if(userMessage == null){
            responseJson.setCode(3);
            responseJson.setMessage("用户信息查询失败，请稍后重试");
            return responseJson;
        }

        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(userMessage.getPhoneNumber(), validateCode);
        if(validatePhone.getCode() != 1){
            responseJson.setCode(validatePhone.getCode());
            responseJson.setMessage(validatePhone.getMessage());
            return responseJson;
        }

        //新密码修改
        newPassword = MD5Util.toMd5(newPassword);
        boolean forgetPwd = userService.forgetPayPwd(user.getUserId(), newPassword);
        if(!forgetPwd){
            responseJson.setCode(3);
            responseJson.setMessage("修改失败，请重试");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("修改成功");
        return responseJson;
    }

    /** 根据登陆密码修改手机号 */
    @RequestMapping(value = "/updatePasswordByPhone.htm", method= RequestMethod.POST)
    public @ResponseBody JsonObjectBO updatePasswordByPhone(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            responseJson.setCode(2);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        String validateCode = StringUtil.stringNullHandle(request.getParameter("validateCode"));
        String password = StringUtil.stringNullHandle(request.getParameter("password"));
        String areaCode = StringUtil.stringNullHandle(request.getParameter("areaCode"));
        String phone = StringUtil.stringNullHandle(request.getParameter("phone"));

        if (!StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(password)
                || !StringUtil.isNotNull(phone) || !StringUtil.isNotNull(areaCode)) {
            responseJson.setCode(3);
            responseJson.setMessage("未接受到参数");
            return responseJson;
        }

        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(phone, validateCode);
        if(validatePhone.getCode() != 1){
            responseJson.setCode(validatePhone.getCode());
            responseJson.setMessage(validatePhone.getMessage());
            return responseJson;
        }

        //登陆密码判定
        password = MD5Util.toMd5(password);
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), password);
        if(userLog == null){
            responseJson.setCode(3);
            responseJson.setMessage("登陆密码错误！");
            return responseJson;
        }

        //手机号修改
        boolean updatePhone = userService.updatePhone(user.getUserId(), areaCode, phone);
        if(!updatePhone){
            responseJson.setCode(5);
            responseJson.setMessage("修改失败");
            return responseJson;
        }
        responseJson.setCode(1);
        responseJson.setMessage("修改成功");
        return responseJson;
    }
}
