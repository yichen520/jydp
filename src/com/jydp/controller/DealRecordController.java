package com.jydp.controller;

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
 */
@Controller
@RequestMapping("/web/myrecord")
@Scope(value = "prototype")
public class DealRecordController {

    /** 查询用户成交记录 */
    @Autowired
    private ITransactionUserDealService transactionUserDealService;

    /** 查询用户成交记录 */
    @RequestMapping("/dealRecord")
    public String getAccountRecord(HttpServletRequest request) {

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            return "page/login";
        }

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        if (!StringUtil.isNotNull(pageNumberStr)) {
            request.setAttribute("code",2);
            request.setAttribute("message","请求参数错误");
            return "page/myrecord";
        }

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int userId = userSession.getUserId();
        int pageSize = 20;
        List<TransactionUserDealDO> transactionUserDealList = transactionUserDealService.getTransactionUserDeallist(userId,pageNumber,pageSize);
        request.setAttribute("code",1);
        request.setAttribute("message","查询成功");
        request.setAttribute("dealRecordList",transactionUserDealList);
        return "page/dealRecord";
    }
}
