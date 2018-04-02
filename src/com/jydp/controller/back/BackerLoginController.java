package com.jydp.controller.back;

import com.google.code.kaptcha.Constants;
import com.iqmkj.utils.Base64Util;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.back.BackerDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IBackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理员登录
 * @author sy
 *
 */
@Controller
@RequestMapping("/backerWeb/backerLogin")
@Scope(value = "prototype")
public class BackerLoginController {

	/** 后台管理员 */
	@Autowired
	private IBackerService backerService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public  String show(){

		return "page/back/login";
	}
	/** 登录 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request) {
		String backerAccount = StringUtil.stringNullHandle(request.getParameter("backerAccount"));
		String password = StringUtil.stringNullHandle(request.getParameter("password"));
		password = Base64Util.decode(password);
		String validateCode = StringUtil.stringNullHandle(request.getParameter("validateCode"));
		String sessionCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

		request.setAttribute("backerAccount", backerAccount);
		
		if(!StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(sessionCode)){
			request.setAttribute("code", 3);
			request.setAttribute("message", "验证码错误");
			return "page/back/login";
		}
		
		validateCode = validateCode.toLowerCase();
		sessionCode = sessionCode.toLowerCase();
		if(!validateCode.equals(sessionCode)){
			request.setAttribute("code", 3);
			request.setAttribute("message", "验证码错误");
			return "page/back/login";
		}
		
		if(!StringUtil.isNotNull(backerAccount) || !StringUtil.isNotNull(password)){
			request.setAttribute("code", 3);
			request.setAttribute("message", "参数错误");
			return "page/back/login";
		}
		
		password = MD5Util.toMd5(password);
		BackerDO backer = backerService.validateBackerLogin(backerAccount, password);
		if (backer == null) {
			request.setAttribute("code", 3);
			request.setAttribute("message", "账号或密码错误");
			return "page/back/login";
		}
		
		BackerSessionBO backerSessionBO = new BackerSessionBO();
		backerSessionBO.setBackerId(backer.getBackerId());
		backerSessionBO.setBackerAccount(backer.getBackerAccount());
		
		BackerWebInterceptor.loginSuccess(request, backerSessionBO, backer.getRoleId());
		return "redirect:/backerWeb/backerIndex/index.htm";
	}
	
	/** 退出登录 */
	@RequestMapping(value = "/loginOut.htm")
	public String loginOut(HttpServletRequest request) {
		BackerWebInterceptor.loginOut(request);
		
		request.setAttribute("code", 1);
		request.setAttribute("message", "退出登录成功");
		return "page/back/login";
	}
	
}
