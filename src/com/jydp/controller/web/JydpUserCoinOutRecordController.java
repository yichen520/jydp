package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IJydpUserCoinOutRecordService;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /** 用户账号 */
    @Autowired
    private IUserService userService;

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
        int userId = userSession.getUserId();
        int totalNumber = jydpUserCoinOutRecordService.countJydpUserCoinOutRecord(userId);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = null;
        if (totalNumber > 0) {
            jydpUserCoinOutRecordList = jydpUserCoinOutRecordService.getJydpUserCoinOutRecordlist(userId,pageNumber,pageSize);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("coinOutRecordList", jydpUserCoinOutRecordList);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");

        return "page/web/recordCoin";
    }

    /** 撤销用户币种转出申请 */
    @RequestMapping("/withdrawCoinOutRecord.htm")
    public @ResponseBody JsonObjectBO withdrawUserCoinOutRecord(HttpServletRequest request){
        JsonObjectBO resultJson = new JsonObjectBO();

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        //获取参数
        String coinRecordNo = StringUtil.stringNullHandle(request.getParameter("coinRecordNo"));

        if (coinRecordNo.isEmpty()) {
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }
        if (userSession == null) {
            resultJson.setCode(2);
            resultJson.setMessage("未登录");
            return resultJson;
        }

        int userId = userSession.getUserId();
        //获取用户信息
        UserDO user = userService.getUserByUserId(userId);
        if(user == null){
            resultJson.setCode(2);
            resultJson.setMessage("该用户不存在");
            return resultJson;
        }

        if(user.getAccountStatus() != 1){
            resultJson.setCode(2);
            resultJson.setMessage("该账号已被禁用");
            return resultJson;
        }

        boolean result = jydpUserCoinOutRecordService.withdrawUserCoinOutRecord(userId,coinRecordNo);

        if (result) {
            resultJson.setCode(1);
            resultJson.setMessage("撤销成功");
        } else {
            resultJson.setCode(2);
            resultJson.setMessage("撤销失败");
        }
        return resultJson;
    }
}
