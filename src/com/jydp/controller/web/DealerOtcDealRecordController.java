package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionUserDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 经销商场外成交记录
 * @author yk
 */
@Controller
@RequestMapping("/userWeb/dealerOtcDealRecord")
@Scope(value = "prototype")
public class DealerOtcDealRecordController {

    /** 场外交易成交记录 **/
    @Autowired
    private IOtcTransactionUserDealService otcTransactionUserDealService;

    /** 经销商回购币-确认收货 **/
    @RequestMapping(value = "/confirmTake.html", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO confirmTake(HttpServletRequest request){
        JsonObjectBO responseJson = new JsonObjectBO();

        String otcOrderNo = StringUtil.stringNullHandle(request.getParameter("otcOrderNo"));

        if (!StringUtil.isNotNull(otcOrderNo)) {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误");
            return responseJson;
        }

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            responseJson.setCode(4);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        OtcTransactionUserDealDO otcTransactionUserDeal =  otcTransactionUserDealService.getOtcTransactionUsealByOrderNo(otcOrderNo);

        if (otcTransactionUserDeal == null) {
            responseJson.setCode(3);
            responseJson.setMessage("该笔订单不存在");
            return responseJson;
        }

        String otcPendingOrderNo = otcTransactionUserDeal.getOtcPendingOrderNo();

        boolean result = otcTransactionUserDealService.dealerConfirmTakeForBuyBack(otcOrderNo,otcPendingOrderNo,userSession.getUserId());

        if (result) {
            responseJson.setCode(1);
            responseJson.setMessage("确认收货成功");
            return responseJson;
        } else {
            responseJson.setCode(1);
            responseJson.setMessage("确认收货失败");
            return responseJson;
        }
    }
}
