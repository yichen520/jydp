package com.jydp.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IHomePageService;
import com.jydp.service.IRedisService;
import com.jydp.service.ISystemHotService;
import config.FileUrlConfig;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /**web端首页*/
    @Autowired
    private IHomePageService homePageService;

    /** 热门话题首页 */
    @RequestMapping(value = "/show.htm")
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
        int totalNumber = systemHotService.sumHotForBack(noticeType, noticeTitle);

        List<SystemHotDO> systemHotList = null;
        int pageSize = 20;
        if (totalNumber > 0) {
            systemHotList = systemHotService.listSystemHotForBack(noticeTitle, noticeType, pageNumber, pageSize);
        }

        int maxRankNumber = 0;

        if (systemHotList != null) {
            int length = systemHotList.size();
            if (length > 0) {
                maxRankNumber = systemHotList.get(length - 1).getRankNumber();
            }
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
        request.setAttribute("maxRankNumber", maxRankNumber);

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
    public @ResponseBody JSONObject addHotTopic(HttpServletRequest request, MultipartFile noticeUrl) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114002);
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
            try {
                imageUrl = FileWriteRemoteUtil.uploadFile(noticeUrl.getOriginalFilename(),
                        noticeUrl.getInputStream(), FileUrlConfig.file_remote_noticeImage_url);
            } catch (IOException e) {
                LogUtil.printErrorLog(e);
            }
        }

        Timestamp addTime = DateUtil.getCurrentTime();
        boolean addResult = systemHotService.insertSystemHot(noticeTitle, noticeType, imageUrl, content, 1, addTime);
        if (addResult) {
            response.put("code", 1);
            response.put("message", "新增成功！");
        } else {
            response.put("code", 5);
            response.put("message", "新增失败！");
        }

        return response;
    }

    /** 打开修改热门页面 */
    @RequestMapping(value = "/openUpdateHotTopic.htm", method = RequestMethod.POST)
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
        String noticeType = StringUtil.stringNullHandle(request.getParameter("query_noticeType"));
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("query_noticeTitle"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
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
        request.setAttribute("noticeType", noticeType);
        request.setAttribute("noticeTitle", noticeTitle);
        request.setAttribute("pageNumber", pageNumberStr);
        return "page/back/updateHotTopic";
    }

    /** 修改热门话题 */
    @RequestMapping(value = "/updateHotTopic.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject modifyNotice(HttpServletRequest request, MultipartFile noticeUrl) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114004);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            return response;
        }

        //获取参数
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
                if(!systemHotDO.getNoticeUrl().equals(FileUrlConfig.notice_hotTopic_defaultImage)){
                    FileWriteRemoteUtil.deleteFile(systemHotDO.getNoticeUrl());
                }
            }
        }

        boolean updateResult = systemHotService.updateSystemHot(id, noticeTitle, noticeType, imageUrl, content);
        if (updateResult) {
            response.put("code", 1);
            response.put("message", "修改成功！");
        } else {
            response.put("code", 5);
            response.put("message", "修改失败！");
        }

        return response;
    }

    /** 打开热门话题详情页面 */
    @RequestMapping(value = "/hotTopicDetails.htm", method = RequestMethod.POST)
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
        String noticeType = StringUtil.stringNullHandle(request.getParameter("query_noticeType"));
        String noticeTitle = StringUtil.stringNullHandle(request.getParameter("query_noticeTitle"));
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
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
        request.setAttribute("noticeType", noticeType);
        request.setAttribute("noticeTitle", noticeTitle);
        request.setAttribute("pageNumber", pageNumberStr);
        return "page/back/hotTopicDetails";
    }

    /** 删除热门话题 */
    @RequestMapping(value = "/deleteHotTopic.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject deleteNotice(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114006);
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

        SystemHotDO systemHotDO = systemHotService.getSystemHotById(id);
        if (systemHotDO == null) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        boolean deleteResult = systemHotService.deteleSystemHot(id);
        String imageUrl = systemHotDO.getNoticeUrl();
        if (deleteResult) {
            if(!imageUrl.equals(FileUrlConfig.notice_hotTopic_defaultImage)){
                // 删除图片
                FileWriteRemoteUtil.deleteFile(systemHotDO.getNoticeUrl());
            }
            response.put("code", 1);
            response.put("message", "删除成功！");
        } else {
            response.put("code", 5);
            response.put("message", "删除失败！");
        }

        List<SystemHotDO> systemHotDOList = homePageService.getSystemHotList();
        String hotTopicKey = RedisKeyConfig.HOMEPAGE_HOT_TOPIC;

        if (systemHotDOList != null && systemHotDOList.size() > 0) {
            redisService.addValue(hotTopicKey,systemHotDOList);
        } else {
            redisService.addValue(hotTopicKey,null);
        }

        return response;
    }

    /** 上移热门话题 */
    @RequestMapping(value = "/upMoveHotTopic.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject upMoveHotTopic(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114007);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            return response;
        }

        //获取参数
        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        if (!StringUtil.isNotNull(idStr)) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        int id = Integer.parseInt(idStr);
        //处理页面参数
        if (id <= 0) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        boolean updateResult = systemHotService.upMoveHotTopicForBack(id);
        if (updateResult) {
            response.put("code", 1);
            response.put("message", "上移成功！");
        } else {
            response.put("code", 5);
            response.put("message", "上移失败！");
        }

        return response;
    }

    /** 下移热门话题 */
    @RequestMapping(value = "/downMoveHotTopic.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject downMoveHotTopic(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114008);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            return response;
        }

        // 获取参数
        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));

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

        boolean updateResult = systemHotService.downMoveHotTopicForBack(id);
        if (updateResult) {
            response.put("code", 1);
            response.put("message", "下移成功！");
        } else {
            response.put("code", 5);
            response.put("message", "下移失败！");
        }

        return response;
    }

    /** 置顶热门话题 */
    @RequestMapping(value = "/top.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject top(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 114003);
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
        Timestamp topTime = DateUtil.getCurrentTime();
        boolean topResult = systemHotService.topHotTopic(id, topTime);
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
