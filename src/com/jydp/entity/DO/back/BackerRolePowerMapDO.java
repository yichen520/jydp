package com.jydp.entity.DO.back;

import java.sql.Timestamp;

/**
 * 角色权限映射
 * @author sy
 *
 */
public class BackerRolePowerMapDO {

	private int powerId; //权限Id	
	private String powerName;	//权限名称	
	private int powerLevel;	//权限等级,从1级开始
	private int uperPowerId;	//上级权限Id,没有上级填0
	private Timestamp addTime;	//添加时间	
	
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
	 * 权限等级
	 * @return powerLevel
	 */
	public int getPowerLevel() {
		return powerLevel;
	}
	
	/**
	 * 权限等级
	 * @param powerLevel the powerLevel to set
	 */
	public void setPowerLevel(int powerLevel) {
		this.powerLevel = powerLevel;
	}
	
	/**
	 * 上级权限Id
	 * @return uperPowerId
	 */
	public int getUperPowerId() {
		return uperPowerId;
	}
	
	/**
	 * 上级权限Id
	 * @param uperPowerId the uperPowerId to set
	 */
	public void setUperPowerId(int uperPowerId) {
		this.uperPowerId = uperPowerId;
	}
	
	/**
	 * 添加时间
	 * @return addTime
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
