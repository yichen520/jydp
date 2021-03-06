package com.jydp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.*;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcDealerUserDO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.DO.otc.UserPaymentTypeDO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import com.jydp.entity.DTO.UserPaymentTypeDTO;
import com.jydp.entity.VO.*;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.*;
import config.FileUrlConfig;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 场外交易模块 交易
 * @author hz
 *
 */
@Controller
@RequestMapping("/userWeb/otcTradeCenter")
@Scope(value="prototype")
public class OtcTradeCenterController {

   /** 场外交易 挂单记录 */
    @Autowired
    private IOtcTransactionPendOrderService otcTransactionPendOrderService;

   /** 场外交易 成交记录 */
    @Autowired
    private IOtcTransactionUserDealService otcTransactionUserDealService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 用户标识经销商相关操作 */
    @Autowired
    private IOtcDealerUserService otcDealerUserService;

    /** 用户收款记录 */
    @Autowired
    private IUserPaymentTypeService userPaymentTypeService;

    /** 用户认证 */
    @Autowired
    private IUserIdentificationService userIdentificationService;

    /** 获取经销商收款方式 */
    @RequestMapping(value = "/getPayType.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO getPayType(HttpServletRequest request){
        JsonObjectBO resultJson = new JsonObjectBO();
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if(userSession == null){
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

        //获取参数
        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));

