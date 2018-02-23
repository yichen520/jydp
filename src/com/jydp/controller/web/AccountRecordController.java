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
@RequestMapping("/web/myrecord")
@Scope(value = "prototype")
public class AccountRecordController {

    /** 用户账户记录 */
    @Autowired
    private IUserBalanceService userBalanceService;

    /** 查询用户账户记录 */
    @RequestMapping("/accountRecord")
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
        List<UserBalanceDO> userBalanceList = userBalanceService.getUserBalancelist(userId,pageNumber,pageSize);
        request.setAttribute("code",1);
        request.setAttribute("message","查询成功");
        request.setAttribute("accountRecordList",userBalanceList);
        return "page/accountRecord";
    }
}
