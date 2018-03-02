package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IHomePageService;
import com.jydp.service.IRedisService;
import com.jydp.service.ISystemBusinessesPartnerService;
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
 * 合作伙伴
 * @author zym
 *
 */
@Controller
@RequestMapping("/backerWeb/backerBusinessesPartner")
@Scope(value="prototype")
public class BackerBusinessesPartnerController {

    /**合作商家 */
    @Autowired
    private ISystemBusinessesPartnerService systemBusinessesPartnerService;

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /**web端首页*/
    @Autowired
    private IHomePageService homePageService;

    /** 展示合作商家页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 112001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        showList(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功");
        return "page/back/businessesPartner";
    }

    /**分页查询商家数据 */
    private void showList(HttpServletRequest request) {
        //查询参数
        String pageNumberStr  = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        //查询数据
        int totalNumber = systemBusinessesPartnerService.countSystemBusinessesPartner();

        List<SystemBusinessesPartnerDO> systemBusinessesPartnerList = null;
        int pageSize = 20;
        if (totalNumber > 0) {
            systemBusinessesPartnerList = systemBusinessesPartnerService.listSystemBusinessesPartnerByPage(pageNumber, pageSize);
        }

        int totalPageNumber = (int) Math.ceil(totalNumber/(pageSize*1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }

        //返回数据
        request.setAttribute("pageNumber",pageNumber);
        request.setAttribute("totalNumber",totalNumber);
        request.setAttribute("totalPageNumber",totalPageNumber);
        request.setAttribute("systemBusinessesPartnerList",systemBusinessesPartnerList);

        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 112000);
    }

    /**新增合作商家*/
    @RequestMapping(value="/add.htm", method=RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO insert(HttpServletRequest request, MultipartFile businessesPartnerImageUrl){
        JsonObjectBO responsJson = new JsonObjectBO();
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 112002);
        if (!havePower) {
            responsJson.setCode(6);
            responsJson.setMessage("您没有该权限");
            return responsJson;
        }

        String businessesName = StringUtil.stringNullHandle(request.getParameter("addBusinessesName"));
        String webLinkUrl = StringUtil.stringNullHandle(request.getParameter("addWebLinkUrl"));
        String wapLinkUrl = StringUtil.stringNullHandle(request.getParameter("addWapLinkUrl"));

        //处理页面参数
        if (!StringUtil.isNotNull(businessesName) || businessesPartnerImageUrl == null ) {
            responsJson.setCode(2);
            responsJson.setMessage("未接受到参数");
            return responsJson;
        }

        //图片上传
        String imgUrl = null;
        try {
            imgUrl = FileWriteRemoteUtil.uploadFile(businessesPartnerImageUrl.getOriginalFilename(),
                    businessesPartnerImageUrl.getInputStream(), FileUrlConfig.file_remote_systemBusinessesPartner_url);
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }
        if (!StringUtil.isNotNull(imgUrl)) {
            responsJson.setCode(-1);
            responsJson.setMessage("文件服务器未响应，请稍后重试");
            return responsJson;
        }

        //封装参数
        SystemBusinessesPartnerDO systemBusinessesPartner = new SystemBusinessesPartnerDO();
        systemBusinessesPartner.setBusinessesName(businessesName);  //商家名称
        systemBusinessesPartner.setBusinessesImageUrl(imgUrl);  //商家图片地址
        systemBusinessesPartner.setWebLinkUrl(webLinkUrl);  //web端链接地址
        systemBusinessesPartner.setWapLinkUrl(wapLinkUrl);  //wap端链接地址
        systemBusinessesPartner.setAddTime(DateUtil.getCurrentTime());  //添加时间

        boolean insertResult = systemBusinessesPartnerService.insertSystemBusinessesPartner(systemBusinessesPartner);
        if (insertResult) {
            responsJson.setCode(1);
            responsJson.setMessage("新增成功");
        } else {
            responsJson.setCode(5);
            responsJson.setMessage("新增失败");
        }

        return responsJson;
    }

    /**置顶合作商家*/
    @RequestMapping(value="/top.htm", method=RequestMethod.POST)
    public @ResponseBody JsonObjectBO top(HttpServletRequest request) {
        JsonObjectBO responsJson = new JsonObjectBO();
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 112003);
        if (!havePower) {
            responsJson.setCode(6);
            responsJson.setMessage("您没有该权限");
            return responsJson;
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        //处理页面参数
        if (!StringUtil.isNotNull(idStr)) {
            responsJson.setCode(2);
            responsJson.setMessage("未接收到参数");
            return responsJson;
        }

        int id = Integer.parseInt(idStr);
        if (id <= 0) {
            responsJson.setCode(3);
            responsJson.setMessage("参数错误");
            return responsJson;
        }

        Timestamp topTime = DateUtil.getCurrentTime();
        boolean topResult = systemBusinessesPartnerService.topTheBusinessesPartner(id, topTime);
        if (topResult) {
            responsJson.setCode(1);
            responsJson.setMessage("置顶成功");
        } else {
            responsJson.setCode(5);
            responsJson.setMessage("置顶失败");
        }

        return responsJson;
    }

