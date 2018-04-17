package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.VO.UserRechargeCoinRecordVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.ISylToJydpChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description: 用户充币记录
 * Author: hht
 * Date: 2018-04-16 11:16
 */
@Controller
@RequestMapping("/userWeb/userRechargeCoinRecord")
@Scope(value = "prototype")
public class UserRechargeCoinRecordController {

    /**
     * SYL转账盛源链记录(SYL-->JYDP)
     */
    @Autowired
    private ISylToJydpChainService sylToJydpChainService;

    /**
     * 查询用户充币记录
     */
    @RequestMapping("/show.htm")
    public String show(HttpServletRequest request) {

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            return "page/web/login";
        }

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = sylToJydpChainService.countUserRechargeCoinRecordForUser(userSession.getUserId());

        int pageSize = 20;
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<UserRechargeCoinRecordVO> userRechargeCoinRecordList = null;
        if (totalNumber > 0) {
            userRechargeCoinRecordList = sylToJydpChainService.listUserRechargeCoinRecordForUser(userSession.getUserId(), pageNumber, pageSize);
        }

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("userRechargeCoinRecordList", userRechargeCoinRecordList);

        return "page/web/userRechargeCoinRecord";
    }
}