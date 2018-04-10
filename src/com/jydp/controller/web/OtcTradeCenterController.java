package com.jydp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.*;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
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
    /** 出售单 */
    @RequestMapping(value = "/buy.htm")
    public @ResponseBody JsonObjectBO buy(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();
        otcTransactionPendOrderService.insertPendOrder();
            resultJson.setCode(1);
            resultJson.setMessage("未登录");
            return resultJson;
    }
}
