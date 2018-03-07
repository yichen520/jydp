package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.ITransactionPendOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 挂单记录
 * @author hz
 *
 */
@Controller
@RequestMapping("/userWeb/transactionPendOrderController")
@Scope(value="prototype")
public class TransactionPendOrderController {

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    /** 展示 挂单记录页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录");
            return "page/web/login";
        }
        //获取参数
        String pageNumberStr  = StringUtil.stringNullHandle(request.getParameter("queryPageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = transactionPendOrderService.countPendOrderByUserId(user.getUserId());

        List<TransactionPendOrderDO> transactionPendOrderRecord = null;
        if (totalNumber > 0) {
            transactionPendOrderRecord = transactionPendOrderService.listPendOrderByUserId(user.getUserId(),pageNumber, pageSize);
        }

        int totalPageNumber = (int) Math.ceil(totalNumber/(pageSize*1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        //返回数据
        request.setAttribute("pageNumber",pageNumber);
        request.setAttribute("totalNumber",totalNumber);
        request.setAttribute("totalPageNumber",totalPageNumber);

        request.setAttribute("transactionPendOrderRecord", transactionPendOrderRecord);
        return "page/web/transactionPendOrder";
    }

    /** 撤销挂单 */
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
        if(transactionPendOrder == null){
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        if(transactionPendOrder.getUserId() != user.getUserId()){
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

}
