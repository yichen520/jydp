package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.*;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.entity.VO.StandardParameterVO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.entity.VO.TransactionPendOrderVO;
import com.jydp.entity.VO.UserDealCapitalMessageVO;
import com.jydp.service.*;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * wap端交易中心
 * @author njx
 */
@Controller
@RequestMapping("/userWap/tradeCenter")
@Scope(value = "prototype")
public class WapTradeCenterController {
    /**
     * 交易币种
     */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;
    /**
     * redis成交记录
     */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;
    /**
     * 用户账号
     */
    @Autowired
    private IUserService userService;
    /**
     * redis服务
     */
    @Autowired
    private IRedisService redisService;
    /**
     * 挂单记录
     */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;
    /**
     * 用户币数量
     */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;
    /**
     * 匹配交易
     */
    @Autowired
    private ITradeCommonService tradeCommonService;

    /**
     * 跳转到交易中心界面
     */
    /*@RequestMapping(value = "/show", method = RequestMethod.POST )*/
    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request) {
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyIdStr"));
        if (StringUtil.isNull(currencyIdStr)) {
            List<TransactionCurrencyVO> transactionCurrencyList = transactionCurrencyService.getOnlineAndSuspensionCurrencyForWap();
            if (transactionCurrencyList == null || transactionCurrencyList.size() <= 0) {
                return "page/wap/index";
            }
            currencyIdStr = transactionCurrencyList.get(0).getCurrencyId() + "";
        }
        request.setAttribute("currencyIdStr", currencyIdStr);
        return "page/wap/deal";
    }

    /**
     * 展示wap端交易中心界面
     */
    @RequestMapping(value = "/getWapTradeCenterInfo", method = RequestMethod.POST)
    public @ResponseBody JSONObject getWapTradeCenterInfo(HttpServletRequest request) {
        //返回对象设置
        JSONObject response = new JSONObject();
        UserDealCapitalMessageVO userDealCapitalMessage = new UserDealCapitalMessageVO();
        List<TransactionPendOrderVO> transactionPendOrderList = null;

        //参数获取和判断
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyIdStr"));
        List<TransactionCurrencyVO> transactionCurrencyList = transactionCurrencyService.getOnlineAndSuspensionCurrencyForWap();
        if (StringUtil.isNull(currencyIdStr)) {
            if (transactionCurrencyList == null || transactionCurrencyList.size() <= 0) {
                //这里返回到哪里
                response.put("code", 1);
                response.put("message", "没有上线的币种");
                return response;
            }
            currencyIdStr = transactionCurrencyList.get(0).getCurrencyId() + "";
        }

        int currencyId = Integer.parseInt(currencyIdStr);
        TransactionCurrencyVO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if (transactionCurrency == null) {
            //这里返回到哪里
            response.put("code", 1);
            response.put("message", "没有上线的币种");
            return response;
        }
        transactionCurrency.setBuyFee(transactionCurrency.getBuyFee() * 100);
        transactionCurrency.setSellFee(transactionCurrency.getSellFee() * 100);

        //查询所有币行情信息
        List<TransactionUserDealDTO> transactionUserDealList = null;
        Object transactionUserDealListObject = redisService.getValue(RedisKeyConfig.HOMEPAGE_CURRENCY_MARKET);
        if (transactionUserDealListObject != null) {
            transactionUserDealList = (List<TransactionUserDealDTO>)transactionUserDealListObject;
        }
        response.put("transactionUserDealList", transactionUserDealList);
        //获取用户交易中心相关资产
        UserSessionBO user = (UserSessionBO) request.getSession().getAttribute("userSession");
        //如果用户已登录
        if (user != null) {
            //用户可用 锁定 总资产
            userDealCapitalMessage = userService.countCheckUserAmountForTimer(user.getUserId(), currencyId);
            //十条挂单记录
            transactionPendOrderList = transactionPendOrderService.listPendOrderForWap(user.getUserId(), currencyId, 0, 10);

            //判断用户是否拥有该币种，如果没有帮助其新增
            UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(user.getUserId(), currencyId);
            if (userCurrencyNum == null) {
                UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();
                userCurrencyNumDO.setUserId(user.getUserId());
                userCurrencyNumDO.setCurrencyId(currencyId);
                userCurrencyNumDO.setCurrencyNumber(0);
                userCurrencyNumDO.setCurrencyNumberLock(0);
                userCurrencyNumDO.setAddTime(DateUtil.getCurrentTime());
                List<UserCurrencyNumDO> userCurrencyNumDOList = new ArrayList<>();
                userCurrencyNumDOList.add(userCurrencyNumDO);
                userCurrencyNumService.insertUserCurrencyForWap(userCurrencyNumDOList);
            }
            UserDO userDO = userService.getUserByUserId(user.getUserId());
            response.put("userIdPwd", user.getIsPwd());
            response.put("payPasswordStatus", userDO.getPayPasswordStatus());
            response.put("userSession", user);
        }

        //该币种基准信息 买一价 买一价 今日最高 今日最低 今日涨幅 昨日收盘 今日成交量
        StandardParameterVO standardParameter = transactionCurrencyService.listTransactionCurrencyAll(currencyId);
        //获取成交记录
        List<TransactionDealRedisDO> dealList = null;
        Object dealListStr = redisService.getValue(RedisKeyConfig.CURRENCY_DEAL_KEY + currencyId);
        if (dealListStr != null && StringUtil.isNotNull(dealListStr.toString())) {
            dealList = (List<TransactionDealRedisDO>) dealListStr;
        }
        if (dealList == null || dealList.isEmpty()) {
            dealList = transactionDealRedisService.listTransactionDealRedis(50, transactionCurrency.getCurrencyId());
        }
        //挂单记录 买入
        List<TransactionPendOrderDTO> transactionPendOrderBuyList = null;
        Object transactionPendOrderBuyListStr = redisService.getValue(RedisKeyConfig.BUY_KEY + transactionCurrency.getCurrencyId());
        if (transactionPendOrderBuyListStr != null && StringUtil.isNotNull(transactionPendOrderBuyListStr.toString())) {
            transactionPendOrderBuyList = (List<TransactionPendOrderDTO>) transactionPendOrderBuyListStr;
        }
        if (transactionPendOrderBuyList == null || transactionPendOrderBuyList.isEmpty()) {
            transactionPendOrderBuyList = transactionPendOrderService.listLatestRecords(1, transactionCurrency.getCurrencyId(), 15);
        }
        //挂单记录 卖出
        List<TransactionPendOrderDTO> transactionPendOrderSellList = null;
        Object transactionPendOrderSellListStr = redisService.getValue(RedisKeyConfig.SELL_KEY + transactionCurrency.getCurrencyId());
        if (transactionPendOrderSellListStr != null && StringUtil.isNotNull(transactionPendOrderSellListStr.toString())) {
            transactionPendOrderSellList = (List<TransactionPendOrderDTO>) transactionPendOrderSellListStr;
        }
        if (transactionPendOrderSellList == null || transactionPendOrderSellList.isEmpty()) {
            transactionPendOrderSellList = transactionPendOrderService.listLatestRecords(2, transactionCurrency.getCurrencyId(), 15);
        }

        //查询成功需要加入项目名称
        String webAppPath = request.getServletContext().getContextPath();
        //设置返回值
        response.put("webAppPath", webAppPath);

        //返回参数设置
        response.put("transactionPendOrderBuyList", transactionPendOrderBuyList);
        response.put("transactionPendOrderSellList", transactionPendOrderSellList);
        response.put("dealList", dealList);
        response.put("transactionPendOrderList", transactionPendOrderList);
        response.put("transactionCurrency", transactionCurrency);
        response.put("standardParameter", standardParameter);
        response.put("userDealCapitalMessage", userDealCapitalMessage);
        response.put("code", 0);
        response.put("message", "查询成功");
        return response;
    }

    /**
     * 卖出
     */
    @RequestMapping(value = "/sell.htm", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject sell(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if (userSession == null) {
            response.put("code", 4);
            response.put("message", "未登录");
            return response;
        }

        //获取参数
        String sellPriceStr = StringUtil.stringNullHandle(request.getParameter("sellPrice"));
        String sellNumStr = StringUtil.stringNullHandle(request.getParameter("sellNum"));
        String sellPwd = StringUtil.stringNullHandle(request.getParameter("sellPwd"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));

        double sellPrice = 0;
        if (StringUtil.isNotNull(sellPriceStr)) {
            sellPrice = Double.parseDouble(sellPriceStr);
        }
        double sellNum = 0;
        if (StringUtil.isNotNull(sellNumStr)) {
            sellNum = Double.parseDouble(sellNumStr);
        }
        int currencyId = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        //获取用户信息
        UserDO user = userService.getUserByUserId(userSession.getUserId());
        if (user == null) {
            response.put("code", 3);
            response.put("message", "用户不存在");
            return response;
        }
        if (user.getAccountStatus() != 1) {
            response.put("code", 3);
            response.put("message", "该账号被禁用");
            return response;
        }

        //获取当前币种
        TransactionCurrencyVO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if (transactionCurrency == null) {
            response.put("code", 3);
            response.put("message", "币种信息获取失败,请稍候再试");
            return response;
        }
        if (transactionCurrency.getUpStatus() == 4) {
            response.put("code", 4);
            response.put("message", "该币种已下线");
            return response;
        }
        if (transactionCurrency.getPaymentType() != 1) {
            response.put("code", 4);
            response.put("message", "该币种不在交易状态");
            return response;
        }
        UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(user.getUserId(), currencyId);
        boolean result = true;
        if (userCurrencyNum == null) {
            UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();
            userCurrencyNumDO.setUserId(user.getUserId());
            userCurrencyNumDO.setCurrencyId(currencyId);
            userCurrencyNumDO.setCurrencyNumber(0);
            userCurrencyNumDO.setCurrencyNumberLock(0);
            userCurrencyNumDO.setAddTime(DateUtil.getCurrentTime());

            result = userCurrencyNumService.insertUserCurrencyNum(userCurrencyNumDO);
        }
        if (!result) {
            response.put("code", 3);
            response.put("message", "参数错误");
            return response;
        }

        //判断交易时间
        boolean timeBoo = DateUtil.isTradeTime();
        if (timeBoo) {
            response.put("code", 3);
            response.put("message", "不在交易时间段内");
            return response;
        }
        //交易数量限制
        if (sellNum <= 0) {
            response.put("code", 3);
            response.put("message", "交易数量不能小于等于0");
            return response;
        }
        //交易价格限制
        if (sellPrice <= 0) {
            response.put("code", 3);
            response.put("message", "交易单价不能小于等于0");
            return response;
        }
        //判断是否需要验证交易密码以及验证交易密码
        int isPwd = userSession.getIsPwd();
        int payPasswordStatus = user.getPayPasswordStatus();
        if (payPasswordStatus == 1 || (payPasswordStatus == 2 && isPwd == 1)) {
            sellPwd = MD5Util.toMd5(sellPwd);
            boolean checkResult = userService.validateUserPay(user.getUserAccount(), sellPwd);
            if (!checkResult) {
                response.put("code", 4);
                response.put("message", "支付密码错误");
                return response;
            }
            //修改session中是否输入过密码标识
            if (payPasswordStatus == 2 && isPwd == 1) {
                userSession.setIsPwd(2);
                request.getSession().setAttribute("userSession", userSession);
                response.put("userIdPwd", 2);
                ;
            }
        }

        if (userCurrencyNum.getCurrencyNumber() < sellNum) {
            response.put("code", 5);
            response.put("message", "用户币不足");
            return response;
        }

        //挂单
        TransactionPendOrderDO transactionPendOrder = transactionPendOrderService.insertPendOrder(user.getUserId(), 2, currencyId,
                transactionCurrency.getCurrencyName(), 0, sellPrice, sellNum, 0);
        if (transactionPendOrder == null) {
            response.put("code", 2);
            response.put("message", "挂单失败");
            return response;
        }

        //从redis判断是否可以匹配交易
        Object buyOneOb = redisService.getValue(RedisKeyConfig.BUY_ONE_KEY + currencyId);
        double buyOne = 0;
        if (buyOneOb != null && buyOneOb != "") {
            buyOne = (double) buyOneOb;
        }
        if (buyOne < sellPrice) {
            response.put("code", 1);
            response.put("message", "挂单成功");
            return response;
        }

        JsonObjectBO trade = tradeCommonService.trade(transactionPendOrder);
        response.put("code", trade.getCode());
        response.put("message", trade.getMessage());
        return response;
    }

    /**
     * 买入
     */
    @RequestMapping(value = "/buy.htm", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject buy(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if (userSession == null) {
            response.put("code", 4);
            response.put("message", "未登录");
            return response;
        }

        //获取参数
        String buyPriceStr = StringUtil.stringNullHandle(request.getParameter("buyPrice"));
        String buyNumStr = StringUtil.stringNullHandle(request.getParameter("buyNum"));
        String buyPwd = StringUtil.stringNullHandle(request.getParameter("buyPwd"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));

        double buyPrice = 0;
        if (StringUtil.isNotNull(buyPriceStr)) {
            buyPrice = Double.parseDouble(buyPriceStr);
        }

        double buyNum = 0;
        if (StringUtil.isNotNull(buyNumStr)) {
            buyNum = Double.parseDouble(buyNumStr);
        }
        int currencyId = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        //获取用户信息
        UserDO user = userService.getUserByUserId(userSession.getUserId());
        if (user == null) {
            response.put("code", 3);
            response.put("message", "用户不存在");
            return response;
        }
        if (user.getAccountStatus() != 1) {
            response.put("code", 3);
            response.put("message", "该账号被禁用");
            return response;
        }

        //获取当前币种
        TransactionCurrencyVO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if (transactionCurrency == null) {
            response.put("code", 3);
            response.put("message", "币种信息获取失败,请稍候再试");
            return response;
        }
        if (transactionCurrency.getUpStatus() == 4) {
            response.put("code", 4);
            response.put("message", "该币种已下线");
            return response;
        }
        if (transactionCurrency.getPaymentType() != 1) {
            response.put("code", 4);
            response.put("message", "该币种不在交易状态");
            return response;
        }
        UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(user.getUserId(), currencyId);
        boolean result = true;
        if (userCurrencyNum == null) {
            UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();
            userCurrencyNumDO.setUserId(user.getUserId());
            userCurrencyNumDO.setCurrencyId(currencyId);
            userCurrencyNumDO.setCurrencyNumber(0);
            userCurrencyNumDO.setCurrencyNumberLock(0);
            userCurrencyNumDO.setAddTime(DateUtil.getCurrentTime());

            result = userCurrencyNumService.insertUserCurrencyNum(userCurrencyNumDO);
        }
        if (!result) {
            response.put("code", 3);
            response.put("message", "参数错误");
            return response;
        }

        //判断交易时间
        boolean timeBoo = DateUtil.isTradeTime();
        if (timeBoo) {
            response.put("code", 3);
            response.put("message", "不在交易时间段内");
            return response;
        }
        //交易数量限制
        if (buyNum <= 0) {
            response.put("code", 3);
            response.put("message", "交易数量不能小于等于0");
            return response;
        }
        //交易价格限制
        if (buyPrice <= 0) {
            response.put("code", 3);
            response.put("message", "交易单价不能小于等于0");
            return response;
        }

        //判断是否需要验证交易密码以及验证交易密码
        int isPwd = userSession.getIsPwd();
        int payPasswordStatus = user.getPayPasswordStatus();
        if (payPasswordStatus == 1 || (payPasswordStatus == 2 && isPwd == 1)) {
            buyPwd = MD5Util.toMd5(buyPwd);
            boolean checkResult = userService.validateUserPay(user.getUserAccount(), buyPwd);
            if (!checkResult) {
                response.put("code", 4);
                response.put("message", "支付密码错误");
                return response;
            }
            //修改session中是否输入过密码标识
            if (payPasswordStatus == 2 && isPwd == 1) {
                userSession.setIsPwd(2);
                request.getSession().setAttribute("userSession", userSession);
                response.put("userIdPwd", 2);
                ;
            }
        }

        //计算手续费及总价
        double buyFee = transactionCurrency.getBuyFee();
        double sumPrice = NumberUtil.doubleUpFormat(BigDecimalUtil.mul(BigDecimalUtil.mul(buyNum, buyPrice), BigDecimalUtil.add(1, buyFee)), 6);
        if (user.getUserBalance() < sumPrice) {
            response.put("code", 5);
            response.put("message", "用户余额不足");
            return response;
        }
        //挂单
        TransactionPendOrderDO transactionPendOrder = transactionPendOrderService.insertPendOrder(user.getUserId(), 1, currencyId,
                transactionCurrency.getCurrencyName(), buyFee, buyPrice, buyNum, sumPrice);
        if (transactionPendOrder == null) {
            response.put("code", 2);
            response.put("message", "挂单失败");
            return response;
        }

        //从redis判断是否可以匹配交易
        Object sellOneOb = redisService.getValue(RedisKeyConfig.SELL_ONE_KEY + currencyId);
        double sellOne = 0;
        if (sellOneOb != null && sellOneOb != "") {
            sellOne = (double) sellOneOb;
        }
        if (sellOne > buyPrice || sellOne == 0) {
            response.put("code", 1);
            response.put("message", "挂单成功");
            return response;
        }

        JsonObjectBO trade = tradeCommonService.trade(transactionPendOrder);
        response.put("code", trade.getCode());
        response.put("message", trade.getMessage());
        return response;
    }
}


