package com.jydp.controller.sljz;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.SignatureUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DTO.TransactionBottomCurrentPriceDTO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionDealRedisService;
import config.SljzConfig;
import config.SystemCommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.TreeMap;

/**
 * Description: 获取当前价和保底价
 * Author: hht
 * Date: 2018-03-13 15:26
 */
@Controller
@RequestMapping("/sljz/getCurrentPriceAndBottomPrice")
@Scope(value = "prototype")
public class GetCurrentPriceAndBottomPriceController {

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 查询当前价和保底价 */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public @ResponseBody JSONObject transfer(HttpServletRequest request) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("data", null);

        String currencyShortName = StringUtil.stringNullHandle(request.getParameter("currencyShortName"));
        String receiveSign = StringUtil.stringNullHandle(request.getParameter("sign"));
        if (!StringUtil.isNotNull(currencyShortName) || !StringUtil.isNotNull(receiveSign)) {
            responseJson.put("code", 2);
            responseJson.put("message", "参数错误");
            return responseJson;
        }

        //验签
        TreeMap<String, String> receiveMap = new TreeMap<>();
        receiveMap.put("currencyShortName", currencyShortName);
        String receiveSignValidate = SignatureUtil.getSign(receiveMap, SljzConfig.SIGN_SECRET_KEY);
        if(!receiveSign.equals(receiveSignValidate)){
            responseJson.put("code", 3);
            responseJson.put("message", "签名错误");
            return responseJson;
        }

        TransactionCurrencyVO transactionCurrency =
                transactionCurrencyService.getTransactionCurrencyByCurrencyShortName(currencyShortName);
        if (transactionCurrency == null) {
            responseJson.put("code", 3);
            responseJson.put("message", "该交易币种不存在");
            return responseJson;
        }
        if (transactionCurrency.getUpStatus() == 1) {
            responseJson.put("code", 5);
            responseJson.put("message", "该交易币种未上线");
            return responseJson;
        }
        if (transactionCurrency.getUpStatus() == 4) {
            responseJson.put("code", 5);
            responseJson.put("message", "该交易币种已下线");
            return responseJson;
        }

        TransactionBottomPriceDTO bottomPriceDTO = transactionDealRedisService.
                getBottomPrice(transactionCurrency.getCurrencyId(), SystemCommonConfig.TRANSACTION_MAKE_ORDER);
        if (bottomPriceDTO == null) {
            responseJson.put("code", 5);
            responseJson.put("message", "查询失败");
            return responseJson;
        }

        //保底价=总价/总数量*0.7
        String div = BigDecimalUtil.div(bottomPriceDTO.getTotalPrice(), bottomPriceDTO.getTotalNumber(), 4);
        double v = Double.parseDouble(div);
        double bottomPrice = BigDecimalUtil.mul(v, 0.7);
        bottomPrice = NumberUtil.doubleFormat(bottomPrice, 4);

        double currentPrice = transactionDealRedisService.
                getCurrentPrice(transactionCurrency.getCurrencyId(), SystemCommonConfig.TRANSACTION_MAKE_ORDER);
        currentPrice = NumberUtil.doubleFormat(currentPrice, 4);

        TransactionBottomCurrentPriceDTO bottomCurrentPrice = new TransactionBottomCurrentPriceDTO();
        bottomCurrentPrice.setCurrencyShortName(transactionCurrency.getCurrencyShortName());
        bottomCurrentPrice.setKeepPrice(bottomPrice);
        bottomCurrentPrice.setCurrentPrice(currentPrice);

        responseJson.put("code", 1);
        responseJson.put("message", "查询成功");
        responseJson.put("data", bottomCurrentPrice);
        return responseJson;
    }

}
