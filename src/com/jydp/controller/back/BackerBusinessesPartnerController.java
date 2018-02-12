package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ISystemBusinessesPartnerService;
import config.FileUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

        //当前页为第一页时，向页面传入已经置顶的 合作商家信息
        if (pageNumber == 0 && systemBusinessesPartnerList != null
                && systemBusinessesPartnerList.size() > 0) {
            SystemBusinessesPartnerDO topBusinessesPartner = systemBusinessesPartnerList.remove(0);
            request.setAttribute("topBusinessesPartner",topBusinessesPartner);
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
    public String insert(HttpServletRequest request, MultipartFile businessesPartnerImageUrl){
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 112002);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        String businessesName = StringUtil.stringNullHandle(request.getParameter("addBusinessesName"));
        String webLinkUrl = StringUtil.stringNullHandle(request.getParameter("addWebLinkUrl"));
        String wapLinkUrl = StringUtil.stringNullHandle(request.getParameter("addWapLinkUrl"));

        //处理页面参数
        if (!StringUtil.isNotNull(businessesName) || businessesPartnerImageUrl == null || !businessesPartnerImageUrl.isEmpty()) {
            showList(request);
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接受到参数");
            return "page/back/businessesPartner";
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
            showList(request);
            request.setAttribute("code", -1);
            request.setAttribute("message", "文件服务器未响应，请稍后重试");
            return "page/back/businessesPartner";
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
            request.setAttribute("code", 1);
            request.setAttribute("message", "新增成功");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "新增失败");
        }

        showList(request);
        return "page/back/businessesPartner";
    }

    /**置顶合作商家*/
    @RequestMapping(value="/top.htm", method=RequestMethod.POST)
    public String top(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 112003);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("id"));
        //处理页面参数
        if (!StringUtil.isNotNull(idStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数");
            return "page/back/businessesPartner";
        }

        int id = Integer.parseInt(idStr);
        if (id <= 0) {
            showList(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误");
            return "page/back/businessesPartner";
        }

        boolean topResult = systemBusinessesPartnerService.topTheBusinessesPartner(id);
        if (topResult) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "置顶成功");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "置顶失败");
        }

        showList(request);
        return "page/back/businessesPartner";
    }

    /**修改合作商家*/
    @RequestMapping(value="/update.htm", method=RequestMethod.POST)
    public String update(HttpServletRequest request, MultipartFile businessesPartnerImageUrl) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 112004);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("updateId"));
        String businessesName = StringUtil.stringNullHandle(request.getParameter("updateBusinessesName"));
        String webLinkUrl = StringUtil.stringNullHandle(request.getParameter("updateWebLinkUrl"));
        String wapLinkUrl = StringUtil.stringNullHandle(request.getParameter("updateWapLinkUrl"));

        //处理页面参数
        if (!StringUtil.isNotNull(idStr) || !StringUtil.isNotNull(businessesName)) {
            showList(request);
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接受到参数");
            return "page/back/businessesPartner";
        }

        int id = Integer.parseInt(idStr);
        if (id <= 0) {
            showList(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误");
            return "page/back/businessesPartner";
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
            showList(request);
            request.setAttribute("code", 5);
            request.setAttribute("message", "文件服务器未响应，请稍后重试");
            return "page/back/businessesPartner";
        }

        SystemBusinessesPartnerDO systemBusinessesPartnerDO = systemBusinessesPartnerService.getSystemBusinessesPartnerById(id);
        if (systemBusinessesPartnerDO == null) {
            showList(request);
            request.setAttribute("code", 5);
            request.setAttribute("message", "合作商家信息错误");
            return "page/back/businessesPartner";
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

            request.setAttribute("code", 1);
            request.setAttribute("message", "修改成功");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "修改失败");
        }

        showList(request);
        return "page/back/businessesPartner";
    }

    /**删除合作商家*/
    @RequestMapping(value="/delete.htm", method=RequestMethod.POST)
    public String delete(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 112005);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        String idStr = StringUtil.stringNullHandle(request.getParameter("deleteId"));
        //处理页面参数
        if (!StringUtil.isNotNull(idStr)) {
            showList(request);
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接受到参数");
            return "page/back/businessesPartner";
        }

        int id = Integer.parseInt(idStr);
        if (id <= 0) {
            showList(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误");
            return "page/back/businessesPartner";
        }

        boolean deleteResult = systemBusinessesPartnerService.deleteSystemBusinessesPartner(id);
        if (deleteResult) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "删除成功");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "删除失败");
        }

        showList(request);
        return "page/back/businessesPartner";
    }
}
