package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.service.ISystemHotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 热门话题
 * @Author: xzp
 */
@Controller
@RequestMapping("/userWap/wapSystemHot")
@Scope(value = "prototype")
public class WapHotController {

    /**  热门话题 */
    @Autowired
    private ISystemHotService systemHotService;

    /**  热门话题展示  */
    @RequestMapping("/show")
    public String show(HttpServletRequest request) {
        showList(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功!");
        return  "page/wap/hot";
    }

    /**  热门话题列表查询 */
    public void showList(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = systemHotService.countSystemHotForUser();
        int pageSize = 10;

        List<SystemHotDO> systemHotList = null;
        if (totalNumber > 0) {
            systemHotList = systemHotService.listSystemHotForUser(pageNumber, pageSize);
        }

        if (systemHotList != null && systemHotList.size() > 0) {
            for (SystemHotDO systemHot : systemHotList) {
                String noticeTitle = systemHot.getNoticeTitle();
                if (StringUtil.isNotNull(noticeTitle)) {
                    noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                    systemHot.setNoticeTitle(noticeTitle);
                }
            }
        }

        int totalPageNumber = (int)Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        ObjectMapper mapper = new ObjectMapper();
        try{
            String systemHotListJson = mapper.writeValueAsString(systemHotList);
            request.setAttribute("systemNoticeList",systemHotListJson);
            request.setAttribute("pageNumber",pageNumber);
            request.setAttribute("totalNumber",totalNumber);
            request.setAttribute("totalPageNumber",totalPageNumber);
        }catch(Exception e){
            LogUtil.printErrorLog(e);
        }
    }

    /**  打开热门话题详情页面 */
    @RequestMapping(value = "/showHotDetail/{idStr}", method = RequestMethod.GET)
    public String showHotDetail(HttpServletRequest request, @PathVariable String idStr) {
        if(!StringUtil.isNotNull(idStr)){
            showList(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return  "page/wap/hot";
        }

        int id = 0;
        String reg = "[0-9]*";
        if (idStr.length() < 11 && Pattern.matches(reg,idStr)) {
            id = Integer.parseInt(idStr);
        }
        SystemHotDO systemHot = systemHotService.getSystemHotById(id);
        if (systemHot != null) {
            String noticeTitle = systemHot.getNoticeTitle();
            if (StringUtil.isNotNull(noticeTitle)) {
                noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                systemHot.setNoticeTitle(noticeTitle);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try{
            String systemHotJson = mapper.writeValueAsString(systemHot);
            request.setAttribute("systemHot",systemHotJson);

        }catch(Exception e){
            LogUtil.printErrorLog(e);
        }
        return "page/wap/hotDetail";
    }

    /**
     * 加载更多公告
     * @return 加载更多公告跳转到公告页面
     */
    @RequestMapping(value = "/showMoreHot", method = RequestMethod.POST)
    public @ResponseBody JSONObject showMoreHot(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));


        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = systemHotService.countSystemHotForUser();
        int pageSize = 10;

        List<SystemHotDO> systemHotList = null;
        if (totalNumber > 0) {
            systemHotList = systemHotService.listSystemHotForUser(pageNumber, pageSize);
        }

        if (systemHotList != null && systemHotList.size() > 0) {
            for (SystemHotDO systemHot : systemHotList) {
                String noticeTitle = systemHot.getNoticeTitle();
                if (StringUtil.isNotNull(noticeTitle)) {
                    noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                    systemHot.setNoticeTitle(noticeTitle);
                }
            }
        }

        int totalPageNumber = (int)Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        try{
            response.put("systemHotList",systemHotList);
            response.put("pageNumber",pageNumber);
            response.put("totalNumber",totalNumber);
            response.put("totalPageNumber",totalPageNumber);
            response.put("code", 1);
            response.put("message", "查询成功!");
        }catch(Exception e){
            LogUtil.printErrorLog(e);
        }

        return response;
    }
}
