package com.jydp.entity.BO;

import java.io.Serializable;

/**
 * 用户登录Session
 * @author whx
 *
 */
public class UserSessionBO implements Serializable {

	private static final long serialVersionUID = -3421010463050713202L;

	private String sessionId;// sessionId
	private int userId;  //用户Id
	private String userAccount;  //用户帐号
	private long outTime;  //过期时间

	public String getSessionId() {
		return sessionId;
	}

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
	 * 用户帐号
	 * @return the userAccount
	 */
	public String getUserAccount() {
		return userAccount;
	}
	
	/**
	 * 用户帐号
	 * @param userAccount the userAccount to set
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	/**
	 * 过期时间
	 * @return the outTime
	 */
	public long getOutTime() {
		return outTime;
	}
	
	/**
	 * 过期时间
	 * @param outTime the outTime to set
	 */
	public void setOutTime(long outTime) {
		this.outTime = outTime;
	}
	
}
