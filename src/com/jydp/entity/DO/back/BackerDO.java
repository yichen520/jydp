package com.jydp.entity.DO.back;

import java.sql.Timestamp;

/**
 * 后台管理员
 * @author sy
 *
 */
public class BackerDO {

	private int backerId;  //后台管理员Id
	private String backerAccount;  //后台管理员帐号
    private String phone; // 电话号码
	private String password;  //登录密码
	private int roleId;  //角色Id
	private int accountStatus;  //帐号状态，1：启用，2：禁用，-1：删除
	private Timestamp addTime;  //添加时间

	private String roleName; //角色名称

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
     * 电话号码
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 电话号码
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 登录密码
     * @return the password
     */
	public String getPassword() {
		return password;
	}

	/**
	 * 登录密码
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 角色Id
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * 角色Id
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * 帐号状态，1：启用，2：禁用，-1：删除
	 * @return the accountStatus
	 */
	public int getAccountStatus() {
		return accountStatus;
	}

	/**
	 * 帐号状态，1：启用，2：禁用，-1：删除
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * 添加时间
	 * @return the addTime
	 */
	public Timestamp getAddTime() {
		return addTime;
	}

	/**
	 * 添加时间
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	/**
	 * 角色名称
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 角色名称
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
