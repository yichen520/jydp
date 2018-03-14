package com.jydp.controller.sljz;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.BigDecimalUtil;
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

        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String receiveSign = StringUtil.stringNullHandle(request.getParameter("sign"));
        if (!StringUtil.isNotNull(currencyIdStr) || !StringUtil.isNotNull(receiveSign)) {
            responseJson.put("code", 2);
            responseJson.put("message", "参数错误");
            return responseJson;
        }

        //验签
        TreeMap<String, String> receiveMap = new TreeMap<>();
        receiveMap.put("currencyId", currencyIdStr);
        String receiveSignValidate = SignatureUtil.getSign(receiveMap, SljzConfig.SIGN_SECRET_KEY);
        if(!receiveSign.equals(receiveSignValidate)){
            responseJson.put("code", 3);
            responseJson.put("message", "签名错误");
            return responseJson;
        }

        int currencyId = Integer.parseInt(currencyIdStr);

        TransactionCurrencyVO transactionCurrency =
                transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
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
                getBottomPrice(currencyId, SystemCommonConfig.TRANSACTION_MAKE_ORDER);
        if (bottomPriceDTO == null) {
            responseJson.put("code", 5);
            responseJson.put("message", "查询失败");
            return responseJson;
        }

        //保底价=总价/总数量*0.7
        String div = BigDecimalUtil.div(bottomPriceDTO.getTotalPrice(), bottomPriceDTO.getTotalNumber(), 4);
        double v = Double.parseDouble(div);
        double bottomPrice = BigDecimalUtil.mul(v, 0.7);


        double currentPrice = transactionDealRedisService.
                getCurrentPrice(currencyId, SystemCommonConfig.TRANSACTION_MAKE_ORDER);

        TransactionBottomCurrentPriceDTO bottomCurrentPrice = new TransactionBottomCurrentPriceDTO();
        bottomCurrentPrice.setCurrencyId(bottomPriceDTO.getCurrencyId());
        bottomCurrentPrice.setBottomPrice(bottomPrice);
        bottomCurrentPrice.setCurrentPrice(currentPrice);

        responseJson.put("code", 1);
        responseJson.put("message", "查询成功");
        responseJson.put("data", bottomCurrentPrice);
        return responseJson;
    }

}