    /**修改合作商家*/
    @RequestMapping(value="/update.htm", method=RequestMethod.POST)
    public @ResponseBody JsonObjectBO update(HttpServletRequest request, MultipartFile businessesPartnerImageUrl) {
        JsonObjectBO responsJson = new JsonObjectBO();
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 112004);
        if (!havePower) {
            responsJson.setCode(6);
            responsJson.setMessage("您没有该权限");
            return responsJson;
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("updateId"));
        String businessesName = StringUtil.stringNullHandle(request.getParameter("updateBusinessesName"));
        String webLinkUrl = StringUtil.stringNullHandle(request.getParameter("updateWebLinkUrl"));
        String wapLinkUrl = StringUtil.stringNullHandle(request.getParameter("updateWapLinkUrl"));

        //处理页面参数
        if (!StringUtil.isNotNull(idStr) || !StringUtil.isNotNull(businessesName)) {
            responsJson.setCode(2);
            responsJson.setMessage("未接受到参数");
            return responsJson;
        }

        int id = Integer.parseInt(idStr);
        if (id <= 0) {
            responsJson.setCode(3);
            responsJson.setMessage("参数错误");
            return responsJson;
        }

        //获取用户上传的图片
        String imgUrl = null;
        if (businessesPartnerImageUrl != null && !businessesPartnerImageUrl.isEmpty()) {
            try {
                imgUrl = FileWriteRemoteUtil.uploadFile(businessesPartnerImageUrl.getOriginalFilename(),
                        businessesPartnerImageUrl.getInputStream(), FileUrlConfig.file_remote_systemBusinessesPartner_url);
            } catch (IOException e) {
                LogUtil.printErrorLog(e);
            }
        }
        if (businessesPartnerImageUrl != null && !businessesPartnerImageUrl.isEmpty() && !StringUtil.isNotNull(imgUrl)) {
            responsJson.setCode(5);
            responsJson.setMessage("文件服务器未响应，请稍后重试");
            return responsJson;
        }

        SystemBusinessesPartnerDO systemBusinessesPartnerDO = systemBusinessesPartnerService.getSystemBusinessesPartnerById(id);
        if (systemBusinessesPartnerDO == null) {
            responsJson.setCode(5);
            responsJson.setMessage("合作商家信息错误");
            return responsJson;
        }

        //封装参数
        SystemBusinessesPartnerDO systemBusinessesPartner = new SystemBusinessesPartnerDO();
        systemBusinessesPartner.setId(id);  //记录Id
        systemBusinessesPartner.setBusinessesName(businessesName);  //商家名称
        systemBusinessesPartner.setBusinessesImageUrl(imgUrl);  //商家图片地址
        systemBusinessesPartner.setWebLinkUrl(webLinkUrl);  //web端链接地址
        systemBusinessesPartner.setWapLinkUrl(wapLinkUrl);  //wap端链接地址

        boolean updateResult = systemBusinessesPartnerService.updateSystemBusinessesPartner(systemBusinessesPartner);
        if (updateResult) {
            //商家图片已更新，删除之前的图片
            if (StringUtil.isNotNull(imgUrl) && systemBusinessesPartnerDO.getBusinessesImageUrl() != null) {
                FileWriteRemoteUtil.deleteFile(systemBusinessesPartnerDO.getBusinessesImageUrl());
            }

            responsJson.setCode(1);
            responsJson.setMessage("修改成功");
        } else {
            responsJson.setCode(5);
            responsJson.setMessage("修改失败");
        }

        return responsJson;
    }

    /**删除合作商家*/
    @RequestMapping(value="/delete.htm", method=RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO delete(HttpServletRequest request) {
        JsonObjectBO responsJson = new JsonObjectBO();
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 112005);
        if (!havePower) {
            responsJson.setCode(6);
            responsJson.setMessage("您没有该权限");
            return responsJson;
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("deleteId"));
        //处理页面参数
        if (!StringUtil.isNotNull(idStr)) {
            responsJson.setCode(2);
            responsJson.setMessage("未接受到参数");
            return responsJson;
        }

        int id = Integer.parseInt(idStr);
        if (id <= 0) {
            responsJson.setCode(3);
            responsJson.setMessage("参数错误");
            return responsJson;
        }

        boolean deleteResult = systemBusinessesPartnerService.deleteSystemBusinessesPartner(id);
        if (deleteResult) {
            responsJson.setCode(1);
            responsJson.setMessage("删除成功");
        } else {
            responsJson.setCode(5);
            responsJson.setMessage("删除失败");
        }

        //查询合作商家列表
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerDOList = homePageService.getSystemBusinessesPartnerList();
        String partnerKey = RedisKeyConfig.HOMEPAGE_PARTNER;

        if (systemBusinessesPartnerDOList != null && systemBusinessesPartnerDOList.size() > 0) {
            redisService.addValue(partnerKey,systemBusinessesPartnerDOList);
        } else {
            redisService.addValue(partnerKey,null);
        }

        return responsJson;
    }
}
