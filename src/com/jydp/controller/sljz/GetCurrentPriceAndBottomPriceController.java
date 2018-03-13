package com.jydp.controller.sljz;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DTO.TransactionBottomCurrentPriceDTO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import com.jydp.service.ITransactionDealRedisService;
import config.SystemCommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /** 查询当前价和保底价 */
    @RequestMapping(value = "/transfer")
    public @ResponseBody JSONObject transfer(HttpServletRequest request, HttpServletResponse response) {
        JSONObject responseJson = new JSONObject();

        /*//接收参数
        String codeStr = StringUtil.stringNullHandle(request.getParameter("code"));
        String message = StringUtil.stringNullHandle(request.getParameter("message"));
        String receiveSign = StringUtil.stringNullHandle(request.getParameter("sign"));
        if (!StringUtil.isNotNull(codeStr)|| !StringUtil.isNotNull(message) || !StringUtil.isNotNull(receiveSign)) {
            responseJson.put("code", 2);
            responseJson.put("message", "参数错误");
            return responseJson;
        }

        //验签
        TreeMap<String, String> receiveMap = new TreeMap<String, String>();
        receiveMap.put("code", codeStr);
        receiveMap.put("message", message);
        String receiveSignValidate = SignatureUtil.getSign(receiveMap, SljzConfig.SIGN_SECRET_KEY);
        if(!receiveSign.equals(receiveSignValidate)){
            responseJson.put("code", 3);
            responseJson.put("message", "签名错误");
            return responseJson;
        }*/
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        if (!StringUtil.isNotNull(currencyIdStr)) {
            responseJson.put("code", 2);
            responseJson.put("message", "参数错误");
            return responseJson;
        }
        int currencyId = Integer.parseInt(currencyIdStr);

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
