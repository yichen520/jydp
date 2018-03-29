package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.service.IJydpUserCoinOutRecordService;
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
        try{
            String CoinOutRecordListJson = mapper.writeValueAsString(jydpUserCoinOutRecordList);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("totalNumber", totalNumber);
            request.setAttribute("totalPageNumber", totalPageNumber);
            request.setAttribute("coinOutRecordList", CoinOutRecordListJson);
        } catch (Exception e){
            LogUtil.printErrorLog(e);
        }
    }

    /**
     * 加载更多提币记录
     * @return 加载更多提币记录跳转到提币页面
     */
    @RequestMapping(value = "/showMorePresent.htm", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject showMoreNotice(HttpServletRequest request) {
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

        try{
            response.put("pageNumber", pageNumber);
            response.put("totalNumber", totalNumber);
            response.put("totalPageNumber", totalPageNumber);
            response.put("coinOutRecordList", jydpUserCoinOutRecordList);
            response.put("code", 1);
            response.put("message", "查询成功!");
        }catch(Exception e){
            LogUtil.printErrorLog(e);
        }

        return response;
    }
}
