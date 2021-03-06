package com.jydp.controller.back;


import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.ImageReduceUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IHomePageService;
import com.jydp.service.IRedisService;
import com.jydp.service.ISystemAdsHomepagesService;
import config.FileUrlConfig;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页广告
 * @author zym
 *
 */
@Controller
@RequestMapping("/backerWeb/backerAdsHomepages")
@Scope(value = "prototype")
public class BackerAdsHomepagesController {

    /** 首页广告 */
    @Autowired
    private ISystemAdsHomepagesService systemAdsHomepagesService;

    /**web端首页*/
    @Autowired
    private IHomePageService homePageService;

    /** redis服务 */
    @Autowired
    private IRedisService redisService;


    /** 首页广告 首页 */
    @RequestMapping(value = "/show.htm", method = RequestMethod.GET)
    public String show(HttpServletRequest request) {
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 111001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        list(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/adsHomepages";
    }

    /** 查询首页广告 */
    private void list(HttpServletRequest request) {
        // 查询参数
        int maxRankNumber = 0;

        // 数据库是否有广告信息
        List<SystemAdsHomepagesDO> homeAdList = systemAdsHomepagesService.getAdsHomepagesForBack();
        if (homeAdList.size() != 0) {
            maxRankNumber = systemAdsHomepagesService.getMaxRankForBack();

            for (SystemAdsHomepagesDO homeAd:homeAdList) {
                String adsTitle = HtmlUtils.htmlEscape(homeAd.getAdsTitle());
                homeAd.setAdsTitle(adsTitle);

                String webLinkUrl = HtmlUtils.htmlEscape(homeAd.getWebLinkUrl());
                homeAd.setWebLinkUrl(webLinkUrl);

                String wapLinkUrl = HtmlUtils.htmlEscape(homeAd.getWapLinkUrl());
                homeAd.setWapLinkUrl(wapLinkUrl);
            }
        }

        request.setAttribute("homeAdList", homeAdList);
        request.setAttribute("maxRankNumber", maxRankNumber);
        request.getSession().setAttribute("backer_pagePowerId", 111000);
    }

    /** 新增首页广告 */
    @RequestMapping(value = "/addHomeAd.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject addHomeAd(HttpServletRequest request, MultipartFile adsImageUrl) {
        JSONObject response = new JSONObject();
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 111002);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            return response;

        }

        //获取参数
        String adsTitle = StringUtil.stringNullHandle(request.getParameter("adsTitle"));
        String webLinkUrl = StringUtil.stringNullHandle(request.getParameter("webLinkUrl"));
        String wapLinkUrl = StringUtil.stringNullHandle(request.getParameter("wapLinkUrl"));
        if (!StringUtil.isNotNull(adsTitle) || adsImageUrl == null || adsImageUrl.isEmpty()) {
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        int maxRankNumber = 0;

        // 数据库是否有广告信息
        List<SystemAdsHomepagesDO> homeAdList = systemAdsHomepagesService.getAdsHomepagesForBack();
        if (homeAdList.size() != 0) {
            maxRankNumber = systemAdsHomepagesService.getMaxRankForBack();
        }

        String imageUrl = ImageReduceUtil.reduceImageUploadRemote(adsImageUrl, FileUrlConfig.file_remote_adImage_url);
        if (imageUrl.equals("") || imageUrl == null) {
             response.put("code", 3);
             response.put("message", "新增失败！");
             return response;
         }
        SystemAdsHomepagesDO systemAdsHomepagesDO = new SystemAdsHomepagesDO();
        systemAdsHomepagesDO.setAdsTitle(adsTitle);
        systemAdsHomepagesDO.setWebLinkUrl(webLinkUrl);
        systemAdsHomepagesDO.setWapLinkUrl(wapLinkUrl);
        systemAdsHomepagesDO.setRankNumber(1);
        systemAdsHomepagesDO.setAddTime(DateUtil.getCurrentTime());
        systemAdsHomepagesDO.setAdsImageUrl(imageUrl);

        boolean addResult = systemAdsHomepagesService.insertSystemAdsHomePages(systemAdsHomepagesDO);
        if (addResult) {
            response.put("code", 1);
            response.put("message", "新增成功！");
        } else {
            response.put("code", 5);
            response.put("message", "新增失败！");
        }

        return response;
    }

    /** 修改首页广告 */
    @RequestMapping(value = "/modifyHomeAd.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject modifyHomeAd(HttpServletRequest request, MultipartFile adsImageUrl) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 111005);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            return response;
        }

