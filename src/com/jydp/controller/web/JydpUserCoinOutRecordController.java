package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IJydpUserCoinOutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户币种提现记录
 * @author yk
 *
 */
@Controller
@RequestMapping("/userWeb/jydpUserCoinOutRecord")
@Scope(value="prototype")
public class JydpUserCoinOutRecordController {

    /** JYDP用户币种转出记录 */
    @Autowired
    private IJydpUserCoinOutRecordService jydpUserCoinOutRecordService;

    /** 查询用户币种转出记录 */
    @RequestMapping("/show.htm")
    public String getJydpUserCoinOutRecord(HttpServletRequest request){

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            return "page/web/login";
        }

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        String userAccount = userSession.getUserAccount();
        int totalNumber = jydpUserCoinOutRecordService.countJydpUserCoinOutRecord(userAccount);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = null;
        if (totalNumber > 0) {
            jydpUserCoinOutRecordList = jydpUserCoinOutRecordService.getJydpUserCoinOutRecordlist(userAccount);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("coinOutRecordList", jydpUserCoinOutRecordList);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");

        return "page/web/recordCoin";
    }
}
