package com.jydp.controller.web;

import config.SystemHelpConfig;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemHelpDO;
import com.jydp.service.ISystemHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    @RequestMapping(value = "/show/{helpIdStr}", method = RequestMethod.GET)
    public String show(HttpServletRequest request, @PathVariable String helpIdStr) {
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
