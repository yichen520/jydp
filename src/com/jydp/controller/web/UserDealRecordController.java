package com.jydp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;
import com.jydp.interceptor.UserWapInterceptor;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionUserDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 场外交易用户成交记录
 * @Author: sy
 */
@Controller
@RequestMapping("/userWeb/userDealRecord")
@Scope(value = "prototype")
public class UserDealRecordController {

    /**  场外交易成交记录 */
    @Autowired
    private IOtcTransactionUserDealService otcTransactionUserDealService;

    /**  场外交易成交记录页面展示 */
    @RequestMapping("show.htm")
    public String show(HttpServletRequest request) {
        UserSessionBO userBo = UserWebInterceptor.getUser(request);
        if (userBo == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录！");
            return "page/web/login";
        }

        String dealerName = StringUtil.stringNullHandle(request.getParameter("dealerName"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String dealTypeStr = StringUtil.stringNullHandle(request.getParameter("dealType"));
        String dealStatusStr = StringUtil.stringNullHandle(request.getParameter("dealStatus"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int currencyId = 0;
        if(StringUtil.isNotNull(currencyIdStr)){
            currencyId = Integer.parseInt(currencyIdStr);
        }

        int dealType = 0;
        if(StringUtil.isNotNull(dealTypeStr)){
            dealType = Integer.parseInt(dealTypeStr);
        }

        int dealStatus = 0;
        if(StringUtil.isNotNull(dealStatusStr)){
            dealStatus = Integer.parseInt(dealStatusStr);
        }

        Timestamp startAddTime = null;
        if(StringUtil.isNotNull(startAddTimeStr)){
            startAddTime = Timestamp.valueOf(startAddTimeStr);
        }

        Timestamp endAddTime = null;
        if(StringUtil.isNotNull(endAddTimeStr)){
            endAddTime = Timestamp.valueOf(endAddTimeStr);
        }

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = otcTransactionUserDealService.numberOtcTransactionUsealByUserId(userBo.getUserId(), dealerName, currencyId, dealType, dealStatus, startAddTime, endAddTime);
        int totalPageNumber = (int) Math.ceil(totalNumber/1.0/pageSize);   //总页码数
        if(totalPageNumber <= 0){
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }
        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        if(totalNumber > 0){
            otcTransactionUserDealList = otcTransactionUserDealService.listOtcTransactionUsealByUserId(userBo.getUserId(),
                    dealerName, currencyId, dealStatus, startAddTime, endAddTime, pageNumber, pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("otcTransactionUserDealList", otcTransactionUserDealList);
        request.setAttribute("dealerName", dealerName);
        request.setAttribute("currencyId", currencyId);
        request.setAttribute("dealType", dealType);
        request.setAttribute("dealStatus", dealStatus);
        request.setAttribute("startAddTime", startAddTimeStr);
        request.setAttribute("endAddTime", endAddTimeStr);
        return "page/web/userCurbExchangeRecord";
    }

    /**
     * 分页查询用户成交记录
     */
    @RequestMapping("/separateQueryUserDeal.htm")
    public @ResponseBody JsonObjectBO separateQueryUserDeal(HttpServletRequest request) {
        UserSessionBO userBo = UserWapInterceptor.getUser(request);
        JsonObjectBO responseJson = new JsonObjectBO();
        if (userBo == null) {
            responseJson.setCode(1);
            responseJson.setMessage("未登录！");
            return responseJson;
        }

        String dealerName = StringUtil.stringNullHandle(request.getParameter("dealerName"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String dealTypeStr = StringUtil.stringNullHandle(request.getParameter("dealType"));
        String dealStatusStr = StringUtil.stringNullHandle(request.getParameter("dealStatus"));
        String startAddTimeStr = StringUtil.stringNullHandle(request.getParameter("startAddTime"));
        String endAddTimeStr = StringUtil.stringNullHandle(request.getParameter("endAddTime"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int currencyId = 0;
        if(StringUtil.isNotNull(currencyIdStr)){
            currencyId = Integer.parseInt(currencyIdStr);
        }

        int dealType = 0;
        if(StringUtil.isNotNull(dealTypeStr)){
            dealType = Integer.parseInt(dealTypeStr);
        }

        int dealStatus = 0;
        if(StringUtil.isNotNull(dealStatusStr)){
            dealStatus = Integer.parseInt(dealStatusStr);
        }

        Timestamp startAddTime = null;
        if(StringUtil.isNotNull(startAddTimeStr)){
            startAddTime = Timestamp.valueOf(startAddTimeStr);
        }

        Timestamp endAddTime = null;
        if(StringUtil.isNotNull(endAddTimeStr)){
            endAddTime = Timestamp.valueOf(endAddTimeStr);
        }

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = otcTransactionUserDealService.numberOtcTransactionUsealByUserId(userBo.getUserId(), dealerName, currencyId, dealType, dealStatus, startAddTime, endAddTime);
        int totalPageNumber = (int) Math.ceil(totalNumber/1.0/pageSize);   //总页码数
        if(totalPageNumber <= 0){
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }
        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        if(totalNumber > 0){
            otcTransactionUserDealList = otcTransactionUserDealService.listOtcTransactionUsealByUserId(userBo.getUserId(),
                    dealerName, currencyId, dealStatus, startAddTime, endAddTime, pageNumber, pageSize);
        }
        //返回对象
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("otcTransactionUserDealList", otcTransactionUserDealList);
        jsonObject.put("pageNumber", pageNumber);
        jsonObject.put("totalNumber", totalNumber);
        jsonObject.put("totalPageNumber", totalPageNumber);
        responseJson.setCode(1);
        responseJson.setMessage("查询成功");
        responseJson.setData(jsonObject);
        return responseJson;
    }

    /**  用户确认（出售 回购（收款，收货）） */
    @RequestMapping(value = "userConfirm.htm")
    public @ResponseBody JsonObjectBO userConfirm(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO userBo = UserWebInterceptor.getUser(request);
        if (userBo == null) {
            response.setCode(4);
            response.setMessage("未登录");
            return response;
        }

        String otcOrderNo = StringUtil.stringNullHandle(request.getParameter("otcOrderNo"));
        if (!StringUtil.isNotNull(otcOrderNo)){
            response.setCode(2);
            response.setMessage("参数错误");
            return response;
        }

        OtcTransactionUserDealDO otcTransactionUserDeal = otcTransactionUserDealService.getOtcTransactionUsealByOrderNo(otcOrderNo);
        if(otcTransactionUserDeal == null){
            response.setCode(3);
            response.setMessage("此订单不存在");
            return response;
        }

        if(otcTransactionUserDeal.getUserId() != userBo.getUserId()){
            response.setCode(3);
            response.setMessage("非法访问");
            return response;
        }
        if(otcTransactionUserDeal.getDealStatus() == 3){
            response.setCode(2);
            response.setMessage("此订单已完成");
            return response;
        }




        boolean userConfirmation ;
        Timestamp currentTime = DateUtil.getCurrentTime();
        //用户确认收货
        if(otcTransactionUserDeal.getDealType() == 1){
            if(otcTransactionUserDeal.getDealStatus() == 1){
                userConfirmation = otcTransactionUserDealService.updateDealStatusByOtcOrderNo(otcOrderNo,1,2, currentTime);
            } else {
                userConfirmation = otcTransactionUserDealService.updateDealStatusByOtcOrderNo(otcOrderNo,2,3, currentTime);
            }
        //用户确认收款
        } else if(otcTransactionUserDeal.getDealType() == 2){
            userConfirmation = otcTransactionUserDealService.userConfirmationOfReceipts(otcTransactionUserDeal);
        } else {
            response.setCode(3);
            response.setMessage("该订单无法确认");
            return response;
        }

        if(!userConfirmation){
            response.setCode(3);
            response.setMessage("该订单确认失败，请刷新页面后重试");
            return response;
        }

        response.setCode(1);
        response.setMessage("确认成功");
        return response;
    }
}
