package com.jydp.controller.web;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionUserDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

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

    @RequestMapping(value = "/show.html", method = RequestMethod.POST)
    public String show(HttpServletRequest request){
        list(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功!");
        return "page/web/tradeOutSeller";
    }

    /** 查询记录 **/
    public void list(HttpServletRequest request){

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        int userId = userSession.getUserId();

        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String dealStatusStr = StringUtil.stringNullHandle(request.getParameter("dealStatus"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int currencyId = 0;
        int dealStatus = 0;
        int pageNumber = 0;
        int paymentType = 0;

        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        if (StringUtil.isNotNull(dealStatusStr)) {
            dealStatus = Integer.parseInt(dealStatusStr);
        }

        if (StringUtil.isNotNull(paymentTypeStr)) {
            paymentType = Integer.parseInt(paymentTypeStr);
        }

        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        Timestamp startAddTime = null;
        if (StringUtil.isNotNull(startAddTimeStr)) {
            startAddTime = DateUtil.stringToTimestamp(startAddTimeStr);
        }

        Timestamp endAddTime = null;
        if (StringUtil.isNotNull(endAddTimeStr)) {
            endAddTime = DateUtil.stringToTimestamp(endAddTimeStr);
        }

        int pageSize = 20;

        int totalNumber = otcTransactionUserDealService.countOtcTransactionUserDeallistByDealerId(userId,userAccount,currencyId,dealStatus,startAddTime,endAddTime,paymentType);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        if (totalNumber > 0) {
            otcTransactionUserDealList = otcTransactionUserDealService.getOtcTransactionUserDeallistByDealerId(userId,userAccount,currencyId,dealStatus,startAddTime,endAddTime,paymentType,pageNumber,pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("otcTransactionUserDealList", otcTransactionUserDealList);
    }

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

        //判断用户是否为经销商
        if(userSession.getIsDealer() != 2){//不是经销商
            responseJson.setCode(3);
            responseJson.setMessage("当前用户不是经销商");
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

    /** 经销商出售币-确认收款 **/
    @RequestMapping(value = "/confirmTakeCoin.html", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO confirmTakeCoin(HttpServletRequest request){
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

        //判断用户是否为经销商
        if(userSession.getIsDealer() != 2){//不是经销商
            responseJson.setCode(3);
            responseJson.setMessage("当前用户不是经销商");
            return responseJson;
        }

        OtcTransactionUserDealDO otcTransactionUserDeal =  otcTransactionUserDealService.getOtcTransactionUsealByOrderNo(otcOrderNo);

        if (otcTransactionUserDeal == null) {
            responseJson.setCode(3);
            responseJson.setMessage("该笔订单不存在");
            return responseJson;
        }

        boolean result = otcTransactionUserDealService.dealerConfirmTakeForSellCoin(otcTransactionUserDeal,userSession.getUserId());

        if (result) {
            responseJson.setCode(1);
            responseJson.setMessage("确认收款成功");
            return responseJson;
        } else {
            responseJson.setCode(1);
            responseJson.setMessage("确认收款失败");
            return responseJson;
        }
    }

}
