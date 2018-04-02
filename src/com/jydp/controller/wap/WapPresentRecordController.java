package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqmkj.utils.LogUtil;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * wap端币种提出记录
 * @author yk
 */
@Controller
@RequestMapping("/userWap/presentRecord")
@Scope(value = "prototype")
public class WapPresentRecordController {

    /**  JYDP用户币种转出记录 */
    @Autowired
    private IJydpUserCoinOutRecordService jydpUserCoinOutRecordService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 币种提出记录展示  */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if (userSession == null) {
            return "page/wap/login";
        }
        showList(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功!");
        return  "page/wap/present";
    }

    /** 币种提出记录查询 */
    public void showList(HttpServletRequest request){
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }
        int pageSize = 10;
        int userId = userSession.getUserId();
        int totalNumber = jydpUserCoinOutRecordService.countJydpUserCoinOutRecord(userId);

        int totalPageNumber = (int)Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = null;
        if (totalNumber > 0) {
            jydpUserCoinOutRecordList = jydpUserCoinOutRecordService.getJydpUserCoinOutRecordlist(userId,pageNumber,pageSize);
        }
        ObjectMapper mapper = new ObjectMapper();
        String CoinOutRecordListJson = "";
        try{
            CoinOutRecordListJson = mapper.writeValueAsString(jydpUserCoinOutRecordList);
        } catch (Exception e){
            LogUtil.printErrorLog(e);
        }
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("coinOutRecordList", CoinOutRecordListJson);
    }

    /**
     * 加载更多提币记录
     * @return 加载更多提币记录跳转到提币页面
     */
    @RequestMapping(value = "/showMorePresent.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject showMoreNotice(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }
        int pageSize = 10;
        int userId = userSession.getUserId();
        int totalNumber = jydpUserCoinOutRecordService.countJydpUserCoinOutRecord(userId);

        int totalPageNumber = (int)Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = null;
        if (totalNumber > 0) {
            jydpUserCoinOutRecordList = jydpUserCoinOutRecordService.getJydpUserCoinOutRecordlist(userId,pageNumber,pageSize);
        }
        response.put("pageNumber", pageNumber);
        response.put("totalNumber", totalNumber);
        response.put("totalPageNumber", totalPageNumber);
        response.put("coinOutRecordList", jydpUserCoinOutRecordList);
        response.put("code", 1);
        response.put("message", "查询成功!");
        return response;
    }

    /** 撤销用户币种转出申请 */
    @RequestMapping(value = "/withdrawCoinOutRecord.htm", method = RequestMethod.POST)
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
