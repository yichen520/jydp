package com.jydp.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.ImageReduceUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IHomePageService;
import com.jydp.service.IRedisService;
import com.jydp.service.ISystemNoticeService;
import config.FileUrlConfig;
import config.RedisKeyConfig;
import config.SystemHelpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

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

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /**web端首页*/
    @Autowired
    private IHomePageService homePageService;

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
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        // 查询数据
        int totalNumber = systemNoticeService.sumNoticeForBack(noticeType, noticeTitle);

        List<SystemNoticeDO> systemNoticeList = null;
        int pageSize = 20;

        int totalPageNumber = (int) Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }

        if (totalNumber > 0) {
            systemNoticeList = systemNoticeService.getNoticeForBack(noticeType, noticeTitle, pageNumber, pageSize);
        }

        int maxRankNumber = 0;

        if (systemNoticeList != null) {
            int length = systemNoticeList.size();
            if (length > 0) {
                maxRankNumber = systemNoticeList.get(length - 1).getRankNumber();
            }
            for (SystemNoticeDO systemNotice:systemNoticeList) {
                String noticeTypes = HtmlUtils.htmlEscape(systemNotice.getNoticeType());
                systemNotice.setNoticeType(noticeTypes);

                String noticeTitles = HtmlUtils.htmlEscape(systemNotice.getNoticeTitle());
                systemNotice.setNoticeTitle(noticeTitles);
            }
        }
        // 返回数据
        request.setAttribute("noticeType", noticeType);
        request.setAttribute("noticeTitle", noticeTitle);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("maxRankNumber", maxRankNumber);
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
            return response;
        }

        // 获取参数
        String noticeType = StringUtil.stringNullHandle(request.getParameter("noticeType"));
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("noticeTitle"));
        String content = StringUtil.stringNullHandle(request.getParameter("content"));
        if (!StringUtil.isNotNull(noticeType) || !StringUtil.isNotNull(noticeTitle) || !StringUtil.isNotNull(content)) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        String imageUrl = "";
        if(noticeUrl == null){
            imageUrl = FileUrlConfig.notice_hotTopic_defaultImage;
        }else{
            /*try {
                imageUrl = FileWriteRemoteUtil.uploadFile(noticeUrl.getOriginalFilename(),
                        noticeUrl.getInputStream(), FileUrlConfig.file_remote_noticeImage_url);
            } catch (IOException e) {
                LogUtil.printErrorLog(e);
            }*/
            imageUrl = ImageReduceUtil.reduceImageUploadRemote
                    (noticeUrl, request, FileUrlConfig.file_remote_noticeImage_url);
        }

        SystemNoticeDO systemNoticeDO = new SystemNoticeDO();
        systemNoticeDO.setNoticeTitle(noticeTitle);
        systemNoticeDO.setNoticeType(noticeType);
        systemNoticeDO.setContent(content);
        systemNoticeDO.setNoticeUrl(imageUrl);
        systemNoticeDO.setRankNumber(1);
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
    @RequestMapping(value = "/openModifyPage.htm", method = RequestMethod.POST)
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
        String noticeType = StringUtil.stringNullHandle(request.getParameter("query_noticeType"));
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("query_noticeTitle"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
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
        request.setAttribute("noticeType", noticeType);
        request.setAttribute("noticeTitle", noticeTitle);
        request.setAttribute("pageNumber", pageNumberStr);
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
            /*try {
                imageUrl = FileWriteRemoteUtil.uploadFile(noticeUrl.getOriginalFilename(),
                        noticeUrl.getInputStream(), FileUrlConfig.file_remote_noticeImage_url);
            } catch (IOException e) {
                LogUtil.printErrorLog(e);
            }*/
            imageUrl = ImageReduceUtil.reduceImageUploadRemote
                    (noticeUrl, request, FileUrlConfig.file_remote_noticeImage_url);

            if (imageUrl != null && !imageUrl.equals("")) {
                if(!systemNoticeDO.getNoticeUrl().equals(FileUrlConfig.notice_hotTopic_defaultImage)){
                    FileWriteRemoteUtil.deleteFile(systemNoticeDO.getNoticeUrl());
                }
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
    @RequestMapping(value = "/details.htm/{idStr}", method = RequestMethod.GET)
    public String details(HttpServletRequest request, @PathVariable String idStr) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113005);
        if (!havePower) {
            list(request);
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/systemNotice";
        }

        String noticeType = StringUtil.stringNullHandle(request.getParameter("query_noticeType"));
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("query_noticeTitle"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        if (!StringUtil.isNotNull(idStr)) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/systemNotice";
        }

        int id = 0;
        String reg = "[0-9]*";
        if (idStr.length() < 11 && Pattern.matches(reg,idStr)) {
            id = Integer.parseInt(idStr);
        }

        // 处理页面参数
        if (id < 0) {
            list(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return "page/back/systemNotice";
        }

        SystemNoticeDO systemNoticeDO = systemNoticeService.getSystemNoticeById(id);

        String noticeTypes = HtmlUtils.htmlEscape(systemNoticeDO.getNoticeType());
        systemNoticeDO.setNoticeType(noticeTypes);

        String noticeTitles = HtmlUtils.htmlEscape(systemNoticeDO.getNoticeTitle());
        systemNoticeDO.setNoticeTitle(noticeTitles);

        request.setAttribute("noticeType", noticeType);
        request.setAttribute("noticeTitle", noticeTitle);
        request.setAttribute("pageNumber", pageNumberStr);
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
        String imageUrl = systemNoticeDO.getNoticeUrl();
        if (deleteResult) {
            if(!imageUrl.equals(FileUrlConfig.notice_hotTopic_defaultImage)){
                // 删除图片
                FileWriteRemoteUtil.deleteFile(systemNoticeDO.getNoticeUrl());
            }
            response.put("code", 1);
            response.put("message", "删除成功！");
        } else {
            response.put("code", 5);
            response.put("message", "删除失败！");
        }

        //查询系统公告列表
        List<SystemNoticeDO> systemNoticeDOList = homePageService.getSystemNoticeList();
        String noticeKey = RedisKeyConfig.HOMEPAGE_NOTICE;

        if (systemNoticeDOList != null && systemNoticeDOList.size() > 0) {
            redisService.addValue(noticeKey,systemNoticeDOList);
        } else {
            redisService.addValue(noticeKey,null);
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

    /** 上移用户公告 */
    @RequestMapping(value = "/upMoveNotice.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject upMoveNotice(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113003);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
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

        boolean updateResult = systemNoticeService.upMoveNoticeForBack(id);
        if (updateResult) {
            response.put("code", 1);
            response.put("message", "上移成功！");
        } else {
            response.put("code", 5);
            response.put("message", "上移失败！");
        }

        return response;

    }

    /** 下移用户公告 */
    @RequestMapping(value = "/downMoveNotice.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject downMoveNotice(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 113003);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
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

        boolean updateResult = systemNoticeService.downMoveNoticeForBack(id);
        if (updateResult) {
            response.put("code", 1);
            response.put("message", "下移成功！");
        } else {
            response.put("code", 5);
            response.put("message", "下移失败！");
        }

        return response;

    }
}
