package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.interceptor.WebInterceptor;
import com.jydp.service.ITransactionPendOrderService;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * web2.0委托记录
 * @author cyfIverson
 * @create 2018-04-19
 */
@RestController
@RequestMapping("/web/webTransactionPendOrderController")
@Scope(value = "prototype")
public class WebTransactionPendOrderController {

    /**
     * 挂单记录
     */
    @Autowired
    ITransactionPendOrderService transactionPendOrderService;

    /**
     * 获取委托记录
     */
    @RequestMapping(value = "/getPendOrderList.htm")
    public JsonObjectBO getTransactionPendOrder(HttpServletRequest request,@RequestBody String requestJson) {
        JsonObjectBO responseJson = new JsonObjectBO();

        //登录校验
        UserSessionBO user = WebInterceptor.getUser(request);
        if (user == null){
            responseJson.setCode(SystemMessageConfig.NOT_LOGININ_CODE);
            responseJson.setMessage(SystemMessageConfig.NOT_LOGININ_MESSAGE);
            return responseJson;
        }

        //参数获取
        JSONObject requestJsonObject = (JSONObject) JSONObject.parse(requestJson);
        String pageNumberStr = (String) requestJsonObject.get("pageNumber");
        int userId = user.getUserId();

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = transactionPendOrderService.countPendOrderByUserId(userId);

        //总页数
        int totalPageNumber = (int) Math.ceil(totalNumber/(pageSize*1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        List<TransactionPendOrderDO> transactionPendOrderRecordList = null;
        if (totalNumber > 0) {
            transactionPendOrderRecordList = transactionPendOrderService.listPendOrderByUserId(user.getUserId(),pageNumber, pageSize);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionPendOrderRecordList", transactionPendOrderRecordList);
        jsonObject.put("totalPageNumber",totalPageNumber);  //总页数
        jsonObject.put("pageNumber",pageNumber);            //当前页面数
        jsonObject.put("totalNumber",totalNumber);          //总共记录数

        responseJson.setData(jsonObject);
        responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        return responseJson;
    }

    /** 撤销挂单 */
    @RequestMapping(value = "/revoke.htm", method = RequestMethod.POST)
    public JsonObjectBO revoke(HttpServletRequest request,@RequestBody String requestJson) {
        JsonObjectBO responseJson = new JsonObjectBO();

        JSONObject requestJsonObject = (JSONObject) JSONObject.parse(requestJson);
        String pendingOrderNo = (String) requestJsonObject.get("pendingOrderNo");

        //登录校验
        UserSessionBO user = WebInterceptor.getUser(request);
        if (user == null) {
            responseJson.setCode(SystemMessageConfig.NOT_LOGININ_CODE);
            responseJson.setMessage(SystemMessageConfig.NOT_LOGININ_MESSAGE);
            return responseJson;
        }

        boolean fq =UserWebInterceptor.handleFrequent(request);
        if(fq){
            responseJson.setCode(SystemMessageConfig.OPERATING_FREQUENCY_CODE);
            responseJson.setMessage(SystemMessageConfig.OPERATING_FREQUENCY_MESSAGE);
            return responseJson;
        }

        if (!StringUtil.isNotNull(pendingOrderNo)) {
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }

        TransactionPendOrderDO transactionPendOrder = transactionPendOrderService.getPendOrderByPendingOrderNo(pendingOrderNo);
        if(transactionPendOrder == null){
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }

        if(transactionPendOrder.getUserId() != user.getUserId()){
            responseJson.setCode(SystemMessageConfig.OPERATION_NOT_PENDING_ORDER_CODE);
            responseJson.setMessage(SystemMessageConfig.OPERATION_NOT_PENDING_ORDER_MESSAGE);
            return responseJson;
        }

        boolean updateResult = transactionPendOrderService.revokePendOrder(pendingOrderNo);
        if (updateResult) {
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        } else {
            responseJson.setCode(SystemMessageConfig.UNDO_FAILED_CODE);
            responseJson.setMessage(SystemMessageConfig.UNDO_FAILED_MESSAGE);
        }

        return responseJson;
    }
}
