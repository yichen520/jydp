package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.*;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcDealerUserDO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.otc.UserPaymentTypeDO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.entity.DTO.UserPaymentTypeDTO;
import com.jydp.entity.VO.OtcTransactionPendOrderVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.*;
import config.FileUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * 场外交易模块 交易
 * @author zym
 *
 */
@Controller
@RequestMapping("/userWap/otcTradeCenter")
@Scope(value="prototype")
public class WapOtcTradeCenterController {

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


    /** 展示页面 */
    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request){
        list(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功!");
        return "page/wap/tradeOut";
    }

    /** 查询数据 */
    public void list(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = otcTransactionPendOrderService.countOtcTransactionPendOrder(0,0,null);
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<OtcTransactionPendOrderVO> otcTransactionPendOrderList = null;
        if (totalNumber > 0) {
            otcTransactionPendOrderList = otcTransactionPendOrderService.getOtcTransactionPendOrderlist(0,0,null,pageNumber,pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("otcTransactionPendOrderList", otcTransactionPendOrderList);
    }

    /** 查看更多*/
    @RequestMapping(value="/showMore", method=RequestMethod.POST)
    public @ResponseBody JsonObjectBO showMore(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }
        int pageSize = 20;
        int totalNumber = otcTransactionPendOrderService.countOtcTransactionPendOrder(0,0,null);
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<OtcTransactionPendOrderVO> otcTransactionPendOrderList = null;
        if (totalNumber > 0) {
            otcTransactionPendOrderList = otcTransactionPendOrderService.getOtcTransactionPendOrderlist(0,0,null,pageNumber,pageSize);
        }
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("totalNumber", totalNumber);
        jsonObject.put("totalPageNumber", totalPageNumber);
        jsonObject.put("pageNumber", pageNumber);
        jsonObject.put("otcTransactionPendOrderList", otcTransactionPendOrderList);
        responseJson.setData(jsonObject);
        responseJson.setCode(1);
        responseJson.setMessage("查询成功");
        return responseJson;
    }

    /** 用户去交易*/
    @RequestMapping(value = "/transaction.htm")
    public String transaction(HttpServletRequest request) {
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if(userSession == null){
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录");
            return "page/wap/login";
        }

        boolean fq = UserWebInterceptor.handleFrequent(request);
        if(fq){
            request.setAttribute("code", 3);
            request.setAttribute("message", "用户操作频繁");
            list(request);
            return "page/wap/tradeOut";
        }
        //获取参数
        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        if (!StringUtil.isNotNull(otcPendingOrderNo)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }
        //获取挂单信息
        OtcTransactionPendOrderVO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrder(otcPendingOrderNo);
        if(otcTransactionPendOrder == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }

        if(otcTransactionPendOrder.getPendingStatus() == -1){
            request.setAttribute("code", 2);
            request.setAttribute("message", "该广告已被删除");
            return "page/wap/tradeOut";
        }

        //经销商出售XT（用户买XT） 显示经销商收款方式
        if(otcTransactionPendOrder.getOrderType() == 1){
            if(!StringUtil.isNotNull(userIdStr)){
                request.setAttribute("code", 2);
                request.setAttribute("message", "参数错误");
                return "page/wap/tradeOut";
            }

            int userId = 0;
            if (StringUtil.isNotNull(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
            //根据用户id、挂单号查询 收款记录列表
            List<UserPaymentTypeDO> userPaymentType = userPaymentTypeService.listUserPaymentType(userId, otcPendingOrderNo);
            if(userPaymentType == null || userPaymentType.size() == 0){
                request.setAttribute("code", 2);
                request.setAttribute("message", "参数错误");
                return "page/wap/tradeOut";
            }

            int hasBank = 0;
            int hasWeiXin = 0;
            int hasAliPay = 0;
            for (UserPaymentTypeDO paymentType : userPaymentType) {
                if(paymentType.getPaymentType() == 1){
                    hasBank = 1;
                }else if(paymentType.getPaymentType() == 2){
                    hasAliPay = 1;
                }else if(paymentType.getPaymentType() == 3){
                    hasWeiXin = 1;
                }
            }

            request.setAttribute("hasBank", hasBank);
            request.setAttribute("hasWeiXin", hasWeiXin);
            request.setAttribute("hasAliPay", hasAliPay);
            request.setAttribute("otcTransactionPendOrder", otcTransactionPendOrder);
            return "page/wap/userBuy";
        }

        request.setAttribute("otcTransactionPendOrder", otcTransactionPendOrder);
        return "page/wap/userSell";
    }

    /** 用户购买XT详情页面*/
    @RequestMapping(value = "/userBuyDetail.htm", method = RequestMethod.POST)
    public String userBuyDetail(HttpServletRequest request) {

        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if(userSession == null){
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录");
            return "page/wap/login";
        }

        boolean fq = UserWebInterceptor.handleFrequent(request);
        if(fq){
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户操作频繁");
            list(request);
            return  "page/wap/tradeOut";
        }
        //获取参数
        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        String buyNumStr = StringUtil.stringNullHandle(request.getParameter("buyNum"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        if(!StringUtil.isNotNull(otcPendingOrderNo) || !StringUtil.isNotNull(buyNumStr)
                || !StringUtil.isNotNull(paymentTypeStr) || !StringUtil.isNotNull(userIdStr)){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            list(request);
            return "page/wap/tradeOut";
        }

        int buyNum = 0;
        if (StringUtil.isNotNull(buyNumStr)) {
            buyNum = Integer.parseInt(buyNumStr);
        }
        int paymentType = 0;
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }
        int userId = 0;
        if (StringUtil.isNotNull(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }

        //获取挂单信息
        OtcTransactionPendOrderVO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrder(otcPendingOrderNo);
        if(otcTransactionPendOrder.getOrderType() != 1){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            list(request);
            return "page/wap/tradeOut";
        }

        if(otcTransactionPendOrder == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            list(request);
            return "page/wap/tradeOut";
        }

        if(otcTransactionPendOrder.getPendingStatus() == -1){
            request.setAttribute("code", 2);
            request.setAttribute("message", "该广告已被删除");
            list(request);
            return "page/wap/tradeOut";
        }
        //经销商信息（名称）
        OtcDealerUserDO otcDealerUserDO = otcDealerUserService.getOtcDealerUserByUserId(userId);
        if(otcDealerUserDO == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            list(request);
            return "page/wap/tradeOut";
        }
        String dealerName = otcDealerUserDO.getDealerName();

        //经销商手机号码
        UserDO userDO = userService.getUserByUserId(userId);
        if(userDO == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            list(request);
            return "page/wap/tradeOut";
        }
        String phoneNumber = userDO.getPhoneNumber();

        //查询用户信息(用户姓名)
        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationByUserIdLately(otcTransactionPendOrder.getUserId());
        if(userIdentification == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            list(request);
            return "page/wap/tradeOut";
        }
        if(userIdentification.getIdentificationStatus() != 2){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            list(request);
            return "page/wap/tradeOut";
        }
        String userName = userIdentification.getUserName();

        //根据用户id、挂单号查询 收款记录列表
        List<UserPaymentTypeDO> userPaymentTypeList = userPaymentTypeService.listUserPaymentType(userId, otcPendingOrderNo);
        if(userPaymentTypeList == null || userPaymentTypeList.size() == 0){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            list(request);
            return "page/wap/tradeOut";
        }

        for(UserPaymentTypeDO userPaymentType : userPaymentTypeList){
            if(userPaymentType.getPaymentType() == paymentType){
                request.setAttribute("phoneNumber", phoneNumber);
                request.setAttribute("userName", userName);
                request.setAttribute("dealerName", dealerName);

                request.setAttribute("userPaymentType", userPaymentType);
                request.setAttribute("otcTransactionPendOrder", otcTransactionPendOrder);
                request.setAttribute("otcDealerUserDO", otcDealerUserDO);
                request.setAttribute("buyNum", buyNum);
                request.setAttribute("paymentType", paymentType);
                return "page/wap/userBuyDetail";
            }
        }

        request.setAttribute("code", 2);
        request.setAttribute("message", "参数错误");
        list(request);
        return "page/wap/tradeOut";
    }

    /** 用户购买(用户购买XT 经销商出售XT)*/
    @RequestMapping(value = "/buy.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO buy(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if(userSession == null){
            resultJson.setCode(4);
            resultJson.setMessage("未登录");
            return resultJson;
        }

        boolean fq = UserWebInterceptor.handleFrequent(request);
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
        OtcTransactionPendOrderVO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrder(otcPendingOrderNo);
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

    /** 用户出售XT详情页面*/
    @RequestMapping(value = "/userSellDetail.htm", method = RequestMethod.POST)
    public String userSellDetail(HttpServletRequest request) {
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if (userSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录");
            return "page/wap/login";
        }

        boolean fq = UserWebInterceptor.handleFrequent(request);
        if (fq) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户操作频繁");
            return "page/wap/tradeOut";
        }
        //获取参数
        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        String sellNumStr = StringUtil.stringNullHandle(request.getParameter("sellNum"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        if (!StringUtil.isNotNull(otcPendingOrderNo) || !StringUtil.isNotNull(sellNumStr)
                || !StringUtil.isNotNull(paymentTypeStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }

        int paymentType = 0;
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }
        int sellNum = 0;
        if (StringUtil.isNotNull(sellNumStr)) {
            sellNum = Integer.parseInt(sellNumStr);
        }
        //支付方式判断
        if (paymentType <= 0) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        } else if (paymentType == 1) {
            //获取银行卡参数
            String paymentAccount = StringUtil.stringNullHandle(request.getParameter("paymentAccount"));
            String bankName = StringUtil.stringNullHandle(request.getParameter("bankName"));
            String bankBranch = StringUtil.stringNullHandle(request.getParameter("bankBranch"));
            String paymentName = StringUtil.stringNullHandle(request.getParameter("paymentName"));
            String paymentPhone = StringUtil.stringNullHandle(request.getParameter("paymentPhone"));

            if (!StringUtil.isNotNull(paymentAccount) || !StringUtil.isNotNull(bankName) || !StringUtil.isNotNull(bankBranch)
                    || !StringUtil.isNotNull(paymentName) || !StringUtil.isNotNull(paymentPhone)) {
                request.setAttribute("code", 2);
                request.setAttribute("message", "参数错误");
                return "page/wap/tradeOut";
            }

            request.setAttribute("paymentAccount", paymentAccount);
            request.setAttribute("bankName", bankName);
            request.setAttribute("bankBranch", bankBranch);
            request.setAttribute("paymentName", paymentName);
            request.setAttribute("paymentPhone", paymentPhone);

        } else if (paymentType == 2) {
            //支付宝参数
            String alipayPaymentAccount = StringUtil.stringNullHandle(request.getParameter("alipayPaymentAccount"));

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile paymentImage = multipartRequest.getFile("alipayPaymentImage");

            if (paymentImage == null || paymentImage.isEmpty() || paymentImage.getSize() <= 0 || !StringUtil.isNotNull(alipayPaymentAccount)) {
                request.setAttribute("code", 2);
                request.setAttribute("message", "参数错误");
                return "page/wap/tradeOut";
            }
            String imageUrl = "";
            imageUrl = ImageReduceUtil.reduceImageUploadRemote(paymentImage, FileUrlConfig.file_remote_qeCodeImage_url);
            if(imageUrl == null || imageUrl == ""){
                request.setAttribute("code", 2);
                request.setAttribute("message", "图片上传失败");
                return "page/wap/tradeOut";
            }
            String imageUrlFormat = FileUrlConfig.file_visit_url + imageUrl;
            request.setAttribute("paymentAccount", alipayPaymentAccount);
            request.setAttribute("imageUrl", imageUrl);
            request.setAttribute("imageUrlFormat", imageUrlFormat);

        }else if(paymentType == 3){
            //微信参数
            String wechatPaymentAccount = StringUtil.stringNullHandle(request.getParameter("wechatPaymentAccount"));
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile paymentImage = multipartRequest.getFile("wechatPaymentImage");
            if (paymentImage == null || paymentImage.isEmpty() || paymentImage.getSize() <= 0 || !StringUtil.isNotNull(wechatPaymentAccount)) {
                request.setAttribute("code", 2);
                request.setAttribute("message", "参数错误");
                return "page/wap/tradeOut";
            }

            String imageUrl = "";
            imageUrl = ImageReduceUtil.reduceImageUploadRemote(paymentImage, FileUrlConfig.file_remote_qeCodeImage_url);
            if(imageUrl == null || imageUrl == ""){
                request.setAttribute("code", 2);
                request.setAttribute("message", "图片上传失败");
                return "page/wap/tradeOut";
            }
            String imageUrlFormat = FileUrlConfig.file_visit_url + imageUrl;
            request.setAttribute("paymentAccount", wechatPaymentAccount);
            request.setAttribute("imageUrlFormat", imageUrlFormat);
            request.setAttribute("imageUrl", imageUrl);

        }

        //获取挂单信息
        OtcTransactionPendOrderVO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrder(otcPendingOrderNo);
        if(otcTransactionPendOrder.getOrderType() != 2){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }

        if(otcTransactionPendOrder == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }

        if(otcTransactionPendOrder.getPendingStatus() == -1){
            request.setAttribute("code", 2);
            request.setAttribute("message", "该广告已被删除");
            return "page/wap/tradeOut";
        }

        int userId = otcTransactionPendOrder.getUserId();
        //查询用户信息(用户姓名)
        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationByUserIdLately(otcTransactionPendOrder.getUserId());
        if(userIdentification == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }
        if(userIdentification.getIdentificationStatus() != 2){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }
        String userName = userIdentification.getUserName();
        String phoneNumber = userIdentification.getUserPhone();

        request.setAttribute("otcTransactionPendOrder", otcTransactionPendOrder);
        request.setAttribute("sellNum", sellNum);
        request.setAttribute("paymentType", paymentType);
        request.setAttribute("userName", userName);
        request.setAttribute("phoneNumber", phoneNumber);
        return "page/wap/userSellDetail";
    }


    /** 用户去交易*/
    @RequestMapping(value = "/backSell.htm", method = RequestMethod.POST)
    public String backSell(HttpServletRequest request) {
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if(userSession == null){
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录");
            return "page/wap/login";
        }

        boolean fq = UserWebInterceptor.handleFrequent(request);
        if(fq){
            request.setAttribute("code", 3);
            request.setAttribute("message", "用户操作频繁");
            return "page/wap/tradeOut";
        }
        //获取参数
        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        String imageUrl = StringUtil.stringNullHandle(request.getParameter("imageUrl"));
        if (!StringUtil.isNotNull(otcPendingOrderNo) || !StringUtil.isNotNull(imageUrl)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }
        //获取挂单信息
        OtcTransactionPendOrderVO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrder(otcPendingOrderNo);
        if(otcTransactionPendOrder == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }

        if(otcTransactionPendOrder.getPendingStatus() == -1){
            request.setAttribute("code", 2);
            request.setAttribute("message", "该广告已被删除");
            return "page/wap/tradeOut";
        }

        FileWriteRemoteUtil.deleteFile(imageUrl);

        request.setAttribute("otcTransactionPendOrder", otcTransactionPendOrder);
        return "page/wap/userSell";
    }

    /** 出售回购单 (用户出售XT 经销商购买XT)*/
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
        }else if(paymentType == 2){
            //支付宝参数
            paymentAccount = StringUtil.stringNullHandle(request.getParameter("alipayPaymentAccount"));
            imageUrl = StringUtil.stringNullHandle(request.getParameter("imageUrl"));
            if (!StringUtil.isNotNull(imageUrl) || !StringUtil.isNotNull(paymentAccount)) {
                resultJson.setCode(2);
                resultJson.setMessage("参数错误");
                return resultJson;
            }
            userPaymentType.setPaymentAccount(paymentAccount);
            userPaymentType.setPaymentImage(imageUrl);
        }else if(paymentType == 3){
            //获取微信
            paymentAccount = StringUtil.stringNullHandle(request.getParameter("wechatPaymentAccount"));
            imageUrl = StringUtil.stringNullHandle(request.getParameter("imageUrl"));
            if (!StringUtil.isNotNull(imageUrl) || !StringUtil.isNotNull(paymentAccount)) {
                resultJson.setCode(2);
                resultJson.setMessage("参数错误");
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
}
