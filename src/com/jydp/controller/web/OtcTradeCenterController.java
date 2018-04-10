package com.jydp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.*;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import com.jydp.entity.VO.*;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.*;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

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

        //获取参数
        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        String buyNumStr = StringUtil.stringNullHandle(request.getParameter("buyNum"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));


        double buyNum = 0;
        if (StringUtil.isNotNull(buyNumStr)) {
            buyNum = Double.parseDouble(buyNumStr);
        }
        int paymentType = 0;
        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
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
        if(otcTransactionPendOrder == null){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

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


        resultJson.setCode(1);
        resultJson.setMessage("下单成功");
        return resultJson;
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

        List<OtcTransactionPendOrderDO> otcTransactionPendOrderList = null;
        if (totalNumber > 0) {
            otcTransactionPendOrderList = otcTransactionPendOrderService.getOtcTransactionPendOrderlist(currencyId,orderType,area,pageNumber,pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("otcTransactionPendOrderList", otcTransactionPendOrderList);
    }
}
