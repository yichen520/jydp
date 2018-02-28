package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IUserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 账户记录
 * @author yk
 */
@Controller
@RequestMapping("/userWeb/accountRecord")
@Scope(value = "prototype")
public class AccountRecordController {

    /** 用户账户记录 */
    @Autowired
    private IUserBalanceService userBalanceService;

    /** 查询用户账户记录 */
    @RequestMapping("/show.htm")
    public String getAccountRecord(HttpServletRequest request) {

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            return "page/web/login";
        }

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int userId = userSession.getUserId();
        int totalNumber = userBalanceService.countUserBalanceForWeb(userId);

        int pageSize = 20;
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber -1;
        }
        List<UserBalanceDO> userBalanceList = null;

        if (totalNumber > 0) {
            userBalanceList = userBalanceService.getUserBalancelistForWeb(userId, pageNumber, pageSize);
        }

        request.setAttribute("code",1);
        request.setAttribute("message","查询成功");
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("accountRecordList",userBalanceList);
        return "page/web/recordAccount";
    }
}
