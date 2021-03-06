package com.jydp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionUserDealService;
import com.jydp.service.ITransactionCurrencyService;
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

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    @RequestMapping("/show.htm")
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
        String dealTypeStr = StringUtil.stringNullHandle(request.getParameter("dealType"));
        String dealStatusStr = StringUtil.stringNullHandle(request.getParameter("dealStatus"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String paymentTypeStr = StringUtil.stringNullHandle(request.getParameter("paymentType"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int currencyId = 0;
        int dealStatus = 0;
        int pageNumber = 0;
        int paymentType = 0;
        int dealType = 0;

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

        if (StringUtil.isNotNull(dealTypeStr)) {
            dealType = Integer.parseInt(dealTypeStr);
        }

        Timestamp startAddTime = null;
        if (StringUtil.isNotNull(startAddTimeStr)) {
            startAddTime = DateUtil.stringToTimestamp(startAddTimeStr);
        }

        Timestamp endAddTime = null;
        if (StringUtil.isNotNull(endAddTimeStr)) {
            endAddTime = DateUtil.stringToTimestamp(endAddTimeStr);
        }

        JSONObject queryParams = new JSONObject();
        queryParams.put("userAccount",userAccount);
        queryParams.put("currencyId",currencyId);
        queryParams.put("dealType",dealType);
        queryParams.put("dealStatus",dealStatus);
        queryParams.put("startAddTime",startAddTimeStr);
        queryParams.put("endAddTime",endAddTimeStr);
        queryParams.put("paymentType",paymentType);

        int pageSize = 20;

        int totalNumber = otcTransactionUserDealService.countOtcTransactionUserDeallistByDealerId(userId,userAccount,currencyId,dealStatus,startAddTime,endAddTime,paymentType,dealType);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        if (totalNumber > 0) {
            otcTransactionUserDealList = otcTransactionUserDealService.getOtcTransactionUserDeallistByDealerId(userId,userAccount,currencyId,dealStatus,startAddTime,endAddTime,paymentType,dealType,pageNumber,pageSize);

            if(otcTransactionUserDealList != null && otcTransactionUserDealList.size() > 0){
                for(OtcTransactionUserDealVO otcTransactionUserDeal : otcTransactionUserDealList){
                    double currencyTotalPrice = NumberUtil.doubleFormat(otcTransactionUserDeal.getCurrencyTotalPrice(), 2);
                    otcTransactionUserDeal.setCurrencyTotalPrice(currencyTotalPrice);
                }
            }
        }

        //获取所有币种信息
        List<TransactionCurrencyVO>  transactionCurrencyList = transactionCurrencyService.getOnlineAndSuspensionCurrencyForWeb();
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("queryParams", queryParams);
        request.setAttribute("transactionCurrencyList", transactionCurrencyList);
        request.setAttribute("otcTransactionUserDealList", otcTransactionUserDealList);
    }

    /** 经销商回购币-确认收货 **/
    @RequestMapping(value = "/confirmTakeCoin.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO confirmTakeCoin(HttpServletRequest request){
        JsonObjectBO responseJson = new JsonObjectBO();

        String otcOrderNo = StringUtil.stringNullHandle(request.getParameter("otcOrderNo"));

        boolean fq =UserWebInterceptor.handleFrequent(request);
        if(fq){
            responseJson.setCode(2);
            responseJson.setMessage("用户操作频繁");
            return responseJson;
        }

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

        responseJson = otcTransactionUserDealService.dealerConfirmTakeForBuyBack(otcOrderNo,otcPendingOrderNo,userSession.getUserId());

        return responseJson;
    }

    /** 经销商出售币-确认收款 **/
    @RequestMapping(value = "/confirmTakeMoney.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO confirmTakeMoney(HttpServletRequest request){
        JsonObjectBO responseJson = new JsonObjectBO();

        String otcOrderNo = StringUtil.stringNullHandle(request.getParameter("otcOrderNo"));

        boolean fq =UserWebInterceptor.handleFrequent(request);
        if(fq){
            responseJson.setCode(2);
            responseJson.setMessage("用户操作频繁");
            return responseJson;
        }

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

        responseJson = otcTransactionUserDealService.dealerConfirmTakeForSellCoin(otcTransactionUserDeal,userSession.getUserId());
        return responseJson;
    }

}
