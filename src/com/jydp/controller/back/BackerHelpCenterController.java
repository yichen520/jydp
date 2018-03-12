package com.jydp.controller.back;

import config.SystemHelpConfig;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.system.SystemHelpDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.ISystemHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 帮助中心
 * @author zym
 *
 */
@Controller
@RequestMapping("/backerWeb/helpCenter")
@Scope(value = "prototype")
public class BackerHelpCenterController {

    /** 帮助中心 */
    @Autowired
    private ISystemHelpService systemHelpService;

    /** 查询帮助信息 */
    public void selectOne(HttpServletRequest request) {
        String helpIdStr = StringUtil.stringNullHandle(request.getParameter("helpId"));
        int helpId = SystemHelpConfig.REGISTER_AGREEMENT;
        if (StringUtil.isNotNull(helpIdStr)) {
            helpId = Integer.parseInt(helpIdStr);
        }

        SystemHelpDO systemHelp = systemHelpService.getSystemHelpById(helpId);
        // 通过ArrayList构造函数把map.entrySet()转换成list
        List<Map.Entry<Integer, String>> helpTitleList =
                new ArrayList<Map.Entry<Integer, String>>(SystemHelpConfig.userHelpMap.entrySet());
        // 通过比较器实现比较排序
        Collections.sort(helpTitleList, new Comparator<Map.Entry<Integer, String>>() {
            public int compare(Map.Entry<Integer, String> mapping1, Map.Entry<Integer, String> mapping2) {
                return mapping1.getKey().compareTo(mapping2.getKey());
            }
        });

        request.setAttribute("helpId", helpId);
        request.setAttribute("helpTitleList", helpTitleList);
        request.setAttribute("systemHelp", systemHelp);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 115000);
    }

    /** 展示帮助中心页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 115001);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_pagePowerId", 0);
            return "page/back/index";
        }

        selectOne(request);
        return "page/back/helpCenter";
    }

    /** 提交帮助信息 */
    @RequestMapping(value = "/submitHelp.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO submitHelp(HttpServletRequest request) {
        JsonObjectBO responsJson = new JsonObjectBO();

        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 115002);
        if (!havePower) {
            responsJson.setCode(6);
            responsJson.setMessage("您没有该权限");
            return responsJson;
        }

        String helpIdStr = StringUtil.stringNullHandle(request.getParameter("helpId"));
        String content = StringUtil.stringNullHandle(request.getParameter("submit_content"));
        if (!StringUtil.isNotNull(helpIdStr) || !StringUtil.isNotNull(content)) {
            responsJson.setCode(3);
            responsJson.setMessage("参数错误");
            return responsJson;
        }

        int helpId = Integer.parseInt(helpIdStr);
        if (!SystemHelpConfig.userHelpMap.containsKey(helpId)) {
            responsJson.setCode(3);
            responsJson.setMessage("参数错误");
            return responsJson;
        }

        SystemHelpDO systemHelp = new SystemHelpDO();
        systemHelp.setId(helpId);
        systemHelp.setHelpType("用户帮助");
        systemHelp.setHelpTitle(SystemHelpConfig.userHelpMap.get(helpId));
        systemHelp.setContent(content);
        systemHelp.setAddTime(DateUtil.getCurrentTime());

        boolean updateResult = systemHelpService.updateSystemHelp(systemHelp);
        if (!updateResult) {
            updateResult = systemHelpService.insertSystemHelp(systemHelp);
        }

        if (updateResult) {
            responsJson.setCode(1);
            responsJson.setMessage("操作成功");
        } else {
            responsJson.setCode(5);
            responsJson.setMessage("操作失败");
        }

        return responsJson;
    }
}
