package com.jydp.entity.BO;

import java.io.Serializable;

/**
 * 后台管理员登录Session
 * @author whx
 *
 */
public class BackerSessionBO implements Serializable {

	private static final long serialVersionUID = -5152044654113146874L;
	
	private int backerId;  //后台管理员Id
	private String backerAccount;  //后台管理员帐号
	private long outTime;  //过期时间
	
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
