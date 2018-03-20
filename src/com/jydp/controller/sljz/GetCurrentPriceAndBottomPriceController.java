package com.jydp.controller.sljz;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.SignatureUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.transaction.TransactionCurrencyCoefficientDO;
import com.jydp.entity.DTO.TransactionBottomCurrentPriceDTO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.ITransactionCurrencyCoefficientService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionDealRedisService;
import com.jydp.service.ITransactionStatisticsService;
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

    /** 交易统计记录表 */
    @Autowired
    private ITransactionStatisticsService transactionStatisticsService;

    /**
     * 币种系数
     */
    @Autowired
    private ITransactionCurrencyCoefficientService transactionCurrencyCoefficientService;

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

        //获取该币种最近的系数
        double todayRatio = 0.8;
        TransactionCurrencyCoefficientDO currencyCoefficient = transactionCurrencyCoefficientService.getCurrencyCoefficientByCurrencyId(transactionCurrency.getCurrencyId());
        if (currencyCoefficient != null && currencyCoefficient.getCurrencyCoefficient() > 0) {
            todayRatio = currencyCoefficient.getCurrencyCoefficient();
        }

        //查询当日成交总价，当日成交总数量
        TransactionBottomPriceDTO bottomPriceToday = transactionDealRedisService.
                getBottomPriceToday(transactionCurrency.getCurrencyId(), SystemCommonConfig.TRANSACTION_MAKE_ORDER);
        if (bottomPriceToday == null) {
            bottomPriceToday = new TransactionBottomPriceDTO();
            bottomPriceToday.setCurrencyId(transactionCurrency.getCurrencyId());
            bottomPriceToday.setTotalNumber(0);
            bottomPriceToday.setTotalPrice(0);
        }

        //查询历史当日总价 * 历史当日系数，历史当日成交总数量
        TransactionBottomPriceDTO bottomPricePast = transactionStatisticsService.getBottomPricePast(transactionCurrency.getCurrencyId());
        if (bottomPricePast == null) {
            bottomPricePast = new TransactionBottomPriceDTO();
            bottomPricePast.setCurrencyId(transactionCurrency.getCurrencyId());
            bottomPricePast.setTotalNumber(0);
            bottomPricePast.setTotalPrice(0);
        }

        //总价:(历史当日总价 * 历史当日系数 + 当日总价*当日总系数)
        double totalPrice = BigDecimalUtil.add(bottomPricePast.getTotalPrice(), BigDecimalUtil.mul(bottomPriceToday.getTotalPrice(), todayRatio));
        //总数量:(历史成交总数量 + 当日成交总数量)
        double totalNumber = BigDecimalUtil.add(bottomPricePast.getTotalNumber(), bottomPriceToday.getTotalNumber());

        //保底价 = (历史当日总价 * 历史当日系数 + 当日总价*当日总系数)/(历史成交总数量 + 当日成交总数量)
        double bottomPrice = 0;
        String bottomPriceStr = BigDecimalUtil.div(totalPrice, totalNumber, 8);
        if (StringUtil.isNotNull(bottomPriceStr)) {
            bottomPrice = Double.parseDouble(bottomPriceStr);
            bottomPrice = NumberUtil.doubleFormat(bottomPrice, 4);
        }

        //当前价
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
