package com.jydp.controller.userWeb;


import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.DTO.QueryParamDTO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;
import com.jydp.interceptor.UserWapInterceptor;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.interceptor.WebInterceptor;
import com.jydp.service.IOtcTransactionUserDealService;
import config.SystemMessageConfig;
import config.SystemSwitchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/web/userDealRecord")
public class WebUserDealRecordController {

    @Autowired
    IOtcTransactionUserDealService otcTransactionUserDealService;


    /**
     * 场外交易成交记录页面展示
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO listUserDealRecord(HttpServletRequest request,
                                           @RequestBody QueryParamDTO queryParamDTO) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO userBo = WebInterceptor.getUser(request);
        if (userBo == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }
        int currencyId = 0;
        if (StringUtil.isNotNull(queryParamDTO.getCurrencyId())) {
            currencyId = Integer.parseInt(queryParamDTO.getCurrencyId());
        }

        int dealType = 0;
        if (StringUtil.isNotNull(queryParamDTO.getDealType())) {
            dealType = Integer.parseInt(queryParamDTO.getDealType());
        }

        int dealStatus = 0;
        if (StringUtil.isNotNull(queryParamDTO.getDealStatus())) {
            dealStatus = Integer.parseInt(queryParamDTO.getDealStatus());
        }

        Timestamp startAddTime = null;
        if (StringUtil.isNotNull(queryParamDTO.getStartAddTime())) {
            startAddTime = Timestamp.valueOf(queryParamDTO.getStartAddTime());
        }

        Timestamp endAddTime = null;
        if (StringUtil.isNotNull(queryParamDTO.getEndAddTime())) {
            endAddTime = Timestamp.valueOf(queryParamDTO.getEndAddTime());
        }

        int pageNumber = 1;
        if (StringUtil.isNotNull(queryParamDTO.getPageNumber())) {
            pageNumber = Integer.parseInt(queryParamDTO.getPageNumber());
        }

        int pageSize = 20;
        int totalNumber = otcTransactionUserDealService.numberOtcTransactionUsealByUserId(userBo.getUserId(), queryParamDTO.getDealerName(), currencyId, dealType, dealStatus, startAddTime, endAddTime);
        if (totalNumber <= 0) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_NO_RESULT);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_NO_RESULT);
            return response;
        }
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);   //总页码数
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        otcTransactionUserDealList = otcTransactionUserDealService.listOtcTransactionUsealByUserId(userBo.getUserId(),
                queryParamDTO.getDealerName(), currencyId, dealType, dealStatus, startAddTime, endAddTime, pageNumber - 1, pageSize);
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
     * 分页查询用户成交记录
     */
    @RequestMapping("/separateQueryUserDeal/list")
    @ResponseBody
    public JsonObjectBO separateQueryUserDeal(HttpServletRequest request,
                                              @RequestBody QueryParamDTO queryParamDTO) {
        UserSessionBO userBo = WebInterceptor.getUser(request);
        JsonObjectBO response = new JsonObjectBO();
        if (userBo == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }

        int currencyId = 0;
        if (StringUtil.isNotNull(queryParamDTO.getCurrencyId())) {
            currencyId = Integer.parseInt(queryParamDTO.getCurrencyId());
        }

        int dealType = 0;
        if (StringUtil.isNotNull(queryParamDTO.getDealType())) {
            dealType = Integer.parseInt(queryParamDTO.getDealType());
        }

        int dealStatus = 0;
        if (StringUtil.isNotNull(queryParamDTO.getDealStatus())) {
            dealStatus = Integer.parseInt(queryParamDTO.getDealStatus());
        }

        Timestamp startAddTime = null;
        if (StringUtil.isNotNull(queryParamDTO.getStartAddTime())) {
            startAddTime = Timestamp.valueOf(queryParamDTO.getStartAddTime());
        }

        Timestamp endAddTime = null;
        if (StringUtil.isNotNull(queryParamDTO.getEndAddTime())) {
            endAddTime = Timestamp.valueOf(queryParamDTO.getEndAddTime());
        }

        int pageNumber = 1;
        if (StringUtil.isNotNull(queryParamDTO.getPageNumber())) {
            pageNumber = Integer.parseInt(queryParamDTO.getPageNumber());
        }

        int pageSize = 20;
        int totalNumber = otcTransactionUserDealService.numberOtcTransactionUsealByUserId(userBo.getUserId(), queryParamDTO.getDealerName(), currencyId, dealType, dealStatus, startAddTime, endAddTime);
        if (totalNumber <= 0) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_NO_RESULT);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_NO_RESULT);
            return response;
        }
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);   //总页码数
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        otcTransactionUserDealList = otcTransactionUserDealService.listOtcTransactionUsealByUserId(userBo.getUserId(),
                queryParamDTO.getDealerName(), currencyId, dealType, dealStatus, startAddTime, endAddTime, pageNumber - 1, pageSize);
        if (otcTransactionUserDealList != null && otcTransactionUserDealList.size() > 0) {
            for (OtcTransactionUserDealVO otcTransactionUserDeal : otcTransactionUserDealList) {
                double currencyTotalPrice = NumberUtil.doubleFormat(otcTransactionUserDeal.getCurrencyTotalPrice(), 2);
                otcTransactionUserDeal.setCurrencyTotalPrice(currencyTotalPrice);
            }
        }
        //返回对象
        JSONObject data = new JSONObject();
        data.put("otcTransactionUserDealList", otcTransactionUserDealList);
        data.put("pageNumber", pageNumber);
        data.put("totalNumber", totalNumber);
        data.put("totalPageNumber", totalPageNumber);
        response.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        response.setData(data);
        return response;
    }


    /**
     * 用户确认（出售 回购（收款，收货））
     */
    @RequestMapping(value = "/userConfirm", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO userConfirm(HttpServletRequest request,
                                    @RequestBody QueryParamDTO queryParamDTO) {
        JsonObjectBO response = new JsonObjectBO();
        if (StringUtil.isNull(queryParamDTO.getOtcOrderNo())) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return response;
        }
        UserSessionBO userBo = WebInterceptor.getUser(request);
        if (userBo == null) {
            response.setCode(SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
            response.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
            return response;
        }

        boolean fq = WebInterceptor.handleFrequent(request);
        if (fq) {
            response.setCode(SystemMessageConfig.CODE_OPERATE_FREQUENT);
            response.setMessage(SystemMessageConfig.MESSAGE_OPERATE_FREQUENT);
            return response;
        }

        //查询交易记录
        OtcTransactionUserDealDO otcTransactionUserDeal = otcTransactionUserDealService.getOtcTransactionUsealByOrderNo(queryParamDTO.getOtcOrderNo());
        if (otcTransactionUserDeal == null) {
            response.setCode(SystemMessageConfig.CODE_ORDER_NOT_EXIST);
            response.setMessage(SystemMessageConfig.MESSAGE_ORDER_NOT_EXIST);
            return response;
        }

        if (otcTransactionUserDeal.getUserId() != userBo.getUserId()) {
            response.setCode(SystemMessageConfig.CODE_VISIT_ILLEGAL);
            response.setMessage(SystemMessageConfig.MESSAGE_VISIT_ILLEGAL);
            return response;
        }
        if (otcTransactionUserDeal.getDealStatus() == 4) {
            response.setCode(SystemMessageConfig.CODE_ORDER_FINISHED);
            response.setMessage(SystemMessageConfig.MESSAGE_ORDER_FINISHED);
            return response;
        }

        JsonObjectBO userConfirmation = null;
        if (otcTransactionUserDeal.getDealType() == 1) {
            userConfirmation = otcTransactionUserDealService.userConfirmationOfReceiptsurchase(otcTransactionUserDeal, userBo.getUserId());
        } else if (otcTransactionUserDeal.getDealType() == 2) {
            userConfirmation = otcTransactionUserDealService.userConfirmationOfReceipts(otcTransactionUserDeal, userBo.getUserId());
        } else {
            response.setCode(SystemMessageConfig.CODE_TYPE_ILLEGAL);
            response.setMessage(SystemMessageConfig.MESSAGE_TYPE_ILLEGAL);
            return response;
        }
        return userConfirmation;
    }
}
