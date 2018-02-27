package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.ITransactionUserDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 成交记录
 * @author fk
 *
 */
@Controller
@RequestMapping("/userWeb/transactionUserDealController")
@Scope(value="prototype")
public class TransactionUserDealController {

    /** 用户成交记录 */
    @Autowired
    private ITransactionUserDealService transactionUserDealService;

    /** 展示页面 */
    @RequestMapping("/show.htm")
    public String show(HttpServletRequest request) {
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录");
            return "page/web/login";
        }
        //获取参数
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String pendingOrderNo = StringUtil.stringNullHandle(request.getParameter("pendingOrderNo"));
        if (!StringUtil.isNotNull(pendingOrderNo)) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误");
            return "page/web/transactionUserDeal";
        }

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = transactionUserDealService.countTransactionUserDealByPendNo(pendingOrderNo);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber < pageNumber) {
            pageNumber = totalPageNumber;
        }

        List<TransactionUserDealDO> userDealList = null;
        if (totalNumber > 0) {
            transactionUserDealService.listTransactionUserDealByPendNo(pendingOrderNo, pageNumber, pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("userDealList", userDealList);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/web/transactionUserDeal";
    }

}
