package com.jydp.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ISystemNoticeService;
import config.FileUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 用户公告管理
 * @author zym
 *
 */
@Controller
@RequestMapping("/backerWeb/backerNotice")
@Scope(value = "prototype")
public class BackerNoticeController {

    /**用户公告管理 */
    @Autowired
    private ISystemNoticeService systemNoticeService;

    /** 用户公告管理 首页 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        list(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/systemNotice";
    }

    /** 分页查询用户公告管理 */
    private void list(HttpServletRequest request) {
        // 查询参数
        String noticeType = StringUtil.stringNullHandle(request.getParameter("query_noticeType"));
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("query_noticeTitle"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("query_pageNumber"));
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        // 查询数据
        int totalNumber = systemNoticeService.sumNoticeForBack(noticeType, noticeTitle);

        List<SystemNoticeDO> systemNoticeList = null;
        int pageSize = 20;
        if (totalNumber > 0) {
            systemNoticeList = systemNoticeService.getNoticeForBack(noticeType, noticeTitle, pageNumber, pageSize);
        }

        int totalPageNumber = (int) Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }

        // 返回数据
        request.setAttribute("noticeType", noticeType);
        request.setAttribute("noticeTitle", noticeTitle);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("systemNoticeList", systemNoticeList);

        request.getSession().setAttribute("backer_pagePowerId", 113000);
    }

    /** 打开新增公告页面 */
    @RequestMapping(value = "/openAddPage.htm", method = RequestMethod.GET)
    public String openAddPage(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113002);
        if (!havePower) {
            list(request);
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/systemNotice";
        }

        return "page/back/systemNoticeAdd";
    }

    /** 新增用户公告*/
    @RequestMapping(value = "/addNotice.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject addNotice(HttpServletRequest request, MultipartFile noticeUrl) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113002);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return response;
        }

        // 获取参数
        String noticeType = StringUtil.stringNullHandle(request.getParameter("noticeType"));
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("noticeTitle"));
        String content = StringUtil.stringNullHandle(request.getParameter("content"));
        if (!StringUtil.isNotNull(noticeType) || !StringUtil.isNotNull(noticeTitle) || !StringUtil.isNotNull(content) || noticeUrl == null) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        String imageUrl = "";
        try {
            imageUrl = FileWriteRemoteUtil.uploadFile(noticeUrl.getOriginalFilename(),
                    noticeUrl.getInputStream(), FileUrlConfig.file_remote_noticeImage_url);
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }

        SystemNoticeDO systemNoticeDO = new SystemNoticeDO();
        systemNoticeDO.setNoticeTitle(noticeTitle);
        systemNoticeDO.setNoticeType(noticeType);
        systemNoticeDO.setContent(content);
        systemNoticeDO.setNoticeUrl(imageUrl);
        systemNoticeDO.setAddTime(DateUtil.getCurrentTime());

        boolean addResult = systemNoticeService.insertSystemNotice(systemNoticeDO);
        if (addResult) {
            response.put("code", 1);
            response.put("message", "新增成功！");
        } else {
            response.put("code", 5);
            response.put("message", "新增失败！");
        }

        return response;
    }

    /** 打开修改用户公告页面 */
    @RequestMapping(value = "/openModifyPage.htm", method = RequestMethod.GET)
    public String openModifyPage(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113004);
        if (!havePower) {
            list(request);
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/systemNotice";
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        if (!StringUtil.isNotNull(idStr)) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/systemNotice";
        }

        int id = Integer.parseInt(idStr);
        // 处理页面参数
        if (id <= 0) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/systemNotice";
        }

        SystemNoticeDO systemNoticeDO = systemNoticeService.getSystemNoticeById(id);

        request.setAttribute("systemNoticeDO", systemNoticeDO);
        return "page/back/systemNoticeModify";
    }

    /** 修改用户公告 */
    @RequestMapping(value = "/modifyNotice.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject modifyNotice(HttpServletRequest request, MultipartFile noticeUrl) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113004);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return response;
        }

        // 获取参数
        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        String noticeType = StringUtil.stringNullHandle(request.getParameter("noticeType"));
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("noticeTitle"));
        String content = StringUtil.stringNullHandle(request.getParameter("content"));
        if (!StringUtil.isNotNull(noticeType) || !StringUtil.isNotNull(idStr) || !StringUtil.isNotNull(noticeTitle) || !StringUtil.isNotNull(content)) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        int id = Integer.parseInt(idStr);
        // 处理页面参数
        if (id <= 0) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        SystemNoticeDO updateDO = new SystemNoticeDO();
        updateDO.setId(id);
        updateDO.setNoticeTitle(noticeTitle);
        updateDO.setNoticeType(noticeType);
        updateDO.setContent(content);

        // 判断是否修改了图片
        if (noticeUrl != null && !noticeUrl.isEmpty()) {
            SystemNoticeDO systemNoticeDO = systemNoticeService.getSystemNoticeById(id);
            String imageUrl = "";
            try {
                imageUrl = FileWriteRemoteUtil.uploadFile(noticeUrl.getOriginalFilename(),
                        noticeUrl.getInputStream(), FileUrlConfig.file_remote_noticeImage_url);
            } catch (IOException e) {
                LogUtil.printErrorLog(e);
            }

            if (imageUrl != null && imageUrl != "") {
                FileWriteRemoteUtil.deleteFile(systemNoticeDO.getNoticeUrl());
            }
            updateDO.setNoticeUrl(imageUrl);
        }

        boolean updateResult = systemNoticeService.updateSystemNotice(updateDO);
        if (updateResult) {
            response.put("code", 1);
            response.put("message", "修改成功！");
        } else {
            response.put("code", 5);
            response.put("message", "修改失败！");
        }

        return response;
    }

    /** 打开用户公告详情页面 */
    @RequestMapping(value = "/details.htm", method = RequestMethod.GET)
    public String details(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113005);
        if (!havePower) {
            list(request);
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/systemNotice";
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        if (!StringUtil.isNotNull(idStr)) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/systemNotice";
        }

        int id = Integer.parseInt(idStr);
        // 处理页面参数
        if (id <= 0) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/systemNotice";
        }

        SystemNoticeDO systemNoticeDO = systemNoticeService.getSystemNoticeById(id);

        request.setAttribute("systemNoticeDO", systemNoticeDO);
        return "page/back/systemNoticeDetails";
    }

    /** 删除系用户公告 */
    @RequestMapping(value = "/deleteNotice.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject deleteNotice(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113006);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return response;
        }

        // 获取参数
        String idStr = StringUtil.stringNullHandle(request.getParameter("deleteId"));
        if (!StringUtil.isNotNull(idStr)) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        int id = Integer.parseInt(idStr);
        // 处理页面参数
        if (id <= 0) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        SystemNoticeDO systemNoticeDO = systemNoticeService.getSystemNoticeById(id);
        if (systemNoticeDO == null) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        boolean deleteResult = systemNoticeService.deleteSystemNotice(id);
        if (deleteResult) {
            // 删除图片
            FileWriteRemoteUtil.deleteFile(systemNoticeDO.getNoticeUrl());

            response.put("code", 1);
            response.put("message", "删除成功！");
        } else {
            response.put("code", 5);
            response.put("message", "删除失败！");
        }

        return response;
    }

    /** 置顶用户公告 */
    @RequestMapping(value = "/top.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject top(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113003);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return response;
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));

        // 处理页面参数
        if (!StringUtil.isNotNull(idStr)) {
            response.put("code", 3);
            response.put("message", "参数错误");
            return response;
        }

        int id = Integer.parseInt(idStr);
        if (id <= 0) {
            response.put("code", 3);
            response.put("message", "参数错误");
            return response;
        }

        boolean topResult = systemNoticeService.topSystemNotice(id);
        if (topResult) {
            response.put("code", 1);
            response.put("message", "置顶成功");
        } else {
            response.put("code", 5);
            response.put("message", "置顶失败");
        }

        return response;
    }
}
