package com.jydp.controller.wap;

import com.jydp.entity.DO.system.SystemHelpDO;
import com.jydp.service.ISystemHelpService;
import config.SystemHelpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * 帮助中心
 * @author cyfIverson
 * @create 2018-03-28
 */
@Controller
@RequestMapping("/userWap/wapHelpCenter")
@Scope(value = "prototype")
public class WapHelpCenterController {

    /**  帮助中心 */
    @Autowired
    private ISystemHelpService systemHelpService;

    /** 跳转到注册中心页面 */
    @RequestMapping(value = "/showHelpCenter")
    public String show(){
        return "page/wap/helpCenter";
    }

    /** 显示帮助中心页面 */
    @RequestMapping(value = "/show/{helpIdStr}", method = RequestMethod.GET)
    public String show(HttpServletRequest request, @PathVariable String helpIdStr) {
        //获取访问源 字段
        String requestSource = request.getParameter("type");
        //默认
        int helpId = SystemHelpConfig.COMPANY_SYNOPSIS;
        String helpTitle = SystemHelpConfig.userHelpMap.get(helpId);

        String reg = "[0-9]*";
        if (helpIdStr.length() < 11 && Pattern.matches(reg,helpIdStr)) {
            int help = Integer.parseInt(helpIdStr);
            if (SystemHelpConfig.userHelpMap.containsKey(help)) {
                helpId = Integer.parseInt(helpIdStr);
            }
            helpTitle = SystemHelpConfig.userHelpMap.get(helpId);
        }
        SystemHelpDO systemHelpDO = systemHelpService.getSystemHelpById(helpId);

        request.setAttribute("requestSource",requestSource);
        request.setAttribute("helpTitle",helpTitle);
        request.setAttribute("systemHelpDO", systemHelpDO);
        request.setAttribute("helpId", helpId);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功！");
        return "page/wap/helpDetail";
    }
}
