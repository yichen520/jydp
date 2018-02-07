package com.jydp.entity.DO.back;

import java.sql.Timestamp;

/**
 * 后台管理员登录记录
 * @author whx
 *
 */
public class BackerSessionDO {

	private String sessionId;  //sessionId，业务类型（2）+日期（6）+随机位（12）
	private int backerId;  //后台管理员Id
	private String backerAccount;  //后台管理员帐号
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
	 * 后台管理员Id
	 * @return the backerId
	 */
	public int getBackerId() {
		return backerId;
	}

	/**
	 * 后台管理员Id
	 * @param backerId the backerId to set
	 */
	public void setBackerId(int backerId) {
		this.backerId = backerId;
	}

	/**
	 * 后台管理员帐号
	 * @return the backerAccount
	 */
	public String getBackerAccount() {
		return backerAccount;
	}

	/**
	 * 后台管理员帐号
	 * @param backerAccount the backerAccount to set
	 */
	public void setBackerAccount(String backerAccount) {
		this.backerAccount = backerAccount;
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
