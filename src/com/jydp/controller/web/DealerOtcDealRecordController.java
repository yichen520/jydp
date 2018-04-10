package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
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

        boolean result = otcTransactionUserDealService.updateDealStatusByOtcOrderNo(otcOrderNo,1,2);

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
