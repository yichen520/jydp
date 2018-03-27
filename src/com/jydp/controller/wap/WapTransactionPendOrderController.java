package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.service.ITransactionPendOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * wap端挂单记录
 * @author njx
 **/
@Controller
@RequestMapping("/userWap/transactionPendOrder")
@Scope(value="prototype")
public class WapTransactionPendOrderController {
    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    /** 撤销挂单 */
    @RequestMapping(value = "/revoke.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject revoke(HttpServletRequest request) {
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
