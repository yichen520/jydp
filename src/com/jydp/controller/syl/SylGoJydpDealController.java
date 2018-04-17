package com.jydp.controller.syl;


import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.SignatureUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.syl.SylToJydpChainDO;
import com.jydp.entity.DO.syl.SylUserBoundDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.ISylToJydpChainService;
import com.jydp.service.ISylUserBoundService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.IUserService;
import config.SylConfig;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.TreeMap;

/**
 * SYL转账盛源链(SYL-->JYDP)
 *
 * @author sy
 */
@Controller
@Scope(value = "prototype")
public class SylGoJydpDealController {

    /** SYL转账盛源链记录(SYL-->JYDP) */
    @Autowired
    private ISylToJydpChainService sylToJydpChainService;

    /** 用户管理 */
    @Autowired
    private IUserService userService;

    /** 盛源链账号绑定 */
    @Autowired
    private ISylUserBoundService sylUserBoundService;

    /** 交易币种管理*/
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 接收SYL转账盛源链请求 */
    @RequestMapping(value = "/rechargeCoin", method = RequestMethod.POST)
    public @ResponseBody JSONObject rechargeCoin(@RequestBody String requestJsonString) {
        JSONObject responseJson = new JSONObject();
        JSONObject requestJson = null;
        try {
            requestJson = JSONObject.parseObject(requestJsonString);
        } catch (Exception e) {
            responseJson.put("code", 3);
            responseJson.put("message", "JSON格式错误");
            return responseJson;
        }

        String orderNo = StringUtil.stringNullHandle(requestJson.getString("orderNo"));
        String walletUserAccount = StringUtil.stringNullHandle(requestJson.getString("walletUserAccount"));
        String userAccount = StringUtil.stringNullHandle(requestJson.getString("userAccount"));
        String coinStr = StringUtil.stringNullHandle(requestJson.getString("coin"));
        String coinType = StringUtil.stringNullHandle(requestJson.getString("coinType"));
        String orderTimeStr = StringUtil.stringNullHandle(requestJson.getString("orderTime"));
        String finishTimeStr = StringUtil.stringNullHandle(requestJson.getString("finishTime"));
        String key = StringUtil.stringNullHandle(requestJson.getString("key"));

        if (!StringUtil.isNotNull(orderNo) || !StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(coinStr) || !StringUtil.isNotNull(walletUserAccount)
                || !StringUtil.isNotNull(coinType) || !StringUtil.isNotNull(key) || !StringUtil.isNotNull(orderTimeStr) || !StringUtil.isNotNull(finishTimeStr)) {
            responseJson.put("code", 2);
            responseJson.put("message", "参数错误");
            return responseJson;
        }

        //签名验证
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("orderNo", orderNo);
        map.put("walletUserAccount", walletUserAccount);
        map.put("userAccount", userAccount);
        map.put("coin", coinStr);
        map.put("coinType", coinType);
        map.put("orderTime", orderTimeStr);
        map.put("finishTime", finishTimeStr);

        String signature = SignatureUtil.getSign(map, SylConfig.SIGN_SECRET_KEY);
        if(!key.equals(signature)){
            responseJson.put("code", 3);
            responseJson.put("message", "签名错误");
            return responseJson;
        }

        //判定是否存在该账号
        UserDO user = userService.getUserByUserAccount(userAccount);
        if(user == null){
            responseJson.put("code", 201);
            responseJson.put("message", "请前往Exchange Technology（EXT）进行注册");
            return responseJson;
        }

        SylUserBoundDO sylUserBound = sylUserBoundService.getSylUserBoundByUserId(user.getUserId());
        if(sylUserBound == null){
            responseJson.put("code", 202);
            responseJson.put("message", "未查询到相关绑定信息");
            return responseJson;
        }

        if(!sylUserBound.getUserSylAccount().equals(walletUserAccount)){
            responseJson.put("code", 205);
            responseJson.put("message", "绑定信息不正确");
            return responseJson;
        }

        TransactionCurrencyVO transactionCurrency = new TransactionCurrencyVO();
        if(UserBalanceConfig.DOLLAR.equals(coinType)){
            transactionCurrency.setCurrencyId(UserBalanceConfig.DOLLAR_ID);
            transactionCurrency.setCurrencyName(UserBalanceConfig.DOLLAR);
        } else {
            transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyShortName(coinType);//查询币种信息
            if(transactionCurrency == null){
                responseJson.put("code", 206);
                responseJson.put("message", "币种信息不正确");
                return responseJson;
            }
        }

        SylToJydpChainDO sylToJydpChain = sylToJydpChainService.getSylToJydpChainBysylRecordNo(orderNo, transactionCurrency.getCurrencyId());
        if(sylToJydpChain != null){
            responseJson.put("code", 203);
            responseJson.put("message", "该订单已存在");
            return responseJson;
        }

        double coin = Double.parseDouble(coinStr);
        Timestamp orderTime = Timestamp.valueOf(orderTimeStr);
        Timestamp finishTime = Timestamp.valueOf(finishTimeStr);
        boolean operation = sylToJydpChainService.operationSylToJydpChain(orderNo, user.getUserId(), userAccount, walletUserAccount, coin, coinType, orderTime, finishTime,
                transactionCurrency.getCurrencyId(), transactionCurrency.getCurrencyName());
        if(!operation){
            responseJson.put("code", 204);
            responseJson.put("message", "转账失败");
            return responseJson;
        }

        responseJson.put("code", 1);
        responseJson.put("message", "转账成功");
        return responseJson;
    }
}
