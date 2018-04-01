package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.transaction.WapTransactionUserDealDO;
import com.jydp.interceptor.UserWapInterceptor;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.ITransactionPendOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * wap端委托记录
 * @author cyfIverson
 * @create 2018-03-26
 */
@Controller
@RequestMapping("/userWap/wapTransactionPendOrderController")
@Scope(value = "prototype")
public class WapTransactionPendOrderController {

    /**
     * 挂单记录
     */
    @Autowired
    ITransactionPendOrderService transactionPendOrderService;

    /**
     * 跳转到我的记录页面
     */
    @RequestMapping(value = "/showMyRecord")
    public String showMyRecord() {
        return "page/wap/myRecord";
    }

    /**
     * 展示委托记录页面
     */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        //是否登录检验
        UserSessionBO user = UserWapInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录");
            return "page/wap/login";
        }
        request.setAttribute("pageNumber",0);
        return "page/wap/entrust";
    }

    /**
     * 获取委托记录
     */
    @RequestMapping(value = "/getTransactionPendOrderList")
    public @ResponseBody
    JsonObjectBO getTransactionPendOrder(HttpServletRequest request) {
        String pageNumberStr = request.getParameter("pageNumber");

        UserSessionBO user = UserWapInterceptor.getUser(request);
        int userId = user.getUserId();

        JsonObjectBO responseJson = new JsonObjectBO();
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 10;
        int totalNumber = transactionPendOrderService.countPendOrderByUserId(userId);
        //总页数
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);

        List<TransactionPendOrderDO> transactionPendOrderRecord = null;
        List<WapTransactionUserDealDO> wapTransactionPendOrderRecordList = new ArrayList();
        if (totalNumber > 0) {
            transactionPendOrderRecord = transactionPendOrderService.listPendOrderByUserId(userId, pageNumber, pageSize);
            WapTransactionUserDealDO wapTransactionUserDealDO = null;
            for (TransactionPendOrderDO transactionPendOrderDO : transactionPendOrderRecord){
                wapTransactionUserDealDO = new WapTransactionUserDealDO();

                wapTransactionUserDealDO.setPendingOrderNo(transactionPendOrderDO.getPendingOrderNo());
                wapTransactionUserDealDO.setUserId(transactionPendOrderDO.getUserId());
                wapTransactionUserDealDO.setUserAccount(transactionPendOrderDO.getUserAccount());
                wapTransactionUserDealDO.setPaymentType(transactionPendOrderDO.getPaymentType());
                wapTransactionUserDealDO.setCurrencyId(transactionPendOrderDO.getCurrencyId());
                wapTransactionUserDealDO.setCurrencyName(transactionPendOrderDO.getCurrencyName());
                wapTransactionUserDealDO.setPendingPrice(transactionPendOrderDO.getPendingPrice());
                wapTransactionUserDealDO.setPendingNumber(transactionPendOrderDO.getPendingNumber());
                wapTransactionUserDealDO.setDealNumber(transactionPendOrderDO.getDealNumber());
                wapTransactionUserDealDO.setBuyFee(transactionPendOrderDO.getBuyFee());
                wapTransactionUserDealDO.setRestBalanceLock(transactionPendOrderDO.getRestBalanceLock());
                wapTransactionUserDealDO.setPendingStatus(transactionPendOrderDO.getPendingStatus());
                wapTransactionUserDealDO.setRemark(transactionPendOrderDO.getRemark());
                wapTransactionUserDealDO.setFeeRemark(transactionPendOrderDO.getFeeRemark());
                wapTransactionUserDealDO.setAddTime(transactionPendOrderDO.getAddTime());
                wapTransactionUserDealDO.setEndTime(transactionPendOrderDO.getEndTime());

                BigDecimal b1 = new BigDecimal(String.valueOf(wapTransactionUserDealDO.getPendingPrice()));
                BigDecimal b2 = new BigDecimal(String.valueOf(wapTransactionUserDealDO.getPendingNumber()));
                String totalPrice = b1.multiply(b2).stripTrailingZeros().toPlainString();

                //总价
                wapTransactionUserDealDO.setTotalPrice(totalPrice);
                wapTransactionPendOrderRecordList.add(wapTransactionUserDealDO);
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionPendOrderRecordList", wapTransactionPendOrderRecordList);
        jsonObject.put("totalPageNumber",totalPageNumber);

        responseJson.setData(jsonObject);
        responseJson.setCode(1);
        responseJson.setMessage("数据请求成功");
        return responseJson;
    }

    /**
     * 撤销挂单
     */
    @RequestMapping(value = "/revoke.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO revoke(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            resultJson.setCode(4);
            resultJson.setMessage("未登录");
            return resultJson;
        }

        String pendingOrderNo = StringUtil.stringNullHandle(request.getParameter("pendingOrderNo"));
        if (!StringUtil.isNotNull(pendingOrderNo)) {
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        TransactionPendOrderDO transactionPendOrder = transactionPendOrderService.getPendOrderByPendingOrderNo(pendingOrderNo);
        if (transactionPendOrder == null) {
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        if (transactionPendOrder.getUserId() != user.getUserId()) {
            resultJson.setCode(4);
            resultJson.setMessage("此操作非该挂单本人");
            return resultJson;
        }

        boolean updateResult = transactionPendOrderService.revokePendOrder(pendingOrderNo);
        if (updateResult) {
            resultJson.setCode(1);
            resultJson.setMessage("撤单成功");
        } else {
            resultJson.setCode(5);
            resultJson.setMessage("撤单失败");
        }

        return resultJson;
    }

    /** 撤销挂单 */
    @RequestMapping(value = "/revokeForDeal.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject revokeForDeal(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        UserSessionBO user = (UserSessionBO) request.getSession().getAttribute("userSession");
        if(user == null){
            response.put("code", 4);
            response.put("message", "未登录");
            return response;
        }

        String pendingOrderNo = StringUtil.stringNullHandle(request.getParameter("pendingOrderNo"));
        if(StringUtil.isNull(pendingOrderNo)){
            response.put("code", 3);
            response.put("message", "参数错误");
            return response;
        }

        TransactionPendOrderDO transactionPendOrder = transactionPendOrderService.getPendOrderByPendingOrderNo(pendingOrderNo);
        if(transactionPendOrder == null){
            response.put("code", 3);
            response.put("message", "参数错误");
            return response;
        }
        if(user.getUserId() != transactionPendOrder.getUserId()){
            response.put("code", 4);
            response.put("message", "此操作非该挂单本人");
            return response;
        }

        boolean result = transactionPendOrderService.revokePendOrder(pendingOrderNo);
        if(!result){
            response.put("code", 5);
            response.put("message", "撤单失败");
            return response;
        }
        response.put("code", 0);
        response.put("message", "撤单成功");
        return response;
    }
}