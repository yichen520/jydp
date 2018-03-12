package com.jydp.controller.web;

import config.SystemHelpConfig;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemHelpDO;
import com.jydp.service.ISystemHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 帮助中心
 * @Author: wqq
 */
@Controller
@RequestMapping("/userWeb/webHelpCenter")
@Scope(value = "prototype")
public class WebHelpCenterController {

    /**  帮助中心 */
    @Autowired
    private ISystemHelpService systemHelpService;

    /** 显示帮助中心页面 */
    @RequestMapping("/show")
    public String show(HttpServletRequest request) {
        String helpIdStr = StringUtil.stringNullHandle(request.getParameter("helpId"));

        int helpId = SystemHelpConfig.COMPANY_SYNOPSIS;
        if (StringUtil.isNotNull(helpIdStr)) {
            helpId = Integer.parseInt(helpIdStr);
        }

        SystemHelpDO systemHelpDO = systemHelpService.getSystemHelpById(helpId);

        request.setAttribute("systemHelpDO", systemHelpDO);
        request.setAttribute("helpId", helpId);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功！");
        return "page/web/helpCenter";
    }
}
