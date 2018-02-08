package com.jydp.entity.VO;

import com.jydp.entity.DTO.BackerRolePowerMapDTO;
import java.util.List;

/**
 * 角色权限映射
 * @author sy
 *
 */
public class BackerRolePowerMapVO {

	private int powerId; //权限Id	
	private String powerName;	//权限名称	
	private List<BackerRolePowerMapDTO> powerPowerMapList;  //角色权限映射
	
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
	
	/**
	 * 角色权限映射
	 * @return the powerPowerMapList
	 */
	public List<BackerRolePowerMapDTO> getPowerPowerMapList() {
		return powerPowerMapList;
	}

	/**
	 * 角色权限映射
	 * @param powerPowerMapList the powerPowerMapList to set
	 */
	public void setPowerPowerMapList(List<BackerRolePowerMapDTO> powerPowerMapList) {
		this.powerPowerMapList = powerPowerMapList;
	}
	
}
