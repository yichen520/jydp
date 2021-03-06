package com.jydp.controller.web;

import com.iqmkj.utils.*;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcDealerUserDO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.BackerUserCurrencyNumDTO;
import com.jydp.entity.DTO.OtcTransactionPendOrderDTO;
import com.jydp.entity.VO.UserCurrencyNumVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.other.SendMessage;
import com.jydp.service.*;
import config.FileUrlConfig;
import config.PhoneAreaConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jydp.service.IOtcTransactionPendOrderService;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author sy
 */
@Controller
@RequestMapping("/userWeb/userMessage")
@Scope(value = "prototype")
public class UserMessageController {

    /**
     * 用户管理
     */
    @Autowired
    private IUserService userService;

    /**
     * 用户币管理
     */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /**
     * 系统手机验证
     */
    @Autowired
    private ISystemValidatePhoneService systemValidatePhoneService;

    /**
     * 手机验证
     */
    @Autowired
    public ISystemValidatePhoneService validatePhoneService;

    /**
     * 场外交易
     */
    @Autowired
    public IOtcTransactionPendOrderService otcTransactionPendOrderService;

    /**
     * 交易币种
     */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /**
     * 用户个人信息查询
     */
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
        if (userMessage == null) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户信息查询失败，请重试！");
            return "page/web/userMessage";
        }

        //手机号格式化
        if (StringUtil.isNotNull(userMessage.getPhoneNumber())) {
            String PhoneNumber = userMessage.getPhoneNumber();
            PhoneNumber = PhoneNumber.substring(0, 3) + "***" + PhoneNumber.substring(PhoneNumber.length() - 3, PhoneNumber.length());
            userMessage.setPhoneNumber(PhoneNumber);
        } else {
            userMessage.setPhoneNumber("未绑定手机");
        }

        //资产精度截取保留两位小数
        double userBalanceNum = NumberUtil.doubleFormat(userMessage.getUserBalance(), 2);
        userMessage.setUserBalance(userBalanceNum);

        double userBalanceLockNum = NumberUtil.doubleFormat(userMessage.getUserBalanceLock(), 2);
        userMessage.setUserBalanceLock(userBalanceLockNum);

        //总资产计算
        double userBalanceSum = BigDecimalUtil.add(userBalanceNum, userBalanceLockNum);

        List<UserCurrencyNumVO> userCurrencyList = new ArrayList<>();
        //查询用户币信息
        List<BackerUserCurrencyNumDTO> currencyList = userCurrencyNumService.getUserCurrencyNumByUserIdForWeb(user.getUserId());
        if (CollectionUtils.isNotEmpty(currencyList)) {
            for (BackerUserCurrencyNumDTO userCurrency : currencyList) {
                UserCurrencyNumVO userCurrencyNum = new UserCurrencyNumVO();
                userCurrencyNum.setCurrencyName(userCurrency.getCurrencyName());
                userCurrencyNum.setCurrencyNumber(userCurrency.getCurrencyNumber());
                userCurrencyNum.setCurrencyId(userCurrency.getCurrencyId());
                userCurrencyNum.setCurrencyNumberLock(userCurrency.getCurrencyNumberLock());

                //币数量精度截取保留四位小数
                double currencyNum = NumberUtil.doubleFormat(userCurrency.getCurrencyNumber(), 4);
                double currencyLock = NumberUtil.doubleFormat(userCurrency.getCurrencyNumberLock(), 4);

                //计算总金额
                double currencyNumberSum = BigDecimalUtil.add(currencyNum, currencyLock);
                userCurrencyNum.setCurrencyNumber(currencyNum);
                userCurrencyNum.setCurrencyNumberLock(currencyLock);
                userCurrencyNum.setCurrencyNumberSum(currencyNumberSum);
                userCurrencyList.add(userCurrencyNum);
            }
        }

        //判断用户是否为经销商
        if(user.getIsDealer() == 2){
            List<OtcTransactionPendOrderDO> otcTransactionPendOrderList = otcTransactionPendOrderService.getOtcTransactionPendOrderByUserId(user.getUserId());
            request.setAttribute("otcTransactionPendOrderList", otcTransactionPendOrderList);
        }

        Map<String, String> phoneAreaMap = PhoneAreaConfig.phoneAreaMap;
        userService.countCheckUserAmountForTimer(user.getUserId(), 1);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        request.setAttribute("phoneAreaMap", phoneAreaMap);
        request.setAttribute("userBalanceSum", userBalanceSum);
        request.setAttribute("userMessage", userMessage);
        request.setAttribute("userCurrencyList", userCurrencyList);

        return "page/web/userMessage";
    }

    /**
     * 用户登录密码修改
     */
    @RequestMapping(value = "/updateLogPassword.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO updateLogPassword(HttpServletRequest request) {
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
        String pasVerifyCode = StringUtil.stringNullHandle(request.getParameter("pasVerifyCode"));

        password = Base64Util.decode(password);
        newPassword = Base64Util.decode(newPassword);
        repetitionPassword = Base64Util.decode(repetitionPassword);

        if (!StringUtil.isNotNull(password) || !StringUtil.isNotNull(newPassword)
                || !StringUtil.isNotNull(repetitionPassword) || !StringUtil.isNotNull(pasVerifyCode)) {
            responseJson.setCode(3);
            responseJson.setMessage("未接受到参数");
            return responseJson;
        }

        //两次密码对比
        if (!newPassword.equals(repetitionPassword)) {
            responseJson.setCode(3);
            responseJson.setMessage("密码输入不一致！");
            return responseJson;
        }

        //新密码与原密码对比
        if (newPassword.equals(password)) {
            responseJson.setCode(3);
            responseJson.setMessage("新密码不可与原密码相同");
            return responseJson;
        }

        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            responseJson.setCode(3);
            responseJson.setMessage("用户信息查询失败，请稍后重试");
            return responseJson;
        }

        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber(), pasVerifyCode);
        if (validatePhone.getCode() != 1) {
            responseJson.setCode(validatePhone.getCode());
            responseJson.setMessage(validatePhone.getMessage());
            return responseJson;
        }

        //原密码判定
        password = MD5Util.toMd5(password);
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), password);
        if (userLog == null) {
            responseJson.setCode(3);
            responseJson.setMessage("原密码错误！");
            return responseJson;
        }

        //新密码修改
        newPassword = MD5Util.toMd5(newPassword);
        boolean userPay = userService.validateUserPay(user.getUserAccount(), newPassword);
        if (userPay) {
            responseJson.setCode(3);
            responseJson.setMessage("不可与支付密码相同！");
            return responseJson;
        }
        boolean forgetPwd = userService.forgetPwd(user.getUserAccount(), newPassword);
        if (!forgetPwd) {
            responseJson.setCode(3);
            responseJson.setMessage("修改失败，请重试");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("修改成功");
        return responseJson;
    }

    /**
     * 根据原支付密码修改用户支付密码
     */
    @RequestMapping(value = "/updatePayPasswordByPassword.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO updatePayPasswordByPassword(HttpServletRequest request) {
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

        password = Base64Util.decode(password);
        newPassword = Base64Util.decode(newPassword);
        repetitionPassword = Base64Util.decode(repetitionPassword);

        if (!StringUtil.isNotNull(password) || !StringUtil.isNotNull(newPassword)
                || !StringUtil.isNotNull(repetitionPassword)) {
            responseJson.setCode(3);
            responseJson.setMessage("未接受到参数");
            return responseJson;
        }

        //两次密码对比
        if (!newPassword.equals(repetitionPassword)) {
            responseJson.setCode(3);
            responseJson.setMessage("密码输入不一致！");
            return responseJson;
        }

        //新密码与原密码对比
        if (newPassword.equals(password)) {
            responseJson.setCode(3);
            responseJson.setMessage("新密码不可与原密码相同");
            return responseJson;
        }

        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            responseJson.setCode(3);
            responseJson.setMessage("用户信息查询失败，请稍后重试");
            return responseJson;
        }

        //原密码判定
        password = MD5Util.toMd5(password);
        boolean userPay = userService.validateUserPay(user.getUserAccount(), password);
        if (!userPay) {
            responseJson.setCode(3);
            responseJson.setMessage("原密码错误！");
            return responseJson;
        }

        //新密码修改
        newPassword = MD5Util.toMd5(newPassword);
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), newPassword);
        if (userLog != null) {
            responseJson.setCode(3);
            responseJson.setMessage("不可与登录密码相同！");
            return responseJson;
        }

        //修改支付密码
        boolean forgetPwd = userService.forgetPayPwd(user.getUserId(), newPassword);
        if (!forgetPwd) {
            responseJson.setCode(3);
            responseJson.setMessage("修改失败，请重试");
            return responseJson;
        }

        user.setIsPwd(1);
        request.getSession().setAttribute("userSession", user);

        responseJson.setCode(1);
        responseJson.setMessage("修改成功");
        return responseJson;
    }

    /**
     * 修改支付密码短信发送
     */
    @RequestMapping(value = "/payNoteVerify.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO payNoteVerify(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            responseJson.setCode(2);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            responseJson.setCode(3);
            responseJson.setMessage("用户信息查询失败，请稍后重试");
            return responseJson;
        }

        String phoneNumber = userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber();
        String ipAddress = IpAddressUtil.getIpAddress(request);
        if (!StringUtil.isNotNull(phoneNumber) || !StringUtil.isNotNull(ipAddress)) {
            responseJson.setCode(3);
            responseJson.setMessage("参数为空！");
            return responseJson;
        }

        String messageCode = SendMessage.createMessageCode();

        JsonObjectBO addValidatePhone = validatePhoneService.addValidatePhone(phoneNumber, messageCode, ipAddress);
        if (addValidatePhone.getCode() == 1) {
            boolean sendBoo = false;
            if (phoneNumber.substring(0, 3).equals(PhoneAreaConfig.PHONE_AREA_CHINA)) {
                sendBoo = SendMessage.send(phoneNumber, SendMessage.getMessageCodeContent(messageCode, 1));
            } else {
                sendBoo = SendMessage.send(phoneNumber, SendMessage.getEnMessageCodeContent(messageCode, 1));
            }
            if (sendBoo) {
                responseJson.setCode(1);
                responseJson.setMessage("短信验证码发送成功！");
                return responseJson;
            } else {
                responseJson.setCode(5);
                responseJson.setMessage("短信验证码发送失败！");
                return responseJson;
            }
        } else {
            responseJson.setCode(addValidatePhone.getCode());
            responseJson.setMessage(addValidatePhone.getMessage());
            return responseJson;
        }
    }

    /**
     * 根据手机号修改用户支付密码
     */
    @RequestMapping(value = "/updatePhoneByPassword.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO updatePhoneByPassword(HttpServletRequest request) {
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

        newPassword = Base64Util.decode(newPassword);
        repetitionPassword = Base64Util.decode(repetitionPassword);

        if (!StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(newPassword)
                || !StringUtil.isNotNull(repetitionPassword)) {
            responseJson.setCode(3);
            responseJson.setMessage("未接受到参数");
            return responseJson;
        }

        //两次密码对比
        if (!newPassword.equals(repetitionPassword)) {
            responseJson.setCode(3);
            responseJson.setMessage("密码输入不一致！");
            return responseJson;
        }

        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            responseJson.setCode(3);
            responseJson.setMessage("用户信息查询失败，请稍后重试");
            return responseJson;
        }

        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber(), validateCode);
        if (validatePhone.getCode() != 1) {
            responseJson.setCode(validatePhone.getCode());
            responseJson.setMessage(validatePhone.getMessage());
            return responseJson;
        }

        //新密码修改
        newPassword = MD5Util.toMd5(newPassword);
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), newPassword);
        if (userLog != null) {
            responseJson.setCode(3);
            responseJson.setMessage("不可与登录密码相同！");
            return responseJson;
        }

        boolean forgetPwd = userService.forgetPayPwd(user.getUserId(), newPassword);
        if (!forgetPwd) {
            responseJson.setCode(3);
            responseJson.setMessage("修改失败，请重试");
            return responseJson;
        }

        user.setIsPwd(1);
        request.getSession().setAttribute("userSession", user);

        responseJson.setCode(1);
        responseJson.setMessage("修改成功");
        return responseJson;
    }

    /**
     * 根据登录密码修改手机号
     */
    @RequestMapping(value = "/updatePasswordByPhone.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO updatePasswordByPhone(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            responseJson.setCode(2);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        String validateCode = StringUtil.stringNullHandle(request.getParameter("validateCode"));
        String newVerifyCode = StringUtil.stringNullHandle(request.getParameter("newVerifyCode"));
        String password = StringUtil.stringNullHandle(request.getParameter("password"));
        password = Base64Util.decode(password);
        String areaCode = StringUtil.stringNullHandle(request.getParameter("areaCode"));
        String phone = StringUtil.stringNullHandle(request.getParameter("phone"));

        if (!StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(password) || !StringUtil.isNotNull(newVerifyCode)
                || !StringUtil.isNotNull(phone) || !StringUtil.isNotNull(areaCode)) {
            responseJson.setCode(3);
            responseJson.setMessage("未接受到参数");
            return responseJson;
        }

        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            responseJson.setCode(3);
            responseJson.setMessage("用户信息查询失败，请稍后重试");
            return responseJson;
        }

        if ((areaCode + phone).equals(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber())) {
            responseJson.setCode(3);
            responseJson.setMessage("新手机号不可与原手机号相同");
            return responseJson;
        }
        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber(), validateCode);
        if (validatePhone.getCode() != 1) {
            responseJson.setCode(validatePhone.getCode());
            responseJson.setMessage("原手机" + validatePhone.getMessage());
            return responseJson;
        }

        //验证码判定
        validatePhone = systemValidatePhoneService.validatePhone(areaCode + phone, newVerifyCode);
        if (validatePhone.getCode() != 1) {
            responseJson.setCode(validatePhone.getCode());
            responseJson.setMessage("新手机" + validatePhone.getMessage());
            return responseJson;
        }

        //登录密码判定
        password = MD5Util.toMd5(password);
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), password);
        if (userLog == null) {
            responseJson.setCode(3);
            responseJson.setMessage("登录密码错误！");
            return responseJson;
        }

        //判定当前手机是否存在重复绑定
        UserDO userPhone = userService.getUserByPhone(phone);
        if (userPhone != null) {
            responseJson.setCode(3);
            responseJson.setMessage("该手机已被绑定");
            return responseJson;
        }

        //手机号修改
        boolean updatePhone = userService.updatePhone(user.getUserId(), areaCode, phone);
        if (!updatePhone) {
            responseJson.setCode(5);
            responseJson.setMessage("修改失败");
            return responseJson;
        }
        responseJson.setCode(1);
        responseJson.setMessage("修改成功");
        return responseJson;
    }

    /**
     * OTC 场外交易挂单 经销商发布订单  包括出售和回购订单
     */
    @RequestMapping(value = "/otcReleaseOrder.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO buy(HttpServletRequest request, OtcTransactionPendOrderDTO otcOrderVO, MultipartFile alipayImageUrl, MultipartFile wechatImageUrl) {
        JsonObjectBO resultJson = new JsonObjectBO();
        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            resultJson.setCode(4);
            resultJson.setMessage("未登录");
            return resultJson;
        }
        boolean fq =UserWebInterceptor.handleFrequent(request);
        if(fq){
            resultJson.setCode(2);
            resultJson.setMessage("用户操作频繁");
            return resultJson;
        }
        //判断用户是否为经销商
        if(userSession.getIsDealer() != 2){//不是经销商
            resultJson.setCode(3);
            resultJson.setMessage("当前用户不是经销商");
            return resultJson;
        }
        otcOrderVO.setUserId(userSession.getUserId());
        otcOrderVO.setUserAccount(userSession.getUserAccount());
        // 校验参数
        if (otcOrderVO.getCurrencyId() == 0 || otcOrderVO.getOrderType() == 0 || otcOrderVO.getPendingRatio() == 0 || otcOrderVO.getMaxNumber() <= 0 || otcOrderVO.getMaxNumber() <= otcOrderVO.getMinNumber()) {
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }
        if(otcOrderVO.getMinNumber() < 0){
            resultJson.setCode(3);
            resultJson.setMessage("最低限额不能小于0");
            return resultJson;
        }
        if(otcOrderVO.getMaxNumber() > 999999.99){
            resultJson.setCode(3);
            resultJson.setMessage("最高限额要小于一百万");
            return resultJson;
        }
        if(otcOrderVO.getPendingRatio() > 999999.99){
            resultJson.setCode(3);
            resultJson.setMessage("挂单比例要小于一百万");
            return resultJson;
        }
        if(BigDecimalUtil.mul( BigDecimalUtil.mul(otcOrderVO.getPendingRatio(),0.0001),10000)>BigDecimalUtil.mul(otcOrderVO.getMaxNumber(),10000)){
            resultJson.setCode(3);
            resultJson.setMessage("最高限额过小");
            return resultJson;
        }
        if(otcOrderVO.getMaxNumber() <= otcOrderVO.getMinNumber()){
            resultJson.setCode(3);
            resultJson.setMessage("最高限额要大于最低限额");
            return resultJson;
        }
        if(otcOrderVO.getOrderType() !=1 && otcOrderVO.getOrderType() !=2){
            resultJson.setCode(3);
            resultJson.setMessage("订单类型不正确");
            return resultJson;
        }
        if (otcOrderVO.getOrderType() == 1) {//出售单需要判断 回购单不需要判断支付方式
            // 判断是否选择付款方式
            if (!StringUtil.isNotNull(otcOrderVO.getBankAccount()) && !StringUtil.isNotNull(otcOrderVO.getAlipayAccount()) && !StringUtil.isNotNull(otcOrderVO.getWechatAccount())) {
                resultJson.setCode(3);
                resultJson.setMessage("付款方式参数错误");
                return resultJson;
            }
            //根据不同支付方式校验 不同的 必填参数
            if (StringUtil.isNotNull(otcOrderVO.getBankAccount())) {//银行卡
                if (!StringUtil.isNotNull(otcOrderVO.getBankAccount()) || !StringUtil.isNotNull(otcOrderVO.getBankName()) || !StringUtil.isNotNull(otcOrderVO.getBankBranch())
                        || !StringUtil.isNotNull(otcOrderVO.getPaymentName()) || !StringUtil.isNotNull(otcOrderVO.getPaymentPhone())) {
                    resultJson.setCode(3);
                    resultJson.setMessage("银行参数错误");
                    return resultJson;
                }
            }
            if (StringUtil.isNotNull(otcOrderVO.getAlipayAccount())) {//支付宝
                if (alipayImageUrl == null || alipayImageUrl.isEmpty()) {
                    resultJson.setCode(3);
                    resultJson.setMessage("支付宝账户参数错误");
                    return resultJson;
                }
                String alipayImage = ImageReduceUtil.reduceImageUploadRemote(alipayImageUrl, FileUrlConfig.file_remote_qeCodeImage_url);
                if (alipayImage.equals("") || alipayImage == null) {
                    resultJson.setCode(3);
                    resultJson.setMessage("支付宝收款码上传失败");
                    return resultJson;
                }
                otcOrderVO.setAlipayImage(alipayImage);
            }
            if (StringUtil.isNotNull(otcOrderVO.getWechatAccount())) {//微信
                if (wechatImageUrl == null || wechatImageUrl.isEmpty()) {
                    resultJson.setCode(3);
                    resultJson.setMessage("微信账户参数错误");
                    return resultJson;
                }
                String wechatImage = ImageReduceUtil.reduceImageUploadRemote(wechatImageUrl, FileUrlConfig.file_remote_qeCodeImage_url);
                if (wechatImage.equals("") || wechatImage == null) {
                    resultJson.setCode(3);
                    resultJson.setMessage("微信收款码上传失败");
                    return resultJson;
                }
                otcOrderVO.setWechatImage(wechatImage);
            }
        }
        if(otcOrderVO.getCurrencyId() == 999) {//如果是999  即为xt 币
            otcOrderVO.setCurrencyName("XT");
        }
        // 根据挂单类型判断 出售单：1 回购单：2   出售单 判断币种信息   回购单不做判断
        if(otcOrderVO.getCurrencyId() != 999){//如果是xt 币    不用判断币种信息  直接允许挂单
        if (otcOrderVO.getOrderType() == 1) {
            //获取币种信息
            TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(otcOrderVO.getCurrencyId());
            otcOrderVO.setCurrencyName(transactionCurrency.getCurrencyName());
            if (transactionCurrency == null) {
                resultJson.setCode(3);
                resultJson.setMessage("币种信息获取失败,请稍候再试");
                return resultJson;
            }

            if (transactionCurrency.getUpStatus() == 4) {
                resultJson.setCode(5);
                resultJson.setMessage("该币种已下线");
                return resultJson;
            }

            if (transactionCurrency.getPaymentType() != 1) {
                resultJson.setCode(4);
                resultJson.setMessage("该币种不在交易状态");
                return resultJson;
            }

            //获取经销商币信息
            UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(userSession.getUserId(),
                    otcOrderVO.getCurrencyId());
            if (userCurrencyNum == null) {
                UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();
                userCurrencyNumDO.setUserId(userSession.getUserId());
                userCurrencyNumDO.setCurrencyId(otcOrderVO.getCurrencyId());
                userCurrencyNumDO.setCurrencyNumber(0);
                userCurrencyNumDO.setCurrencyNumberLock(0);
                userCurrencyNumDO.setAddTime(DateUtil.getCurrentTime());

                userCurrencyNumService.insertUserCurrencyNum(userCurrencyNumDO);
                resultJson.setCode(5);
                resultJson.setMessage("经销商币不足");
                return resultJson;
            }

            if (userCurrencyNum.getCurrencyNumber() <= 0) {
                resultJson.setCode(5);
                resultJson.setMessage("经销商币不足");
                return resultJson;
            }
        }
        }
        //挂单操作
        resultJson = otcTransactionPendOrderService.insertPendOrder(otcOrderVO);

        return resultJson;
    }

    /**
     * 根据订单号删除用户挂单信息
     */
    @RequestMapping(value = "/deleteOtcTransactionPendOrder.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO deleteOtcTransactionPendOrder(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            responseJson.setCode(2);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        if (!StringUtil.isNotNull(otcPendingOrderNo)) {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误");
            return responseJson;
        }

        Timestamp currentTime = DateUtil.getCurrentTime();
        boolean deleteOtcTransaction = otcTransactionPendOrderService.deleteOtcTransactionPendOrderByOtcPendingOrderNo(user.getUserId(), otcPendingOrderNo, currentTime);
        if(!deleteOtcTransaction){
            responseJson.setCode(3);
            responseJson.setMessage("删除失败");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("删除成功");
        return responseJson;
    }
}
