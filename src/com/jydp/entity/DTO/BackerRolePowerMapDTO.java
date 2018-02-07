package com.jydp.entity.DTO;

/**
 * 角色权限映射
 * @author sy
 *
 */
public class BackerRolePowerMapDTO {

	private int powerId; //权限Id	
	private String powerName;	//权限名称	
	
	/**
	 * 权限Id	
	 * @return powerId
	 */
	public int getPowerId() {
		return powerId;
	}
	
	/**
	 * 权限Id	
	 * @param powerId the powerId to set
	 */
	public void setPowerId(int powerId) {
		this.powerId = powerId;
	}
	
	/**
	 * 权限名称
	 * @return powerName
	 */
	public String getPowerName() {
		return powerName;
	}
	
	/**
	 * 权限名称
	 * @param powerName the powerName to set
	 */
	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}
	
}
