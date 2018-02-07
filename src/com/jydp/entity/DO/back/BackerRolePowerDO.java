package com.jydp.entity.DO.back;

import java.sql.Timestamp;

/**
 * 账号角色权限
 * @author sy
 */
public class BackerRolePowerDO {

	private int roleId; //角色Id
	private String powerJson; //权限信息（Json格式字符串）
	private Timestamp addTime; //添加时间
	
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
	 * 权限信息（Json格式字符串）
	 * @return the powerJson
	 */
	public String getPowerJson() {
		return powerJson;
	}

	/**
	 * 权限信息（Json格式字符串）
	 * @param powerJson the powerJson to set
	 */
	public void setPowerJson(String powerJson) {
		this.powerJson = powerJson;
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
}
