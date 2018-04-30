package com.jydp.controller.userWeb;


import com.alibaba.fastjson.JSONObject;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.ITransactionCurrencyService;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/web/otcTradeCenter")
public class WebOtcTradeCenterController {

    @Autowired
    ITransactionCurrencyService transactionCurrencyService;


    /**
     * 获取所有的币种列表
     *
     * @return
     */
    @RequestMapping(value = "/currency/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBO getCurrencyList() {
        JsonObjectBO response = new JsonObjectBO();
        List<TransactionCurrencyVO> transactionCurrencyList = transactionCurrencyService.getOnlineAndSuspensionCurrencyForWeb();
        if (CollectionUtils.isEmpty(transactionCurrencyList)) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_NO_RESULT);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_NO_RESULT);
            return response;
        }
        response.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        JSONObject data = new JSONObject();
        data.put("transactionCurrencyList", transactionCurrencyList);
        response.setData(data);
        return response;
    }
}
