package com.jydp.controller.web;

import com.jydp.service.ISystemNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统公告
 * @Author: wqq
 */
@Controller
@RequestMapping("/userWeb/systemNotice")
@Scope(value = "prototype")
public class SystemNoticeController {

    /**  系统公告 */
    @Autowired
    private ISystemNoticeService systemNoticeService;

    /** 系统公告展示  */
    @RequestMapping(value = "/show.htm")
    public void show(HttpServletRequest request) {
 }
}
