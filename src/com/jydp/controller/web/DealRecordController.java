package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import com.jydp.entity.VO.TransactionUserDealVO;
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
 * @author yk
 */
@Controller
@RequestMapping("/userWeb/dealRecord")
@Scope(value = "prototype")
public class DealRecordController {

    /** 查询用户成交记录 */
    @Autowired
    private ITransactionUserDealService transactionUserDealService;

    /** 查询用户成交记录 */
    @RequestMapping("/show.htm")
    public String getAccountRecord(HttpServletRequest request) {

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            return "page/web/login";
        }

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String pendingOrderNo = StringUtil.stringNullHandle(request.getParameter("pendingOrderNo"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;

        if (StringUtil.isNotNull(pendingOrderNo)) {
            int totalNumber = transactionUserDealService.countTransactionUserDealByPendNo(pendingOrderNo, userSession.getUserId());

            int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
            if (totalPageNumber <= 0) {
                totalPageNumber = 1;
            }
            if (totalPageNumber <= pageNumber) {
                pageNumber = totalPageNumber - 1;
            }

            List<TransactionUserDealVO> dealRecordList = null;
            if (totalNumber > 0) {
                dealRecordList = transactionUserDealService.listTransactionUserDealByPendNo(pendingOrderNo, userSession.getUserId(), pageNumber, pageSize);
            }

            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("totalNumber", totalNumber);
            request.setAttribute("totalPageNumber", totalPageNumber);
            request.setAttribute("pendingOrderNo", pendingOrderNo);
            request.setAttribute("dealRecordList", dealRecordList);
            request.setAttribute("code", 1);
            request.setAttribute("message", "查询成功");
            return "page/web/recordTrade";
        }

        int userId = userSession.getUserId();
        int totalNumber = transactionUserDealService.countUserDealForWeb(userId);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber -1;
        }

        List<TransactionUserDealVO> transactionUserDealList = null;
        if (totalNumber > 0) {
            transactionUserDealList = transactionUserDealService.getTransactionUserDeallist(userId,pageNumber,pageSize);
        }

        request.setAttribute("code",1);
        request.setAttribute("message","查询成功");
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("dealRecordList",transactionUserDealList);
        return "page/web/recordTrade";
    }
}
