package com.jydp.entity.DO.user;

import java.sql.Timestamp;

/**
 * 用户登录记录
 * @author whx
 *
 */
public class UserSessionDO {

	private String sessionId;  //sessionId，业务类型（2）+日期（6）+随机位（12）
	private int userId;  //用户Id
	private int loginForm;  //登录来源，1：web端，2：wap端，3：APP端
	private String ipAddress;  //操作时的ip地址
	private Timestamp loginTime;  //登录时间

	/**
	 * sessionId，业务类型（2）+日期（6）+随机位（12）
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * sessionId，业务类型（2）+日期（6）+随机位（12）
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * 用户Id
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * 用户Id
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * 登录来源，1：web端，2：wap端，3：APP端
	 * @return the loginForm
	 */
	public int getLoginForm() {
		return loginForm;
	}

	/**
	 * 登录来源，1：web端，2：wap端，3：APP端
	 * @param loginForm the loginForm to set
	 */
	public void setLoginForm(int loginForm) {
		this.loginForm = loginForm;
	}

	/**
	 * 操作时的ip地址
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * 操作时的ip地址
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * 登录时间
	 * @return the loginTime
	 */
	public Timestamp getLoginTime() {
		return loginTime;
	}

	/**
	 * 登录时间
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
}
