package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ISystemHotService;
import config.FileUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 热门话题
 * @author zym
 *
 */
@Controller
@RequestMapping("/backerWeb/hotTopic")
@Scope(value = "prototype")
public class BackerHotTopicController {

    /** 热门话题 */
    @Autowired
    private ISystemHotService systemHotService;

    /** 热门话题首页 */
    @RequestMapping(value = "/show.htm", method = RequestMethod.GET)
    public String show(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        list(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/hotTopic";
    }

    /**分页查询热门话题*/
    private void list(HttpServletRequest request) {
        // 查询参数
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("noticeTitle"));
        String noticeType = StringUtil.stringNullHandle(request.getParameter("noticeType"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        // 查询数据
        int totalNumber = systemHotService.sumHotForBack(noticeTitle, noticeType);

        List<SystemHotDO> systemHotList = null;
        int pageSize = 20;
        if (totalNumber > 0) {
            systemHotList = systemHotService.listSystemHotForBack(noticeTitle, noticeType, pageNumber, pageNumber);
        }

        int totalPageNumber = (int) Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }

        //返回数据
        request.setAttribute("noticeTitle", noticeTitle);
        request.setAttribute("noticeType", noticeType);

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("systemHotList", systemHotList);

        request.getSession().setAttribute("backer_pagePowerId", 114000);
    }

    /** 打开新增热门话题页面 */
    @RequestMapping(value = "/openAddHotTopic.htm", method = RequestMethod.GET)
    public String openAddHotTopic(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114002);
        if (!havePower) {
            list(request);
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/hotTopic";
        }

        return "page/back/addHotTopic";
    }

    /** 新增热门话题 */
    @RequestMapping(value = "/addHotTopic.htm", method = RequestMethod.POST)
    public String addHotTopic(HttpServletRequest request, MultipartFile noticeUrl) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114002);
        if (!havePower) {
            list(request);
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/systemNotice";
        }

        // 获取参数
        String noticeType = StringUtil.stringNullHandle(request.getParameter("noticeType"));
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("noticeTitle"));
        String content = StringUtil.stringNullHandle(request.getParameter("content"));

        if (!StringUtil.isNotNull(noticeType) || !StringUtil.isNotNull(noticeTitle) || !StringUtil.isNotNull(content) || noticeUrl == null) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/addHotTopic";
        }

        String imageUrl = "";
        try {
            imageUrl = FileWriteRemoteUtil.uploadFile(noticeUrl.getOriginalFilename(),
                    noticeUrl.getInputStream(), FileUrlConfig.file_remote_noticeImage_url);
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }

        Timestamp addTime = DateUtil.getCurrentTime();

        boolean addResult = systemHotService.insertSystemHot(noticeTitle, noticeType, imageUrl, content, addTime, null);
        if (addResult) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "新增成功！");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "新增失败！");
        }

        list(request);
        return "page/back/hotTopic";
    }

    /** 打开修改热门页面 */
    @RequestMapping(value = "/openUpdateHotTopic.htm", method = RequestMethod.GET)
    public String openUpdateHotTopic(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114004);
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
            return "page/back/hotTopic";
        }

        int id = Integer.parseInt(idStr);
        // 处理页面参数
        if (id <= 0) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/hotTopic";
        }

        SystemHotDO systemHotDO = systemHotService.getSystemHotById(id);
        request.setAttribute("systemHotDO", systemHotDO);
        return "page/back/updateHotTopic";
    }

    /** 修改热门话题 */
    @RequestMapping(value = "/updateHotTopic.htm", method = RequestMethod.POST)
    public String modifyNotice(HttpServletRequest request, MultipartFile noticeUrl) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114004);
        if (!havePower) {
            list(request);
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/hotTopic";
        }

        //获取参数
        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        String noticeType = StringUtil.stringNullHandle(request.getParameter("noticeType"));
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("noticeTitle"));
        String content = StringUtil.stringNullHandle(request.getParameter("content"));

        if (!StringUtil.isNotNull(noticeType) || !StringUtil.isNotNull(idStr) || !StringUtil.isNotNull(noticeTitle) || !StringUtil.isNotNull(content)) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/hotTopic";
        }

        int id = Integer.parseInt(idStr);
        // 处理页面参数
        if (id <= 0) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/hotTopic";
        }

        // 判断是否修改了图片
        String imageUrl = "";
        if (noticeUrl != null && !noticeUrl.isEmpty()) {
            SystemHotDO systemHotDO  = systemHotService.getSystemHotById(id);

            try {
                imageUrl = FileWriteRemoteUtil.uploadFile(noticeUrl.getOriginalFilename(),
                        noticeUrl.getInputStream(), FileUrlConfig.file_remote_noticeImage_url);
            } catch (IOException e) {
                LogUtil.printErrorLog(e);
            }

            if (imageUrl != null && imageUrl != "") {
                FileWriteRemoteUtil.deleteFile(systemHotDO.getNoticeUrl());
            }
        }

        boolean updateResult = systemHotService.updateSystemHot(id, noticeTitle, noticeType, imageUrl, content);
        if (updateResult) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "修改成功！");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "修改失败！");
        }

        list(request);
        return "page/back/hotTopic";
    }

    /** 打开热门话题详情页面 */
    @RequestMapping(value = "/hotTopicDetails.htm", method = RequestMethod.GET)
    public String details(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114005);
        if (!havePower) {
            list(request);
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/hotTopic";
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        if (!StringUtil.isNotNull(idStr)) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/hotTopic";
        }

        int id = Integer.parseInt(idStr);
        // 处理页面参数
        if (id <= 0) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/hotTopic";
        }

        SystemHotDO systemHotDO  = systemHotService.getSystemHotById(id);

        request.setAttribute("systemHotDO", systemHotDO);
        return "page/back/hotTopicDetails";
    }

    /** 删除系统公告 */
    @RequestMapping(value = "/deleteHotTopic.htm", method = RequestMethod.POST)
    public String deleteNotice(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114006);
        if (!havePower) {
            list(request);
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/hotTopic";
        }

        // 获取参数
        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));

        if (!StringUtil.isNotNull(idStr)) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/hotTopic";
        }

        int id = Integer.parseInt(idStr);
        // 处理页面参数
        if (id <= 0) {
            request.setAttribute("code", 3);
            list(request);
            request.setAttribute("message", "参数错误！");
            return "page/back/hotTopic";
        }

        SystemHotDO systemHotDO = systemHotService.getSystemHotById(id);
        if (systemHotDO == null) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/hotTopic";
        }

        boolean deleteResult = systemHotService.deteleSystemHot(id);
        if (deleteResult) {
            // 删除图片
            FileWriteRemoteUtil.deleteFile(systemHotDO.getNoticeUrl());

            request.setAttribute("code", 1);
            request.setAttribute("message", "删除成功！");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "删除失败！");
        }

        list(request);
        return "page/back/hotTopic";
    }

    /** 置顶热门话题 */
    @RequestMapping(value = "/top.htm", method = RequestMethod.GET)
    public String top(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114003);
        if (!havePower) {
            list(request);
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/hotTopic";
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));

        // 处理页面参数
        if (!StringUtil.isNotNull(idStr)) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误");
            return "page/back/hotTopic";
        }

        int id = Integer.parseInt(idStr);
        if (id <= 0) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误");
            return "page/back/hotTopic";
        }
        Timestamp topTime = DateUtil.getCurrentTime();
        boolean topResult = systemHotService.topHotTopic(id, topTime);
        if (topResult) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "置顶成功");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "置顶失败");
        }

        list(request);
        return "page/back/hotTopic";
    }
}