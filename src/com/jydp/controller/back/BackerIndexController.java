package com.jydp.controller.back;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理系统首页Index
 * @author sy
 *
 */
@Controller
@RequestMapping("/backerWeb/backerIndex")
@Scope(value = "prototype")
public class BackerIndexController {

	/** 展示index页面 */
	@RequestMapping(value = "/index.htm")
	public String showIndex(HttpServletRequest request) {
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 0);
		return "page/back/index";
	}




}
