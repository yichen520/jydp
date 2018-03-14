package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.service.ISystemNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统公告
 * @Author: wqq
 */
@Controller
@RequestMapping("/userWeb/webSystemNotice")
@Scope(value = "prototype")
public class WebSystemNoticeController {

    /**  系统公告 */
    @Autowired
    private ISystemNoticeService systemNoticeService;

    /** 系统公告展示  */
    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request) {
        showList(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功!");
        return  "page/web/systemNotice";
    }

    /**  系统公告列表查询 */
    public void showList(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = systemNoticeService.countSystemNoticeForUser();
        int pageSize = 20;

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

        int totalPageNumber = (int)Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        request.setAttribute("pageNumber",pageNumber);
        request.setAttribute("totalNumber",totalNumber);
        request.setAttribute("totalPageNumber",totalPageNumber);

        request.setAttribute("systemNoticeList",systemNoticeList);
    }

    /**  打开系统公告详情页面 */
    @RequestMapping(value = "/showNoticeDetail/{idStr}", method = RequestMethod.GET)
    public String showNoticeDetail(HttpServletRequest request, @PathVariable String idStr) {
        if(!StringUtil.isNotNull(idStr)){
            showList(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return  "page/web/systemNotice";
        }

        int id = 0;
        if (idStr.length() < 11) {
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

        request.setAttribute("systemNotice",systemNotice);
        return  "page/web/systemNoticeDetail";
    }
}
