package com.jydp.controller.userWeb;


import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.DTO.QueryParamDTO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.interceptor.WebInterceptor;
import com.jydp.service.IOtcTransactionUserDealService;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * web2.0场外交易
 * Created by xushilong3623 on 2018/4/18.
 */

@Controller
@RequestMapping("/web/dealerOtcDealRecord")
public class WebDealerOtcDealRecordController {

    @Autowired
    IOtcTransactionUserDealService otcTransactionUserDealService;

    /**
     * 分页获取场外交易成交记录列表
     *
     * @param request
     * @param queryParamDTO
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO listUserDealRecord(HttpServletRequest request,
                                           @RequestBody QueryParamDTO queryParamDTO) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO userSession = WebInterceptor.getUser(request);
        if (userSession == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }

        int userId = userSession.getUserId();

        int currencyId = 0;
        int dealStatus = 0;
        int pageNumber = 1;
        int paymentType = 0;
        int dealType = 0;

        if (StringUtil.isNotNull(queryParamDTO.getCurrencyId())) {
            currencyId = Integer.parseInt(queryParamDTO.getCurrencyId());
        }

        if (StringUtil.isNotNull(queryParamDTO.getDealStatus())) {
            dealStatus = Integer.parseInt(queryParamDTO.getDealStatus());
        }

        if (StringUtil.isNotNull(queryParamDTO.getPaymentType())) {
            paymentType = Integer.parseInt(queryParamDTO.getPaymentType());
        }

        if (StringUtil.isNotNull(queryParamDTO.getPageNumber())) {
            pageNumber = Integer.parseInt(queryParamDTO.getPageNumber());
        }

        if (StringUtil.isNotNull(queryParamDTO.getDealType())) {
            dealType = Integer.parseInt(queryParamDTO.getDealType());
        }

        Timestamp startAddTime = null;
        if (StringUtil.isNotNull(queryParamDTO.getStartAddTime())) {
            startAddTime = DateUtil.stringToTimestamp(queryParamDTO.getStartAddTime());
        }

        Timestamp endAddTime = null;
        if (StringUtil.isNotNull(queryParamDTO.getEndAddTime())) {
            endAddTime = DateUtil.stringToTimestamp(queryParamDTO.getEndAddTime());
        }

        int pageSize = 20;

        int totalNumber = otcTransactionUserDealService.countOtcTransactionUserDeallistByDealerId(userId, queryParamDTO.getUserAccount(), currencyId, dealStatus, startAddTime, endAddTime, paymentType, dealType);
        if (totalNumber <= 0) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_NO_RESULT);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_NO_RESULT);
            return response;
        }
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        otcTransactionUserDealList = otcTransactionUserDealService.getOtcTransactionUserDeallistByDealerId(userId, queryParamDTO.getUserAccount(), currencyId, dealStatus, startAddTime, endAddTime, paymentType, dealType, pageNumber - 1, pageSize);
        if (otcTransactionUserDealList != null && otcTransactionUserDealList.size() > 0) {
            for (OtcTransactionUserDealVO otcTransactionUserDeal : otcTransactionUserDealList) {
                double currencyTotalPrice = NumberUtil.doubleFormat(otcTransactionUserDeal.getCurrencyTotalPrice(), 2);
                otcTransactionUserDeal.setCurrencyTotalPrice(currencyTotalPrice);
            }
        }
        response.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        JSONObject data = new JSONObject();
        data.put("pageNumber", pageNumber);
        data.put("totalNumber", totalNumber);
        data.put("totalPageNumber", totalPageNumber);
        data.put("otcTransactionUserDealList", otcTransactionUserDealList);
        response.setData(data);
        return response;
    }

    /**
     * 经销商出售币-确认收款
     **/
    @RequestMapping(value = "/confirmTakeMoney", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO confirmTakeMoney(HttpServletRequest request,
                                         @RequestBody QueryParamDTO queryParamDTO) {
        JsonObjectBO response = new JsonObjectBO();
        if (StringUtil.isNull(queryParamDTO.getOtcOrderNo())) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return response;
        }
        boolean fq = WebInterceptor.handleFrequent(request);
        if (fq) {
            response.setCode(SystemMessageConfig.CODE_OPERATE_FREQUENT);
            response.setMessage(SystemMessageConfig.MESSAGE_OPERATE_FREQUENT);
            return response;
        }

        UserSessionBO userSession = WebInterceptor.getUser(request);
        if (userSession == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }

        //判断用户是否为经销商
        if (userSession.getIsDealer() != 2) {//不是经销商
            response.setCode(SystemMessageConfig.CODE_USER_NOT_DEALER);
            response.setMessage(SystemMessageConfig.MESSAGE_USER_NOT_DEALER);
            return response;
        }

        OtcTransactionUserDealDO otcTransactionUserDeal = otcTransactionUserDealService.getOtcTransactionUsealByOrderNo(queryParamDTO.getOtcOrderNo());
        if (otcTransactionUserDeal == null) {
            response.setCode(SystemMessageConfig.CODE_ORDER_NOT_EXIST);
            response.setMessage(SystemMessageConfig.MESSAGE_ORDER_NOT_EXIST);
            return response;
        }

        response = otcTransactionUserDealService.dealerConfirmTakeForSellCoin(otcTransactionUserDeal, userSession.getUserId());
        return response;
    }

    /**
     * 经销商回购币-确认收货
     **/
    @RequestMapping(value = "/confirmTakeCoin", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO confirmTakeCoin(HttpServletRequest request,
                                        @RequestBody QueryParamDTO queryParamDTO) {
        JsonObjectBO response = new JsonObjectBO();
        if (StringUtil.isNull(queryParamDTO.getOtcOrderNo())) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return response;
        }
        boolean fq = WebInterceptor.handleFrequent(request);
        if (fq) {
            response.setCode(SystemMessageConfig.CODE_OPERATE_FREQUENT);
            response.setMessage(SystemMessageConfig.MESSAGE_OPERATE_FREQUENT);
            return response;
        }

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }

        //判断用户是否为经销商
        if (userSession.getIsDealer() != 2) {//不是经销商
            response.setCode(SystemMessageConfig.CODE_USER_NOT_DEALER);
            response.setMessage(SystemMessageConfig.MESSAGE_USER_NOT_DEALER);
            return response;
        }

        OtcTransactionUserDealDO otcTransactionUserDeal = otcTransactionUserDealService.getOtcTransactionUsealByOrderNo(queryParamDTO.getOtcOrderNo());
        if (otcTransactionUserDeal == null) {
            response.setCode(SystemMessageConfig.CODE_ORDER_NOT_EXIST);
            response.setMessage(SystemMessageConfig.MESSAGE_ORDER_NOT_EXIST);
            return response;
        }

        String otcPendingOrderNo = otcTransactionUserDeal.getOtcPendingOrderNo();
        response = otcTransactionUserDealService.dealerConfirmTakeForBuyBack(queryParamDTO.getOtcOrderNo(), otcPendingOrderNo, userSession.getUserId());
        return response;
    }
}
