package com.iqmkj.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户帮助基本配置信息
 * @author whx
 *
 */
public class SystemHelpConfig {

	public static final Map<Integer, String> userHelpMap = new HashMap<Integer, String>(){
		private static final long serialVersionUID = 1L;{
			put(101010, "注册协议");
			put(101011, "常见问题");
			put(101012, "关于我们");
			put(101013, "联系我们");
			put(101014, "公司简介");
			put(101015, "充值流程");
			put(101016, "注册指南");
			put(101017, "交易指南");

		}
	};
	
	/** 注册协议 */
	public static final int REGISTER_AGREEMENT = 101010;
	
	/** 常见问题 */
	public static final int COMMON_PROBLEM = 101011;

	/** 关于我们 */
	public static final int ABOUT_US = 101012;
	
	/** 联系我们 */
	public static final int CONTACT_US = 101013;

	/** 公司简介 */
	public static final int COMPANY_SYNOPSIS = 101014;

	/** 充值流程 */
	public static final int RECHARGE_PROCESS = 101015;

	/** 注册指南 */
	public static final int REGISTRATION_GUIDE = 101016;

	/** 交易指南 */
	public static final int TRADING_GUIDE = 101017;

}
