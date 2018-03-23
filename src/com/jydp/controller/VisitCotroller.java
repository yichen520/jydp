package com.jydp.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 访问分发
 * @author whx
 *
 */
@Controller
@RequestMapping("")
@Scope(value = "prototype")
public class VisitCotroller {
	
	/** 根目录访问 */
	@RequestMapping(value = "")
	public String visitPage(HttpServletRequest request) {
		return "redirect:/userWeb/homePage/show";
	}

	/** web端根目录访问 */
	@RequestMapping(value = "/webLogin")
	public String webLogin(HttpServletRequest request) {
		return "page/web/login";
	}

	/** wap端根目录访问 */
	@RequestMapping(value = "/wapLogin")
	public String wapLogin(HttpServletRequest request) {
		return "page/wap/login";
	}

	/** 后台根目录访问 */
	@RequestMapping(value = "/backLogin")
	public String backLogin(HttpServletRequest request) {
		return "page/back/login";
	}

}
