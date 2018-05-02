package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.service.ISystemNoticeService;
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
 * 系统公告
 * @Author: xzp
 */
@Controller
@RequestMapping("/userWap/wapSystemNotice")
@Scope(value = "prototype")
public class WapSystemNoticeController {

    /**
     * 系统公告
     */
    @Autowired
    private ISystemNoticeService systemNoticeService;

    /**
     * 系统公告展示
     */
    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request) {
        showList(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功!");
        return "page/wap/notice";
    }

    /**
     * 系统公告列表查询
     */
    public void showList(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = systemNoticeService.countSystemNoticeForUser();
        int pageSize = 10;

        List<SystemNoticeDO> systemNoticeList = null;
        if (totalNumber > 0) {
            systemNoticeList = systemNoticeService.listSystemNoticeForUser(pageNumber, pageSize);
        }

        if (systemNoticeList != null && systemNoticeList.size() > 0) {
            for (SystemNoticeDO systemNotice : systemNoticeList) {
                String noticeTitle = systemNotice.getNoticeTitle();
                if (StringUtil.isNotNull(noticeTitle)) {
                    noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                    systemNotice.setNoticeTitle(noticeTitle);
                }
            }
        }

        int totalPageNumber = (int) Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            String systemNoticeListJson = mapper.writeValueAsString(systemNoticeList);
            request.setAttribute("systemNoticeList", systemNoticeListJson);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("totalNumber", totalNumber);
            request.setAttribute("totalPageNumber", totalPageNumber);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
    }

    /**
     * 打开系统公告详情页面
     */
    @RequestMapping(value = "/showNoticeDetail/{idStr}", method = RequestMethod.GET)
    public String showNoticeDetail(HttpServletRequest request, @PathVariable String idStr) {
        if (!StringUtil.isNotNull(idStr)) {
            showList(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/wap/notice";
        }

        int id = 0;
        String reg = "[0-9]*";
        if (idStr.length() < 11 && Pattern.matches(reg, idStr)) {
            id = Integer.parseInt(idStr);
        }
        SystemNoticeDO systemNotice = systemNoticeService.getSystemNoticeById(id);
        if (systemNotice != null) {
            String noticeTitle = systemNotice.getNoticeTitle();
            if (StringUtil.isNotNull(noticeTitle)) {
                noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                systemNotice.setNoticeTitle(noticeTitle);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            String systemNoticeJson = mapper.writeValueAsString(systemNotice);
            request.setAttribute("systemNotice", systemNoticeJson);

        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return "page/wap/noticeDetail";

    }

    /**
     * 加载更多公告
     * @return 加载更多公告跳转到公告页面
     */
    @RequestMapping(value = "/showMoreNotice", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject showMoreNotice(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = systemNoticeService.countSystemNoticeForUser();
        int pageSize = 10;

        List<SystemNoticeDO> systemNoticeList = null;
        if (totalNumber > 0) {
            systemNoticeList = systemNoticeService.listSystemNoticeForUser(pageNumber, pageSize);
        }

        if (systemNoticeList != null && systemNoticeList.size() > 0) {
            for (SystemNoticeDO systemNotice : systemNoticeList) {
                String noticeTitle = systemNotice.getNoticeTitle();
                if (StringUtil.isNotNull(noticeTitle)) {
                    noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                    systemNotice.setNoticeTitle(noticeTitle);
                }
            }
        }

        int totalPageNumber = (int) Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        try {
            response.put("systemNoticeList", systemNoticeList);
            response.put("pageNumber", pageNumber);
            response.put("totalNumber", totalNumber);
            response.put("totalPageNumber", totalPageNumber);
            response.put("code", 1);
            response.put("message", "查询成功!");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return response;
    }
}