        //获取参数
        String idStr = StringUtil.stringNullHandle(request.getParameter("modifyId"));
        String adsTitle = StringUtil.stringNullHandle(request.getParameter("modifyAdsTitle"));
        String webLinkUrl = StringUtil.stringNullHandle(request.getParameter("modifyWebLinkUrl"));
        String wapLinkUrl = StringUtil.stringNullHandle(request.getParameter("modifyWapLinkUrl"));

        if (!StringUtil.isNotNull(adsTitle) || !StringUtil.isNotNull(idStr)) {
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

        SystemAdsHomepagesDO updateDO = new SystemAdsHomepagesDO();
        updateDO.setAdsTitle(adsTitle);
        updateDO.setWebLinkUrl(webLinkUrl);
        updateDO.setWapLinkUrl(wapLinkUrl);
        updateDO.setId(id);

        //判断是否修改了图片
        if (adsImageUrl != null && !adsImageUrl.isEmpty()) {
            SystemAdsHomepagesDO systemAdsHomePagesDO = systemAdsHomepagesService.getSystemAdsHomePagesById(id);
            String imageUrl = ImageReduceUtil.reduceImageUploadRemote(adsImageUrl, FileUrlConfig.file_remote_adImage_url);

            if (imageUrl != null && !imageUrl.equals("")) {
                FileWriteRemoteUtil.deleteFile(systemAdsHomePagesDO.getAdsImageUrl());
            }
            updateDO.setAdsImageUrl(imageUrl);
        }

        boolean updateResult = systemAdsHomepagesService.updateSystemAdsHomePages(updateDO);
        if (updateResult) {
            response.put("code", 1);
            response.put("message", "修改成功！");
        } else {
            response.put("code", 5);
            response.put("message", "修改失败！");
        }

        return response;
    }

    /** 删除首页广告 */
    @RequestMapping(value = "/deleteHomeAd.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject deleteHomeAd(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 111006);
        if (!havePower) {
            response.put("code", 6);
            response.put("message", "您没有该权限");
            return response;
        }

        //获取参数
        String idStr = StringUtil.stringNullHandle(request.getParameter("deleteId"));

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

        SystemAdsHomepagesDO systemAdsHomepagesDO = systemAdsHomepagesService.getSystemAdsHomePagesById(id);
        if (systemAdsHomepagesDO == null) {
            list(request);
            response.put("code", 3);
            response.put("message", "参数错误！");
            return response;
        }

        boolean deleteResult = systemAdsHomepagesService.deleteSystemAdsHomePages(systemAdsHomepagesDO);
        if (deleteResult) {
            // 删除图片
            FileWriteRemoteUtil.deleteFile(systemAdsHomepagesDO.getAdsImageUrl());

            response.put("code", 1);
            response.put("message", "删除成功！");
        } else {
            response.put("code", 5);
            response.put("message", "删除失败！");
        }

        List<SystemAdsHomepagesDO> systemAdsHomepagesDOList = homePageService.getSystemAdsHomepageList();
        String adsKey = RedisKeyConfig.HOMEPAGE_ADS;

        if (systemAdsHomepagesDOList != null && systemAdsHomepagesDOList.size() > 0) {
            redisService.addValue(adsKey,systemAdsHomepagesDOList);
        } else {
            redisService.addValue(adsKey,null);
        }

        return response;
    }

    /** 上移首页广告 */
    @RequestMapping(value = "/upMoveHomeAd.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject upMoveHomeAd(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 111003);
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

        boolean updateResult = systemAdsHomepagesService.upMoveAdsHomepagesForBack(id);
        if (updateResult) {
            response.put("code", 1);
            response.put("message", "上移成功！");
        } else {
            response.put("code", 5);
            response.put("message", "上移失败！");
        }

        return response;
    }

    /** 下移首页广告 */
    @RequestMapping(value = "/downMoveHomeAd.htm", method = RequestMethod.POST)
    public @ResponseBody JSONObject downMoveHomeAd(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        // 业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 111004);
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

        boolean updateResult = systemAdsHomepagesService.downMoveAdsHomepagesForBack(id);
        if (updateResult) {
            response.put("code", 1);
            response.put("message", "下移成功！");
        } else {
            response.put("code", 5);
            response.put("message", "下移失败！");
        }

        return response;
    }

    /** 置顶首页公告 */
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

        boolean topResult = systemAdsHomepagesService.topAdsHomepages(id);
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