        if (!StringUtil.isNotNull(otcPendingOrderNo) || !StringUtil.isNotNull(userIdStr)) {
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        int userId = 0;
        if (StringUtil.isNotNull(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }

        //根据用户id、挂单号查询 收款记录列表
        List<UserPaymentTypeDO> userPaymentType = userPaymentTypeService.listUserPaymentType(userId, otcPendingOrderNo);
        if(userPaymentType == null || userPaymentType.size() == 0){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        int hasBank = 0;
        int hasWeiXin = 0;
        int hasAliPay = 0;
        for (UserPaymentTypeDO paymentType : userPaymentType) {
            if(paymentType.getPaymentType() == 1){
                hasBank = 1;
            }else if(paymentType.getPaymentType() == 2){
                hasWeiXin = 1;
            }else if(paymentType.getPaymentType() == 3){
                hasAliPay = 1;
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hasBank", hasBank);
        jsonObject.put("hasWeiXin", hasWeiXin);
        jsonObject.put("hasAliPay", hasAliPay);

        resultJson.setCode(1);
        resultJson.setMessage("获取信息成功");
        resultJson.setData(jsonObject);
        return resultJson;
    }

    /** 获取经销商收款详情 */
    @RequestMapping(value = "/getPayDetails.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO getPayDetails(HttpServletRequest request){
        JsonObjectBO resultJson = new JsonObjectBO();
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if(userSession == null){
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

        //获取参数
        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));

        if (!StringUtil.isNotNull(otcPendingOrderNo) || !StringUtil.isNotNull(paymentTypeStr)) {
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        int paymentType = 0;
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }

        //获取挂单信息
        OtcTransactionPendOrderDO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrderByOrderNo(otcPendingOrderNo);
        if(otcTransactionPendOrder == null){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }
        //查询用户信息
        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationByUserIdLately(otcTransactionPendOrder.getUserId());
        if(userIdentification == null){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }
        if(userIdentification.getIdentificationStatus() != 2){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        //查询经销商信息
        OtcDealerUserDO otcDealerUser = otcDealerUserService.getOtcDealerUserByUserId(otcTransactionPendOrder.getUserId());
        if(otcDealerUser == null){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        //根据用户id、挂单号、支付方式查询 收款记录
        UserPaymentTypeDO userPaymentType = userPaymentTypeService.getUserPaymentType(otcTransactionPendOrder.getUserId(), otcPendingOrderNo, paymentType);
        if(userPaymentType == null){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dealerName", otcDealerUser.getDealerName());
        jsonObject.put("userName", userIdentification.getUserName());
        jsonObject.put("userPhone", userIdentification.getUserPhone());
        jsonObject.put("userPaymentType", userPaymentType);

        resultJson.setCode(1);
        resultJson.setMessage("获取信息成功");
        resultJson.setData(jsonObject);
        return resultJson;
    }

    /** 购买出售单 */
    @RequestMapping(value = "/buy.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO buy(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if(userSession == null){
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

        //获取参数
        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        String buyNumStr = StringUtil.stringNullHandle(request.getParameter("buyNum"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));

        if (!StringUtil.isNotNull(otcPendingOrderNo) || !StringUtil.isNotNull(buyNumStr) || !StringUtil.isNotNull(paymentTypeStr)) {
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        double buyNum = 0;
        if (StringUtil.isNotNull(buyNumStr)) {
            buyNum = Double.parseDouble(buyNumStr);
        }

        int paymentType = 0;
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }

        //判断交易时间限制
        boolean timeBoo = DateUtil.isTradeTime();
        if(timeBoo){
            resultJson.setCode(3);
            resultJson.setMessage("不在交易时间段内");
            return resultJson;
        }

        //交易数量限制
        if(buyNum <= 0){
            resultJson.setCode(3);
            resultJson.setMessage("交易数量不能小于等于0");
            return resultJson;
        }

        //支付方式判断
        if(paymentType <= 0){
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        //获取用户信息
        UserDO user = userService.getUserByUserId(userSession.getUserId());
        if(user == null){
            resultJson.setCode(3);
            resultJson.setMessage("该用户不存在");
            return resultJson;
        }

        if(user.getAccountStatus() != 1){
            resultJson.setCode(3);
            resultJson.setMessage("该账号已被禁用");
            return resultJson;
        }

        //获取挂单信息
        OtcTransactionPendOrderDO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrderByOrderNo(otcPendingOrderNo);
        if(otcTransactionPendOrder.getOrderType() != 1){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        if(otcTransactionPendOrder == null){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        if(otcTransactionPendOrder.getPendingStatus() == -1){
            resultJson.setCode(2);
            resultJson.setMessage("该广告已被删除");
            return resultJson;
        }

        double sum = BigDecimalUtil.mul(otcTransactionPendOrder.getPendingRatio(),buyNum);

        if(sum < otcTransactionPendOrder.getMinNumber()){
            resultJson.setCode(3);
            resultJson.setMessage("交易额度不能低于最低限额");
            return resultJson;
        }

        if(sum > otcTransactionPendOrder.getMaxNumber()){
            resultJson.setCode(3);
            resultJson.setMessage("交易额度不能高于最高限额");
            return resultJson;
        }

        //获取经销商信息
        UserDO dealer = userService.getUserByUserId(otcTransactionPendOrder.getUserId());
        if(user == null){
            resultJson.setCode(3);
            resultJson.setMessage("该经销商不存在");
            return resultJson;
        }

        if(otcTransactionPendOrder.getCurrencyId() == 999){
            if(dealer.getUserBalance() < buyNum){
                resultJson.setCode(5);
                resultJson.setMessage("经销商币不足");
                return resultJson;
            }
        }else {
            //获取币种信息
            TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(otcTransactionPendOrder.getCurrencyId());
            if(transactionCurrency == null){
                resultJson.setCode(3);
                resultJson.setMessage("币种信息获取失败,请稍候再试");
                return resultJson;
            }

            if(transactionCurrency.getUpStatus() == 4){
                resultJson.setCode(5);
                resultJson.setMessage("该币种已下线");
                return resultJson;
            }

            if(transactionCurrency.getPaymentType() != 1){
                resultJson.setCode(4);
                resultJson.setMessage("该币种不在交易状态");
                return resultJson;
            }

            //获取经销商币信息
            UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(otcTransactionPendOrder.getUserId(),
                    otcTransactionPendOrder.getCurrencyId());
            if(userCurrencyNum == null){
                UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();
                userCurrencyNumDO.setUserId(otcTransactionPendOrder.getUserId());
                userCurrencyNumDO.setCurrencyId(otcTransactionPendOrder.getCurrencyId());
                userCurrencyNumDO.setCurrencyNumber(0);
                userCurrencyNumDO.setCurrencyNumberLock(0);
                userCurrencyNumDO.setAddTime(DateUtil.getCurrentTime());

                userCurrencyNumService.insertUserCurrencyNum(userCurrencyNumDO);

                resultJson.setCode(5);
                resultJson.setMessage("经销商币不足");
                return resultJson;
            }

            if(userCurrencyNum.getCurrencyNumber() < buyNum){
                resultJson.setCode(5);
                resultJson.setMessage("经销商币不足");
                return resultJson;
            }
        }

        //获取经销商收款信息
        UserPaymentTypeDO userPaymentType = userPaymentTypeService.getUserPaymentType(otcTransactionPendOrder.getUserId(),
                otcPendingOrderNo, paymentType);
        if(userPaymentType == null){
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        //新增成交记录
        JsonObjectBO jsonObject = otcTransactionUserDealService.insertOtcTransactionUserDeal(
                otcPendingOrderNo, user.getUserId(), otcTransactionPendOrder.getUserId(), userPaymentType.getTypeId(),
                user.getUserAccount(), 1, otcTransactionPendOrder.getCurrencyId(), otcTransactionPendOrder.getCurrencyName(),
                otcTransactionPendOrder.getPendingRatio(), buyNum, sum, otcTransactionPendOrder.getAddTime(),0,null);
        if(jsonObject.getCode() != 1){
            resultJson.setCode(jsonObject.getCode());
            resultJson.setMessage(jsonObject.getMessage());
            return resultJson;
        }

        resultJson.setCode(1);
        resultJson.setMessage("下单成功");
        return resultJson;
    }

    /** 出售回购单 */
    @RequestMapping(value = "/sell.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO sell(HttpServletRequest request){
        JsonObjectBO resultJson = new JsonObjectBO();
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if(userSession == null){
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

        //获取参数
        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        String sellNumStr = StringUtil.stringNullHandle(request.getParameter("sellNum"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));

        if (!StringUtil.isNotNull(otcPendingOrderNo) || !StringUtil.isNotNull(sellNumStr) || !StringUtil.isNotNull(paymentTypeStr)) {
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        double sellNum = 0;
        if (StringUtil.isNotNull(sellNumStr)) {
            sellNum = Double.parseDouble(sellNumStr);
        }

        int paymentType = 0;
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }

        //交易数量限制
        if(sellNum <= 0){
            resultJson.setCode(3);
            resultJson.setMessage("交易数量不能小于等于0");
            return resultJson;
        }

        String paymentAccount;
        String bankName;
        String bankBranch;
        String paymentName;
        String paymentPhone;
        MultipartFile paymentImage;
        String imageUrl = "";
        UserPaymentTypeDTO userPaymentType = new UserPaymentTypeDTO();

        //支付方式判断
        if(paymentType <= 0){
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }else if(paymentType == 1){
            //获取银行卡参数
            paymentAccount = StringUtil.stringNullHandle(request.getParameter("paymentAccount"));
            bankName = StringUtil.stringNullHandle(request.getParameter("bankName"));
            bankBranch = StringUtil.stringNullHandle(request.getParameter("bankBranch"));
            paymentName = StringUtil.stringNullHandle(request.getParameter("paymentName"));
            paymentPhone = StringUtil.stringNullHandle(request.getParameter("paymentPhone"));

            if (!StringUtil.isNotNull(paymentAccount) || !StringUtil.isNotNull(bankName) || !StringUtil.isNotNull(bankBranch)
                    ||!StringUtil.isNotNull(paymentName) || !StringUtil.isNotNull(paymentPhone)) {
                resultJson.setCode(2);
                resultJson.setMessage("参数错误");
                return resultJson;
            }
            userPaymentType.setPaymentAccount(paymentAccount);
            userPaymentType.setBankName(bankName);
            userPaymentType.setBankBranch(bankBranch);
            userPaymentType.setPaymentName(paymentName);
            userPaymentType.setPaymentPhone(paymentPhone);
        }else if(paymentType == 2 || paymentType == 3){
            //获取微信/支付宝参数
            paymentAccount = StringUtil.stringNullHandle(request.getParameter("paymentAccount"));
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            paymentImage = multipartRequest.getFile("paymentImage");
            if (paymentImage == null || paymentImage.isEmpty() || paymentImage.getSize() <= 0 || !StringUtil.isNotNull(paymentAccount)) {
                resultJson.setCode(2);
                resultJson.setMessage("参数错误");
                return resultJson;
            }
            imageUrl = ImageReduceUtil.reduceImageUploadRemote(paymentImage, FileUrlConfig.file_remote_qeCodeImage_url);
            if (imageUrl.equals("") || imageUrl == null) {
                resultJson.setCode(2);
                resultJson.setMessage("上传二维码失败");
                return resultJson;
            }
            userPaymentType.setPaymentAccount(paymentAccount);
            userPaymentType.setPaymentImage(imageUrl);
        }

        //判断交易时间限制
        boolean timeBoo = DateUtil.isTradeTime();
        if(timeBoo){
            resultJson.setCode(3);
            resultJson.setMessage("不在交易时间段内");
            return resultJson;
        }

        //获取用户信息
        UserDO user = userService.getUserByUserId(userSession.getUserId());
        if(user == null){
            resultJson.setCode(3);
            resultJson.setMessage("该用户不存在");
            return resultJson;
        }

        if(user.getAccountStatus() != 1){
            resultJson.setCode(3);
            resultJson.setMessage("该账号已被禁用");
            return resultJson;
        }

        //获取挂单信息
        OtcTransactionPendOrderDO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrderByOrderNo(otcPendingOrderNo);
        if(otcTransactionPendOrder.getOrderType() != 2){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        if(otcTransactionPendOrder == null){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        if(otcTransactionPendOrder.getPendingStatus() == -1){
            resultJson.setCode(2);
            resultJson.setMessage("该广告已被删除");
            return resultJson;
        }

        double sum = BigDecimalUtil.mul(otcTransactionPendOrder.getPendingRatio(),sellNum);

        if(sum < otcTransactionPendOrder.getMinNumber()){
            resultJson.setCode(3);
            resultJson.setMessage("交易额度不能低于最低限额");
            return resultJson;
        }

        if(sum > otcTransactionPendOrder.getMaxNumber()){
            resultJson.setCode(3);
            resultJson.setMessage("交易额度不能高于最高限额");
            return resultJson;
        }

        //获取经销商信息
        UserDO dealer = userService.getUserByUserId(otcTransactionPendOrder.getUserId());
        if(user == null){
            resultJson.setCode(3);
            resultJson.setMessage("该经销商不存在");
            return resultJson;
        }

        if(otcTransactionPendOrder.getCurrencyId() == 999){
            if(user.getUserBalance() < sellNum){
                resultJson.setCode(5);
                resultJson.setMessage("用户币不足");
                return resultJson;
            }
        }else {
            //获取币种信息
            TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(otcTransactionPendOrder.getCurrencyId());
            if(transactionCurrency == null){
                resultJson.setCode(3);
                resultJson.setMessage("币种信息获取失败,请稍候再试");
                return resultJson;
            }

            if(transactionCurrency.getUpStatus() == 4){
                resultJson.setCode(5);
                resultJson.setMessage("该币种已下线");
                return resultJson;
            }

            if(transactionCurrency.getPaymentType() != 1){
                resultJson.setCode(4);
                resultJson.setMessage("该币种不在交易状态");
                return resultJson;
            }

            //获取用户币信息
            UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(user.getUserId(),
                    otcTransactionPendOrder.getCurrencyId());
            if(userCurrencyNum == null){
                UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();
                userCurrencyNumDO.setUserId(user.getUserId());
                userCurrencyNumDO.setCurrencyId(otcTransactionPendOrder.getCurrencyId());
                userCurrencyNumDO.setCurrencyNumber(0);
                userCurrencyNumDO.setCurrencyNumberLock(0);
                userCurrencyNumDO.setAddTime(DateUtil.getCurrentTime());

                userCurrencyNumService.insertUserCurrencyNum(userCurrencyNumDO);

                resultJson.setCode(5);
                resultJson.setMessage("用户币不足");
                return resultJson;
            }

            if(userCurrencyNum.getCurrencyNumber() < sellNum){
                resultJson.setCode(5);
                resultJson.setMessage("用户币不足");
                return resultJson;
            }
        }

        //新增成交记录
        JsonObjectBO jsonObject = otcTransactionUserDealService.insertOtcTransactionUserDeal(
                otcPendingOrderNo, otcTransactionPendOrder.getUserId(), user.getUserId(), 0,
                otcTransactionPendOrder.getUserAccount(), 2, otcTransactionPendOrder.getCurrencyId(), otcTransactionPendOrder.getCurrencyName(),
                otcTransactionPendOrder.getPendingRatio(), sellNum, sum, otcTransactionPendOrder.getAddTime(),paymentType,userPaymentType);
        if(jsonObject.getCode() != 1){
            // 删除图片
            FileWriteRemoteUtil.deleteFile(imageUrl);

            resultJson.setCode(jsonObject.getCode());
            resultJson.setMessage(jsonObject.getMessage());
            return resultJson;
        }

        resultJson.setCode(1);
        resultJson.setMessage("下单成功");
        return resultJson;
    }

    /** 展示页面 */
    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request){
        list(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功!");
        return "page/web/tradeOut";
    }

    /** 查询数据 */
    public void list(HttpServletRequest request) {

        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String orderTypeStr = StringUtil.stringNullHandle(request.getParameter("orderType"));
        String area = StringUtil.stringNullHandle(request.getParameter("area"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int currencyId = 0;
        int orderType = 0;
        int pageNumber = 0;

        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        if (StringUtil.isNotNull(orderTypeStr)) {
            orderType = Integer.parseInt(orderTypeStr);
        }

        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;

        int totalNumber = otcTransactionPendOrderService.countOtcTransactionPendOrder(currencyId,orderType,area);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<OtcTransactionPendOrderVO> otcTransactionPendOrderList = null;
        if (totalNumber > 0) {
            otcTransactionPendOrderList = otcTransactionPendOrderService.getOtcTransactionPendOrderlist(currencyId,orderType,area,pageNumber,pageSize);
        }

        request.setAttribute("currencyId", currencyId);
        request.setAttribute("orderType", orderType);
        request.setAttribute("area", area);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("otcTransactionPendOrderList", otcTransactionPendOrderList);
    }
}
