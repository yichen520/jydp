package com.jydp.entity.VO;

/**
 * 账号用户角色信息
 * @author sy
 *
 */
public class BackerRoleVO {

	private int roleId; //角色Id
	private String roleName; //角色名称
	private String roleData; //角色信息

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
	 * 角色信息
	 * @return the roleData
	 */
	public String getRoleData() {
		return roleData;
	}

	/**
	 * 角色信息
	 * @param roleData the roleData to set
	 */
	public void setRoleData(String roleData) {
		this.roleData = roleData;
	}
	
}
