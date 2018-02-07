package com.jydp.entity.DTO;

/**
 * 账户角色权限
 * @author sy
 *
 */
public class BackerRoleDTO {

	private int roleId; //角色I
	private String roleName; //角色名称
	private String powerJson; //权限信息，Json格式字符串
	
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
	
	/**
	 * 权限信息，Json格式字符串
	 * @return the powerJson
	 */
	public String getPowerJson() {
		return powerJson;
	}

	/**
	 * 权限信息，Json格式字符串
	 * @param powerJson the powerJson to set
	 */
	public void setPowerJson(String powerJson) {
		this.powerJson = powerJson;
	}
	
}
